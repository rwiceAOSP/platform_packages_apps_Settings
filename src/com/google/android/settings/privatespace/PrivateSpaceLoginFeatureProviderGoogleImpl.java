package com.google.android.settings.privatespace;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;

import com.android.settings.R;
import com.android.settings.privatespace.PrivateSpaceLoginFeatureProvider;

import java.io.IOException;

public class PrivateSpaceLoginFeatureProviderGoogleImpl
        implements PrivateSpaceLoginFeatureProvider {
    private static final String TAG = "PrivateSpaceGoogleImpl";

    private ActivityResultLauncher<Intent> mAddAccountToPrivateProfile;

    private final AccountManagerCallback<Bundle> mCallbackLocal =
            new AccountManagerCallback<>() {
                @Override
                public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
                    try {
                        final Intent intent =
                                accountManagerFuture
                                        .getResult()
                                        .getParcelable(AccountManager.KEY_INTENT);
                        if (intent == null) {
                            Log.e(TAG, "Failed to retrieve add account intent from authenticator");
                        } else {
                            mAddAccountToPrivateProfile.launch(intent);
                        }
                    } catch (AuthenticatorException | OperationCanceledException | IOException e) {
                        Log.e(TAG, "Failed to get add account intent Activity:  ", e);
                    }
                }
            };

    @Override
    public boolean initiateAccountLogin(
            Context context, ActivityResultLauncher<Intent> activityResultLauncher) {
        mAddAccountToPrivateProfile = activityResultLauncher;
        final Bundle addAccountOptions = new Bundle();
        addAccountOptions.putBoolean("allow_skip", true);
        AccountManager.get(context)
                .addAccount(
                        "com.google", null, null, addAccountOptions, null, mCallbackLocal, null);
        return context.getResources().getBoolean(R.bool.config_privatespace_account_login_enabled);
    }
}
