import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.*;
import java.util.function.ToDoubleBiFunction;

public class TTTController extends TimerTask implements EventHandler<ActionEvent>, Initializable {

    @FXML
    private Label player;
    @FXML
    private Button button0;
    @FXML
    private Button button2;
    @FXML
    private Button button1;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;

    @FXML
    private ImageView yourImageView, comImage;
    private final int BUTTONSIZE = 80;

    @FXML
    private List<Button> buttons = new ArrayList<Button>();

    private String url;
    private String comIconURL;
    private String playerName;
    private String date;

    String youIcon = "cow.png";
    String comIcon = "lion.jpg";
    List<String> urlArr = new ArrayList<>();

    int clickCount = 0;

    public TTTController(String playerName, String date) {

        this.playerName = playerName;
        this.date = date;
    }

    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
        System.out.println(new Date());
    }

    /**
     * Invoked when a specific event of the type for which this handler is
     * registered happens.
     *
     * @param event the event which occurred
     */
    @FXML
    @Override
    public void handle(ActionEvent event) {

        // playerImage's URL
        Image playerImage = new LocatedImage(youIcon);
        url = playerImage instanceof LocatedImage ?
                ((LocatedImage) playerImage).getURL() : null;
        ImageView yourImageView = new ImageView(playerImage);

        // Image's size
        yourImageView.setFitHeight(BUTTONSIZE);
        yourImageView.setFitWidth(BUTTONSIZE);

        // A button Clicked
        Button button = (Button) event.getSource();
        for (int i = 0; i < buttons.size(); i++) {
            if (button.getId().equals(buttons.get(i).getId())) {
                System.out.println("HEYYYYYYY");
                urlArr.set(i, url);
                clickCount++;
                System.out.println(clickCount);
            }
        }

        if (!button.isDisable()) {
            button.setGraphic(yourImageView);
            button.setDisable(true);
            checkStatus();
        }
        System.out.println(urlArr.toString());

        if (!isWin(url)){
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(
                            () -> {
                                // commputerImage's URL
                                Image comImage = new LocatedImage(comIcon);
                                comIconURL = comImage instanceof  LocatedImage ?
                                        ((LocatedImage) comImage).getURL() : null;
                                ImageView comImageView = new ImageView(comImage);

                                comImageView.setFitWidth(BUTTONSIZE);
                                comImageView.setFitHeight(BUTTONSIZE);


                                // Computer turn.
                                Random random = new Random();
//                            boolean check = true;

                                if(clickCount < 5) {
//                                check = false;
//                                checkStatus();

                                    while (true){

                                        int randomIndex = random.nextInt(buttons.size());
                                        Button randomElement = buttons.get(randomIndex);
                                        System.out.println(randomElement);
                                        System.out.println("randomElemtn1: " + randomElement);
                                        System.out.println("NOOOOOO");

                                        if (!randomElement.isDisable()){
                                            randomElement.setGraphic(comImageView);
                                            System.out.println(comImageView);
                                            randomElement.setDisable(true);
                                            urlArr.set(randomIndex, comIconURL);
                                            System.out.println("HEYYYYYYY");
                                            checkStatus();
                                            //check = false;
                                            break;
                                        }
                                    }
                                }

                                System.out.println(urlArr.toString());
                            }
                    );
                }
            };

            Timer timer = new Timer();
            timer.schedule(task, 1000L);
        }
    }


    public void checkStatus() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(
                        () -> {

                            if (isWin(url))
                            {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("YOU WIN");
                                alert.setHeaderText("Congratulation!");
                                alert.setContentText("you wanna try again?");

                                alertButton(alert);



                            } else if (isWin(comIconURL)) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("COMPUTER WIN");
                                alert.setHeaderText("Ooops! You lose..");
                                alert.setContentText("you wanna try again?");

                                alertButton(alert);

                            } else if (clickCount == 5){
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("TIE");
                                alert.setHeaderText("Tie!");
                                alert.setContentText("you wanna try again?");

                                alertButton(alert);

                            }
                        });
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 800L);


    }

    private boolean isWin(String url) {
        return urlArr.get(0).equals(url) && urlArr.get(1).equals(url) && urlArr.get(2).equals(url) ||
                urlArr.get(3).equals(url) && urlArr.get(4).equals(url) && urlArr.get(5).equals(url) ||
                urlArr.get(6).equals(url) && urlArr.get(7).equals(url) && urlArr.get(8).equals(url) ||
                urlArr.get(0).equals(url) && urlArr.get(3).equals(url) && urlArr.get(6).equals(url) ||
                urlArr.get(1).equals(url) && urlArr.get(4).equals(url) && urlArr.get(7).equals(url) ||
                urlArr.get(2).equals(url) && urlArr.get(5).equals(url) && urlArr.get(8).equals(url) ||
                urlArr.get(0).equals(url) && urlArr.get(4).equals(url) && urlArr.get(8).equals(url) ||
                urlArr.get(2).equals(url) && urlArr.get(4).equals(url) && urlArr.get(6).equals(url);
    }


    private void alertButton(Alert alert) {
        ButtonType button1 = new ButtonType("One More!");
        ButtonType button2 = new ButtonType("Exit");

        alert.getButtonTypes().setAll(button2, button1);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == button1){
            // TODO figured out how to restart the game.

            for(Button btn: buttons) {
                btn.setGraphic(null);
                btn.setDisable(false);
            }

            for (int i = 0; i < 9; i++) {
                urlArr.set(i, String.valueOf(i));
            }
            clickCount = 0;
        }
        else {
            System.exit(0); //0 == don't delete
        }
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image image = new Image(youIcon);
        yourImageView.setImage(image);

        Image image1 = new Image(comIcon);
        comImage.setImage(image1);

        buttons.add(button0);
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);
        buttons.add(button7);
        buttons.add(button8);

        for (int i = 0; i < 9; i++) {
            urlArr.add(String.valueOf(i));

        }
        System.out.println("HEY");
        player.setText(playerName);
        System.out.println(playerName);

    }

}
