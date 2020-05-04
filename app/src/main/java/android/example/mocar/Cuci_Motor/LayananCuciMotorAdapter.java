package android.example.mocar.Cuci_Motor;

import android.content.Context;
import android.content.Intent;
import android.example.mocar.Cuci_Mobil.LayananCuciMobilAdapter;
import android.example.mocar.Cuci_Mobil.LayananCuciMobilItem;
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

public class LayananCuciMotorAdapter extends RecyclerView.Adapter<LayananCuciMotorAdapter.LayananCuciMotorItemViewHolder> {
    private List<LayananCuciMotorItem> layananCuciMotorItems;
    Context c;
    private TextView mPemesanan;

    @NonNull
    @Override
    public LayananCuciMotorAdapter.LayananCuciMotorItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LayananCuciMotorAdapter.LayananCuciMotorItemViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layanan_cuci_motor_list,parent,false)

        );
    }


    public LayananCuciMotorAdapter(ArrayList<LayananCuciMotorItem> layananCuciMotorItems, Context context) {
        this.layananCuciMotorItems = layananCuciMotorItems;
        this.c = context;

    }

    @Override
    public void onBindViewHolder(@NonNull final LayananCuciMotorAdapter.LayananCuciMotorItemViewHolder holder, int position) {
        holder.bind(layananCuciMotorItems.get(position));
    }

    @Override
    public int getItemCount() {
        return layananCuciMotorItems.size();
    }

    class LayananCuciMotorItemViewHolder extends RecyclerView.ViewHolder {
        private TextView TitleLayanan, DeskripsiLayanan, HargaLayanan, pemesanan;
        private RelativeLayout rl2;
        ImageView image1;
        private ItemClickListener mitemClickListener;

        public LayananCuciMotorItemViewHolder(@NonNull View itemView) {
            super(itemView);
            TitleLayanan = itemView.findViewById(R.id.LayananCuciMotor);
            DeskripsiLayanan = itemView.findViewById(R.id.DeskripsiLayananCuciMotor);
            HargaLayanan = itemView.findViewById(R.id.HargaLayananCuciMotor);
            image1 = itemView.findViewById(R.id.imageLayananCuciMotor);
            pemesanan = itemView.findViewById(R.id.pesananCuciMotor);
        }

        private void bind(final LayananCuciMotorItem layananCuciMotorItem){
            TitleLayanan.setText(layananCuciMotorItem.getJenisLayanan());
            DeskripsiLayanan.setText(layananCuciMotorItem.getDeskripsiLayanan());
            HargaLayanan.setText(layananCuciMotorItem.getHarga());
            pemesanan.setText(layananCuciMotorItem.getPemesanan());
            new LayananCuciMotorAdapter.LayananCuciMotorItemViewHolder.DownloadImageTask(image1).execute(layananCuciMotorItem.getImageURL());

            pemesanan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( layananCuciMotorItem.getPemesanan().indexOf("6")==0) {
                        String message1 = "Jenis Kendaraan :";
                        String message2 = "Lokasi :";
                        String message3 = "Saya ingin memesan layanan";

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s&text=%s",
                                layananCuciMotorItem.getPemesanan(), message3 + " " + layananCuciMotorItem.getJenisLayanan() +"\n"+ "\n" + message1 + "\n" + message2 + "\n")));
                        c.startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+layananCuciMotorItem.getPemesanan()));
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
