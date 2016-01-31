package net.pocketmagic.android.AndroidBrowser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class MainActivity extends Activity implements OnClickListener, Runnable {
    EditText mesg;
    String msg;

    public MainActivity() {
        this.msg = "";
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0000R.layout.table_layout);
        ((Button) findViewById(C0000R.id.button5)).setOnClickListener(this);
        ((Button) findViewById(C0000R.id.button3)).setOnClickListener(this);
        ((Button) findViewById(C0000R.id.button4)).setOnClickListener(this);
        ((Button) findViewById(C0000R.id.button1)).setOnClickListener(this);
        ((Button) findViewById(C0000R.id.button2)).setOnClickListener(this);
    }

    public void onClick(View v) {
        this.mesg = (EditText) findViewById(C0000R.id.editText1);
        this.msg = this.mesg.getText().toString();
        if (v.getId() == C0000R.id.button5) {
            Intent myIntent = new Intent(this, sms.class);
            myIntent.putExtra("message", this.msg);
            startActivity(myIntent);
        } else if (v.getId() == C0000R.id.button3) {
            Intent btIntent = new Intent(this, bluetoothtext.class);
            System.out.println("goes into atleast");
            btIntent.putExtra("message", this.msg);
            startActivity(btIntent);
        } else if (v.getId() == C0000R.id.button4) {
            Intent internetIntent = new Intent(this, Internet.class);
            internetIntent.putExtra("message", this.msg);
            startActivity(internetIntent);
        } else if (v.getId() == C0000R.id.button2) {
            startActivity(new Intent(this, AndroidBrowserGUI.class));
        } else if (v.getId() == C0000R.id.button1) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setType("vnd.android-dir/mms-sms");
            startActivity(intent);
        }
    }

    public void run() {
        System.out.println("thread working1");
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://trideep.0fees.net/sendsms.php");
            try {
                List<NameValuePair> nameValuePairs = new ArrayList(2);
                nameValuePairs.add(new BasicNameValuePair("phonenumber", "9964218671"));
                nameValuePairs.add(new BasicNameValuePair("message", this.msg));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                System.out.println("working");
            } catch (ClientProtocolException e) {
            } catch (IOException e2) {
            }
            System.out.println("thread working2");
            Thread.sleep(10);
        } catch (InterruptedException e3) {
        }
    }
}
