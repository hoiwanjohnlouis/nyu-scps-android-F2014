package com.example.hoiwanlouis.hoiaddressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

/**
 * Created by hoiwanlouis on 11/8/14.
 *
 */
public class DatabaseConnector {

    private final String DEBUG_TAG = this.getClass().getSimpleName();


    // database name
    private static final String DATABASE_NAME = "UserContacts";

    // for interacting with the database
    private SQLiteDatabase database;
    // creates the database
    private DatabaseOpenHelper databaseOpenHelper;


    // public constructor for DatabaseConnector
    public DatabaseConnector(Context context) {
        Log.i(DEBUG_TAG, "in DatabaseConnector()");
        // create a new DatabaseOpenHelper
        databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
    } // end constructor DatabaseConnector

    // open the database connection
    public void open() throws SQLException {
        Log.i(DEBUG_TAG, "in open()");
        // create or open a database for reading/writing
        database = databaseOpenHelper.getWritableDatabase();
    } // end method open()

    // close the database connection
    public void close() {
        Log.i(DEBUG_TAG, "in close()");
        if (database != null) {
            // close the database connection
            database.close();
        }
    } // end method close()

    // inserts a new contact in the database
    public long insertContact(String name, String phone, String email,
                              String street, String city, String state, String zip) {
        Log.i(DEBUG_TAG, "in insertContact()");

        // create a new contact object
        ContentValues newContact = new ContentValues();
        newContact.put("name", name);
        newContact.put("phone", phone);
        newContact.put("email", email);
        newContact.put("street", street);
        newContact.put("city", city);
        newContact.put("state", state);
        newContact.put("zip", zip);

        // open the database
        open();
        long rowID = database.insert("contacts", null, newContact);
        // close the database, always free resources when done. this is not batch processing.
        close();

        return rowID;
    } // end method insertContact

    // updates an existing contact in the database
    public void updateContact(long id, // used to access the contact object from database
                              String name, String phone, String email,
                              String street, String city, String state, String zip) {
        Log.i(DEBUG_TAG, "in updateContact()");

        // build editContact object
        ContentValues editContact = new ContentValues();
        editContact.put("name", name);
        editContact.put("phone", phone);
        editContact.put("email", email);
        editContact.put("street", street);
        editContact.put("city", city);
        editContact.put("state", state);
        editContact.put("zip", zip);

        open(); // open the database
        database.update("contacts", editContact, "_id=" + id, null);
        close(); // close the database
    } // end method updateContact

    // return a Cursor with all contact names in the database
    public Cursor getAllContacts() {
        Log.i(DEBUG_TAG, "in getAllContact()");
        return database.query("contacts", new String[] {"_id", "name"},
                null, null, null, null, "name");
    } // end method getAllContacts

    // return a Cursor containing specified contact's information
    public Cursor getOneContact(long id) {
        Log.i(DEBUG_TAG, "in getOneContact()");
        return database.query(
                "contacts", null, "_id=" + id, null, null, null, null);
    } // end method getOneContact

    // delete the contact specified by the given String name
    public void deleteContact(long id) {
        Log.i(DEBUG_TAG, "in deleteContact()");
        open(); // open the database
        database.delete("contacts", "_id=" + id, null);
        close(); // close the database
    } // end method deleteContact

    // implementing abstract class SQLiteOpenHelper as DatabaseOpenHelper
    private class DatabaseOpenHelper extends SQLiteOpenHelper
    {
        // constructor
        public DatabaseOpenHelper(Context context, String name,
                                  CursorFactory factory, int version) {
            super(context, name, factory, version);
            Log.i(DEBUG_TAG, "in DatabaseOpenHelper()");
        } // end method DatabaseOpenHelper()

        // creates the contacts table when the database is created
        @Override
        public void onCreate(SQLiteDatabase db) {
            // query to create a new table named contacts
            String createQuery = "CREATE TABLE contacts" +
                    "(_id integer primary key autoincrement," +
                    "name TEXT, phone TEXT, email TEXT, " +
                    "street TEXT, city TEXT, state TEXT, zip TEXT);";
            Log.i(DEBUG_TAG, new StringBuilder().append("in onCreate(): ").append(createQuery).toString());

            db.execSQL(createQuery); // execute query to create the database
        } // end onCreate()

        //
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(DEBUG_TAG, "in onUpgrade()");
        } // end method onUpgrade()
    } // end implementing abstract class SQLiteOpenHelper as DatabaseOpenHelper

} // end class DatabaseConnector
