package screen.menu;

import javafx.scene.paint.Color;
import manager.MapManager;
import javafx.scene.Group;
import javafx.scene.Scene;

public class PregameMenu extends Menu {
    @Override
    public Scene getScene(){
        Group root = new Group();
        Scene scene = new Scene(root, 1280, 720, Color.BLACK);
        setBackground(root, getClass().getResource("/Menu/Background.png").toExternalForm());
        drawImage(getClass().getResource("/PregameMenu/ChooseMap.png").toExternalForm(), 284, 16);
        drawImage(getClass().getResource("/PregameMenu/Bilkent.png").toExternalForm(), 104, 138);
        drawImage(getClass().getResource("/PregameMenu/World.png").toExternalForm(), 711, 138);
        strokeRect("gold", 708, 135, 431, 326, 3);
        strokeRect("gold", 101, 135, 469, 326, 3);
        addButtons(root);
        return scene;
    }

    public void addButtons(Group root) {
        addTransitionButton(root, "", 495, 561, 294, 51, getClass().getResource("/Menu/Back.png").toExternalForm(), new MainMenu());
        addTransitionButton(root, "", 68,  468, 536, 37, getClass().getResource("/PregameMenu/BilkentButton.png").toExternalForm(), new GameStartMenu(MapManager.BILKENT_MAP));
        addTransitionButton(root, "", 674,  468, 536, 37, getClass().getResource("/PregameMenu/WorldButton.png").toExternalForm(), new GameStartMenu(MapManager.WORLD_MAP));
    }
}
