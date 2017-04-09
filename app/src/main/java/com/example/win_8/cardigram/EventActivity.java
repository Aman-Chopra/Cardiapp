//package com.example.win_8.cardigram;
//
//import android.app.DatePickerDialog;
//import android.app.ProgressDialog;
//import android.app.TimePickerDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
//import com.google.android.gms.common.GooglePlayServicesRepairableException;
//import com.google.android.gms.location.places.Place;
//import com.google.android.gms.location.places.ui.PlacePicker;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.gson.Gson;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//
//
//
//public class EventActivity extends AppCompatActivity {
//
//	private static final String TAG = "AddEventActivity";
//
//	String placeName;
//	double placeLat;
//	double placeLng;
//	FirebaseDatabase database;
//	DatabaseReference myRef;
//	FirebaseUser user;
//	String uid;
//
//	EditText eventNameEditText;
//	EditText descriptionEditText;
//	TextView dateEditText;
//	TextView timeEditText;
//	TextView locationEditText;
//
//	Calendar myCalendar;
//	DatePickerDialog.OnDateSetListener date;
//
//	int PLACE_PICKER_REQUEST = 1;
//
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_event);
//
//		database = FirebaseDatabase.getInstance();
//		myRef = FirebaseDatabase.getInstance().getReference();
//		user = FirebaseAuth.getInstance().getCurrentUser();
//		uid = user.getUid();
//
//		dateEditText = (TextView) findViewById(R.id.date);
//		timeEditText = (TextView) findViewById(R.id.time);
//		locationEditText = (TextView) findViewById(R.id.locationeditText);
//
//		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//				.setDefaultFontPath("fonts/sfui.ttf")
//				.setFontAttrId(R.attr.fontPath)
//				.build()
//		);
//
//		timeEditText.setText(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
//		dateEditText.setText(new SimpleDateFormat("MMM dd, yyyy").format(Calendar.getInstance().getTime()));
//
//		myCalendar = Calendar.getInstance();
//
//		date = new DatePickerDialog.OnDateSetListener() {
//
//			@Override
//			public void onDateSet(DatePicker view, int year, int monthOfYear,
//			                      int dayOfMonth) {
//				// TODO Auto-generated method stub
//				myCalendar.set(Calendar.YEAR, year);
//				myCalendar.set(Calendar.MONTH, monthOfYear);
//				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//				updateLabel();
//			}
//
//
//		};
//
//		dateEditText.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				new DatePickerDialog(EventActivity.this, date, myCalendar
//						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//			}
//		});
//
//
//		timeEditText.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Calendar mcurrentTime = Calendar.getInstance();
//				int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//				int minute = mcurrentTime.get(Calendar.MINUTE);
//				TimePickerDialog mTimePicker;
//				mTimePicker = new TimePickerDialog(EventActivity.this, new TimePickerDialog.OnTimeSetListener() {
//					@Override
//					public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//						timeEditText.setText( selectedHour + ":" + selectedMinute);
//					}
//				}, hour, minute, true);//24 hour time
//				mTimePicker.show();
//
//			}
//		});
//
//		locationEditText.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//				try {
//					startActivityForResult(builder.build(EventActivity.this), PLACE_PICKER_REQUEST);
//				} catch (GooglePlayServicesRepairableException e) {
//					e.printStackTrace();
//				} catch (GooglePlayServicesNotAvailableException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//
//		Button button = (Button)findViewById(R.id.addbutton);
//		button.setOnClickListener(new View.OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				if(!validate()){
//					return;
//				}
//				eventNameEditText = (EditText)findViewById(R.id.eventNameeditText);
//				descriptionEditText = (EditText)findViewById(R.id.descriptioneditText);
//				dateEditText = (TextView) findViewById(R.id.date);
//				timeEditText = (TextView) findViewById(R.id.time);
//
//				String eventNameString = eventNameEditText.getText().toString();
//				String descriptionString = descriptionEditText.getText().toString();
//				String dateString = dateEditText.getText().toString();
//				String timeString = timeEditText.getText().toString();
//
//				HashMap<String, String> peopleAttending = new HashMap<String, String>();
//				peopleAttending.put(uid, uid);
//
//
//				final ProgressDialog progressDialog = new ProgressDialog(EventActivity.this);
//				progressDialog.setMessage("Creating Event");
//				progressDialog.show();
//
//				final Event newEvent = new Event(eventNameString, descriptionString, dateString, timeString, placeName, placeLat, placeLng, peopleAttending);
//
//				final String pushKey = myRef.child("Events").push().getKey();
//
//				myRef.child("Events").child(pushKey).setValue(newEvent).addOnCompleteListener(new OnCompleteListener<Void>() {
//					@Override
//					public void onComplete(@NonNull Task<Void> task) {
//						if(task.isSuccessful()){
//							Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();
//							Intent i = new Intent(getApplicationContext(), EventDetailsActivity.class);
//							i.putExtra("eventId", pushKey);
//							i.putExtra("event", (new Gson()).toJson(newEvent));
//							finish();
//							startActivity(i);
//							progressDialog.dismiss();
//						}
//						else{
//							Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
//							progressDialog.dismiss();
//						}
//					}
//				});
//
//			}
//		});
//	}
//
//
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//		locationEditText = (TextView)findViewById(R.id.locationeditText);
//
//		if (requestCode == PLACE_PICKER_REQUEST) {
//			if (resultCode == RESULT_OK) {
//				Place place = PlacePicker.getPlace(data, this);
//				locationEditText.setText(place.getName());
//				placeName = place.getName().toString();
//				placeLat = place.getLatLng().latitude;
//				placeLng = place.getLatLng().longitude;
//
//			}
//		}
//	}
//
//	private void updateLabel() {
//
//		String myFormat = "MMM dd, yyyy"; //In which you need put here
//		SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
//
//		dateEditText.setText(sdf.format(myCalendar.getTime()));
//	}
//
//	public boolean validate() {
//		boolean valid = true;
//
//
//
//		eventNameEditText = (EditText)findViewById(R.id.eventNameeditText);
//		descriptionEditText = (EditText)findViewById(R.id.descriptioneditText);
//		dateEditText = (TextView) findViewById(R.id.date);
//		timeEditText = (TextView) findViewById(R.id.time);
//		locationEditText = (TextView) findViewById(R.id.locationeditText);
//
//		String eventNameString = eventNameEditText.getText().toString();
//		String descriptionString = descriptionEditText.getText().toString();
//		String dateString = dateEditText.getText().toString();
//		String timeString = timeEditText.getText().toString();
//		String locationString = locationEditText.getText().toString();
//
//
//		if(TextUtils.isEmpty(eventNameString)){
//			eventNameEditText.setError("Enter the event name.");
//			valid = false;
//		}
//		else{
//			eventNameEditText.setError(null);
//		}
//
//		if(TextUtils.isEmpty(descriptionString)){
//			descriptionEditText.setError("Enter the event description.");
//			valid = false;
//		}
//		else{
//			descriptionEditText.setError(null);
//		}
//
//		if(TextUtils.isEmpty(dateString)){
//			dateEditText.setError("Enter the event date.");
//			valid = false;
//		}
//		else{
//			dateEditText.setError(null);
//		}
//
//		if(TextUtils.isEmpty(timeString)){
//			timeEditText.setError("Enter the event time.");
//			valid = false;
//		}
//		else {
//			timeEditText.setError(null);
//		}
//
//		if(TextUtils.isEmpty(locationString)){
//			locationEditText.setError("Select the event location.");
//			valid = false;
//		}
//		else {
//			locationEditText.setError(null);
//		}
//
//
//		return valid;
//	}
//
//	@Override
//	protected void attachBaseContext(Context newBase) {
//		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//	}
//
//
//}
