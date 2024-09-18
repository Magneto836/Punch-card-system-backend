package com.example.myapplication_master.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication_master.R;

import java.util.List;

import api.StatisticsPojo;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.StatisticsViewHolder> {

    private List<StatisticsPojo> statisticsList;

    public StatisticsAdapter(List<StatisticsPojo> statisticsList) {
        this.statisticsList = statisticsList;
    }

    @NonNull
    @Override
    public StatisticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_statistics, parent, false);
        return new StatisticsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsViewHolder holder, int position) {
        StatisticsPojo statistics = statisticsList.get(position);
        holder.usernameTextView.setText(statistics.getUsername());
        holder.totalHoursTextView.setText(String.valueOf(statistics.getTotalHours()));
        holder.rankingTextView.setText(String.valueOf(statistics.getRanking()));
    }

    @Override
    public int getItemCount() {
        return statisticsList.size();
    }

    public static class StatisticsViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView;
        TextView totalHoursTextView;
        TextView rankingTextView;

        public StatisticsViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.text_view_username);
            totalHoursTextView = itemView.findViewById(R.id.text_view_total_hours);
            rankingTextView = itemView.findViewById(R.id.text_view_ranking);
        }
    }
}
