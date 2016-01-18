package ca.ualberta.awhittle.ev3btrc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RCActivity extends AppCompatActivity {

    BT_Comm btComm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rc);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        btComm = new BT_Comm();

        // To notify user for permission to enable bt, if needed
        AlertDialog.Builder builder = new AlertDialog.Builder(RCActivity.this);

        builder.setMessage(R.string.bt_permission_request);
        builder.setPositiveButton(R.string.allow, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                btComm.setBtPermission(true);
                btComm.reply();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                btComm.setBtPermission(false);
                btComm.reply();
            }
        });

        // Create the AlertDialog
        AlertDialog btPermissionAlert = builder.create();

        Context context = getApplicationContext();
        //CharSequence text1 = getString(R.string.bt_enabled);
        CharSequence text1 = getString(R.string.bt_disabled);
        CharSequence text2 = getString(R.string.bt_failed);


        Toast btDisabledToast = Toast.makeText(context, text1, Toast.LENGTH_LONG);
        Toast btFailedToast = Toast.makeText(context, text2, Toast.LENGTH_LONG);

        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.MyPREFERENCES), Context.MODE_PRIVATE);

        //if(btComm.enableBT(btPermissionAlert, btEnabledToast)){
        //}
        if(!btComm.initBT()){
            // User did not enable Bluetooth
            btDisabledToast.show();
            Intent intent = new Intent(RCActivity.this, ConnectActivity.class);
            startActivity(intent);
        }

        if(!btComm.connectToEV3(sharedpreferences.getString(getString(R.string.EV3KEY), ""))){
            //Cannot connect to given mac address, return to connect activity
            btFailedToast.show();
            Intent intent = new Intent(RCActivity.this, ConnectActivity.class);
            startActivity(intent);
        }

        final Button buttonF = (Button) findViewById(R.id.buttonF);

        buttonF.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    try {
                        btComm.writeMessage((byte) 1);
                        return true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return false;
                    }

                case MotionEvent.ACTION_UP:
                    try {
                        btComm.writeMessage((byte) 10);
                        return true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return false;
                    }
                }


                return false;
            }
        });

        final Button buttonB = (Button) findViewById(R.id.buttonB);

        buttonB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        try {
                            btComm.writeMessage((byte) 2);
                            return true;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return false;
                        }

                    case MotionEvent.ACTION_UP:
                        try {
                            btComm.writeMessage((byte) 10);
                            return true;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return false;
                        }
                }


                return false;
            }
        });

        final Button buttonL = (Button) findViewById(R.id.buttonL);

        buttonL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        try {
                            btComm.writeMessage((byte) 3);
                            return true;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return false;
                        }

                    case MotionEvent.ACTION_UP:
                        try {
                            btComm.writeMessage((byte) 10);
                            return true;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return false;
                        }
                }


                return false;
            }
        });

        final Button buttonR = (Button) findViewById(R.id.buttonR);

        buttonR.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        try {
                            btComm.writeMessage((byte) 4);
                            return true;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return false;
                        }

                    case MotionEvent.ACTION_UP:
                        try {
                            btComm.writeMessage((byte) 10);
                            return true;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return false;
                        }
                }


                return false;
            }
        });

        final Button buttonFast = (Button) findViewById(R.id.buttonFast);

        buttonFast.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        try {
                            btComm.writeMessage((byte) 5);
                            return true;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return false;
                        }

                }


                return false;
            }
        });

        final Button buttonSlow = (Button) findViewById(R.id.buttonSlow);

        buttonSlow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        try {
                            btComm.writeMessage((byte) 6);
                            return true;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return false;
                        }

                }


                return false;
            }
        });

        final Button buttonExit = (Button) findViewById(R.id.buttonExit);

        buttonExit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        try {
                            btComm.writeMessage((byte) 7);
                            Intent intent = new Intent(RCActivity.this, ConnectActivity.class);
                            startActivity(intent);
                            return true;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return false;
                        }
                }
                return false;
            }
        });

    }

}
