package com.hkbuild.FitApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


public class bmrCalc extends AppCompatActivity {

    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    EditText sex, weight, height, age;
    TextView resultText;
    private Button back, calculate;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmr_calc);

        sex = findViewById(R.id.sex);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        age = findViewById(R.id.age);
        resultText = findViewById(R.id.result);
        calculate = findViewById(R.id.calculate);
        back = findViewById(R.id.backButton);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        loadAd();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(bmrCalc.this, HomeActivity.class);
                bmrCalc.this.startActivity(i);
            }
        });


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateBMR();
            }

            private void calculateBMR() {
                String s1 = sex.getText().toString();
                String s2 = weight.getText().toString();
                String s3 = height.getText().toString();
                String s4 = age.getText().toString();

                double weightValue = Double.parseDouble(s2);
                double heightValue = Double.parseDouble(s3);
                double ageValue = Double.parseDouble(s4);
                double bmr = 0;

                if (s1.isEmpty() || s2.isEmpty() || s3.isEmpty() || s4.isEmpty()) {
                    Toast.makeText(bmrCalc.this, "Please enter details.", Toast.LENGTH_SHORT).show();
                } else {
                    if (s1.equalsIgnoreCase("m") || s1.equalsIgnoreCase("male")) {
                        bmr = (5 + (10 * weightValue) + (6.25 * heightValue) - (5 * ageValue));
                    } else if (s1.equalsIgnoreCase("f") || s1.equalsIgnoreCase("female")) {
                        bmr = (161 - (10 * weightValue) + (6.25 * heightValue) - (5 * ageValue));
                    } else {
                        Toast.makeText(bmrCalc.this, "Please enter valid details.", Toast.LENGTH_SHORT).show();
                    }
                    showInterstitial();
                    String resultBMR = String.format("%.0f", bmr);
                    resultText.setText(resultBMR);
                }
            }
        });
    }


    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, AD_UNIT_ID, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                bmrCalc.this.interstitialAd = interstitialAd;
                interstitialAd.setFullScreenContentCallback(
                        new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                bmrCalc.this.interstitialAd = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                bmrCalc.this.interstitialAd = null;
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                            }
                        });
            }
        });
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null) {
            interstitialAd.show(this);
        }
    }
}
