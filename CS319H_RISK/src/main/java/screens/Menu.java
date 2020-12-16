package screens;

import game.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import java.nio.file.Paths;

public abstract class Menu implements Screen {
	public boolean addCanvas() {
		return true;
	}

	public Button addButtons(Group root, String text, double x, double y, int height, int width, String imagePath,
			EventHandler<ActionEvent> eventHandler) {
		Button button = new Button(text);
		button.setLayoutX(x);
		button.setLayoutY(y);
		if (height != 0)
			button.setMinHeight(height);
		if (width != 0)
			button.setMinWidth(width);
		if(eventHandler != null)
			button.setOnAction(eventHandler); //439 x 75
		root.getChildren().add(button);
		Image background = new Image(Paths.get(imagePath).toUri().toString(), true);
		button.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		return button;
	}

	public void addTransitionButton(Group root, String text, double x, double y, int height, int width, String imagePath,
			Scene nextScene) {

			addButtons(root, text, x, y, height, width, imagePath, new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					Game.getInstance().getStage().setScene(nextScene);
					System.out.println("Hello");
				}
			});
	}
}
