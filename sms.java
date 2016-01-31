package net.pocketmagic.android.AndroidBrowser;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class sms extends Activity implements OnClickListener {
    String msg;
    EditText phonenumber;
    String sendTo;

    public sms() {
        this.sendTo = "";
        this.msg = "";
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0000R.layout.phonenumber);
        this.phonenumber = (EditText) findViewById(C0000R.id.ph);
        ((Button) findViewById(C0000R.id.send)).setOnClickListener(this);
        ((Button) findViewById(C0000R.id.contact)).setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == C0000R.id.send) {
            SmsManager smsm = SmsManager.getDefault();
            this.msg = getIntent().getStringExtra("message");
            this.sendTo = this.phonenumber.getText().toString();
            smsm.sendTextMessage(this.sendTo, null, this.msg, null, null);
            Toast.makeText(getApplicationContext(), "Sending message via SMS", 1).show();
            startActivity(new Intent(this, MainActivity.class));
        } else if (v.getId() == C0000R.id.contact) {
            try {
                startActivityForResult(new Intent("android.intent.action.PICK", Contacts.CONTENT_URI), 1);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Error in intent : ", e.toString());
            }
        }
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == -1) {
            try {
                Cursor cur = managedQuery(data.getData(), null, null, null, null);
                ContentResolver contect_resolver = getContentResolver();
                if (cur.moveToFirst()) {
                    String id = cur.getString(cur.getColumnIndexOrThrow("_id"));
                    String name = "";
                    String no = "";
                    Cursor phoneCur = contect_resolver.query(Phone.CONTENT_URI, null, "contact_id = ?", new String[]{id}, null);
                    if (phoneCur.moveToFirst()) {
                        name = phoneCur.getString(phoneCur.getColumnIndex("display_name"));
                        no = phoneCur.getString(phoneCur.getColumnIndex("data1"));
                    }
                    this.phonenumber.setText(no);
                    Log.e("Phone no & name :***: ", new StringBuilder(String.valueOf(name)).append(" : ").append(no).toString());
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                Log.e("IllegalArgumentException :: ", e.toString());
            } catch (Exception e2) {
                e2.printStackTrace();
                Log.e("Error :: ", e2.toString());
            }
        }
    }
}
