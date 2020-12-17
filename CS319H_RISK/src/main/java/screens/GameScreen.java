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
        regions = Game.getInstance().getGameManager().getMapManager().getMap().getRegionList();
        root = new Group();
        scene = new Scene(root, 1280, 720);
        update();
        Game.getInstance().getGameManager().getInputManager().awaitAttackAction();
    }

    public void update(){
        System.out.println("hello");
        root.getChildren().clear();
        Canvas canvas = new Canvas(1280, 720);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "Game\\Map.png").toUri().toString()), 0, 0, 1280, 720);
        root.getChildren().add(canvas);
        populateMap();
    }

    private void populateMap() {
        for(Region r: regions){
            addLabel(r);
            System.out.println(r.getyCoordinate() + ", " + r.getyCoordinate());
        }
    }

    private void addLabel(Region region){
        Button label = new Button(String.valueOf(region.getUnitCount()));
        Image image = new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "Game\\Region\\RedLabel.png").toUri().toString());
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
        namebar.setLayoutX(region.getxCoordinate() - region.getRegionName().length() - 25);
        namebar.setMinSize(55, 20);
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
