package com.hkbuild.FitApp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.ArrayList;
import java.util.List;

public class PaywallActivity extends AppCompatActivity {

    private Button annualPay;
    private Button quarterlyPay;
    private Button monthlyPay;
    private Button back;
    BillingClient billingClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paywall);

        billingClient = BillingClient.newBuilder(this)
                .enablePendingPurchases()
                .setListener(
                        new PurchasesUpdatedListener() {
                            @Override
                            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
                                if(billingResult.getResponseCode()==BillingClient.BillingResponseCode.OK && list !=null) {
                                    for (Purchase purchase: list){
                                        verifySubPurchase(purchase);
                                    }
                                }
                            }

                            private void verifySubPurchase(Purchase purchase) {
                            }
                        }
                ).build();

        establishConnection();


        annualPay = findViewById(R.id.annualPay);
        quarterlyPay = findViewById(R.id.quarterlyPay);
        monthlyPay = findViewById(R.id.monthlyPay);
        back = findViewById(R.id.backButton);


        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaywallActivity.this, RegisterActivity.class);
                PaywallActivity.this.startActivity(i);
            }
        });
    }

    private void establishConnection() {

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
                establishConnection();
            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    showProducts();
                }
            }

            private void showProducts() {
                List<String> skuList = new ArrayList<>();
                skuList.add("sub_annual");
                skuList.add("sub_quarterly");
                skuList.add("sub_monthly");

                SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);
                billingClient.querySkuDetailsAsync(params.build(),
                        new SkuDetailsResponseListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onSkuDetailsResponse(@NonNull BillingResult billingResult,
                                                             List<SkuDetails> skuDetailsList) {
                                if (billingResult.getResponseCode() == BillingClient
                                        .BillingResponseCode.OK && skuDetailsList != null) {
                                    for (SkuDetails skuDetails : skuDetailsList) {
                                        if (skuDetails.getSku().equals("sub_annual")) {
                                            annualPay.setText(skuDetails.getPrice() + " Per Year");
                                            annualPay.setOnClickListener(view -> {
                                                launchPurchaseFlow(skuDetails);
                                            });
                                        } else if (skuDetails.getSku().equals("sub_quarterly")) {
                                            quarterlyPay.setText(skuDetails.getPrice() + " Per Quarter");
                                            quarterlyPay.setOnClickListener(view -> {
                                                launchPurchaseFlow(skuDetails);
                                            });

                                        } else if (skuDetails.getSku().equals("sub_monthly")) {
                                            monthlyPay.setText(skuDetails.getPrice() + " Per Month");
                                            monthlyPay.setOnClickListener(view -> {
                                                launchPurchaseFlow(skuDetails);
                                            });
                                        }
                                    }
                                }
                            }

                            private void launchPurchaseFlow(SkuDetails skuDetails) {
                                BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                                        .setSkuDetails(skuDetails)
                                        .build();
                                billingClient.launchBillingFlow(PaywallActivity.this, billingFlowParams);
                            }
                        });
            }
        });
    }
}
