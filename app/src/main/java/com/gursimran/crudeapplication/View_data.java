package com.gursimran.crudeapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class View_data extends AppCompatActivity
{
    TextView id,name,contact,address,email;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        id=findViewById(R.id.textview_id);
        name=findViewById(R.id.textview_name);
        contact=findViewById(R.id.textview_contact);
        address=findViewById(R.id.textview_address);
        email=findViewById(R.id.textview_email);
        Intent intent=getIntent();
        position= intent.getExtras().getInt("position");
        id.setText(home_page.arrayList.get(position).getId());
        name.setText(home_page.arrayList.get(position).getName());
        contact.setText(home_page.arrayList.get(position).getContact());
        address.setText(home_page.arrayList.get(position).getAddress());
        email.setText(home_page.arrayList.get(position).getEmail());
    }
}