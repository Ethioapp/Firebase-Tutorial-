package com.ethiopia.firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MSAdapter extends  RecyclerView.Adapter<MSAdapter.ViewHolder> {
    private ArrayList<String> card ;
    private ArrayList<String> card1 ;
    private ArrayList<String> card2 ;



    public MSAdapter(ArrayList<String> card, ArrayList<String> card1, ArrayList<String> card2) {
        this.card = card;
        this.card1 = card1;
        this.card2 = card2;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MSAdapter.ViewHolder viewHolder, int i) {

        viewHolder.title.setText("Name:-"+card.get(i));
        viewHolder.title1.setText("gender:-"+card1.get(i));
        viewHolder.title2.setText("Age:-"+card2.get(i));

    }

    @Override
    public int getItemCount() {

        return card.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title,title1,title2;
        public ViewHolder(View view) {
            super(view);

            title = (TextView)view.findViewById(R.id.card);
            title1 = (TextView)view.findViewById(R.id.card1);
            title2 = (TextView)view.findViewById(R.id.card2);

        }
    }


}
