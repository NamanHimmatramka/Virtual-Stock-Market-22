package com.oculus.vsm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oculus.vsm.models.Important;
import com.oculus.vsm.models.News;
import com.oculus.vsm.models.NewsFB;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    RecyclerView recyclerView;
    List<News> newslist;
    NewsAdapterRV adapter;
    int roundNo;
    FirebaseFirestore db;
    public NewsFragment(){

    }
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
        db = FirebaseFirestore.getInstance();
        initializedata(view);
        return view;
    }

    private void initializedata(View view) {
        db.collection("information").document("values").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Important obj = documentSnapshot.toObject(Important.class);
                db.collection("news").document("round"+roundNo).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        NewsFB news = documentSnapshot.toObject(NewsFB.class);
                        if(obj.day==1){
                            newslist=new ArrayList<>();
                                newslist.clear();

                                newslist.add(new News(news.news1,roundNo));
                                newslist.add(new News(news.news2,roundNo));
                                newslist.add(new News(news.news3,roundNo));
                                newslist.add(new News(news.news4,roundNo));



                            recyclerViewNews(view);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });





    }

    private void recyclerViewNews(View view) {
        recyclerView=(RecyclerView)view.findViewById(R.id.RV_news);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new NewsAdapterRV(newslist);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}