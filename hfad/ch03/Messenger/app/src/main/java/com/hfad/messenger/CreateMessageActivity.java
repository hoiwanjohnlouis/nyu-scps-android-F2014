package com.hfad.messenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
    }

    public void onClickSendMessage(View view) {
        Intent targetActivity = new Intent(this,ReceiveMessageActivity.class);
        EditText clientMessage = (EditText) findViewById(R.id.clientMessageId);
        String messageToSend = clientMessage.toString();
        targetActivity.putExtra("message",messageToSend);
        startActivity(targetActivity);
    }
}
