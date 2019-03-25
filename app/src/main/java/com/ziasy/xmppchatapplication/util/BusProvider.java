package com.ziasy.xmppchatapplication.util;

import android.os.Handler;
import android.os.Looper;
/*
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;*/

public final class BusProvider {
  /*  private static  Bus bus = new Bus(ThreadEnforcer.ANY) ;


    public static Bus getInstance() {

        return bus;
    }

    private BusProvider() {

    }

    public static class MainThreadBus extends Bus {
        private final Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void post(final Object event) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                super.post(event);
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        MainThreadBus.super.post(event);
                    }
                });
            }
        }
    }*/
}
