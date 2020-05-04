package android.example.mocar.Cuci_Mobil;

import android.content.Context;
import android.content.Intent;
import android.example.mocar.Bengkel_Mobil.LayananBengkelMobilAdapter;
import android.example.mocar.Bengkel_Mobil.LayananBengkelMobilItem;
import android.example.mocar.Interface.ItemClickListener;
import android.example.mocar.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LayananCuciMobilAdapter extends RecyclerView.Adapter<LayananCuciMobilAdapter.LayananCuciMobilItemViewHolder> {
    private List<LayananCuciMobilItem> layananCuciMobilItems;
    Context c;
    private TextView mPemesanan;

    @NonNull
    @Override
    public LayananCuciMobilItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LayananCuciMobilItemViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layanan_cuci_mobil_list,parent,false)

        );
    }


    public LayananCuciMobilAdapter(ArrayList<LayananCuciMobilItem> layananCuciMobilItems, Context context) {
        this.layananCuciMobilItems = layananCuciMobilItems;
        this.c = context;

    }

    @Override
    public void onBindViewHolder(@NonNull final LayananCuciMobilItemViewHolder holder, int position) {
        holder.bind(layananCuciMobilItems.get(position));
    }

    @Override
    public int getItemCount() {
        return layananCuciMobilItems.size();
    }

    class LayananCuciMobilItemViewHolder extends RecyclerView.ViewHolder {
        private TextView TitleLayanan, DeskripsiLayanan, HargaLayanan, pemesanan;
        private RelativeLayout rl2;
        ImageView image1;
        private ItemClickListener mitemClickListener;

        public LayananCuciMobilItemViewHolder(@NonNull View itemView) {
            super(itemView);
            TitleLayanan = itemView.findViewById(R.id.LayananCuciMobil);
            DeskripsiLayanan = itemView.findViewById(R.id.DeskripsiLayananCuciMobil);
            HargaLayanan = itemView.findViewById(R.id.HargaLayananCuciMobil);
            image1 = itemView.findViewById(R.id.imageLayanan);
            pemesanan = itemView.findViewById(R.id.pesanan);
        }

        private void bind(final LayananCuciMobilItem layananCuciMobilItem){
            TitleLayanan.setText(layananCuciMobilItem.getJenisLayanan2());
            DeskripsiLayanan.setText(layananCuciMobilItem.getDeskripsiLayanan2());
            HargaLayanan.setText(layananCuciMobilItem.getHarga2());
            pemesanan.setText(layananCuciMobilItem.getPemesanan2());
            new LayananCuciMobilAdapter.LayananCuciMobilItemViewHolder.DownloadImageTask(image1).execute(layananCuciMobilItem.getImageURL2());

            pemesanan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( layananCuciMobilItem.getPemesanan2().indexOf("6")==0) {
                        String message1 = "Jenis Kendaraan :";
                        String message2 = "Lokasi :";
                        String message3 = "Saya ingin memesan layanan";

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s&text=%s",
                                layananCuciMobilItem.getPemesanan2(), message3 + " " + layananCuciMobilItem.getJenisLayanan2() +"\n"+ "\n" + message1 + "\n" + message2 + "\n")));
                        c.startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+layananCuciMobilItem.getPemesanan2()));
                        c.startActivity(intent);
                    }
                }
            });
        }

        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;
            public DownloadImageTask(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                bmImage.setImageBitmap(result);
            }
        }

    }



}
