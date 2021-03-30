package com.example.covid.ui.fragments.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid.R;
import com.example.covid.ui.fragments.adapters.EmergencyAdapter;
import com.example.covid.ui.fragments.models.Emergency;
import com.example.covid.ui.fragments.responses.EmergencyResponse;
import com.example.covid.ui.fragments.services.ApiAdapter;
import com.example.covid.ui.fragments.utils.ClickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmergenciesFragment extends Fragment {
    //variable tipo adapter
    public EmergencyAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //variable tipo view y se le asigna el inflater
        View view = inflater.inflate(R.layout.fragment_emergencies, container, false);
        //instancia del adaptador
        adapter = new EmergencyAdapter(new ClickListener() {
            @Override
            public void onItemClick(int position) {
                Emergency emergency = adapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + emergency.getPhone()));
                startActivity(intent);
            }
        });

        //instancia del recyclerView
        //variable tipo recycler y se le asigna la lista del layout a mostar
        RecyclerView recyclerView = view.findViewById(R.id.list_emergencies);
        //layout manager, se le asigna orientacion
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //mete el adaptador al recycler
        recyclerView.setAdapter(adapter);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //llama al metodo singleton y consumo del API
        //el callback es el consumo del API
        ApiAdapter.getInstance().getEmergencies().enqueue(new Callback<EmergencyResponse>() {
            @Override
            public void onResponse(Call<EmergencyResponse> call, Response<EmergencyResponse> response) {
                //mete la data al adaptador y a la respuesta
                adapter.setData(response.body().getData());
            }

            @Override
            public void onFailure(Call<EmergencyResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}