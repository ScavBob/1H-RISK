package screens;

import application.Faction;
import application.Player;
import game.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;

public class MainMenu extends Menu {


	   @Override
	    public Scene getScene() {
	        Group root = new Group();
	        Scene scene = new Scene(root, 1280, 720, Color.BLACK);
		   setBackground(root, getClass().getResource("/Menu/Background.png").toExternalForm());
		   	addButtons(root);
		   	drawImage(getClass().getResource("/MainMenu/Label.png").toExternalForm(), 302, 66);
	        return scene;
	    }

	private void addButtons(Group root) {
		addTransitionButton(root, "", 495, 335, 294, 51, getClass().getResource("/MainMenu/NewGame.png").toExternalForm(), new PregameMenu());
		addButtons(root, "", 495, 392, 294, 51, getClass().getResource("/MainMenu/LoadGame.png").toExternalForm(), new EventHandler<ActionEvent>() {
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
		addTransitionButton(root, "", 495, 447, 294, 51, getClass().getResource("/MainMenu/Options.png").toExternalForm(), new OptionsMenu(this));
		addTransitionButton(root, "", 495, 504, 294, 51, getClass().getResource("/MainMenu/HowToPlay.png").toExternalForm(),  new HowToPlayScreen());
		addButtons(root, "", 495, 561, 294, 51, getClass().getResource("/MainMenu/Exit.png").toExternalForm(), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
	}
}
