package screens;

import application.Player;
import application.Region;
import game.Game;

import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;

public class GameScreen implements UpdatableScreen{

    private Group root;
    private Scene scene;
    private Region[] regions;
    private Canvas canvas;
    private int armyCount;
    private Text timer;
    private boolean confirmation;
    private int currentArmyCount;
    private Text battleLog;

    public GameScreen() {
        regions = Game.getInstance().getGameManager().getMatch().getMap().getRegionList();
        armyCount = Game.getInstance().getGameManager().getInputManager().getArmyCount();
        root = new Group();
        scene = new Scene(root, 1280, 720);
        battleLog = new Text();
        timer = new Text();
        update();

        Game.getInstance().subscribeForUpdate(this);
        Game.getInstance().setCurrentGameScreen(this);

        setBattleLog("Game Started...");
    }

    public void update(){
        currentArmyCount = 0;
        root.getChildren().clear();
        canvas = new Canvas(1280, 720);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(new Image(getClass().getResource("/GameResources/" + Game.getInstance().getGameManager().getMatch().getMap().getMapName() + "Map.png").toExternalForm()), 0, 0, 1280, 720);
        gc.drawImage(new Image(getClass().getResource("/GameResources/Phases/" + Game.getInstance().getGameManager().getMatch().getCurrentPhase() + ".png").toExternalForm()), 495, 1, 291, 60);
        gc.setFill(Paint.valueOf("#0000007f"));
        gc.fillRect(1170, 20, 90, 40);
        root.getChildren().add(canvas);
        gc.setFill(Paint.valueOf("#0000007f"));
        gc.fillRect(757, 694, 523, 26);
        drawBattleLog();
        root.getChildren().add(battleLog);
        root.getChildren().add(timer);
        populateScreen();
    }

    public void nextPhaseInScreen()
    {
        armyCount = 0;
    }

    public void setBattleLog(String log) {
        battleLog.setText(log);
    }

    public void drawBattleLog(){   //757 x 694
        battleLog.setLayoutX(767);
        battleLog.setLayoutY(714);
        battleLog.setFont(new Font("Helvenica", 20));
        battleLog.setFill(Paint.valueOf("white"));
    }

    public void updateTimer()
    {
        runTimer();
    }

    private void runTimer() {
        timer.setX(1180);
        timer.setY(50);
        timer.setFont(Font.font("Helvenica", 30));
        timer.setFill(Paint.valueOf("white"));
        int remainingTime = Game.getInstance().getGameManager().getMatch().getRemainingTime();
        if(remainingTime <= 50000) {
            String time = "";
            if (remainingTime / 60 < 10)
                time += "0" + remainingTime / 60;
            else
                time += remainingTime / 60;
            time += ":";
            if (remainingTime % 60 < 10)
                time += "0" + remainingTime % 60;
            else
                time += remainingTime % 60;
            timer.setText(time);
        }else{
            timer.setText("  ∞");
        }
    }

