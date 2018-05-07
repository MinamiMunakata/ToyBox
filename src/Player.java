import javafx.beans.property.SimpleStringProperty;

public class Player {
    private final SimpleStringProperty name;
    private final SimpleStringProperty score;
    private final SimpleStringProperty date;

    public Player(String name, String  score, String  date) {
        this.name = new SimpleStringProperty(name);
        this.score = new SimpleStringProperty(score);
        this.date = new SimpleStringProperty(date);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getScore() {
        return score.get();
    }

    public SimpleStringProperty scoreProperty() {
        return score;
    }

    public void setScore(String score) {
        this.score.set(score);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }
    //    private final SimpleStringProperty firstName;
//    private final SimpleStringProperty lastName;
//    private final SimpleStringProperty email;
//
//    public Player(String fName, String lName, String email) {
//        this.firstName = new SimpleStringProperty(fName);
//        this.lastName = new SimpleStringProperty(lName);
//        this.email = new SimpleStringProperty(email);
//    }
//    private String name;
//    private String score;
//    private String date;

//    public Player(String name, String date) {
////        this.id = id;
//        this.name = name != null ? name: "No name";
//        this.date = date;
//    }
//
//    public Player(String date) {
//        this.name = "None";
//        this.date = date;
//    }
//
//    public void setScore(String score) {
//        this.score = score;
//    }

}
