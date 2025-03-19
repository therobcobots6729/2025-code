// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.extendy;
import frc.robot.subsystems.sucky;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class outtake extends Command {
  /** Creates a new outtake. */
sucky s_Sucky;
extendy e_Extendy;
private double speed;
private boolean lastState = false;
  public outtake(sucky s_Sucky, extendy e_Extendy) {
    addRequirements(s_Sucky);
    this.s_Sucky= s_Sucky;
    this.e_Extendy = e_Extendy;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (e_Extendy.elevatorHeight < 5){
      speed = .1;
    }
    else{
      speed = .75;
    }
    sucky.leftMotor.set(speed);
    sucky.rightMotor.set(speed);
    //sucky.intakeMotor.set(-.5);
   
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    sucky.leftMotor.set(0);
    sucky.rightMotor.set(0);
    //sucky.intakeMotor.set(-.5);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean currentState = s_Sucky.finish().getAsBoolean();

    // Check if it transitioned from false → true
    boolean risingEdge = !lastState && currentState;

    // Update lastState for next cycle
    lastState = currentState;

    return risingEdge; 
  }
}
