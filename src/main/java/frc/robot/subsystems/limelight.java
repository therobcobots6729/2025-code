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
  public static double x;
  public static double y;
  public static double reefd;
  public static double processord;
  public static double ID;
  public limelight() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry tid = table.getEntry("tid");
    double targetOffsetAngle_Vertical = ty.getDouble(0.0);
    double limelightMountAngleDegrees = 0;// change
    double limelightLensHeightInches = 18; // change 
    
    
    double reefHeightInches = 12.125;
    double angletoReefDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
    double angletoReefRadians= angletoReefDegrees * (3.14159/180);
    double distanceFromLimelighttoReefInches = (reefHeightInches-limelightLensHeightInches)/Math.tan(angletoReefRadians);
    reefd = distanceFromLimelighttoReefInches;
    
    
    double processorHeightInches = 51.125;
    double angletoprocessorDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
    double angletoprocessorRadians= angletoprocessorDegrees * (3.14159/180);
    double distanceFromLimelighttoprocessorInches = (processorHeightInches-limelightLensHeightInches)/Math.tan(angletoprocessorRadians);
    processord = distanceFromLimelighttoprocessorInches;

//read values periodically
    x = tx.getDouble(0.0);
    y = ty.getDouble(0.0);
    ID = tid.getDouble(0.0);
  }
}
