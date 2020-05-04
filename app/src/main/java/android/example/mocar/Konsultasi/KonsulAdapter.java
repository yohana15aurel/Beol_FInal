package android.example.mocar.Konsultasi;

import android.content.Context;
import android.content.Intent;
import android.example.mocar.Bengkel_Mobil.LayananBengkelMobilAdapter;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;

public class KonsulAdapter extends RecyclerView.Adapter<KonsulAdapter.KonsulViewHolder>{
    private List<KonsulItem> mKonsulItems;
    Context c;

    @NonNull
    @Override
    public KonsulViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new KonsulViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.konsul_item_list_display,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull KonsulViewHolder holder, int position) {
        holder.textTitle.setText(mKonsulItems.get(position).getTitle());
        holder.Description.setText(mKonsulItems.get(position).getContent());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                String titleKonsul = mKonsulItems.get(position).getTitle();
                String descKonsul = mKonsulItems.get(position).getContent();
                String idKonsul = mKonsulItems.get(position).getId();

                Intent intent = new Intent(c, List_Konsul.class);
                intent.putExtra("iID", idKonsul);
                intent.putExtra("iTitle", titleKonsul);
                intent.putExtra("iDescription", descKonsul);

                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mKonsulItems.size();
    }

    public KonsulAdapter(Context c, List<KonsulItem> konsulItems) {
        this.mKonsulItems = konsulItems;
        this.c = c;
    }



    class KonsulViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textTitle, Description;
        ItemClickListener mItemClickListener;
        ImageView mImage;

        public KonsulViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textJudul);
            Description = itemView.findViewById(R.id.textKeluhan);
            mImage = itemView.findViewById(R.id.images);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            this.mItemClickListener.onItemClickListener(v,getLayoutPosition());

        }

        public void setItemClickListener(ItemClickListener ic){
            this.mItemClickListener=ic;

        }
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

