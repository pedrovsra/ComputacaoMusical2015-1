/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.material.Material;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.util.TangentBinormalGenerator;
import de.lessvoid.nifty.Nifty;

/**
 *
 * @author Pedro Tiago
 */
public class StartScreenApp extends SimpleApplication {

    private StartScreenController start;
    private CalibrationScreenController calib;
    public AudioNode song;
    
    private boolean play;
    //Box b;
    //Geometry geom;
    //Material mat;

    @Override
    public void simpleInitApp() {

        NiftyJmeDisplay display = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = display.getNifty();
        nifty.fromXml("Interface/screen.xml", "calibration");
        guiViewPort.addProcessor(display);
        flyCam.setDragToRotate(true);
        
        play = true;

        //Sphere red = new Sphere(32, 32, 2f);
        //b = new Box(1, 1, 1);
        //geom = new Geometry("Sphere", b);
        //red.setTextureMode(Sphere.TextureMode.Projected);
        //TangentBinormalGenerator.generate(b);

        //mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //Texture cube1Tex = assetManager.loadTexture("Interface/Clickers.png");
        //mat.setColor("Color", ColorRGBA.Red);
        //mat.setTexture("ColorMap", cube1Tex);
        //geom.setMaterial(mat);

        //rootNode.attachChild(geom);
        //initAudio();
    }

    private void initAudio() {
        song = new AudioNode(assetManager, "Sounds/Songs/Master Exploder.ogg", true);
        song.setLooping(false);  // activate continuous playing
        song.setPositional(false);
        song.setVolume(3);
        rootNode.attachChild(song);
        song.play(); // play continuously!
    }

    @Override
    public void simpleUpdate(float tpf) {
        if(!play) song.stop();
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    public void stopAudio() {
        play = false;
        //this.song.pause();
    }
}
