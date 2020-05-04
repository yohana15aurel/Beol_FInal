package android.example.mocar.Bengkel_Motor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.mocar.Bengkel_Mobil.Display_ListMobil;
import android.example.mocar.Bengkel_Mobil.LayananBengkelMobilAdapter;
import android.example.mocar.Bengkel_Mobil.LayananBengkelMobilItem;
import android.example.mocar.LocalStorage;
import android.example.mocar.R;
import android.example.mocar.Review_Activity;
import android.example.mocar.callback.callback_read_layanan_bengkel_mobil;
import android.example.mocar.callback.callback_read_layanan_bengkel_motor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Display_ListMotor extends AppCompatActivity {
    //Display List Bengkel Mobil
    TextView mTitle, mDesc, mTelp, mWa;
    ImageView mImage;

    //Display List Layanan Bengkel Mobil
    String stringID;
    private String stringIDLayanan, stringTitle, stringDeskripsi, stringHarga, stringURL, stringPemesanan;
    private static final String TAG = "LoginActivity";
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__list_motor);

        mTitle = findViewById(R.id.TitleBengkelMotor);
        mDesc = findViewById(R.id.DescriptionBengkelMotor);
        mTelp = findViewById(R.id.KontakBengkelMotor);
        mWa = findViewById(R.id.KontakBengkelWAMotor);
        //mCall = findViewById(R.id.callBengkel);
        mImage = findViewById(R.id.BengkelMotorView);

        Intent intent = getIntent();

        final String mTitleTv = intent.getStringExtra("iTitle");
        String mDescTv = intent.getStringExtra("iDesc");
        final String mTelpTv = intent.getStringExtra("iTelp");
        final String mWaTv = intent.getStringExtra("iTelp1");
        new Display_ListMotor.DownloadImageTask(mImage).execute(intent.getStringExtra("iImage"));

        mTitle.setText(mTitleTv);
        mDesc.setText(mDescTv);
        mTelp.setText(mTelpTv);
        mWa.setText(mWaTv);

        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display_ListMotor.this, Review_Activity.class);
                startActivity(intent);
            }
        });

        mTelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mTelpTv));
                startActivity(intent);


            }
        });

        mWa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message1 = "Jenis Kendaraan :";
                String message2 = "Lokasi :";
                String message4 = "Jenis Layanan :";
                String message3 = "Saya ingin memesan layanan di ";

                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(
                                String.format("https://api.whatsapp.com/send?phone=%s&text=%s",
                                        mWaTv, message3 + mTitleTv + "\n" + "\n" + message1 + "\n" + message2 + "\n" + message4))));
            }
        });
        //Display List Layanan Motor
        LocalStorage localStorage = new LocalStorage();
        ArrayList<LayananBengkelMotorItem> layananBengkelMotorItems = new ArrayList<>();
        stringID = localStorage.getCustomerId(getApplicationContext());
        ArrayList<HashMap<String, String>> alBookPick;
        alBookPick = Eksekusi(stringID);

        for(int i=0; i<alBookPick.size();++i){
            stringIDLayanan = alBookPick.get(i).get("id_layanan");
            stringTitle = alBookPick.get(i).get("jenis_layanan");
            stringDeskripsi = alBookPick.get(i).get("deskripsi_layanan");
            stringHarga = alBookPick.get(i).get("harga");
            stringURL = alBookPick.get(i).get("image_url");
            stringPemesanan = alBookPick.get(i).get("kontak_pemesanan");
            layananBengkelMotorItems.add(
                    new LayananBengkelMotorItem(stringIDLayanan,stringTitle, stringDeskripsi, stringHarga, stringURL, stringPemesanan));
        }
        RecyclerView LayananMotorRecycleView = findViewById(R.id.ListLayananMotor);
        layoutManager = new GridLayoutManager(this,1);
        LayananMotorRecycleView.setHasFixedSize(true);
        LayananMotorRecycleView.setLayoutManager(layoutManager);


        LayananBengkelMotorAdapter layananBengkelMotorAdapter = new LayananBengkelMotorAdapter((ArrayList<LayananBengkelMotorItem>)layananBengkelMotorItems ,getApplicationContext());
        LayananMotorRecycleView.setAdapter(layananBengkelMotorAdapter);
    }

    public ArrayList<HashMap<String, String>> Eksekusi(String v1) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_read_layanan_bengkel_motor read_layanan_bengkel_motor = new callback_read_layanan_bengkel_motor(Display_ListMotor.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = read_layanan_bengkel_motor.execute(
                    v1
            ).get();
            Log.d(TAG, "Eksekusi: "+v1);

        } catch (Exception e) {

        }

        return arrayList;
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
