// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot {

  ///Initalize motor controllers
  WPI_TalonSRX cont_DriveL_1 = new WPI_TalonSRX(11);
  WPI_TalonSRX cont_DriveL_2 = new WPI_TalonSRX(12);
  WPI_TalonSRX cont_DriveR_1 = new WPI_TalonSRX(13);
  WPI_TalonSRX cont_DriveR_2 = new WPI_TalonSRX(14);
  WPI_VictorSPX cont_RotateZ_1 = new WPI_VictorSPX(15);
  WPI_VictorSPX cont_RotateZ_2 = new WPI_VictorSPX(16);
  WPI_VictorSPX cont_RotateY_1 = new WPI_VictorSPX(17);
  WPI_VictorSPX cont_RotateY_2 = new WPI_VictorSPX(18);

  //Initalize motor controller groups
  SpeedControllerGroup cGroup_DriveL = new SpeedControllerGroup(cont_DriveL_1, cont_DriveL_2);
  SpeedControllerGroup cGroup_DriveR = new SpeedControllerGroup(cont_DriveR_1, cont_DriveR_2);
  SpeedControllerGroup cGroup_RotateZ = new SpeedControllerGroup(cont_RotateZ_1, cont_RotateZ_2);
  SpeedControllerGroup cGroup_RotateY = new SpeedControllerGroup(cont_RotateY_1, cont_RotateY_2);

  //Initalize Drivetrain
  DifferentialDrive drive = new DifferentialDrive(cGroup_DriveL, cGroup_DriveR);

  //Initalize imput devices
  XboxController xbox = new XboxController(0);

  //Initalize buttons
  Double btn_DriveFB = xbox.getRawAxis(5);
  Double btn_DriveSpin = xbox.getRawAxis(4);
  Double btn_TurretRotateZ = xbox.getRawAxis(0);
  Double btn_TurretRotateY = xbox.getRawAxis(1);
  Boolean btn_CannonFire = xbox.getRawButton(0);
  Boolean btn_ResetLoaded = xbox.getRawButton(1);

  //Pneumatics
  Compressor compressor = new Compressor(0);

  Solenoid sol_fire1 = new Solenoid(0);
  Solenoid sol_fire2 = new Solenoid(1);

  //Sensors
  AnalogPotentiometer barrel1Sensor = new AnalogPotentiometer(0);
  AnalogPotentiometer barrel2Sensor = new AnalogPotentiometer(1);

  // Sensor Variables
  Double barrel1Distance = barrel1Sensor.get()*5;
  Double barrel2Distance = barrel2Sensor.get()*5;

  //Counting variables
  Boolean cannonPrimed;
  Boolean barrel1loaded;
  Boolean barrel2loaded;

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  @Override
  public void robotInit() {}

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {

    btn_DriveFB = xbox.getRawAxis(5);
    btn_DriveSpin = xbox.getRawAxis(4);
    btn_TurretRotateZ = xbox.getRawAxis(0);
    btn_TurretRotateZ = xbox.getRawAxis(1);
    btn_CannonFire = xbox.getRawButton(0);
    btn_ResetLoaded = xbox.getRawButton(1);

    if (barrel1Distance < 20){
      barrel1loaded = true;
    } else {
      barrel1loaded = false;
    }
    if (barrel2Distance < 20){
      barrel2loaded = true;
    } else {
      barrel2loaded = false;
    }

    if (cannonPrimed){
      if (btn_CannonFire){
        if (barrel1loaded){

          sol_fire1.set(true);
          Timer.delay(0.2);
          sol_fire1.set(false);

          barrel1loaded = false;

        } else if (barrel2loaded){

          sol_fire2.set(true);
          Timer.delay(0.2);
          sol_fire2.set(false);

        } else {
          xbox.setRumble(RumbleType.kRightRumble, 1);
          xbox.setRumble(RumbleType.kLeftRumble, 1);

          Timer.delay(0.4);

          xbox.setRumble(RumbleType.kLeftRumble, 0);
          xbox.setRumble(RumbleType.kRightRumble, 0);
        }
      }
    }




    //Drivetrain stuff
  
    drive.arcadeDrive(0.8 * btn_DriveFB, 0.8 * btn_DriveSpin);

  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
