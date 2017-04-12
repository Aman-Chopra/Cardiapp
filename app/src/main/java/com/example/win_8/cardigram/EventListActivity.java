package com.example.win_8.cardigram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventListActivity extends AppCompatActivity {

	DatabaseReference ref;
	List<Event> events;
	RecyclerView recyclerView;
	RecyclerView.Adapter adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);

		recyclerView = (RecyclerView) findViewById(R.id.event);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		events = new ArrayList<>();
		adapter = new EventAdapter(events, this);
		recyclerView.setAdapter(adapter);

		ref = FirebaseDatabase.getInstance().getReference("Events");
		ref.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for(DataSnapshot ds : dataSnapshot.getChildren()){

					Event event = ds.getValue(Event.class);
					String date = event.getEventDate();
					SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
					try {
						Date mdate = sdf.parse(date);
						Date now = new Date();
						if(mdate.compareTo(now) > 0){
							events.add(event);
							Log.d("ww", event.getEventName()+"");
							adapter.notifyItemInserted(events.size() - 1);
						}

					}catch (Exception e){e.getStackTrace();}


				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
	}
}
