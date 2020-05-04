package android.example.mocar.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.example.mocar.LocalStorage;
import android.example.mocar.Menu_Dashboard.Menu;
import android.example.mocar.R;
import android.example.mocar.Register.RegisterActivity;
import android.example.mocar.callback.callback_login;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    //Validation Email
    String email,  pass;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ImageView bgapp,logo;
    TextView textSignIn;
    Animation frombottom;
    LinearLayout menuSign;

    private EditText mPassword;
    private Button mCreate, mLogin;
    private EditText mEmail;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            //Set internet available or not
            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
                //whe internet inactive
                Dialog dialog = new Dialog(this);
                //set content view
                dialog.setContentView(R.layout.alert_dialog);
                //set outside touch
                dialog.setCanceledOnTouchOutside(false);
                //set Dialog width and height
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                //set transparant background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //set animation
                dialog.getWindow().getAttributes().windowAnimations =
                        android.R.style.Animation_Dialog;

                //initialize dialog variable
                Button btnTry = dialog.findViewById(R.id.btnTry);
                //Perform onClick listener
                btnTry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Call recreate methode
                        recreate();
                    }
                });
                //show dialog
                dialog.show();
            } else {
                //when internet active
                setContentView(R.layout.activity_main);
            }


        final LocalStorage localStorage = new LocalStorage();

        if(localStorage.getCustomerId(getApplicationContext())!=""){
            finish();
            Intent intent = new Intent(getApplicationContext(), Menu.class);
            startActivity(intent);
        }

        //animation
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        bgapp = (ImageView)findViewById(R.id.bgapp);
        logo = (ImageView)findViewById(R.id.logo);
        textSignIn = (TextView) findViewById(R.id.textSign);
        menuSign = (LinearLayout)findViewById(R.id.menuSign);

        mCreate = (Button)findViewById(R.id.createbox);
        mLogin = (Button)findViewById(R.id.btnLogin) ;

        bgapp.animate().translationY(-1100).setDuration(700).setStartDelay(400);
        logo.animate().alpha(0).setDuration(900).setStartDelay(600);

        textSignIn.startAnimation(frombottom);
        menuSign.startAnimation(frombottom);
        mPassword = (EditText) findViewById(R.id.password);

        //email
        mEmail = (EditText)findViewById(R.id.email);

        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set internet available or not
                ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
                    //whe internet inactive
                    Dialog dialog = new Dialog(MainActivity.this);
                    //set content view
                    dialog.setContentView(R.layout.alert_dialog);
                    //set outside touch
                    dialog.setCanceledOnTouchOutside(false);
                    //set Dialog width and height
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.WRAP_CONTENT);
                    //set transparant background
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    //set animation
                    dialog.getWindow().getAttributes().windowAnimations =
                            android.R.style.Animation_Dialog;

                    //initialize dialog variable
                    Button btnTry = dialog.findViewById(R.id.btnTry);
                    //Perform onClick listener
                    btnTry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Call recreate methode
                            recreate();
                        }
                    });
                    //show dialog
                    dialog.show();
                }else{
                    //when internet active
                    //setContentView(R.layout.activity_main);
                    email = mEmail.getText().toString();
                    pass = mPassword.getText().toString();

                    if(email.isEmpty()){
                        mEmail.setError("Field cannot be empty");
                    }else if(!email.matches(emailPattern)){
                        mEmail.setError("Enter Valid Email");
                    }
                    else if(pass.isEmpty()){
                        mEmail.setError("Field cannot be empty");

                    }else{
                        //CALL WEB SERVICE
                        ArrayList<HashMap<String, String>> alBookPick = null;
                        //ArrayList alBookPick = new ArrayList<>();
                        alBookPick = Eksekusi(email,pass);
                        if (alBookPick.size()>0){
                            localStorage.setCustomerId(getApplicationContext(),alBookPick.get(0).get("id"));
                            localStorage.setusername(getApplicationContext(),email);
                            localStorage.setpassword(getApplicationContext(),pass);
                            Intent intent = new Intent(MainActivity.this, Menu.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public ArrayList<HashMap<String, String>> Eksekusi(String v1, String v2){
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_login update_book = new callback_login(MainActivity.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = update_book.execute(
                    v1
                    , v2
            ).get();
        }catch (Exception e){

        }

        return arrayList;
    }


}
