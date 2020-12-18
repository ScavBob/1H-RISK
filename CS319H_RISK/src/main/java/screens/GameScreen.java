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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import managers.StorageManager;

import java.nio.file.Paths;

public class GameScreen implements UpdatableScreen{

    private Group root;
    private Scene scene;
    private Region[] regions;
    public GameScreen() {
        regions = Game.getInstance().getGameManager().getMatch().getMap().getRegionList();
        root = new Group();
        scene = new Scene(root, 1280, 720);
        update();

        Game.getInstance().subscribeForUpdate(this);
    }

    public void update(){
        root.getChildren().clear();
        Canvas canvas = new Canvas(1280, 720);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "Game\\Map.png").toUri().toString()), 0, 0, 1280, 720);
        root.getChildren().add(canvas);
        gc.drawImage(new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "Game\\Phases\\" + Game.getInstance().getGameManager().getInputManager().getCurrentPhaseName() + ".png").toUri().toString()), 495, 1, 291, 60);
        populateScreen();
    }

    private void populateScreen() {
        for(Region r: regions){
            addLabels(r);
        }

        addButton("", 575, 645, 75, 75, StorageManager.RESOURCES_FOLDER_NAME + "Game\\UI\\Pause-Skip\\Pause.png", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Game.getInstance().setScreen(new PauseMenu());
            }
        });
        addButton("", 630, 645, 75, 75, StorageManager.RESOURCES_FOLDER_NAME + "Game\\UI\\Pause-Skip\\Skip.png", new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Game.getInstance().getGameManager().getInputManager().endPhase();
                    }
                });
        addElements();
    }

    private void addElements(){
        addButton("", 0, 420, 250 , 300, StorageManager.RESOURCES_FOLDER_NAME + "Game\\UI\\Source-Destination\\Background.png", null);
        TextArea text = new TextArea("Hello");
        text.setFont(new Font("Serif Sans", 25));
        text.setLayoutX(100);
        text.setLayoutY(100);
        text.setMinSize(100, 100);
        //root.getChildren().add(text);
    }

    private void addLabels(Region region){
        int x = region.getxCoordinate();
        int y = region.getyCoordinate();
        String unitCount = String.valueOf(region.getUnitCount());
        String regionName = region.getRegionName();
        addButton(unitCount, x - 10, y - 10, 25, 25, StorageManager.RESOURCES_FOLDER_NAME + "Game\\Region\\" + region.getController().getColor() + "Label.png", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(region.getRegionName());
                Game.getInstance().getGameManager().getInputManager().chooseRegion(region);
            }
        }).setTextFill(Paint.valueOf("#ffffffff"));

        addButton(regionName, x+regionName.length()/2-25, y + 15, regionName.length() + 20, 25, StorageManager.RESOURCES_FOLDER_NAME + "Game\\Region\\Namebar.png", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Game.getInstance().getGameManager().getInputManager().chooseRegion(region);
            }
        }).setTextFill(Paint.valueOf("#ffffffff"));
    }

    private Button addButton(String title, int x, int y, int width, int height, String imagePath, EventHandler<ActionEvent> event){
        Button button = new Button(title);
        Image image = new Image(Paths.get(imagePath).toUri().toString());
        button.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setMinSize(width, height);
        button.setOnAction(event);
        root.getChildren().add(button);
        return button;
    }

    @Override
    public Scene getScene() {
       update();
       return scene;
    }
}
