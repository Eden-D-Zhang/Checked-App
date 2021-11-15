package com.example.checkedapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestActivity extends AppCompatActivity {

    private Button getBtn;
    private TextView mTextViewResult;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        mTextViewResult = (TextView) findViewById(R.id.text_view_result);
        getBtn = (Button) findViewById(R.id.getBtn);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWebservice();
            }
        });
        client = new OkHttpClient();
    }

    private void getWebservice(){

        final Request request = new Request.Builder().url("https://amazon-data-product-scraper.p.rapidapi.com/search/macbook%20air%20m1?api_key=548851825ac43f460f8ec20f2c8ab823;").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable(){
                    @Override
                            public void run() {
                        mTextViewResult.setText("Failure!");
                    }
            });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mTextViewResult.setText(response.body().string());
                            } catch (IOException ioe) {
                                mTextViewResult.setText("Error during get body.");
                            }
                        }
                    });
                }

        });

    }
}