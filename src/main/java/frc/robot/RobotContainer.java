package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
 // private final SendableChooser<Command> autoChooser;
  /* Controllers */
  private final Joystick driver = new Joystick(0);
  private final Joystick operator = new Joystick(1);
  private final SendableChooser<Command> autoChooser;

  /* Drive Controls */
  private final int translationAxis = XboxController.Axis.kLeftY.value;
  private final int strafeAxis = XboxController.Axis.kLeftX.value;
  private final int rotationAxis = XboxController.Axis.kRightX.value;

  /* Driver Buttons */
  private final JoystickButton zeroGyro =
      new JoystickButton(driver, XboxController.Button.kStart.value);
  private final JoystickButton robotCentric =
      new JoystickButton(driver, XboxController.Button.kRightStick.value);
  private final JoystickButton upExtendy = new JoystickButton(driver, XboxController.Button.kY.value);
  private final JoystickButton downExtendy = new JoystickButton(driver, XboxController.Button.kA.value);
  private final JoystickButton outtake = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
  private final JoystickButton intake = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
  private final JoystickButton out = new JoystickButton(driver, XboxController.Button.kB.value);
  private final JoystickButton in = new JoystickButton(driver, XboxController.Button.kX.value);
  public final  JoystickButton override = new JoystickButton(driver, XboxController.Button.kBack.value);
 
  /* Operator Buttons */
  private final JoystickButton L4 = new JoystickButton(operator, XboxController.Button.kY.value);
  private final JoystickButton L3 = new JoystickButton(operator, XboxController.Button.kB.value);
  private final JoystickButton L2 = new JoystickButton(operator, XboxController.Button.kX.value);
  private final JoystickButton L1 = new JoystickButton(operator, XboxController.Button.kA.value);
  private final JoystickButton Shelf = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
  private final JoystickButton Barge = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);

  /* Subsystems */
  private final Swerve s_Swerve = new Swerve();
  private final extendy e_Extendy = new extendy();
  private final flippy f_Flippy = new flippy();
  private final sucky s_Sucky = new sucky();
  private final limelight l_Limelight = new limelight(); // do not touch, is required for limelight to work even if it says not used
   private final ScoringLog s_Log = new ScoringLog(()->override.getAsBoolean());
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    s_Swerve.setDefaultCommand(
        new TeleopSwerve(
            s_Swerve,
            () -> driver.getRawAxis(translationAxis),
            () -> driver.getRawAxis(strafeAxis) ,
            () -> driver.getRawAxis(rotationAxis) ,
            () -> robotCentric.getAsBoolean()));
    
   e_Extendy.setDefaultCommand(
        new runExtendy(
            e_Extendy,
            () -> upExtendy.getAsBoolean(),
            () -> downExtendy.getAsBoolean()));
    

  
    f_Flippy.setDefaultCommand(
      new runFlippy(
        f_Flippy, 
        () -> out.getAsBoolean(), 
        () -> in.getAsBoolean())
    );
           
    //Auto Commands
    NamedCommands.registerCommand("Coral Station Drive", new AutoStationSwerve(s_Swerve));
    NamedCommands.registerCommand("Coral Reef Drive", new AutoReefSwerve(s_Swerve));
    NamedCommands.registerCommand("L4 Extension", new L4Extension(e_Extendy));
    NamedCommands.registerCommand("Retract Elevator", new processorExtension(e_Extendy));
    NamedCommands.registerCommand("Intake", new Intake(s_Sucky));
    NamedCommands.registerCommand("Score", new outtake(s_Sucky));
    NamedCommands.registerCommand("Flip Forward", new flipDown(f_Flippy));
    NamedCommands.registerCommand("Flip Back", new flipBack(f_Flippy, e_Extendy));
   
                
    // Configure the button bindings
    configureButtonBindings();
    
    // Build an auto chooser. This will use Commands.none() as the default option.
    autoChooser = AutoBuilder.buildAutoChooser();

    // Another option that allows you to specify the default auto by its name
    // autoChooser = AutoBuilder.buildAutoChooser("My Default Auto");

    SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    /* Driver Buttons */
    zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroHeading()));
    intake.whileTrue(
      new Intake(s_Sucky)
    );
    outtake.whileTrue(
      new outtake(s_Sucky)
    );

 
   
    /* Operator Buttons */
    L1.onTrue(
      new flipDown(f_Flippy)
        .alongWith( new L1Extension(e_Extendy)));


    L2.onTrue(
      new flipDown(f_Flippy)
        .alongWith( new L2Extension(e_Extendy)));

    L3.onTrue(
      new flipDown(f_Flippy)
        .alongWith( new L3Extension(e_Extendy)));

    L4.onTrue(
       new flipDown(f_Flippy)
        .alongWith( new L4Extension(e_Extendy)));
        
    Barge.onTrue(
      new flipDown(f_Flippy)
          .alongWith(new L4Extension(e_Extendy))
            .andThen(new flipUp(f_Flippy))
    );
    Shelf.onTrue(
      new processorExtension(e_Extendy)
        .alongWith(new flipBack(f_Flippy))
    );
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autoChooser.getSelected();
  }
}
