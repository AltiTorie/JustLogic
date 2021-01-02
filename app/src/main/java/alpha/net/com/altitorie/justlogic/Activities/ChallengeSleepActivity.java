package alpha.net.com.altitorie.justlogic.Activities;

import alpha.net.com.altitorie.justlogic.Activities.Challenges.Challenge;
import alpha.net.com.altitorie.justlogic.Activities.Challenges.ChallengeClass;
import alpha.net.com.altitorie.justlogic.Activities.Challenges.ChallengeIterator;
import alpha.net.com.altitorie.justlogic.R;

import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;

import java.util.logging.Logger;

public class ChallengeSleepActivity extends Challenge implements ChallengeIterator {


    private static final Logger logger = Logger.getLogger(ChallengeSleepActivity.class.getName());
    private int firstBrightness;
    final Handler handler = new Handler();
    private static final int DELAY = 500;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_sleep);
        logger.info("Started sleep");
        challengeClass = ChallengeClass.SLEEP;

        firstBrightness = getBrightness();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable = this::runChallenge, DELAY);
    }

    private void runChallenge() {
        int brightness = getBrightness();
        if (canGoToSleep(brightness)) {
            finishChallenge();
        } else {
            handler.postDelayed(runnable, DELAY);
        }
    }

    private int getBrightness() {
        try {
            int brightness = Settings.System.getInt(getApplicationContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
            if (brightness != firstBrightness) {
                firstBrightness = -1;
            }
            return brightness;

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }


    private void changePicture() {
        ImageView image = findViewById(R.id.sleeping_challengeImage);
        image.setImageResource(R.drawable.sleeping_pic_after);
    }

    private void finishChallenge() {
        changePicture();
        new Handler().postDelayed(this::runNextChallenge, DELAY_BETWEEN);
    }

    private boolean canGoToSleep(int brightness) {
        logger.info("Bri: " + brightness + " fir: " + firstBrightness);
        return brightness < 25 && brightness != firstBrightness;
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