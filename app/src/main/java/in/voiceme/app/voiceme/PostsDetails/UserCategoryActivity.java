package in.voiceme.app.voiceme.PostsDetails;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.like.LikeButton;

import java.util.List;

import in.voiceme.app.voiceme.DiscoverPage.LikeUnlikeClickListener;
import in.voiceme.app.voiceme.R;
import in.voiceme.app.voiceme.infrastructure.BaseActivity;
import in.voiceme.app.voiceme.infrastructure.BaseSubscriber;
import in.voiceme.app.voiceme.infrastructure.Constants;
import in.voiceme.app.voiceme.infrastructure.VoicemeApplication;
import in.voiceme.app.voiceme.services.PostsModel;
import rx.android.schedulers.AndroidSchedulers;

public class UserCategoryActivity extends BaseActivity {

    private int mPage;
    private RecyclerView recyclerView;
    private UserCategoryAdapter activityInteractionAdapter;

    private String categoryId;

    private String family = "family";
    private String health = "health";
    private String work = "work";
    private String social = "social";
    private String others = "others";

    private String currentCategoryID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_category);
        getSupportActionBar().setTitle("LoginUser Feelings");
        toolbar.setNavigationIcon(R.mipmap.ic_ab_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.user_category_recyclerview);

        categoryId = getIntent().getStringExtra(Constants.CATEGORY);

        if (categoryId.equals(family)){
            Toast.makeText(this, "emotion ID = 1", Toast.LENGTH_SHORT).show();
            setFeeling("1");
        } else if (categoryId.equals(health)){
            Toast.makeText(this, "emotion ID = 2", Toast.LENGTH_SHORT).show();
            setFeeling("2");
        } else if (categoryId.equals(work)){
            Toast.makeText(this, "emotion ID = 3", Toast.LENGTH_SHORT).show();
            setFeeling("3");
        } else if (categoryId.equals(social)){
            Toast.makeText(this, "emotion ID = 4", Toast.LENGTH_SHORT).show();
            setFeeling("4");
        } else if (categoryId.equals(others)){
            Toast.makeText(this, "emotion ID = 5", Toast.LENGTH_SHORT).show();
            setFeeling("5");
        }


        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        try {
            getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFeeling(String feelingID){
        this.currentCategoryID = feelingID;
    }

    @Override public String toString() {
        return "documentary";
    }

    private void getData() throws Exception {
        ((VoicemeApplication) getApplication()).getWebService()
                .getCategoryPosts(currentCategoryID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<PostsModel>>() {
                    @Override public void onNext(List<PostsModel> response) {
                        Log.e("RESPONSE:::", "Size===" + response.size());
                        showRecycleWithDataFilled(response);
                    }
                });
    }

    private void showRecycleWithDataFilled(final List<PostsModel> myList) {
        activityInteractionAdapter = new UserCategoryAdapter(myList, this);
        activityInteractionAdapter.setOnItemClickListener(new LikeUnlikeClickListener() {
            @Override public void onItemClick(PostsModel model, View v) {
                String name = model.getIdUserName();
            }

            @Override public void onLikeUnlikeClick(PostsModel model, LikeButton v) {

            }
        });
        recyclerView.setAdapter(activityInteractionAdapter);
    }
}
