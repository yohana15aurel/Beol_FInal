package android.example.mocar.Cuci_Motor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.mocar.Bengkel_Mobil.Display_ListMobil;
import android.example.mocar.Cuci_Mobil.CuciMobilAdapter;
import android.example.mocar.Cuci_Mobil.Cuci_Mobil_Item;
import android.example.mocar.Cuci_Mobil.ListCuciMobil;
import android.example.mocar.R;
import android.example.mocar.callback.callback_read_cuci_mobil;
import android.example.mocar.callback.callback_read_cuci_motor;
import android.example.mocar.callback.callback_search_cuciMobil;
import android.example.mocar.callback.callback_search_cuciMotor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListCuciMotor extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private String stringIDBengkel,stringNama, stringAlamat, stringKontak, stringKontakWa,stringURL;
    private static final String TAG = "LoginActivity";

    private RecyclerView.LayoutManager layoutManager;

    private Toolbar toolbar;
    List<CuciMotorItem> cuciMotorItems;
    RecyclerView cuciMotorRecycleView;
    CuciMotorItemAdapter cuciMotorItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cuci_motor);

        initView();
        loadDataBengkel();
    }

    private void loadDataBengkel() {
        cuciMotorItems = new ArrayList<>();
        ArrayList<HashMap<String, String>> alBookPick;
        alBookPick = Eksekusi("get");

        for(int i=0; i<alBookPick.size();++i){
            stringIDBengkel = alBookPick.get(i).get("id_cuci_motor");
            stringNama = alBookPick.get(i).get("nama_cuci_motor");
            stringAlamat = alBookPick.get(i).get("alamat_cuci_motor");
            stringKontak = alBookPick.get(i).get("kontak_cuciMotor");
            stringKontakWa = alBookPick.get(i).get("kontakWa_cuciMotor");
            stringURL = alBookPick.get(i).get("image_url_cuciMotor");
            cuciMotorItems.add(
                    new CuciMotorItem(stringIDBengkel,stringNama, stringAlamat, stringKontak, stringKontakWa, stringURL));
        }


       cuciMotorItemAdapter = new CuciMotorItemAdapter((ArrayList<CuciMotorItem>) cuciMotorItems,getApplicationContext(),new CuciMotorItemAdapter.OnClicked() {
            @Override
            public void onClick(CuciMotorItem cuciMotorItem) {
                String gTitle = cuciMotorItem.getTitle();
                String gDesc = cuciMotorItem.getDescription();
                String idBengkel = cuciMotorItem.getId();
                String gTelp = cuciMotorItem.getKontak();
                String gWa = cuciMotorItem.getWa();
                String gImage = cuciMotorItem.getImageURL();

                Intent intent = new Intent(ListCuciMotor.this, Display_List_CuciMotor.class);
                intent.putExtra("iID", idBengkel);
                intent.putExtra("iTitle", gTitle);
                intent.putExtra("iDesc", gDesc);
                intent.putExtra("iTelp", gTelp);
                intent.putExtra("iTelp1", gWa);
                intent.putExtra("iImage", gImage);
                startActivity(intent);
            }
        });
        cuciMotorRecycleView.setAdapter(cuciMotorItemAdapter);
    }

