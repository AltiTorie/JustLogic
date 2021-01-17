package alpha.net.com.altitorie.justlogic.Settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import alpha.net.com.altitorie.justlogic.R;

public class AboutAuthorSettingsFragment extends Fragment {

    private String GITHUB_URL = "https://github.com/AltiTorie";
    private String LINKEDIN_URL = "https://www.linkedin.com/in/arkadiusz-stepniak/";

    public AboutAuthorSettingsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_about_author_settings, container, false);
        Button b = view.findViewById(R.id.settings_aboutAuthor_github_button);
        b.setOnClickListener(this::goToGithub);

        Button b2 = view.findViewById(R.id.settings_aboutAuthor_linkedin_button);
        b2.setOnClickListener(this::goToLinkedin);
        return view;
    }

    public void goToGithub(View view){
        openLink(GITHUB_URL);
    }

    public void goToLinkedin(View view){
        openLink(LINKEDIN_URL);
    }

    private void openLink(String link){
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}