package com.gursimran.crudeapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
    List<Model> arrayList;
    Context context;

    public MyAdapter(List<Model> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Model temp=arrayList.get(position);
        holder.id.setText(arrayList.get(position).getId());
        holder.name.setText(arrayList.get(position).getName());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.name.getContext());
                ProgressDialog progressDialog=new ProgressDialog(holder.name.getContext());
                CharSequence[] dialogItem={"View Data","Edit Data","Delete Data"};
                builder.setTitle(arrayList.get(position).getName());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which)
                        {
                            case 0:
                              Intent intent=new Intent(context,View_data.class);
                               intent.putExtra("position",position);
                              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                              context.startActivity(intent);

                                break;
                                case 1:
                                    Intent intent1=new Intent(context,update_data.class);
                                    intent1.putExtra("position",position);
                                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent1);

                                    break;
                                case 2:
                                    deleteData(arrayList.get(position).getId());



                        }

                    }
                });
                builder.create().show();

            }
        });
    }

    private void deleteData(final String id) {
        StringRequest request=new StringRequest(Request.Method.POST, "https://gursimranmysql.000webhostapp.com/delete.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("Data Deleted"))
                {
                    Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<String, String>();
                parms.put("id", id);
                return  parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
class ViewHolder extends RecyclerView.ViewHolder
{
    TextView name,id;
    public ViewHolder(@NonNull View itemView)
    {

        super(itemView);
        id=itemView.findViewById(R.id.idtext);
        name=itemView.findViewById(R.id.nametext);

    }
}
