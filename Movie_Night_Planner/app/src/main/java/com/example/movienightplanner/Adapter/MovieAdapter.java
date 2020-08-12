package com.example.movienightplanner.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.movienightplanner.Model.Movie;
import com.example.movienightplanner.R;

import java.io.File;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private List<Movie> moviesList;
    private Context context;

    public MovieAdapter(Context context, List<Movie> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_movie_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Movie movie = moviesList.get(i);
        holder.tvMovieTitle.setText(movie.getMovie_name());
        holder.tvMovieYear.setText(movie.getMovie_year());
        if (movie.getMovie_path() != null) {
            Glide.with(context).load(new File(movie.getMovie_path())).into(holder.ivPoster);
        } else {
            holder.ivPoster.setImageDrawable(context.getResources().getDrawable(R.drawable.place_holder));
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMovieTitle, tvMovieYear;
        private ImageView ivPoster;

        public MyViewHolder(View view) {
            super(view);
            tvMovieTitle = (TextView) view.findViewById(R.id.tvMovieTitle);
            tvMovieYear = (TextView) view.findViewById(R.id.tvMovieYear);
            ivPoster = (ImageView) view.findViewById(R.id.ivPoster);
        }
    }

}
