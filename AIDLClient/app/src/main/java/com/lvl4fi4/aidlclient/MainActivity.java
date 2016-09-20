package com.lvl4fi4.aidlclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lvl4fi4.aidlserver.IMainServer;

public class MainActivity extends Activity {
    TextView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log = (TextView) findViewById(R.id.txt);
        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.setText("");
                Intent serviceintent = new Intent();
                serviceintent.setComponent(new ComponentName("com.lvl4fi4.aidlserver",
                        "com.lvl4fi4.aidlserver.MainServer"));
                log.append("starting...\n");
                startService(serviceintent);
                log.append("connecting..\n");
                bindService(serviceintent, connection, BIND_AUTO_CREATE);

            }
        });

        ((Button)findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClassName("com.lvl4fi4.aidlserver",
                        "com.lvl4fi4.aidlserver.MainActivity");
                startActivityForResult(intent,555);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==555)
            log.append(data.getStringExtra("name")+"\n");

        super.onActivityResult(requestCode, resultCode, data);
    }

    IMainServer server;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            log.append("connected\n");
            server = IMainServer.Stub.asInterface(service);
            try {
                log.append(server.change("HELLOW WORLD!!") + "\n");
                server.stop();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            server = null;
            log.append("Disconnected\n");
        }
    };
}
