package screens;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class OptionsMenu extends Menu {

    public OptionsMenu()
    {

    }

    @Override
    public Scene getScene() {
        Group root = new Group();
        Scene scene = new Scene(root);
        Button button1 = new Button("Back");
        root.getChildren().add(button1);
        return scene;
    }
}
