package cc.mightu.sms_forward;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Telephony;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSION_SEND_MSG_REQUEST_CODE = 88;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(cc.mightu.sms_forward.R.layout.activity_main);

        String url = getSharedPreferences("data", Context.MODE_PRIVATE).getString("url", "");
        Log.d("log", "url: " + url);
        EditText editText = (EditText) findViewById(cc.mightu.sms_forward.R.id.edit_phone_number);
        editText.setText(url, TextView.BufferType.EDITABLE);
        setupPermissions();

        Intent startServiceIntent = new Intent(this, ForwardSMSService.class);
        startServiceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        startService(startServiceIntent);
    }

    private void setupPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String[] permissionsWeNeed = new String[]{Manifest.permission.SEND_SMS
                        , Manifest.permission.READ_SMS
                        , Manifest.permission.RECEIVE_SMS};
                requestPermissions(permissionsWeNeed, MY_PERMISSION_SEND_MSG_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_SEND_MSG_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Permission for audio not granted. Visualizer can't run." + String.valueOf(grantResults[0]), Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void sendSMS(View v) {
        EditText editText = findViewById(R.id.edit_phone_number);
        String number = editText.getText().toString();

        SharedPreferences.Editor editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        editor.putString("url", number);
        editor.apply();

        String message = "This is a test message to " + number;
        Log.i("sms", "message send:" + message);
    }
}


