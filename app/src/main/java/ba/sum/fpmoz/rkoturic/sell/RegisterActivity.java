package ba.sum.fpmoz.rkoturic.sell;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button loginBtn2, regBtn;
    private EditText nameInp, emailInp2, passInp, passInp2;
    private TextView messageTxt;

    private ImageView profilePicture;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange)));

        loginBtn2 = findViewById(R.id.loginBtn2);
        regBtn = findViewById(R.id.regBtn);
        nameInp = findViewById(R.id.nameInp);
        emailInp2 = findViewById(R.id.emailInp2);
        passInp = findViewById(R.id.passInp);
        passInp2 = findViewById(R.id.passInp2);
        messageTxt = findViewById(R.id.messageTxt);
        profilePicture = findViewById(R.id.profilePicture);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        this.loginBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        this.regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String displayName = nameInp.getText().toString();
                String email = emailInp2.getText().toString();
                String password = passInp.getText().toString();
                String passwordCnf = passInp2.getText().toString();
                if (!password.equals(passwordCnf)) {
                    messageTxt.setText("Lozinke se ne podudaraju");
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                UserProfileChangeRequest changeRequest = new UserProfileChangeRequest
                                        .Builder()//preko buildera se radi update profila... Mozemo staviti i neku sliku
                                        .setDisplayName(displayName).build();
                                user.updateProfile(changeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            nameInp.setText("");
                                            emailInp2.setText("");
                                            passInp.setText("");
                                            passInp2.setText("");
                                            messageTxt.setText("Uspješno ste se registrirali");
                                            Log.d("Poruka", "Profil je ažuriran");
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(getApplicationContext(), "Nastala je greška prilikom registracije na sustav" + task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }

}