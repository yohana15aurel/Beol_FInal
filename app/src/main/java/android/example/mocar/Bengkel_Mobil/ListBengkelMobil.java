package android.example.mocar.Bengkel_Mobil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.example.mocar.R;
import android.example.mocar.RestApi;
import android.example.mocar.SharedFunctions;
import android.example.mocar.callback.callback_read_bengkel;
import android.example.mocar.callback.callback_search_bengkel;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListBengkelMobil extends AppCompatActivity implements SearchView.OnQueryTextListener{

    BengkelItemAdapter mobilRecyclerView;
    SharedFunctions mSharedFunctions;

    private String stringIDBengkel,stringNama, stringAlamat, stringKontak, stringKontakWa,stringURL;
    private static final String TAG = "LoginActivity";

    private RecyclerView.LayoutManager layoutManager;

    private Toolbar toolbar;
    List<BengkelItem> bengkelItem;
    RecyclerView mobilRecycleView;
    BengkelItemAdapter bengkelItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bengkel_mobil);
        initView();
        loadDataBengkel();
    }

    public void initView(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mobilRecycleView = findViewById(R.id.MobilRecycleView);

        layoutManager = new GridLayoutManager(this,1);
        mobilRecycleView.setHasFixedSize(true);
        mobilRecycleView.setLayoutManager(layoutManager);
    }

    public void loadDataBengkel(){
        bengkelItem = new ArrayList<>();
        ArrayList<HashMap<String, String>> alBookPick;
        alBookPick = Eksekusi("get");

        for(int i=0; i<alBookPick.size();++i){
            stringIDBengkel = alBookPick.get(i).get("id_bengkel");
            stringNama = alBookPick.get(i).get("nama_bengkel");
            stringAlamat = alBookPick.get(i).get("alamat_bengkel");
            stringKontak = alBookPick.get(i).get("kontak");
            stringKontakWa = alBookPick.get(i).get("kontakWa");
            stringURL = alBookPick.get(i).get("image_url");
            bengkelItem.add(
                    new BengkelItem(stringIDBengkel,stringNama, stringAlamat, stringKontak, stringKontakWa, stringURL));
        }

        bengkelItemAdapter = new BengkelItemAdapter((ArrayList<BengkelItem>) bengkelItem,getApplicationContext(),new BengkelItemAdapter.OnClicked() {
            @Override
            public void onClick(BengkelItem bengkelItem) {
                String gTitle = bengkelItem.getTitle();
                String gDesc = bengkelItem.getDescription();
                String idBengkel = bengkelItem.getId();
                String gTelp = bengkelItem.getKontak();
                String gWa = bengkelItem.getWa();
                String gImage = bengkelItem.getImageURL();

                Intent intent = new Intent(ListBengkelMobil.this,Display_ListMobil.class);
                intent.putExtra("iID", idBengkel);
                intent.putExtra("iTitle", gTitle);
                intent.putExtra("iDesc", gDesc);
                intent.putExtra("iTelp", gTelp);
                intent.putExtra("iTelp1", gWa);
                intent.putExtra("iImage", gImage);
                startActivity(intent);
            }
        });
        mobilRecycleView.setAdapter(bengkelItemAdapter);
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
        bengkelItem = new ArrayList<>();
        ArrayList<HashMap<String, String>> alBookPick;
        alBookPick = searchAction(keyword);

        for(int i=0; i<alBookPick.size();++i){
            stringIDBengkel = alBookPick.get(i).get("id_bengkel");
            stringNama = alBookPick.get(i).get("nama_bengkel");
            stringAlamat = alBookPick.get(i).get("alamat_bengkel");
            stringKontak = alBookPick.get(i).get("kontak");
            stringKontakWa = alBookPick.get(i).get("kontakWa");
            stringURL = alBookPick.get(i).get("image_url");
            bengkelItem.add(
                    new BengkelItem(stringIDBengkel,stringNama, stringAlamat, stringKontak, stringKontakWa, stringURL));
        }

        bengkelItemAdapter = new BengkelItemAdapter((ArrayList<BengkelItem>) bengkelItem,getApplicationContext(),new BengkelItemAdapter.OnClicked() {
            @Override
            public void onClick(BengkelItem bengkelItem) {
                String gTitle = bengkelItem.getTitle();
                String gDesc = bengkelItem.getDescription();
                String idBengkel = bengkelItem.getId();
                String gTelp = bengkelItem.getKontak();
                String gWa = bengkelItem.getWa();
                String gImage = bengkelItem.getImageURL();

                Intent intent = new Intent(ListBengkelMobil.this,Display_ListMobil.class);
                intent.putExtra("iID", idBengkel);
                intent.putExtra("iTitle", gTitle);
                intent.putExtra("iDesc", gDesc);
                intent.putExtra("iTelp", gTelp);
                intent.putExtra("iTelp1", gWa);
                intent.putExtra("iImage", gImage);
                startActivity(intent);
            }
        });
        mobilRecycleView.setVisibility(View.VISIBLE);
        mobilRecycleView.setAdapter(bengkelItemAdapter);
    }

    public ArrayList<HashMap<String, String>> Eksekusi(String v1) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_read_bengkel read_bengkel = new callback_read_bengkel(ListBengkelMobil.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = read_bengkel.execute(
                    v1
            ).get();
            Log.d(TAG, "Eksekusi: "+v1);

        } catch (Exception e) {

        }

        return arrayList;
    }

    public ArrayList<HashMap<String,String>> searchAction(String alamat_bengkel){
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_search_bengkel search_bengkel = new callback_search_bengkel(ListBengkelMobil.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = search_bengkel.execute(
                    alamat_bengkel
            ).get();

        } catch (Exception e) {

        }

        return arrayList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        bengkelItem.clear();
        mobilRecycleView.setVisibility(View.GONE);
        searchDatabase(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;

    }

    @Override
    public void onBackPressed() {
        bengkelItem.clear();
        loadDataBengkel();
    }
}
