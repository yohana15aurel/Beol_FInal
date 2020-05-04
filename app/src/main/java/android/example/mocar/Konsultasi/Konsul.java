package android.example.mocar.Konsultasi;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.example.mocar.LocalStorage;
import android.example.mocar.Login.MainActivity;
import android.example.mocar.Profile;
import android.example.mocar.R;
import android.example.mocar.RestApi;
import android.example.mocar.SharedFunctions;
import android.example.mocar.callback.callback_konsul;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Konsul extends AppCompatActivity {
    private Button mSend;
    private EditText mTitle, mContent;
    private String stringTitle, stringContent;
    private ImageView profpic, addprofpic;

    private Activity mActivity;
    private Context mContext;
    private boolean isImageEmpty = true;
    private Uri mCropImageUri;
    final LocalStorage localStorage = new LocalStorage();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsul);

        //CALL WEB SERVICE
        mActivity = Konsul.this;
        mContext = getApplicationContext();

        mTitle = findViewById(R.id.title);
        mContent = findViewById(R.id.content);
        mSend = findViewById(R.id.send);
        profpic = findViewById(R.id.image_konsul);
        addprofpic = findViewById(R.id.add);

        final LocalStorage localStorage = new LocalStorage();

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        enableImageAdd();
        getSupportActionBar().setTitle("Konsultasi");

        mSend.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Creating alert Dialog with two Buttons

                //AlertDialog.Builder alertDialog = new AlertDialog.Builder(AlertDialogActivity.this);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(arg0.getContext());

                // Setting Dialog Title
                alertDialog.setTitle("Confirm Send...");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want send this?");

                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.ic_assignment_ind_black_24dp);

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                // Write your code here to execute after dialog
                                Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                                Toast.makeText(Konsul.this, "Title : "+mTitle+"\nContent : "+mContent,
                                        Toast.LENGTH_LONG).show();
                                String id = localStorage.getCustomerId(getApplicationContext());
                                stringTitle = mTitle.getText().toString();
                                stringContent = mContent.getText().toString();

                                ArrayList<HashMap<String, String>> alBookPick = null;
                                alBookPick = Eksekusi(id,stringTitle,stringContent);

//                Intent intent = new Intent(Konsul.this, List_Konsul.class);
//                startActivity(intent);

                                if (alBookPick.size()<0){
                                    Toast.makeText(Konsul.this,"Error", Toast.LENGTH_SHORT).show();
                                }else{

                                    Toast.makeText(Konsul.this,"Message Send", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Konsul.this, List_Konsul.class);
                                    startActivity(intent);
                                }
                            }
                        });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,	int which) {
                                // Write your code here to execute after dialog
                                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                // Showing Alert Message
                uploadToServer();
                alertDialog.show();

            }


        });

    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_konsul,menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        final LocalStorage localStorage = new LocalStorage();
//        String msg = " ";
//        switch (item.getItemId()){
//            case R.id.save:
//                msg = "Save Profile";
//                uploadToServer();
//                return true;
//        }
//        Toast.makeText(Konsul.this, msg, Toast.LENGTH_SHORT).show();
//        return super.onOptionsItemSelected(item);
//    }
    public ArrayList<HashMap<String, String>> Eksekusi(String v1, String v2,String v3){
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_konsul update_book = new callback_konsul(Konsul.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = update_book.execute(
                    v1
                    , v2
                    ,v3

            ).get();
        }catch (Exception e){

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
                            profpic.setImageResource(R.drawable.ic_photo_black_24dp);
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

        req = server.postItemImageKonsul(isImageEmptyBody, userToken, itemImage);


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
