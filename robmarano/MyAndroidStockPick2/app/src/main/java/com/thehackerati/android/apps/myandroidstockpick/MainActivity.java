package com.thehackerati.android.apps.myandroidstockpick;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends Activity {
    private String TAG = this.getClass().getSimpleName();
    private Button goButton;
    private EditText editText;
    private static String initialCall = "http://api.flickr.com/services/feeds/photos_public.gne?id=26648248@N04&lang=en-us&format=atom";
    private Handler mHandler = new Handler();
    private TextSwitcher status;
    private ImageSwitcher imageSwitcher;
    private static final int IO_BUFFER_SIZE = 4 * 1024;
    DelayedLooperThread imageThread = new DelayedLooperThread();
    private String title = "non";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyFlashScreen().build();

        StrictMode.setThreadPolicy(policy);


        setContentView(R.layout.main);

        goButton = (Button) findViewById(R.id.button);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchNetworkResult4();
            }
        });

        editText = (EditText) findViewById(R.id.editText);
        editText.setText(MainActivity.initialCall);

        status = (TextSwitcher) findViewById(R.id.status);
        status.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                TextView tv = new TextView(MainActivity.this);
                tv.setGravity(Gravity.CENTER_VERTICAL
                        | Gravity.CENTER_HORIZONTAL);
                tv.setTextSize(24);
                return tv;
            }

        });
        imageSwitcher = (ImageSwitcher) findViewById(R.id.images);
        imageSwitcher.setFactory(new ImageSwitcher.ViewFactory() {

            public View makeView() {
                ImageView iv = new ImageView(MainActivity.this);
                iv.setBackgroundColor(0xFF000000);
                iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                iv.setLayoutParams(new ImageSwitcher.LayoutParams(
                        ImageSwitcher.LayoutParams.MATCH_PARENT,
                        ImageSwitcher.LayoutParams.MATCH_PARENT));
                return iv;
            }

        });
        //imageSwitcher.setInAnimation(in);
        //imageSwitcher.setOutAnimation(out);

        status.setText("<no status>");

        imageThread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    protected void fetchNetworkResult2() {
        new Thread() {
            public void run() {

                try {
                    URL text = new URL(MainActivity.initialCall);

                    XmlPullParserFactory parserCreator = XmlPullParserFactory
                            .newInstance();
                    XmlPullParser parser = parserCreator
                            .newPullParser();

                    parser.setInput(text.openStream(), null);

                    mHandler.post(new Runnable() {
                        public void run() {
                            status.setText("Parsing...");
                        }
                    });
                    int imgCount = 0;
                    int parserEvent = parser.getEventType();
                    while (parserEvent != XmlPullParser.END_DOCUMENT) {
                        switch (parserEvent) {
                            case XmlPullParser.START_TAG:
                                String tag = parser.getName();
                                if (tag.compareTo("link") == 0) {
                                    String relType = parser
                                            .getAttributeValue(null, "rel");
                                    if (relType.compareTo("enclosure") == 0) {
                                        String encType = parser
                                                .getAttributeValue(null,
                                                        "type");
                                        if (encType.startsWith("image/")) {
                                            String imageSrc = parser
                                                    .getAttributeValue(
                                                            null, "href");
                                            Log.i("Net", "image source = "
                                                    + imageSrc);
                                            final int curImageCount = ++imgCount;
                                            mHandler.post(new Runnable() {
                                                public void run() {
                                                    status.setText("imgCount = "
                                                            + curImageCount);
                                                }
                                            });
                                        }
                                    }
                                }
                                break;
                        }

                        parserEvent = parser.next();

                    }
                    mHandler.post(new Runnable() {
                        public void run() {
                            status.setText("Done...");
                        }
                    });

                } catch (Exception e) {
                    Log.e("Net", "Error in network call", e);
                }
            }
        }.start();
    }

    protected void fetchNetworkResult() {
        try {
            URL text = new URL(MainActivity.initialCall);
            HttpURLConnection http = (HttpURLConnection) text.openConnection();
            Log.i(TAG, "length = " + http.getContentLength());
            Log.i(TAG, "respCode = " + http.getResponseCode());
            Log.i(TAG, "contentType = " + http.getContentType());
            Log.i(TAG, "content = " + http.getContent());
            InputStream isText = http.getInputStream();
            byte[] bText = new byte[2048];
            FileOutputStream fos = openFileOutput("file.out",
                    MODE_PRIVATE);
            int numAvailable = isText.available();

            Log.i("Net", "available = " + numAvailable);

            int readSize = 0;
            while (readSize != -1) {
                numAvailable = isText.available();
                Log.i("Net", "available = " + numAvailable);

                readSize = isText.read(bText);

                if (readSize > 0) {
                    fos.write(bText, 0, readSize);
                }
                Log.i("Net", "readSize = " + readSize);

            }

            isText.close();
            http.disconnect();
            fos.close();
        } catch (MalformedURLException ex1) {
            Log.d(TAG, ex1.toString());
        } catch (Exception ex) {
            Log.d(TAG, ex.toString());
        }
    }

    protected void fetchNetworkResult3() {
        try {
            new ImageLoader()
                    .execute(new URL(MainActivity.initialCall));
        } catch (MalformedURLException e) {
            Log.e(TAG, "Failed to generate URL");
        }
    }

    protected void fetchNetworkResult4() {
        new Thread() {
            public void run() {

                try {
                    URL text = new URL(MainActivity.initialCall);

                    XmlPullParserFactory parserCreator = XmlPullParserFactory
                            .newInstance();
                    XmlPullParser parser = parserCreator
                            .newPullParser();

                    parser.setInput(text.openStream(), null);

                    mHandler.post(new Runnable() {
                        public void run() {
                            status.setText("Parsing...");
                        }
                    });
                    String tag;
                    boolean inTitle = false;
                    int imgCount = 0;
                    int parserEvent = parser.getEventType();
                    while (parserEvent != XmlPullParser.END_DOCUMENT) {

                        switch (parserEvent) {
                            case XmlPullParser.TEXT:
                                if (inTitle) {
                                    title = parser.getText();
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                tag = parser.getName();
                                if (tag.compareTo("title") == 0) {
                                    inTitle = false;
                                }
                                break;
                            case XmlPullParser.START_TAG:
                                tag = parser.getName();

                                if (tag.compareTo("title") == 0) {
                                    inTitle = true;
                                }
                                if (tag.compareTo("link") == 0) {
                                    String relType = parser.getAttributeValue(null, "rel");
                                    if (relType.compareTo("enclosure") == 0) {
                                        String encType = parser.getAttributeValue(null, "type");
                                        if (encType.startsWith("image/")) {
                                            final String imageSrc = parser.getAttributeValue(
                                                    null, "href");
                                            Log.i("Net", "image source = " + imageSrc);
                                            final int curImageCount = ++imgCount;

                                            mHandler.post(new Runnable() {
                                                public void run() {
                                                    status.setText("imgCount = "
                                                            + curImageCount);
                                                }
                                            });

                                            final String currentTitle = new String(title);
                                            imageThread.queueEvent(new Runnable() {
                                                public void run() {
                                                    // InputStream bmis;
                                                    BufferedInputStream in;
                                                    BufferedOutputStream out;
                                                        /* This code has a currently known bug see http://groups.google.com/group/android-developers/browse_thread/thread/4ed17d7e48899b26/a15129024bb845bf?show_docid=a15129024bb845bf
                                                        * for more information bmis = new URL(imageSrc).openStream();
                                                        * final Drawable image = new BitmapDrawable(BitmapFactory.decodeStream(bmis));
                                                        */
                                                    try {
                                                        in = new BufferedInputStream(new URL(imageSrc).openStream(), IO_BUFFER_SIZE);
                                                        final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                                                        out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
                                                        copy(in, out);
                                                        out.flush();

                                                        final byte[] data = dataStream.toByteArray();
                                                        Bitmap bitmap = BitmapFactory.decodeByteArray(
                                                                data,
                                                                0,
                                                                data.length);
                                                        final Drawable image = new BitmapDrawable(getResources(), bitmap);
                                                        mHandler.post(new Runnable() {
                                                            public void run() {
                                                                imageSwitcher.setImageDrawable(image);
                                                                status.setText(currentTitle);
                                                            }
                                                        });
                                                    } catch (Exception e) {
                                                        Log.e("Net", "Failed to grab image", e);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                                break;
                        }
                        parserEvent = parser.next();
                    }

                    mHandler.post(new Runnable() {
                        public void run() {
                            status.setText("Done...");
                        }
                    });
                } catch (Exception e) {
                    Log.e("Net", "Error in network call", e);
                }
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        imageThread.finish();
    }

    /**
     * Copy the content of the input stream into the output stream, using a
     * temporary byte array buffer whose size is defined by
     * {@link #IO_BUFFER_SIZE}.
     *
     * @param in  The input stream to copy from.
     * @param out The output stream to copy to.
     * @throws IOException If any error occurs during the copy.
     */
    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[IO_BUFFER_SIZE];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }


    protected class ImageLoader extends AsyncTask<URL, String, String> {

        @Override
        protected String doInBackground(URL... params) {
            // although this pattern takes more than one param, we'll just use
            // the first
            try {
                URL text = params[0];

                XmlPullParserFactory parserCreator;

                parserCreator = XmlPullParserFactory.newInstance();

                XmlPullParser parser = parserCreator.newPullParser();

                parser.setInput(text.openStream(), null);

                publishProgress("Parsing...");

                int imgCount = 0;
                int parserEvent = parser.getEventType();
                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_TAG:
                            String tag = parser.getName();
                            if (tag.compareTo("link") == 0) {
                                String relType = parser.getAttributeValue(null,
                                        "rel");
                                if (relType.compareTo("enclosure") == 0) {
                                    String encType = parser.getAttributeValue(null,
                                            "type");
                                    if (encType.startsWith("image/")) {
                                        String imageSrc = parser.getAttributeValue(
                                                null, "href");
                                        Log.i("Net", "image source = " + imageSrc);
                                        final int curImageCount = ++imgCount;
                                        publishProgress("imgCount = "
                                                + curImageCount);

                                    }
                                }
                            }
                            break;
                    }

                    parserEvent = parser.next();
                }

            } catch (Exception e) {
                Log.e("Net", "Failed in parsing XML", e);
                return "Finished with failure.";
            }

            return "Done...";
        }

        protected void onCancelled() {
            Log.e("Net", "Async task Cancelled");
        }

        protected void onPostExecute(String result) {
            status.setText(result);
        }

        protected void onPreExecute() {
            status.setText("About to load URL");
        }

        protected void onProgressUpdate(String... values) {
            // just one value, please
            status.setText(values[0]);
            super.onProgressUpdate(values);
        }
    }

    protected class DelayedLooperThread extends Thread {
        private boolean mDone = false;
        private ArrayList<Runnable> mEventQueue = new ArrayList<Runnable>();


        private long lastTime = 0;
        private long waitTime = 5000;

        public void run() {

            while (!mDone) {
                synchronized (this) {
                    long thisTime = System.currentTimeMillis();
                    long dif = thisTime - lastTime;
                    Log.i(TAG, "diff = " + dif);
                    if (dif < waitTime) {
                        try {
                            Log.i(TAG, "Gotta wait...");
                            wait(waitTime - dif);
                        } catch (InterruptedException e) {
                            Log.e(TAG, "Looper Error", e);

                        }
                    } else {
                        Runnable r = getEvent();
                        if (r != null) {
                            lastTime = thisTime;
                            r.run();
                        } else {
                            try {
                                Log.i(TAG, "Wait for queued event...");
                                wait();
                            } catch (InterruptedException e) {

                            }
                        }

                    }
                }
                Log.i("Net", "Looping...");
            }
            Log.i("Net", "Loop over.");
        }

        public void queueEvent(Runnable r) {
            synchronized (this) {
                mEventQueue.add(r);
                notify();
            }
        }

        private Runnable getEvent() {
            synchronized (this) {
                if (mEventQueue.size() > 0) {
                    return mEventQueue.remove(0);
                }

            }
            return null;
        }

        private void finish() {
            synchronized (this) {
                mDone = true;
                notify();
            }
            try {
                join();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
