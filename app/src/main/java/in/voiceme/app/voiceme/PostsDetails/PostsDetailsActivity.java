package in.voiceme.app.voiceme.PostsDetails;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import in.voiceme.app.voiceme.R;
import in.voiceme.app.voiceme.infrastructure.BaseActivity;
import in.voiceme.app.voiceme.infrastructure.MainNavDrawer;

public class PostsDetailsActivity extends BaseActivity implements View.OnClickListener {

    private MessageAdapter mMessageAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    EditText mMessageEditText;
    ImageButton mSendMessageImageButton;
    RecyclerView mMessageRecyclerView;

    private MessageAdapter.InsertMessageListener mInsertMessageListener = new MessageAdapter.InsertMessageListener() {

        @Override
        public void onMessageInserted(int position) {
            mLinearLayoutManager.scrollToPosition(position);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_details);
        getSupportActionBar().setTitle("Home");
        setNavDrawer(new MainNavDrawer(this));
        mMessageEditText = (EditText) findViewById(R.id.et_message);
        mSendMessageImageButton = (ImageButton) findViewById(R.id.btn_send_message);
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.rv_messages);

        initRecyclerView();

        mSendMessageImageButton.setOnClickListener(this);

    }

    private void initRecyclerView() {
        mMessageAdapter = new MessageAdapter(PostsDetailsActivity.this, mInsertMessageListener);

        mLinearLayoutManager = new LinearLayoutManager(PostsDetailsActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mLinearLayoutManager.setStackFromEnd(true);

        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mMessageAdapter);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_send_message){
            sendMessage();
        }
    }

    void sendMessage() {
        String message = mMessageEditText.getText().toString();
        String imageUri = "https://pp.vk.me/c631524/v631524029/453fe/YONZuru763Q.jpg";
        String userName = "@user_name2133";

        if (!TextUtils.isEmpty(message)) {
            mMessageEditText.setText("");
            mMessageAdapter.addMessage(new MessageItem(message, imageUri, userName));
        } else {
            mMessageAdapter.addMessage(new MessageItem("AAA???777??/A", "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQmNCk7L76tvylZw_VPB3oYMHUDejqnl4TvgpGhpB4qEJdu1oDh", "SlowPoke"));
        }
    }
}
