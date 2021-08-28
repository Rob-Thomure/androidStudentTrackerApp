package com.robertthomure.rt_mob_app_proj2.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.robertthomure.rt_mob_app_proj2.Entity.AssessmentEntity;
import com.robertthomure.rt_mob_app_proj2.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.termTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final AssessmentEntity selected = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    AssessmentDetails.returnAssessmentId = selected.getAssessmentId();

                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<AssessmentEntity> mAssessments;

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentViewHolder holder, int position) {
        if(mAssessments != null) {
            final AssessmentEntity selected = mAssessments.get(position);
            holder.assessmentItemView.setText(selected.getAssessmentName());
        } else {
            holder.assessmentItemView.setText("No Word");
        }
    }

    @Override
    public int getItemCount() {
        if(mAssessments != null) {
            return mAssessments.size();
        } else {
            return 0;
        }
    }

    public void setWords(List<AssessmentEntity> words) {
        mAssessments = words;
        notifyDataSetChanged();
    }
}