package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Capitalized class name (follows Java conventions)
  
 

public class limelight extends SubsystemBase {
  private Swerve s_Swerve;
  private final NetworkTable table1 = NetworkTableInstance.getDefault().getTable("limelight-front"); // Forward Limelight
  private final NetworkTableEntry tx1 = table1.getEntry("tx");
  private final NetworkTableEntry ty1 = table1.getEntry("ty");
  public final NetworkTableEntry tid1 = table1.getEntry("tid");
  private final NetworkTableEntry botposeEntry = table1.getEntry("botpose");
  
  private final NetworkTable table2 = NetworkTableInstance.getDefault().getTable("limelight2"); // Backward Limelight
  private final NetworkTableEntry tx2 = table2.getEntry("tx");
  private final NetworkTableEntry ty2 = table2.getEntry("ty");
  private final NetworkTableEntry tid2 = table2.getEntry("tid");

  private final double limelight1MountAngleDegrees = 0; 
  private final double limelight1LensHeightInches = 18; 
  private final double limelight2MountAngleDegrees = 0; 
  private final double limelight2LensHeightInches = 24; 

  public double reefd, stationd, processord;
  public double x1, y1, ID1, x2, y2, ID2;

  /** Constructor */
  public limelight(Swerve s_Swerve) {
    this.s_Swerve=  s_Swerve;
  }

  /** Gets the detected AprilTag ID from the forward Limelight */
  public double getTagID() {
      return tid1.getDouble(0.0);
  }
  public double getTargetYaw(){
    if(getTagID() == 7.0 ||  getTagID() == 18.0){
      if (s_Swerve.gyro.getYaw().getValueAsDouble() <360 && s_Swerve.gyro.getYaw().getValueAsDouble() >=180){
      return 2* Math.PI;}
      else {return 0;}
    }
    else if(getTagID() == 17.0 ||  getTagID() == 8.0){
      return Math.toRadians(300);
    }
    else if(getTagID() == 11.0 ||  getTagID() == 20.0){
      return Math.toRadians(240);
    }
    else if(getTagID() == 6.0 ||  getTagID() == 19.0){
      return Math.toRadians(60);
    }
    else if(getTagID() == 9.0 ||  getTagID() == 22.0){
      return Math.toRadians(120);
    }
    else if(getTagID() == 10.0 ||  getTagID() == 21.0){
      return Math.toRadians(180);
    }
    else if (getTagID() == 2.0 ||  getTagID() == 12.0){
      return Math.toRadians(55);
    }
    else if (getTagID() == 1.0 ||  getTagID() == 13.0){
      return Math.toRadians(305);
    }
    else{
      return 0;
    }
  }

  /** This method runs periodically */

  @Override
public void periodic() {
    // Fetch new botpose values every cycle
    double[] botpose = botposeEntry.getDoubleArray(new double[6]);
    double x = botpose[0];  // X position
    double y = botpose[1];  // Y position
    double z = botpose[2];  // Z position (height)
    double pitch = botpose[3];  // Pitch angle
    double yaw = Math.toDegrees(botpose[4]);  // Yaw angle
    double roll = botpose[5];  // Roll angle
    double targetOffsetAngle_Vertical1 = ty1.getDouble(0.0);
    double targetOffsetAngle_Vertical2 = ty2.getDouble(0.0);

    double reefHeightInches = 12.125;
    double angleToReefDegrees = limelight1MountAngleDegrees + targetOffsetAngle_Vertical1;
    double angleToReefRadians = Math.toRadians(angleToReefDegrees);
    reefd = (Math.abs(angleToReefRadians) > 0.01) ? 
             (reefHeightInches - limelight1LensHeightInches) / Math.tan(angleToReefRadians) : 
             Double.POSITIVE_INFINITY;

    double stationHeightInches = 58.5;
    double angleToStationDegrees = limelight2MountAngleDegrees + targetOffsetAngle_Vertical2;
    double angleToStationRadians = Math.toRadians(angleToStationDegrees);
    stationd = (Math.abs(angleToStationRadians) > 0.01) ? 
                (stationHeightInches - limelight2LensHeightInches) / Math.tan(angleToStationRadians) : 
                Double.POSITIVE_INFINITY;

    double processorHeightInches = 51.125;
    double angleToProcessorDegrees = limelight1MountAngleDegrees + targetOffsetAngle_Vertical1;
    double angleToProcessorRadians = Math.toRadians(angleToProcessorDegrees);
    processord = (Math.abs(angleToProcessorRadians) > 0.01) ? 
                  (processorHeightInches - limelight1LensHeightInches) / Math.tan(angleToProcessorRadians) : 
                  Double.POSITIVE_INFINITY;

    x1 = tx1.getDouble(0.0);
    y1 = ty1.getDouble(0.0);
    ID1 = tid1.getDouble(0.0);
    x2 = tx2.getDouble(0.0);
    y2 = ty2.getDouble(0.0);
    ID2 = tid2.getDouble(0.0);

    SmartDashboard.putNumber("Reef Distance", reefd);
    SmartDashboard.putNumber("Station Distance", stationd);
    SmartDashboard.putNumber("Processor Distance", processord);
    SmartDashboard.putNumber("Tag ID 1", ID1);
    SmartDashboard.putNumber("Tag ID 2", ID2);
    SmartDashboard.putNumber("Reef X", x1);
    SmartDashboard.putNumber("Station X", x1);
    
      SmartDashboard.putNumber("Botpose X", x);
      SmartDashboard.putNumber("Botpose Y", y);
      SmartDashboard.putNumber("Botpose Z", z);
      SmartDashboard.putNumber("Botpose Pitch", pitch);
      SmartDashboard.putNumber("Botpose Yaw", yaw);
      SmartDashboard.putNumber("Botpose Roll", roll);
  }}
