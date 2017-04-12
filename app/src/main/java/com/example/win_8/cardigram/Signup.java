package com.example.win_8.cardigram;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.win_8.cardigram.Fire.RC_PHOTO_PICKER;

public class Signup extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    Button signupButton;
    TextView _loginLink;
    EditText _passwordText;
    EditText _emailText;
    EditText _nameText;
    ImageButton imageBtn;
    public int check = 0;
    Bitmap imagebitmap;
    Bitmap scaled;
    String encodedImage;
    TextView selected;
    public int flag = 0;




    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupButton = (Button)findViewById(R.id.btn_signup);
        _loginLink = (TextView)findViewById(R.id.link_login);
        _passwordText = (EditText)findViewById(R.id.input_password);
        _emailText = (EditText)findViewById(R.id.input_email);
        _nameText = (EditText)findViewById(R.id.input_name);
        imageBtn = (ImageButton) findViewById(R.id.imageButton);
        selected = (TextView)findViewById(R.id.image_input_layout);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = _emailText.getText().toString();
                String password = _passwordText.getText().toString();
                String name = _nameText.getText().toString();
                createAccount(email,password);

            }
        });

        imageBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");

                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeActivity();

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void createAccount(String email, String password) {

        Log.d(TAG, "createAccount:" + email);
        if (!validate()) {
            return;
        }




        if(!isOnline())
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle);
            builder.setTitle("Connectivity Error.");
            builder.setMessage("Check your internet connection.");
            builder.setPositiveButton("OK", null);
            builder.show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(Signup.this,
                R.style.AppTheme_Dark_Dialog);

        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        long delayInMillis = 8000;
        Timer timer = new Timer();




        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                progressDialog.dismiss();
                //toast("Process is taking too long");
                Signup.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(flag==0)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext(),R.style.AppCompatAlertDialogStyle);
                            builder.setTitle("Connectivity Error.");
                            builder.setMessage("Check your internet connection.");
                            builder.setPositiveButton("OK", null);
                            builder.show();
                            return;

                        }

                    }
                });


            }
        }, delayInMillis);






        final AlertDialog.Builder builder1 =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            flag = 1;
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                            String name = _nameText.getText().toString();
                            String email = _emailText.getText().toString();
                            String image = encodedImage;

                            Chalja Chalja = new Chalja(name, image,email);
                            //String a = profile.


                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "Email sent.");
                                            }
                                        }
                                    });
//                            HashMap<String,Object> datamap = new HashMap<String, Object>();
//                           datamap.put("Name",name);
//                           datamap.put("E-mail",email);
//                            datamap.put("Image",image);
                            // final Context context = v.getContext();
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("Users").child(user.getUid()).setValue(Chalja).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        //Toast.makeText(context,"Successful",Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        //Toast.makeText(context,"Error",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            progressDialog.dismiss();
                            builder1.setTitle("Congratulations!");
                            builder1.setMessage("Your account has been registered successfully.");
                            builder1.setPositiveButton(android.R.string.ok,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            changeActivity();
                                        }
                                    }).create().show();
                            //builder.show();

                        }
                        if (!task.isSuccessful()) {
                            flag = 1;
                            progressDialog.dismiss();
                            builder1.setTitle("Error.");
                            builder1.setMessage(task.getException().getMessage());
                            builder1.setPositiveButton(android.R.string.ok,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).create().show();
                            return;
                            //callDialog();


                        }

                    }
                });
    }

    private void changeActivity()
    {
        finish();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }




    public boolean validate() {
        boolean valid = true;
        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        TextInputLayout til = (TextInputLayout) findViewById(R.id.name_input_layout);

        if(check==0)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle);
            builder.setTitle("Missing fields");
            builder.setMessage("Please select an image");
            builder.setPositiveButton("OK", null);
            builder.show();
            return false;
        }

        if (name.isEmpty() || name.length() < 3) {
            til.setErrorEnabled(true);
            til.setError("Enter a valid name.");
            valid = false;
        } else {
            til.setErrorEnabled(false);
        }

        TextInputLayout till = (TextInputLayout) findViewById(R.id.email_input_layout);

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            till.setErrorEnabled(true);
            till.setError("Enter a valid email.");
            valid = false;
        } else {
            till.setErrorEnabled(false);
        }

        TextInputLayout pass = (TextInputLayout) findViewById(R.id.passw_input_layout);

        if (password.isEmpty() || password.length() < 6) {
            pass.setErrorEnabled(true);
            pass.setError("Password must be at least six characters.");
            valid = false;
        } else {
            pass.setErrorEnabled(false);
        }

        return valid;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if (requestCode == 42 && resultCode == RESULT_OK) {
        //	ChatMessageViewHolder.saveImage();
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK && data!=null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            check = 1;
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

            selected.setText("Image selected!");

            final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Signup.this);
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