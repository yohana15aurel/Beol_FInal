package android.example.mocar.Bengkel_Motor;

import android.content.Context;
import android.example.mocar.Bengkel_Mobil.BengkelItem;
import android.example.mocar.Bengkel_Mobil.BengkelItemAdapter;
import android.example.mocar.Interface.ItemClickListener;
import android.example.mocar.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class BengkelMotorItemAdapter extends RecyclerView.Adapter<BengkelMotorItemAdapter.BengkelMotorItemViewHolder> {
    private List<BengkelMotorItem> bengkelMotorItems;
    Context context;

    @NonNull
    @Override
    public BengkelMotorItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BengkelMotorItemViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.bengkelmotor_item_list,parent,false)
        );
    }

    private BengkelMotorItemAdapter.OnClicked onClick;
    public BengkelMotorItemAdapter(ArrayList<BengkelMotorItem> bengkelMotorItems, Context context, OnClicked onClick) {
        this.bengkelMotorItems = bengkelMotorItems;
        this.context = context;
        this.onClick = onClick;
    }

    public interface OnClicked{
        void onClick (BengkelMotorItem bengkelMotorItem);
    }

    @Override
    public void onBindViewHolder(@NonNull BengkelMotorItemViewHolder holder, int position) {
        holder.bind(bengkelMotorItems.get(position), onClick);
    }

    @Override
    public int getItemCount() {
        return bengkelMotorItems.size();
    }

    class BengkelMotorItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle, Description, kontak, Wa;
        private RelativeLayout rl;
        ImageView image;
        private ItemClickListener mitemClickListener;

        public BengkelMotorItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle1);
            Description = itemView.findViewById(R.id.Description1);
            kontak = itemView.findViewById(R.id.kontak1);
            Wa = itemView.findViewById(R.id.Wa1);
            image = itemView.findViewById(R.id.images1);
            rl = itemView.findViewById(R.id.rl1);
        }

        private void bind(final BengkelMotorItem bengkelMotorItem, final BengkelMotorItemAdapter.OnClicked onClick){
            textTitle.setText(bengkelMotorItem.getTitle1());
            Description.setText(bengkelMotorItem.getDescription1());
            kontak.setText(bengkelMotorItem.getKontak1());
            Wa.setText(bengkelMotorItem.getWa1());

            new BengkelMotorItemAdapter.BengkelMotorItemViewHolder.DownloadImageTask(image).execute(bengkelMotorItem.getImageURL1());
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick.onClick(bengkelMotorItem);
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
