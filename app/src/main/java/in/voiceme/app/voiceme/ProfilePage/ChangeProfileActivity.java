package in.voiceme.app.voiceme.ProfilePage;

import android.os.Bundle;
import android.widget.EditText;

import in.voiceme.app.voiceme.R;
import in.voiceme.app.voiceme.infrastructure.BaseActivity;
import in.voiceme.app.voiceme.infrastructure.MainNavDrawer;

public class ChangeProfileActivity extends BaseActivity {
    private EditText username;
    private EditText aboutme;
    private EditText userAge;
    private EditText userGender;
    private EditText userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        getSupportActionBar().setTitle("Home");
        setNavDrawer(new MainNavDrawer(this));

        username = (EditText) findViewById(R.id.edittext_profile_username);
        aboutme = (EditText) findViewById(R.id.edittext_profile_aboutme);
        userAge = (EditText) findViewById(R.id.edittext_profile_age);
        userGender = (EditText) findViewById(R.id.edittext_profile_gender);
        userLocation = (EditText) findViewById(R.id.edittext_profile_location);

    }
}
