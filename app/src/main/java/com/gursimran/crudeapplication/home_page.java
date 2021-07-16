package com.gursimran.crudeapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class home_page extends AppCompatActivity {
        RecyclerView recyclerView;
        public  static ArrayList<Model> arrayList=new ArrayList<>();
        MyAdapter adapter=new MyAdapter(arrayList,this);
        Model model;
        String url="https://gursimranmysql.000webhostapp.com/retrivedata.php";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        recyclerView=findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        retriveData();

    }
    public void retriveData()
    {
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arrayList.clear();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.getString("success");
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    if(success.equals("1"))
                    {
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject1= jsonArray.getJSONObject(i);
                            String id=jsonObject1.getString("id");
                            String name=jsonObject1.getString("name");
                            String email=jsonObject1.getString("email");
                            String contact=jsonObject1.getString("contact");
                            String address=jsonObject1.getString("address");
                            model=new Model(id,name,email,contact,address);
                            arrayList.add(model);
                            adapter.notifyDataSetChanged();

                        }
                    }

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(home_page.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void addActivity(View view)
    {
        startActivity(new Intent(getApplicationContext(),Add_data.class));
    }
}