package com.example;


import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;


import com.example.utils.CommonHelper;
import com.example.utils.LambaExpr;

public class MainActivity extends Activity {

    Button btnAnnonyme, btnInterimaire, btnEmployeur;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.blue));

        CommonHelper.addReturnBtnOnImg(this);
        btnAnnonyme = (Button) findViewById(R.id.button_annonyme);
        btnInterimaire = (Button) findViewById(R.id.button_interimaire);
        btnEmployeur = (Button) findViewById(R.id.button_employeur);


        btnAnnonyme.setOnClickListener(view -> {
            //CommonHelper.makeNotification(this, "Test tile", "Some text for notification here", R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
            CommonHelper.changeActivity(this, new Candidate_ListOffersActivity());

        });

        btnInterimaire.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Candidate_LoginActivity());
        });

        btnEmployeur.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Employer_LoginActivity());
        });

        LambaExpr lambaExprSyncYes = () -> {
        };

        // Démarrer les animations
        animateBackground();
    }

    private void animateBackground() {
        final ImageView backgroundOne = (ImageView) findViewById(R.id.background_one);
        final ImageView backgroundTwo = (ImageView) findViewById(R.id.background_two);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(20000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundOne.getWidth();
                final float translationX = width * progress;
                backgroundOne.setTranslationX(translationX);
                backgroundTwo.setTranslationX(translationX - width);
            }
        });
        animator.start();
    }

    /*private void animateBackground() {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        TranslateAnimation animation = new TranslateAnimation(0, -random.nextInt(screenWidth), 0, -random.nextInt(screenHeight));
        animation.setDuration(10000); // Durée de l'animation en millisecondes
        animation.setRepeatCount(Animation.INFINITE); // Animation en boucle
        animation.setRepeatMode(Animation.REVERSE); // Inversion à la fin de chaque itération
        scrollView.startAnimation(animation);
    }*/
}