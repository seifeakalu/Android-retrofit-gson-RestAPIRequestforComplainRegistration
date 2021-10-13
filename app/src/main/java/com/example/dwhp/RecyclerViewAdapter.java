package com.example.dwhp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dwhp.model.MessageContent;


import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onUpdateAgreedCount(int position);

        void onUpdateDisagreedCount(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        System.out.println("at least here");
        mListener = listener;
    }
    private ArrayList<MessageContent> messageDataArrayList;


    public RecyclerViewAdapter(ArrayList<MessageContent> messageDataArrayList, Context mcontext) {
        this.messageDataArrayList = messageDataArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new RecyclerViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        MessageContent modal = messageDataArrayList.get(position);
        holder.message.setText(modal.getMessage());
        holder.messageBody.setText(modal.getSubject());
        holder.agreeCount.setText(modal.getAgreeCount());
        holder.disagreeCount.setText(modal.getDisagreeCount());
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return messageDataArrayList.size();
    }
    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our views.
        private TextView message, messageBody;
        private ImageView courseIV;
        private TextView agreeCount,disagreeCount;
        private ImageView agreeImage, disagreeImage;

        public RecyclerViewHolder(View itemView,final OnItemClickListener listener) {
            super(itemView);
            // initializing our views with their ids.

            message = itemView.findViewById(R.id.idMessage);
            messageBody = itemView.findViewById(R.id.idBody);
            agreeImage = itemView.findViewById(R.id.i_agree);
            disagreeImage = itemView.findViewById(R.id.i_disagree);
            agreeCount = itemView.findViewById(R.id.idAgree);
            disagreeCount = itemView.findViewById(R.id.idDisAgree);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            agreeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onUpdateAgreedCount(position);
                        }
                    }
                }
            });
            disagreeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onUpdateDisagreedCount(position);
                        }
                    }
                }
            });

        }
    }
}

