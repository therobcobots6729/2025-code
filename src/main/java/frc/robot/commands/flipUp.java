// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.flippy;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class flipUp extends Command {
  private PIDController pid;
  private ArmFeedforward feedforward;
  /** Creates a new flipUp. */
  public flipUp() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    feedforward = new ArmFeedforward(0, .43, .45, .01);
    pid = new PIDController(5, 0, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    flippy.leftPivot.setVoltage(pid.calculate(flippy.wristEncoder.get(), 135) + feedforward.calculate(flippy.wristAngle, 0));
    flippy.rightPivot.setVoltage(pid.calculate(flippy.wristEncoder.get(), 135) + feedforward.calculate(flippy.wristAngle, 0));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Math.abs(pid.calculate(flippy.wristEncoder.get(), 135)) <.005){
      return true;
    }
    else{
      return false;
    }
  }
}
