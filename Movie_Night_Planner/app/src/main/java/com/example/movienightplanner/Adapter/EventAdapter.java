package com.example.movienightplanner.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.movienightplanner.Model.Events;
import com.example.movienightplanner.R;
import com.example.movienightplanner.activity.EdittingEventActivity;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Events> eventsList = new ArrayList<>();
    private Context context;

    public EventAdapter(List<Events> eventsList, Context context) {
        this.eventsList = eventsList;
        this.context = context;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.event_adapter_item, viewGroup, false);
        return new EventAdapter.EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {
        final Events events = eventsList.get(i);
        eventViewHolder.tvEventTitle.setText(events.getTitle() + " (" + events.getMovie_name() + ")");
        eventViewHolder.tvDate.setText(events.getStart_date());
        eventViewHolder.tvVenue.setText(events.getVenue());
        if (events.getAttendee().equalsIgnoreCase("0")) {
            eventViewHolder.tvAttendee.setText(String.valueOf(0));
        } else {
            eventViewHolder.tvAttendee.setText(String.valueOf(events.getAttendee().split(",").length));
        }
        eventViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EdittingEventActivity.class);
                intent.putExtra("ID", events.get_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEventTitle, tvDate, tvVenue, tvAttendee;
        private LinearLayout linearLayout;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEventTitle = (TextView) itemView.findViewById(R.id.tvEventTitle);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvVenue = (TextView) itemView.findViewById(R.id.tvVenue);
            tvAttendee = (TextView) itemView.findViewById(R.id.tvAttendeeCount);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
