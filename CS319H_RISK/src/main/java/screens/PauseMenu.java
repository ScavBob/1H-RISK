package screens;

import game.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import managers.StorageManager;

public class PauseMenu extends Menu {

    private final int SWIDTH  = 1280;
    private final int SHEIGHT = 720;
   private final int BUTTONXLAYOUT = 495;
   private final int BUTTONYLAYOUT = 490;

    //Resume
    //Save
    //Exit
    TextInputDialog saveWindow = new TextInputDialog("");

    @Override
    public Scene getScene() {
        Group root = new Group();
        Scene scene = new Scene(root, SWIDTH, SHEIGHT, Color.BLACK);
        setBackground(root, StorageManager.RESOURCES_FOLDER_NAME + "Menu\\Background.png");
        addTransitionButtons(root);
        addEventButtons(root);
        return scene;
    }

    private void addTransitionButtons(Group root){
        addTransitionButton(root, "", BUTTONXLAYOUT, BUTTONYLAYOUT - 71, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "Menu\\Back.png", new GameScreen());
        addTransitionButton(root, "", BUTTONXLAYOUT, BUTTONYLAYOUT+71, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "Menu\\Back.png", new MainMenu());
    }

    private void addEventButtons(Group root){
        addButtons(root, "", BUTTONXLAYOUT, BUTTONYLAYOUT, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\Exit.png", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveWindow.showAndWait();
                Game.getInstance().getGameManager().getStorageManager().saveGame(Game.getInstance().getGameManager().getMatch(),
                        saveWindow.getEditor().getText());
            }
        });


    }
}
