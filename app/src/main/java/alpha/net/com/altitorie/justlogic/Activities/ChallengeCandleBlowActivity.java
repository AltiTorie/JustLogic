package alpha.net.com.altitorie.justlogic.Activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.logging.Logger;

import alpha.net.com.altitorie.justlogic.Activities.Challenges.Challenge;
import alpha.net.com.altitorie.justlogic.Activities.Challenges.ChallengeClass;
import alpha.net.com.altitorie.justlogic.Activities.Challenges.ChallengeIterator;
import alpha.net.com.altitorie.justlogic.R;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import static android.media.AudioManager.MODE_NORMAL;

public class ChallengeCandleBlowActivity extends Challenge implements ChallengeIterator {

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int BLOW_THRESHOLD = 18000;//!!!!!!!!!!!!!!!!!!!!!!!!!!
    private static final Logger logger = Logger.getLogger(ChallengeCandleBlowActivity.class.getName());
    private int delay = 100;
    Handler handler = new Handler();
    Runnable runnable;

    private MediaRecorder recorder = null;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_candle_blow);

        challengeClass = ChallengeClass.BLOW_CANDLE;

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
    }

    private void runChallenge() {
        if (checkAmplitude()) {
            winChallenge();
        } else {
            handler.postDelayed(runnable, delay);
        }
    }

    public void winChallenge() {
        Toast.makeText(this, "Candle done!", Toast.LENGTH_SHORT).show();
        ImageView image = findViewById(R.id.candleBlow_candleImage);
        image.setImageResource(R.drawable.candle_not_lit_pic);
        if (recorder != null) {
            stopRecording();
        }
        new Handler().postDelayed(this::runNextChallenge, DELAY_BETWEEN);

    }

    private boolean checkAmplitude() {
        int maxAmplitude = recorder.getMaxAmplitude();
        logger.info(String.valueOf(maxAmplitude));
        return maxAmplitude > BLOW_THRESHOLD;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        if (!permissionToRecordAccepted) finish();
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        recorder.setOutputFile("/dev/null");
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        if (isMicrophoneAvailable())
            recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }
        handler.removeCallbacks(runnable);

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (recorder == null) {
            startRecording();
        }
        handler.postDelayed(runnable = this::runChallenge, delay);
    }

    public boolean isMicrophoneAvailable() {
        AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        return audioManager.getMode() == MODE_NORMAL;
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