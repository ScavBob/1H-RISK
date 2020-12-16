package screens;

import game.Game;
import screens.TransitionButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import java.nio.file.Paths;

public abstract class Menu implements Screen {

	private Canvas canvas;

	private void addCanvas(){
		canvas = new Canvas(1280.0, 720.0);
	}

	public void setBackground(Group root, String backgroundPath){
		addCanvas();
		GraphicsContext graphics = canvas.getGraphicsContext2D();
		Image background = new Image(Paths.get(backgroundPath).toUri().toString());
		graphics.drawImage(background, 0, 0, 1280.0, 720.0);
		root.getChildren().add(canvas);
	}

	public void addTransitionButton(Group root, String text, int x, int y, int width, int height, String imagePath, Screen screen) {
		TransitionButton button = new TransitionButton(text, x, y, width, height, imagePath, screen);
		root.getChildren().add(button);
	}


}
