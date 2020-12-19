package screens;


import game.Game;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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


    private void initImageArray(){
        for(int i = 1; i <= HowToPlayImgNum; i++) {
            imageArray[i-1] = new Image(getClass().getResource("/LearnToConquer/" + i + ".png").toExternalForm());
        }
    }


    public Scene getScene() {
        Group root = new Group();
        Scene scene = new Scene(root, SWIDTH, SHEIGHT, Color.BLACK);
        setBackground(root, StorageManager.RESOURCES_FOLDER_NAME + "Menu\\Background.png");
        addButtons(root);
        initImageArray();
        imageView = new ImageView(imageArray[currentImageNumber]);
        imageView.setLayoutX((SWIDTH - IWIDTH) / 2);
        imageView.setLayoutY(25);
        imageView.setFitWidth(IWIDTH);
        imageView.setFitHeight(IHEIGHT);
        root.getChildren().add(imageView);
        return scene;
    }



    public void addButtons(Group root){

        addTransitionButton(root, "", 495, 561, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "\\Menu\\Back.png", new MainMenu());
        addButtons(root, "", 0 , 400 , 51 , 51, StorageManager.RESOURCES_FOLDER_NAME + "\\Menu\\Back.png", event ->{
            currentImageNumber = (currentImageNumber - 1 )% HowToPlayImgNum;
            if (currentImageNumber < 0) {currentImageNumber += HowToPlayImgNum; }
            imageView.setImage(imageArray[currentImageNumber]);
        } );
        addButtons(root, "", SWIDTH-51 , 400 , 51 , 51, StorageManager.RESOURCES_FOLDER_NAME + "\\Menu\\Back.png", event ->{
            currentImageNumber = (currentImageNumber + 1 )% HowToPlayImgNum;
            imageView.setImage(imageArray[currentImageNumber]);
        } );
    }



}




