package com.example.projecttwo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.UserViewHolder> {

    private List<EventModel> listEvents;

    public EventAdapter(List<EventModel> listUsers) {
        this.listEvents = listEvents;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_adapter, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewName.setText(listEvents.get(position).getName());
        holder.textViewDatetime.setText(listEvents.get(position).getDatetime());
        holder.textViewDescription.setText(listEvents.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        Log.v(EventAdapter.class.getSimpleName(),""+listEvents.size());
        return listEvents.size();
    }


    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewName;
        public AppCompatTextView textViewDatetime;
        public AppCompatTextView textViewDescription;

        public UserViewHolder(View view) {
            super(view);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            textViewDatetime = (AppCompatTextView) view.findViewById(R.id.textViewDatetime);
            textViewDescription = (AppCompatTextView) view.findViewById(R.id.textViewDescription);
        }
    }


}