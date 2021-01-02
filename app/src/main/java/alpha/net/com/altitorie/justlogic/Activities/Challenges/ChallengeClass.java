package alpha.net.com.altitorie.justlogic.Activities.Challenges;

import android.app.Activity;

import alpha.net.com.altitorie.justlogic.Activities.ChallengeCandleBlowActivity;
import alpha.net.com.altitorie.justlogic.Activities.ChallengeDicesActivity;
import alpha.net.com.altitorie.justlogic.Activities.ChallengeEquationActivity;
import alpha.net.com.altitorie.justlogic.Activities.ChallengeGolfActivity;
import alpha.net.com.altitorie.justlogic.Activities.ChallengeSleepActivity;
import alpha.net.com.altitorie.justlogic.Activities.GameFinishedActivity;
import alpha.net.com.altitorie.justlogic.MainActivity;

public enum ChallengeClass {
    NONE,
    BLOW_CANDLE,
    SLEEP,
    EQUATION,
    GOLF,
    DICES;

    public Class<? extends Activity> next() {
        switch (this) {
            case NONE:
                return ChallengeCandleBlowActivity.class;
            case BLOW_CANDLE:
                return ChallengeSleepActivity.class;
            case SLEEP:
                return ChallengeEquationActivity.class;
            case EQUATION:
                return ChallengeGolfActivity.class;
            case GOLF:
                return ChallengeDicesActivity.class;
            case DICES:
                return GameFinishedActivity.class;
            default:
                return MainActivity.class;
        }
    }

    public String getHint() {
        switch (this) {
            case BLOW_CANDLE:
                return "I've heard wind works pretty good against fire...";
            case SLEEP:
                return "Aren't your eyes tired from your screen? Well, his are.";
            case EQUATION:
                return "Sometimes in math you just have to look from a different \"angle\"...";
            case GOLF:
                return "Well... You know, just push your ball to the pit?";
            case DICES:
                return "Shake it of baby! But only yours you know...";
            default:
                return null;
        }
    }

    public String getAnswer(){
        switch (this) {
            case BLOW_CANDLE:
                return "Blow some air into your microphone";
            case SLEEP:
                return "Lower yours screen brightness, so it will look like lights are turned off";
            case EQUATION:
                return "Rotate your phone upside down so your inequality spells 6<9";
            case GOLF:
                return "Tilt your phone to right so the ball will start falling in the pits direction";
            case DICES:
                return "Hold your enemy dices and shake your phone";
            default:
                return null;
        }
    }
}

