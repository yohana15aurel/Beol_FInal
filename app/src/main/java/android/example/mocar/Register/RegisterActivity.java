package android.example.mocar.Register;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.example.mocar.Menu_Dashboard.Menu;
import android.example.mocar.R;
import android.example.mocar.callback.callback_register;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    //Calendar Birth Date
    private static final String TAG = "MainActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    //Background
    ImageView bgapp;
    Animation frombottom;

    //Menu Register
    TextView textSignIn;
    EditText mName, mEmail, mPhone, mPassword, mConfirmation;

    //Button
    private Button mRegister;

    //Validation
    String email, pass, name,telp, date;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //find View By Id
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        bgapp = (ImageView)findViewById(R.id.bgapp);
        textSignIn = (TextView) findViewById(R.id.textSign);
        mName = (EditText) findViewById(R.id.name);
        mEmail = (EditText) findViewById(R.id.email);
        mPhone = (EditText)findViewById(R.id.phone);
        mPassword = (EditText)findViewById(R.id.password);
        mConfirmation = (EditText)findViewById(R.id.confirmation);
        mRegister = (Button)findViewById(R.id.btnRegister);

        //Date Picker
        mDisplayDate = (TextView)findViewById(R.id.tvDate);

        //animation
        bgapp.animate().translationY(-1100).setDuration(700).setStartDelay(400);
        textSignIn.startAnimation(frombottom);
        mName.startAnimation(frombottom);
        mEmail.startAnimation(frombottom);
        mPhone.startAnimation(frombottom);
        mPassword.startAnimation(frombottom);
        mConfirmation.startAnimation(frombottom);
        mRegister.startAnimation(frombottom);
        mDisplayDate.startAnimation(frombottom);

        //Display Birth Date
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(255,255,255)));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;

                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        //Button Register
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                pass = mPassword.getText().toString();
                name = mName.getText().toString();
                telp = mPhone.getText().toString();
                date = mDisplayDate.getText().toString();

                //Validation Register
                if (name.isEmpty()){
                    mName.setError("Field Cannot be Empty");
                }
                else if(email.isEmpty()){
                    mEmail.setError("Field Cannot be Empty");
                }else if(!email.matches(emailPattern)){
                    mEmail.setError("Enter Valid Email");
                }
                else if(telp.isEmpty()){
                    mPhone.setError("Field Cannot be Empty");
                }
                else if(date.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Field Cannot be Empty", Toast.LENGTH_SHORT).show();
                }
                else if(pass.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }else if(pass.length()<5){
                    Toast.makeText(RegisterActivity.this, "Enter Password more than 5 character", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(RegisterActivity.this, "Email : "+email+"\npassword : "+pass+"\nname : "+name,
                            Toast.LENGTH_LONG).show();
                    //CALL WEB SERVICE
                    ArrayList<HashMap<String, String>> alBookPick = null;
                    //ArrayList alBookPick = new ArrayList<>();
                    alBookPick = Eksekusi(name,email,pass,date,telp);
                    Intent intent = new Intent(RegisterActivity.this, Menu.class);
                    startActivity(intent);
                }
            }
        });

    }

    public ArrayList<HashMap<String, String>> Eksekusi(String v1, String v2,String v3,String v4, String v5){
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_register update_book = new callback_register(RegisterActivity.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = update_book.execute(
                    v1
                    , v2
                    , v3
                    , v4
                    , v5
            ).get();
        }catch (Exception e){

        }

        return arrayList;
    }
}
