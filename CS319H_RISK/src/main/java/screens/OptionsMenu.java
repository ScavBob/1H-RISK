package screens;

import game.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import managers.StorageManager;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

public class OptionsMenu extends Menu{

    public OptionsMenu()
    {

    }

    @Override
    public Scene getScene() {
        Group root = new Group();
        Scene scene = new Scene(root);
        setBackground(root, StorageManager.RESOURCES_FOLDER_NAME + "\\Menu\\Background.png");

        addTransitionButton(root, "", 495, 511, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "\\Menu\\Back.png", new MainMenu());

        HBox box = new HBox();
        Slider volumeSlider = new Slider(0, 10, Game.getInstance().getGameManager().getSettingsManager().getVolume());
        volumeSlider.setMinWidth(300);
        volumeSlider.setMinHeight(300);
        volumeSlider.setMinorTickCount(0);
        volumeSlider.setMajorTickUnit(1);
        volumeSlider.setSnapToTicks(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setShowTickLabels(true);

        box.getChildren().add(volumeSlider);
        box.setPadding(new Insets(100,400,0,490));
        root.getChildren().add(box);

        Label volumeLabel = new Label("Volume");
        volumeLabel.setLayoutX(300);
        volumeLabel.setLayoutY(300);
        root.getChildren().add(volumeLabel);

        addButtons(root, "CHANGE SAVE FOLDER", 400, 400, 200, 100, "", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.showOpenDialog(Game.getInstance().getStage());
            }
        });

        return scene;
    }

}
