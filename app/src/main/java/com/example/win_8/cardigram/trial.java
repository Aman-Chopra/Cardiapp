package com.example.win_8.cardigram;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class trial extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trial);
	}
	public void click(View v)
	{
		Toast.makeText(getApplicationContext(),"yo",Toast.LENGTH_SHORT).show();
		String url = "https://firebasestorage.googleapis.com/v0/b/cardigram-469f9.appspot.com/o/images%2FLUxLDKxHASWiTwmEMxubqUaAuFS2%2Fimage%3A16012?alt=media&token=2cc8aad2-a655-4bbb-8590-be8725d555a1";
		file_download(url);
	}
	public void file_download(String uRl) {
		Toast.makeText(getApplicationContext(),"co",Toast.LENGTH_SHORT).show();
		File direct = new File(Environment.getExternalStorageDirectory()
				+ "/Cardigram");

		if (!direct.exists()) {
			direct.mkdirs();
		}

		DownloadManager mgr = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);

		Uri downloadUri = Uri.parse(uRl);
		DownloadManager.Request request = new DownloadManager.Request(
				downloadUri);

		request.setAllowedNetworkTypes(
				DownloadManager.Request.NETWORK_WIFI
						| DownloadManager.Request.NETWORK_MOBILE)
				.setAllowedOverRoaming(false).setTitle("Demo")
				.setDescription("Something useful. No, really.")
				.setDestinationInExternalPublicDir("Cardigram", "test.jpg");

		mgr.enqueue(request);

	}
}
