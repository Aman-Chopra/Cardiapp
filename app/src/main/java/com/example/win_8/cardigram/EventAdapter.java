package com.example.win_8.cardigram;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by vinayak on 4/11/17.
 */

public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private List<Event> events;
	private Context context;

	public EventAdapter(List<Event> events, Context context) {
		this.events = events;
		this.context = context;
	}

	@Override
	public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.recycler_event, parent, false);
		return new EventViewHolder(v);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
		Event event = events.get(position);
		EventViewHolder evh = (EventViewHolder) holder;
		evh.eventName.setText(event.getEventName());
		evh.eventDate.setText(event.getEventTime() + " on " + event.getEventDate());

		evh.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public int getItemCount() {
		return events.size();
	}

	static class EventViewHolder extends RecyclerView.ViewHolder{

		TextView eventName;
		TextView eventDate;

		public EventViewHolder(View itemView) {
			super(itemView);
			eventName = (TextView) itemView.findViewById(R.id.eventName);
			eventDate = (TextView) itemView.findViewById(R.id.eventDate);
		}
	}

}
