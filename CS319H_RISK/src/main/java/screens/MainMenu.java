package screens;

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
		   System.out.println("hello");
	        Group root = new Group();
		   System.out.println("hello1");
	        Scene scene = new Scene(root, 1280, 720, Color.BLACK);
		   System.out.println("hello2");
		   setBackground(root, getClass().getResource("/Menu/Background.png").toExternalForm());
		   System.out.println("hello3");
		   	addButtons(root);
		   System.out.println("hello4");
		   	drawImage(getClass().getResource("/MainMenu/Label.png").toExternalForm(), 302, 66);
		   System.out.println("hello5");
	        return scene;
	    }

	private void addButtons(Group root) {
		System.out.println("atakan1");
		addTransitionButton(root, "", 495, 335, 294, 51, getClass().getResource("/MainMenu/NewGame.png").toExternalForm(), new PregameMenu());
		System.out.println("atakan2");
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
		System.out.println("atakan3");
		addTransitionButton(root, "", 495, 447, 294, 51, getClass().getResource("/MainMenu/Options.png").toExternalForm(), new OptionsMenu());
		System.out.println("atakan4");
		addTransitionButton(root, "", 495, 504, 294, 51, getClass().getResource("/MainMenu/HowToPlay.png").toExternalForm(),  new HowToPlayScreen());
		System.out.println("atakan5");
		addButtons(root, "", 495, 561, 294, 51, getClass().getResource("/MainMenu/Exit.png").toExternalForm(), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		System.out.println("atakan6");
	}
}
