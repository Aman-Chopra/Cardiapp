package com.example.win_8.cardigram;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ChatMessageViewHolder extends RecyclerView.ViewHolder {
	private static final String TAG = "ChatMessageViewHolder";
	private static Activity activity;
	private static int i = 0;

	TextView name, message;
	static ImageView image;
	private static BitmapDrawable drawable;

	public ChatMessageViewHolder(Activity activity, View itemView) {
		super(itemView);
		this.activity = activity;
		name = (TextView) itemView.findViewById(R.id.name);
		message = (TextView) itemView.findViewById(R.id.message);
		image = (ImageView) itemView.findViewById(R.id.image);
		TextView messageTime = (TextView) itemView.findViewById(R.id.message_time);
		messageTime.setText("Aman");
	}

	public void bind(ChatMessage chat) {
		name.setText(chat.name);
		if (chat.message.startsWith("https://firebasestorage.googleapis.com/") || chat.message.startsWith("content://")) {
			message.setVisibility(View.INVISIBLE);
			image.setVisibility(View.VISIBLE);
			Glide.with(activity)
					.load(chat.message)
					.asBitmap()
					.into(image);

			image.setVisibility(View.VISIBLE);
			message.setVisibility(View.GONE);

			image.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(final View view) {
					drawable = (BitmapDrawable) image.getDrawable();
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
						if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
							saveImage();
						} else {
							activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 42);
						}
					} else {
						saveImage();
					}

					return true;
				}
			});
		} else {
			message.setVisibility(View.VISIBLE);
			image.setVisibility(View.GONE);
			message.setText(chat.message);
		}
	}

	public static void saveImage() {
		final Bitmap bitmap = drawable.getBitmap();
		final File mediaDir = new File(Environment.getExternalStorageDirectory() + "/Cardigram/");

		if (!mediaDir.exists()) {
			if (!mediaDir.mkdirs())
				return;
		}

		try {
			final FileOutputStream out = new FileOutputStream(mediaDir + "/pic" + i++ + ".jpg");
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}