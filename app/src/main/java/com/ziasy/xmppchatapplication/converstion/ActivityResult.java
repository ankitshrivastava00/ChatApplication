package com.ziasy.xmppchatapplication.converstion;

import android.content.Intent;

public class ActivityResult {
    public final int requestCode;
    public final int resultCode;
    public final Intent data;

    private ActivityResult(int requestCode, int resultCode, final Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    public static ActivityResult of(int requestCode, int resultCode, Intent data) {
        return new ActivityResult(requestCode,resultCode,data);
    }
}
