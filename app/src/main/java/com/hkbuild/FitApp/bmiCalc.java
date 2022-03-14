package com.hkbuild.FitApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class bmiCalc extends AppCompatActivity {
    EditText weight, height;
    TextView resultText;
    String calculation, bmiResult;
    private Button back, calculate;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calc);

        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        resultText = findViewById(R.id.result);
        back = findViewById(R.id.backButton);
        calculate = findViewById(R.id.calculate);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = new AdView(this);

        mAdView.setAdSize(AdSize.BANNER);

        mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        calculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calculateBMI();
            }

            private void calculateBMI() {
                String s1 = weight.getText().toString();
                String s2 = height.getText().toString();

                float weightValue = Float.parseFloat(s1);
                float heightValue = Float.parseFloat(s2) / 100;
                float bmi = weightValue / (heightValue * heightValue);

                if (s1.isEmpty() || s2.isEmpty()) {
                    Toast.makeText(bmiCalc.this, "Please enter details.", Toast.LENGTH_SHORT).show();
                } else {
                    if (bmi < 18.5) {
                        bmiResult = "Under Weight \n Recommended to increase calorie intake.";
                    } else if (bmi >= 18.5 && bmi < 25) {
                        bmiResult = "Normal Weight";
                    } else if (bmi >= 25 && bmi < 30) {
                        bmiResult = "Overweight\n Recommended to decrease calorie intake.";
                    } else if (bmi >= 30 && bmi < 35) {
                        bmiResult = "Class 1 Obesity \n Recommended to decrease calorie intake.";
                    } else if (bmi >= 35 && bmi < 40) {
                        bmiResult = "Class 2 Obesity \n Recommended to decrease calorie intake.";
                    } else if (bmi >= 40) {
                        bmiResult = "Class 3 Obesity \n Recommended to decrease calorie intake.";
                    }
                    String resultBMI = String.format("%.0f", bmi);
                    calculation = "Result:\n\n" + resultBMI + "\n" + bmiResult;
                    resultText.setText(calculation);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(bmiCalc.this, HomeActivity.class);
                bmiCalc.this.startActivity(i);
            }
        });
    }
}


