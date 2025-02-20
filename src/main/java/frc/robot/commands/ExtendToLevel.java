package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.extendy;
import frc.robot.subsystems.flippy;

public class ExtendToLevel extends Command {
  private final extendy e_Extendy;
  private final flippy f_Flippy;
  private double targetHeight;

  /**
   * Creates a new ExtendToLevel command.
   * @param e_Extendy The elevator subsystem.
   * @param f_Flippy The wrist subsystem.
   * @param a Button A pressed status.
   * @param b Button B pressed status.
   * @param c Button C pressed status.
   * @param d Button D pressed status.
   * @param e Button E pressed status.
   */
  public ExtendToLevel(extendy e_Extendy, flippy f_Flippy, boolean a, boolean b, boolean c, boolean d, boolean e) {
    this.e_Extendy = e_Extendy;
    this.f_Flippy = f_Flippy;
    this.targetHeight = determineTargetHeight(a, b, c, d, e);
    addRequirements(e_Extendy, f_Flippy);
  }

  private double determineTargetHeight(boolean a, boolean b, boolean c, boolean d, boolean e) {
    return a ? 10 : (b ? 20 : (c ? 30 : (d ? 40 : (e ? 15 : 0))));
  }

  @Override
  public void initialize() {
    // Initialization logic if needed
  }

  @Override
  public void execute() {
    // Only extend if wrist is in a safe range
    if (f_Flippy.getWristAngle() < 270 && f_Flippy.getWristAngle() > 90) {
      double pidOutput = e_Extendy.elevatorPID.calculate(e_Extendy.extendyPosition.getDistance(), targetHeight);
      double feedForward = e_Extendy.elevatorFeedForward.calculate(0, 0);
      e_Extendy.spoolMotor.setVoltage(pidOutput + feedForward);
    } else {
      e_Extendy.spoolMotor.setVoltage(e_Extendy.elevatorFeedForward.calculate(0, 0));
    }
  }

  @Override
  public void end(boolean interrupted) {
    e_Extendy.spoolMotor.setVoltage(e_Extendy.elevatorFeedForward.calculate(0, 0));
  }

  @Override
  public boolean isFinished() {
    return Math.abs(e_Extendy.extendyPosition.getDistance() - targetHeight) < 0.005;
  }
}
