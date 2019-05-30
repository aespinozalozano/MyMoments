package com.fernandopretell.mymoments.feature.main;

import com.fernandopretell.mymoments.base.ui.BaseInteractor;
import com.fernandopretell.mymoments.data.remote.model.Login;
import com.fernandopretell.mymoments.data.remote.model.Place;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fernando Pretell.
 */

public class MainInteractor extends BaseInteractor implements MainContract.MainInteractor {

    public MainInteractor() {
        super();
    }


    @Override
    public void authListPost(MainContract.OnMainListener mainListener) {
        FirebaseFirestore.getInstance().collection("places").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    List<Place> placeList=new ArrayList<>();
                    if (!queryDocumentSnapshots.isEmpty()){
                        for (DocumentSnapshot snapshot:queryDocumentSnapshots)
                            placeList.add(snapshot.toObject(Place.class));

                    }

                    mainListener.onShowListPostSuccess(placeList);
                });
    }
}
