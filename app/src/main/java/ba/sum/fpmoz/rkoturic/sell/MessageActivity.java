package ba.sum.fpmoz.rkoturic.sell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ba.sum.fpmoz.rkoturic.sell.adapters.MessageAdapter;
import ba.sum.fpmoz.rkoturic.sell.model.Message;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView messageRecyclerView;
    private MessageAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private List<Message> mMess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        messageRecyclerView = findViewById(R.id.messageRecyclerView);
        messageRecyclerView.setHasFixedSize(true);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMess = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("messages/articleMessage");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Message message = postSnapshot.getValue(Message.class);
                    mMess.add(message);
                }
                mAdapter = new MessageAdapter(mMess, MessageActivity.this);

                messageRecyclerView.setAdapter(mAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MessageActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}