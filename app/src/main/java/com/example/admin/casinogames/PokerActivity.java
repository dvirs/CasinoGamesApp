package com.example.admin.casinogames;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class PokerActivity extends Activity {

    private final int NUM_USER_CARDS = 2;
    private final int NUM_FLOP_CARDS = 5;

    private Bundle bundle;
    private int couter = 0; //count number of clicks on bet button

    private ImageView cards;
    private Button bet;
    private SeekBar moneySk;
    private TextView betMoney;

    private int[] viewUserIds = {R.id.user_card1, R.id.user_card2};
    private int[] viewFlopIde = {R.id.flop_card1, R.id.flop_card2,
            R.id.flop_card3, R.id.flop_card4, R.id.flop_card5};

    private int[] animationUserIds = {R.anim.translate_user1, R.anim.translate_user2};
    private int[] animationFlopIds = {R.anim.translate_flop1, R.anim.translate_flop2,
            R.anim.translate_flop3, R.anim.translate_flop4, R.anim.translate_flop5, R.anim.flip};

    private ArrayList<Integer> allCards = new ArrayList(Arrays.asList(R.drawable.heart_2, R.drawable.heart_3,
            R.drawable.heart_4, R.drawable.heart_5, R.drawable.heart_6, R.drawable.heart_7,
            R.drawable.heart_8));
    //R.drawable.heart_9, R.drawable.heart_10, R.drawable.heart_j, R.drawable.heart_a};

    private ArrayList<ImageView> userCards = new ArrayList<ImageView>();
    private ArrayList<ImageView> flopCards = new ArrayList<ImageView>();
    private ArrayList userInfo;

    private Animation translate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poker);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        userInfo = (ArrayList) bundle.get("userinfo");

        setViewOfFields();
        dealer();
        setButtonClickable();

        moneySk.setVisibility(View.INVISIBLE);
        betMoney.setVisibility(View.INVISIBLE);
        moneySk.setMax((int) userInfo.get(4)); //set total money for max bet


    }

    private void setViewOfFields() {
        cards = (ImageView) findViewById(R.id.cards);
        bet = (Button) findViewById(R.id.play);
        moneySk = (SeekBar) findViewById(R.id.money_poker);
        betMoney = (TextView) findViewById(R.id.bet_poker_txt);
    }

    private void setButtonClickable() {

        bet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                couter++;
                moneySk.setVisibility(View.VISIBLE);
                betMoney.setVisibility(View.VISIBLE);
                bet.setText("Bet");

                int card = -1;
                if(couter == 1) {
                    //flip user cards
                    for (int i = 0; i < NUM_USER_CARDS; i++) {
                        flipCard(userCards.get(i));
                    }
                }

                else if(couter == 2) {
                    //flip flop cards
                    for(int i = 0; i < 3; i++) {
                        flipCard(flopCards.get(i));
                    }
                }

                else if(couter == 3) {
                    flipCard(flopCards.get(3));
                }

                else if(couter == 4) {
                    flipCard(flopCards.get(4));
                }

                else{

                }
            }

        });

        moneySk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                betMoney.setText("Bet: " + progress + "$");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void dealer() {
        //user cards
        for(int i = 0; i < NUM_USER_CARDS; i++) {
            userCards.add(i, (ImageView) findViewById(viewUserIds[i]));
            userCards.get(i).setImageResource(R.drawable.back_card);
            translate = AnimationUtils.loadAnimation(PokerActivity.this, animationUserIds[i]);
            userCards.get(i).startAnimation(translate);
        }

        //flop cards
        for(int i = 0; i < NUM_FLOP_CARDS; i++) {
            flopCards.add(i, (ImageView) findViewById(viewFlopIde[i]));
            flopCards.get(i).setImageResource(R.drawable.back_card);
            translate = AnimationUtils.loadAnimation(PokerActivity.this, animationFlopIds[i]);
            flopCards.get(i).startAnimation(translate);
        }
    }

    private int randomCard() {
        int card = -1;
        boolean flag1 = false, flag2 = false;
        Random random = new Random();
        while (true) {
            card = random.nextInt(allCards.size());

            for(int i = 0; i < userCards.size(); i++) {
                Log.e("debug", "card = " + card +
                        " r = " + allCards.get(card) +
                        " a = " + getResources().getResourceName(allCards.get(card)) +
                        " b = " + userCards.get(i).getDrawable() +
                        " d = " + userCards.get(i).getResources() +
                        " c = " + getDrawable(allCards.get(card)));
                if(getDrawable(allCards.get(card)) == userCards.get(i).getDrawable()) {
                    flag1 = true;
                }
            }
            for(int i = 0; i < flopCards.size()-1; i++) {
                if(flopCards.get(i).getBackground() == getResources().getDrawable(allCards.get(card))) {
                    flag2 = true;
                }
            }
            if((flag1 == false) && (flag2 == false)) {
                return card;
            }
        }
    }


    private void flipCard(final ImageView imageView) {
        ObjectAnimator flip = (ObjectAnimator) AnimatorInflater.loadAnimator(PokerActivity.this,
                animationFlopIds[animationFlopIds.length - 1]);
        final int card = randomCard();
        flip.setTarget(imageView);
        flip.setDuration(5000);
        flip.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageView.setImageResource(allCards.get(card));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        flip.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_poker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
