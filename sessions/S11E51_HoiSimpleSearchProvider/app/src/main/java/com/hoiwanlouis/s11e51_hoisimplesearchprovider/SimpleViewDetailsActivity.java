package com.hoiwanlouis.s11e51_hoisimplesearchprovider;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class SimpleViewDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        try {
            Intent launchIntent = getIntent();
            Uri launchData = launchIntent.getData();
            String id = launchData.getLastPathSegment();
            Uri dataDetails = Uri.withAppendedPath(
                    SimpleFieldnotesContentProvider.CONTENT_URI, id);

            CursorLoader loader = new CursorLoader(this,
                    dataDetails,
                    null, null,	null, null);
            Cursor cursor = loader.loadInBackground();

            cursor.moveToFirst();
            String fieldnoteTitle = cursor
                    .getString(cursor
                            .getColumnIndex(SimpleFieldnotesContentProvider.FIELDNOTES_TITLE));
            String fieldnoteBody = cursor
                    .getString(cursor
                            .getColumnIndex(SimpleFieldnotesContentProvider.FIELDNOTES_BODY));
            TextView fieldnoteView = (TextView) findViewById(R.id.text_title);
            fieldnoteView.setText(fieldnoteTitle);
            TextView bodyView = (TextView) findViewById(R.id.text_body);
            bodyView.setLinksClickable(true);
            bodyView.setAutoLinkMask(Linkify.ALL);
            bodyView.setText(fieldnoteBody);
        } catch (Exception e) {
            Toast.makeText(this, "Failed.", Toast.LENGTH_LONG).show();
        }
    }

}

