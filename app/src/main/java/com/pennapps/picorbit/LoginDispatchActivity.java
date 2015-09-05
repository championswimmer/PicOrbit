package com.pennapps.picorbit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;
import com.parse.ui.ParseLoginDispatchActivity;

/**
 * Created by championswimmer on 5/9/15.
 */
public class LoginDispatchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseUser user = ParseUser.getCurrentUser();
        if (user.isAuthenticated()) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            ParseLoginBuilder builder = new ParseLoginBuilder(getApplicationContext());
            startActivityForResult(builder.build(), 0);
            finish();
        }
    }
}