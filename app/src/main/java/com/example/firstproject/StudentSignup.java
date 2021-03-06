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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentSignup extends AppCompatActivity {
    TextInputLayout reg_email, reg_fullname, reg_class, reg_address, reg_phonenumber, reg_password;
    private static final int CHOOSE_IMAGE = 101;
    ImageView imageView;
    Button StudentRegisterBtn;
    FirebaseAuth mAuth;
    String PicUrl;
    RadioButton male, female;
    TextView Text;
    ProgressBar progressBar;
    String gender;
    String imageurl;

    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);
        reg_email = findViewById(R.id.RegisterStudentEmailAddress);
        reg_fullname = findViewById(R.id.RegisterStudentName);
        reg_class = findViewById(R.id.RegisterStudentClass);
        reg_address = findViewById(R.id.RegisterStudentAddress);
        reg_phonenumber = findViewById(R.id.RegisterStudentPhonenumber);
        reg_password = findViewById(R.id.RegisterStudentPassword);
        imageView = findViewById(R.id.studentimage);
        StudentRegisterBtn = findViewById(R.id.registerstudent);
        progressBar = findViewById(R.id.reg_progress);
        male = findViewById(R.id.malestudent);
        female = findViewById(R.id.femalestudent);
        Text = findViewById(R.id.imagetext);
        mAuth = FirebaseAuth.getInstance();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showimagechooser();
                saveuserinfo();
                uploadimagetofirebasestorage();
                Text.setVisibility(View.GONE);

            }
        });

        StudentRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationofvalues();

            }
        });

    }


    private void saveuserinfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && PicUrl != null) {

            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(PicUrl)).build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(StudentSignup.this, "Pic url save", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
                uploadimagetofirebasestorage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadimagetofirebasestorage() {
        // progressBar.setVisibility(View.VISIBLE);
        final StorageReference profileimageref = FirebaseStorage.getInstance().getReference("StudentProfilePics/" + System.currentTimeMillis() + "jpg");
        if (uri != null) {
            profileimageref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                     while (!uriTask.isComplete());
                     Uri urlimage = uriTask.getResult();
                     imageurl = urlimage.toString();
                    Toast.makeText(StudentSignup.this, "Image Uploaded", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void showimagechooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);

    }

    public void validationofvalues() {
        final String email = reg_email.getEditText().getText().toString().trim();
        String password = reg_password.getEditText().getText().toString().trim();
        final String phonenumber = reg_phonenumber.getEditText().getText().toString().trim();
        final String address = reg_address.getEditText().getText().toString().trim();
        final String Fullname = reg_fullname.getEditText().getText().toString().trim();
        final String studentclass = reg_class.getEditText().getText().toString().trim();

        // Validation of Input Fields

        if (TextUtils.isEmpty(email)) {
            reg_email.setError("Email Is Required");

        }
        if (TextUtils.isEmpty(password)) {
            reg_password.setError("Password is Required");

        }
        if (password.length() < 6) {
            reg_password.setError("Password must be greater than 6 letters");
        }
        if (TextUtils.isEmpty(phonenumber)) {
            reg_phonenumber.setError("Phone Number Is Required");

        }
        if (TextUtils.isEmpty(address)) {
            reg_address.setError("Address Is Required");

        }
        if (TextUtils.isEmpty(Fullname)) {
            reg_fullname.setError("FullName Is Required");
        }
        if (TextUtils.isEmpty(studentclass)) {
            reg_class.setError("Class Is Required");
        }
        if (male.isChecked()) {
            gender = "Male";
        }
        if (female.isChecked()) {
            gender = "Female";
        }
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.VISIBLE);
                if (task.isSuccessful()) {
                    Toast.makeText(StudentSignup.this, "Create Kaar Leta", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(StudentSignup.this, "Verification Email Has Been Sent....Check Your Inbox", Toast.LENGTH_SHORT).show();
                        }
                    });

                    DBhelperStudent student = new DBhelperStudent(imageurl, Fullname, studentclass, address, phonenumber, email, gender);
                    FirebaseDatabase.getInstance().getReference("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(StudentSignup.this, "Values Inserted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(StudentSignup.this, StudentLogin.class);
                                startActivity(intent);
                                finish();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are Already Register", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });


    }

    public void movetologin(View view) {
        Intent intent = new Intent(this, StudentLogin.class);
        startActivity(intent);
        finish();
    }
}
