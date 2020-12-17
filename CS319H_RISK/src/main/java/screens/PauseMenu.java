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

    //Resume
    //Save
    //Exit
    TextInputDialog saveWındow = new TextInputDialog("");

    @Override
    public Scene getScene() {
        Group root = new Group();
        Scene scene = new Scene(root, 1280, 720, Color.BLACK);
        setBackground(root, StorageManager.RESOURCES_FOLDER_NAME + "Menu\\Background.png");
        addTransitionButtons(root);
        addEventButtons(root);
        return scene;
    }

    private void addTransitionButtons(Group root){
        addTransitionButton(root, "", 495, 561, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "Menu\\Back.png", new GameScreen());
        addTransitionButton(root, "", 495, 490, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "Menu\\Back.png", new MainMenu());
    }

    private void addEventButtons(Group root){
        addButtons(root, "", 495, 540, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\Exit.png", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveWındow.showAndWait();
                System.out.println(saveWındow.getEditor().getText());
                //Game.getInstance().getGameManager().getStorageManager().saveGame("td.getEditor().getText()");
            }
        });


    }
}
