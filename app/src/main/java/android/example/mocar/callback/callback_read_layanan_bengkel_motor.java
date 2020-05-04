package android.example.mocar.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class callback_read_layanan_bengkel_motor extends AsyncTask<String, String, ArrayList<HashMap<String, String>>> {
    //Global global = new Global();
    private ProgressDialog pDialog;
    Context context;
    int success;
    String message;
    private JSONParser jsonParser = new JSONParser();
    JSONArray jsonArray = null;
    ArrayList<HashMap<String, String>> arrayListRet;

    public callback_read_layanan_bengkel_motor(Context m_context) {
        this.context= m_context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(String... params) {

        // Building Parameters
        List<NameValuePair> paramsed = new ArrayList<NameValuePair>();


        Log.d("CEKIDBOOK",params[0]);



        paramsed.add(new BasicNameValuePair("id_user", params[0])); //id


        JSONObject json = jsonParser.makeHttpRequest("http://dev.projectlab.co.id/mit/1317012/mst_read_layanan_bengkelMotor.php",
                "POST", paramsed);

        System.out.println("json 2 = "+json.toString());
        Log.d("CEKIDBOOK_MSG",json.toString());

        arrayListRet = new ArrayList<>();

        try {
            success = json.getInt("success");

            message = json.getString("message");


            if (success == 1) {
                jsonArray = json.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put("id_layanan", c.getString("id_layanan"));
                    map.put("jenis_layanan", c.getString("jenis_layanan"));
                    map.put("deskripsi_layanan", c.getString("deskripsi_layanan"));
                    map.put("harga", c.getString("harga"));
                    map.put("image_url", c.getString("image_url"));
                    map.put("kontak_pemesanan", c.getString("kontak_pemesanan"));
                    arrayListRet.add(map);
                }
            } else {
                //Unsuccessfully picking book. Another driver has taken the order.
                HashMap<String, String> map = new HashMap<String, String>();

                arrayListRet.add(map);

            }
        } catch (JSONException e) {
            return null;
        }

        System.out.println("arrayListRet = "+arrayListRet);
        return arrayListRet;
    }


    /**
     * After completing sys_navdrawer_background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(ArrayList<HashMap<String, String>> file_url) {

    }
}
