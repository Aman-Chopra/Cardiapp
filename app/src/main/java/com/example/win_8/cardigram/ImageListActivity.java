package com.example.win_8.cardigram;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImageListActivity extends AppCompatActivity {

	private DatabaseReference mDatabaseRef;
	private List<ImageUpload> imgList;
	private ListView lv;
	private ImageListAdapter adapter;
	private ProgressDialog progressDialog;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_list);
		imgList = new ArrayList<>();
		lv = (ListView)findViewById(R.id.ListViewImage);
		progressDialog = new ProgressDialog(this);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setMessage("Please wait!");
		progressDialog.show();

		mDatabaseRef = FirebaseDatabase.getInstance().getReference("image");
		mDatabaseRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(final DataSnapshot dataSnapshot) {
				progressDialog.dismiss();
				for(DataSnapshot snapshot : dataSnapshot.getChildren()){
					ImageUpload img = snapshot.getValue(ImageUpload.class);
					imgList.add(img);
				}
				adapter = new ImageListAdapter(ImageListActivity.this,R.layout.item_card,imgList);
				lv.setAdapter(adapter);
			}

			@Override
			public void onCancelled(final DatabaseError databaseError) {
				progressDialog.dismiss();

			}
		});
	}
}