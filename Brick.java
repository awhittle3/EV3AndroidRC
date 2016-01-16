import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.hardware.Bluetooth;
import lejos.hardware.motor.NXTMotor;
import lejos.hardware.port.MotorPort;
import lejos.remote.nxt.BTConnection;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;
import lejos.robotics.EncoderMotor;

/*
 * Modified code from Tawat Atigarbodee, September 21, 2009
 * http://lejos.sourceforge.net/forum/viewtopic.php?t=1723
 */


public class Brick {
		private static DataOutputStream dataOut; 
	  private static DataInputStream dataIn;
	  private static BTConnection BTLink;
	  private static int transmitReceived=0;
	  private static int power = 50;
	private static boolean app_alive;
	
	private static EncoderMotor motorLeft;
	private static EncoderMotor motorRight;
	  
	  public static void main(String[] args){
		  
		  connect();
		  app_alive = true;
			motorLeft = new NXTMotor (MotorPort.A);
			motorRight = new NXTMotor (MotorPort.B);
			motorLeft.setPower(power);
	    	motorRight.setPower(power);
	    	motorLeft.stop();
	    	motorRight.stop();
			while(app_alive){
				  try {
				       transmitReceived = (int) dataIn.readByte();
				       System.out.println("Received " + transmitReceived);
				       switch(transmitReceived){
				       // Forwards
				       case 1:
				    	   motorLeft.forward();
				    	   motorRight.forward();
				    	   break;
				    	   
				       // Backwards
				       case 2: 
				    	   motorLeft.backward();
				    	   motorRight.backward();
				    	   break;
				    	   
				       // Turn Left
				       case 3:
				    	   motorLeft.backward();
				    	   motorRight.forward();
				    	   break;
				    	   
				       // Turn Right
				       case 4:
				    	   motorLeft.forward();
				    	   motorRight.backward();
				    	   break;
				    	   
				       // Speed up
				       case 5:
				    	   if(power < 100){
				    		   power += 10;
				    	   }
				    	   motorLeft.setPower(power);
				    	   motorRight.setPower(power);
				    	   break;
				    	   
				       // Slow down
				       case 6:
				    	   if(power > 10){
				    		   power -= 10;
				    	   }
				    	   motorLeft.setPower(power);
				    	   motorRight.setPower(power);
				    	   break;
				       // Terminate command
				       case 7:
				    	   app_alive = false;
				    	   break;
				    	   
				    	// Directional button released
				       case 10:
				    	   motorLeft.stop();
				    	   motorRight.stop();
				    	   break;
				       }
				       
				       
				       
				   }
				    
				    catch (IOException ioe) {
				       System.out.println("IO Exception readInt");
				   }
			}
			
			motorLeft.flt();
			motorRight.flt();
			
	  }
	  
	 public static void connect()
	 {  
	    System.out.println("Listening");
	    BTConnector ncc = (BTConnector) Bluetooth.getNXTCommConnector();
	    BTLink = (BTConnection) ncc.waitForConnection(30000, NXTConnection.RAW);
	    dataOut = BTLink.openDataOutputStream();
	    dataIn = BTLink.openDataInputStream();
	 }
}
