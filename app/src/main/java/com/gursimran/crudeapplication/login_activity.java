package com.gursimran.crudeapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class login_activity extends AppCompatActivity {
    String str_email,str_password;
    String url="https://gursimranmysql.000webhostapp.com/login.php";
    EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        email=findViewById(R.id.ed_email);
        password=findViewById(R.id.ed_password);
    }

    public void moveToRegistration(View view)
    {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        overridePendingTransition(R.anim.left_slide_in,R.anim.right_slide_out);
        finish();
    }

    public void Login(View view)
    {
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Redirecting...");
        progressDialog.show();
     if(email.getText().toString().equals(""))
     {
         Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
         progressDialog.dismiss();
     }
     else if(password.getText().toString().equals(""))
     {
         Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
         progressDialog.dismiss();
     }
     else
     {
         str_email=email.getText().toString().trim();
         str_password=password.getText().toString().trim();
         StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                 if(response.equalsIgnoreCase("login successfully")){
                     email.setText("");
                     password.setText("");
                     progressDialog.dismiss();
                     Toast.makeText(login_activity.this, response, Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(getApplicationContext(),home_page.class));
                     finish();
                 }
                else{
                     Toast.makeText(login_activity.this, "user not found", Toast.LENGTH_SHORT).show();
                     progressDialog.dismiss();
                 }

             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Toast.makeText(login_activity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                 progressDialog.dismiss();
             }
         }){
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 Map<String,String> params=new HashMap<String, String>();
                 params.put("email",str_email);
                 params.put("password",str_password);
                 return params;
             }
         };
         RequestQueue requestQueue= Volley.newRequestQueue(this);
         requestQueue.add(request);
     }
    }
}