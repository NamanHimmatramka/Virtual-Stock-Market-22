package com.example.vsm22;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

public class LeaderboardRVAdapter extends RecyclerView.Adapter<LeaderboardRVViewHolder>{
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @NonNull
    @Override
    public LeaderboardRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_holder,parent,false);
        return new LeaderboardRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardRVViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

class LeaderboardRVViewHolder extends RecyclerView.ViewHolder{

    TextView rank;
    TextView userName;
    TextView netWorth;
    public LeaderboardRVViewHolder(@NonNull View itemView) {
        super(itemView);

        rank = itemView.findViewById(R.id.rank);
        userName = itemView.findViewById(R.id.userName);
        netWorth = itemView.findViewById(R.id.pointsUser);
    }
}