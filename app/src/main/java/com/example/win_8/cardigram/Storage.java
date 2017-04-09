package com.example.win_8.cardigram;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.OnProgressListener;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import java.io.IOException;
//
//public class Storage extends AppCompatActivity implements View.OnClickListener /*  implementing click listener */ {
//    //a constant to track the file chooser intent
//    private static final int PICK_IMAGE_REQUEST = 234;
//    private static final int PICK_IMAGE = 1;
//
//    //Buttons
//    private Button buttonChoose;
//    private Button buttonUpload;
//    private Bitmap imagebitmap;
//    private Bitmap scaled;
//    private DatabaseReference mDatabase;
//
//    //ImageView
//    private ImageView imageView;
//    private StorageReference storageReference;
//    private DatabaseReference mDataBaseRef;
//
//    //a Uri object to store file path
//    private Uri filePath;
//    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//    String uid = user.getUid();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_storage);
//
//        //getting views from layout
//        buttonChoose = (Button) findViewById(R.id.buttonChoose);
//        buttonUpload = (Button) findViewById(R.id.buttonUpload);
//
//        imageView = (ImageView) findViewById(R.id.imageView);
//
//        //attaching listener
//        buttonChoose.setOnClickListener(this);
//        buttonUpload.setOnClickListener(this);
//        storageReference = FirebaseStorage.getInstance().getReference();
//        mDataBaseRef = FirebaseDatabase.getInstance().getReference("image");
//    }
//
//    //method to show file chooser
//    private void showFileChooser() {
//        //Intent intent = new Intent();
//        //intent.setType("*/*");
//        //intent.setAction(Intent.ACTION_GET_CONTENT);
//        //startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
//
//
//
//                Intent gallIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        gallIntent.setType("image/*");
//        startActivityForResult(Intent.createChooser(gallIntent, "Select Profile Picture"), PICK_IMAGE);
//    }
//
//    //handling the image chooser activity result
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //Log.i(TAG,"Status: " + requestCode + " " + resultCode);
//        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            filePath = data.getData();
//            //filePath = data.getExtras().get("data");
//            try {
//                imagebitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//
//                int scaleSize = (int) ( imagebitmap.getHeight() * (512.0 / imagebitmap.getWidth()) );
//                scaled = Bitmap.createScaledBitmap(imagebitmap, 512, scaleSize, true);
//
//                imageView = (ImageView) findViewById(R.id.imageView);
//                imageView.setImageBitmap(scaled);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    //this method will upload the file
//    private void uploadFile() {
//        String uid = user.getUid();
//        //if there is a file to upload
//        if (filePath != null) {
//            //displaying a progress dialog while upload is going on
//            final ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.setTitle("Uploading");
//            progressDialog.show();
//
//            StorageReference riversRef = storageReference.child("images/" + uid + "/" + filePath.getLastPathSegment());
//            riversRef.putFile(filePath)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            //if the upload is successful
//                            //hiding the progress dialog
//                            progressDialog.dismiss();
//                            ImageUpload imageUpload = new ImageUpload(taskSnapshot.getDownloadUrl().toString());
//                            String uploadId = mDataBaseRef.push().getKey();
//                            mDataBaseRef.child(uploadId).setValue(imageUpload);
//
//                            //and displaying a success toast
//                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            //if the upload is not successfull
//                            //hiding the progress dialog
//                            progressDialog.dismiss();
//
//                            //and displaying error message
//                            Toast.makeText(getApplicationContext(), exception.getMessage() + "Exception" + filePath, Toast.LENGTH_LONG).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            //calculating progress percentage
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//
//                            //displaying percentage in progress dialog
//                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
//                        }
//                    });
//        }
//        //if there is not any file
//        else {
//            Toast.makeText(getApplicationContext(), "Upload", Toast.LENGTH_LONG).show();
//            //you can display an error toast
//        }
//
//
//
//        /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        scaled.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte[] byteFormat = stream.toByteArray();
//        String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
//
//        //Decoding string to a bitmap
//        //byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
//        //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        //profilePictureImageView.setImageBitmap(decodedByte);
//
//        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
//        String uploadId = mDataBaseRef.push().getKey();
//
//
//        ImageUpload imageUpload = new ImageUpload(encodedImage);
//        //String uploadId = mDataBaseRef.push().getKey();
//       // mDataBaseRef.child(uploadId).setValue(imageUpload);
//        mDataBaseRef.child(uploadId).setValue(imageUpload).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                if(task.isSuccessful()){
//                    Toast.makeText(getApplicationContext(),"Successful", Toast.LENGTH_LONG).show();
//                }
//                else{
//                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
//                }
//            }
//        });*/
//
//
//
//
//
//    }
//
//    @Override
//    public void onClick(View view) {
//        //if the clicked button is choose
//        if (view == buttonChoose) {
//            showFileChooser();
//        }
//        //if the clicked button is upload
//        else if (view == buttonUpload) {
//            uploadFile();
//        }
//    }
//}


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Storage extends AppCompatActivity implements View.OnClickListener /*  implementing click listener */ {
    //a constant to track the file chooser intent
    private static final int PICK_IMAGE_REQUEST = 234;

    //Buttons
    private Button buttonChoose;
    private Button buttonUpload;

    //ImageView
    private ImageView imageView;
    private StorageReference storageReference;
    private DatabaseReference mDataBaseRef;

    //a Uri object to store file path
    private Uri filePath;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

    private Bitmap imagebitmap;
    private Bitmap scaled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        //getting views from layout
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        imageView = (ImageView) findViewById(R.id.imageView);

        //attaching listener
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        mDataBaseRef = FirebaseDatabase.getInstance().getReference("image");


    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data != null) {
//            filePath = data.getData();
//            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data.getData() != null) {
//                filePath = data.getData();
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                    imageView.setImageBitmap(bitmap);
//
//                } catch (IOException e) {
//                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    //this method will upload the file
    private void uploadFile() {
        String uid = user.getUid();
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference riversRef = storageReference.child("images/" + uid + "/" + filePath.getLastPathSegment());
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successful
                            //hiding the progress dialog
                            progressDialog.dismiss();
                            @SuppressWarnings("VisibleForTests")ImageUpload imageUpload = new ImageUpload(taskSnapshot.getDownloadUrl().toString());
                            String uploadId = mDataBaseRef.push().getKey();
                            mDataBaseRef.child(uploadId).setValue(imageUpload);

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage() + "ypoooooo" + filePath, Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            @SuppressWarnings("VisibleForTests")double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            Toast.makeText(getApplicationContext(), "Upload", Toast.LENGTH_LONG).show();
            //you can display an error toast
        }
    }

    @Override
    public void onClick(View view) {
        //if the clicked button is choose
        if (view == buttonChoose) {
            //showFileChooser();
            selectImage();
        }
        //if the clicked button is upload
        else if (view == buttonUpload) {
            uploadFile();
        }
    }

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Storage.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent,0);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    //Uri selectedImage = data.getData();
                    //filePath = data.getData();
                    //Toast.makeText(getApplicationContext(),""+filePath,Toast.LENGTH_SHORT).show();
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(photo);
                    //imageView.setImageURI(selectedImage);

                    //knop.setVisibility(Button.VISIBLE);


                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = getImageUri(getApplicationContext(), photo);
                    Toast.makeText(getApplicationContext(),""+tempUri,Toast.LENGTH_SHORT).show();

                    filePath = tempUri;

                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    File finalFile = new File(getRealPathFromURI(tempUri));
                    Toast.makeText(getApplicationContext(),"yo",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),""+finalFile,Toast.LENGTH_SHORT).show();



                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    //Uri selectedImage = data.getData();
                    filePath = data.getData();
                    Toast.makeText(getApplicationContext(),""+filePath,Toast.LENGTH_SHORT).show();
                    //imageView.setImageURI(filePath);



                    //System.out.println(mImageCaptureUri);



                    try {
                imagebitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                int scaleSize = (int) ( imagebitmap.getHeight() * (512.0 / imagebitmap.getWidth()) );
                scaled = Bitmap.createScaledBitmap(imagebitmap, 512, scaleSize, true);

                imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(scaled);
            } catch (IOException e) {
                e.printStackTrace();
            }








                }
                break;
        }

