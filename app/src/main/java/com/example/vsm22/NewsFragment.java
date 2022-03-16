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

import com.example.vsm22.models.Important;
import com.example.vsm22.models.News;
import com.example.vsm22.models.Stock;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    RecyclerView recyclerView;
    List<News> newslist;
    NewsAdapterRV adapter;
    int roundNo;
    FirebaseFirestore db;
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
                if(obj.day==1){
                    newslist=new ArrayList<>();
                    if(roundNo==1){
                        newslist.clear();

                        newslist.add(new News(getText(R.string.News_1_1).toString(),1));
                        newslist.add(new News(getText(R.string.News_1_2).toString(),1));
                        newslist.add(new News(getText(R.string.News_1_3).toString(),1));
                        newslist.add(new News(getText(R.string.News_1_4).toString(),1));
                    }
                    else if(roundNo==2){
                        newslist.clear();
                        newslist.add(new News(getText(R.string.News_2_1).toString(),2));
                        newslist.add(new News(getText(R.string.News_2_2).toString(),2));
                        newslist.add(new News(getText(R.string.News_2_3).toString(),2));
                        newslist.add(new News(getText(R.string.News_1_1).toString(),1));
                        newslist.add(new News(getText(R.string.News_1_2).toString(),1));
                        newslist.add(new News(getText(R.string.News_1_3).toString(),1));
                        newslist.add(new News(getText(R.string.News_1_4).toString(),1));
                    }
                    else if(roundNo==3){
                        newslist.clear();
                        newslist.add(new News(getText(R.string.News_3_1).toString(),3));
                        newslist.add(new News(getText(R.string.News_3_2).toString(),3));
                        newslist.add(new News(getText(R.string.News_3_3).toString(),3));
                        newslist.add(new News(getText(R.string.News_3_4).toString(),3));
                        newslist.add(new News(getText(R.string.News_2_1).toString(),2));
                        newslist.add(new News(getText(R.string.News_2_2).toString(),2));
                        newslist.add(new News(getText(R.string.News_2_3).toString(),2));
                        newslist.add(new News(getText(R.string.News_1_1).toString(),1));
                        newslist.add(new News(getText(R.string.News_1_2).toString(),1));
                        newslist.add(new News(getText(R.string.News_1_3).toString(),1));
                        newslist.add(new News(getText(R.string.News_1_4).toString(),1));

                    }
                    else if(roundNo==4){
                        newslist.clear();
                        newslist.add(new News(getText(R.string.News_4_1).toString(),4));
                        newslist.add(new News(getText(R.string.News_4_2).toString(),4));
                        newslist.add(new News(getText(R.string.News_4_3).toString(),4));
                        newslist.add(new News(getText(R.string.News_4_4).toString(),4));
                        newslist.add(new News(getText(R.string.News_3_1).toString(),3));
                        newslist.add(new News(getText(R.string.News_3_2).toString(),3));
                        newslist.add(new News(getText(R.string.News_3_3).toString(),3));
                        newslist.add(new News(getText(R.string.News_3_4).toString(),3));
                        newslist.add(new News(getText(R.string.News_2_1).toString(),2));
                        newslist.add(new News(getText(R.string.News_2_2).toString(),2));
                        newslist.add(new News(getText(R.string.News_2_3).toString(),2));
                        newslist.add(new News(getText(R.string.News_1_1).toString(),1));
                        newslist.add(new News(getText(R.string.News_1_2).toString(),1));
                        newslist.add(new News(getText(R.string.News_1_3).toString(),1));
                        newslist.add(new News(getText(R.string.News_1_4).toString(),1));

                    }
                    else if(roundNo==5){
                        newslist.clear();
                        newslist.add(new News(getText(R.string.News_5_1).toString(),5));
                        newslist.add(new News(getText(R.string.News_5_2).toString(),5));
                        newslist.add(new News(getText(R.string.News_5_3).toString(),5));
                        newslist.add(new News(getText(R.string.News_5_4).toString(),5));
                        newslist.add(new News(getText(R.string.News_4_1).toString(),4));
                        newslist.add(new News(getText(R.string.News_4_2).toString(),4));
                        newslist.add(new News(getText(R.string.News_4_3).toString(),4));
                        newslist.add(new News(getText(R.string.News_4_4).toString(),4));
                        newslist.add(new News(getText(R.string.News_3_1).toString(),3));
                        newslist.add(new News(getText(R.string.News_3_2).toString(),3));
                        newslist.add(new News(getText(R.string.News_3_3).toString(),3));
                        newslist.add(new News(getText(R.string.News_3_4).toString(),3));
                        newslist.add(new News(getText(R.string.News_2_1).toString(),2));
                        newslist.add(new News(getText(R.string.News_2_2).toString(),2));
                        newslist.add(new News(getText(R.string.News_2_3).toString(),2));
                        newslist.add(new News(getText(R.string.News_1_1).toString(),1));
                        newslist.add(new News(getText(R.string.News_1_2).toString(),1));
                        newslist.add(new News(getText(R.string.News_1_3).toString(),1));
                        newslist.add(new News(getText(R.string.News_1_4).toString(),1));
                    }


                    recyclerViewNews(view);
                }
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