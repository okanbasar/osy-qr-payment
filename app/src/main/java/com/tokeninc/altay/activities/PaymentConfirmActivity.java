package com.tokeninc.altay.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tokeninc.altay.R;
import com.tokeninc.altay.models.QRModel;
import com.tokeninc.altay.utilities.ConnectionUtility;
import com.tokeninc.altay.utilities.CurrencyUtility;
import com.tokeninc.altay.utilities.QRUtility;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PaymentConfirmActivity extends AppCompatActivity {

    private Button confirmButton;
    private TextView amountValueTextView;
    private ProgressBar progressBar;

    private QRModel qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirm);

        setTitle(R.string.payment_confirm_activity_title);

        confirmButton = findViewById(R.id.confirm_button);
        amountValueTextView = findViewById(R.id.confirm_amount_value);
        progressBar = findViewById(R.id.confirm_progressBar);

        String qrContent = getIntent().getStringExtra("qr");
        qr = QRUtility.parseQRContent(PaymentConfirmActivity.this, qrContent);

        String formattedAmount = String.format(Locale.US, "%.2f", ((double)qr.getTransactionAmount() / 100)) + " " + CurrencyUtility.getCurrencyCode(qr.getTransactionCurrency());

        amountValueTextView.setText(formattedAmount);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                confirmButton.setClickable(false);
                makePayment();
            }
        });
    }

    private void makePayment() {

        String content = new StringBuilder("{\"returnCode\":1000,\"returnDesc\":\"success\"," +
                "\"receiptMsgCustomer\":\"beko Campaign\",\"receiptMsgMerchant\":\"beko Campaign Merchant\"," +
                "\"paymentInfoList\":[{\"paymentProcessorID\":67,\"paymentActionList\":[{\"paymentType\":3," +
                "\"amount\":").append(qr.getTransactionAmount())
                .append(",\"currencyID\":").append(qr.getTransactionCurrency())
                .append(",\"vatRate\":").append(qr.getVatStr().split("-")[0])
                .append("}]}],\"QRdata\":\"").append(qr.getCompleteQRcontent())
                .append("\"}").toString();

        ConnectionUtility.post(ConnectionUtility.PAYMENT_URL_STRING, content, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        confirmButton.setClickable(true);
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
                        confirmButton.setClickable(true);
                    }
                });
                try {
                    Intent intent = new Intent(PaymentConfirmActivity.this, PaymentEndPageActivity.class);
                    intent.putExtra("result", 0);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
