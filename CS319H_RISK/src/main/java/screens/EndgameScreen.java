package screens;

import application.Player;
import game.Game;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class EndgameScreen implements Screen{
    private Canvas canvas;
    private Player player;

    public EndgameScreen(Player player){
        canvas = new Canvas(1280, 720);
        this.player = player;
    }

    @Override
    public Scene getScene() {
        Group root = new Group();
        Scene scene = new Scene(root, 1280, 720, Color.BLACK);
        setBackground(root, getClass().getResource("/GameResources/EndScreen/Background.png").toExternalForm());
        addButtons(root);
        canvas.getGraphicsContext2D().setFill(Paint.valueOf("#0000007f"));
        canvas.getGraphicsContext2D().fillRect(130, 305, 1005, 193);
        drawImage(root, getClass().getResource("/GameResources/EndScreen/Label.png").toExternalForm(), 140, 315);
        writeEndScreen();
        root.getChildren().add(canvas);
        return scene;
    }

    private void setBackground(Group root, String imagePath) {
        drawImage(root, imagePath, 0, 0);
    }

    private void addButtons(Group root){
        Button button = new Button();
        button.setLayoutX(495);
        button.setLayoutY(561);
        Image image = new Image(getClass().getResource("/Menu/Back.png").toExternalForm());
        button.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        button.setOnAction(event -> {
            Game.getInstance().setScreen(new MainMenu());
        });
    }

    private void writeEndScreen(){
        Image flag = new Image(getClass().getResource("/GameResources/Factions/" + player.getFaction().getFactionID() + ".png").toExternalForm());
        canvas.getGraphicsContext2D().drawImage(flag, 130, 503);
        Text congratz = new Text();

    }

    private void drawImage(Group root, String imagePath, int x, int y){
        Image image = new Image(imagePath);
        canvas.getGraphicsContext2D().drawImage(image, x, y);
    }
}
