package in.voiceme.app.voiceme.PostsDetails;

import android.os.Bundle;

import in.voiceme.app.voiceme.R;
import in.voiceme.app.voiceme.infrastructure.BaseActivity;
import in.voiceme.app.voiceme.infrastructure.MainNavDrawer;

public class PostsDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_details);
        getSupportActionBar().setTitle("Home");
        setNavDrawer(new MainNavDrawer(this));
    }
}
