package com.sosa.leslintest12_1_2020.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sosa.leslintest12_1_2020.R;
import com.sosa.leslintest12_1_2020.Retrofit.Retrofit_models.Result;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {

    Context context;
    List<Result> list;

    public FilmAdapter(Context context, List<Result> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_films,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Result result=list.get(position);

        holder.tv_film_name.setText(result.getTitle());
        holder.tv_director.setText(result.getDirector());
        holder.tv_plot.setText(result.getOpeningCrawl());
        holder.tv_date.setText(result.getReleaseDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_film_name,tv_director,tv_plot,tv_date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_film_name=itemView.findViewById(R.id.tv_film_name);
            tv_director=itemView.findViewById(R.id.tv_director);
            tv_plot=itemView.findViewById(R.id.tv_plot);
            tv_date=itemView.findViewById(R.id.tv_date);

        }
    }
}
