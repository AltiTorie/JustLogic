package alpha.net.com.altitorie.justlogic.Activities.Challenges;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import alpha.net.com.altitorie.justlogic.MainActivity;
import alpha.net.com.altitorie.justlogic.R;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public abstract class Challenge extends AppCompatActivity implements ChallengeIterator {
    protected static final int DELAY_BETWEEN = 500;
    protected ChallengeClass challengeClass;

    @Override
    public void runNextChallenge() {
        final Intent intent = new Intent(this, challengeClass.next());
        SharedPreferences pref = getApplicationContext().getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("current_challenge",challengeClass.toString());
        editor.apply();
        showChallengeFinishedDialog(intent);

    }

    private void showChallengeFinishedDialog(Intent intent) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.challenge_finished, null);
        alert.setView(view);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        Button btn = alertDialog.findViewById(R.id.finished_button);
        if (btn != null) {
            btn.setOnClickListener(v -> {
                alertDialog.dismiss();
                end(intent);
            });
        } else {
            end(intent);
        }
    }

    private void end(Intent intent) {
        finish();
        startActivity(intent);
    }


    public void showHint() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View popUpView = getLayoutInflater().inflate(R.layout.challenge_hint, null);
        alert.setView(popUpView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        TextView tv = alertDialog.findViewById(R.id.hint_text);
        if (tv != null) {
            tv.setText(this.challengeClass.getHint());
        }

        Button btn = alertDialog.findViewById(R.id.hint_button);
        if (btn != null) {
            btn.setOnClickListener(v -> alertDialog.dismiss());
        }
    }

    public void showAnswer() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View popUpView = getLayoutInflater().inflate(R.layout.challenge_answer, null);
        alert.setView(popUpView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        TextView tv = alertDialog.findViewById(R.id.answer_text);
        if (tv != null) {
            tv.setText(this.challengeClass.getAnswer());
        }
        Button btn = alertDialog.findViewById(R.id.answer_button);
        if (btn != null) {
            btn.setOnClickListener(v -> alertDialog.dismiss());
        }
    }

    protected void showMenu() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View popUpView = getLayoutInflater().inflate(R.layout.challenge_menu, null);
        alert.setView(popUpView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Button resumeButton = alertDialog.findViewById(R.id.menu_resume_button);
        if (resumeButton != null) {
            resumeButton.setOnClickListener(v -> alertDialog.dismiss());
        }
        Button menuButton = alertDialog.findViewById(R.id.menu_exit_button);
        if (menuButton != null) {
            final Intent intent = new Intent(this, MainActivity.class);
            menuButton.setOnClickListener(v -> {
                alertDialog.dismiss();
                finish();
                startActivity(intent);
            });
        }
    }
}
