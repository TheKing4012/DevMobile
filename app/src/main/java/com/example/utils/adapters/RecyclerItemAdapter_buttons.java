package com.example.utils.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.employer.Employer_CandidateProfilActivity;
import com.example.utils.RecyclerItem;
import com.example.utils.entities.Candidate;
import com.example.utils.helpers.CommonHelper;

import java.util.List;

public class RecyclerItemAdapter_buttons extends RecyclerView.Adapter<RecyclerItemAdapter_buttons.ViewHolder> {

    private List<RecyclerItem> recyclerItemList;
    private OnItemClickListener onItemClickListener;
    private Activity activity;
    private String textProfile = "";

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RecyclerItemAdapter_buttons(List<RecyclerItem> recyclerItemList) {
        this.recyclerItemList = recyclerItemList;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setTextProfile(String textProfile) {
        this.textProfile = textProfile;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_buttons, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecyclerItem item = recyclerItemList.get(position);
        Candidate candidate = item.getCandidate();

        holder.textView1.setText(item.getText1());

        String text = textProfile;
        SpannableString spannableString = new SpannableString(text);

        int start = -1;
        int end;
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Employer_CandidateProfilActivity.class);
                intent.putExtra("candidate", candidate);
                activity.startActivity(intent);
            }
        };

        // Appliquer le style et le ClickableSpan
        spannableString.setSpan(clickableSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD | Typeface.ITALIC), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Définir la couleur du texte
        spannableString.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.blue)), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.textView2.setText(spannableString);
        holder.textView2.setMovementMethod(LinkMovementMethod.getInstance());

        holder.imageCheck.setVisibility(View.VISIBLE);
        holder.imageCheck.setImageResource(R.drawable.check);
        holder.imageDeny.setVisibility(View.VISIBLE);
        holder.imageDeny.setImageResource(R.drawable.deny);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(adapterPosition);
                        Intent intent = new Intent(activity, Employer_CandidateProfilActivity.class);
                        intent.putExtra("candidate", candidate);
                        activity.startActivity(intent);
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return recyclerItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        ImageView imageCheck;
        ImageView imageDeny;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            imageCheck = itemView.findViewById(R.id.button_accept);
            imageDeny = itemView.findViewById(R.id.button_deny);
        }
    }
}
