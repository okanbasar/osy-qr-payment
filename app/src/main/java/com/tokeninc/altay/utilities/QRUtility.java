package com.tokeninc.altay.utilities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tokeninc.altay.models.QRModel;

/**
 * Created by Okan Engin Başar on 10.05.2019.
 */
public class QRUtility {

    public static QRModel parseQRContent(Context context, String qrContent) {
        QRModel qr = new QRModel();
        qr.setCompleteQRcontent(qrContent);

        int tagSize = 2;
        int lengthSize = 2;
        int valueSize;
        int offset;

        String tag;
        String value;

        try {
            char[] arr = qrContent.toCharArray();

            for (int i = 0; i < qrContent.length(); i += offset) {
                offset = 0;
                tag = new StringBuilder().append(arr, i, tagSize).toString();
                valueSize = Integer.parseInt(new StringBuilder().append(arr, i + tagSize, lengthSize).toString());
                value = new StringBuilder().append(arr, i + tagSize + lengthSize, valueSize).toString();
                offset = tagSize + lengthSize + valueSize;

                switch (tag) {
                    case "00": // Payload Format Indicator
                        qr.setPayloadFormatIndicator(value);
                        break;
                    case "53": // Transaction Currency
                        qr.setTransactionCurrency(Integer.parseInt(value));
                        break;
                    case "54": // Transaction Amount
                        qr.setTransactionAmount(Integer.parseInt(value));
                        break;
                    case "80": // Arçelik QR Version
                        qr.setArcelikQRVersion(value);
                        break;
                    case "81": // Transaction Type
                        qr.setTransactionType(value);
                        break;
                    case "82": // Receipt Datetime
                        qr.setReceiptDatetime(value);
                        break;
                    case "83": // Receipt ID
                        qr.setReceiptID(value);
                        break;
                    case "84": // SessionID
                        qr.setSessionID(value);
                        break;
                    case "86": // vatStr
                        qr.setVatStr(value);
                        break;
                    case "87": // posID
                        qr.setPosID(value);
                        break;
                    case "88": // Secure QR Signature
                        qr.setSecureQRSignature(value);
                        break;
                    case "89": // Batch Number
                        qr.setBatchNumber(Integer.parseInt(value));
                        break;
                    default:   // UNDEFINED QR TAG !!!
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context.getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        return qr;
    }
}
