 package ba.sum.fpmoz.rkoturic.sell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    //zbog dostupnosti ih deklariramo odmah u klasi, ali ih instaciramo u onCreate metodi 
    private FirebaseAuth mAuth;
    private EditText emailInp;
    private EditText passwordInp;
    private Button loginBtn;
    private Button registerBtn;
    private TextView loginTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));

        this.emailInp = findViewById(R.id.emailInp);
        this.passwordInp = findViewById(R.id.passwordInp);
        this.loginBtn = findViewById(R.id.loginBtn);
        this.registerBtn = findViewById(R.id.registerBtn);
        this.loginTxt = findViewById(R.id.loginTxt);

        this.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInp.getText().toString();
                String password = passwordInp.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                            startActivity(i);
                        } else {
                            loginTxt.setText("neuspješna prijava");
                        }
                    }
                });

            }
        });

        this.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("For sale");

    }


}