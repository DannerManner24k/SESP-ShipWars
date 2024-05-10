package dk.sdu.sesp.geight.main.Core.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import org.lwjgl.opengl.GL20;

import dk.sdu.sesp.geight.main.GameEngine.CameraMovement;
import dk.sdu.sesp.geight.mapsystem.MapPlugin;

import static dk.sdu.sesp.geight.main.Helper.Constants.PPM;


public class GameScreen extends ScreenAdapter {

    private SpriteBatch batch; // Batch to render the game
    private World world; // Box2D world
    private Box2DDebugRenderer box2DDebugRenderer; // Box2D debug renderer

    private MapPlugin mapPlugin; // Map plugin

    //Camera instance
    private final CameraMovement cameraMovement;
    private final OrthographicCamera camera; // Camera to render the game


    public GameScreen() {
        this.batch = new SpriteBatch();// Set the batch
        this.world = new World(new Vector2(0,0), false); // Set the world
        this.box2DDebugRenderer = new Box2DDebugRenderer(); // Set the debug renderer
        this.mapPlugin = new MapPlugin(); // Set the map plugin

        // Initialize the components
        this.cameraMovement = CameraMovement.getInstance();
        this.camera = cameraMovement.getCamera();
    }



    private void update() {
        world.step(1/60f, 6, 2); // Update the world
        cameraMovement.updateCamera(); // Updated line

        batch.setProjectionMatrix(camera.combined); // Set the batch projection matrix

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) { // If the escape key is pressed
            Gdx.app.exit(); // Exit the game
        }
    }


    @Override
    public void render(float delta) {
        this.update();// Update the game
        Gdx.gl.glClearColor(0, 0, 0, 1); // Set the clear color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen


        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        batch.begin(); // Begin the batch



        batch.end();// End the batch
        box2DDebugRenderer.render(world, camera.combined.scl(PPM));// Render the Box2D world
    }

    @Override
    public void dispose() {
        // Dispose of resources
        batch.dispose();
        world.dispose();
        box2DDebugRenderer.dispose();
    }

}
