package com.example.coffeecare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class My2ndAdapter extends RecyclerView.Adapter<com.example.coffeecare.MyAdapter.MyViewHolder> {

        Context context;
        ArrayList<Agrochemist> list;

        public My2ndAdapter(Context context, ArrayList<Agrochemist> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public com.example.coffeecare.MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.userentrylone,parent, false);
            return new com.example.coffeecare.MyAdapter.MyViewHolder(v);

        }


        @Override
        public void onBindViewHolder(@NonNull com.example.coffeecare.MyAdapter.MyViewHolder holder, int position) {
            Agrochemist agrochemist= list.get(position);
            holder.Chemicalname.setText(agrochemist.getChemicalname());
            holder.description.setText(agrochemist.getDescription());
            holder.Industry.setText(agrochemist.getIndustry());

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




