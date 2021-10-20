package ba.sum.fpmoz.rkoturic.sell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ba.sum.fpmoz.rkoturic.sell.adapters.ImageAdapter;
import ba.sum.fpmoz.rkoturic.sell.model.Upload;

public class ProductsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    //iz reference ćemo dobiti iteme koje kasnije stavljamo u listu
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange)));

        mRecyclerView = findViewById(R.id.productsRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();
        //popunjavanje produktima
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                  Upload upload = postSnapshot.getValue(Upload.class);
                  mUploads.add(upload);
              }
              mAdapter = new ImageAdapter(ProductsActivity.this, mUploads);

              mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProductsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}