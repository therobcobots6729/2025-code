package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Capitalized class name (follows Java conventions)
  
 

public class limelight extends SubsystemBase {
  private Swerve s_Swerve;
  private final NetworkTable table1 = NetworkTableInstance.getDefault().getTable("limelight-fr"); // Forward Limelight
  private final NetworkTableEntry tx1 = table1.getEntry("tx");
  private final NetworkTableEntry ty1 = table1.getEntry("ty");
  public final NetworkTableEntry tid1 = table1.getEntry("tid");
  private final NetworkTableEntry botposeEntry = table1.getEntry("botpose");
  
  private final NetworkTable table2 = NetworkTableInstance.getDefault().getTable("limelight-fl"); // Backward Limelight
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
public double getTagID2() {
    
  return tid2.getDouble(0.0);
}
  public double getTargetYaw(){
    if(getTagID()==7.0||getTagID2() == 7.0 ||  getTagID() == 18.0 || getTagID2() == 18){
      return 0;
    }
    else if(getTagID() == 17.0 || getTagID2() ==17.0 ||  getTagID() == 8.0 || getTagID2() == 8.0){
      return 60;
    }
    else if(getTagID() == 11.0 || getTagID2() ==11.0 ||  getTagID() == 20.0|| getTagID2() ==20.0){
      return -120;
    }
    else if(getTagID() == 6.0 || getTagID2() ==6.0 ||  getTagID() == 19.0|| getTagID2() ==19.0){
      return -60;
    }
    else if(getTagID() == 9.0 || getTagID2() ==9.0 ||  getTagID() == 22.0 || getTagID2() ==22.0){
      return 120;
    }
    else if(getTagID() == 10.0|| getTagID2() ==10.0 ||  getTagID() == 21.0|| getTagID2() ==21.0){
      if (s_Swerve.getPose().getRotation().getDegrees() >=0){
        return 180;}
        else {return -180;}
    }
    
    else if (getTagID() == 2.0 || getTagID2() ==2.0 ||  getTagID() == 12.0 || getTagID2() ==12.0){
      return 55;
    }
    else if (getTagID() == 1.0 || getTagID2() ==1.0 ||  getTagID() == 13.0 || getTagID2() ==13.0 ){
     return  -55;
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
    SmartDashboard.putNumber("right Reef X", x1);
    SmartDashboard.putNumber("left reef X", x2);
    SmartDashboard.putNumber("right Reef Y", y1);
    SmartDashboard.putNumber("left reef Y", y2);
    
      
      boolean isConnected = NetworkTableInstance.getDefault().isConnected();
SmartDashboard.putBoolean("NetworkTables Connected", isConnected);
  }}
