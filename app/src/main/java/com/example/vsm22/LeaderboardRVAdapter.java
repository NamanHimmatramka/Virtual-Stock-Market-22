package com.example.vsm22;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vsm22.models.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class LeaderboardRVAdapter extends RecyclerView.Adapter<LeaderboardRVViewHolder>{
    ArrayList<User> users;

    public LeaderboardRVAdapter(ArrayList<User> users){
        this.users = users;
    }
    @NonNull
    @Override
    public LeaderboardRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_holder,parent,false);
        return new LeaderboardRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardRVViewHolder holder, int position) {
        holder.rank.setText((position+1)+"");
        holder.userName.setText(users.get(position).userName);
        holder.netWorth.setText(users.get(position).netWorth+"");
    }

    @Override
    public int getItemCount() {
        return users.size();
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