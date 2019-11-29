package com.techcom.entrymanagementsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VisitorAdapter extends RecyclerView.Adapter<VisitorAdapter.VisitorViewHolder>{
    private Context mCtx;
    private List<Visitor> VisitorList;

    public VisitorAdapter(Context mCtx, List<Visitor> artistList) {
        this.mCtx = mCtx;
        this.VisitorList = artistList;
    }
    @NonNull
    @Override
    public VisitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_visitor, parent, false);

        return new VisitorViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull VisitorViewHolder holder, int position) {
        Visitor visitor = VisitorList.get(position);
        holder.textViewName.setText("Name: "+visitor.getName());
        holder.textViewEmail.setText("Email: " + visitor.getEmail());
        holder.textViewPhone.setText("Phone: " + visitor.getPhone());
        holder.textViewTime.setText("Entry Time: " + visitor.getTime());
    }

    @Override
    public int getItemCount() {
        return VisitorList.size();
    }

    class VisitorViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewEmail, textViewPhone, textViewTime;

        public  VisitorViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewEmail = itemView.findViewById(R.id.text_view_email);
            textViewPhone = itemView.findViewById(R.id.text_view_phone);
            textViewTime = itemView.findViewById(R.id.text_view_entry_time);
        }


    }
}
