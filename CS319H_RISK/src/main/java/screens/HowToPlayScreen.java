package screens;


import game.Game;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import managers.StorageManager;
import javafx.scene.image.Image;


public class HowToPlayScreen extends Menu {


    private final int HowToPlayImgNum = 35;
    private final int SWIDTH  = 1280;
    private final int SHEIGHT = 720;
    private final int IWIDTH = SWIDTH * 2 / 3;
    private final int IHEIGHT = SHEIGHT * 2 / 3;
    Image[] imageArray = new Image[HowToPlayImgNum];
    int currentImageNumber = 0;
    ImageView imageView;
    Label pageNumberDisplay;
    Group root;
    Scene scene;

    /**
     * HowToPlayScreen constructor
     */
    HowToPlayScreen(){
        root = new Group();
        scene = new Scene(root, SWIDTH, SHEIGHT, Color.BLACK);
        setBackground(root, getClass().getResource("/Menu/Background.png").toExternalForm());
        addButtons(root);
        initImageArray();
        pageNumberDisplay = new Label();
        imageView = new ImageView(imageArray[currentImageNumber]);
        imageView.setLayoutX((SWIDTH - IWIDTH) / 2);
        imageView.setLayoutY(25);
        imageView.setFitWidth(IWIDTH);
        imageView.setFitHeight(IHEIGHT);
        pageNumberDisplay.setLayoutX(620);
        pageNumberDisplay.setLayoutY(520);
        pageNumberDisplay.setFont(new Font("Helvetica", 24));
        pageNumberDisplay.setTextFill(Color.web("#ff0000", 0.8));
        pageNumberDisplay.setText(currentImageNumber+1 + "/" + HowToPlayImgNum);
        root.getChildren().addAll(imageView,pageNumberDisplay);
    }

    /**
     * A method that initializes image array to be displayed in the scene
     */
    private void initImageArray(){
        for(int i = 1; i <= HowToPlayImgNum; i++) {
            imageArray[i-1] = new Image(getClass().getResource("/LearnToConquer/" + i + ".png").toExternalForm());
        }
    }

    /**
     * @return Scene returns the scene object for display
     */
    @Override
    public Scene getScene() {
        return scene;
    }


    /**
     * A method for adding buttons to the scene
     * @param root
     */
    public void addButtons(Group root){

        addTransitionButton(root, "", 495, 561, 294, 51, getClass().getResource("/Menu/Back.png").toExternalForm(), new MainMenu());
        addButtons(root, "", 0 , 400 , 51 , 51, getClass().getResource("/Menu/Back.png").toExternalForm(), event ->{
            currentImageNumber = (currentImageNumber - 1 )% HowToPlayImgNum;
            if (currentImageNumber < 0) {currentImageNumber += HowToPlayImgNum; }
            imageView.setImage(imageArray[currentImageNumber]);
            pageNumberDisplay.setText(currentImageNumber+1 + "/" + HowToPlayImgNum);
        } );
        addButtons(root, "", SWIDTH-51 , 400 , 51 , 51, getClass().getResource("/Menu/Back.png").toExternalForm(), event ->{
            currentImageNumber = (currentImageNumber + 1 )% HowToPlayImgNum;
            imageView.setImage(imageArray[currentImageNumber]);
            pageNumberDisplay.setText(currentImageNumber+1 + "/" + HowToPlayImgNum);
        } );
    }



}




