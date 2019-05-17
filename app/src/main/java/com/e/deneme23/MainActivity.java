package com.e.deneme23;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
     TextView tv;
     RequestQueue mQueue;
    Integer deger=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView) findViewById(R.id.textView);
        tv.setMovementMethod(new ScrollingMovementMethod());
        mQueue= Volley.newRequestQueue(this);
        Button btn=(Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sayfaSayisiGetir();
                for (int i=1;i<=deger;i++){
                getir(i);
                }
            }
        });
    }
    public void sayfaSayisiGetir(){

        String url="https://jsontestdata.com/api/oyuncu/";
        JsonObjectRequest oyuncu=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {


                    @Override
            public void onResponse(JSONObject parametre) {

                try {

                    String sayfaSayisi=parametre.getString("toplamSayfa");
                   deger= Integer.parseInt(sayfaSayisi);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

    }
    public void getir(int a){
        String url="https://jsontestdata.com/api/oyuncu/?page="+a;
    JsonObjectRequest oyuncu=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject parametre) {

            try {
                JSONArray liste= parametre.getJSONArray("liste");
                for (int i=0;i<=liste.length();i++) {
                JSONObject eleman=liste.getJSONObject(i);
                CharSequence ad= eleman.getString("ad");
                CharSequence soyad=eleman.getString("soyad");
                tv.append(String.valueOf(ad) +" "+ String.valueOf(soyad) +"\n\n");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
        }
    });
    mQueue.add(oyuncu);
    }

}
