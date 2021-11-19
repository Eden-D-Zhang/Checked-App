package com.example.checkedapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.checkedapp.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttp;
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

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(2, TimeUnit.MINUTES);
        builder.readTimeout(2, TimeUnit.MINUTES);
        builder.writeTimeout(2, TimeUnit.MINUTES);
        client = builder.build();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }

    private void getWebservice(){

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("test-amazon-scraper-api.p.rapidapi.com")
                .addPathSegment("search")
                .addPathSegment("gaming%20mic?")
                .addQueryParameter("api_key", "af37dabb3aed7c2ca0a8d90442c931ec")
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .addHeader("x-rapidapi-host", "test-amazon-scraper-api.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "9762313f3fmsh261831e1ac2a541p11b3d8jsna6690dad2326")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable(){
                    @Override
                            public void run() {
                        mTextViewResult.setText("Failure!");
                        e.printStackTrace();
                    }
              });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response){
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