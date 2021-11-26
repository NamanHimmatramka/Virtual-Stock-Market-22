package com.example.vsm22;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vsm22.models.News;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class NewsAdapterRV  extends RecyclerView.Adapter<NewsAdapterRV.NewsViewHolder> {
private List<News> newsList;

    public NewsAdapterRV(List<News> newsList) {
        this.newsList = newsList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.news_itemview,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterRV.NewsViewHolder holder, int position) {
        int roundNo=newsList.get(position).getRoundNo();
        String headline=newsList.get(position).getHeadline();
        holder.Headline.setText(headline);
        holder.RoundNo.setText(roundNo+"");
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    protected class NewsViewHolder extends RecyclerView.ViewHolder {
        private TextView Headline,RoundNo ;
        public NewsViewHolder(View itemView) {
            super(itemView);
            Headline=itemView.findViewById(R.id.TV_news_heading);
            RoundNo=itemView.findViewById(R.id.TV_round_no);

        }
    }
}
