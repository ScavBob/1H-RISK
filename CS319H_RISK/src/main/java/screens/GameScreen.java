package screens;

import application.Region;
import game.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import managers.StorageManager;

import java.nio.file.Paths;

public class GameScreen implements Screen{

    private Group root;
    private Scene scene;
    private Region[] regions;
    public GameScreen(){
        regions = Game.getInstance().getGameManager().getMatch().getMap().getRegionList();
        root = new Group();
        scene = new Scene(root, 1280, 720);
        update();
    }

    public void update(){
        root.getChildren().clear();
        Canvas canvas = new Canvas(1280, 720);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "Game\\Map.png").toUri().toString()), 0, 0, 1280, 720);
        root.getChildren().add(canvas);
        gc.drawImage(new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "Game\\Phases\\" + Game.getInstance().getGameManager().getInputManager().getCurrentPhaseName() + ".png").toUri().toString()), 452, 1, 375, 77);
        populateScreen();
    }

    private void populateScreen() {
        for(Region r: regions){
            addLabels(r);
        }
        Button pauseButton = new Button();
        Image image = new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "Game\\Pause.png").toUri().toString());
        pauseButton.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        pauseButton.setLayoutX(575);
        pauseButton.setLayoutY(645);
        pauseButton.setMinSize(75, 75);
        pauseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Game.getInstance().setScreen(new PauseMenu());
            }
        });
        Button skipButton = new Button();
        image = new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "Game\\Skip.png").toUri().toString());
        skipButton.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        skipButton.setLayoutX(630);
        skipButton.setLayoutY(645);
        skipButton.setMinSize(75, 75);
        skipButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Game.getInstance().getGameManager().getInputManager().endPhase();
                System.out.println("hello");
            }
        });
        root.getChildren().add(pauseButton);
        root.getChildren().add(skipButton);
    }

    private void addElements(){

    }

    private void addLabels(Region region){
        Button label = new Button(String.valueOf(region.getUnitCount()));
        Image image = new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "Game\\Region\\"+ region.getController().getColor() + "Label.png").toUri().toString());
        label.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        label.setLayoutX(region.getxCoordinate() - 10);
        label.setLayoutY(region.getyCoordinate() - 10);
        label.setTextFill(Paint.valueOf("#ffffffff"));
        label.setMinSize(image.getWidth(), image.getHeight());
        EventHandler<ActionEvent> actionHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(region.getRegionName());
                System.out.println(Game.getInstance().getGameManager().getInputManager().chooseRegion(region));
                System.out.println(Game.getInstance().getGameManager().getInputManager().getAttackAction());
            }
        };
        Button namebar = new Button(region.getRegionName());
        Image barImage = new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "Game\\Region\\Namebar.png").toUri().toString());
        namebar.setBackground(new Background(new BackgroundImage(barImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        namebar.setLayoutY(region.getyCoordinate() + 15);
        namebar.setLayoutX(region.getxCoordinate() + region.getRegionName().length()/2 - 25);
        namebar.setMinSize(region.getRegionName().length() + 20, 25);
        namebar.setTextFill(Paint.valueOf("#ffffffff"));
        namebar.setOnAction(actionHandler);
        label.setOnAction(actionHandler);
        root.getChildren().add(label);
        root.getChildren().add(namebar);
    }

    @Override
    public Scene getScene() {
       update();
       return scene;
    }
}
