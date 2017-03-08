package com.app.sandboxcontenntresolver;


import android.app.Application;
import android.content.Context;

public class SandboxApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DependencyResolver.setContext(this);
    }
}
