import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.sql.*;
import static javafx.collections.FXCollections.*;

public class GameHistory {
    private final Group group = new Group();
    private final TableView<Player> table = new TableView<>();
    private final ObservableList<Player> data = observableArrayList();

    public Group createGameHistoryTable() {
        final Label label = new Label("Game History");
        label.setFont(new Font("Arial", 20));

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(90);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Player, String>("name"));
        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setMinWidth(90);
        scoreCol.setCellValueFactory(
                new PropertyValueFactory<Player, String>("score"));
        TableColumn dateCol = new TableColumn("Date");
        dateCol.setMinWidth(180);
        dateCol.setCellValueFactory(
                new PropertyValueFactory<Player, String>("date"));
        table.setItems(data);
        table.getColumns().addAll(nameCol,scoreCol,dateCol);
        nameCol.setStyle("-fx-alignment: center");
        scoreCol.setStyle("-fx-alignment: center");

        dateCol.setStyle("-fx-alignment: center");

        final VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(20,20,0,20));
        vBox.getChildren().addAll(label,table);
        group.getChildren().addAll(vBox);
        return group;
    }

    // database
    public Connection connect() {
        Connection connection = null;
        connection = getConnection(connection);
        return connection;
    }

    public static void addPlayRecord(String name, String score, String date){
        Connection connection = null;
        connection = getConnection(connection);
        String query = "INSERT INTO gameHistory(name, score, date) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, score);
            preparedStatement.setString(3, date);
            preparedStatement.executeUpdate();
            System.out.println("Successfully added a new record");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection(connection);
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection is closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection(Connection connection) {
        try {
//            String url = "jdbc:sqlite:src/toybox.db";
            String url = "jdbc:sqlite::resource:toybox.db";
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite toybox.db has been established.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void readHistory(Connection connection) {
        String query = "SELECT * FROM gameHistory";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                data.add(new Player(
                        resultSet.getString("name"),
                        resultSet.getString("score"),
                        resultSet.getString("date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
