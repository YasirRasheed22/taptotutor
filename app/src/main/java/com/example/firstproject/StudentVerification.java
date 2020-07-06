package com.example.firstproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class StudentVerification extends AppCompatActivity {
    TextInputLayout sdisplayname;
    private static final int CHOOSE_IMAGE = 101;
    TextView textView, textView2;
    ImageView imageView;
    Button savebtn, resend;
    Uri uriprofileimage;
    FirebaseAuth fAuth;
    String Profileimageurl;
    ProgressBar progressBar, progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_verification);
        fAuth = FirebaseAuth.getInstance();
        imageView = findViewById(R.id.profileimagechoose);
        progressBar = findViewById(R.id.imageprogress);
        progressBar2 = findViewById(R.id.saveprocess);
        textView = findViewById(R.id.text1);
        textView2 = findViewById(R.id.emailverification);
        resend = findViewById(R.id.resendemail);
        textView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        final FirebaseUser user = fAuth.getCurrentUser();

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showimagechooser();
            }
        });
        findViewById(R.id.savepic).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                progressBar2.setVisibility(View.VISIBLE);
                if (!user.isEmailVerified()) {
                    textView2.setVisibility(View.VISIBLE);
                    resend.setVisibility(View.VISIBLE);
                    Toast.makeText(StudentVerification.this, "You Have To Verify Email For Futher Process", Toast.LENGTH_SHORT).show();
                    progressBar2.setVisibility(View.GONE);
                    resend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(StudentVerification.this, "Verification Email Has Been Sent", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
                if (user.isEmailVerified()) {
                   uploadimagetofirebasestorage();
                   saveuserinfo();
                 startActivity(new Intent(StudentVerification.this,StudentDashboard.class));
                 finish();
                }

            }

        });


    }

    private void saveuserinfo() {
        FirebaseUser user = fAuth.getCurrentUser();
        if (user !=null && Profileimageurl !=null){

            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(Profileimageurl)).build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){

                        Toast.makeText(StudentVerification.this, "Pic url save", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data !=null && data.getData() !=null){
             uriprofileimage = data.getData();
             try {
                 Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriprofileimage);
                imageView.setImageBitmap(bitmap);
                uploadimagetofirebasestorage();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
    }

    private void uploadimagetofirebasestorage() {
        progressBar.setVisibility(View.VISIBLE);
        final StorageReference profileimageref = FirebaseStorage.getInstance().getReference("StudentProfilePics/"+System.currentTimeMillis() + "jpg");
        if(uriprofileimage !=null){
            profileimageref.putFile(uriprofileimage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    textView.setText("Image Selected");
               Profileimageurl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                }
            });
        }
    }

    private void showimagechooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Image"),CHOOSE_IMAGE);

    }

}