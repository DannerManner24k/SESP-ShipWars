package dk.sdu.sesp.geight.main.Core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import dk.sdu.sesp.geight.main.Core.screens.GameScreen;
import dk.sdu.sesp.geight.main.Core.screens.StartScreen;
import dk.sdu.sesp.geight.main.Helper.Constants;


public class DesktopLauncher {

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setIdleFPS(60);
        config.setTitle("ShipWars");
        config.setWindowedMode(Constants.screenWidth, Constants.screenHeight);

        new Lwjgl3Application(new Game() {
            @Override
            public void create() {
                setScreen(new StartScreen(this));
            }
        }, config);
    }
}
