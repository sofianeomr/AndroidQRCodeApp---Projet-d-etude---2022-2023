package com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class Login extends AppCompatActivity {

    EditText login,passwd;
    String lien="192.108.10.4/lpro/api/login.php?key=iot1235&username=Kevin&password=11044";

    Button connexion;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login_connexion);
        connexion=findViewById(R.id.connexion);
        passwd=findViewById(R.id.passwd_connexion);
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lien="http://192.168.10.2/lpro/api/login.php?key=iot1235&username="+login.getText().toString()+"&password="+passwd.getText().toString();





                StringRequest stringRequest = new StringRequest(Request.Method.POST, lien, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray obj = null;
                        try {
                            obj = new JSONArray(response);
                            Toast toast = Toast.makeText(Login.this, "response "+response, Toast.LENGTH_LONG);

                            String [] activite=new String[obj.length()];
                            for (int i =0;i< obj.length();i++){
                                System.out.println("obj get "+obj.getString(i));
                                activite[i] =obj.getString(i);
                                activite[i] = deleteChar(activite[i]);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error "+error);
                    }


                })

                ;
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);


                //Intent i=new Intent(Login.this,MainActivity.class);
                //startActivity(i);
            }
        });




    }

    public String deleteChar(String activite){
        activite = activite.replaceAll("[ +^\" ]","");
        activite = activite.replaceAll("\\\\", "");
        activite = activite.replaceAll("\\[","");
        activite = activite.replaceAll("]",",");
        activite = activite.replaceAll(","," ");

        return activite;
    }
}