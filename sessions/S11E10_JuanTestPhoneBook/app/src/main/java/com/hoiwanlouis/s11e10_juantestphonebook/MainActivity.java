package com.hoiwanlouis.s11e10_juantestphonebook;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    public static final String DEBUG_TAG = "SimpleContactsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void listContact(View v) {

         class PhoneContactInfo{
             public int getPhoneContactID() {
                 return PhoneContactID;
             }

             public void setPhoneContactID(int phoneContactID) {
                 PhoneContactID = phoneContactID;
             }

             public String getContactName() {
                 return ContactName;
             }

             public void setContactName(String contactName) {
                 ContactName = contactName;
             }

             public String getContactNumber() {
                 return ContactNumber;
             }

             public void setContactNumber(String contactNumber) {
                 ContactNumber = contactNumber;
             }

             int PhoneContactID;
             String ContactName;
             String ContactNumber;


        }



        Log.d("START", "Getting all Contacts");
        ArrayList<PhoneContactInfo> arrContacts = new ArrayList<PhoneContactInfo>();
        PhoneContactInfo phoneContactInfo;
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = v.getContext().getContentResolver().query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone._ID}, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false)
        {
            String contactNumber= cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String contactName =  cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            int phoneContactID = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));


            phoneContactInfo = new PhoneContactInfo();
            phoneContactInfo.setPhoneContactID(phoneContactID);
            phoneContactInfo.setContactName(contactName);
            phoneContactInfo.setContactNumber(contactNumber);

            Log.d(DEBUG_TAG, "ID: " + phoneContactInfo.getPhoneContactID());
            Log.d(DEBUG_TAG, "Name: " + phoneContactInfo.getContactName());
            Log.d(DEBUG_TAG, "Phone: " + phoneContactInfo.getContactNumber());
            if (phoneContactInfo != null)
            {
                arrContacts.add(phoneContactInfo);
            }
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("END","Got all Contacts");










    }


    @Override
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
    }
}
