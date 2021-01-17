package alpha.net.com.altitorie.justlogic.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

import alpha.net.com.altitorie.justlogic.Activities.Challenges.ChallengeClass;
import alpha.net.com.altitorie.justlogic.R;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.settings_frameLayout_fragments, new AboutAuthorSettingsFragment())
                .commit();

    }

    public void goToAboutAuthorFragment(View view) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.settings_frameLayout_fragments, new AboutAuthorSettingsFragment())
                .commit();
    }

    public void goToOtherSettingsFragment(View view) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.settings_frameLayout_fragments, new OtherSettingsFragment())
                .commit();
    }



}