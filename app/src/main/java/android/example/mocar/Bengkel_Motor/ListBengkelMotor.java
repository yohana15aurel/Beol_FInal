package android.example.mocar.Bengkel_Motor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.mocar.Bengkel_Mobil.BengkelItem;
import android.example.mocar.Bengkel_Mobil.BengkelItemAdapter;
import android.example.mocar.Bengkel_Mobil.Display_ListMobil;
import android.example.mocar.Bengkel_Mobil.ListBengkelMobil;
import android.example.mocar.R;
import android.example.mocar.callback.callback_read_bengkel;
import android.example.mocar.callback.callback_read_bengkel_motor;
import android.example.mocar.callback.callback_search_bengkel;
import android.example.mocar.callback.callback_search_bengkelMotor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListBengkelMotor extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private RecyclerView motorRecyclerView;

    private String stringIDBengkelMotor,stringNamaMotor, stringAlamatMotor, stringKontakMotor, stringKontakWaMotor,stringURLMotor;
    private static final String TAG = "LoginActivity";

    private RecyclerView.LayoutManager layoutManager;

    private Toolbar toolbar;
    List<BengkelMotorItem> bengkelMotorItems;
    RecyclerView motorRecycleView;
    BengkelMotorItemAdapter bengkelMotorItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bengkel_motor);
        initView();
        loadDataBengkel();
    }

    public void initView(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        motorRecycleView = findViewById(R.id.MotorRecycleView);

        layoutManager = new GridLayoutManager(this,1);
        motorRecycleView.setHasFixedSize(true);
        motorRecycleView.setLayoutManager(layoutManager);
    }

    public void loadDataBengkel(){
        bengkelMotorItems = new ArrayList<>();
        ArrayList<HashMap<String, String>> alBookPick;
        alBookPick = Eksekusi("get");

        for(int i=0; i<alBookPick.size();++i){
            stringIDBengkelMotor = alBookPick.get(i).get("id_bengkel_motor");
            stringNamaMotor = alBookPick.get(i).get("nama_bengkel_motor");
            stringAlamatMotor = alBookPick.get(i).get("alamat_bengkel_motor");
            stringKontakMotor = alBookPick.get(i).get("kontak_motor");
            stringKontakWaMotor = alBookPick.get(i).get("kontakWa_motor");
            stringURLMotor = alBookPick.get(i).get("image_url");
            bengkelMotorItems.add(
                    new BengkelMotorItem(stringIDBengkelMotor,stringNamaMotor, stringAlamatMotor, stringKontakMotor, stringKontakWaMotor,stringURLMotor));
        }
//        RecyclerView motorRecycleView = findViewById(R.id.MotorRecycleView);
//
//        layoutManager = new GridLayoutManager(this,1);
//        motorRecycleView.setHasFixedSize(true);
//        motorRecycleView.setLayoutManager(layoutManager);

        bengkelMotorItemAdapter  = new BengkelMotorItemAdapter((ArrayList<BengkelMotorItem>) bengkelMotorItems,getApplicationContext(),new BengkelMotorItemAdapter.OnClicked() {
            @Override
            public void onClick(BengkelMotorItem bengkelMotorItems) {
                String gTitle = bengkelMotorItems.getTitle1();
                String gDesc = bengkelMotorItems.getDescription1();
                String idBengkel = bengkelMotorItems.getId1();
                String gTelp = bengkelMotorItems.getKontak1();
                String gWa = bengkelMotorItems.getWa1();
                String gImage = bengkelMotorItems.getImageURL1();

                Intent intent = new Intent(ListBengkelMotor.this, Display_ListMotor.class);
                intent.putExtra("iID", idBengkel);
                intent.putExtra("iTitle", gTitle);
                intent.putExtra("iDesc", gDesc);
                intent.putExtra("iTelp", gTelp);
                intent.putExtra("iTelp1", gWa);
                intent.putExtra("iImage", gImage);
                startActivity(intent);
            }
        });
        motorRecycleView.setAdapter(bengkelMotorItemAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    public void searchDatabase(final String keyword){
        bengkelMotorItems = new ArrayList<>();
        ArrayList<HashMap<String, String>> alBookPick;
        alBookPick = searchAction(keyword);

        for(int i=0; i<alBookPick.size();++i){
            stringIDBengkelMotor = alBookPick.get(i).get("id_bengkel_motor");
            stringNamaMotor = alBookPick.get(i).get("nama_bengkel_motor");
            stringAlamatMotor = alBookPick.get(i).get("alamat_bengkel_motor");
            stringKontakMotor = alBookPick.get(i).get("kontak_motor");
            stringKontakWaMotor = alBookPick.get(i).get("kontakWa_motor");
            stringURLMotor = alBookPick.get(i).get("image_url");
            bengkelMotorItems.add(
                    new BengkelMotorItem(stringIDBengkelMotor,stringNamaMotor, stringAlamatMotor, stringKontakMotor, stringKontakWaMotor, stringURLMotor));
        }

        bengkelMotorItemAdapter = new BengkelMotorItemAdapter((ArrayList<BengkelMotorItem>) bengkelMotorItems,getApplicationContext(),new BengkelMotorItemAdapter.OnClicked() {
            @Override
            public void onClick(BengkelMotorItem bengkelMotorItem) {
                String gTitle = bengkelMotorItem.getTitle1();
                String gDesc = bengkelMotorItem.getDescription1();
                String idBengkel = bengkelMotorItem.getId1();
                String gTelp = bengkelMotorItem.getKontak1();
                String gWa = bengkelMotorItem.getWa1();
                String gImage = bengkelMotorItem.getImageURL1();

                Intent intent = new Intent(ListBengkelMotor.this,Display_ListMotor.class);
                intent.putExtra("iID", idBengkel);
                intent.putExtra("iTitle", gTitle);
                intent.putExtra("iDesc", gDesc);
                intent.putExtra("iTelp", gTelp);
                intent.putExtra("iTelp1", gWa);
                intent.putExtra("iImage", gImage);
                startActivity(intent);
            }
        });
        motorRecyclerView.setVisibility(View.VISIBLE);
        motorRecyclerView.setAdapter(bengkelMotorItemAdapter);
    }
    public ArrayList<HashMap<String, String>> Eksekusi(String v1) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_read_bengkel_motor read_bengkel_motor = new callback_read_bengkel_motor(ListBengkelMotor.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = read_bengkel_motor.execute(
                    v1
            ).get();
            Log.d(TAG, "Eksekusi: "+v1);

        } catch (Exception e) {

        }

        return arrayList;
    }

    public ArrayList<HashMap<String,String>> searchAction(String alamat_bengkel_motor){
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_search_bengkelMotor search_bengkelMotor = new callback_search_bengkelMotor(ListBengkelMotor.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = search_bengkelMotor.execute(
                    alamat_bengkel_motor
            ).get();

        } catch (Exception e) {

        }

        return arrayList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        bengkelMotorItems.clear();
        motorRecyclerView.setVisibility(View.GONE);
        searchDatabase(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;

    }

    @Override
    public void onBackPressed() {
        bengkelMotorItems.clear();
        loadDataBengkel();
    }
}