package com.example.admin.casinogames;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.casinogames.UtilClass.apiConnectorDB;
import com.example.admin.casinogames.UtilClass.utils;
import com.example.admin.casinogames.com.example.admin.tasks.updateUserTotalMoneyTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import fr.castorflex.android.flipimageview.library.FlipImageView;


public class PokerActivity extends Activity {

    private enum handStrength {
        HIGH_CARD(1), PAIR(2), TWO_PAIR(3), THREE_OF_A_KIND(4), STRAIGHT(5), FLUSH(6),FULL_HOUSE(7), FOUR_OF_A_KIND(8);

        private int Rankpoints;

        handStrength(int points) {
            this.Rankpoints = points;
        }

        public int getRankpoints() {
            return this.Rankpoints;
        }
    }

    private final int NUM_USER_CARDS = 2;
    private final int NUM_FLOP_CARDS = 5;
    private final int USED_CARD = -100;
    private final int SLEEP_TIME = 2000;

    private final int USER_ID = 0;
    private final int USER_NAME = 1;
    private final int MONEY = 4;
    private final int IMAGE = 5;

    private Bundle bundle;
    private ArrayList userInfo;
    private Animation translate;
    private TextView betTV, betMoney, totalBetTitle;
    private Button betBtn,foldBtn,checkBtn;
    private SeekBar moneySk;
    private FlipImageView cards;
    private handStrength[] hands= new handStrength[2];

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
            R.drawable.club_7, R.drawable.club_8, R.drawable.club_9,R.drawable.club_10, R.drawable.club_j,
            R.drawable.club_q, R.drawable.club_k,R.drawable.club_a,
            R.drawable.diam_2, R.drawable.diam_3, R.drawable.diam_4,
            R.drawable.diam_5, R.drawable.diam_6, R.drawable.diam_7,R.drawable.diam_8,
            R.drawable.diam_9, R.drawable.diam_10, R.drawable.diam_j, R.drawable.diam_q, R.drawable.diam_k,R.drawable.diam_a,
            R.drawable.spade_2, R.drawable.spade_3, R.drawable.spade_4,
            R.drawable.spade_5, R.drawable.spade_6, R.drawable.spade_7, R.drawable.spade_8,
            R.drawable.spade_9, R.drawable.spade_10,R.drawable.spade_j, R.drawable.spade_q, R.drawable.spade_k,R.drawable.spade_a));

    private ArrayList<Integer> userCardsID = new ArrayList<>();
    private ArrayList<Integer> flopCardID = new ArrayList<>();
    private ArrayList<Integer> dealerCardsID = new ArrayList<>();
    private ArrayList<FlipImageView> userCards = new ArrayList<FlipImageView>();
    private ArrayList<FlipImageView> flopCards = new ArrayList<FlipImageView>();
    private ArrayList<FlipImageView> dealerCards = new ArrayList<FlipImageView>();

    private int[] sounds = {R.raw.cardflip,R.raw.cointoss,R.raw.pokerroom,R.raw.screamnoooh,R.raw.shufflingcards,R.raw.lost};
    private int[] tempAllCards = new int[52];
    private int[] highCard = new int[2]; // 0- user 1- computer

    private int rank[] = new int[7];
    private int suit[] = new int[7]; // 0 - heart  ** 1 - club ** 2- diam ** 3 - spade

    private int counter = 0; //count number of clicks on bet button
    private int numOfUsers = 0;
    private int totalBetMoney = 0;
    private int userTotalMoney;
    private int betMulti = 1;

    private boolean userWon = false;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poker);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        userInfo = (ArrayList) bundle.get("userinfo");
        userTotalMoney = (int) userInfo.get(MONEY);
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

        cards = (FlipImageView) findViewById(R.id.cards);
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

                if(counter !=0 && moneySk.getProgress() == 0){
                    Toast.makeText(PokerActivity.this,"You Must Bet Cheack/Fold",Toast.LENGTH_LONG).show();

                }
                else {
                    counter++;
                    if(counter > 1) utils.playSound(PokerActivity.this,sounds[1]);
                    flag=true;
                    checkMove();
                }
            }


        });

        foldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userWon = false;
                //update total money
                updateUserMoney();
                flag = false;
                newGame();
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                checkMove();
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

    private void checkMove() {
        if (counter == 1) {
            setVisibleButtons(true);
            for(int i = 0;i<tempAllCards.length;i++) tempAllCards[i] = 0;

            //flip user cards
            for (int i = 0; i < NUM_USER_CARDS; i++) {
                int index = randomCard();

                flipCard(userCards.get(i),index);
                userCardsID.add(i,allCards.get(index));
            }

        } else if (counter == 2) {
            checkBtn.setVisibility(View.VISIBLE);

            updateMoneyTextView();

            //flip flop cards
            for (int i = 0; i < 3; i++) {
                int index = randomCard();
                flipCard(flopCards.get(i),index);
                flopCardID.add(i,allCards.get(index));
            }
            betBtn.setText("Raise");

        } else if (counter == 3) {
            updateMoneyTextView();
            int index = randomCard();
            flipCard(flopCards.get(3),index);
            flopCardID.add(3, allCards.get(index));

        } else if (counter == 4) {
            updateMoneyTextView();
            int index = randomCard();
            flipCard(flopCards.get(4),index);
            flopCardID.add(4,allCards.get(index));

        } else if (counter == 5){
            for(int i = 0; i < NUM_USER_CARDS; i++) {
                int index = randomCard();
                flipCard(dealerCards.get(i),index);
                dealerCardsID.add(i,allCards.get(index));
            }

            updateMoneyTextView();
            //check if the user won
            getWinner();
            delayFunction();
        }
        moneySk.setProgress(0);

    }

    private void delayFunction() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateUserMoney();
                //start a new game
                //ask if wants a new game
                newGame();
            }
        },SLEEP_TIME);

    }



    private void newGame() {
        try{
            if(flag){Thread.sleep(5000);}
            else{Thread.sleep(1500);}
        }catch (Exception e){}

        AlertDialog.Builder builder = new AlertDialog.Builder(PokerActivity.this);
        builder.setMessage("Do You Want to Play One more Game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        betTV.setText(0 + "$");
                        counter = 0;
                        moneySk.setProgress(0);

                        setVisibleButtons(false);
                        dealer();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PokerActivity.this.finish();
                    }
                });
        builder.create();
        builder.show();

    }

    private void updateUserMoney() {
        if(userWon){ userTotalMoney+=totalBetMoney*betMulti;}
        else{ userTotalMoney -= totalBetMoney;}
        userInfo.set(MONEY, userTotalMoney);
        ArrayList<NameValuePair> userInfoArray = new ArrayList<NameValuePair>();
        userInfoArray.add(new BasicNameValuePair("id", userInfo.get(USER_ID).toString()));
        userInfoArray.add(new BasicNameValuePair("totalmoney",""+userTotalMoney));
        moneySk.setMax(userTotalMoney);

        new updateUserTotalMoneyTask(userInfoArray).execute(new apiConnectorDB());
    }

    private void getWinner() {
        numOfUsers = 0;
        for(int i = 0; i < 5; i++){
            rank[i] = (allCards.indexOf(flopCardID.get(i))%13)+2 ;
            suit[i] = (allCards.indexOf(flopCardID.get(i))/13);
        }
        while(numOfUsers < 2){
            if(numOfUsers == 0) {
                for (int i = 5; i < rank.length; i++) {
                    rank[i] = (allCards.indexOf(userCardsID.get(i - 5)) % 13) + 2;
                    suit[i] = (allCards.indexOf(userCardsID.get(i - 5)) / 13);
                }
            }else{
                for (int i = 5; i < rank.length; i++) {
                    rank[i] = (allCards.indexOf(dealerCardsID.get(i - 5)) % 13) + 2;
                    suit[i] = (allCards.indexOf(dealerCardsID.get(i - 5)) / 13);
                }
            }
            //Debug
            String str ="[";
            for(int i = 0; i < rank.length; i++) str+=rank[i]+",";
            str+="]";
            Log.e("Debug",""+str);
            //End Debug

            getHandStrenght();
            numOfUsers++;
        }

        Log.e("DEBUG","User Hand: " + hands[0]);
        Log.e("DEBUG","Dealer Hand: " + hands[1]);
        if(hands[0].getRankpoints() > hands[1].getRankpoints()){
            userWon();
        }else if(hands[0].getRankpoints() == hands[1].getRankpoints()){
            if(highCard[0] >= highCard[1]){
                userWon();
            }else{
                dealerWon();
            }
        }else {
            dealerWon();
        }

    }

    private void dealerWon() {
        Toast.makeText(this, "Dealer WON!!! With " + hands[1], Toast.LENGTH_LONG).show();
        userWon = false;
        utils.playSound(PokerActivity.this,sounds[3]);
    }

    private void userWon() {
        Toast.makeText(this,"User WON!!!With "+hands[0],Toast.LENGTH_LONG).show();
        userWon = true;
        utils.playSound(PokerActivity.this,sounds[5]);
    }

    private void getHandStrenght(){
        if(isFourOfAKind()){
            hands[numOfUsers] = handStrength.FOUR_OF_A_KIND;
        }else if(isFullHouse()){
            hands[numOfUsers] = handStrength.FULL_HOUSE;
        }else if(isFlush()){
            hands[numOfUsers] = handStrength.FLUSH;
        }else if(isStrait()){
            hands[numOfUsers] = handStrength.STRAIGHT;
        }else if(isThreeOfAKind()){
            hands[numOfUsers] = handStrength.THREE_OF_A_KIND;
        }else if(isTwoPair()){
            hands[numOfUsers] = handStrength.TWO_PAIR;
        }else if(isPair()){
            hands[numOfUsers] = handStrength.PAIR;
        }else if(isHighCard()){
            hands[numOfUsers] = handStrength.HIGH_CARD;
        }
    }

    private boolean isFourOfAKind(){
        int counter = 0;
        int[] tempRank = new int[rank.length];
        for(int i = 0; i < rank.length; i++) tempRank[i] = rank[i];
        Arrays.sort(tempRank);
        int lastRank = tempRank[tempRank.length-1];
        for(int i = tempRank.length-1; i > 0; i--){
            if(tempRank[i]==lastRank) counter++;
            else{
                lastRank=tempRank[i];
                counter = 1;
            }
            if(counter == 4){
                highCard[numOfUsers]=lastRank;
                return true;
            }
        }

        return false;
    }

    private boolean isFullHouse() {
        if (isThreeOfAKind()) {
            int counter = 0;
            int[] tempRank = new int[rank.length];
            for(int i = 0; i < rank.length; i++) tempRank[i] = rank[i];
            Arrays.sort(tempRank);
            int lastRank = tempRank[tempRank.length - 1];
            for (int i = tempRank.length - 1; i > 0; i--) {
                if (lastRank != highCard[numOfUsers]) {
                    if (tempRank[i] == lastRank) counter++;
                    else{
                        counter = 1;
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
        int[] tempRank = new int[rank.length];
        for(int i = 0; i < rank.length; i++) tempRank[i] = rank[i];
        int lastRank;
        int counter = 1;
        Arrays.sort(tempRank);
        int loop = 1;
        if(tempRank[tempRank.length-1] == 14){
            loop = 2;
        }
        while(loop > 0) {

            for (int i = 2; i > 0; i--) {
                lastRank = tempRank[i];
                for (int j = i+1; j < i+5; j++) { //run from index 2 to 0, checking the high cards first
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
            if(loop == 2){
                tempRank[tempRank.length-1] = 1;
                Arrays.sort(tempRank);
            }
            loop--;
        }


        return false;
    }

    private boolean isFlush() {

        int counter = 1;
        for(int i=0 ; i<suit.length; i++){
            highCard[numOfUsers] = rank[i];

            for(int j = i; j < suit.length; j++){
                if(i != j){
                    if(suit[i] == suit[j]){
                        counter++;
                        if(rank[j] > highCard[numOfUsers]) {
                            highCard[numOfUsers] = rank[j];
                        }
                    }
                }
            }
            if(counter >= 5) {
                return true;
            }else {counter = 1;}
        }
        return false;
    }

    private boolean isThreeOfAKind(){
        int counter = 0;
        int[] tempRank = new int[rank.length];
        for(int i = 0;i < rank.length; i++) tempRank[i] = rank[i];
        int lastRank = tempRank[tempRank.length-1];
        Arrays.sort(tempRank);
        for(int i = tempRank.length-1; i > 0; i--){
            if(tempRank[i] == lastRank)
                counter++;
            else {
                lastRank = tempRank[i];
                counter = 1;
            }

            if(counter == 3){
                highCard[numOfUsers] = lastRank;
                return true;
            }
        }
        return false;
    }

    private boolean isTwoPair() {
        int counter = 0;
        int pairsCounter = 0;
        int[] tempRank = new int[rank.length];
        for(int i = 0; i < rank.length; i++) tempRank[i] = rank[i];
        int lastRank = tempRank[tempRank.length-1];
        int firstRank = tempRank[tempRank.length-1];
        Arrays.sort(tempRank);
        for( int i = tempRank.length-1; i > 0; i--) {
            if(tempRank[i] == lastRank) {
                counter++;
            }
            else {
                lastRank = tempRank[i];
                counter = 1;
            }

            if(counter == 2) {
                firstRank = tempRank[i];
                pairsCounter++;
                counter = 1;
            }

            if(pairsCounter == 2) {
                highCard[numOfUsers] = firstRank;
                return true;
            }
        }
        return false;
    }

    private boolean isPair(){
        int counter = 0;
        int[] tempRank = new int[rank.length];
        for(int i = 0;i < rank.length; i++) tempRank[i] = rank[i];
        int lastRank = tempRank[tempRank.length-1];
        Arrays.sort(tempRank);
        for(int i = tempRank.length-1; i > 0; i--){
            if(tempRank[i] == lastRank)
                counter++;
            else {
                lastRank = tempRank[i];
                counter = 1;
            }

            if(counter == 2){
                highCard[numOfUsers] = lastRank;
                return true;
            }
        }
        return false;
    }

    private boolean isHighCard() {
        highCard[numOfUsers] = rank[rank.length-1];
        return true;
    }


    private void updateMoneyTextView(){
        betMulti += 0.5;
        totalBetMoney += moneySk.getProgress();
        moneySk.setMax(userTotalMoney-totalBetMoney);
        betTV.setText(totalBetMoney+"$");
    }

    private void dealer() {
        utils.playSound(PokerActivity.this,sounds[4]);
        //user cards
        for(int i = 0; i < NUM_USER_CARDS; i++) {
            userCards.add(i, (FlipImageView) findViewById(viewUserIds[i]));
            userCards.get(i).setImageResource(R.drawable.back_card);
            translate = AnimationUtils.loadAnimation(PokerActivity.this, animationUserIds[i]);
            userCards.get(i).startAnimation(translate);
        }

        //flop cards
        for(int i = 0; i < NUM_FLOP_CARDS; i++) {
            flopCards.add(i, (FlipImageView) findViewById(viewFlopIde[i]));
            flopCards.get(i).setImageResource(R.drawable.back_card);
            translate = AnimationUtils.loadAnimation(PokerActivity.this, animationFlopIds[i]);
            flopCards.get(i).startAnimation(translate);
        }

        //dealer cards
        for(int i = 0;i < NUM_USER_CARDS; i++) {
            dealerCards.add(i, (FlipImageView) findViewById(viewDealerIds[i]));
            dealerCards.get(i).setImageResource(R.drawable.back_card);
            translate = AnimationUtils.loadAnimation(PokerActivity.this, animationDealerIds[i]);
            dealerCards.get(i).startAnimation(translate);
        }
    }

    private void flipCard(final FlipImageView imageView, final int card_index) {
        ObjectAnimator flip = (ObjectAnimator) AnimatorInflater.loadAnimator(PokerActivity.this,
                animationFlopIds[animationFlopIds.length - 1]);

        Log.e("debug", "card_index = " + card_index + " allCards[i] = " + allCards.get(card_index));
        flip.setTarget(imageView);
        flip.setDuration(1500);
        flip.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageView.setImageResource(allCards.get(card_index));

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        flip.start();


        utils.playSound(PokerActivity.this,sounds[0]);
    }

    private int randomCard() {
        Random random = new Random();
        int index =- 1 ;
        boolean flag = true ;

        while (flag) {
            index = random.nextInt(allCards.size());
            Log.e("Debug","Index: " +index);
            if(tempAllCards[index] == USED_CARD) {
                flag = true;

            }
            else {
                flag = false;
                tempAllCards[index] = USED_CARD; //set selected card be used
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
                        utils.endSound(PokerActivity.this);
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

    @Override
    protected void onPause() {
        super.onPause();
        utils.endSound(PokerActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        utils.endSound(PokerActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        utils.endSound(PokerActivity.this);
    }
}

