package com.muholabs.abusereport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class AuthenticateUser extends AppCompatActivity {

    FirebaseAuth auth;

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate_user);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null){
            //signed id
            startActivity(new Intent(AuthenticateUser.this,MainActivity.class));
            finish();

        }else{
            //not signed in

            signInUser();
        }


    }


    private void signInUser(){

        startActivityForResult(
                // Get an instance of AuthUI based on the default app
                AuthUI.getInstance().createSignInIntentBuilder().build(),
                RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {

                startActivity(new Intent(AuthenticateUser.this,MainActivity.class));
                finish();
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                   // showSnackbar(R.string.sign_in_cancelled);
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                   // showSnackbar(R.string.no_internet_connection);
                    return;
                }

               // showSnackbar(R.string.unknown_error);
               // Log.e(TAG, "Sign-in error: ", response.getError());
            }
        }
    }


}
