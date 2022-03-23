package com.oculus.vsm.daos;

import com.oculus.vsm.models.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDao {
    FirebaseFirestore db  = FirebaseFirestore.getInstance();
    CollectionReference userCollection = db.collection("users");
    public void addUser(User user){
        userCollection.document(user.uid).set(user);
    }

}
