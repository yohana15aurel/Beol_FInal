package android.example.mocar.Bengkel_Mobil;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BengkelItemAdapter extends RecyclerView.Adapter<BengkelItemAdapter.BengkelItemViewHolder>implements Filterable {
    private List<BengkelItem> bengkelItems;

    private List<BengkelItem> bengkelItemsAll;
    Context context;

    @NonNull
    @Override
    public BengkelItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BengkelItemViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.bengkelmobil_item_list,parent,false)
        );
    }

    private BengkelItemAdapter.OnClicked onClick;
    public BengkelItemAdapter(ArrayList<BengkelItem> bengkelItems, Context context, OnClicked onClick) {
        this.bengkelItems = bengkelItems;
        bengkelItemsAll = new ArrayList<BengkelItem>();
        bengkelItemsAll.addAll(bengkelItems);
        this.context = context;
        this.onClick = onClick;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<BengkelItem> filteredList = new ArrayList<>();
            if(charSequence.toString().isEmpty()){
                filteredList.addAll(bengkelItemsAll);
            }else{
                for(BengkelItem bengkel: bengkelItemsAll){
                    if(bengkel.toString().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(bengkel);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;

        }
        //run on ui thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            bengkelItems.clear();
            bengkelItemsAll.addAll((Collection<? extends BengkelItem>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public interface OnClicked{
        void onClick (BengkelItem bengkelItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final BengkelItemViewHolder holder, int position) {
        holder.bind(bengkelItems.get(position), onClick);

    }

    @Override
    public int getItemCount() {
        return bengkelItems.size();
    }

    class BengkelItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle, Description, kontak, Wa;
        private RelativeLayout rl;
        ImageView image;
        private ItemClickListener mitemClickListener;

        public BengkelItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            Description = itemView.findViewById(R.id.Description);
            kontak = itemView.findViewById(R.id.kontak);
            Wa = itemView.findViewById(R.id.Wa);
            image = itemView.findViewById(R.id.images);
            rl = itemView.findViewById(R.id.rl);
        }

        private void bind(final BengkelItem bengkelItem, final OnClicked onClick){
            textTitle.setText(bengkelItem.getTitle());
            Description.setText(bengkelItem.getDescription());
            kontak.setText(bengkelItem.getKontak());
            Wa.setText(bengkelItem.getWa());

            new DownloadImageTask(image).execute(bengkelItem.getImageURL());
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick.onClick(bengkelItem);
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
