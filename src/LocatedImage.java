
import javafx.scene.image.Image;

public class LocatedImage extends Image {
    private final String url;
    private String url1;


    public LocatedImage(String url) {
        super(url);
        this.url = url;
    }



    public String getURL() {
        return url;

    }

    public String getUrl1() {
        return url1;
    }
}
