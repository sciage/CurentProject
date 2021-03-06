package in.voiceme.app.voiceme.infrastructure;

import android.app.Application;
import android.content.Context;
import com.evernote.android.job.JobManager;
import com.facebook.FacebookSdk;
import com.squareup.otto.Bus;
import in.voiceme.app.voiceme.Module;
import in.voiceme.app.voiceme.loginV2.RefreshTokenJobCreator;
import in.voiceme.app.voiceme.services.ServiceFactory;
import in.voiceme.app.voiceme.services.WebService;
import timber.log.Timber;

/**
 * Created by Harish on 7/20/2016.
 */
public class VoicemeApplication extends Application {
  private static Auth auth;
  private static Bus bus;
  private WebService webService;
  private static Context context;

  public VoicemeApplication() {
    bus = new Bus();
  }

  @Override public void onCreate() {
    super.onCreate();
    //Fabric.with(this, new Crashlytics());
    auth = new Auth(this);
    FacebookSdk.sdkInitialize(this);
    Module.register(this);
    webService = ServiceFactory.createRetrofitService(WebService.class);
    Timber.plant(new Timber.DebugTree() {
      // Add the line number to the TAG
      @Override protected String createStackElementTag(StackTraceElement element) {
        return super.createStackElementTag(element) + ":" + element.getLineNumber();
      }
    });
    context = getApplicationContext();
    /**
     *Creates a periodic job to refresh token
     */
    JobManager.create(this).addJobCreator(new RefreshTokenJobCreator());
  }

  public static Context getContext() {
    return context;
  }

  public WebService getWebService() {
    return webService;
  }

  public static Auth getAuth() {
    return auth;
  }

  public static Bus getBus() {
    return bus;
  }
}
