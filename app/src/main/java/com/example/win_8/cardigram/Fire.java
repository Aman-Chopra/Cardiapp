package com.example.win_8.cardigram;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Fire extends AppCompatActivity {
	private static final String TAG = "Fire";

	static final int RC_PHOTO_PICKER = 1;

	private Button sendBtn;
	private EditText messageTxt;
	private RecyclerView messagesList;
	private ChatMessageAdapter adapter;
	private ImageButton imageBtn;
	private TextView usernameTxt;
	private View loginBtn;
	private View logoutBtn;
	private Bitmap imagebitmap;
	private Bitmap scaled;
	private String encodedImage;

	private FirebaseApp app;
	private FirebaseDatabase database;
	private FirebaseAuth auth;
	private FirebaseStorage storage;

	private DatabaseReference databaseRef;
	private StorageReference storageRef;

	private String username;
	String mess;
	String imagemap;
	public int imageflag = 0;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fire);


		sendBtn = (Button) findViewById(R.id.sendBtn);
		messageTxt = (EditText) findViewById(R.id.messageTxt);
		messagesList = (RecyclerView) findViewById(R.id.messagesList);






		imageBtn = (ImageButton) findViewById(R.id.imageBtn);
		//loginBtn = findViewById(R.id.loginBtn);
		//logoutBtn = findViewById(R.id.logoutBtn);
		usernameTxt = (TextView) findViewById(R.id.usernameTxt);
		//setUsername("Tushya");

		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		messagesList.setHasFixedSize(false);
		messagesList.setLayoutManager(layoutManager);

		// Show an image picker when the user wants to upload an imasge
		imageBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/jpeg");

				intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
				startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
			}
		});
		// Show a popup when the user asks to sign in
		/*loginBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				LoginDialog.showLoginPrompt(Fire.this, app);
			}
		});
		// Allow the user to sign out
		logoutBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				auth.signOut();
			}
		});*/

		adapter = new ChatMessageAdapter(this);
		messagesList.setAdapter(adapter);
		adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
			public void onItemRangeInserted(int positionStart, int itemCount) {
				messagesList.smoothScrollToPosition(adapter.getItemCount());
			}
		});

		// Get the Firebase app and all primitives we'll use
		app = FirebaseApp.getInstance();
		Log.d("URL","Aman");
		database = FirebaseDatabase.getInstance(app);
		auth = FirebaseAuth.getInstance(app);
		storage = FirebaseStorage.getInstance(app);

		username = auth.getCurrentUser().getEmail();
		usernameTxt.setText(username);

		// Get a reference to our chat "room" in the database
		databaseRef = database.getReference("Chatting");

		sendBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {


				if(imageflag==0) {
					mess = messageTxt.getText().toString();
					imagemap = "";
				}
				else
				{
					mess = "";
					imagemap = encodedImage;
					imageflag = 0;
					encodedImage="";
				}
				ChatMessage chat = new ChatMessage(username, mess,imagemap);
				// Push the chat message to the database
				databaseRef.push().setValue(chat);
				messageTxt.setText("");
			}
		});
		// Listen for when child nodes get added to the collection
		databaseRef.addChildEventListener(new ChildEventListener() {
			public void onChildAdded(DataSnapshot snapshot, String s) {
				// Get the chat message from the snapshot and add it to the UI
				ChatMessage chat = snapshot.getValue(ChatMessage.class);
				adapter.addMessage(chat);
			}

			public void onChildChanged(DataSnapshot dataSnapshot, String s) {
			}

			public void onChildRemoved(DataSnapshot dataSnapshot) {
			}

			public void onChildMoved(DataSnapshot dataSnapshot, String s) {
			}

			public void onCancelled(DatabaseError databaseError) {
			}
		});

		// When the user has entered credentials in the login dialog
		/*LoginDialog.onCredentials(new OnSuccessListener<LoginDialog.EmailPasswordResult>() {
			public void onSuccess(LoginDialog.EmailPasswordResult result) {
				// Sign the user in with the email address and password they entered
				auth.signInWithEmailAndPassword(result.email, result.password);
			}
		});

		// When the user signs in or out, update the username we keep for them
		auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
			public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
				if (firebaseAuth.getCurrentUser() != null) {
					// User signed in, set their email address as the user name
					setUsername(firebaseAuth.getCurrentUser().getEmail());
				} else {
					// User signed out, set a default username
					setUsername("Android");
				}
			}
		});*/
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//if (requestCode == 42 && resultCode == RESULT_OK) {
		//	ChatMessageViewHolder.saveImage();
		if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK && data!=null && data.getData() != null) {
			Uri selectedImageUri = data.getData();
			imageflag = 1;
			Log.d("URL",selectedImageUri.toString());


			try {
              imagebitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);

                int scaleSize = (int) ( imagebitmap.getHeight() * (512.0 / imagebitmap.getWidth()) );
                scaled = Bitmap.createScaledBitmap(imagebitmap, 512, scaleSize, true);

                //imageView = (ImageView) findViewById(imageView);
                //imageView.setImageBitmap(scaled);
            } catch (IOException e) {
                e.printStackTrace();
            }

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
       scaled.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);






			/*String[] filePath = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedImageUri, filePath, null, null, null);
			cursor.moveToFirst();
			String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);*/




			/*ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			byte[] byteFormat = stream.toByteArray();
			String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);*/
			//String encodedImage = "aman";

			messageTxt.setText("Image selected!");

			final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Fire.this);
			final SharedPreferences.Editor editor = sharedPreferences.edit();


			final Set<String> set = sharedPreferences.getStringSet("imageUrls", new HashSet<String>());
			set.add(encodedImage);
			editor.putStringSet("imageUrls", set).apply();

			Log.e(TAG, (String) set.toArray()[0]);



			//cursor.close();







			// Get a reference to the location where we'll store our photos
			/*storageRef = storage.getReference("chat_photos");
			// Get a reference to store file at chat_photos/<FILENAME>
			final StorageReference photoRef = storageRef.child(selectedImageUri.getLastPathSegment());

			// Upload file to Firebase Storage
			photoRef.putFile(selectedImageUri)
					.addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
						public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
							// When the image has successfully uploaded, we get its download URL
							Uri downloadUrl = taskSnapshot.getDownloadUrl();
							// Set the download URL to the message box, so that the user can send it to the database
							messageTxt.setText(downloadUrl.toString());

							final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Fire.this);
							final SharedPreferences.Editor editor = sharedPreferences.edit();


							final Set<String> set = sharedPreferences.getStringSet("imageUrls", new HashSet<String>());
							set.add(downloadUrl.toString());
							editor.putStringSet("imageUrls", set).apply();

							Log.e(TAG, (String) set.toArray()[0]);
						}
					});*/
		}
	}
}