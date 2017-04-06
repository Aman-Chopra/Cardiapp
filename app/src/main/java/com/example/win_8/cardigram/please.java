package com.example.win_8.cardigram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class please extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_please);
		ImageView aman;
		TextView title;
		TextView description;
		title = (TextView)findViewById(R.id.card_title);
		description = (TextView)findViewById(R.id.card_text);
		title.setText("Something");
		description.setText("Just like this");
		aman = (ImageView)findViewById(R.id.card_image);
		Picasso.with(this).load("https://firebasestorage.googleapis.com/v0/b/cardigram-469f9.appspot.com/o/images%2FLUxLDKxHASWiTwmEMxubqUaAuFS2%2Fimage%3A59938?alt=media&token=8ceb4d80-aa18-4721-83eb-9cf2bc39a282")
				.into(aman);
	}
}
