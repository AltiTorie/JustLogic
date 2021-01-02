package alpha.net.com.altitorie.justlogic.Activities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.logging.Logger;

import alpha.net.com.altitorie.justlogic.Activities.Challenges.Challenge;
import alpha.net.com.altitorie.justlogic.Activities.Challenges.ChallengeClass;
import alpha.net.com.altitorie.justlogic.Activities.Challenges.ChallengeIterator;
import alpha.net.com.altitorie.justlogic.R;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class ChallengeGolfActivity extends Challenge implements ChallengeIterator, SensorEventListener {
    private SensorManager sensorManager;
    private static final String TAG = "MainActivity";
    private static final float FINAL_HORIZONTAL_BIAS = 0.85f;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private float[] mGravity;
    private float[] mGeomagnetic;
    Logger logger = Logger.getLogger(ChallengeGolfActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_golf);

        challengeClass = ChallengeClass.GOLF;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Setup the sensors
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer == null) {
            Log.d(TAG, "accelerometer is null");
        }
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magnetometer == null) {
            Log.d(TAG, "magnetometer is null");
        }

        // Detect the window position
        switch (getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                Log.d(TAG, "Rotation 0");
                break;
            case Surface.ROTATION_90:
                Log.d(TAG, "Rotation 90");
                break;
            case Surface.ROTATION_180:
                Log.d(TAG, "Rotation 180");
                break;
            case Surface.ROTATION_270:
                Log.d(TAG, "Rotation 270");
                break;
            default:
                Log.w(TAG, "Rotation unknown");
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stop();
    }

    public void start() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Log.d(TAG, "onSensorChanged()");
        if (event.values == null) {
            Log.w(TAG, "event.values is null");
            return;
        }
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                mGravity = event.values;
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mGeomagnetic = event.values;
                break;
            default:
                Log.w(TAG, "Unknown sensor type " + sensorType);
                return;
        }
        if (mGravity == null) {
            Log.w(TAG, "mGravity is null");
            return;
        }
        if (mGeomagnetic == null) {
            Log.w(TAG, "mGeomagnetic is null");
            return;
        }
        float rot[] = new float[9];
        if (!SensorManager.getRotationMatrix(rot, null, mGravity, mGeomagnetic)) {
            Log.w(TAG, "getRotationMatrix() failed");
            return;
        }

        float orientation[] = new float[9];
        SensorManager.getOrientation(rot, orientation);
        float roll = orientation[2];
        int rollDeg = (int) Math.round(Math.toDegrees(roll));
        ImageView image = findViewById(R.id.golf_ballImage);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) image.getLayoutParams();
        if (rollDeg < -5 && rollDeg > -150) {
            logger.info("<-------- LEFT");
            if (params.horizontalBias > -0.90) {
                params.horizontalBias += -0.01f;
                image.setLayoutParams(params);
            }
        }
        if (rollDeg > 5 && rollDeg < 150) {
            logger.info("RIGHT ------->");
            params.horizontalBias += 0.01f;
            image.setLayoutParams(params);
            if (params.horizontalBias >= FINAL_HORIZONTAL_BIAS) {
                endChallenge();
            }
        }


    }


    private void endChallenge() {
        stop();
        new Handler().postDelayed(this::runNextChallenge, DELAY_BETWEEN);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
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