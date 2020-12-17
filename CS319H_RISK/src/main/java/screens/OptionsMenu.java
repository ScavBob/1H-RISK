package screens;

import game.Game;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import managers.StorageManager;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;

public class OptionsMenu extends Menu{

    public OptionsMenu()
    {

    }

    @Override
    public Scene getScene() {
        Group root = new Group();
        Scene scene = new Scene(root, 1280, 720, Color.BLACK);
        setBackground(root, StorageManager.RESOURCES_FOLDER_NAME + "\\Menu\\Background.png");

        addTransitionButton(root, "", 495, 561, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "\\Menu\\Back.png", new MainMenu());

        HBox box = new HBox();
        //box.setBackground(new Background(new BackgroundFill(Paint.valueOf("#92D0507f")), CornerRadii.EMPTY, Insets.EMPTY));
        Slider volumeSlider = new Slider(0, 10, Game.getInstance().getGameManager().getSettingsManager().getVolume());
        volumeSlider.setMinWidth(300);
        volumeSlider.setMinHeight(300);
        volumeSlider.setMinorTickCount(0);
        volumeSlider.setMajorTickUnit(1);
        volumeSlider.setSnapToTicks(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Game.getInstance().getGameManager().getSettingsManager().setVolume(newValue.intValue());
            }
        });

        box.getChildren().add(volumeSlider);
        box.setPadding(new Insets(100,400,0,490));
        root.getChildren().add(box);

        //TODO
        Text volumeText = new Text(540, 200, "Volume");
        volumeText.setFill(Paint.valueOf("#ffccffff"));
        volumeText.setFont(new Font(60));
        root.getChildren().add(volumeText);

        addButtons(root, "CHANGE SAVE FOLDER", 400, 400, 200, 100, "", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DirectoryChooser directoryChooser = new DirectoryChooser ();
                File selectedDirectory = directoryChooser.showDialog(Game.getInstance().getStage());
                if (selectedDirectory != null)
                {
                    Game.getInstance().getGameManager().getSettingsManager().setSaveLocation(selectedDirectory);
                }
            }
        });

        return scene;
    }

}
