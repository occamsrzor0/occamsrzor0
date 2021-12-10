package com.example.realestatesearchapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Property> data;

    Adapter(Context context, List<Property> data ) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_view, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String location = data.get(position).getLocation();
        String p_type = data.get(position).getProperty_type().display();
        String l_type = data.get(position).getListing_type().display();
        Integer n_bedroom = data.get(position).getNumber_of_bedroom();
        Integer price = data.get(position).getPrice();
        holder.textTitle.setText("Property Location : "+location+'\n'+"Property Type : "+p_type+'\n'+"Bedroom : " + n_bedroom+'\n'+"Listing Type : " + l_type);
        holder.textDesc.setText("$ " +price);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(),MainActivity2.class);
                    i.putExtra("title", data.get(getAdapterPosition()).display_in_ui());
                    view.getContext().startActivity(i);
                }
            });

            textTitle = itemView.findViewById(R.id.textView5);
            textDesc = itemView.findViewById(R.id.textView6);
        }
    }
}
