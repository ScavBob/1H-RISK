package screens;

import javafx.scene.Group;
import javafx.scene.Scene;

public class MainMenu extends Menu {

	   @Override
	    public Scene getScene() {
	        Group root = new Group();
	        Scene scene = new Scene(root);
	        addButtons(root);

	        return scene;
	    }

	private void addButtons(Group root) {
		addTransitionButton(root, "", 100, 100, 75, 439, "img.png", null);
		addTransitionButton(root, "Load Game", 300, 300, 160, 40,"img.png", null);
		addTransitionButton(root, "Learn to Conquer", 300, 400, 160, 40,"img.png", null);
		addTransitionButton(root, "Settings", 300, 450, 160, 40,"img.png", new OptionsMenu());
		addTransitionButton(root, "Exit", 300, 500, 160, 40,"img.png", null);
	}
}
