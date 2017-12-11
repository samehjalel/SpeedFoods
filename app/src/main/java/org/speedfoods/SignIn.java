package org.speedfoods;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.neilijalel.speedfoods.Model.User;
import com.example.neilijalel.speedfoods.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {

    EditText editPhone,editPassword;
    Button btnSignIn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editPassword = (MaterialEditText) findViewById(R.id.editPassword);
        editPhone = (MaterialEditText) findViewById(R.id.editPhone);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        //init firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Wait...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user exists in database

                        if (dataSnapshot.child(editPhone.getText().toString()).exists()) {
                            //Get User Information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(editPhone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(editPassword.getText().toString())) {

                                Toast.makeText(SignIn.this, "Sign in sucessfully", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(SignIn.this, "Sign in Failed", Toast.LENGTH_SHORT).show();
                            }


                        } else {

                            mDialog.dismiss();

                            Toast.makeText(SignIn.this, "User not exist in Database", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
    }
    }

