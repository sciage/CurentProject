package in.voiceme.app.voiceme.ProfilePage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import in.voiceme.app.voiceme.R;
import in.voiceme.app.voiceme.infrastructure.BaseActivity;
import in.voiceme.app.voiceme.infrastructure.BaseSubscriber;
import in.voiceme.app.voiceme.infrastructure.MainNavDrawer;
import in.voiceme.app.voiceme.infrastructure.VoicemeApplication;
import in.voiceme.app.voiceme.services.PostsModel;
import rx.android.schedulers.AndroidSchedulers;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {
    private View avatarProgressFrame;

    private TextView username;
    private TextView about;
    private TextView total_posts;
    private TextView followers;
    private TextView following;

    private TextView age;
    private TextView gender;
    private TextView location;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_profile);
        setNavDrawer(new MainNavDrawer(this));

        try {
            getData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        avatarProgressFrame = findViewById(R.id.activity_profile_avatarProgressFrame);

        username = (TextView) findViewById(R.id.name);
        followers = (TextView) findViewById(R.id.profile_followers);
        following = (TextView) findViewById(R.id.profile_following);
        about = (TextView) findViewById(R.id.about);
        total_posts = (TextView) findViewById(R.id.user_profile_textview);

        age = (TextView) findViewById(R.id.age);
        gender = (TextView) findViewById(R.id.gender);
        location = (TextView) findViewById(R.id.location);

        followers.setOnClickListener(this);
        following.setOnClickListener(this);
        age.setOnClickListener(this);
        gender.setOnClickListener(this);
        location.setOnClickListener(this);
        total_posts.setOnClickListener(this);

        avatarProgressFrame.setVisibility(View.GONE);



        //   if (isProgressBarVisible)
        //     setProgressBarVisible(true);

    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.user_profile_textview){
            startActivity(new Intent(this, TotalPostsActivity.class));
        } else if(viewId == R.id.profile_followers){
            startActivity(new Intent(this, FollowersActivity.class));
        } else if (viewId == R.id.profile_following){
            startActivity(new Intent(this, FollowingActivity.class));
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.activity_profile_menuEdit) {
            startActivity(new Intent(this, ChangeProfileActivity.class));
            return true;
        } else if (itemId == R.id.activity_profile_menuChangePassword) {
            //  startActivity(new Intent(this, AppConfigsActivity.class));
            return true;
        }

        return false;
    }

    private void getData() throws Exception {
        ((VoicemeApplication) getApplication()).getWebService()
                .getFollowers("2")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<PostsModel>>() {
                    @Override
                    public void onNext(List<PostsModel> response) {
                        Log.e("RESPONSE:::", "Size===" + response.size());
                        //     followers.setText(String.valueOf(response.size()));
                    }
                });
    }

//   http://54.164.58.140/posts.php?follower=2

}