    public boolean confirmBattle(int attacking, int defending, boolean showButtons){
        confirmation = true;
        Stage dialog = new Stage();
        dialog.setResizable(false);
        dialog.initStyle(StageStyle.UTILITY);
        Group root = new Group();
        Scene scene = new Scene(root, 500, 270, Color.BLACK);
        Canvas canvas = new Canvas(500, 270);
        dialog.setScene(scene);
        Image background = new Image(getClass().getResource("/GameResources/RollingDice/Background.png").toExternalForm());
        canvas.getGraphicsContext2D().drawImage(background, 0, 0);
        Image dice = new Image(getClass().getResource("/GameResources/RollingDice/qm.png").toExternalForm());
        for(int i = 0; i < attacking; i++){
            canvas.getGraphicsContext2D().drawImage(dice, 5, 40 + (240/attacking)*i);
        }
        for(int i = 0; i < defending; i++){
            canvas.getGraphicsContext2D().drawImage(dice, 425, 60 + (280/attacking)*i);
        }
        root.getChildren().add(canvas);
        if(showButtons) {
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
                }
            });
            denialButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    confirmation = false;
                    dialog.close();
                }
            });
            root.getChildren().add(confirmationButton);
            root.getChildren().add(denialButton);
            addText(root, "Confirm Attack?", 175, 220, new Font("Helvenica", 20), "white");
        }else{
            Button close = new Button("Close");
            close.setLayoutX(175);
            close.setLayoutY(220);
            close.setMinSize(75,25);
            close.setOnAction(event -> {
                dialog.close();
            });
            root.getChildren().add(close);
        }
        addText(root, "Attacking Armies", 10, 30, new Font("Impact", 25), "white");
        addText(root, "Defending Armies", 310, 30, new Font("Impact", 25), "white");
        dialog.showAndWait();
        return confirmation;
    }

    public int showBattleResults(List<Integer> attackerDice, List<Integer> defenderDice, List<Boolean> results, boolean won, int maxMovableArmies, boolean isAI){
        currentArmyCount = 1;
        Stage dialog = new Stage();
        dialog.setResizable(false);
        dialog.initStyle(StageStyle.UTILITY);
        Group root = new Group();
        Scene scene = new Scene(root, 500, 270, Color.BLACK);
        scene.getStylesheets().add("style.css");
        Canvas canvas = new Canvas(500, 270);
        dialog.setScene(scene);
        Image background = new Image(getClass().getResource("/GameResources/RollingDice/Background.png").toExternalForm());
        canvas.getGraphicsContext2D().drawImage(background, 0, 0);
        Timeline timeline = new Timeline();
        for(int i = 0; i < defenderDice.size(); i++){
            int x = 25;
            int y = 40 + (240/defenderDice.size())*i;
            Image dice = new Image(getClass().getResource("/GameResources/RollingDice/" + attackerDice.get(i) + ".png").toExternalForm());
            canvas.getGraphicsContext2D().drawImage(dice, x, y);
            dice = new Image(getClass().getResource("/GameResources/RollingDice/" + defenderDice.get(i) + ".png").toExternalForm());
            canvas.getGraphicsContext2D().drawImage(dice, x + 385, y);
        }
        root.getChildren().add(canvas);
        if(!isAI) {
            if (won) {
                if(maxMovableArmies != 1) {
                    Slider armySlider = new Slider(1, maxMovableArmies, 1);
                    armySlider.setSnapToTicks(true);
                    armySlider.setMinWidth(100);
                    armySlider.setLayoutX(185);
                    armySlider.setLayoutY(185);
                    armySlider.setMinorTickCount(0);
                    armySlider.setMajorTickUnit(1);
                    armySlider.setSnapToTicks(true);
                    armySlider.setShowTickMarks(true);
                    armySlider.setShowTickLabels(true);
                    armySlider.getStyleClass().setAll("slider");

                    addText(root, "# of Armies to move:", 150, 100, new Font("Helvetica", 20), "white");
                    Text armyCount = new Text(String.valueOf(currentArmyCount));
                    armySlider.valueProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            currentArmyCount = newValue.intValue();
                            armyCount.setText(currentArmyCount + "");
                        }
                    });
                    armyCount.setLayoutX(250);
                    armyCount.setLayoutY(150);
                    armyCount.setFill(Paint.valueOf("white"));
                    armyCount.setFont(new Font("Helvetica", 25));
                    root.getChildren().add(armySlider);
                    root.getChildren().add(armyCount);
                }
            }
            Button confirm = new Button("Confirm");
            confirm.setLayoutX(217);
            confirm.setLayoutY(225);
            confirm.setMinSize(75, 25);
            confirm.setOnAction(event -> {
                dialog.close();
            });
            root.getChildren().add(confirm);
        }else{
            Button close = new Button("Close");
            close.setLayoutX(217);
            close.setLayoutY(225);
            close.setMinSize(75, 25);
            close.setOnAction(event -> {
                dialog.close();
            });
            root.getChildren().add(close);
        }

        addText(root, "Attacking Armies", 10, 30, new Font("Impact", 25), "white");
        addText(root, "Defending Armies", 310, 30, new Font("Impact", 25), "white");
        dialog.showAndWait();
        return currentArmyCount;
    }

    private void populateScreen() {
        for(Region r: regions){
            addLabels(r);
        }

        addButton(root, "", 575, 645, 75, 75, getClass().getResource("/GameResources/UI/Pause-Skip/Pause.png").toExternalForm(), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Game.getInstance().setScreen(new PauseMenu());
            }
        });
        addButton(root, "", 630, 645, 75, 75, getClass().getResource("/GameResources/UI/Pause-Skip/Skip.png").toExternalForm(), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Game.getInstance().getGameManager().getInputManager().endPhase();
                    }
                });
        addElements(Game.getInstance().getGameManager().getInputManager().getCurrentPhase());
    }

    private void addElements(int phase){
        addButton(root, "", 0, 420, 250 , 300, getClass().getResource("/GameResources/UI/Source-Destination/Background.png").toExternalForm(), null);
        String color = Paint.valueOf(Game.getInstance().getGameManager().getMatch().getCurrentPlayer().getColor()).toString();

        color = color.substring(0, color.length() - 2) + "5f";

        canvas.getGraphicsContext2D().setFill(Paint.valueOf(color));
        canvas.getGraphicsContext2D().fillRect(15, 15, 400, 50);
        addText("It's the " + Game.getInstance().getGameManager().getMatch().getCurrentPlayer().getFaction().getFactionName() + " Turn (" + Game.getInstance().getGameManager().getMatch().getCurrentPlayer().getName() + ")", 50, 50, 25);
        Region region = Game.getInstance().getGameManager().getInputManager().getFirstRegion();
        /*if(phase == 0){
            addButton(root, "Use Cards", 25, 380, 50, 30, "", event -> {

            });*/
            if(region == null)
                addText("No region selected.", 0, 460, 20);
            else {
                addText("Reinforcing Region: ", 0, 460, 20);
                addText(region.getRegionName(), 50, 480, 20);
            }
            addText("Reinforcements Left: ", 0, 540, 20);
            addText(String.valueOf(Game.getInstance().getGameManager().getMatch().getCurrentPlayer().getAvailableReinforcements() - armyCount), 200, 540, 25);
        }

        else if(phase == 1){
            if(region == null)
                addText("No region selected.", 0, 460, 20);
            else {
                addText("Attacking from Region: ", 0, 460, 20);
                addText(region.getRegionName(), 50, 480, 20);
            }
            region = Game.getInstance().getGameManager().getInputManager().getSecondRegion();
            if(region == null)
                addText("No region selected.", 0, 500, 20);
            else {
                addText("Attacking to Region: ", 0, 500, 20);
                addText(region.getRegionName(), 50, 520, 20);
            }
        }

        else{
            if(region == null)
            addText("No region selected.", 0, 460, 20);
            else {
                addText("Fortifying from Region: ", 0, 460, 20);
                addText(region.getRegionName(), 50, 480, 20);
                addText("Fortifications Left: ", 0, 540, 20);
                addText(String.valueOf(Game.getInstance().getGameManager().getInputManager().getFirstRegion().getUnitCount() - armyCount - 1), 200, 540, 25);
            }
            region = Game.getInstance().getGameManager().getInputManager().getSecondRegion();
            if(region == null)
                addText("No region selected.", 0, 500, 20);
            else {
                addText("Fortifying to Region: ", 0, 500, 20);
                addText(region.getRegionName(), 50, 520, 20);
            }
        }
        Text armyCountText = addText(String.valueOf(armyCount), 105, 610, 25);
        addButton(root, "", 150, 575, 50, 50, getClass().getResource("/GameResources/UI/Source-Destination/Add.png").toExternalForm(), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(armyCount + 1 <= Game.getInstance().getGameManager().getInputManager().getMaxChoosableArmy())
                    armyCount = armyCount + 1;
                armyCountText.setText(armyCount + "");
                update();
            }
        });
        addButton(root, "", 25, 575, 50, 50, getClass().getResource("/GameResources/UI/Source-Destination/Remove.png").toExternalForm(), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(armyCount - 1 >= 0)
                    armyCount = armyCount - 1;
                armyCountText.setText(armyCount + "");
                update();
            }
        });
        addButton(root,"", 115, 650, 50, 50, getClass().getResource("/GameResources/UI/Source-Destination/Done.png").toExternalForm(), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(Game.getInstance().getGameManager().getInputManager().getFirstRegion() != null) {
                    Game.getInstance().getGameManager().getInputManager().chooseArmyCount(armyCount);
                    armyCount = 0;
                    armyCountText.setText(armyCount + "");
                    update();
                }
            }
        });
        addButton(root, "", 55, 650, 50, 50, getClass().getResource("/GameResources/UI/Source-Destination/Cancel.png").toExternalForm(), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               Game.getInstance().getGameManager().getInputManager().resetRegions();
                update();
            }
        });
        addButton(root, "", 175, 650, 50, 50, getClass().getResource("/GameResources/UI/Source-Destination/Learn.png").toExternalForm(), event -> {
            Player player = Game.getInstance().getGameManager().getMatch().getCurrentPlayer();
            Game.getInstance().showInformationMessage(player.getName()+ "'s Mission",player.getMission().getMissionName(),  player.getMission().getMissionDetails(), getClass().getResource("/GameResources/Factions/" + player.getFaction().getFactionID() + ".png").toExternalForm());
        });
    }

    private Text addText(Group root, String title, int x, int y, Font font, String color){
        Text text = new Text(title);
        text.setFill(Paint.valueOf(color));
        text.setLayoutX(x);
        text.setLayoutY(y);
        text.setFont(font);
        root.getChildren().add(text);
        return text;
    }

    private Text addText(String title, int x, int y, int fontSize){
        return addText(root, title, x, y, new Font("Helvetica", fontSize), "white");
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
        addButton(root, unitCount, x - 10, y - 10, 25, 25, getClass().getResource("/GameResources/Region/" + region.getController().getColor() + "Label.png").toExternalForm(), handler).setTextFill(Paint.valueOf("#ffffffff"));

        addButton(root, regionName, x+regionName.length()/2-25, y + 15, regionName.length() + 20, 25, getClass().getResource("/GameResources/Region/Namebar.png").toExternalForm(), handler).setTextFill(Paint.valueOf("#ffffffff"));
    }

    private Button addButton(Group root, String title, int x, int y, int width, int height, String imagePath, EventHandler<ActionEvent> event){
        Button button = new Button(title);
        Image image = new Image(imagePath);
        if(image != null)
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
