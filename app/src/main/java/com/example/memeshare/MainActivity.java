package com.example.memeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

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
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://meme-api.herokuapp.com/gimme";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        String url = null;
                        try {
                            url = response.getString("url");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ImageView imageView = findViewById(R.id.imageViewId);
                        //Glide.with(MainActivity.this).load(url).into(imageView);
                        RequestManager manager = Glide.with(imageView);
                        manager.load(url).into(imageView);
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

    }

    public void nextMeme(View view) {
    }
}