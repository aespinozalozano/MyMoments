package com.fernandopretell.mymoments.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fernandopretell.mymoments.R;
import com.fernandopretell.mymoments.data.remote.model.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PlacesViewHolder> {

    private final List<Place> item;
    private Context ctx;
    //private Bitmap bitmap;

    public PostsAdapter(List<Place> item, Context ctx) {
        this.item = item;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public PostsAdapter.PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post,viewGroup,false);
        return new PlacesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostsAdapter.PlacesViewHolder placeesViewHolder, int position) {



        final Place place = item.get(position);

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        ImagePagerAdapter adapter = new ImagePagerAdapter(ctx,place.getArrayPictures());
        placeesViewHolder.viewPager.setAdapter(adapter);



        SimpleDateFormat sfd = new SimpleDateFormat("dd 'de' MMMM yyyy',' HH:mm",Locale.getDefault());
        String date = sfd.format(new Date(place.getCreatedAt()));

        placeesViewHolder.fecha.setText(date);
        placeesViewHolder.textoPublicaciones.setText(place.getDescriptionPlace());






    }

    @Override
    public int getItemCount() {
        return item.size();
    }


    class PlacesViewHolder extends RecyclerView.ViewHolder{

        private TextView nombre;
        private TextView fecha;
        private TextView textoPublicaciones;
        private ViewPager viewPager;
        private ProgressBar progressBar;


        PlacesViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.tv_nombre_post);
            fecha = itemView.findViewById(R.id.tv_date_post);
            textoPublicaciones = itemView.findViewById(R.id.tvDescripcionPost);
            viewPager = itemView.findViewById(R.id.ivImagePost);
            progressBar = itemView.findViewById(R.id.pb_item_publicacion);

        }

    }


}
