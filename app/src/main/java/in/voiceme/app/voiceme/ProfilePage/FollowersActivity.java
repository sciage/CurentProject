package in.voiceme.app.voiceme.ProfilePage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import in.voiceme.app.voiceme.R;
import in.voiceme.app.voiceme.RecyclerViewDetails.Person;
import in.voiceme.app.voiceme.RecyclerViewDetails.RVAdapter;
import in.voiceme.app.voiceme.infrastructure.BaseActivity;

public class FollowersActivity extends BaseActivity {
    private static final int REQUEST_VIEW_MESSAGE = 1;
    private List<Person> persons;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        getSupportActionBar().setTitle("Hugs LoginUser");
        toolbar.setNavigationIcon(R.mipmap.ic_ab_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                finish();
            }
        });

        rv = (RecyclerView) findViewById(R.id.user_follower_recyclerview);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
    }

    private void initializeData() {
        persons = new ArrayList<>();
        persons.add(new Person("Emma Wilson", R.mipmap.ic_launcher));
        persons.add(new Person("Lavery Maiss", R.mipmap.ic_launcher));
        persons.add(new Person("Lillie Watts", R.mipmap.ic_launcher));
    }

    private void initializeAdapter() {
        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);
    }
}