//        List<CuciMotorItem> cuciMotorItems = new ArrayList<>();
//        //stringIDBengkel = localStorage.getCustomerId(getApplicationContext());
//        ArrayList<HashMap<String, String>> alBookPick;
//        alBookPick = Eksekusi("get");
//
//        for(int i=0; i<alBookPick.size();++i){
//            stringIDBengkel = alBookPick.get(i).get("id_cuci_motor");
//            stringNama = alBookPick.get(i).get("nama_cuci_motor");
//            stringAlamat = alBookPick.get(i).get("alamat_cuci_motor");
//            stringKontak = alBookPick.get(i).get("kontak_cuciMotor");
//            stringKontakWa = alBookPick.get(i).get("kontakWa_cuciMotor");
//            stringURL = alBookPick.get(i).get("image_url_cuciMotor");
//            cuciMotorItems.add(
//                    new CuciMotorItem(stringIDBengkel,stringNama, stringAlamat, stringKontak, stringKontakWa, stringURL));
//        }
//        RecyclerView cucimotorrecycleview = findViewById(R.id.CuciMotorRecycleView);
//
//        layoutManager = new GridLayoutManager(this,1);
//        cucimotorrecycleview.setHasFixedSize(true);
//        cucimotorrecycleview.setLayoutManager(layoutManager);
//
//        CuciMotorItemAdapter cuciMotorItemAdapter = new CuciMotorItemAdapter((ArrayList<CuciMotorItem>) cuciMotorItems,getApplicationContext(),new CuciMotorItemAdapter.OnClicked() {
//            @Override
//            public void onClick(CuciMotorItem cuciMotorItem) {
//                String gTitle = cuciMotorItem.getTitle();
//                String gDesc = cuciMotorItem.getDescription();
//                String idBengkel = cuciMotorItem.getId();
//                String gTelp = cuciMotorItem.getKontak();
//                String gWa = cuciMotorItem.getWa();
//                String gImage = cuciMotorItem.getImageURL();
//
//                Intent intent = new Intent(ListCuciMotor.this, Display_List_CuciMotor.class);
//                intent.putExtra("iID", idBengkel);
//                intent.putExtra("iTitle", gTitle);
//                intent.putExtra("iDesc", gDesc);
//                intent.putExtra("iTelp", gTelp);
//                intent.putExtra("iTelp1", gWa);
//                intent.putExtra("iImage", gImage);
//                startActivity(intent);
//            }
//        });
//        cucimotorrecycleview.setAdapter(cuciMotorItemAdapter);
//    }

    private void initView() {
            toolbar = (Toolbar)findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            cuciMotorRecycleView = findViewById(R.id.CuciMotorRecycleView);

            layoutManager = new GridLayoutManager(this,1);
            cuciMotorRecycleView.setHasFixedSize(true);
            cuciMotorRecycleView.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }


    public ArrayList<HashMap<String, String>> Eksekusi(String v1) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_read_cuci_motor read_cuci_motor = new callback_read_cuci_motor(ListCuciMotor.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = read_cuci_motor.execute(
                    v1
            ).get();
            Log.d(TAG, "Eksekusi: "+v1);

        } catch (Exception e) {

        }

        return arrayList;
    }

    public void searchDatabase(final String keyword){
        cuciMotorItems = new ArrayList<>();
        ArrayList<HashMap<String, String>> alBookPick;
        alBookPick = searchAction(keyword);

        for(int i=0; i<alBookPick.size();++i){
            stringIDBengkel = alBookPick.get(i).get("id_cuci_motor");
            stringNama = alBookPick.get(i).get("nama_cuci_motor");
            stringAlamat = alBookPick.get(i).get("alamat_cuci_motor");
            stringKontak = alBookPick.get(i).get("kontak_cuciMotor");
            stringKontakWa = alBookPick.get(i).get("kontakWa_cuciMotor");
            stringURL = alBookPick.get(i).get("image_url_cuciMotor");
            cuciMotorItems.add(
                    new CuciMotorItem(stringIDBengkel,stringNama, stringAlamat, stringKontak, stringKontakWa, stringURL));
        }


        cuciMotorItemAdapter = new CuciMotorItemAdapter((ArrayList<CuciMotorItem>) cuciMotorItems,getApplicationContext(),new CuciMotorItemAdapter.OnClicked() {
            @Override
            public void onClick(CuciMotorItem cuciMotorItem) {
                String gTitle = cuciMotorItem.getTitle();
                String gDesc = cuciMotorItem.getDescription();
                String idBengkel = cuciMotorItem.getId();
                String gTelp = cuciMotorItem.getKontak();
                String gWa = cuciMotorItem.getWa();
                String gImage = cuciMotorItem.getImageURL();

                Intent intent = new Intent(ListCuciMotor.this, Display_List_CuciMotor.class);
                intent.putExtra("iID", idBengkel);
                intent.putExtra("iTitle", gTitle);
                intent.putExtra("iDesc", gDesc);
                intent.putExtra("iTelp", gTelp);
                intent.putExtra("iTelp1", gWa);
                intent.putExtra("iImage", gImage);
                startActivity(intent);
            }
        });
        cuciMotorRecycleView.setVisibility(View.VISIBLE);
        cuciMotorRecycleView.setAdapter(cuciMotorItemAdapter);


    }

    private ArrayList<HashMap<String, String>> searchAction(String alamat_cuci_motor) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_search_cuciMotor search_cuciMotor = new callback_search_cuciMotor(ListCuciMotor.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = search_cuciMotor.execute(
                    alamat_cuci_motor
            ).get();

        } catch (Exception e) {

        }

        return arrayList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        cuciMotorItems.clear();
        cuciMotorRecycleView.setVisibility(View.GONE);
        searchDatabase(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onBackPressed() {
        cuciMotorItems.clear();
        loadDataBengkel();
    }
}

