package com.example.bmicalculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BMIHistoryAdapter extends RecyclerView.Adapter<BMIHistoryAdapter.BMIViewHolder> {
    private List<BMIResult> bmiResults;  // Use BMIResultModel

    public BMIHistoryAdapter(List<BMIResult> bmiResults) {  // Constructor accepts BMIResult
        this.bmiResults = bmiResults;
    }

    @NonNull
    @Override
    public BMIViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bmi_result, parent, false);
        return new BMIViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BMIViewHolder holder, int position) {
        BMIResult bmiResult = bmiResults.get(position);  // Use BMIResult
        holder.tvBMIValue.setText(String.format("%.1f", bmiResult.bmiValue));
        holder.tvBMICategory.setText(bmiResult.bmiCategory);
        holder.tvDateTime.setText(bmiResult.dateTime);

        switch (bmiResult.bmiCategory) {
            case "Healthy":
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.healthy));
                break;
            case "Obese":
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.obese));
                break;
            case "Underweight":
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.underWeight));
                break;
            case "Overweight":
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.overWeight));
                break;
            default:
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return bmiResults.size();
    }

    public static class BMIViewHolder extends RecyclerView.ViewHolder {
        TextView tvBMIValue, tvBMICategory, tvDateTime;

        public BMIViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBMIValue = itemView.findViewById(R.id.tvBMIValue);
            tvBMICategory = itemView.findViewById(R.id.tvBMICategory);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
        }
    }
}
