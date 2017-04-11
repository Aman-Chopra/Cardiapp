package com.example.win_8.cardigram;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class Collapse extends AppCompatActivity {

	public static final String EXTRA_POSITION = "position";
	public List<ImageUpload> Plist;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// Set Collapsing Toolbar layout to the screen
		CollapsingToolbarLayout collapsingToolbar =
				(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
		// Set title of Detail page
		collapsingToolbar.setTitle(getString(R.string.item_title));

		int postion = getIntent().getIntExtra(EXTRA_POSITION, 0);


		Resources resources = getResources();
		String[] places = resources.getStringArray(R.array.places);
		collapsingToolbar.setTitle("Chopra");


		TextView placeDetail = (TextView) findViewById(R.id.place_detail);
		placeDetail.setText("Aman");




		TypedArray placePictures = resources.obtainTypedArray(R.array.places_picture);
		ImageView placePicutre = (ImageView) findViewById(R.id.image);
		Intent i = getIntent();
		Plist = (List<ImageUpload>) i.getSerializableExtra("mylist");
		String urll = Plist.get(postion).getUrl();
		Picasso.with(this)
				.load(urll)
				.into(placePicutre);

		//Glide.with(this).load(Plist.get(postion).getUrl()).into(placePicutre);
		String pos = Integer.toString(postion);
		//String url = Plist.get(postion).getUrl();
		Toast.makeText(getApplicationContext(),urll,Toast.LENGTH_SHORT).show();



		//Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
		//placePicutre.setImageDrawable(placePictures.getDrawable(postion % placePictures.length()));

		placePictures.recycle();
	}

}