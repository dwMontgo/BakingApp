package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.StepList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {

    private final Context context;
    private final StepList[] stepList;

    private OnItemClickListener clickListener;

    public StepAdapter(Context context, StepList[] steps) {

        this.context = context;
        stepList = steps;
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.step_item, parent, false);
        return new StepHolder(v);
    }

    @Override
    public void onBindViewHolder(StepHolder stepHolder, int position) {
        stepHolder.stepLabelTextView.setText(stepList[position].getShortDescription());

    }

    @Override
    public int getItemCount() {
        return stepList.length;
    }

    public interface OnItemClickListener {
        void onStepClicked(int position);
    }

    public class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.step_card)
        CardView cardView;
        @BindView(R.id.step_short_description)
        TextView stepLabelTextView;

        StepHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onStepClicked(getAdapterPosition());

                notifyDataSetChanged();
            }
        }
    }
}

