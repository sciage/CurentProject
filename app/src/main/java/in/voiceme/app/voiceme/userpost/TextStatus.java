package in.voiceme.app.voiceme.userpost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import in.voiceme.app.voiceme.R;
import in.voiceme.app.voiceme.infrastructure.BaseActivity;
import in.voiceme.app.voiceme.infrastructure.MainNavDrawer;

public class TextStatus extends BaseActivity {
    private TextView textView_category;
    private TextView textView_feeling;
    private TextView textView_status;
    //private TextModel textModel;

    private Button button_post_text_status;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_text_status);
        getSupportActionBar().setTitle("Text Status");
        setNavDrawer(new MainNavDrawer(this));

        textView_category = (TextView) findViewById(R.id.textView_category);
        textView_feeling = (TextView) findViewById(R.id.textView_feeling);
        textView_status = (TextView) findViewById(R.id.textView_status);
        button_post_text_status = (Button) findViewById(R.id.button_post_text_status);

        textView_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(TextStatus.this, CategoryActivity.class);
                startActivityForResult(categoryIntent, 1);
            }
        });
        textView_feeling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent feelingIntent = new Intent(TextStatus.this, FeelingActivity.class);
                startActivityForResult(feelingIntent, 2);
            }
        });
        textView_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent statusIntent = new Intent(TextStatus.this, StatusActivity.class);
                startActivityForResult(statusIntent, 3);
            }
        });

        button_post_text_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        //        Intent intent = new Intent(TextStatus.this, AudioActivity.class);
        //        startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                String result=data.getStringExtra("resultFromCategory");
                //  textModel.setCategory_id(result);
                Toast.makeText(this, "Data returned: " + result, Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 2){
            if (resultCode == RESULT_OK){
                String result=data.getStringExtra("resultFromFeeling");
                //  textModel.setFeeling_id(result);
                Toast.makeText(this, "Data returned: " + result, Toast.LENGTH_SHORT).show();

            }
        } else if (requestCode == 3){
            if (resultCode == RESULT_OK){
                String result=data.getStringExtra("resultFromStatus");
                //   textModel.setText_Status(result);
                Toast.makeText(this, "Data returned: " + result, Toast.LENGTH_SHORT).show();
            }
        }

    }


}
