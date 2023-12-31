package com.example.coffeecare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> list;

    public MyAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public  MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry,parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user= list.get(position);
        holder.Chemicalname.setText(user.getChemicalname());
        holder.description.setText(user.getDescription());
        holder.Industry.setText(user.getIndustry());

    }

    @Override
    public int getItemCount() {
        return list.size();


    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Chemicalname, description, Industry;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Chemicalname = itemView.findViewById(R.id.Agroname );
            description = itemView.findViewById(R.id.Agrodescpt);
            Industry = itemView.findViewById(R.id.Agroindusry);
        }
    }
}








