package alpha.net.com.altitorie.justlogic.Activities;

import alpha.net.com.altitorie.justlogic.Activities.Challenges.Challenge;
import alpha.net.com.altitorie.justlogic.Activities.Challenges.ChallengeClass;
import alpha.net.com.altitorie.justlogic.Activities.Challenges.ChallengeIterator;
import alpha.net.com.altitorie.justlogic.R;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import java.util.logging.Logger;

public class ChallengeEquationActivity extends Challenge implements SensorEventListener, ChallengeIterator {

    private SensorManager sensorManager;
    private Sensor sensor;
    private final float[] rotationMatrix = new float[9];
    private static final Logger logger = Logger.getLogger(ChallengeEquationActivity.class.getName());
    private static boolean alreadyWon = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_equation);

        challengeClass = ChallengeClass.EQUATION;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        rotationMatrix[0] = 1;
        rotationMatrix[3] = 1;
        rotationMatrix[6] = 1;

    }

    public void start() {
        sensorManager.registerListener(this, sensor, 10000000);
    }

    public void stop() {
        sensorManager.unregisterListener(this);
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        // we received a sensor event. it is a good practice to check
        // that we received the proper event
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {

            SensorManager.getRotationMatrixFromVector(
                    rotationMatrix, event.values);
            float[] rotations = new float[]{rotationMatrix[0], rotationMatrix[5], rotationMatrix[7]};
            if (rotations[2] < -0.9 && !alreadyWon) {
                alreadyWon = true;
                winChallenge();
            }
        }

    }

    public void winChallenge() {
        new Handler().postDelayed(this::runNextChallenge, DELAY_BETWEEN);

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void showHintDialog(View view){
        super.showHint();
    }

    public void showAnswerDialog(View view){
        super.showAnswer();
    }


    public void showMenuDialog(View view) {
        super.showMenu();
    }
}