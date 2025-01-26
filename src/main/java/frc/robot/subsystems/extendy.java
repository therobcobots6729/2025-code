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
  /** Creates a new extendy. */
  public extendy() {
    spoolMotor = new TalonFX(Constants.extendy.spoolMotor);
    extendyPosition = new DutyCycleEncoder(5);
    
    spoolMotor.setNeutralMode(NeutralModeValue.Brake);
  }

  @Override
  public void periodic() {
    elevatorHeight = extendyPosition.get() * 1.79 * Math.PI;
    SmartDashboard.putNumber("elevator height", elevatorHeight);//# of rotations div or multiply by a factor for actual height
    // This method will be called once per scheduler run
  }
}
