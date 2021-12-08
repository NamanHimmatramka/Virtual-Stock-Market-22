package com.example.vsm22;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.vsm22.models.News;
import com.example.vsm22.models.Stock;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    RecyclerView recyclerView;
    List<News> newslist;
    NewsAdapterRV adapter;
    int roundNo;
    public NewsFragment(int roundNo) {
        this.roundNo = roundNo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_news, container, false);
        initializedata();
        recyclerViewNews(view);
        return view;
    }

    private void initializedata() {

        newslist=new ArrayList<>();
        newslist.add(new News("News 1",1));
        newslist.add(new News("News 2",1));
        newslist.add(new News("News 3",1));
        newslist.add(new News("News 4",1));
        newslist.add(new News("News 5",1));

    }

    private void recyclerViewNews(View view) {
        recyclerView=(RecyclerView)view.findViewById(R.id.RV_news);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new NewsAdapterRV(newslist);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}