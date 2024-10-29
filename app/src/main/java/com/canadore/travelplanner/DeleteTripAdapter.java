package com.canadore.travelplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DeleteTripAdapter extends RecyclerView.Adapter<DeleteTripAdapter.TripViewHolder> {

    private List<Trip> tripList;
    private final OnDeleteClickListener onDeleteClickListener;

    public DeleteTripAdapter(List<Trip> tripList, OnDeleteClickListener onDeleteClickListener) {
        this.tripList = tripList;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_item, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.titleTextView.setText(position + 1 + ". " + trip.getCityName());

        // Set up delete button click listener
        holder.deleteButton.setOnClickListener(v -> {
            onDeleteClickListener.onDeleteClick(trip.getTripId());
            // Optionally remove the trip from the list
            tripList.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView imageView;
        ImageButton deleteButton;

        TripViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.image);
            deleteButton = itemView.findViewById(R.id.deleteButton); // Ensure this ID matches your layout
        }
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(String tripId);
    }
}
