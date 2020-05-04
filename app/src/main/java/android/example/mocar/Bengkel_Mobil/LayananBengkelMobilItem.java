package android.example.mocar.Bengkel_Mobil;

public class LayananBengkelMobilItem {
    private String id;
    private String jenisLayanan;
    private String deskripsiLayanan;
    private String harga;
    private String imageURL;
    private String pemesanan;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public LayananBengkelMobilItem(String id, String jenisLayanan, String deskripsiLayanan, String harga, String imageURL, String pemesanan) {
        this.id = id;
        this.jenisLayanan = jenisLayanan;
        this.deskripsiLayanan = deskripsiLayanan;
        this.harga = harga;
        this.imageURL = imageURL;
        this.pemesanan = pemesanan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJenisLayanan() {
        return jenisLayanan;
    }

    public void setJenisLayanan(String jenisLayana) {
        this.jenisLayanan = jenisLayana;
    }

    public String getDeskripsiLayanan() {
        return deskripsiLayanan;
    }

    public void setDeskripsiLayanan(String deskripsiLayanan) {
        this.deskripsiLayanan = deskripsiLayanan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getPemesanan() {return pemesanan;}


}
