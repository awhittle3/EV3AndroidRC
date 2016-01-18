# EV3AndroidRC

Control your Lego Mindstorms EV3 with your Android device!

To run:

1. Get eclipse and the Lejos plugin
  http://www.lejos.org/ev3.php
  http://sourceforge.net/p/lejos/wiki/Installing%20the%20Eclipse%20plugin/

2. Create a EV3 project for the brick and put the Brick.java file into the src directory.  Run it on the EV3.

3. Get Android Studio
http://developer.android.com/tools/studio/index.html

4. Open EV3BTRC as an Android Project and build the project to your android device

5. Lauch the app and enter the mac address for the EV3. This can be found by connecting with ssh to the EV3 while it plugged in and calling "hciconfig -a" in the terminal.

6. Start the program on the EV3 and hit the Connect button in the app.

7. Control the EV3!

Tested with Lejos firmware version 0.8.1
