package net.pocketmagic.android.AndroidBrowser;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class setting extends Activity implements OnClickListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0000R.layout.setting);
        ((Button) findViewById(C0000R.id.save)).setOnClickListener(this);
    }

    public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "Username and Password Saved", 1).show();
    }
}
