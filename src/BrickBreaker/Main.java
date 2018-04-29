package BrickBreaker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // first scene
        primaryStage.setTitle("Toy Box");
        Button btn = new Button("Brick Breaker");
        StackPane pane = new StackPane();
        pane.getChildren().add(btn);
        Scene firstScene = new Scene(pane,710,600);
        primaryStage.setScene(firstScene);
        // Controller
        Controller controller = new Controller();
        // Brick Breaker
        BorderPane root = new BorderPane();

        root.setCenter(controller.getGroup());
        // second scene
        Scene brickBreakerScreen = new Scene(root, 710, 600, Color.WHITE);
//        Canvas canvas = new Canvas(710, 600);
//        GraphicsContext g = canvas.getGraphicsContext2D();
//        Controller c = new Controller();
//        c.draw(g);
//        g.setFill(Color.rgb(2,0,86));
//        g.fillRect(310, 550, 100,8);
//        root.getChildren().add(canvas);
        btn.setOnAction(event -> {primaryStage.setScene(brickBreakerScreen);});

        controller.setEventHandler(brickBreakerScreen);
        primaryStage.show();
        controller.gamePlay();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
