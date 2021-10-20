package ba.sum.fpmoz.rkoturic.sell.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.List;

import ba.sum.fpmoz.rkoturic.sell.R;
import ba.sum.fpmoz.rkoturic.sell.model.Message;
import ba.sum.fpmoz.rkoturic.sell.model.Upload;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private String link;
    private DatabaseReference mDatabaseRef;

    public ImageAdapter(Context context, List<Upload> uploads) {
        this.mContext = context;
        this.mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        //final String productKey = getRef(position).getKey();


        //Picasso.with(mContext).load(uploadCurrent.getmImageUrl()).fit().centerCrop().into(holder.imageView);
        Glide.with(mContext).asBitmap().load(uploadCurrent.getmImageUrl()).into(holder.imageView);

        holder.textViewName.setText(uploadCurrent.getName());
        holder.txtDescription.setText(mUploads.get(position).getDescription());
        holder.txtPrice.setText(mUploads.get(position).getPrice() + "€");

        if (mUploads.get(position).isExpanded()) {
            //Transition manager je zaduzen za neku vrsu animacije
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);
        }else {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelLayout.setVisibility(View.GONE);
            holder.downArrow.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName, showProductName;
        public ImageView imageView;
        private CardView parent;
        private ImageView downArrow, upArrow;
        private RelativeLayout expandedRelLayout;
        private TextView txtPrice, txtDescription;
        private EditText addressInp;
        private Button orderBtn;
        private DatabaseReference mDatabaseRefMessage;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            textViewName = itemView.findViewById(R.id.productNameTxt);
            imageView = itemView.findViewById(R.id.productImg);
            downArrow = itemView.findViewById(R.id.btnDownArrow);
            upArrow = itemView.findViewById(R.id.btnUpArrow);
            expandedRelLayout = itemView.findViewById(R.id.expandedReyLayout);
            txtPrice = itemView.findViewById(R.id.productPriceTxt);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            addressInp = itemView.findViewById(R.id.addressInp);
            orderBtn = itemView.findViewById(R.id.orderBtn);
            showProductName = itemView.findViewById(R.id.showProductName);
            mDatabaseRefMessage = FirebaseDatabase.getInstance().getReference("messages/articleMessage");

            orderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Upload u = mUploads.get(getAdapterPosition());
                    String reciver = u.getmUser();
                    String mOrderAdress = addressInp.getText().toString();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String mName = u.getName();
                    mDatabaseRefMessage.push().setValue(new Message(user.getUid(), mOrderAdress, reciver, mName));
                    //Message m = new Message(mOrderAdrress,"ASDASD",);
                    //ref.push().setValue(new Student(studentName,studentSurname, studentUid));
                }
            });

            downArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Upload u = mUploads.get(getAdapterPosition());
                    u.setExpanded(!u.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            upArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Upload u = mUploads.get(getAdapterPosition());
                    u.setExpanded(!u.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
