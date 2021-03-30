package com.example.covid.ui.fragments.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.covid.R;
import com.example.covid.ui.fragments.adapters.NewsAdapter;
import com.example.covid.ui.fragments.models.News;
import com.squareup.picasso.Picasso;

public class InformationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("news")) {
            News news = (News) bundle.getSerializable("news");

            TextView txtTitle = findViewById(R.id.txt_title);
            TextView txtDetail = findViewById(R.id.txt_detail);
            ImageView imgNews = findViewById(R.id.img_news);

            txtTitle.setText(news.getTitle());
            txtDetail.setText(news.getDetail());
            Picasso.get().load(news.getImage()).into(imgNews);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                txtDetail.setText(Html.fromHtml(news.getDetail(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                txtDetail.setText(Html.fromHtml(news.getDetail()));
            }
        }
    }
}