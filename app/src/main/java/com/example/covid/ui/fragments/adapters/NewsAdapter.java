package com.example.covid.ui.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid.R;
import com.example.covid.ui.fragments.models.News;
import com.example.covid.ui.fragments.utils.ClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> data;
    private ClickListener clickListener;

    public NewsAdapter(ClickListener clickListener) {
        data = new ArrayList<>();
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = data.get(position);

        holder.txtTitle.setText(news.getTitle());
        holder.txtCategory.setText(news.getName());
        Picasso.get().load(news.getImage()).into(holder.imgNew);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<News> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public News getItem(int position) {
        return data.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView txtTitle;
        private final TextView txtCategory;
        private final ImageView imgNew;
        private final ClickListener listener;

        public ViewHolder(@NonNull View itemView, ClickListener listener) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtCategory = itemView.findViewById(R.id.txt_category);
            imgNew = itemView.findViewById(R.id.img_news);

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.listener.onItemClick(getAdapterPosition());
        }
    }
}
