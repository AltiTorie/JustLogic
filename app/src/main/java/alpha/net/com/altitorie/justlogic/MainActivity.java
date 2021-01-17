package alpha.net.com.altitorie.justlogic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.util.logging.Logger;

import alpha.net.com.altitorie.justlogic.Activities.Challenges.ChallengeClass;
import alpha.net.com.altitorie.justlogic.Settings.SettingsActivity;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private static ChallengeClass c = ChallengeClass.NONE;
    Logger logger = Logger.getLogger(MainActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
    }

    public void runChallenge(View view) {
        setupNextChallenge();
        final Intent intent = new Intent(this, c.next());
        startActivity(intent);
    }

    public void runSettings(View view) {
        final Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void setup() {
        setupNextChallenge();
    }


    private void setupNextChallenge(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("AppPreferences", MODE_PRIVATE);
        String nc = pref.getString("current_challenge", "NONE");
        c = ChallengeClass.valueOf(nc);
    }

}
