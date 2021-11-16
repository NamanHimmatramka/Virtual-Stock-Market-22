package com.example.vsm22.daos;

import com.example.vsm22.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDao {
    FirebaseFirestore db  = FirebaseFirestore.getInstance();
    CollectionReference userCollection = db.collection("users");
    public void addUser(User user){
        userCollection.document(user.uid).set(user);
    }

}
