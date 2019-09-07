package com.mjb.android.mvvm.di

import android.app.Activity
import android.app.Application
import android.os.Bundle

abstract class ActivityCreatedCallback : Application.ActivityLifecycleCallbacks {

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}