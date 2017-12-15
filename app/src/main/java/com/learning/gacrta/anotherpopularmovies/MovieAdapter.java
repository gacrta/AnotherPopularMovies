package com.learning.gacrta.anotherpopularmovies;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.learning.gacrta.anotherpopularmovies.utilities.Movie;

/**
 * Created by gabrielc.almeida on 04/10/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Movie[] mMovies;

    public MovieAdapter() {
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) {
            return 0;
        } return mMovies.length;
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
        holder.bind(position);
    }

    public void setMoviesData(Movie[] movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;

        MovieViewHolder(View itemView){
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_movie_poster);
        }

        void bind(int position) {
            // TODO
            //mImageView.setImageDrawable(mMovies[position]);
        }
    }
}
