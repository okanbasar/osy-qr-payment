package com.tokeninc.altay.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tokeninc.altay.R;
import com.tokeninc.altay.models.QRModel;
import com.tokeninc.altay.utilities.ConnectionUtility;
import com.tokeninc.altay.utilities.QRUtility;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PaymentEnterInfoActivity extends AppCompatActivity {

    private Button continueButton;
    private EditText amountEditText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_enter_info);

        setTitle(R.string.payment_enter_info_activity_title);

        continueButton = findViewById(R.id.enter_info_continue_button);
        amountEditText = findViewById(R.id.enter_info_amount_editText);
        progressBar = findViewById(R.id.enter_info_progressBar);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                continueButton.setClickable(false);
                String input = amountEditText.getText().toString();
                if (!input.trim().equals("") && !input.trim().equals(".") && !input.trim().equals(",")) {
                    getQRContentFromServer(Math.round(Double.parseDouble(input) * 100));
                } else {
                    progressBar.setVisibility(View.GONE);
                    continueButton.setClickable(true);
                    Toast.makeText(getApplicationContext(), "Please enter an amount!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getQRContentFromServer(long amount) {

        String content = new StringBuilder("{\"totalReceiptAmount\":").append(amount).append("}").toString();

        ConnectionUtility.post(ConnectionUtility.GET_QR_SALE_URL_STRING, content, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        continueButton.setClickable(true);
                        Toast.makeText(getApplicationContext(), "Request failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        continueButton.setClickable(true);
                    }
                });
                try {

                    String s = response.body().string();
                    QRModel qr = QRUtility.parseQRContent(PaymentEnterInfoActivity.this, (new JSONObject(s)).getString("QRdata"));

                    Intent intent = new Intent(PaymentEnterInfoActivity.this, PaymentConfirmActivity.class);
                    intent.putExtra("qr", qr.getCompleteQRcontent());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
