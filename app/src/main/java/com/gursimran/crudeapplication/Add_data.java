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

public class Add_data extends AppCompatActivity
{
    EditText name,email,contact,address;
    String s_name,s_email,s_contact,s_address;
    String url="https://gursimranmysql.000webhostapp.com/insert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        contact=findViewById(R.id.contact);
        address=findViewById(R.id.address);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void insData(View view)
    {

        if(name.getText().toString().equals(""))
        {
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
        }
        else if(email.getText().toString().equals(""))
        {
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
        }
        else if(contact.getText().toString().equals(""))
        {
            Toast.makeText(this, "Enter contact", Toast.LENGTH_SHORT).show();
        }
        else if(address.getText().toString().equals(""))
        {
            Toast.makeText(this, "Enter address", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            s_name=name.getText().toString().trim();
            s_email=email.getText().toString().trim();
            s_contact=contact.getText().toString().trim();
            s_address=address.getText().toString().trim();
            StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equalsIgnoreCase("Inserted successfully"))
                    {
                        name.setText("");
                        email.setText("");
                        contact.setText("");
                        address.setText("");
                        progressDialog.dismiss();
                        Toast.makeText(Add_data.this, "Inserted successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),home_page.class));
                        finish();

                    }
                    else
                    {
                        Toast.makeText(Add_data.this, "Failed", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Add_data.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String, String>();
                    params.put("name",s_name);
                    params.put("email",s_email);
                    params.put("contact",s_contact);
                    params.put("address",s_address);
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(request);

        }
    }
}