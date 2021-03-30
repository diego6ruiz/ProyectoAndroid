package com.example.covid.ui.fragments.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid.R;
import com.example.covid.ui.fragments.activities.InformationActivity;
import com.example.covid.ui.fragments.adapters.NewsAdapter;
import com.example.covid.ui.fragments.models.Emergency;
import com.example.covid.ui.fragments.models.News;
import com.example.covid.ui.fragments.services.ApiAdapter;
import com.example.covid.ui.fragments.utils.ClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {

    public NewsAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        adapter = new NewsAdapter(new ClickListener() {
            @Override
            public void onItemClick(int position) {
                News news = adapter.getItem(position);
                Intent intent = new Intent(getContext(), InformationActivity.class);
                intent.putExtra("news", news);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.list_news);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ApiAdapter.getInstance().getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                adapter.setData(response.body());
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }
}