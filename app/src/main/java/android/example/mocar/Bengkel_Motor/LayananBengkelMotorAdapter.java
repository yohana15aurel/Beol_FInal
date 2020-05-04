package android.example.mocar.Bengkel_Motor;

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

public class LayananBengkelMotorAdapter extends RecyclerView.Adapter<LayananBengkelMotorAdapter.LayananBengkelMotorItemViewHolder> {
    private List<LayananBengkelMotorItem> layananBengkelMotorItems;
    Context c;
    private TextView mPemesanan;

    @NonNull
    @Override
    public LayananBengkelMotorItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LayananBengkelMotorItemViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layanan_bengkel_motor_list,parent,false)

        );
    }
    public LayananBengkelMotorAdapter(ArrayList<LayananBengkelMotorItem> layananBengkelMotorItems, Context context) {
        this.layananBengkelMotorItems = layananBengkelMotorItems;
        this.c = context;

    }


    @Override
    public void onBindViewHolder(@NonNull final LayananBengkelMotorItemViewHolder holder, int position) {
        holder.bind(layananBengkelMotorItems.get(position));
    }

    @Override
    public int getItemCount() {
        return layananBengkelMotorItems.size();
    }

    class LayananBengkelMotorItemViewHolder extends RecyclerView.ViewHolder  {
        private TextView textTitle1, Description1, harga1, pemesanan;
        private RelativeLayout rl1;
        ImageView image1;
        private ItemClickListener mitemClickListener;

        public LayananBengkelMotorItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle1 = itemView.findViewById(R.id.textJudulLayanan);
            Description1 = itemView.findViewById(R.id.textDeskripsiLayanan);
            harga1 = itemView.findViewById(R.id.textHarga);
            image1 = itemView.findViewById(R.id.imageLayanan);
            pemesanan = itemView.findViewById(R.id.pesanan);
        }
        private void bind(final LayananBengkelMotorItem layananBengkelMotorItem){
            textTitle1.setText(layananBengkelMotorItem.getJenisLayanan());
            Description1.setText(layananBengkelMotorItem.getDeskripsiLayanan());
            harga1.setText(layananBengkelMotorItem.getHarga());
            pemesanan.setText(layananBengkelMotorItem.getPemesanan());
            new LayananBengkelMotorAdapter.LayananBengkelMotorItemViewHolder.DownloadImageTask(image1).execute(layananBengkelMotorItem.getImageURL());

            pemesanan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( layananBengkelMotorItem.getPemesanan().indexOf("6")==0) {
                        String message1 = "Jenis Kendaraan :";
                        String message2 = "Lokasi :";
                        String message3 = "Saya ingin memesan layanan";

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s&text=%s",
                                layananBengkelMotorItem.getPemesanan(), message3 + " " + layananBengkelMotorItem.getJenisLayanan() +"\n"+ "\n" + message1 + "\n" + message2 + "\n")));
                        c.startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+layananBengkelMotorItem.getPemesanan()));
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
