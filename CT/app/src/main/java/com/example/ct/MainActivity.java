package com.example.ct;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.clevertap.android.sdk.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements CTInboxListener
{
    private CleverTapAPI cleverTapDefaultInstance;
    Button app_inbox_button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        CleverTapAPI.setDebugLevel(1277182231);
        setContentView(R.layout.activity_main);
        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        app_inbox_button = (Button) findViewById(R.id.buttonClick);
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CleverTapAPI.getDefaultInstance(this).getAllInboxMessages();

        //Profile Creation

        HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
        profileUpdate.put("Name", "CT User");
        profileUpdate.put("Email", "prerna+p@clevertap.com");
        profileUpdate.put("Phone", "+919876543210");
        profileUpdate.put("Gender", "F");
        profileUpdate.put("Tz", "Asia/Kolkata");
        profileUpdate.put("Identity", "ct");


        profileUpdate.put("MSG-email", true);
        profileUpdate.put("MSG-push", true);
        profileUpdate.put("MSG-sms", true);

        cleverTapDefaultInstance.onUserLogin(profileUpdate);

        CleverTapAPI cleverTapAPI = CleverTapAPI.getDefaultInstance(getApplicationContext());
        cleverTapAPI.createNotificationChannel(getApplicationContext(), "CT", "ctapp", "gfgh", NotificationManager.IMPORTANCE_MAX, true);


        if (cleverTapDefaultInstance != null)
        {
            //Set the Notification Inbox Listener
            cleverTapDefaultInstance.setCTNotificationInboxListener(this);
            //Initialize the inbox and wait for callbacks on overridden methods
            cleverTapDefaultInstance.initializeInbox();
        }
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void inboxDidInitialize()
    {
            app_inbox_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<String> tabs = new ArrayList<>();
            tabs.add("Promotions");
            tabs.add("Offers");
            tabs.add("Others");//We support upto 2 tabs only. Additional tabs will be ignored

            CTInboxStyleConfig styleConfig = new CTInboxStyleConfig();
            styleConfig.setTabs(tabs);//Do not use this if you don't want to use tabs
            styleConfig.setTabBackgroundColor("#FF0000");//provide Hex code in string ONLY
            styleConfig.setSelectedTabIndicatorColor("#0000FF");
            styleConfig.setSelectedTabColor("#000000");
            styleConfig.setUnselectedTabColor("#FFFFFF");
            styleConfig.setBackButtonColor("#FF0000");
            styleConfig.setNavBarTitleColor("#FF0000");
            styleConfig.setNavBarTitle("MY INBOX");
            styleConfig.setNavBarColor("#FFFFFF");
            styleConfig.setInboxBackgroundColor("#00FF00");

            cleverTapDefaultInstance.showAppInbox(styleConfig); //Opens activity tith Tabs
        }
    });

    }

    @Override
    public void inboxMessagesDidUpdate() {

    }
}
