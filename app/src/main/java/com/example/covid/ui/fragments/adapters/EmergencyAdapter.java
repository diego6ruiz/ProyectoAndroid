package com.example.covid.ui.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid.R;
import com.example.covid.ui.fragments.models.Emergency;
import com.example.covid.ui.fragments.utils.ClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EmergencyAdapter extends RecyclerView.Adapter<EmergencyAdapter.ViewHolder> {
    //lista para almacenar la data
    private List<Emergency> data;
    private final ClickListener clickListener;

    //constructor, se instancia la data como una lista de array
    public EmergencyAdapter(ClickListener clickListener) {
        data = new ArrayList<>();
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public EmergencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emergencies, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EmergencyAdapter.ViewHolder holder, int position) {
        //variable de tipo Emergency y se asigna la data obteniendo la posicion
        Emergency emergency = data.get(position);
        //accede a la data por medio de la vista y mete la informacion en la variable del modelo
        holder.txtTitle.setText(emergency.getTitle());
        holder.txtNumber.setText(emergency.getPhone());
        //obtiene el icono
        Picasso.get().load(emergency.getIcon()).into(holder.imgInstitution);
    }

    @Override
    public int getItemCount() {
        //devuelve el tamaño de la lista
        return data.size();
    }
    //metodo para meter la data en la lista
    public void setData(List<Emergency> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public Emergency getItem(int position) {
        return data.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView imgInstitution;
        private final TextView txtTitle;
        private final TextView txtNumber;
        private final ClickListener listener;

        public ViewHolder(@NonNull View itemView, ClickListener listener) {
            super(itemView);
            imgInstitution = itemView.findViewById(R.id.img_emergencies);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtNumber = itemView.findViewById(R.id.txt_number);

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.listener.onItemClick(getAdapterPosition());
        }
    }
}
