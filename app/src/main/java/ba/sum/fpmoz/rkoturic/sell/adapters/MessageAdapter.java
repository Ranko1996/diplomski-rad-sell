package ba.sum.fpmoz.rkoturic.sell.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import ba.sum.fpmoz.rkoturic.sell.R;
import ba.sum.fpmoz.rkoturic.sell.model.Message;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

   //private static final int INCORRECT_RECIVER = 0;
    //private static final int CORRECT_RECIVER = 1;
    private List<Message> mMessages;
    private Context mContext;
    MessageAdapter messageAdapter;


    FirebaseUser fUser;

    public MessageAdapter(List<Message> mMessages, Context mContext) {
        this.mMessages = mMessages;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(mMessages.get(position).getReciver().equals(user.getUid())){
            Message m = mMessages.get(position);
            holder.showMessage.setText(m.getAdress());
            holder.showProductName.setText(m.getName());
        }else {
            holder.messageRel.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }


    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView showMessage, showProductName;
        public RelativeLayout messageRel;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            showMessage = itemView.findViewById(R.id.showMessage);
            showProductName = itemView.findViewById(R.id.showProductName);
            messageRel = itemView.findViewById(R.id.messageRel);
        }
    }

    /*@Override
    public int getItemViewType(int position) {
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mMessages.get(position).getReciver().equals(fUser.getUid())){
            return CORRECT_RECIVER;
        }else {
            return INCORRECT_RECIVER;
        }
    }*/
}
