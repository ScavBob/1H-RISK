package screens;

import game.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import managers.StorageManager;

import java.awt.*;
import java.nio.file.Paths;
import javafx.scene.control.Button;

public class MainMenu extends Menu {


	   @Override
	    public Scene getScene() {
	        Group root = new Group();
	        Scene scene = new Scene(root, 1280, 720);
			setBackground(root, StorageManager.RESOURCES_FOLDER_NAME + "Menu\\Background.png");
		   	addButtons(root);
		   	drawImage(StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\Label.png", 302, 66);
	        return scene;
	    }

	private void addButtons(Group root) {
		addTransitionButton(root, "", 495, 335, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\NewGame.png", new PregameMenu());
		addTransitionButton(root, "", 495, 392, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\LoadGame.png", new HowToPlayScreen());
		addTransitionButton(root, "", 495, 447, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\Options.png", new OptionsMenu());
		addTransitionButton(root, "", 495, 504, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\HowToPlay.png", null);
		//TODO
		addButtons(root, "", 495, 561, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\Exit.png", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
	}
}