//        if (resultCode == RESULT_OK) {
//            if (requestCode == 1) {
//                //filePath = data.getData();
//
//                File f = new File(Environment.getExternalStorageDirectory().toString());
//                for (File temp : f.listFiles()) {
//                    if (temp.getName().equals("temp.jpg")) {
//                        f = temp;
//                        break;
//                    }
//                }
//                try {
//                    Bitmap bitmap;
//                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//
//                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
//                            bitmapOptions);
//
//                    imageView.setImageBitmap(bitmap);
//
//                    String path = android.os.Environment
//                            .getExternalStorageDirectory()
//                            + File.separator
//                            + "Phoenix" + File.separator + "default";
//                    f.delete();
//                    OutputStream outFile = null;
//                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
//                    //file.mkdir();
//                    try {
//                        outFile = new FileOutputStream(file);
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
//                        outFile.flush();
//                        outFile.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else if (requestCode == 2) {
//
//                filePath = data.getData();
//                Toast.makeText(getApplicationContext(),filePath.toString(),Toast.LENGTH_LONG).show();
//                String[] filepath = { MediaStore.Images.Media.DATA };
//                Cursor c = getContentResolver().query(filePath,filepath, null, null, null);
//                c.moveToFirst();
//                int columnIndex = c.getColumnIndex(filepath[0]);
//                String picturePath = c.getString(columnIndex);
//                c.close();
//                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//                //Log.w("Path of the image from gallery:", picturePath+"");
//                imageView.setImageBitmap(thumbnail);
//            }
//        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}




















