package com.lovroludvig.newsnow.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lovroludvig.newsnow.R;
import com.lovroludvig.newsnow.entities.Article;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<Article> articles;

    public NewsAdapter(ArrayList<Article> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_layout, viewGroup, false);
        return new NewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView articleTitle = holder.itemView.findViewById(R.id.articleTitle);
        ImageView articleImage = holder.itemView.findViewById(R.id.articleImage);
        TextView articleDescription = holder.itemView.findViewById(R.id.articleDescription);
        TextView articleAuthorAndDate = holder.itemView.findViewById(R.id.articleAuthorAndDate); //add if needed... I don't like it

        final Article article = articles.get(position);
        final Context context = holder.itemView.getContext();


        if (!(article.getUrlToImage() == null)) {
            Glide.with(context).load(Uri.parse(article.getUrlToImage())).into(articleImage);
        }
        articleTitle.setText(article.getTitle());
        articleDescription.setText(article.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = article.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });
    }

    public void addArticle(Article article) {
        if (!articles.contains(article)) {
            articles.add(article);
            notifyItemInserted(articles.size());
        }
    }

    @Override
    public int getItemCount() {
        if (articles != null) {
            return articles.size();
        } else {
            return 0;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
