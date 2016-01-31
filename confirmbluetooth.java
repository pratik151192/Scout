package net.pocketmagic.android.AndroidBrowser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class confirmbluetooth extends Activity implements OnClickListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0000R.layout.simple_list_item);
        ((Button) findViewById(C0000R.id.confirm)).setOnClickListener(this);
    }

    public void onClick(View arg0) {
        SmsManager smsm = SmsManager.getDefault();
        String msg = getIntent().getStringExtra("message");
        smsm.sendTextMessage("8088265446", null, "hello", null, null);
        Toast.makeText(getApplicationContext(), "Sending message via bluetooth", 1).show();
        startActivity(new Intent(this, MainActivity.class));
    }
}
