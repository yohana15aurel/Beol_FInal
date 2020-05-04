package android.example.mocar.Cuci_Motor;

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
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CuciMotorItemAdapter extends RecyclerView.Adapter<CuciMotorItemAdapter.CuciMotorItemViewHolder> {
    private List<CuciMotorItem> cuciMotorItems;

    Context context;

    @NonNull
    @Override
    public CuciMotorItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CuciMotorItemViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.cucimotor_item_list,parent,false)
        );
    }

    private CuciMotorItemAdapter.OnClicked onClick;
    public CuciMotorItemAdapter(ArrayList<CuciMotorItem> cuciMotorItems, Context context, OnClicked onClick) {
        this.cuciMotorItems = cuciMotorItems;
        this.context = context;
        this.onClick = onClick;
    }
    

    public interface OnClicked{
        void onClick (CuciMotorItem cuciMotorItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final CuciMotorItemAdapter.CuciMotorItemViewHolder holder, int position) {
        holder.bind(cuciMotorItems.get(position), onClick);

    }

    @Override
    public int getItemCount() {
        return cuciMotorItems.size();
    }

    class CuciMotorItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle, Description, kontak, Wa;
        private RelativeLayout rl;
        ImageView image;
        private ItemClickListener mitemClickListener;

        public CuciMotorItemViewHolder (@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            Description = itemView.findViewById(R.id.Description);
            kontak = itemView.findViewById(R.id.kontak);
            Wa = itemView.findViewById(R.id.Wa);
            image = itemView.findViewById(R.id.images);
            rl = itemView.findViewById(R.id.rl);
        }

        private void bind(final CuciMotorItem cuciMotorItem, final CuciMotorItemAdapter.OnClicked onClick){
            textTitle.setText(cuciMotorItem.getTitle());
            Description.setText(cuciMotorItem.getDescription());
            kontak.setText(cuciMotorItem.getKontak());
            Wa.setText(cuciMotorItem.getWa());

            new DownloadImageTask(image).execute(cuciMotorItem.getImageURL());
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick.onClick(cuciMotorItem);
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
