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

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    public interface MovieGridClickListener {
        void onMovieGridClick(int clickedMovieIndex);
    }

    private ArrayList<Movie> mMovies;
    private final String TAG;
    private final MovieGridClickListener mClickListener;

    public MovieAdapter(MovieGridClickListener listener) {
        mMovies = new ArrayList<>();
        TAG = this.toString();
        mClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return (mMovies != null) ? mMovies.size() : 0;
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

    public void setMoviesData(List<Movie> movies) {
        mMovies.addAll(movies);
        Log.d(TAG, Integer.toString(mMovies.size()) + " movies added");
        notifyDataSetChanged();
    }

    public Movie getMovieData(int position){
        return mMovies.get(position);
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
                    .load(NetworkUtils.buildUrlForMoviePoster(mMovies.get(position).getPosterPath(),
                            NetworkUtils.THUMBNAIL_SIZE).toString()).into(mImageView);
            Log.d(TAG, "Fetching poster from " + NetworkUtils.buildUrlForMoviePoster(mMovies.get(position).getPosterPath(),
                    NetworkUtils.POSTER_SIZE).toString());
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mClickListener.onMovieGridClick(clickedPosition);
        }
    }
}
