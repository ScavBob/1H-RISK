package screens;

import application.Region;
import game.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import managers.StorageManager;

import java.nio.file.Paths;

public class GameScreen implements UpdatableScreen{

    private Group root;
    private Scene scene;
    private Region[] regions;
    private Canvas canvas;
    private int armyCount;

    public GameScreen() {
        regions = Game.getInstance().getGameManager().getMatch().getMap().getRegionList();
        armyCount = Game.getInstance().getGameManager().getInputManager().getArmyCount();
        root = new Group();
        scene = new Scene(root, 1280, 720);
        update();

        Game.getInstance().subscribeForUpdate(this);
    }

    public void update(){
        root.getChildren().clear();
        canvas = new Canvas(1280, 720);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "Game\\Map.png").toUri().toString()), 0, 0, 1280, 720);
        root.getChildren().add(canvas);
        gc.drawImage(new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "Game\\Phases\\" + Game.getInstance().getGameManager().getInputManager().getCurrentPhase() + ".png").toUri().toString()), 495, 1, 291, 60);
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
        addElements(Game.getInstance().getGameManager().getInputManager().getCurrentPhase());
    }

    private void addElements(int phase){
        addButton("", 0, 420, 250 , 300, StorageManager.RESOURCES_FOLDER_NAME + "Game\\UI\\Source-Destination\\Background.png", null);
        if(phase == 0){
            Region region = Game.getInstance().getGameManager().getInputManager().getFirstRegion();
            if(region == null)
                addText("No region selected.", 0, 450, 250, 50);
            else
                addText(region.getRegionName(), 0, 450, 100, 50);
            addButton("", 50, 650, 25, 25, StorageManager.RESOURCES_FOLDER_NAME + "Menu\\Back.png", new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Game.getInstance().getGameManager().getInputManager().chooseArmyCount(3);
                }
            });

        }else if(phase == 1){
            Region region = Game.getInstance().getGameManager().getInputManager().getFirstRegion();
            if(region == null)
                addText("No region selected.", 0, 450, 250, 50);
            else
                addText(region.getRegionName(), 0, 450, 100, 50);
            region = Game.getInstance().getGameManager().getInputManager().getSecondRegion();
            if(region == null)
                addText("No region selected.", 0, 520, 250, 50);
            else
                addText(region.getRegionName(), 0, 520, 250, 50);
            addText(String.valueOf(armyCount), 100, 625, 50, 50);
            addButton("", 50, 650, 25, 25, StorageManager.RESOURCES_FOLDER_NAME + "Menu\\Back.png", new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(armyCount + 1 <= Game.getInstance().getGameManager().getInputManager().getFirstRegion().getUnitCount())
                        armyCount = armyCount + 1;
                    Game.getInstance().updateScreen();
                }
            });
            addButton("", 100, 675, 25, 25, StorageManager.RESOURCES_FOLDER_NAME + "Menu\\Back.png", new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Game.getInstance().getGameManager().getInputManager().chooseArmyCount(armyCount);
                    armyCount = 0;
                    Game.getInstance().updateScreen();
                }
            });

        }else{

        }
    }

    private void addText(String title, int x, int y, int width, int height){
        Text text = new Text(title);
        text.setFont(Font.font("Helvetica", 25));
        text.setLayoutX(x);
        text.setLayoutY(y);
        text.setFill(Paint.valueOf("#ffffffff"));
        //text.setMaxSize(width, height);
        //text.setEditable(false);
        //text.setBackground(new Background(new BackgroundFill(Paint.valueOf("#808080ff"), CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(text);
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
                canvas.getGraphicsContext2D().fillOval(x - 10, y- 10, 25, 25);
                canvas.getGraphicsContext2D().fillRect(x+regionName.length()/2-25, y+15, regionName.length() + 20,25);
            }
        }).setTextFill(Paint.valueOf("#ffffffff"));

        addButton(regionName, x+regionName.length()/2-25, y + 15, regionName.length() + 20, 25, StorageManager.RESOURCES_FOLDER_NAME + "Game\\Region\\Namebar.png", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Game.getInstance().getGameManager().getInputManager().chooseRegion(region);
                canvas.getGraphicsContext2D().fillOval(x - 10, y- 10, 25, 25);
                canvas.getGraphicsContext2D().fillRect(x+regionName.length()/2-25, y+15, regionName.length() + 20,25);
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
