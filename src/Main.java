import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/Image/unicorn.jpg");
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        // first scene
        primaryStage.setTitle("Toy Box");
        primaryStage.setResizable(false);
        Button btn_changeIcon = new Button("Change an icon");
//        FileInputStream inputStream = new FileInputStream("Image/unicorn.jpg");
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        final TextField txt_playerName = new TextField();
        txt_playerName.setPromptText("Enter your first name.");
        txt_playerName.setPrefColumnCount(10);
        Button btn_GoToBB = new Button("Brick Breaker");
        Button btn_GoToTTT = new Button("Tic Tac Toe");
        Button btn_GoToHistory = new Button("History");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10,10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.add(btn_changeIcon, 0, 0, 2, 1);
        gridPane.add(imageView, 0, 1, 2, 4);
        gridPane.add(txt_playerName, 0, 2, 2, 1);
        gridPane.add(btn_GoToBB, 0, 2, 1, 1);
        gridPane.add(btn_GoToTTT,1,3,1,1);
        gridPane.add(btn_GoToHistory,0,4,1,1);

        Scene firstScene = new Scene(gridPane);
        primaryStage.setScene(firstScene);

        // Brick Breaker scene
        btn_GoToBB.setOnAction(event -> {
            DateFormat dateFormat= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            BBController brickBreakerCtrl = new BBController(txt_playerName.getText(), dateFormat.format(date));   // BBController instance
            Scene brickBreakerScreen = new Scene(brickBreakerCtrl.getGroup(), brickBreakerCtrl.getWIDTH(), brickBreakerCtrl.getHEIGHT(), Color.WHITE);
            brickBreakerCtrl.setEventHandler(brickBreakerScreen);
            brickBreakerCtrl.gamePlay();
            primaryStage.setScene(brickBreakerScreen);
            primaryStage.setTitle("Brick Breaker");
            setCenter(primaryStage);
        });

        // TIc Tac Toe scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tic_tac_toe.fxml"));
        btn_GoToTTT.setOnAction(event -> {
            DateFormat dateFormat= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            TTTController ticTacToeCtrl = new TTTController(txt_playerName.getText(),dateFormat.format(date));
            loader.setController(ticTacToeCtrl);
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene ticTacToeScreen = new Scene(root, 500, 500);
            ticTacToeScreen.getStylesheets().add("Styles/style.css");
            primaryStage.setScene(ticTacToeScreen);
            setCenter(primaryStage);
        });

        // History scene
        btn_GoToHistory.setOnAction(event -> {
            GameHistory history = new GameHistory();
            Connection connection = history.connect();
            history.readHistory(connection);

            Group group = history.createGameHistoryTable();
            Scene historyScreen = new Scene(group);
            primaryStage.setTitle("Toy Box");
            primaryStage.setWidth(450);
            primaryStage.setHeight(550);

            primaryStage.setScene(historyScreen);
            setCenter(primaryStage);

            GameHistory.closeConnection(connection);

        });
        primaryStage.show();
    }

    private void setCenter(Stage primaryStage) {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(
                (primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY(
                (primScreenBounds.getHeight() - primaryStage.getHeight()) / 4);
    }


    public static void main(String[] args) {
        launch(args);
    }

}
