package com.example.softteam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://softteam-88b9b-default-rtdb.firebaseio.com/");
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText name = findViewById(R.id.name);
        final EditText mobile = findViewById(R.id.number);
        final EditText email = findViewById(R.id.email);
        final AppCompatButton reg = findViewById(R.id.button2);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        if (!MemoryData.getData(this).isEmpty()) {
            Intent intent = new Intent(Register.this, MainActivity.class);
            intent.putExtra("Mobile", MemoryData.getData(this));
            intent.putExtra("Name", MemoryData.getName(this));
            intent.putExtra("Email", " ");
            startActivity(intent);
            finish();
        }

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nametxt = name.getText().toString();
                final String mobiletxt = mobile.getText().toString();
                final String emailtxt = email.getText().toString();

                if (nametxt.isEmpty() || mobiletxt.isEmpty() || emailtxt.isEmpty()) {
                    Toast.makeText(Register.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show(); // Show progress dialog

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child("users").hasChild(mobiletxt)) {
                                progressDialog.dismiss(); // Dismiss progress dialog
                                Toast.makeText(Register.this, "Mobile already exists", Toast.LENGTH_SHORT).show();
                            } else {
                                // Set user data in Firebase
                                databaseReference.child("users").child(mobiletxt).child("Email").setValue(emailtxt);
                                databaseReference.child("users").child(mobiletxt).child("Name").setValue(nametxt);

                                // Save data to memory
                                MemoryData.saveData(mobiletxt, Register.this);
                                MemoryData.saveName(nametxt, Register.this);

                                progressDialog.dismiss(); // Dismiss progress dialog
                                Toast.makeText(Register.this, "Registered successfully", Toast.LENGTH_SHORT).show();

                                // Start MainActivity
                                Intent intent = new Intent(Register.this, MainActivity.class);
                                intent.putExtra("Mobile", mobiletxt);
                                intent.putExtra("Name", nametxt);
                                intent.putExtra("Email", emailtxt);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            progressDialog.dismiss(); // Dismiss progress dialog
                            Toast.makeText(Register.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}

