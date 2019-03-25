package com.ziasy.xmppchatapplication.ChatModulSocket;

/**
 * Created by PnP on 12-02-2018.
 */

import android.app.Application;

import com.ziasy.xmppchatapplication.common.Confiq;

import java.net.URISyntaxException;


import io.socket.client.IO;
import io.socket.client.Socket;

public class ChatApplication extends Application {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Confiq.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
