package alpha.net.com.altitorie.justlogic.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import alpha.net.com.altitorie.justlogic.Activities.Challenges.ChallengeClass;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

import alpha.net.com.altitorie.justlogic.R;

import static android.content.Context.MODE_PRIVATE;

public class OtherSettingsFragment extends Fragment {


    public OtherSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other_settings, container, false);
        Button b = view.findViewById(R.id.other_settings_reset_button);
        b.setOnClickListener(this::resetProgress);
        return view;
    }


    public void resetProgress(View view) {
        SharedPreferences pref = Objects.requireNonNull(getActivity()).getApplicationContext().getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("current_challenge", ChallengeClass.NONE.toString());
        editor.apply();

        Toast.makeText(view.getContext(), "Progress reseted", Toast.LENGTH_SHORT).show();
    }



}