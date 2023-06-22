package com.shop.eagleway.utility;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.razorpay.Checkout;

import org.json.JSONObject;

public class PaymentUtility {

    private static final String TAG = "PaymentUtility";

    public void startPayment(int amount, Activity activity) {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_TanYqmbGzSnFHw");

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Shopping Cart");
            options.put("description", "Quality products at affordable price.");
            options.put("theme.color", "#1F4FE0");
            options.put("currency", "INR");
            options.put("amount", amount * 100);

            JSONObject prefill = new JSONObject();
            prefill.put("email","sheikhzs1032@gmail.com");
            prefill.put("contact","9380914768");
            options.put("prefill",prefill);

            checkout.open(activity, options);

        } catch (Exception exception) {
            Log.e(TAG, exception.getMessage());
        }
    }
}
