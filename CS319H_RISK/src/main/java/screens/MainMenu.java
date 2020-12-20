package screens;

import game.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import managers.StorageManager;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import javafx.scene.control.Button;

public class MainMenu extends Menu {


	   @Override
	    public Scene getScene() {
	        Group root = new Group();
	        Scene scene = new Scene(root, 1280, 720, Color.BLACK);
			setBackground(root, getClass().getResource( "/Menu/Background.png").toExternalForm(), "");
		   	addButtons(root);
		   	drawImage(getClass().getResource("/MainMenu/Label.png").toExternalForm(), 302, 66, "");
	        return scene;
	    }

	private void addButtons(Group root) {
		addTransitionButton(root, "", 495, 335, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\NewGame.png", new PregameMenu());
		addButtons(root, "", 495, 392, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\LoadGame.png", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser ();
				fileChooser.setInitialDirectory(Game.getInstance().getGameManager().getSettingsManager().getSaveLocation());
				File selectedFile = fileChooser.showOpenDialog(Game.getInstance().getStage());
				if (selectedFile != null)
				{
					Game.getInstance().getGameManager().loadMatch(selectedFile);
				}
			}
		});
		addTransitionButton(root, "", 495, 447, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\Options.png", new OptionsMenu());
		addTransitionButton(root, "", 495, 504, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\HowToPlay.png",  new HowToPlayScreen());
		//TODO
		addButtons(root, "", 495, 561, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "MainMenu\\Exit.png", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
	}
}
