package com.example.win_8.cardigram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

/**
 * Created by win-8 on 06-04-2017.
 */

public class ImageListAdapter extends ArrayAdapter<ImageUpload> {
	private Activity context;
	private int resource;
	public List<ImageUpload> listImage;


	public ImageListAdapter(final Activity context, final int resource, final List<ImageUpload> objects) {
		super(context, resource, objects);
		this.context = context;
		this.resource = resource;
		listImage = objects;

	}

	@NonNull
	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();

		View v = inflater.inflate(resource,null);
		TextView tvName = (TextView)v.findViewById(R.id.card_text);
		TextView tvTitle = (TextView)v.findViewById(R.id.card_title);
		ImageView img = (ImageView)v.findViewById(R.id.card_image);

		Glide.with(context).load(listImage.get(position).getUrl()).into(img);
		img.buildDrawingCache();
		final Bitmap bitmap = img.getDrawingCache();


		tvName.setText("Aman");
		tvTitle.setText("Chopra");
		v.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Context context = v.getContext();
				Intent intent = new Intent(context, DetailActivity.class);
				intent.putExtra(DetailActivity.EXTRA_POSITION, position);
				intent.putExtra("mylist", (Serializable) listImage);
				//intent.putExtra("BitmapImage", bitmap);
				//intent.putExtra(DetailActivity.Imglist,listImage);
				context.startActivity(intent);
			}
		});

		return v;



	}
}
