package android.example.mocar.Bengkel_Motor;

public class LayananBengkelMotorItem {
    private String id;
    private String jenisLayanan;
    private String deskripsiLayanan;
    private String harga;
    private String imageURL;
    private String pemesanan;

    public String getId() {
        return id;
    }

    public String getJenisLayanan() {
        return jenisLayanan;
    }

    public String getDeskripsiLayanan() {
        return deskripsiLayanan;
    }

    public String getHarga() {
        return harga;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getPemesanan() {
        return pemesanan;
    }

    public LayananBengkelMotorItem(String id, String jenisLayanan, String deskripsiLayanan, String harga, String imageURL, String pemesanan) {
        this.id = id;
        this.jenisLayanan = jenisLayanan;
        this.deskripsiLayanan = deskripsiLayanan;
        this.harga = harga;
        this.imageURL = imageURL;
        this.pemesanan = pemesanan;
    }


}
