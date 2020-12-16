package screens;

import game.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.w3c.dom.events.Event;

import java.nio.file.Paths;

public class TransitionButton extends Button implements EventHandler<ActionEvent> {

    private Screen screenToShow;

    public TransitionButton(String text, int x, int y, int width, int height, String imagePath, Screen screenToShow)
    {
        super(text);
        setLayoutX(x);
        setLayoutY(y);
        if (height != 0)
            setMinHeight(height);
        if (width != 0)
            setMinWidth(width);

        this.screenToShow = screenToShow;
        setOnAction(this);

        Image background = new Image(Paths.get(imagePath).toUri().toString(), true);
        setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
    }


    @Override
    public void handle(ActionEvent event) {
        Game.getInstance().setScreen(this.screenToShow);
    }
}
