package com.robertthomure.rt_mob_app_proj2.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.robertthomure.rt_mob_app_proj2.Entity.TermEntity;
import com.robertthomure.rt_mob_app_proj2.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;

        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.termTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final TermEntity selected = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetails.class);
                    TermDetails.returnTermId = selected.getTermId();
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<TermEntity> mTerms;

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TermViewHolder holder, int position) {
        if(mTerms != null) {
            final TermEntity selected = mTerms.get(position);
            holder.termItemView.setText(selected.getTermName());
        } else {
            holder.termItemView.setText("No Word");
        }
    }

    @Override
    public int getItemCount() {
        if(mTerms != null) {
            return  mTerms.size();
        } else return 0;
    }

    public void setWords(List<TermEntity> words) {
        mTerms = words;
        notifyDataSetChanged();
    }

}
