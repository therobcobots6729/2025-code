// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class limelight extends SubsystemBase {
  /** Creates a new limelight. */
  public static double x1;
  public static double y1;
  public static double reefd;
  public static double processord;
  public static double ID1;
  public static double stationd;
  public static double x2;
  public static double y2;
  public static double ID2;
  public limelight() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    NetworkTable table1 = NetworkTableInstance.getDefault().getTable("limelight1");//forward limelight
    NetworkTableEntry tx1 = table1.getEntry("tx");
    NetworkTableEntry ty1 = table1.getEntry("ty");
    NetworkTableEntry tid1 = table1.getEntry("tid");
    NetworkTable table2 = NetworkTableInstance.getDefault().getTable("limelight2");//backward limelight
    NetworkTableEntry tx2 = table2.getEntry("tx");
    NetworkTableEntry ty2 = table2.getEntry("ty");
    NetworkTableEntry tid2 = table2.getEntry("tid");
    double targetOffsetAngle_Vertical1 = ty1.getDouble(0.0);
    double targetOffsetAngle_Vertical2 = ty2.getDouble(0.0);
    double limelight1MountAngleDegrees = 0;// change
    double limelight1LensHeightInches = 18; // change 
    double limelight2MountAngleDegrees = 0;// change
    double limelight2LensHeightInches = 0;// change
    
    
    double reefHeightInches = 12.125;
    double angletoReefDegrees = limelight1MountAngleDegrees + targetOffsetAngle_Vertical1;
    double angletoReefRadians= angletoReefDegrees * (3.14159/180);
    double distanceFromLimelighttoReefInches = (reefHeightInches-limelight1LensHeightInches)/Math.tan(angletoReefRadians);
    reefd = distanceFromLimelighttoReefInches;

    double stationHeightInches = 58.5;
    double angleToStationDegrees = limelight2MountAngleDegrees + targetOffsetAngle_Vertical2;
    double angleToStationRadians = angleToStationDegrees *(3.14159/180);
    double distanceFromLimelighttoStationInches = (stationHeightInches-limelight2LensHeightInches)/Math.tan(angleToStationRadians);
    stationd = distanceFromLimelighttoStationInches;
    
    
    double processorHeightInches = 51.125;
    double angletoprocessorDegrees = limelight1MountAngleDegrees + targetOffsetAngle_Vertical1;
    double angletoprocessorRadians= angletoprocessorDegrees * (3.14159/180);
    double distanceFromLimelighttoprocessorInches = (processorHeightInches-limelight1LensHeightInches)/Math.tan(angletoprocessorRadians);
    processord = distanceFromLimelighttoprocessorInches;

//read values periodically
    x1 = tx1.getDouble(0.0);
    y1 = ty1.getDouble(0.0);
    ID1 = tid1.getDouble(0.0);
    x2 = tx2.getDouble(0.0);
    y2 = ty2.getDouble(0.0);
    ID2 = tid2.getDouble(0.0);
  }
}
