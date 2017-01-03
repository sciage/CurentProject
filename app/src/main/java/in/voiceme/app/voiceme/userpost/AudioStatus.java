package in.voiceme.app.voiceme.userpost;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;
import in.voiceme.app.voiceme.ActivityPage.MainActivity;
import in.voiceme.app.voiceme.R;
import in.voiceme.app.voiceme.infrastructure.BaseActivity;
import in.voiceme.app.voiceme.infrastructure.BaseSubscriber;
import in.voiceme.app.voiceme.infrastructure.MainNavDrawer;
import in.voiceme.app.voiceme.infrastructure.MySharedPreferences;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class AudioStatus extends BaseActivity {
    private TextView textView_category;
    private TextView textView_feeling;
    private TextView textView_status;
    private Button post_status;

    private String category;
    private String feeling;
    private String textStatus;

    private static final int REQUEST_RECORD_AUDIO = 0;
    private static final String AUDIO_FILE_PATH =
            Environment.getExternalStorageDirectory().getPath() + "/" + "recorded_audio.wav";


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_audio_status);
        getSupportActionBar().setTitle("Audio Status");
        setNavDrawer(new MainNavDrawer(this));

        textView_category = (TextView) findViewById(R.id.textView_category);
        textView_feeling = (TextView) findViewById(R.id.textView_feeling);
        textView_status = (TextView) findViewById(R.id.textView_status);
        post_status = (Button) findViewById(R.id.button_post_audio_status);

        textView_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(AudioStatus.this, CategoryActivity.class);
                startActivityForResult(categoryIntent, 1);
            }
        });
        textView_feeling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feelingIntent = new Intent(AudioStatus.this, FeelingActivity.class);
                startActivityForResult(feelingIntent, 2);
            }
        });
        textView_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent statusIntent = new Intent(AudioStatus.this, StatusActivity.class);
                startActivityForResult(statusIntent, 3);
            }
        });

        post_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (category == null || feeling == null || textStatus == null) {
                    Toast.makeText(AudioStatus.this, "Please select all categories to Post Status", Toast.LENGTH_SHORT).show();
                }
                // network call from retrofit
                try {
                    application.getWebService().postStatus(MySharedPreferences.getUserId(preferences),
                            textStatus, category, feeling, "")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseSubscriber<Response>() {
                                @Override
                                public void onNext(Response response) {
                                    Timber.e("Response " + response.getStatus() + "===" + response.getMsg());
                                    if (response.getStatus() == 1){
                                        Toast.makeText(AudioStatus.this, "Successfully posted status", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(AudioStatus.this, MainActivity.class));
                                    }
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RECORD_AUDIO) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Audio recorded successfully!", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, AUDIO_FILE_PATH, Toast.LENGTH_SHORT).show();
                Timber.e("file location" + AUDIO_FILE_PATH);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Audio was not recorded", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("resultFromCategory");
                category = result;
                Toast.makeText(this, "Data returned: " + result, Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("resultFromFeeling");
                feeling = result;
                Toast.makeText(this, "Data returned: " + result, Toast.LENGTH_SHORT).show();

            }
        } else if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("resultFromStatus");
                textStatus = result;
                Toast.makeText(this, "Data returned: " + result, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void recordAudio(View v) {
        AndroidAudioRecorder.with(this)
                // Required
                .setFilePath(AUDIO_FILE_PATH)
                .setColor(ContextCompat.getColor(this, R.color.recorder_bg))
                .setRequestCode(REQUEST_RECORD_AUDIO)

                // Optional
                .setSource(AudioSource.MIC)
                .setChannel(AudioChannel.STEREO)
                .setSampleRate(AudioSampleRate.HZ_48000)
                .setAutoStart(false)
                .setKeepDisplayOn(true)

                // Start recording
                .record();
    }
}
