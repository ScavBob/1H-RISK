package screens;


import game.Game;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import managers.StorageManager;
import javafx.scene.image.Image;


public class HowToPlayScreen extends Menu {


    private final int HowToPlayImgNum = 5;
    Image[] imageArray = new Image[HowToPlayImgNum];
    int currentImageNumber = 0;
    ImageView imageView;


    private void initImageArray(){
        imageArray[0] = new Image(getClass().getResource("/PregameMenu/BilkentButton.png").toExternalForm());
        imageArray[1] =  new Image(getClass().getResource("/PregameMenu/Bilkent.png").toExternalForm());
        imageArray[2] = new Image(getClass().getResource("/PregameMenu/ChooseMap.png").toExternalForm());
        imageArray[3] = new Image(getClass().getResource("/PregameMenu/WorldButton.png").toExternalForm());
        imageArray[4] = new Image(getClass().getResource("/PregameMenu/World.png").toExternalForm());
    }


    public Scene getScene() {
        Group root = new Group();
        BorderPane bPane = new BorderPane();
        Scene scene = new Scene(bPane, 1280, 720);
        setBackground(root, StorageManager.RESOURCES_FOLDER_NAME + "Menu\\Background.png");
        addButtons(root);
        initImageArray();
        imageView = new ImageView(imageArray[currentImageNumber]);
        imageView.setLayoutX(211);
        imageView.setLayoutY(25);
        imageView.setFitHeight(500);
        imageView.setFitWidth(794);
        root.getChildren().add(imageView);
        bPane.setCenter(root);
        return scene;
    }



    public void addButtons(Group root){

        addTransitionButton(root, "", 495, 561, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "\\Menu\\Back.png", new MainMenu());
        addButtons(root, "", 0 , 400 , 51 , 51, StorageManager.RESOURCES_FOLDER_NAME + "\\Menu\\Back.png", event ->{
            currentImageNumber = (currentImageNumber - 1 )% HowToPlayImgNum;
            if (currentImageNumber < 0) {currentImageNumber += HowToPlayImgNum; }
            imageView.setImage(imageArray[currentImageNumber]);
        } );
        addButtons(root, "", 1280-51 , 400 , 51 , 51, StorageManager.RESOURCES_FOLDER_NAME + "\\Menu\\Back.png", event ->{
            currentImageNumber = (currentImageNumber + 1 )% HowToPlayImgNum;
            imageView.setImage(imageArray[currentImageNumber]);
        } );
    }



}




