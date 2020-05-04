package android.example.mocar.Cuci_Mobil;

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

public class CuciMobilAdapter extends RecyclerView.Adapter<CuciMobilAdapter.CuciMobilItemViewHolder>{
    private List<Cuci_Mobil_Item> cuci_mobil_items;
    Context context;

    @NonNull
    @Override
    public CuciMobilItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CuciMobilItemViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.cucimobil_item_list,parent,false)

        );
    }

    private CuciMobilAdapter.OnClicked onClick;
    public CuciMobilAdapter(ArrayList<Cuci_Mobil_Item> cuci_mobil_items, Context context, OnClicked onClick) {
        this.cuci_mobil_items = cuci_mobil_items;
        this.context = context;
        this.onClick = onClick;

    }

    public interface OnClicked{
        void onClick (Cuci_Mobil_Item cuci_mobil_item);
    }

    @Override
    public void onBindViewHolder(@NonNull final CuciMobilItemViewHolder holder, int position) {
        holder.bind(cuci_mobil_items.get(position), onClick);

    }

    @Override
    public int getItemCount() {
        return cuci_mobil_items.size();
    }

    class CuciMobilItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle, Description, kontak, Wa;
        private RelativeLayout rl;
        ImageView image;
        private ItemClickListener mitemClickListener;

        public CuciMobilItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            Description = itemView.findViewById(R.id.Description);
            kontak = itemView.findViewById(R.id.kontak);
            Wa = itemView.findViewById(R.id.Wa);
            image = itemView.findViewById(R.id.images);
            rl = itemView.findViewById(R.id.rl2);
        }
        private void bind(final Cuci_Mobil_Item cuci_mobil_item, final CuciMobilAdapter.OnClicked onClick){
            textTitle.setText(cuci_mobil_item.getTitle());
            Description.setText(cuci_mobil_item.getDescription());
            kontak.setText(cuci_mobil_item.getKontak());
            Wa.setText(cuci_mobil_item.getWa());

            new CuciMobilAdapter.CuciMobilItemViewHolder.DownloadImageTask(image).execute(cuci_mobil_item.getImageURL());
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick.onClick(cuci_mobil_item);
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
