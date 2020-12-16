package screens;

import javafx.scene.Group;
import javafx.scene.Scene;
import managers.StorageManager;

import java.awt.*;
import java.nio.file.Paths;

public class MainMenu extends Menu {


	   @Override
	    public Scene getScene() {
	        Group root = new Group();
	        Scene scene = new Scene(root, 1280, 720);
			setBackground(root, StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\Background.png");
		   	addButtons(root);
	        return scene;
	    }

	private void addButtons(Group root) {
		addTransitionButton(root, "", 495, 285, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\NewGame.png", null);
		addTransitionButton(root, "", 495, 342, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\LoadGame.png", null);
		addTransitionButton(root, "", 495, 397, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\Options.png", new OptionsMenu());
		addTransitionButton(root, "", 495, 454, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\HowToPlay.png", null);
		addTransitionButton(root, "", 495, 511, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\Exit.png", null);
	}
}
