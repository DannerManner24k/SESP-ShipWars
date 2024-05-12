package dk.sdu.sesp.geight.main.Core;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import dk.sdu.sesp.geight.main.Core.screens.GameScreen;
import dk.sdu.sesp.geight.main.GameEngine.CameraMovement;

public class Boot extends Game {

    public static Boot INSTANCE; // Singleton instance

    public Boot() {
        INSTANCE = this;
    }


    @Override
    public void create() {
        // Screen dimensions
        int widthScreen = Gdx.graphics.getWidth();
        int heightScreen = Gdx.graphics.getHeight();
        CameraMovement.initialize(widthScreen, heightScreen);
        setScreen( new GameScreen());
    }
}
