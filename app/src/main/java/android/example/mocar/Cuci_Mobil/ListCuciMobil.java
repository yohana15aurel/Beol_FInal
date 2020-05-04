package android.example.mocar.Cuci_Mobil;

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
import android.example.mocar.callback.callback_read_cuci_mobil;
import android.example.mocar.callback.callback_search_bengkel;
import android.example.mocar.callback.callback_search_cuciMobil;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListCuciMobil extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private String stringIDBengkel,stringNama, stringAlamat, stringKontak, stringKontakWa,stringURL;
    private static final String TAG = "LoginActivity";

    private RecyclerView.LayoutManager layoutManager;

    private Toolbar toolbar;
    List<Cuci_Mobil_Item> cuci_mobil_items;
    RecyclerView cuciMobilRecycleView;
    CuciMobilAdapter cuciMobilAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cuci_mobil);

        initView();
        loadDataBengkel();
    }

    private void loadDataBengkel() {
        cuci_mobil_items = new ArrayList<>();
        ArrayList<HashMap<String, String>> alBookPick;
        alBookPick = Eksekusi("get");

        for(int i=0; i<alBookPick.size();++i){
            stringIDBengkel = alBookPick.get(i).get("id_cuci_mobil");
            stringNama = alBookPick.get(i).get("nama_cuci_mobil");
            stringAlamat = alBookPick.get(i).get("alamat_cuci_mobil");
            stringKontak = alBookPick.get(i).get("kontak");
            stringKontakWa = alBookPick.get(i).get("kontakWa");
            stringURL = alBookPick.get(i).get("image_url");
            cuci_mobil_items.add(
                    new Cuci_Mobil_Item(stringIDBengkel,stringNama, stringAlamat, stringKontak, stringKontakWa, stringURL));
        }

        cuciMobilAdapter = new CuciMobilAdapter((ArrayList<Cuci_Mobil_Item>) cuci_mobil_items,getApplicationContext(),new CuciMobilAdapter.OnClicked() {
            @Override
            public void onClick(Cuci_Mobil_Item cuci_mobil_item) {
                String gTitle = cuci_mobil_item.getTitle();
                String gDesc = cuci_mobil_item.getDescription();
                String idBengkel = cuci_mobil_item.getId();
                String gTelp = cuci_mobil_item.getKontak();
                String gWa = cuci_mobil_item.getWa();
                String gImage = cuci_mobil_item.getImageURL();

                Intent intent = new Intent(ListCuciMobil.this, Display_List_CuciMobil.class);
                intent.putExtra("iID", idBengkel);
                intent.putExtra("iTitle", gTitle);
                intent.putExtra("iDesc", gDesc);
                intent.putExtra("iTelp", gTelp);
                intent.putExtra("iTelp1", gWa);
                intent.putExtra("iImage", gImage);
                startActivity(intent);
            }
        });
        cuciMobilRecycleView.setAdapter(cuciMobilAdapter);
    }

    private void initView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cuciMobilRecycleView = findViewById(R.id.CuciMobilRecycleView);

        layoutManager = new GridLayoutManager(this,1);
        cuciMobilRecycleView.setHasFixedSize(true);
        cuciMobilRecycleView.setLayoutManager(layoutManager);
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
        cuci_mobil_items = new ArrayList<>();
        ArrayList<HashMap<String, String>> alBookPick;
        alBookPick = searchAction(keyword);

        for(int i=0; i<alBookPick.size();++i){
            stringIDBengkel = alBookPick.get(i).get("id_cuci_mobil");
            stringNama = alBookPick.get(i).get("nama_cuci_mobil");
            stringAlamat = alBookPick.get(i).get("alamat_cuci_mobil");
            stringKontak = alBookPick.get(i).get("kontak");
            stringKontakWa = alBookPick.get(i).get("kontakWa");
            stringURL = alBookPick.get(i).get("image_url");
            cuci_mobil_items.add(
                    new Cuci_Mobil_Item(stringIDBengkel,stringNama, stringAlamat, stringKontak, stringKontakWa, stringURL));
        }

        cuciMobilAdapter = new CuciMobilAdapter((ArrayList<Cuci_Mobil_Item>) cuci_mobil_items,getApplicationContext(),new CuciMobilAdapter.OnClicked() {
            @Override
            public void onClick(Cuci_Mobil_Item cuci_mobil_item) {
                String gTitle = cuci_mobil_item.getTitle();
                String gDesc = cuci_mobil_item.getDescription();
                String idBengkel = cuci_mobil_item.getId();
                String gTelp = cuci_mobil_item.getKontak();
                String gWa = cuci_mobil_item.getWa();
                String gImage = cuci_mobil_item.getImageURL();

                Intent intent = new Intent(ListCuciMobil.this, Display_List_CuciMobil.class);
                intent.putExtra("iID", idBengkel);
                intent.putExtra("iTitle", gTitle);
                intent.putExtra("iDesc", gDesc);
                intent.putExtra("iTelp", gTelp);
                intent.putExtra("iTelp1", gWa);
                intent.putExtra("iImage", gImage);
                startActivity(intent);
            }
        });
        cuciMobilRecycleView.setVisibility(View.VISIBLE);
        cuciMobilRecycleView.setAdapter(cuciMobilAdapter);
    }


    public ArrayList<HashMap<String,String>> searchAction(String alamat_cuci_mobil){
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_search_cuciMobil search_cuciMobil = new callback_search_cuciMobil(ListCuciMobil.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = search_cuciMobil.execute(
                    alamat_cuci_mobil
            ).get();

        } catch (Exception e) {

        }

        return arrayList;
    }

    public ArrayList<HashMap<String, String>> Eksekusi(String v1) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_read_cuci_mobil read_cuci_mobil = new callback_read_cuci_mobil(ListCuciMobil.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = read_cuci_mobil.execute(
                    v1
            ).get();
            Log.d(TAG, "Eksekusi: "+v1);

        } catch (Exception e) {

        }

        return arrayList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        cuci_mobil_items.clear();
        cuciMobilRecycleView.setVisibility(View.GONE);
        searchDatabase(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onBackPressed() {
        cuci_mobil_items.clear();
        loadDataBengkel();
    }
}
