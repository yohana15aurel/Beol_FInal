package android.example.mocar.Bengkel_Mobil;

public class BengkelItem {
    private String id;
    private String title;
    private String description;
    private String kontak;
    private String Wa;
    private String imageURL;

    public BengkelItem(String id, String title, String description, String kontak, String Wa, String imageURL){
        this.id = id;
        this.title = title;
        this.description = description;
        this.kontak = kontak;
        this.Wa = Wa;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    public String getWa() {
        return Wa;
    }

    public void setWa(String wa) {
        Wa = wa;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


}
