package com.example.memeshare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
//import android.graphics.drawable.Drawable;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
//import com.bumptech.glide.RequestManager;
//import com.bumptech.glide.request.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadMeme();
    }

    private void loadMeme(){
         ProgressBar progressbar = findViewById(R.id.progressBar);
         progressbar.setVisibility(View.VISIBLE);
         RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://meme-api.herokuapp.com/gimme";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        String url = null;
                        try {
                            url = response.getString("url");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ImageView imageView = findViewById(R.id.imageViewId);;
                        Glide.with(MainActivity.this).load(url).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                progressbar.setVisibility(progressbar.INVISIBLE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressbar.setVisibility(progressbar.INVISIBLE);
                                return false;
                            }
                        }).into(imageView);
                        //RequestManager manager = Glide.with(imageView);
                        //manager.load(url).into(imageView);
                    }
                    }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void shareMeme(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Hey , Checkout this meme i saw in reddit");
        startActivity(intent);
    }

    public void nextMeme(View view) {
        loadMeme();
    }
}