package com.example.amplifytester.data;

import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignOutOptions;
import com.amazonaws.mobile.client.results.SignInResult;
import com.example.amplifytester.data.model.LoggedInUser;

import java.io.IOException;
import java.util.Collections;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            AWSMobileClient.getInstance().signIn(username, password, Collections.emptyMap(), new Callback<SignInResult>() {
                @Override
                public void onResult(SignInResult result) {
                    Log.i("AWSMobileClient", "Login getSignInState: " + result.getSignInState().name());
                }

                @Override
                public void onError(Exception e) {
                    Log.e("AWSMobileClient", "Login via mobileclient failed", e);
                }
            });
            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        AWSMobileClient.getInstance().signOut(SignOutOptions.builder().build(), new Callback<Void>() {
            @Override
            public void onResult(Void result) {
                Log.i("AWSMobileClient", "Signout via mobileclient successful");
            }

            @Override
            public void onError(Exception e) {
                Log.e("AWSMobileClient", "Signout via mobileclient failed", e);
            }
        });
    }
}