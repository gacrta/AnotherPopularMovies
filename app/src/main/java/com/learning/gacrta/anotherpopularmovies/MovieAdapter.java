package com.learning.gacrta.anotherpopularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.learning.gacrta.anotherpopularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import com.learning.gacrta.anotherpopularmovies.utilities.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    public interface MovieGridClickListener {
        void onMovieGridClick(int clickedMovieIndex);
    }

    private Movie[] mMovies;
    private final String TAG;
    private final MovieGridClickListener mClickListener;

    public MovieAdapter(MovieGridClickListener listener) {
         TAG = this.toString();
         mClickListener = listener;
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
        return new MovieViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Log.d(TAG, "Binding movie " + Integer.toString(position));
        holder.bind(position);
    }

    public void setMoviesData(Movie[] movies) {
        mMovies = movies;
        Log.d(TAG, Integer.toString(mMovies.length) + " movies added");
        notifyDataSetChanged();
    }

    public Movie getMovieData(int position){
        return mMovies[position];
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImageView;
        private final Context parentContext;

        MovieViewHolder(Context context, View itemView){
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_movie_poster);
            parentContext = context;
            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            Picasso.with(parentContext)
                    .load(NetworkUtils.buildUrlForMoviePoster(mMovies[position].getPosterPath(),
                            NetworkUtils.THUMBNAIL_SIZE).toString()).into(mImageView);
            Log.d(TAG, "Fetching poster from " + NetworkUtils.buildUrlForMoviePoster(mMovies[position].getPosterPath(),
                    NetworkUtils.POSTER_SIZE).toString());
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mClickListener.onMovieGridClick(clickedPosition);
        }
    }
}
