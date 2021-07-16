package com.gursimran.crudeapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class update_data extends AppCompatActivity {
    EditText upid,upname,upemail,upcontact,upaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        upid=findViewById(R.id.upid);
        upname=findViewById(R.id.upname);
        upemail=findViewById(R.id.upemail);
        upcontact=findViewById(R.id.upcontact);
        upaddress=findViewById(R.id.upaddress);
        Intent intent=getIntent();
        int position=intent.getExtras().getInt("position");
        upid.setText(home_page.arrayList.get(position).getId());
        upname.setText(home_page.arrayList.get(position).getName());
        upemail.setText(home_page.arrayList.get(position).getEmail());
        upcontact.setText(home_page.arrayList.get(position).getContact());
        upaddress.setText(home_page.arrayList.get(position).getAddress());

    }

    public void updateData(View view) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating....");
        progressDialog.show();
        String id, name, email, contact, address;
        id = upid.getText().toString().trim();
        name = upname.getText().toString().trim();
        email = upemail.getText().toString().trim();
        contact = upcontact.getText().toString().trim();
        address = upaddress.getText().toString().trim();
        if (name.equals("")) {
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (email.equals("")) {
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (contact.equals("")) {
            Toast.makeText(this, "Enter mobile", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (address.equals("")) {
            Toast.makeText(this, "Enter address", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else
        {
            StringRequest request = new StringRequest(Request.Method.POST, "https://gursimranmysql.000webhostapp.com/update.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(update_data.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),home_page.class));
                    finish();
                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(update_data.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String, String>();
                    params.put("id",id);
                    params.put("name",name);
                    params.put("email",email);
                    params.put("contact",contact);
                    params.put("address",address);
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(request);
        }
    }
}