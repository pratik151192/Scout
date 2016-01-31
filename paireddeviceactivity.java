package net.pocketmagic.android.AndroidBrowser;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.Set;

public class ListPairedDevicesActivity extends ListActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> btArrayAdapter = new ArrayAdapter(this, 17367043);
        Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String deviceBTName = device.getName();
                btArrayAdapter.add(new StringBuilder(String.valueOf(deviceBTName)).append("\n").append(getBTMajorDeviceClass(device.getBluetoothClass().getMajorDeviceClass())).toString());
            }
        }
        setListAdapter(btArrayAdapter);
    }

    private String getBTMajorDeviceClass(int major) {
        switch (major) {
            case 0:
                return "MISC";
            case 256:
                return "COMPUTER";
            case 512:
                return "PHONE";
            case 768:
                return "NETWORKING";
            case 1024:
                return "AUDIO_VIDEO";
            case 1280:
                return "PERIPHERAL";
            case 1536:
                return "IMAGING";
            case 1792:
                return "AUDIO_VIDEO";
            case 2048:
                return "TOY";
            case 2304:
                return "HEALTH";
            case 7936:
                return "UNCATEGORIZED";
            default:
                return "unknown!";
        }
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, confirmbluetooth.class);
        String msg = getIntent().getStringExtra("message");
        Toast.makeText(getApplicationContext(), "connection has been established", 1).show();
        intent.putExtra("message", msg);
        startActivity(intent);
    }
}
