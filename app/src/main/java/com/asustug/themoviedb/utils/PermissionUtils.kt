package com.asustug.themoviedb.utils

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager

object PermissionUtils {

    fun useRunTimePermissions(): Boolean {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    fun hasPermission(activity: AppCompatActivity, permission: String): Boolean {
        if (useRunTimePermissions()) {
            return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true
    }

    fun requestPermissions(
        activity: AppCompatActivity,
        permission: Array<String>,
        requestCode: Int = 100
    ) {
        if (useRunTimePermissions()) {
            activity.requestPermissions(permission, requestCode);
        }
    }

    fun requestPermissions(
        fragment: androidx.fragment.app.Fragment,
        permission: Array<String>,
        requestCode: Int
    ) {
        if (useRunTimePermissions()) {
            fragment.requestPermissions(permission, requestCode);
        }
    }

    fun shouldShowRational(activity: AppCompatActivity, permission: String): Boolean {
        if (useRunTimePermissions()) {
            return activity.shouldShowRequestPermissionRationale(permission);
        }
        return false;
    }

    fun shouldAskForPermission(activity: AppCompatActivity, permission: String): Boolean {
        if (useRunTimePermissions()) {
            return !hasPermission(activity, permission) &&
                    (!hasAskedForPermission(activity, permission) ||
                            shouldShowRational(activity, permission));
        }
        return false;
    }

    fun goToAppSettings(activity: AppCompatActivity) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", activity.getPackageName(), null)
        );
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    fun hasAskedForPermission(activity: AppCompatActivity, permission: String): Boolean {

        return PreferenceManager
            .getDefaultSharedPreferences(activity)
            .getBoolean(permission, false);
    }

    fun markedPermissionAsAsked(activity: AppCompatActivity, permission: String) {
        PreferenceManager
            .getDefaultSharedPreferences(activity)
            .edit()
            .putBoolean(permission, true)
            .apply();
    }
}