package net.pocketmagic.android.AndroidBrowser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Date;

public class AndroidBrowserGUI extends Activity implements OnClickListener, SensorEventListener {
    static final String LOG_TAG = "SCOUT Browser";
    static final int idAddr = 104;
    static final int idBack = 102;
    static final int idBotLayout = 103;
    static final int idBotLayoutAlt = 110;
    static final int idButBack = 105;
    static final int idButFwd = 106;
    static final int idButGo = 109;
    static final int idButReload = 107;
    static final int idButStop = 108;
    static final int idTopLayout = 101;
    private final float NOISE;
    int count;
    int currentSecond;
    Date f0d;
    Button fullScreen;
    RelativeLayout ibMenu;
    RelativeLayout ibMenuBot;
    RelativeLayout ibMenuBotAlt;
    private Sensor mAccelerometer;
    private boolean mInitialized;
    private float mLastX;
    private float mLastY;
    private float mLastZ;
    private SensorManager mSensorManager;
    Button m_bButBack;
    Button m_bButFwd;
    Button m_bButGo;
    Button m_bButReload;
    Button m_bButStop;
    EditText m_etAddr;
    int m_nHTMLSize;
    String m_szPage;
    TextView m_tv;
    WebView m_web;

    class JavaScriptInterface {
        JavaScriptInterface() {
        }

        public void showHTML(String html) {
            AndroidBrowserGUI.this.m_nHTMLSize = 0;
            if (html != null) {
                AndroidBrowserGUI.this.m_nHTMLSize = html.length();
                Log.d(AndroidBrowserGUI.LOG_TAG, "HTML content is: " + html + "\nSize:" + AndroidBrowserGUI.this.m_nHTMLSize + " bytes");
            }
        }
    }

