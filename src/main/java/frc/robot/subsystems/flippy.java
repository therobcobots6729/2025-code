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
  public static TalonFX pivot;
  public static DutyCycleEncoder wristEncoder;
  public flippy() {
    pivot = new TalonFX(20);
    wristEncoder = new DutyCycleEncoder(5);
    //pivot.setNeutralMode(NeutralModeValue.Brake);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("wrist angle", wristEncoder.get());
    // This method will be called once per scheduler run
  }
}
