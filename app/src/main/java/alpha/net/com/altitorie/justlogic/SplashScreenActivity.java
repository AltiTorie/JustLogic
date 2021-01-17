package alpha.net.com.altitorie.justlogic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashScreenActivity extends Activity {
    private static final int CZAS = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ActivityStarter starter = new ActivityStarter();
        starter.start();
    }

    private class ActivityStarter extends Thread {

        @Override
        public void run() {
            try {
                // tutaj wrzucamy wszystkie akcje potrzebne podczas ładowania aplikacji
                Thread.sleep(CZAS);
            } catch (Exception e) {
                Log.e("SplashScreen", e.getMessage());
            }

            // Włącz główną aktywność
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            SplashScreenActivity.this.startActivity(intent);
            SplashScreenActivity.this.finish();
        }
    }
}