package android.example.mocar.Cuci_Motor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.mocar.Cuci_Mobil.Display_List_CuciMobil;
import android.example.mocar.Cuci_Mobil.LayananCuciMobilAdapter;
import android.example.mocar.Cuci_Mobil.LayananCuciMobilItem;
import android.example.mocar.LocalStorage;
import android.example.mocar.R;
import android.example.mocar.Review_Activity;
import android.example.mocar.callback.callback_read_layanan_cuciMobil;
import android.example.mocar.callback.callback_read_layanan_cuciMotor;
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

public class Display_List_CuciMotor extends AppCompatActivity {
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
        setContentView(R.layout.activity_display__list__cuci_motor);

        mTitle = findViewById(R.id.TitleBengkelCuciMotor);
        mDesc = findViewById(R.id.DescriptionBengkelCuciMotor);
        mTelp = findViewById(R.id.KontakBengkelCuciMotor);
        mWa = findViewById(R.id.KontakBengkelCuciMotorWA);
        mImage = findViewById(R.id.CuciMotorView);

        Intent intent = getIntent();

        final String mTitleTv = intent.getStringExtra("iTitle");
        String mDescTv = intent.getStringExtra("iDesc");
        final String mTelpTv = intent.getStringExtra("iTelp");
        final String mWaTv = intent.getStringExtra("iTelp1");
        new DownloadImageTask(mImage).execute(intent.getStringExtra("iImage"));

        mTitle.setText(mTitleTv);
        mDesc.setText(mDescTv);
        mTelp.setText(mTelpTv);
        mWa.setText(mWaTv);

        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display_List_CuciMotor.this, Review_Activity.class);
                startActivity(intent);
            }
        });

        mTelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+mTelpTv));
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
                                        mWaTv, message3+mTitleTv+"\n"+"\n"+message1+"\n"+message2+"\n"+message4))));
            }
        });

        //Display List Layanan Mobil
        LocalStorage localStorage = new LocalStorage();
        ArrayList<LayananCuciMotorItem> layananCuciMotorItems = new ArrayList<>();
        stringID = localStorage.getCustomerId(getApplicationContext());
        ArrayList<HashMap<String, String>> alBookPick;
        alBookPick = Eksekusi(stringID);

        for(int i=0; i<alBookPick.size();++i){
            stringIDLayanan = alBookPick.get(i).get("id_layanan_cuciMotor");
            stringTitle = alBookPick.get(i).get("jenis_layanan_cuciMotor");
            stringDeskripsi = alBookPick.get(i).get("deskripsi_layanan_cuciMotor");
            stringHarga = alBookPick.get(i).get("harga_cuciMotor");
            stringURL = alBookPick.get(i).get("image_url_cuciMotor");
            stringPemesanan = alBookPick.get(i).get("kontak_pemesanan_cuciMotor");
            layananCuciMotorItems.add(
                    new LayananCuciMotorItem(stringIDLayanan,stringTitle, stringDeskripsi, stringHarga, stringURL, stringPemesanan));
        }
        RecyclerView LayananCuciMotorRecycleView = findViewById(R.id.LayananCuciMotor);
        layoutManager = new GridLayoutManager(this,1);
        LayananCuciMotorRecycleView.setHasFixedSize(true);
        LayananCuciMotorRecycleView.setLayoutManager(layoutManager);


        LayananCuciMotorAdapter layananCuciMotorAdapter = new LayananCuciMotorAdapter((ArrayList<LayananCuciMotorItem>)layananCuciMotorItems ,getApplicationContext());
        LayananCuciMotorRecycleView.setAdapter(layananCuciMotorAdapter);
    }

    public ArrayList<HashMap<String, String>> Eksekusi(String v1) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_read_layanan_cuciMotor read_layanan_cuci_motor = new callback_read_layanan_cuciMotor(Display_List_CuciMotor.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = read_layanan_cuci_motor.execute(
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
