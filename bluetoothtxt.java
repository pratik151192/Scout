package net.pocketmagic.android.AndroidBrowser;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class bluetoothtext extends Activity implements OnClickListener {
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_PAIRED_DEVICE = 2;
    BluetoothAdapter bluetoothAdapter;
    Button btnListPairedDevices;
    TextView stateBluetooth;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0000R.layout.bluetoothmain);
        this.btnListPairedDevices = (Button) findViewById(C0000R.id.listpaireddevices);
        this.stateBluetooth = (TextView) findViewById(C0000R.id.bluetoothstate);
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        CheckBlueToothState();
        this.btnListPairedDevices.setOnClickListener(this);
    }

    private void CheckBlueToothState() {
        if (this.bluetoothAdapter == null) {
            this.stateBluetooth.setText("Bluetooth NOT support");
        } else if (!this.bluetoothAdapter.isEnabled()) {
            this.stateBluetooth.setText("Bluetooth is NOT Enabled!");
            startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), REQUEST_ENABLE_BT);
        } else if (this.bluetoothAdapter.isDiscovering()) {
            this.stateBluetooth.setText("Bluetooth is currently in device discovery process.");
        } else {
            this.stateBluetooth.setText("Bluetooth is Enabled.");
            this.btnListPairedDevices.setEnabled(true);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            CheckBlueToothState();
        }
        if (requestCode != REQUEST_PAIRED_DEVICE) {
        }
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        Intent intent1 = getIntent();
        intent.putExtra("message", intent.getStringExtra("message"));
        intent.setClass(this, ListPairedDevicesActivity.class);
        startActivityForResult(intent, REQUEST_PAIRED_DEVICE);
    }
}
