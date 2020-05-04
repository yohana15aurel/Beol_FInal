package android.example.mocar.Bengkel_Mobil;

import android.content.Context;
import android.content.Intent;
import android.example.mocar.Bengkel_Motor.BengkelMotorItemAdapter;
import android.example.mocar.Interface.ItemClickListener;
import android.example.mocar.R;
import android.example.mocar.Review_Activity;
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

public class LayananBengkelMobilAdapter extends RecyclerView.Adapter<LayananBengkelMobilAdapter.LayananBengkelMobilItemViewHolder> {
    private List<LayananBengkelMobilItem> layananBengkelMobilItems;
    Context c;
    private TextView mPemesanan;

    @NonNull
    @Override
    public LayananBengkelMobilItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LayananBengkelMobilItemViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layanan_bengkel_mobil_list,parent,false)

        );
    }


    public LayananBengkelMobilAdapter(ArrayList<LayananBengkelMobilItem> layananBengkelMobilItems, Context context) {
        this.layananBengkelMobilItems = layananBengkelMobilItems;
        this.c = context;

    }


    @Override
    public void onBindViewHolder(@NonNull final LayananBengkelMobilItemViewHolder holder, int position) {
        holder.bind(layananBengkelMobilItems.get(position));
    }

    @Override
    public int getItemCount() {
        return layananBengkelMobilItems.size();
    }

    class LayananBengkelMobilItemViewHolder extends RecyclerView.ViewHolder  {
        private TextView textTitle1, Description1, harga1, pemesanan;
        private RelativeLayout rl1;
        ImageView image1;
        private ItemClickListener mitemClickListener;

        public LayananBengkelMobilItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle1 = itemView.findViewById(R.id.textJudulLayanan);
            Description1 = itemView.findViewById(R.id.textDeskripsiLayanan);
            harga1 = itemView.findViewById(R.id.textHarga);
            image1 = itemView.findViewById(R.id.imageLayanan);
            pemesanan = itemView.findViewById(R.id.pesanan);
        }
        private void bind(final LayananBengkelMobilItem layananBengkelMobilItem){
            textTitle1.setText(layananBengkelMobilItem.getJenisLayanan());
            Description1.setText(layananBengkelMobilItem.getDeskripsiLayanan());
            harga1.setText(layananBengkelMobilItem.getHarga());
            pemesanan.setText(layananBengkelMobilItem.getPemesanan());
            new DownloadImageTask(image1).execute(layananBengkelMobilItem.getImageURL());

            pemesanan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( layananBengkelMobilItem.getPemesanan().indexOf("6")==0) {
                        String message1 = "Jenis Kendaraan :";
                        String message2 = "Lokasi :";
                        String message3 = "Saya ingin memesan layanan";

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s&text=%s",
                                layananBengkelMobilItem.getPemesanan(), message3 + " " + layananBengkelMobilItem.getJenisLayanan() +"\n"+ "\n" + message1 + "\n" + message2 + "\n")));
                        c.startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+layananBengkelMobilItem.getPemesanan()));
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
