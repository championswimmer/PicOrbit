package com.pennapps.picorbit;

import com.parse.ui.ParseLoginDispatchActivity;

/**
 * Created by championswimmer on 5/9/15.
 */
public class LoginDispatchActivity extends ParseLoginDispatchActivity {
    @Override
    protected Class<?> getTargetClass() {
        return MainActivity.class;
    }
}