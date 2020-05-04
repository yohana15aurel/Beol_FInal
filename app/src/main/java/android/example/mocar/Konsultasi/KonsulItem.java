package android.example.mocar.Konsultasi;

public class KonsulItem {
    private String title;
    private String content;
    private String id;
    private String imageURL;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public KonsulItem(String id, String title, String content, String imageURL) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imageURL = imageURL;
    }

}
