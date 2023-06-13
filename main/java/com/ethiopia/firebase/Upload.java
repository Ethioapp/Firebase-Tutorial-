package com.ethiopia.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class Upload extends AppCompatActivity {
    Button up, load ;
    ImageView image;
    ProgressDialog progressDialog;
    StorageReference storageReference;
    StorageTask storageTask;
     Uri mImageUri;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String filename,name,extension;
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);

        up=findViewById(R.id.upload);
        load=findViewById(R.id.load);
        image=findViewById(R.id.img);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Student Data");
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                //intent.setType("image/*");
            intent.setType("file/*");
          //   intent.setType("application/pdf");
              //  intent.setType("application/zip");
                  intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);


            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ms();
            }
        });
    }

    private void ms() {

        storageReference = storageReference.child("Image/"+name);
        progressDialog = new ProgressDialog(Upload.this);
        progressDialog.setTitle("Uploading Image");
        progressDialog.setMessage("Uploading to Image to the Cloud\n Please wait!!!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        storageTask = storageReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        if (taskSnapshot.getMetadata() != null) {
                            if (taskSnapshot.getMetadata().getReference() != null) {
                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imag = uri.toString();
                                        myRef.child("2").child("imageUrl").setValue(imag);

                                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Your Image  Upload Successfully ", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                        progressDialog.cancel();
                                    ///    finish();
                                    }
                                }); } }
                    }

                })                    .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Upload.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setProgress((int) progress);
                        progressDialog.setMessage("Uploading Image "+progress+" %");
                    }
                });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            String path = new File(data.getData().getPath()).getAbsolutePath();
            if(path != null){
                mImageUri = data.getData();


                Cursor cursor = getContentResolver().query(mImageUri,null,null,null,null);

                if(cursor == null) filename=mImageUri.getPath();
                else{
                    cursor.moveToFirst();
                    int idx = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME);
                    filename = cursor.getString(idx);
                    cursor.close();
                }
                name = filename.substring(filename.lastIndexOf("/")+1);
                extension = filename.substring(filename.lastIndexOf("."));
            }
            image.setImageURI(mImageUri);
        }
    }



}