package com.example.win_8.cardigram;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EventActivity extends AppCompatActivity {

	private static final String TAG = "AddEventActivity";

	String placeName;
	double placeLat;
	double placeLng;
	FirebaseDatabase database;
	DatabaseReference myRef;
	FirebaseUser user;
	String uid;

	EditText doctorNameEditText;
	TextView dateEditText;
	TextView timeEditText;
	TextView locationEditText;

	Calendar myCalendar;
	DatePickerDialog.OnDateSetListener date;

	int PLACE_PICKER_REQUEST = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);

		database = FirebaseDatabase.getInstance();
		myRef = FirebaseDatabase.getInstance().getReference();
		user = FirebaseAuth.getInstance().getCurrentUser();
		uid = user.getUid();

		dateEditText = (TextView) findViewById(R.id.date);
		timeEditText = (TextView) findViewById(R.id.time);
		locationEditText = (TextView) findViewById(R.id.locationeditText);


		timeEditText.setText(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
		dateEditText.setText(new SimpleDateFormat("MMM dd, yyyy").format(Calendar.getInstance().getTime()));

		date = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
			                      int dayOfMonth) {
				// TODO Auto-generated method stub
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);
				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				updateLabel();
			}
		};

//		dateEditText.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				myCalendar = Calendar.getInstance();
//				new DatePickerDialog(EventActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//			}
//		});


		dateEditText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myCalendar = Calendar.getInstance();
				DatePickerDialog dp = new DatePickerDialog(EventActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
				dp.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
				dp.show();

			}
		});



		timeEditText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar mcurrentTime = Calendar.getInstance();
				int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
				int minute = mcurrentTime.get(Calendar.MINUTE);
				TimePickerDialog mTimePicker;
				mTimePicker = new TimePickerDialog(EventActivity.this, new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
						timeEditText.setText( selectedHour + ":" + selectedMinute);
					}
				}, hour, minute, true);//24 hour time
				mTimePicker.show();

			}
		});


		locationEditText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
				try {
					startActivityForResult(builder.build(EventActivity.this), PLACE_PICKER_REQUEST);
				} catch (GooglePlayServicesRepairableException e) {
					e.printStackTrace();
				} catch (GooglePlayServicesNotAvailableException e) {
					e.printStackTrace();
				}
			}
		});

		Button button = (Button)findViewById(R.id.addbutton);
		button.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				if(!validate()){
					return;
				}
				doctorNameEditText = (EditText)findViewById(R.id.eventNameeditText);
				dateEditText = (TextView) findViewById(R.id.date);
				timeEditText = (TextView) findViewById(R.id.time);

				String doctorNameString = doctorNameEditText.getText().toString();
				String dateString = dateEditText.getText().toString();
				String timeString = timeEditText.getText().toString();



				final ProgressDialog progressDialog = new ProgressDialog(EventActivity.this);
				progressDialog.setMessage("Creating Event");
				progressDialog.show();

				final Event newEvent = new Event(doctorNameString, dateString, timeString, placeName, placeLat, placeLng);

				final String pushKey = myRef.child("Events").push().getKey();

				myRef.child("Events").child(pushKey).setValue(newEvent).addOnCompleteListener(new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						if(task.isSuccessful()){
							Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();
							Intent i = new Intent(getApplicationContext(), MainActivity.class);
							i.putExtra("eventId", pushKey);
							i.putExtra("event", (new Gson()).toJson(newEvent));
							finish();
							startActivity(i);
							progressDialog.dismiss();
						}
						else{
							AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext(),R.style.AppCompatAlertDialogStyle);
							builder.setTitle("Done");
							builder.setMessage("Appointment saved successfully!");
							builder.setPositiveButton("OK", null);
							builder.show();
							progressDialog.dismiss();
						}
					}
				});

			}
		});
	}



	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		locationEditText = (TextView)findViewById(R.id.locationeditText);

		if (requestCode == PLACE_PICKER_REQUEST) {
			if (resultCode == RESULT_OK) {
				Place place = PlacePicker.getPlace(data, this);
				locationEditText.setText(place.getName());
				placeName = place.getName().toString();
				placeLat = place.getLatLng().latitude;
				placeLng = place.getLatLng().longitude;

			}
		}
	}

	private void updateLabel() {

		String myFormat = "MMM dd, yyyy"; //In which you need put here
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

		dateEditText.setText(sdf.format(myCalendar.getTime()));
	}

	public boolean validate() {
		boolean valid = true;



		doctorNameEditText = (EditText)findViewById(R.id.eventNameeditText);
		dateEditText = (TextView) findViewById(R.id.date);
		timeEditText = (TextView) findViewById(R.id.time);
		locationEditText = (TextView) findViewById(R.id.locationeditText);

		String doctorNameString = doctorNameEditText.getText().toString();
		String dateString = dateEditText.getText().toString();
		String timeString = timeEditText.getText().toString();
		String locationString = locationEditText.getText().toString();


		if(TextUtils.isEmpty(doctorNameString)){
			doctorNameEditText.setError("Enter the doctor's name.");
			valid = false;
		}
		else{
			doctorNameEditText.setError(null);
		}



		if(TextUtils.isEmpty(dateString)){
			dateEditText.setError("Enter the appointment date.");
			valid = false;
		}
		else{
			dateEditText.setError(null);
		}

		if(TextUtils.isEmpty(timeString)){
			timeEditText.setError("Enter the appointment time.");
			valid = false;
		}
		else {
			timeEditText.setError(null);
		}

		if(TextUtils.isEmpty(locationString)){
			locationEditText.setError("Select the appointment location.");
			valid = false;
		}
		else {
			locationEditText.setError(null);
		}


		return valid;
	}


}
