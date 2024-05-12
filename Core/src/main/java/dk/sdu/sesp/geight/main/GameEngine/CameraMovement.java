package dk.sdu.sesp.geight.main.GameEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraMovement {
    //Creating a singleton and camera setup
    private static CameraMovement instance;
    private int widthScreen, heightScreen;
    private final OrthographicCamera camera;
    private final float initialX = 500;

    private CameraMovement(int width, int height) {
        this.widthScreen = width;
        this.heightScreen = height;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, widthScreen, heightScreen);
        setInitialPosition(initialX);
    }

    // Static method for initializing the camera singleton
    public static synchronized void initialize(int width, int height) {
        if (instance == null) {
            instance = new CameraMovement(width, height);
        } else {
            // Handle re-initialization logic here
            // if the camera settings needs to be changed without creating a new instance
            instance.widthScreen = width;
            instance.heightScreen = height;
        }
    }

    // Static method to get the instance of the class
    public static synchronized CameraMovement getInstance() {
        if (instance == null) {
            throw new IllegalStateException("CameraMovement is not initialized");
        }
        return instance;
    }

    // Get camera
    public OrthographicCamera getCamera () {
        return camera;
    }

    public void updateCamera() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float cameraMoveSpeed = 500; // Adjust as necessary

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x -= cameraMoveSpeed * deltaTime;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += cameraMoveSpeed * deltaTime;
        }

        camera.position.x = Math.max(camera.position.x, widthScreen / 2);
        camera.position.x = Math.min(camera.position.x, 7680 - widthScreen / 2);
        camera.update();

    }




    private void setInitialPosition(float x){
        camera.position.set(x, 1080/2, 0);
        camera.update();
    }
}

