package vn.edu.hcmut.linexo.presentation.view.play;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.presentation.model.Message;

/**
 * Created by Anh on 3/13/2019.
 */

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.RecyclerViewHolder> {

    private List<Message> messages = new ArrayList<>();
    private String Uid;

    public ChatRecyclerViewAdapter(List<Message> messages, String Uid) {
        this.messages = messages;
        this.Uid = Uid;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_chat_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.itemView.setMessageView(
                messages.get(position).getId(),
                messages.get(position).getName(),
                messages.get(position).getAvatarURL(),
                messages.get(position).getMessage(),
                Uid
        );
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ChatViewLayout itemView;

        public RecyclerViewHolder(View itemMessageView) {
            super(itemMessageView);
            this.itemView = (ChatViewLayout) itemMessageView;
        }
    }

    public void updateMessageListItems(List<Message> messages) {
        final MessageDiffCallBack diffCallback = new MessageDiffCallBack(this.messages, messages);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.messages.clear();
        this.messages.addAll(messages);
        diffResult.dispatchUpdatesTo(this);
    }
}