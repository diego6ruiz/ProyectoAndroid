package com.example.covid.ui.fragments.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.covid.R;
import com.example.covid.ui.fragments.models.Statistic;
import com.example.covid.ui.fragments.services.ApiAdapter;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticsFragment extends Fragment {
    private TextView txtCountry;
    private TextView txtConfirmed;
    private TextView txtDeaths;
    private TextView txtRecovered;
    private PieChart pieChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        txtCountry = view.findViewById(R.id.txt_country);
        txtConfirmed = view.findViewById(R.id.txt_number_confirmed);
        txtDeaths = view.findViewById(R.id.txt_number_deaths);
        txtRecovered = view.findViewById(R.id.txt_number_recovered);
        pieChart = view.findViewById(R.id.piechart);

        setData();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ApiAdapter.getInstance().getStatistics().enqueue(new Callback<Statistic>() {
            @Override
            public void onResponse(Call<Statistic> call, Response<Statistic> response) {

                txtCountry.setText(response.body().getCountry());
                txtConfirmed.setText(response.body().getConfirmed().toString());
                txtDeaths.setText(response.body().getDeaths().toString());
                txtRecovered.setText(response.body().getRecovered().toString());
            }

            @Override
            public void onFailure(Call<Statistic> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setData()
    {
        txtConfirmed.setText(Integer.toString(193556));
        txtRecovered.setText(Integer.toString(175896));
        txtDeaths.setText(Integer.toString(5));

        pieChart.addPieSlice(
                new PieModel(
                        "Confirmed",
                        Integer.parseInt(txtConfirmed.getText().toString()),
                        Color.parseColor("#B71C1C")));
        pieChart.addPieSlice(
                new PieModel(
                        "Dead",
                        Integer.parseInt(txtDeaths.getText().toString()),
                        Color.parseColor("#000000")));
        pieChart.addPieSlice(
                new PieModel(
                        "Recovered",
                        Integer.parseInt(txtRecovered.getText().toString()),
                        Color.parseColor("#64DD17")));

        pieChart.startAnimation();
    }
}