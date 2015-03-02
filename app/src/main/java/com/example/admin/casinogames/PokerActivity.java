package com.example.admin.casinogames;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private final int USED_CARD = -100;

    private Bundle bundle;
    private int couter = 0; //count number of clicks on bet button

    private ImageView cards;
    private Button betBtn,foldBtn,checkBtn;
    private SeekBar moneySk;
    private TextView betMoney, totalBetTitle;

    private int[] viewUserIds = {R.id.user_card1, R.id.user_card2};
    private int[] viewDealerIds = {R.id.dealer_card1, R.id.dealer_card2};
    private int[] viewFlopIde = {R.id.flop_card1, R.id.flop_card2,
            R.id.flop_card3, R.id.flop_card4, R.id.flop_card5};

    private int[] animationUserIds = {R.anim.translate_user1, R.anim.translate_user2};
    private int[] animationDealerIds = {R.anim.translate_dealer1, R.anim.translate_dealer2};
    private int[] animationFlopIds = {R.anim.translate_flop1, R.anim.translate_flop2,
            R.anim.translate_flop3, R.anim.translate_flop4, R.anim.translate_flop5, R.anim.flip};

    private ArrayList<Integer> allCards = new ArrayList(Arrays.asList(
            R.drawable.heart_2, R.drawable.heart_3, R.drawable.heart_4, R.drawable.heart_5,
            R.drawable.heart_6, R.drawable.heart_7, R.drawable.heart_8, R.drawable.heart_9,
            R.drawable.heart_10, R.drawable.heart_j, R.drawable.heart_q, R.drawable.heart_k,R.drawable.heart_a,
            R.drawable.club_2, R.drawable.club_3, R.drawable.club_4, R.drawable.club_5, R.drawable.club_6,
            R.drawable.club_7, R.drawable.club_8, R.drawable.club_9, R.drawable.club_j, R.drawable.club_q, R.drawable.club_k,R.drawable.club_a,
            R.drawable.diam_2, R.drawable.diam_3, R.drawable.diam_4,
            R.drawable.diam_5, R.drawable.diam_6, R.drawable.diam_7,R.drawable.diam_8,
            R.drawable.diam_9, R.drawable.diam_j, R.drawable.diam_q, R.drawable.diam_k,R.drawable.diam_a,
            R.drawable.spade_2, R.drawable.spade_3, R.drawable.spade_4,
            R.drawable.spade_5, R.drawable.spade_6, R.drawable.spade_7, R.drawable.spade_8,
            R.drawable.spade_9, R.drawable.spade_j, R.drawable.spade_q, R.drawable.spade_k,R.drawable.spade_a));


    private ArrayList<ImageView> userCards = new ArrayList<ImageView>();
    private ArrayList<ImageView> flopCards = new ArrayList<ImageView>();
    private ArrayList<ImageView> dealerCards = new ArrayList<ImageView>();
    private ArrayList userInfo;
    private Animation translate;
    private TextView betTV;
    private int[] highCard=new int[2]; // 0- user 1- computer
    private int numOfUsers = 0;

    private int rank[] = new int[7];
    private int suit[] = new int[7]; // 0 - heart  ** 1 - club ** 2- diam ** 3 - spade

    private int totalBetMoney = 0;
    private int userTotalMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poker);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        userInfo = (ArrayList) bundle.get("userinfo");
        userTotalMoney = (int) userInfo.get(4);

        setViewOfFields();
        dealer();
        setButtonClickable();

        setVisibleButtons(false);

        moneySk.setMax(userTotalMoney); //set total money for max


    }

    private void setVisibleButtons(boolean visible) {

        if(visible == false) {
            moneySk.setVisibility(View.INVISIBLE);
            betMoney.setVisibility(View.INVISIBLE);
            checkBtn.setVisibility(View.INVISIBLE);
            foldBtn.setVisibility(View.INVISIBLE);
            betTV.setVisibility(View.INVISIBLE);
            totalBetTitle.setVisibility(View.INVISIBLE);
            betBtn.setText("Play");
        }

        else {
            moneySk.setVisibility(View.VISIBLE);
            betMoney.setVisibility(View.VISIBLE);
            betTV.setVisibility(View.VISIBLE);
            totalBetTitle.setVisibility(View.VISIBLE);
            foldBtn.setVisibility(View.VISIBLE);
            betBtn.setText("Bet");
        }

    }

    private void setViewOfFields() {
        cards = (ImageView) findViewById(R.id.cards);
        betBtn = (Button) findViewById(R.id.playBtn);
        foldBtn = (Button) findViewById(R.id.foldBtn);
        checkBtn = (Button) findViewById(R.id.checkBtn);
        moneySk = (SeekBar) findViewById(R.id.money_poker);
        betMoney = (TextView) findViewById(R.id.bet_poker_txt);
        betTV = (TextView) findViewById(R.id.betTV);
        totalBetTitle = (TextView) findViewById(R.id.total_bet_title);
    }

    private void setButtonClickable() {

        betBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couter++;

                if (couter == 1) {
                    setVisibleButtons(true);

                    //flip user cards
                    for (int i = 0; i < NUM_USER_CARDS; i++) {
                        flipCard(userCards.get(i));
                    }

                } else if (couter == 2) {
                    checkBtn.setVisibility(View.VISIBLE);
                    updateMoneyTextView();

                    //flip flop cards
                    for (int i = 0; i < 3; i++) {
                        flipCard(flopCards.get(i));
                    }

                } else if (couter == 3) {
                    updateMoneyTextView();
                    flipCard(flopCards.get(3));

                } else if (couter == 4) {
                    updateMoneyTextView();

                    flipCard(flopCards.get(4));
                } else if (couter == 5){
                    updateMoneyTextView();
                    //check if the user won
                    getWinner();
                }
                moneySk.setProgress(0);
            }

        });

        foldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couter = 0;
                moneySk.setProgress(0);
                userTotalMoney -= totalBetMoney;
                betTV.setText(0+"$");
                moneySk.setMax(userTotalMoney);
                //update total money
                foldBtn.setVisibility(View.INVISIBLE);
                checkBtn.setVisibility(View.INVISIBLE);

                dealer();
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

    private void getWinner() {
        for(int i=0;i<5;i++){
            rank[i] = (allCards.indexOf(flopCards.get(i))%13)+2 ;
            suit[i] = (allCards.indexOf(flopCards.get(i))/13);
        }
        while(numOfUsers<2){
            for(int i=5;i<rank.length;i++) {
                rank[i] = (allCards.indexOf(userCards.get(i-5))%13)+2 ;
                suit[i] = (allCards.indexOf(userCards.get(i-5))/13);
            }
        }



    }

    private boolean isFourOfAKind(){
        int counter=0;
        int[] tempRank = rank;
        Arrays.sort(tempRank);
        int lastRank = tempRank[tempRank.length-1];
        for(int i=tempRank.length-1 ; i>0 ; i--){
            if(tempRank[i]==lastRank) counter++;
            else{
                lastRank=tempRank[i];
                counter=1;
            }
            if(counter==4){
                highCard[numOfUsers]=lastRank;
                return true;
            }
        }

        return false;
    }

    private boolean isFullHouse() {
        if (isThreeOfAKind()) {
            int counter = 0;
            int[] tempRank = rank;
            Arrays.sort(tempRank);
            int lastRank = tempRank[tempRank.length - 1];
            for (int i = tempRank.length - 1; i > 0; i--) {
                if (lastRank != highCard[numOfUsers]) {
                    if (tempRank[i] == lastRank) counter++;
                    else{
                        counter =1;
                        lastRank = tempRank[i];
                    }
                    if (counter == 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isStrait(){
        int[] tempRank = rank;
        int lastRank;
        int counter=1;
        Arrays.sort(tempRank);
        int loop=1;
        if(tempRank[tempRank.length-1] == 14){
            loop = 2;
        }
        while(loop >0) {

            for (int i = 2; i > 0; i--) {
                lastRank = tempRank[i];
                for (int j = i + 1; j < i + 5; j++) { //run from index 2 to 0, checking the high cards first
                    if (tempRank[j] != lastRank + 1) j = tempRank.length;
                    else {
                        lastRank = tempRank[j];
                        counter++;
                    }
                }
                if (counter >= 5) {
                    highCard[numOfUsers] = lastRank;
                    return true;
                }
                counter = 1;
            }
            if(loop==2){
                tempRank[tempRank.length-1]=1;
                Arrays.sort(tempRank);
            }
            loop--;
        }


        return false;
    }
    private boolean isFlush() {
        int counter = 1;
        for(int i=0 ; i<suit.length;i++){
            highCard[numOfUsers]=rank[i];
            for(int j=i;j<suit.length;i++){

                if(i!=j){
                    if(suit[i] == suit[j]){
                        counter++;
                         highCard[numOfUsers] = rank[j];
                    }
                }
            }
            if(counter >= 5) return true;
            counter = 1;
        }
        return false;
    }


    private boolean isThreeOfAKind(){
        int counter=1;
        int[] tempRank = rank;
        int lastRank = tempRank[tempRank.length-1];
        Arrays.sort(tempRank);
        for(int i=tempRank.length-1 ; i>0 ; i--){
            if(tempRank[i]==lastRank) counter++;
            else lastRank=tempRank[i];
            if(counter==3){
                highCard[numOfUsers]=lastRank;
                return true;
            }
        }
        return false;
    }
    private boolean isPair(){
        int counter=1;
        int[] tempRank = rank;
        int lastRank = tempRank[tempRank.length-1];
        Arrays.sort(tempRank);
        for(int i=tempRank.length-1 ; i>0 ; i--){
            if(tempRank[i]==lastRank) counter++;
            else lastRank=tempRank[i];
            if(counter==2){
                highCard[numOfUsers]=lastRank;
                return true;
            }
        }
        return false;
    }

    private void updateMoneyTextView(){
        totalBetMoney += moneySk.getProgress();
        moneySk.setMax(userTotalMoney-totalBetMoney);
        betTV.setText(totalBetMoney+"$");
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

        //dealer cards
        for(int i = 0;i < NUM_USER_CARDS; i++) {
            dealerCards.add(i, (ImageView) findViewById(viewDealerIds[i]));
            dealerCards.get(i).setImageResource(R.drawable.back_card);
            translate = AnimationUtils.loadAnimation(PokerActivity.this, animationDealerIds[i]);
            dealerCards.get(i).startAnimation(translate);
        }

    }

    private void flipCard(final ImageView imageView) {
        ObjectAnimator flip = (ObjectAnimator) AnimatorInflater.loadAnimator(PokerActivity.this,
                animationFlopIds[animationFlopIds.length - 1]);
        final int card_index = randomCard();
        Log.e("debug", "card_index = " + card_index + " allCards[i] = " + allCards.get(card_index));
        flip.setTarget(imageView);
        flip.setDuration(4000);
        flip.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageView.setImageResource(allCards.get(card_index));
                allCards.set(card_index, USED_CARD); //set selected card be used
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

    private int randomCard() {
        Random random = new Random();
        int index=-1 ;
        boolean flag = true ;

        while (flag) {
            index = random.nextInt(allCards.size());
            Log.e("Debug","Index: " +index);
            if(allCards.get(index) == USED_CARD) {
                flag = true;
            }
            else {
                flag = false;
            }

        }
        return index;
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PokerActivity.this);
        builder.setMessage("Are you sure you want to exit the Poker Game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PokerActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });
        builder.create();
        builder.show();
    }
}

