package ba.sum.fpmoz.rkoturic.sell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    private ImageView profilePictureUser, showMsgBtn, addNewBtn, listProdBtn;
    private Button logOutBtn;
    private TextView userMailLbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        showMsgBtn = findViewById(R.id.showMsgBtn);
        addNewBtn = findViewById(R.id.addNewBtn);
        listProdBtn = findViewById(R.id.listProdBtn);
        logOutBtn = findViewById(R.id.logOutBtn);

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        showMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MessageActivity.class);
                startActivity(i);
            }
        });

        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddItemForSale.class);
                startActivity(i);
            }
        });

        listProdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ProductsActivity.class);
                startActivity(i);
            }
        });
    }
}