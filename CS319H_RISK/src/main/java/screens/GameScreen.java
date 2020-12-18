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
        canvas.getGraphicsContext2D().setFill(Paint.valueOf(Game.getInstance().getGameManager().getMatch().getCurrentPlayer().getColor()));
        canvas.getGraphicsContext2D().fillRect(15, 15, 250, 50);
        addText("It's " + Game.getInstance().getGameManager().getMatch().getCurrentPlayer().getName() + "'s Turn", 50, 50, 250, 50, 25);
        Region region = Game.getInstance().getGameManager().getInputManager().getFirstRegion();
        if(phase == 0){
            if(region == null)
                addText("No source region selected.", 0, 460, 250, 50, 20);
            else {
                addText("Source Region: ", 0, 460, 100, 50, 20);
                addText(region.getRegionName(), 50, 480, 100, 50, 20);
            }
            addText("Reinforcements Left: ", 0, 540, 250, 50, 20);
            addText(String.valueOf(Game.getInstance().getGameManager().getMatch().getCurrentPlayer().getAvailableReinforcements() - armyCount), 200, 540, 250, 50, 25);
        }

        else if(phase == 1){
            if(region == null)
                addText("No source region selected.", 0, 460, 250, 50, 20);
            else {
                addText("Source Region: ", 0, 460, 100, 50, 20);
                addText(region.getRegionName(), 50, 480, 100, 50, 20);
            }
            region = Game.getInstance().getGameManager().getInputManager().getSecondRegion();
            if(region == null)
                addText("No destination region selected.", 0, 500, 250, 50, 20);
            else {
                addText("Destination Region: ", 0, 500, 100, 50, 20);
                addText(region.getRegionName(), 50, 520, 100, 50, 20);
            }
        }

        else{
            if(region == null)
            addText("No source region selected.", 0, 460, 250, 50, 20);
            else {
                addText("Source Region: ", 0, 460, 100, 50, 20);
                addText(region.getRegionName(), 50, 480, 100, 50, 20);
                addText("Fortifications Left: ", 0, 540, 250, 50, 20);
                addText(String.valueOf(Game.getInstance().getGameManager().getInputManager().getFirstRegion().getUnitCount() - armyCount), 200, 540, 250, 50, 25);
            }
            region = Game.getInstance().getGameManager().getInputManager().getSecondRegion();
            if(region == null)
                addText("No destination region selected.", 0, 500, 250, 50, 20);
            else {
                addText("Destination Region: ", 0, 500, 100, 50, 20);
                addText(region.getRegionName(), 50, 520, 100, 50, 20);
            }
        }
        addText(String.valueOf(armyCount), 105, 610, 50, 50, 25);
        addButton("", 150, 575, 50, 50, StorageManager.RESOURCES_FOLDER_NAME + "Game\\UI\\Source-Destination\\Add.png", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(armyCount + 1 <= Game.getInstance().getGameManager().getInputManager().getMaxChoosableArmy())
                    armyCount = armyCount + 1;
                Game.getInstance().updateScreen();
            }
        });
        addButton("", 25, 575, 50, 50, StorageManager.RESOURCES_FOLDER_NAME + "Game\\UI\\Source-Destination\\Remove.png", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(armyCount - 1 >= 0)
                    armyCount = armyCount - 1;
                Game.getInstance().updateScreen();
            }
        });
        addButton("", 87, 650, 50, 50, StorageManager.RESOURCES_FOLDER_NAME + "Game\\UI\\Source-Destination\\Done.png", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(Game.getInstance().getGameManager().getInputManager().getFirstRegion() != null) {
                    Game.getInstance().getGameManager().getInputManager().chooseArmyCount(armyCount);
                    armyCount = 0;
                    Game.getInstance().updateScreen();
                }
            }
        });
    }

    private void addText(String title, int x, int y, int width, int height, int fontSize){
        Text text = new Text(title);
        text.setFont(Font.font("Helvetica", fontSize));
        text.setLayoutX(x);
        text.setLayoutY(y);
        text.setFill(Paint.valueOf("#ffffffff"));
        root.getChildren().add(text);
    }

    private void addLabels(Region region){
        int x = region.getxCoordinate();
        int y = region.getyCoordinate();
        String unitCount = String.valueOf(region.getUnitCount());
        String regionName = region.getRegionName();
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Game.getInstance().getGameManager().getInputManager().chooseRegion(region);
                update();
            }
        };
        addButton(unitCount, x - 10, y - 10, 25, 25, StorageManager.RESOURCES_FOLDER_NAME + "Game\\Region\\" + region.getController().getColor() + "Label.png", handler).setTextFill(Paint.valueOf("#ffffffff"));

        addButton(regionName, x+regionName.length()/2-25, y + 15, regionName.length() + 20, 25, StorageManager.RESOURCES_FOLDER_NAME + "Game\\Region\\Namebar.png", handler).setTextFill(Paint.valueOf("#ffffffff"));
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
