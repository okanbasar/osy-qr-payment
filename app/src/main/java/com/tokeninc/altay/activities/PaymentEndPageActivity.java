package com.tokeninc.altay.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tokeninc.altay.R;
import com.tokeninc.altay.models.QRModel;
import com.tokeninc.altay.utilities.QRUtility;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class PaymentEndPageActivity extends AppCompatActivity  implements AnimationListener {

    private Button endPageButton;
    private GifImageView confirmationGif;
    private GifDrawable gifDrawable;
    private TextView endPageTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_end_page);

        setTitle(R.string.payment_end_page_activity_title);

        endPageButton = findViewById(R.id.end_page_button);
        confirmationGif = findViewById(R.id.end_page_gif);
        endPageTextview = findViewById(R.id.end_page_textview);

        int paymentResult = getIntent().getIntExtra("result", -1);

        if (paymentResult != -1) {
            confirmationGif.setImageResource(R.drawable.onay_gif);
            endPageTextview.setText(R.string.end_page_success_message);
        } else {
            confirmationGif.setImageResource(R.drawable.error_gif);
            endPageTextview.setText(R.string.end_page_failure_message);
        }

        endPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentEndPageActivity.this, PaymentTypeSelectionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        gifDrawable = (GifDrawable) confirmationGif.getDrawable();
        gifDrawable.addAnimationListener(this);
    }

    @Override
    public void onAnimationCompleted(int loopNumber) {
        gifDrawable.stop();

    }
}
