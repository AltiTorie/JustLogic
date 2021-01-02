package alpha.net.com.altitorie.justlogic.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.Objects;

import alpha.net.com.altitorie.justlogic.Activities.Challenges.Challenge;
import alpha.net.com.altitorie.justlogic.Activities.Challenges.ChallengeClass;
import alpha.net.com.altitorie.justlogic.Activities.Challenges.ChallengeIterator;
import alpha.net.com.altitorie.justlogic.R;

public class ChallengeDicesActivity extends Challenge implements ChallengeIterator, SensorEventListener {
    ImageView enemyDices;
    ImageView friendlyDices;
    ImageView friend;
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private boolean buttonHold = false;
    private boolean finishing = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_dices);

        challengeClass = ChallengeClass.DICES;
        enemyDices = findViewById(R.id.dices_enemyDicesImage);
        friendlyDices = findViewById(R.id.dices_friendlyDicesImage);
        friend = findViewById(R.id.dices_friendImage);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager)
                .registerListener(this,
                        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                        SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        enemyDices.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                buttonHold = true;
            }
            if (event.getAction() == MotionEvent.ACTION_UP && buttonHold) {
                buttonHold = false;
            }
            return true;
        });
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (buttonHold) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt(x * x + y * y + z * z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 12 && !finishing) {
                finishing = true;
                friendlyDices.setImageResource(R.drawable.dices_friendly_win);
                friend.setImageResource(R.drawable.dices_happy_friend);
                new Handler().postDelayed(this::runNextChallenge, DELAY_BETWEEN);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //No need to implement this method.
    }


    @Override
    protected void onResume() {
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this);
        super.onPause();
    }


    public void showHintDialog(View view) {
        super.showHint();
    }

    public void showAnswerDialog(View view) {
        super.showAnswer();
    }


    public void showMenuDialog(View view) {
        super.showMenu();
    }
}