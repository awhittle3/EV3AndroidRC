package ca.ualberta.awhittle.ev3btrc;

/**
 * From http://stackoverflow.com/questions/4969053/bluetooth-connection-between-android-and-lego-mindstorm-nxt
 * answered Feb 14 '11 at 23:05 by joen
 *
 * Modified to work with 1 EV3 brick and take a user-entered mac address
 */
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;


import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import android.util.Log;
import android.widget.Toast;

public class BT_Comm {
    private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    //Target NXTs for communication
    //final String nxt2 = "00:16:53:04:52:3A";
    //final String nxt1 = "00:16:53:07:AA:F6";

    BluetoothAdapter localAdapter;
    BluetoothSocket socket_ev3_1, socket_nxt2;
    boolean success=false;
    private boolean btPermission=false;
    private boolean alertReplied=false;

    public void reply(){this.alertReplied = true;}
    public void setBtPermission(boolean btPermission) {
        this.btPermission = btPermission;
    }

    public boolean initBT(){
        localAdapter=BluetoothAdapter.getDefaultAdapter();
        return localAdapter.isEnabled();
    }

    //Enables Bluetooth if not enabled
    // Modified to ask permission and show a toast
    /*public boolean enableBT(AlertDialog alert, Toast toast){
        localAdapter=BluetoothAdapter.getDefaultAdapter();
        //If Bluetooth not enable then do it
        if(localAdapter.isEnabled()==false){
            alert.show();
            while(!alertReplied){}
            if(btPermission) {
                localAdapter.enable();
            } else {
                return false;
            }

            while(!(localAdapter.isEnabled())){

            }

            toast.show();
            return true;
        } else {
            return true;
        }

    }*/

    //connect to both NXTs
    public  boolean connectToEV3(String macAdd){



        //get the BluetoothDevice of the EV3
        //BluetoothDevice nxt_2 = localAdapter.getRemoteDevice(nxt2);
        BluetoothDevice ev3_1 = localAdapter.getRemoteDevice(macAdd);
        //try to connect to the nxt
        try {
            //socket_nxt2 = nxt_2.createRfcommSocketToServiceRecord(UUID
            //        .fromString("00001101-0000-1000-8000-00805F9B34FB"));


            socket_ev3_1 = ev3_1.createRfcommSocketToServiceRecord(UUID
                    .fromString(SPP_UUID));


            //socket_nxt2.connect();

        /*    Method m = null;
            try {
                m = ev3_1.getClass().getMethod("createRfcommSocket", new Class[] { int.class });
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                socket_ev3_1 = (BluetoothSocket) m.invoke(ev3_1, 1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }*/

            socket_ev3_1.connect();


            success = true;



        } catch (IOException e) {
            Log.d("Bluetooth","Err: Device not found or cannot connect " + macAdd);
            success=false;


        }
        return success;

    }


    public void writeMessage(byte msg) throws InterruptedException{
        BluetoothSocket connSock;

        //Swith nxt socket
        //if(nxt.equals("nxt2")){
        //    connSock=socket_nxt2;
        //}else
        //if(nxt.equals("nxt1")){
            connSock= socket_ev3_1;
        //}else{
        //    connSock=null;
        //}

        if(connSock!=null){
            try {

                OutputStreamWriter out=new OutputStreamWriter(connSock.getOutputStream());
                out.write(msg);
                out.flush();

                Thread.sleep(1000);


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else{
            //Error
        }
    }

    // Note: Not needed for the current program
    public int readMessage(String nxt){
        BluetoothSocket connSock;
        int n;
        //Swith nxt socket
        if(nxt.equals("nxt2")){
            connSock=socket_nxt2;
        }else if(nxt.equals("nxt1")){
            connSock= socket_ev3_1;
        }else{
            connSock=null;
        }

        if(connSock!=null){
            try {

                InputStreamReader in=new InputStreamReader(connSock.getInputStream());
                n=in.read();

                return n;


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return -1;
            }
        }else{
            //Error
            return -1;
        }

    }

}