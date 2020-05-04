package android.example.mocar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Review_Activity extends AppCompatActivity {
    private TextView ambilRating;
    private Button submit;
    private AppCompatRatingBar RatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_);

        ambilRating = (TextView)findViewById(R.id.rate);
        submit = findViewById(R.id.submit);
        RatingBar = findViewById(R.id.penilaian);

        RatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

            @Override
            public void onRatingChanged(RatingBar ratingBar, float nilai, boolean b) {
                ambilRating.setText("Rating : "+nilai);
            }
        });

        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(Review_Activity.this, "Rating dari Anda adalah :" +RatingBar, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
