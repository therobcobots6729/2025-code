package frc.robot.commands;

import com.ctre.phoenix6.controls.VoltageOut;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.extendy;
import frc.robot.subsystems.flippy;

public class ExtendToLevel extends Command {
  private final extendy e_Extendy;
  private final flippy f_Flippy;
 

  /**
   * Creates a new ExtendToLevel command.
   * @param e_Extendy The elevator subsystem.
   * @param f_Flippy The wrist subsystem.
   */
  public ExtendToLevel(extendy e_Extendy, flippy f_Flippy) {
    this.e_Extendy = e_Extendy;
    this.f_Flippy = f_Flippy;
    addRequirements(e_Extendy, f_Flippy);
  }

  @Override
  public void execute() {
    // Only extend if wrist is in a safe range
    if (f_Flippy.getWristAngle()) {
      double pidOutput = e_Extendy.elevatorPID.calculate(e_Extendy.getElevatorHeight(), e_Extendy.determineTargetHeight());
      double feedForward = e_Extendy.elevatorFeedForward.calculate(0, 0);
      e_Extendy.spoolMotor.setControl(new VoltageOut(pidOutput + feedForward));
    } else {
      e_Extendy.spoolMotor.setControl(new VoltageOut(e_Extendy.elevatorFeedForward.calculate(0, 0)));
    }
  }

  @Override
  public void end(boolean interrupted) {
    e_Extendy.spoolMotor.setControl(new VoltageOut(e_Extendy.elevatorFeedForward.calculate(0, 0)));
  }

  @Override
  public boolean isFinished() {
    return Math.abs(e_Extendy.getElevatorHeight() - e_Extendy.determineTargetHeight()) < 0.005;
  }
}
