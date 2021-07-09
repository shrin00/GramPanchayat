package com.example.gramlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class Payments extends AppCompatActivity implements PaymentResultListener {

    String fees;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        Checkout.preload(getApplicationContext());

        Intent intent = getIntent();
        fees = intent.getStringExtra("fees");
        String email = intent.getStringExtra("email");
        String contactno = intent.getStringExtra("contactno");


        startPayment(fees, email, contactno);

    }

    public void startPayment(String fees, String email, String contactno) {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_GsyOckxAqsv9v5");
        checkout.setImage(R.drawable.kg);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "GRAM PANCHAYAT");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", fees+"00");//pass amount in currency subunits
            options.put("prefill.email", email);
            options.put("prefill.contact",contactno);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("key", s);
        resultIntent.putExtra("fees", fees);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("key", s);
        setResult(RESULT_CANCELED, resultIntent);
        finish();
    }
}

