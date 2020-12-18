package screens;

import application.Player;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import managers.MapManager;
import managers.StorageManager;

import java.nio.file.Paths;
import java.util.ArrayList;

public class GameStartMenu extends Menu {

    private class Slot {
        private int x, y;
        private Group root;
        private String playerType;
        private String playerName = "";
        private String playerFaction = "";
        private String playerColor = "";
        private boolean playerAdded = false;

        private Button   addPlayer;
        //private Button faction;
        private Button   color;
        private Button   delete;
        private Button   changeType;
        private Button   type;
        private TextArea name;

        protected Slot(Group root, int i, String playerColor){
            addPlayer = new Button();
            //faction = new Button();
            color = new Button();
            delete = new Button();
            changeType = new Button();
            type = new Button();
            name = new TextArea();
            this.playerColor = playerColor;
            playerName = "Player " + (i + 1);
            this.root = root;
            x = 55;
            y = 60 + i*90;
            addButton(addPlayer, x, y, 495, 77, StorageManager.RESOURCES_FOLDER_NAME + "GameStartMenu\\AddPlayerButton.png");
            addButton(type, x, y, 495, 77, StorageManager.RESOURCES_FOLDER_NAME + "GameStartMenu\\Type.png");
            //addButton(faction, x - 200, y, 50, 50, StorageManager.RESOURCES_FOLDER_NAME + "Game\\Factions\\England.png");
            addButton(color, x + 40, y + 25, 40, 26,  StorageManager.RESOURCES_FOLDER_NAME + "Game\\Colors\\" + playerColor + ".png");
            addButton(delete, x + 420, y + 13, 50, 50, StorageManager.RESOURCES_FOLDER_NAME + "GameStartMenu\\Delete.png");
            addButton(changeType, x + 340, y + 8, 68, 60, StorageManager.RESOURCES_FOLDER_NAME + "GameStartMenu\\HUMAN.png");
            changeType.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Image image;
                    if(playerType == "HUMAN") {
                        image = new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "GameStartMenu\\AI.png").toUri().toString());
                        playerType = "AI";
                    }
                    else{
                        image = new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "GameStartMenu\\HUMAN.png").toUri().toString());
                        playerType = "HUMAN";
                    }
                    changeType.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                }
            });
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    removePlayer();
                }
            });
            name.setLayoutX(x + 90);
            name.setLayoutY(y + 13);
            name.setMaxSize(175, 50);
            name.setFont(new Font(15));
            name.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    playerName = name.getText();
                }
            });
            draw(i);
        }

        private void addButton(Button button, int x, int y, int width, int height, String imagePath){
            Image image = new Image(Paths.get(imagePath).toUri().toString());
            button.setMinSize(width, height);
            button.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
            button.setLayoutX(x);
            button.setLayoutY(y);
        }

        private void draw(int i){
                addPlayer.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event){
                    addPlayer();
                    playerType = "HUMAN";
                    name.setText(playerName);
                }
            });
                root.getChildren().add(addPlayer);

        }

        private void addPlayer() {
            root.getChildren().remove(addPlayer);
            root.getChildren().add(type);
            root.getChildren().add(delete);
            //root.getChildren().add(faction);
            root.getChildren().add(color);
            root.getChildren().add(name);
            root.getChildren().add(changeType);
            playerAdded = true;
        }

        private void addAI(){

        }

        private void removePlayer(){
            root.getChildren().remove(type);
            root.getChildren().remove(delete);
            //root.getChildren().remove(faction);
            root.getChildren().remove(color);
            root.getChildren().remove(name);
            root.getChildren().remove(changeType);
            root.getChildren().add(addPlayer);
            playerAdded = false;
        }

    }

    private String[] colors = {"Red", "Green", "Blue", "Yellow", "Purple", "Black", "Pink"};
    private String map;
    private Slot[] slots = new Slot[7];

    public GameStartMenu(String map) {
        this.map = map;
    }

    @Override
    public Scene getScene() {
        Group root = new Group();
        Scene scene = new Scene(root, 1280, 720, Color.BLACK);
        Canvas canvas = new Canvas();
        setBackground(root, StorageManager.RESOURCES_FOLDER_NAME + "Menu\\Background.png");
        drawRect("#b4c7e77f", 38, 38, 540, 654);
        root.getChildren().add(canvas);
        for(int i = 0; i < 7; i++) {
            slots[i] = new Slot(root, i, colors[i]);
        }
        drawImage(StorageManager.RESOURCES_FOLDER_NAME + "\\GameStartMenu\\" + map + "Map.png", 578, 38);
        addButtons(root);
        return scene;
    }

    private void addButtons(Group root) {
        addButtons(root, "", 1220, 50, 55, 50, StorageManager.RESOURCES_FOLDER_NAME + "GameStartMenu\\Start.png", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startGame();
            }
        });
    }

    private void startGame() {
        ArrayList<Player> playerList = new ArrayList<Player>();
        for (Slot s : slots) {
            if(s.playerAdded) {
                playerList.add(new Player(s.playerName, s.playerColor));
            }
        }
        if (playerList.size() >= 2) {
            Game.getInstance().getGameManager().startMatch(MapManager.WORLD_MAP, playerList);
            Game.getInstance().setScreen(new GameScreen());
        }
        else{
            //TODO
        }
    }
}
