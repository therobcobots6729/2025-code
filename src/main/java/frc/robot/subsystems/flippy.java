// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class flippy extends SubsystemBase {  
  /** Creates a new flippy. */
  public static TalonFX leftPivot;
  public static TalonFX rightPivot;
  public static DutyCycleEncoder wristEncoder;
  public static double wristAngle;
  public double backWristTargetDistance;
  public double upWristTargetDistance;
  public double downWristTargetDistance;
  public double forwardWristTargetDistance;
  public flippy() {
    leftPivot = new TalonFX(20);
    rightPivot = new TalonFX(21);
    wristEncoder = new DutyCycleEncoder(6);
    leftPivot.setNeutralMode(NeutralModeValue.Brake);
    rightPivot.setNeutralMode(NeutralModeValue.Brake);
  }

  @Override
  public void periodic() {
    wristAngle = (wristEncoder.get() / 360) - 0;
    backWristTargetDistance = 24 - wristAngle; //24 is a placeholder for the target angle in degrees
    forwardWristTargetDistance = 24 - wristAngle;
    upWristTargetDistance = 24 - wristAngle;
    downWristTargetDistance = 24 - wristAngle;
    SmartDashboard.putNumber("wrist angle", wristAngle);// 0 is a placeholder for an offset
    // This method will be called once per scheduler run
  }
}
