package com.lvl4fi4.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MainServer extends Service {
    public MainServer() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return binder;
    }

    IMainServer.Stub binder = new IMainServer.Stub() {
        @Override
        public String change(String s) throws RemoteException {
            String tmp = "";
            for (char c : s.toCharArray())
                tmp = String.valueOf(c) + tmp;
            return tmp;
        }

        @Override
        public void stop() throws RemoteException {
            stopSelf();
            System.exit(0);
        }
    };
}
