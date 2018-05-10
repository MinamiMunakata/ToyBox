import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
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
        Text text = new Text();
        text.setText("TOY BOX");
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Comic Sans MS",FontWeight.BOLD,40));
        text.setTextAlignment(TextAlignment.CENTER);
        Pane spacer = new Pane();
        hBoxAtTop.setHgrow(spacer,Priority.ALWAYS);
        spacer.setMinSize(10,1);
        Image icon = new Image(new FileInputStream("src/Image/clock.png"));
        Button btn_GoToHistory = new Button();
        btn_GoToHistory.setGraphic(new ImageView(icon));
        btn_GoToHistory.setStyle("-fx-background-color: #c46ab5");
        btn_GoToHistory.setMinSize(Button.USE_PREF_SIZE,Button.USE_PREF_SIZE);
        btn_GoToHistory.setAlignment(Pos.CENTER);
        hBoxAtTop.getChildren().addAll(text,spacer,btn_GoToHistory);
        borderPane.setTop(hBoxAtTop);

        final TextField txt_playerName = new TextField();
        txt_playerName.setPromptText("Enter your first name.");
        txt_playerName.setPrefColumnCount(10);
        txt_playerName.setMinWidth(200);
        txt_playerName.setMaxWidth(250);
        txt_playerName.setFont(Font.font(15));
        FileInputStream inputStream = new FileInputStream("src/Image/unicorn.jpg");
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);
        final ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton rb_BB = new RadioButton("Brick Breaker");
        rb_BB.setToggleGroup(toggleGroup);
        rb_BB.setFont(Font.font(14));
        RadioButton rb_TTT = new RadioButton("Tic Tac Toe");
        rb_TTT.setFont(Font.font(14));
        rb_TTT.setToggleGroup(toggleGroup);

        HBox rb_HBox = new HBox(20);
        rb_HBox.setAlignment(Pos.CENTER);
        rb_HBox.getChildren().addAll(rb_BB,rb_TTT);

        Button btn_play = new Button("P L A Y !");
        btn_play.setFont(Font.font("Comic Sans MS",15));
        StackPane pane = new StackPane(btn_play);
        pane.setPadding(new Insets(20,0,0,0));

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(30,40,60,40));
        vBox.setStyle("-fx-background-color: #F4DF29");
        vBox.getChildren().addAll(txt_playerName,imageView,rb_HBox,pane);
        vBox.setAlignment(Pos.CENTER);
        borderPane.setCenter(vBox);

        Scene firstScene = new Scene(borderPane, 400,550);
        primaryStage.setScene(firstScene);

        // when play! button is clicked
        btn_play.setOnAction(event -> {
            DateFormat dateFormat= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            if (toggleGroup.getSelectedToggle() != null){
                if (rb_BB.isSelected()){
                    BBController brickBreakerCtrl = new BBController(txt_playerName.getText(), dateFormat.format(date));   // BBController instance
                    Scene brickBreakerScreen = new Scene(brickBreakerCtrl.getGroup(), brickBreakerCtrl.getWIDTH(), brickBreakerCtrl.getHEIGHT(), Color.WHITE);
                    brickBreakerCtrl.setEventHandler(brickBreakerScreen);
                    brickBreakerCtrl.gamePlay();
                    primaryStage.setScene(brickBreakerScreen);
                    primaryStage.setTitle("Brick Breaker");
                    setCenter(primaryStage);
                } else if(rb_TTT.isSelected()){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("tic_tac_toe.fxml"));
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
                }
            }
        });

        // History scene
        btn_GoToHistory.setOnAction(event -> {
            GameHistory history = new GameHistory();
            Connection connection = history.connect();
            history.readHistory(connection);

            Group group = history.createGameHistoryTable();
            Scene historyScreen = new Scene(group, 400,550);
            primaryStage.setTitle("Toy Box");
//            primaryStage.setWidth(400);
//            primaryStage.setHeight(550);

            primaryStage.setScene(historyScreen);
//            setCenter(primaryStage);

            GameHistory.closeConnection(connection);

        });
        btn_GoToHistory.setOnMouseEntered(
                event -> btn_GoToHistory.setStyle("-fx-background-color: #d99ff4")
        );
        btn_GoToHistory.setOnMouseExited(
                event -> btn_GoToHistory.setStyle("-fx-background-color: #c46ab5")
        );
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
