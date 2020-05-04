package android.example.mocar.Bengkel_Motor;

public class BengkelMotorItem {
    private String id1;
    private String title1;
    private String description1;
    private String kontak1;
    private String Wa1;
    private String imageURL1;

    public BengkelMotorItem(String id1, String title1, String description1, String kontak1, String wa1, String imageURL1) {
        this.id1 = id1;
        this.title1 = title1;
        this.description1 = description1;
        this.kontak1 = kontak1;
        this.Wa1 = wa1;
        this.imageURL1 = imageURL1;
    }

    public String getId1() {
        return id1;
    }

    public void setId(String id1) {
        this.id1 = id1;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle(String title) {
        this.title1 = title1;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription(String description1) {
        this.description1 = description1;
    }

    public String getKontak1() {
        return kontak1;
    }

    public void setKontak(String kontak1) {
        this.kontak1 = kontak1;
    }

    public String getWa1() {
        return Wa1;
    }

    public void setWa(String wa1) {
        Wa1 = wa1;
    }

    public String getImageURL1() {
        return imageURL1;
    }

    public void setImageURL(String imageURL1) {
        this.imageURL1 = imageURL1;
    }
}
