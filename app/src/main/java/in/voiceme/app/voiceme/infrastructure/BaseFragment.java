package in.voiceme.app.voiceme.infrastructure;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.squareup.otto.Bus;

import static in.voiceme.app.voiceme.login.Constants.CONSTANT_PREF_FILE;


public abstract class BaseFragment extends Fragment {
    protected VoicemeApplication application;
    protected Bus bus;
    protected ActionScheduler scheduler;
    protected SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        application = (VoicemeApplication) getActivity().getApplication();
        scheduler = new ActionScheduler(application);
        preferences = getActivity().getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE);

        bus = application.getBus();
        bus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        scheduler.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        scheduler.onResume();
    }
}
