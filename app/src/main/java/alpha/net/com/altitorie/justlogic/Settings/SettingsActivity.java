package alpha.net.com.altitorie.justlogic;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import alpha.net.com.altitorie.justlogic.Activities.Challenges.ChallengeClass;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public void ResetProgress(View view) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("current_challenge", ChallengeClass.NONE.toString());
        editor.apply();
    }
}