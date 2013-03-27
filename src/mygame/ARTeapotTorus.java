package mygame;

import armonkeykit.core.app.ARMonkeyKitApp;
import armonkeykit.core.app.utils.eventlisteners.NodeRotateTranslateListener;
import armonkeykit.core.markerprocessor.pattern.PatternMarkerProcessor;
import armonkeykit.core.markers.PatternMarker;
import com.jme3.bounding.BoundingSphere;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Torus;

public class ARTeapotTorus extends ARMonkeyKitApp {

    private PatternMarkerProcessor markerProcessor;
    private NodeRotateTranslateListener rtl;

    public ARTeapotTorus() {
        super();
        showCamera = true; // enable or disable camera feed
        showSceneViewer = true; // enable or disable SceneMonitor
    }

    protected void simpleInitARSystem() {
        markerProcessor = initPatternProcessor();
        rtl = new NodeRotateTranslateListener(true);
        markerProcessor.registerEventListener(rtl);
    }

    protected void addMarkers() {
        PatternMarker kanji = markerProcessor.createMarkerObject("kanji", 16,
                "ardata/patt.kanji", 80);
        markerProcessor.registerMarker(kanji);

        PatternMarker hiro = markerProcessor.createMarkerObject("hiro", 16,
                "ardata/patt.hiro", 80);
        markerProcessor.registerMarker(hiro);

        Node teapotAffectedNode = new Node("Affected Teapot Node");
        Teapot tp = new Teapot("ShinyTeapot");
        tp.setModelBound(new BoundingSphere());
        tp.updateModelBound();
        tp.setLocalScale(10f);
        // rotate our teapot so its base sits on the marker
        Quaternion q = new Quaternion();
        q = q.fromAngleAxis(-FastMath.PI / 2, new Vector3f(1f, 0f, 0f));
        tp.setLocalRotation(q);

        teapotAffectedNode.attachChild(tp);
        rootNode.attachChild(teapotAffectedNode);

        Node torusAffectedNode = new Node("Affected Torus Node");
        Torus torus = new Torus("Torus", 12, 40, 1.5f, 3f);
        torus.setLocalScale(10f);
        torus.setModelBound(new BoundingSphere());
        torus.updateModelBound();
        torusAffectedNode.attachChild(torus);
        rootNode.attachChild(torusAffectedNode);

        /**
         * Use the associate method of the event listener to create a
         * relationship between a marker object and the ARContentNode we created
         * for that marker.
         */
        rtl.associate(kanji, teapotAffectedNode);
        rtl.associate(hiro, torusAffectedNode);

        /**
         * This method must be called after adding markers, to ensure that the
         * detection list is up to date
         */
        markerProcessor.finaliseMarkers();

    }

    protected void callUpdates() {
    }

    public static void main(String[] args) {
        ARTeapotTorus app = new ARTeapotTorus();
        app.setConfigShowMode(ConfigShowMode.AlwaysShow);
        app.start();
    }

    protected void configOptions() {
    }
}
