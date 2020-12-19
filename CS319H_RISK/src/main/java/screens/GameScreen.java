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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import managers.StorageManager;

import java.nio.file.Paths;

public class GameScreen implements UpdatableScreen{

    private Group root;
    private Scene scene;
    private Region[] regions;
    private Canvas canvas;
    private int armyCount;
    private Text timer;
    private boolean confirmation;

    public GameScreen() {
        regions = Game.getInstance().getGameManager().getMatch().getMap().getRegionList();
        armyCount = Game.getInstance().getGameManager().getInputManager().getArmyCount();
        root = new Group();
        scene = new Scene(root, 1280, 720);
        update();

        Game.getInstance().subscribeForUpdate(this);
        Game.getInstance().setCurrentGameScreen(this);
    }

    public void update(){
        root.getChildren().clear();
        canvas = new Canvas(1280, 720);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "Game\\" + Game.getInstance().getGameManager().getMatch().getMap().getMapName() + "Map.png").toUri().toString()), 0, 0, 1280, 720);
        root.getChildren().add(canvas);
        gc.drawImage(new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "Game\\Phases\\" + Game.getInstance().getGameManager().getMatch().getCurrentPhase() + ".png").toUri().toString()), 495, 1, 291, 60);
        gc.setFill(Paint.valueOf("#0000007f"));
        gc.fillRect(1170, 20, 90, 40);
        populateScreen();
        runTimer();
    }

    private void runTimer() {
        timer = new Text();
        int remainingTime = Game.getInstance().getGameManager().getMatch().getRemainingTime();
        String time = "";
        if(remainingTime/60 < 10)
            time+="0"+remainingTime/60;
        else
            time+=remainingTime/60;
        time += ":";
        if(remainingTime%60 < 10)
            time += "0" + remainingTime%60;
        else
            time += remainingTime%60;

        timer.setText(time);
        timer.setX(1180);
        timer.setY(50);
        timer.setFont(Font.font("Helvenica",30));
        timer.setFill(Paint.valueOf("white"));
        root.getChildren().add(timer);
    }

    public boolean confirmBattle(int attacking, int defending){
        confirmation = false;
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        Group root = new Group();
        Scene scene = new Scene(root, 500, 270, Color.BLACK);
        Canvas canvas = new Canvas(500, 270);
        dialog.setScene(scene);
        Image background = new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "Game\\RollingDice\\Background.png").toUri().toString());
        System.out.println(StorageManager.RESOURCES_FOLDER_NAME + "Game\\RollingDice\\Background.png");
        canvas.getGraphicsContext2D().drawImage(background, 0, 0);
        Text attackingText = new Text("Attacking Armies");
        attackingText.setFill(Paint.valueOf("white"));
        attackingText.setLayoutX(10);
        attackingText.setLayoutY(30);
        attackingText.setFont(new Font("Impact", 25));
        Text defendingText = new Text("Defending Armies");
        defendingText.setFill(Paint.valueOf("white"));
        defendingText.setLayoutX(310);
        defendingText.setLayoutY(30);
        defendingText.setFont(new Font("Impact", 25));
        Image dice = new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "Game\\RollingDice\\qm.png").toUri().toString());
        for(int i = 0; i < attacking; i++){
            canvas.getGraphicsContext2D().drawImage(dice, 5, 40 + (240/attacking)*i);
        }
        for(int i = 0; i < defending; i++){
            canvas.getGraphicsContext2D().drawImage(dice, 435, 60 + (280/attacking)*i);
        }
        root.getChildren().add(canvas);
        root.getChildren().add(attackingText);
        root.getChildren().add(defendingText);
        Text confirmationText = new Text("Confirm Attack?");
        confirmationText.setLayoutX(175);
        confirmationText.setLayoutY(220);
        confirmationText.setFill(Paint.valueOf("white"));
        confirmationText.setFont(new Font("Helvenica", 20));
        Button confirmationButton = new Button("Accept");
        confirmationButton.setLayoutX(170);
        confirmationButton.setLayoutY(240);
        confirmationButton.setMinSize(75, 25);
        Button denialButton = new Button("Deny");
        denialButton.setLayoutX(255);
        denialButton.setLayoutY(240);
        denialButton.setMinSize(75, 25);
        confirmationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                confirmation = true;
                dialog.close();
                System.out.println(confirmation);
            }
        });
        denialButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                confirmation = false;
                dialog.close();
                System.out.println(confirmation);
            }
        });
        root.getChildren().add(confirmationButton);
        root.getChildren().add(denialButton);
        root.getChildren().add(confirmationText);
        dialog.showAndWait();
        return confirmation;
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
                addText(String.valueOf(Game.getInstance().getGameManager().getInputManager().getFirstRegion().getUnitCount() - armyCount - 1), 200, 540, 250, 50, 25);
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
