// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class extendy extends SubsystemBase {
  /* Motor declaration */
  public static TalonFX spoolMotor;
  public static DutyCycleEncoder extendyPosition; 
  public double elevatorHeight;
  public double L4ExtensionTargetDistance;
  public double L3ExtensionTargetDistance;
  public double L2ExtensionTargetDistance;
  public double L1ExtensionTargetDistance;
  public double baseExtensionTargetDistance;
  /** Creates a new extendy. */
  public extendy() {
    spoolMotor = new TalonFX(Constants.extendy.spoolMotor);
    extendyPosition = new DutyCycleEncoder(5);
    
    spoolMotor.setNeutralMode(NeutralModeValue.Brake);
  }

  @Override
  public void periodic() {
    elevatorHeight = extendyPosition.get() * 1.79 * Math.PI;
    L4ExtensionTargetDistance = 24 - elevatorHeight;//24 is a placeholder for the target height in inches
    L3ExtensionTargetDistance = 24 - elevatorHeight;
    L2ExtensionTargetDistance = 24 - elevatorHeight;
    L1ExtensionTargetDistance = 24 - elevatorHeight;
    baseExtensionTargetDistance = 24 - elevatorHeight;
    SmartDashboard.putNumber("elevator height", elevatorHeight);//# of rotations div or multiply by a factor for actual height
    // This method will be called once per scheduler run
  }
}
