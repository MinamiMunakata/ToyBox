import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    public void start(Stage primaryStage) throws Exception{
        // first scene
        primaryStage.setTitle("Toy Box");
        primaryStage.setResizable(false);
        BorderPane borderPane = new BorderPane();
        HBox hBoxAtTop = new HBox();
        hBoxAtTop.setPadding(new Insets(15,12,15,12));
        hBoxAtTop.setSpacing(10);
        hBoxAtTop.setStyle("-fx-background-color: #c46ab5");
//        hBoxAtTop.setStyle("-fx-background-color: #F1DC21");
        borderPane.setTop(hBoxAtTop);


//        Button btn_changeIcon = new Button("Change an icon");
        FileInputStream inputStream = new FileInputStream("src/Image/unicorn.jpg");
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);
        HBox image_HBox = new HBox(imageView);
        final TextField txt_playerName = new TextField();
        txt_playerName.setPromptText("Enter your first name.");
        txt_playerName.setPrefColumnCount(10);


        final ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton rb_BB = new RadioButton("Brick Breaker");
        rb_BB.setToggleGroup(toggleGroup);
        RadioButton rb_TTT = new RadioButton("Tic Tac Toe");
        rb_TTT.setToggleGroup(toggleGroup);

        HBox rb_HBox = new HBox(5);
        rb_HBox.getChildren().addAll(rb_BB,rb_TTT);

        VBox vBox = new VBox(5);
        vBox.setStyle("-fx-background-color: #F4DF29");
        vBox.getChildren().addAll(txt_playerName,imageView,rb_HBox);
        borderPane.setCenter(vBox);


        StackPane stackPane = new StackPane();
        Image icon = new Image(new FileInputStream("src/Image/clock.png"));
        ImageView iconView = new ImageView(icon);
        stackPane.getChildren().add(iconView);
        hBoxAtTop.getChildren().add(stackPane);




        Button btn_GoToBB = new Button("Brick Breaker");
        Button btn_GoToTTT = new Button("Tic Tac Toe");
        Button btn_GoToHistory = new Button("History");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10,10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
//        gridPane.add(btn_changeIcon, 0, 0, 2, 1);
        gridPane.add(image_HBox, 0, 6, 2, 4);
        gridPane.add(txt_playerName, 0, 2, 2, 1);
        gridPane.add(btn_GoToBB, 0, 2, 1, 1);
        gridPane.add(btn_GoToTTT,1,3,1,1);
        gridPane.add(btn_GoToHistory,0,4,1,1);
        gridPane.add(rb_HBox,0,5,1,1);

        Scene firstScene = new Scene(gridPane, 400,500);
        primaryStage.setScene(firstScene);

        // Brick Breaker scene
        btn_GoToBB.setOnAction(event -> {
            DateFormat dateFormat= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            final TextField txt_socre = new TextField();
            BBController brickBreakerCtrl = new BBController(txt_playerName.getText(), dateFormat.format(date));   // BBController instance
            if (txt_playerName.getText().equals("")){
                txt_playerName.setText("Player");
            }
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
            System.out.println(txt_playerName.getText());
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
