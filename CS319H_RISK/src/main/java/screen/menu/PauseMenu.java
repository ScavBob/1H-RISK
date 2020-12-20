package screen.menu;

import gamelauncher.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import screen.GameScreen;

public class PauseMenu extends Menu {

    private final int SWIDTH  = 1280;
    private final int SHEIGHT = 720;
    private final int BUTTONXLAYOUT = 495;
    private final int BUTTONYLAYOUT = 490;
    Group root;
    Scene scene;
    TextInputDialog saveWindow;

    /**
     * PauseMenu constructor
     */
    public PauseMenu() {
        root = new Group();
        scene = new Scene(root, SWIDTH, SHEIGHT, Color.BLACK);
        saveWindow = new TextInputDialog("");
        setBackground(root, getClass().getResource("/Menu/Background.png").toExternalForm());
        addTransitionButtons(root);
        addEventButtons(root);
        saveWindow.setTitle("Save File");
        saveWindow.setContentText("Choose the name of your save game.");
        saveWindow.setHeaderText("");
    }

    /**
     * @return Scene returns the scene object for display
     */
    @Override
    public Scene getScene() {

        return scene;
    }

    /**
     * A method that creates transition buttons, adds the to the roots and returns the roots
     * @param root returns a Group object named root
     */
    private void addTransitionButtons(Group root){
        addTransitionButton(root, "", BUTTONXLAYOUT, BUTTONYLAYOUT - 140, 294, 51, getClass().getResource("/GameResources/PauseMenu/Resume.png").toExternalForm(), new GameScreen());
        addTransitionButton(root, "", BUTTONXLAYOUT, BUTTONYLAYOUT - 71, 294, 51, getClass().getResource("/MainMenu/Options.png").toExternalForm(), new OptionsMenu(this));
        addTransitionButton(root, "", BUTTONXLAYOUT, BUTTONYLAYOUT+71, 294, 51, getClass().getResource("/GameResources/PauseMenu/BackToMenu.png").toExternalForm(), new MainMenu());
    }

    /**
     * A method that creates event buttons, adds the to the roots and returns the roots
     * @param root returns a Group object named root
     */
    private void addEventButtons(Group root){
        addButtons(root, "", BUTTONXLAYOUT, BUTTONYLAYOUT, 294, 51, getClass().getResource("/GameResources/PauseMenu/SaveGame.png").toExternalForm(), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveWindow.showAndWait();
                if(saveWindow.getEditor().getText().equals("")){

                }
                else{
                    Game.getInstance().getGameManager().getStorageManager().saveGame(Game.getInstance().getGameManager().getMatch(),
                            saveWindow.getEditor().getText());
                }

            }
        });


    }
}
