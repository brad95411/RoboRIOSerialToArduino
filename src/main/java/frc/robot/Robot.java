/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  private SerialPort arduino; //The serial port that we try to communicate with

  private Timer timer; //The timer to keep track of when we send our signal to the Arduino

  @Override
  public void robotInit() {
    //A "Capture Try/Catch". Tries all the possible serial port
    //connections that make sense if you're using the USB ports
    //on the RoboRIO. It keeps trying unless it never finds anything.
    try {
      arduino = new SerialPort(9600, SerialPort.Port.kUSB);
      System.out.println("Connected on kUSB!");
    } catch (Exception e) {
      System.out.println("Failed to connect on kUSB, trying kUSB 1");

      try {
        arduino = new SerialPort(9600, SerialPort.Port.kUSB1);
        System.out.println("Connected on kUSB1!");
      } catch (Exception e1) {
        System.out.println("Failed to connect on kUSB1, trying kUSB 2");

        try {
          arduino = new SerialPort(9600, SerialPort.Port.kUSB2);
          System.out.println("Connected on kUSB2!");
        } catch (Exception e2) {
          System.out.println("Failed to connect on kUSB2, all connection attempts failed!");
        }
      }
    }

    //Create a timer that will be used to keep track of when we should send
    //a signal and start it. 
    timer = new Timer();
    timer.start();
  }

  @Override
  public void robotPeriodic() {
    //If more than 5 seconds has passed
    if(timer.get() > 5) {
      //Output that we wrote to the arduino, write our "trigger byte"
      //to the arduino and reset the timer for next time
      System.out.println("Wrote to Arduino");
      arduino.write(new byte[] {0x12}, 1);
      timer.reset();
    }

    //If we've received something, read the entire buffer
    //from the arduino as a string
    if(arduino.getBytesReceived() > 0) {
      System.out.print(arduino.readString());
    }
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
