package com.tokeninc.altay.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tokeninc.altay.models.QRModel;
import com.tokeninc.altay.utilities.QRUtility;
import com.tokeninc.altay.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Response;

public class PaymentTypeSelectionActivity extends AppCompatActivity {

    private static final int OPEN_DOCUMENT_REQUEST_CODE = 100;

    private Button readQRContentFromServerButton;
    private Button readQRContentFromFileSystemButton;

    private Response response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setTitle(R.string.payment_type_selection_activity_title);

        readQRContentFromServerButton = findViewById(R.id.readQRContentFromServerButton);
        readQRContentFromFileSystemButton = findViewById(R.id.readQRContentFromFileSystemButton);

        readQRContentFromServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentTypeSelectionActivity.this, PaymentEnterInfoActivity.class);
                startActivity(intent);
            }
        });

        readQRContentFromFileSystemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readQRContentFromServerButton.setClickable(false);
                readQRContentFromFileSystemButton.setClickable(false);
                readQRCodeFromTxtFile(v);
            }
        });
    }

    public void readQRCodeFromTxtFile(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        startActivityForResult(intent, OPEN_DOCUMENT_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        readQRContentFromServerButton.setClickable(true);
        readQRContentFromFileSystemButton.setClickable(true);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == OPEN_DOCUMENT_REQUEST_CODE) {
                if (resultData != null) {
                    Uri currentUri = resultData.getData();

                    try {
                        String content = readQRContentFromFileSystem(currentUri);

                        QRModel qr = QRUtility.parseQRContent(PaymentTypeSelectionActivity.this, (new JSONObject(content)).getString("QRdata"));

                        Intent intent = new Intent(PaymentTypeSelectionActivity.this, PaymentConfirmActivity.class);
                        intent.putExtra("qr", qr.getCompleteQRcontent());
                        startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String readQRContentFromFileSystem(Uri uri) throws IOException {

        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            stringBuilder.append(currentLine + "\n");
        }
        if (inputStream != null) {
            inputStream.close();
        }
        return stringBuilder.toString();
    }






}
