package android.example.mocar.Konsultasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.example.mocar.LocalStorage;
import android.example.mocar.R;
import android.example.mocar.callback.callback_read_konsul;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class List_Konsul extends AppCompatActivity {
    String stringID;
    private String stringIDKonsul, stringTitle, stringContent, stringImage;
    private static final String TAG = "LoginActivity";

    private RecyclerView.LayoutManager layoutManager;

    private RecyclerView ListKonsul;

    TextView mTitle, mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__konsul);

        LocalStorage localStorage = new LocalStorage();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Riwayat Konsultasi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<KonsulItem> konsulItem = new ArrayList<>();
        stringID = localStorage.getCustomerId(getApplicationContext());
        ArrayList<HashMap<String, String>> alBookPick;
        alBookPick = Eksekusi(stringID);

        for(int i=0; i<alBookPick.size();++i){
            stringIDKonsul = alBookPick.get(i).get("id_konsul");
            stringTitle = alBookPick.get(i).get("judulKonsul");
            stringContent = alBookPick.get(i).get("isiKonsul");
            stringImage = alBookPick.get(i).get("img_konsul");
            konsulItem.add(
                    new KonsulItem(stringIDKonsul,stringTitle, stringContent, stringImage));
        }

        RecyclerView konsulRecycleView = findViewById(R.id.ListKonsul);
        layoutManager = new GridLayoutManager(this,1);
        konsulRecycleView.setHasFixedSize(true);
        konsulRecycleView.setLayoutManager(layoutManager);

        konsulRecycleView.setAdapter(new KonsulAdapter(this,konsulItem));

    }
    public ArrayList<HashMap<String, String>> Eksekusi(String v1) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_read_konsul read_konsul = new callback_read_konsul(List_Konsul.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = read_konsul.execute(
                    v1
            ).get();
            Log.d(TAG, "Eksekusi: "+v1);

        } catch (Exception e) {

        }

        return arrayList;
    }

}
