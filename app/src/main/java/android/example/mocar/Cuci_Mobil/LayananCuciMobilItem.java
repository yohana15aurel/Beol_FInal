package android.example.mocar.Cuci_Mobil;

public class LayananCuciMobilItem {
    private String id;
    private String jenisLayanan;
    private String deskripsiLayanan;
    private String harga;
    private String imageURL;
    private String pemesanan;

    public String getId2() {
        return id;
    }

    public String getJenisLayanan2() {
        return jenisLayanan;
    }

    public String getDeskripsiLayanan2() {
        return deskripsiLayanan;
    }

    public String getHarga2() {
        return harga;
    }

    public String getImageURL2() {
        return imageURL;
    }

    public String getPemesanan2() {
        return pemesanan;
    }

    public LayananCuciMobilItem(String id, String jenisLayanan, String deskripsiLayanan, String harga, String imageURL, String pemesanan) {
        this.id = id;
        this.jenisLayanan = jenisLayanan;
        this.deskripsiLayanan = deskripsiLayanan;
        this.harga = harga;
        this.imageURL = imageURL;
        this.pemesanan = pemesanan;
    }


}
