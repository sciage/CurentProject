package in.voiceme.app.voiceme.contactPage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.HashMap;
import java.util.Map;

import in.voiceme.app.voiceme.R;
import in.voiceme.app.voiceme.infrastructure.BaseActivity;
import in.voiceme.app.voiceme.infrastructure.MainNavDrawer;
import in.voiceme.app.voiceme.login.account.AccountManager;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class ContactsActivity extends BaseActivity {
    private static final String TWITTER_KEY = "I6Zt8s6wSZzMtnPqun18Raw0T";
    private static final String TWITTER_SECRET = "Jb92MdEm2GmK40RMqZvoxmjTFR4aUipanCOYr3BHloy43cvOsA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());

        setContentView(R.layout.activity_contacts);
        getSupportActionBar().setTitle("Home");
        setNavDrawer(new MainNavDrawer(this));

        // Create a digits button and callback
        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setCallback(new AuthCallback() {

            @Override
            public void success(DigitsSession session, String phoneNumber) {
                Timber.v("DIGITS SUCCESSFUL authentication");
                Timber.v("phone number: " + phoneNumber);

                TwitterAuthToken authToken = (TwitterAuthToken)session.getAuthToken();
                String value = authToken.token + ";" + authToken.secret;
                Map<String, String> logins = new HashMap<String, String>();
                logins.put("www.digits.com", value);

                // Store the data in Amazon Cognito
                AccountManager.getInstance().getCredentialsProvider().setLogins(logins);

                // Send the data to Amazon Lambda
                // 1. Setup a PhoneInfo (containing relevant information)
                PhoneInfo ph = new PhoneInfo();
                ph.setPhoneNumber(phoneNumber);
                ph.setId(session.getId());
                ph.setAccessToken(authToken.token);
                ph.setAccessTokenSecret(authToken.secret);

                // 2. Send the data to the function sendData to parse the request asynchronously
                sendData(ph);

            }

            @Override
            public void failure(DigitsException exception) {
                // Do something on failure
                Timber.d("Oops Digits issue");
            }
        });

        // Create a Twitter login button and callback

    }

    private void sendData(PhoneInfo phoneInfo){

        Timber.d( "LAMBDA: Sending Data");
        // 1. Setup a provider to allow posting to Amazon Lambda

        // 2. Setup a LambdaInvoker Factory w/ provider data
        LambdaInvokerFactory factory = new LambdaInvokerFactory(
                this.getApplicationContext(),
                Regions.US_EAST_1,
                AccountManager.getInstance().getCredentialsProvider());

        // 3. Create an interface (see MyInterface)
        final MyInterface myInterface = factory.build(MyInterface.class);

        // 3. Send the data to the "digitsLogin" function on Amazon Lambda.
        // Note: Make sure it is done in background, not in main thread.
        new AsyncTask<PhoneInfo, Void, String>() {

            @Override
            protected String doInBackground(PhoneInfo... params) {
                // invoke "echo" method. In case it fails, it will throw a
                // LambdaFunctionException.
                try {
                    Timber.d("LAMBDA: Attempting to send data");
                    return myInterface.digitsLogin(params[0]);
                } catch (LambdaFunctionException lfe) {
                    Log.e("amazon", "Failed to invoke echo", lfe);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result == null) {
                    Timber.d( "LAMBDA: Response from request is null");
                    return;
                } else {
                    Timber.d( "LAMBDA: Received result");
                    Timber.d( result);
                }
                // Do a toast
                Timber.d( "LAMBDA: Making Toast with result");
                Toast.makeText(ContactsActivity.this, result, Toast.LENGTH_LONG).show();
                // Clear session on logout
                Digits.clearActiveSession();

            }
        }.execute(phoneInfo);

    }
}
