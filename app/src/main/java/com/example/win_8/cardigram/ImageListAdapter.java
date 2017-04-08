package com.example.win_8.cardigram;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
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
		Button button = (Button)v.findViewById(R.id.action_button);

		Glide.with(context).load(listImage.get(position).getUrl()).into(img);
		img.buildDrawingCache();
		final Bitmap bitmap = img.getDrawingCache();
		String name = Environment.getExternalStorageDirectory().getAbsolutePath();
		//Toast.makeText(context,name,Toast.LENGTH_LONG).show();

		/*button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View view) {
				String url = listImage.get(position).getUrl();
				trial ob = new trial();
				ob.file_download(url);

			}
		});*/


		button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {

				String url = listImage.get(position).getUrl();


				File direct = new File(Environment.getExternalStorageDirectory()
						+ "/dhaval_files");

				if (!direct.exists()) {
					direct.mkdirs();
				}

				DownloadManager mgr = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);

				Uri downloadUri = Uri.parse(url);
				DownloadManager.Request request = new DownloadManager.Request(
						downloadUri);

				request.setAllowedNetworkTypes(
						DownloadManager.Request.NETWORK_WIFI
								| DownloadManager.Request.NETWORK_MOBILE)
						.setAllowedOverRoaming(false).setTitle("Demo")
						.setDescription("Something useful. No, really.")
						.setDestinationInExternalPublicDir("/dhaval_files", "test.jpg");

				mgr.enqueue(request);







				Snackbar.make(v, "Action is pressed",
						Snackbar.LENGTH_LONG).show();
			}
		});






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
