// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class extendy extends SubsystemBase {
  /* Motor declaration */
  public final TalonFX spoolMotor;
  public final DutyCycleEncoder extendyPosition; 
  
  /** Creates a new extendy. */
  public extendy() {
    spoolMotor = new TalonFX(0);
    extendyPosition = new DutyCycleEncoder(0);

    spoolMotor.setNeutralMode(NeutralModeValue.Brake);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