    private class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d(AndroidBrowserGUI.LOG_TAG, "onPageStarted");
            AndroidBrowserGUI.this.m_tv.setText("Loading page...");
            AndroidBrowserGUI.this.m_bButStop.setEnabled(true);
        }

        public void onPageFinished(WebView view, String url) {
            Log.d(AndroidBrowserGUI.LOG_TAG, "onPageFinished");
            AndroidBrowserGUI.this.m_tv.setText("Ready");
            AndroidBrowserGUI.this.m_bButStop.setEnabled(false);
            AndroidBrowserGUI.this.m_web.loadUrl("javascript:window.HTMLOUT.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            AndroidBrowserGUI.this.m_bButBack.setEnabled(AndroidBrowserGUI.this.m_web.canGoBack());
            AndroidBrowserGUI.this.m_bButFwd.setEnabled(AndroidBrowserGUI.this.m_web.canGoForward());
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public AndroidBrowserGUI() {
        this.NOISE = 5.0f;
        this.count = 1;
        this.m_szPage = "http://www.google.com";
        this.m_nHTMLSize = 0;
        this.ibMenu = null;
        this.ibMenuBot = null;
        this.ibMenuBotAlt = null;
        this.f0d = null;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        this.f0d = new Date();
        System.out.println(" time is : " + this.f0d.getSeconds());
        RelativeLayout global_panel = new RelativeLayout(this);
        global_panel.setLayoutParams(new LayoutParams(-1, -1));
        global_panel.setGravity(119);
        global_panel.setBackgroundDrawable(getResources().getDrawable(C0000R.drawable.back));
        this.ibMenu = new RelativeLayout(this);
        this.ibMenu.setId(idTopLayout);
        this.ibMenu.setBackgroundDrawable(getResources().getDrawable(C0000R.drawable.line));
        this.ibMenu.setPadding(6, 6, 6, 6);
        LayoutParams topParams = new RelativeLayout.LayoutParams(-1, -2);
        topParams.addRule(10);
        global_panel.addView(this.ibMenu, topParams);
        this.m_bButGo = new Button(this);
        this.m_bButGo.setId(idButGo);
        this.m_bButGo.setOnClickListener(this);
        this.m_bButGo.setText("Go");
        this.m_bButGo.setTextSize((float) 12);
        this.m_bButGo.setTypeface(Typeface.create("arial", 1));
        RelativeLayout.LayoutParams lpb5 = new RelativeLayout.LayoutParams(-2, -2);
        lpb5.addRule(9);
        lpb5.addRule(10);
        this.ibMenu.addView(this.m_bButGo, lpb5);
        this.m_etAddr = new EditText(this);
        this.m_etAddr.setId(idAddr);
        this.m_etAddr.setText(this.m_szPage);
        this.m_etAddr.setFocusable(true);
        this.m_etAddr.setTextSize((float) 12);
        this.m_etAddr.setTypeface(Typeface.create("arial", 0));
        RelativeLayout.LayoutParams lpbEdit = new RelativeLayout.LayoutParams(-1, -2);
        lpbEdit.addRule(1, idButGo);
        lpbEdit.addRule(10);
        this.ibMenu.addView(this.m_etAddr, lpbEdit);
        this.ibMenuBot = new RelativeLayout(this);
        this.ibMenuBot.setId(idBotLayout);
        this.ibMenuBot.setBackgroundDrawable(getResources().getDrawable(C0000R.drawable.line));
        this.ibMenuBot.setPadding(6, 6, 6, 6);
        RelativeLayout.LayoutParams botParams = new RelativeLayout.LayoutParams(-1, -2);
        botParams.addRule(12);
        global_panel.addView(this.ibMenuBot, botParams);
        this.m_bButBack = new Button(this);
        this.m_bButBack.setId(idButBack);
        this.m_bButBack.setOnClickListener(this);
        this.m_bButBack.setText("Back");
        this.m_bButBack.setTextSize((float) 12);
        this.m_bButBack.setTypeface(Typeface.create("arial", 1));
        RelativeLayout.LayoutParams lpb1 = new RelativeLayout.LayoutParams(-2, -2);
        lpb1.addRule(9);
        lpb1.addRule(10);
        this.ibMenuBot.addView(this.m_bButBack, lpb1);
        this.m_bButFwd = new Button(this);
        this.m_bButFwd.setId(idButFwd);
        this.m_bButFwd.setOnClickListener(this);
        this.m_bButFwd.setText("Fwd");
        this.m_bButFwd.setTextSize((float) 12);
        this.m_bButFwd.setTypeface(Typeface.create("arial", 1));
        RelativeLayout.LayoutParams lpb2 = new RelativeLayout.LayoutParams(-2, -2);
        lpb2.addRule(1, idButBack);
        lpb2.addRule(10);
        this.ibMenuBot.addView(this.m_bButFwd, lpb2);
        this.m_bButReload = new Button(this);
        this.m_bButReload.setId(idButReload);
        this.m_bButReload.setOnClickListener(this);
        this.m_bButReload.setText("Rld");
        this.m_bButReload.setTextSize((float) 12);
        this.m_bButReload.setTypeface(Typeface.create("arial", 1));
        RelativeLayout.LayoutParams lpb3 = new RelativeLayout.LayoutParams(-2, -2);
        lpb3.addRule(1, idButFwd);
        lpb3.addRule(10);
        this.ibMenuBot.addView(this.m_bButReload, lpb3);
        this.m_bButStop = new Button(this);
        this.m_bButStop.setId(idButStop);
        this.m_bButStop.setOnClickListener(this);
        this.m_bButStop.setText("Stop");
        this.m_bButStop.setTextSize((float) 12);
        this.m_bButStop.setTypeface(Typeface.create("arial", 1));
        RelativeLayout.LayoutParams lpb4 = new RelativeLayout.LayoutParams(-2, -2);
        lpb4.addRule(1, idButReload);
        lpb4.addRule(10);
        this.ibMenuBot.addView(this.m_bButStop, lpb4);
        this.m_tv = new TextView(this);
        this.m_tv.setText("Status");
        this.m_tv.setTextColor(Color.rgb(255, 255, 255));
        this.m_tv.setTextSize((float) 12);
        this.m_tv.setTypeface(Typeface.create("arial", 1));
        RelativeLayout.LayoutParams lpcTV = new RelativeLayout.LayoutParams(-2, -2);
        lpcTV.addRule(1, idButStop);
        lpcTV.addRule(15);
        this.ibMenuBot.addView(this.m_tv, lpcTV);
        this.m_web = new WebView(this);
        this.m_web.setLayoutParams(new LayoutParams(-1, -1));
        RelativeLayout.LayoutParams midParams = new RelativeLayout.LayoutParams(-1, -1);
        midParams.addRule(2, this.ibMenuBot.getId());
        midParams.addRule(3, this.ibMenu.getId());
        global_panel.addView(this.m_web, midParams);
        this.m_web.getSettings().setJavaScriptEnabled(true);
        this.m_web.setWebViewClient(new MyWebViewClient(null));
        if (this.m_szPage != null) {
            this.m_web.loadUrl(this.m_szPage);
        }
        this.m_web.addJavascriptInterface(new JavaScriptInterface(), "HTMLOUT");
        setContentView(global_panel);
        this.mInitialized = false;
        this.mSensorManager = (SensorManager) getSystemService("sensor");
        this.mAccelerometer = this.mSensorManager.getDefaultSensor(1);
        this.mSensorManager.registerListener(this, this.mAccelerometer, 3);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0000R.menu.browser_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case C0000R.id.fullscreen:
                this.ibMenu.setVisibility(8);
                this.ibMenuBot.setVisibility(8);
                return true;
            case C0000R.id.showtabs:
                this.ibMenu.setVisibility(0);
                this.ibMenuBot.setVisibility(0);
                return true;
            case C0000R.id.exit:
                Intent startMain = new Intent("android.intent.action.MAIN");
                startMain.addCategory("android.intent.category.HOME");
                startMain.setFlags(268435456);
                startActivity(startMain);
                return true;
            case C0000R.id.messenger:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onResume() {
        super.onResume();
        this.mSensorManager.registerListener(this, this.mAccelerometer, 3);
    }

    protected void onPause() {
        super.onPause();
        this.mSensorManager.unregisterListener(this);
    }

    public void onClick(View arg0) {
        int id = arg0.getId();
        if (id == idBack) {
            finish();
        }
        if (id == idButGo) {
            this.m_szPage = this.m_etAddr.getText().toString();
            Log.d(LOG_TAG, "Go for page:" + this.m_szPage);
            if (this.m_szPage != null) {
                this.m_web.loadUrl(this.m_szPage);
            }
        }
        if (id == idButBack) {
            Log.d(LOG_TAG, "Go back");
            this.m_web.goBack();
        }
        if (id == idButFwd) {
            Log.d(LOG_TAG, "Go forward");
            this.m_web.goForward();
        }
        if (id == idButReload) {
            Log.d(LOG_TAG, "Reload page");
            this.m_web.reload();
        }
        if (id == idButStop) {
            Log.d(LOG_TAG, "Stop loading page");
            this.m_web.stopLoading();
        }
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if (this.mInitialized) {
            float deltaX = Math.abs(this.mLastX - x);
            float deltaY = Math.abs(this.mLastY - y);
            float deltaZ = Math.abs(this.mLastZ - z);
            if (deltaX < 5.0f) {
                deltaX = 0.0f;
            }
            if (deltaY < 5.0f) {
                deltaY = 0.0f;
            }
            if (deltaZ < 5.0f) {
            }
            this.mLastX = x;
            this.mLastY = y;
            this.mLastZ = z;
            if (deltaX > deltaY) {
                this.m_web.reload();
                return;
            }
            return;
        }
        this.mLastX = x;
        this.mLastY = y;
        this.mLastZ = z;
        this.mInitialized = true;
    }
}
