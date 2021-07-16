package com.gursimran.crudeapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText name,email,password;
    String str_name,str_email,str_password;
    String url="https://gursimranmysql.000webhostapp.com/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.ed_username);
        email=findViewById(R.id.ed_email);
        password=findViewById(R.id.ed_password);


    }

    public void moveToLogin(View view) {
        startActivity(new Intent(getApplicationContext(),login_activity.class));
         overridePendingTransition(R.anim.left_slide_out,R.anim.right_slide_in);
        finish();
    }

    public void Register(View view) {
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading data");
        progressDialog.show();
        if(name.getText().toString().equals("")){
            progressDialog.dismiss();
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
        }
        else if(email.getText().toString().equals("")){
            progressDialog.dismiss();
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
        }
        else if(password.getText().toString().equals("")){
            progressDialog.dismiss();
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
        }
        else{
              str_name=name.getText().toString().trim();
              str_email=email.getText().toString().trim();
              str_password=password.getText().toString().trim();
            startActivity(new Intent(getApplicationContext(),login_activity.class));
            overridePendingTransition(R.anim.left_slide_out,R.anim.right_slide_in);
            finish();
            StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    name.setText("");
                    email.setText("");
                    password.setText("");

                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String, String>();
                    params.put("name",str_name);
                    params.put("email",str_email);
                    params.put("password",str_password);
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(request);


        }
    }
}