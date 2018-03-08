package me.riddhi.gada.scarnsdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int comp_total = 0, comp_turn = 0;
    int user_total = 0, user_turn = 0;
    Button roll, hold, reset;
    ImageView DICE;
    TextView userscore, compscore, msg;
    String message;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roll = findViewById(R.id.roll);
        roll.setOnClickListener(this);
        hold = findViewById(R.id.hold);
        hold.setOnClickListener(this);
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(this);
        DICE = findViewById(R.id.DICE);
        userscore = findViewById(R.id.userscore);
        compscore = findViewById(R.id.compscore);
        msg = findViewById(R.id.msg);
    }

    static int randInt(int min, int max) {
        Random generator = new Random();
        return generator.nextInt((max - min) + 1) + min;
    }

    @Override
    public void onClick(View view) {
        Animation roll_effect = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.roll_effect);
        if (view.getId() == R.id.roll) {
            int i = randInt(1, 6);
            message = "";
            msg.setText(message);
            if (i == 1) {
                user_turn = 0;
                message = "Your Turn Score is now 0.";
                msg.setText(message);
                DICE.startAnimation(roll_effect);
                DICE.setImageResource(R.drawable.dice1);
                message = "User Score: " + user_total + " User turn: " + user_turn;
                userscore.setText(message);
                roll.setEnabled(false);
                hold.setEnabled(false);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        computer_turn();
                    }
                }, 1000);
            } else {
                user_turn += i;
                String dice = "dice" + i;
                int resId = getResources().getIdentifier(dice, "drawable", getPackageName());
                DICE.setImageResource(resId);
                DICE.startAnimation(roll_effect);
                message = "User Score: " + user_total + " User turn: " + user_turn;
                userscore.setText(message);
            }
        }
        if (view.getId() == R.id.hold) {
            user_total += user_turn;
            user_turn = 0;
            if (user_total >= 100) {

                roll.setEnabled(false);
                hold.setEnabled(false);
                message = "YOU WON. (now click reset for a new game.)";
                msg.setText(message);
            } else {
                message = "User Score: " + user_total;
                userscore.setText(message);
                message = "It is now Computer's Turn.";
                msg.setText(message);
                roll.setEnabled(false);
                hold.setEnabled(false);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        computer_turn();
                    }
                }, 1000);
            }
        }
        if (view.getId() == R.id.reset) {
            comp_total = comp_turn = user_turn = user_total = 0;
            message = "User Score: " + user_total;
            userscore.setText(message);
            message = "Comp Score: " + user_total;
            compscore.setText(message);
            message = "Your game has now been reset.";
            DICE.startAnimation(roll_effect);
            DICE.setImageResource(R.drawable.dice1);
            msg.setText(message);
            roll.setEnabled(true);
            hold.setEnabled(true);
        }
    }

    private void computer_turn() {
        Animation roll_effect = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.roll_effect);
        while (comp_turn <= 4) {
            int i = randInt(1, 6);
            if (i == 1) {
                comp_turn = 0;
                message = "Computer Turn Score is now 0.";
                msg.setText(message);
                String dice = "dice" + i;
                int resId = getResources().getIdentifier(dice, "drawable", getPackageName());
                DICE.startAnimation(roll_effect);
                DICE.setImageResource(resId);
                message = "Comp Score: " + comp_total + " Comp turn: " + comp_turn;
                compscore.setText(message);
            }
            else {
                comp_turn += i;
                String dice = "dice" + i;
                int resId = getResources().getIdentifier(dice, "drawable", getPackageName());
                DICE.startAnimation(roll_effect);
                DICE.setImageResource(resId);
                message = "Comp Score: " + comp_total + " Comp turn: " + comp_turn;
                compscore.setText(message);
            }
        }
        comp_total += comp_turn;
        if (comp_total >= 100) {
            roll.setEnabled(false);
            hold.setEnabled(false);
            message = "COMPUTER WON. (now click reset for a new game.)";
            msg.setText(message);
        } else {
            comp_turn = 0;
            message = "Computer Turn Score is now 0.";
            msg.setText(message);
            DICE.startAnimation(roll_effect);
            DICE.setImageResource(R.drawable.dice1);
            message = "Comp Score: " + comp_total + " Comp turn: " + comp_turn;
            compscore.setText(message);
            roll.setEnabled(true);
            hold.setEnabled(true);
        }
    }
}