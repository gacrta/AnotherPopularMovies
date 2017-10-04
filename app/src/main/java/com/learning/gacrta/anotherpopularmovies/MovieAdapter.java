package com.learning.gacrta.anotherpopularmovies;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by gabrielc.almeida on 04/10/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private int mNumberOfItens;

    public MovieAdapter(int numberOfItens) {
        mNumberOfItens = numberOfItens;
    }

    @Override
    public int getItemCount() {
        return mNumberOfItens;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachImmediately = false;

        View view = inflater.inflate(R.layout.movie_grid_item, parent, shouldAttachImmediately);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        //TODO bind method
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;

        MovieViewHolder(View itemView){
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_movie_poster);
        }

        void bind(Drawable drawable) {
            mImageView.setImageDrawable(drawable);
        }
    }
}
