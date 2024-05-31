package com.example.utils.animators;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

public class FadeItemAnimator extends DefaultItemAnimator {

    private final Context context;

    public FadeItemAnimator(Context context) {
        this.context = context;
    }

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        final int marginInPixels = dpToPx(10); // Convertir 10dp en pixels

        holder.itemView.animate()
                .alpha(0)
                .translationY(-(holder.itemView.getHeight() / 2 + marginInPixels)) // Ajouter la marge à la translation
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        holder.itemView.setAlpha(1);
                        holder.itemView.setTranslationY(0);
                        dispatchRemoveFinished(holder);
                    }
                }).start();
        return true;
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    // Override des autres méthodes si nécessaire

}
