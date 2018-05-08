import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.*;

public class TTTController extends TimerTask implements EventHandler<ActionEvent>, Initializable {

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
    private ImageView yourImageView,comImage;

    private final int BUTTONSIZE = 80;

    @FXML
    private List<Button> buttons = new ArrayList<Button>();

    String url;
    String url1;

    String youIcon = "cow.png";
    String comIcon = "lion.jpg";
    List<String> urlArr = new ArrayList<>();

    int clickCount = 0;
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
    @Override
    public void handle(ActionEvent event) {

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


    }
}
