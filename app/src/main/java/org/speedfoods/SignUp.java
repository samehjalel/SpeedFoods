package org.speedfoods;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.neilijalel.speedfoods.Model.User;
import com.example.neilijalel.speedfoods.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

    MaterialEditText editPhone,editName,editPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editName=(MaterialEditText)findViewById(R.id.editName);
        editPhone=(MaterialEditText)findViewById(R.id.editPhone);
        editPassword=(MaterialEditText)findViewById(R.id.editPassword);


        btnSignUp=(Button)findViewById(R.id.btnSignUp);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Wait...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if already user phone exists

                        if(dataSnapshot.child(editPhone.getText().toString()).exists())
                        {

                            mDialog.dismiss();
                            Toast.makeText(SignUp.this,"Phone Number Already register",Toast.LENGTH_SHORT).show();
                        }
                        else

                        {

                            mDialog.dismiss();
                            User user=new User(editName.getText().toString(),editPassword.getText().toString());
                            table_user.child(editPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this,"Sign Up Sucessfully",Toast.LENGTH_SHORT).show();
                            finish();

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
