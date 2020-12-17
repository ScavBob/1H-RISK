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
        imageArray[1] =  new Image(getClass().getResource("/PregameMenu/BilkentMap.png").toExternalForm());
        imageArray[2] = new Image(getClass().getResource("/PregameMenu/ChooseMap.png").toExternalForm());
        imageArray[3] = new Image(getClass().getResource("/PregameMenu/WorldButton.png").toExternalForm());
        imageArray[4] = new Image(getClass().getResource("/PregameMenu/WorldMap.png").toExternalForm());
    }


    public Scene getScene() {
        Group root = new Group();
        BorderPane bPane = new BorderPane();
        Scene scene = new Scene(bPane, 1280, 720);
        setBackground(root, StorageManager.RESOURCES_FOLDER_NAME + "Menu\\Background.png");
        addButtons(root);
        initImageArray();
        imageView = new ImageView(imageArray[currentImageNumber]);
        imageView.setLayoutX(294.0);
        imageView.setLayoutY(200.0);
        imageView.setFitHeight(600);
        imageView.setFitWidth(600);
        root.getChildren().add(imageView);
        bPane.setCenter(root);
        return scene;
    }



    public void addButtons(Group root){

        addTransitionButton(root, "", 0, 20, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "\\Menu\\Back.png", new MainMenu());
        addButtons(root, "Hey", 0 , 400 , 51 , 51, StorageManager.RESOURCES_FOLDER_NAME + "\\Menu\\Back.png", event ->{
            currentImageNumber = (currentImageNumber - 1 )% HowToPlayImgNum;
            if (currentImageNumber < 0) {currentImageNumber += HowToPlayImgNum; }
            imageView.setImage(imageArray[currentImageNumber]);
            System.out.println("Hey");
        } );
        addButtons(root, "Hey", 1280-51 , 400 , 51 , 51, StorageManager.RESOURCES_FOLDER_NAME + "\\Menu\\Back.png", event ->{
            currentImageNumber = (currentImageNumber + 1 )% HowToPlayImgNum;
            imageView.setImage(imageArray[currentImageNumber]);
            System.out.println("Hey");
        } );
    }



}




