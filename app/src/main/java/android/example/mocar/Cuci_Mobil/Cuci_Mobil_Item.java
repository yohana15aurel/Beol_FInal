package android.example.mocar.Cuci_Mobil;

public class Cuci_Mobil_Item {
    private String id;
    private String title;
    private String description;
    private String kontak;
    private String Wa;

    public Cuci_Mobil_Item(String id, String title, String description, String kontak, String wa, String imageURL) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.kontak = kontak;
        Wa = wa;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getKontak() {
        return kontak;
    }

    public String getWa() {
        return Wa;
    }

    public String getImageURL() {
        return imageURL;
    }

    private String imageURL;
}
