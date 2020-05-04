package android.example.mocar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.example.mocar.Bengkel_Mobil.BengkelItem;
import android.example.mocar.Bengkel_Mobil.Display_ListMobil;
import android.example.mocar.Bengkel_Mobil.ListBengkelMobil;
import android.example.mocar.Konsultasi.Konsul;
import android.example.mocar.Konsultasi.List_Konsul;
import android.example.mocar.Login.MainActivity;
import android.example.mocar.Menu_Dashboard.Menu;
import android.example.mocar.Register.RegisterActivity;
import android.example.mocar.callback.callback_read_konsul;
import android.example.mocar.callback.callback_read_register;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {
    ImageView mImageView;
    private ImageView profpic, addprofpic;

    //Toolbar
    private Toolbar toolbar;

    private static final String TAG = "LoginActivity";

    TextView mName, mEmail, mPhone,mDisplayDate;
    String id1,email1, name1,telp1, date1, imageURL;

    private Activity mActivity;
    private Context mContext;
    private boolean isImageEmpty = true;
    private Uri mCropImageUri;
    final LocalStorage localStorage = new LocalStorage();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //CALL WEB SERVICE
        mActivity = Profile.this;
        mContext = getApplicationContext();

        mName = findViewById(R.id.isiNama);
        mEmail = findViewById(R.id.isiEmail);
        mDisplayDate = findViewById(R.id.isiTanggalLahir);
        mPhone = findViewById(R.id.isiHp);
        profpic = findViewById(R.id.image_view);
        addprofpic = findViewById(R.id.add);

        //CALL WEB SERVICE
        id1 = localStorage.getCustomerId(getApplicationContext());
        ArrayList<HashMap<String, String>> alBookPick;
        alBookPick = Eksekusi(id1);

        for (int i=0; i<alBookPick.size();++i){
            //id1 = alBookPick.get(i).get("id_user");
            name1 = alBookPick.get(i).get("name");
            email1 = alBookPick.get(i).get("email");
            date1 = alBookPick.get(i).get("birthdate");
            telp1 = alBookPick.get(i).get("mobilephone");
            imageURL = alBookPick.get(i).get("img_profile");
        }


        mName.setText(name1);
        mEmail.setText(email1);
        mDisplayDate.setText(date1);
        mPhone.setText(telp1);
        if (!imageURL.equals("null")) {
            String IMAGE_URL = "https://dev.projectlab.co.id/mit/1317012/images/profPic/";
            Glide.with(mContext).load(IMAGE_URL + imageURL).into(profpic);
        } else {
            Glide.with(mContext).load(R.drawable.ic_account_circle_black_24dp).into(profpic);
        }

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        enableImageAdd();
        getSupportActionBar().setTitle("Profile");
        BottomNavigationView bottomNavigationView = findViewById(R.id.NavBot);

        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Menu.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.konsul:
                        startActivity(new Intent(getApplicationContext(), Konsul.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        mImageView = findViewById(R.id.image_view);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_profile,menu);
        return true;
    }

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
@Override
public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    final LocalStorage localStorage = new LocalStorage();
    String msg = " ";
    switch (item.getItemId()){
        case R.id.save:
            msg = "Save Profile";
            uploadToServer();
            return true;
        case R.id.setting:
            msg = "Setting";
            break;
        case R.id.logout:
            msg = "Logout";
            localStorage.logout(getApplicationContext(),localStorage.getCustomerId(getApplicationContext()));
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            break;
    }
    Toast.makeText(Profile.this, msg, Toast.LENGTH_SHORT).show();
    return super.onOptionsItemSelected(item);
}

    public ArrayList<HashMap<String, String>> Eksekusi(String v1) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_read_register read_register = new callback_read_register(Profile.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = read_register.execute(
                    v1
            ).get();
            Log.d(TAG, "Eksekusi: "+v1);

        } catch (Exception e) {

        }

        return arrayList;
    }

    private void enableImageAdd() {
        addprofpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CropImage.isExplicitCameraPermissionRequired(mContext)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
                    }
                } else {
                    CropImage.startPickImageActivity(mActivity);
                }
            }
        });
    }
    private void enableImageRemove() {
        addprofpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Hapus"};
                final AlertDialog.Builder imgDialog = new AlertDialog.Builder(mActivity);
                imgDialog.setTitle("gambar");
                imgDialog.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Hapus")) {
                            profpic.setImageResource(R.drawable.user);
                            isImageEmpty = true;
                            mCropImageUri = null;
                            enableImageAdd();
                        }
                    }
                });
                imgDialog.show();
            }
        });
    }

    private void disableImage() {
        addprofpic.setOnClickListener(null);
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setFixAspectRatio(true)
                .setAspectRatio(16,9)
                .setRequestedSize(800, 450, CropImageView.RequestSizeOptions.RESIZE_INSIDE)
                .start(this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case (CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE): {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CropImage.startPickImageActivity(this);
                } else {
                    Toast.makeText(this, "Batal, izin kamera tidak diberikan", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case (CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE): {
                if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCropImageActivity(mCropImageUri);
                } else {
                    Toast.makeText(this, "Batal, izin penyimpanan tidak diberikan", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case (CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE): {
                    Uri imageUri = CropImage.getPickImageResultUri(this, data);

                    if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                        mCropImageUri = imageUri;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
                        }
                    } else {
                        startCropImageActivity(imageUri);
                    }
                    break;
                }
                case (CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE): {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    mCropImageUri = result.getUri();
                    Glide.with(mActivity).load(mCropImageUri).into(profpic);
                    enableImageRemove();
                    isImageEmpty = false;
                    break;
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void uploadToServer() {
        disableImage();

        RestApi server = SharedFunctions.getRetrofit().create(RestApi.class);
        RequestBody isImageEmptyBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(this.isImageEmpty));
        RequestBody userToken = RequestBody.create(MediaType.parse("text/plain"),localStorage.getCustomerId(this));

        Call<ResponseBody> req;
        File file = new File(Objects.requireNonNull(mCropImageUri.getPath()));
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part itemImage = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);

        req = server.postItemImage(isImageEmptyBody, userToken, itemImage);


        req.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    JSONObject json = new JSONObject(Objects.requireNonNull(response.body()).string());
                    int success = json.getInt("success");
                    int imgSuccess = json.getInt("imgSuccess");

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
                    if (success == 0) {
                        showConnError();
                    } else {
                        alertDialog.setPositiveButton(mContext.getString(R.string.alert_agree), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        if (success == 1 && imgSuccess == 0) {
                            alertDialog.setTitle(R.string.alert_success_title)
                                    .setMessage(R.string.alert_success_item_0_desc)
                                    .setIcon(R.drawable.ic_check_circle_black_24dp);
                        } else if (success == 1 && (imgSuccess == 1 || imgSuccess == -1)) {
                            alertDialog.setTitle(R.string.alert_success_title)
                                    .setMessage(R.string.alert_success_item_1_desc)
                                    .setIcon(R.drawable.ic_check_circle_black_24dp);
                        }
                    }
                    alertDialog.show();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                showConnError();
            }
        });
    }
    private void showConnError() {
        if (isImageEmpty) {
            enableImageAdd();
        } else {
            enableImageRemove();
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
        alertDialog.setTitle(R.string.alert_conn_title)
                .setMessage(R.string.alert_conn_desc)
                .setIcon(R.drawable.ic_error_black_24dp)
                .setPositiveButton(mContext.getString(R.string.alert_agree), null)
                .show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }



}
