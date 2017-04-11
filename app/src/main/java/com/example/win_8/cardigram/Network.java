package com.example.win_8.cardigram;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Network extends AppCompatActivity {

	ProgressDialog progressDialog;
	private DatabaseReference mDatabaseRef;
	private FirebaseAuth auth;
	private FirebaseApp app;
	public static String USERNAME;
	public static String EMAIL;
	public static String IMAGE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_network);

		progressDialog = new ProgressDialog(Network.this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Retrieving");
		progressDialog.show();
		if(!isOnline())
		{

		}

		app = FirebaseApp.getInstance();
		auth = FirebaseAuth.getInstance(app);
		mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getUid());



		mDatabaseRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				//CircleImageView pic = (de.hdodenhof.circleimageview.CircleImageView)findViewById(R.id.profile_image);

				Chalja Chalja = dataSnapshot.getValue(Chalja.class);
				String encodedImage = Chalja.getAmankiphoto();
				IMAGE = encodedImage;
				USERNAME = Chalja.getAman();
//				byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
//				Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//				pic.setImageBitmap(decodedByte);
//				//drawerUsername.setText(USERNAME);
//				drawerUsername.setText("Suvimal");



				// Hide the loading screen
				Toast.makeText(getApplicationContext(),USERNAME,Toast.LENGTH_SHORT).show();
				progressDialog.hide();
				Intent intent = new Intent(Network.this, MainActivity.class);
				intent.putExtra("image", IMAGE);
				startActivity(intent);

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
			}
		});




















	}

	private boolean isOnline() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();}

}
