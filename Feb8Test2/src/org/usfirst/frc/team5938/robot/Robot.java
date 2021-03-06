package org.usfirst.frc.team5938.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.VictorSP;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot implements PIDOutput{
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	RobotDrive myRobot;
	Joystick mainStick;
	AHRS ahrs;
	VictorSP motorZero;
	VictorSP motorOne; 
	int autoLoopCounter;
	 PIDController turnController;
	 static final double kP = 0.03;
	 static final double kI = 0.00;
	 static final double kD = 0.00;
	 static final double kF = 0.00;
	 static final double kToleranceDegrees = 2.0f;
	 boolean buttonPress = true;
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
myRobot = new RobotDrive(0, 1);
	mainStick = new Joystick(0);
	
		 try {

	          /* Communicate w/navX-MXP via the MXP SPI Bus.                                     */

	          /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */

	          /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */

	          ahrs = new AHRS(SerialPort.Port.kUSB1); 

	      } catch (RuntimeException ex ) {

	          DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);

	      }
		 turnController = new PIDController(kP, kI, kD, kF, ahrs, this);
	        turnController.setInputRange(-180.0f,  180.0f);
	        turnController.setOutputRange(-1.0, 1.0);
	        turnController.setAbsoluteTolerance(kToleranceDegrees);
	        turnController.setContinuous(true);

	  }
	
	
	@Override
	public void autonomousInit() {
	
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		while (isOperatorControl() && isEnabled()){
			myRobot.arcadeDrive(mainStick);
			buttonPress = true;
			if(mainStick.getRawButton(1)){
				
				while(buttonPress){
				myRobot.arcadeDrive(.1, .5);
				myRobot.arcadeDrive(.1, -.5);
				testNum();
				
				}
				
			}
			
			if(mainStick.getRawButton(2)){
				myRobot.arcadeDrive(.1, .5);
				myRobot.arcadeDrive(.1, -.5);
			}
			
			
			
			
			 		/* wait for one motor update time period (50Hz)     */
			 
			 Timer.delay(0.020);
	          
			
	          
	         

	          /* Display 6-axis Processed Angle Data                                      */

	          SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());

	          SmartDashboard.putBoolean(  "IMU_IsCalibrating",    ahrs.isCalibrating());

	          SmartDashboard.putNumber(   "IMU_Yaw",              ahrs.getYaw());

	          SmartDashboard.putNumber(   "IMU_Pitch",            ahrs.getPitch());

	          SmartDashboard.putNumber(   "IMU_Roll",             ahrs.getRoll());

	          

	          /* Display tilt-corrected, Magnetometer-based heading (requires             */

	          /* magnetometer calibration to be useful)                                   */

	          

	          SmartDashboard.putNumber(   "IMU_CompassHeading",   ahrs.getCompassHeading());

	          

	          /* Display 9-axis Heading (requires magnetometer calibration to be useful)  */

	          SmartDashboard.putNumber(   "IMU_FusedHeading",     ahrs.getFusedHeading());



	          /* These functions are compatible w/the WPI Gyro Class, providing a simple  */

	          /* path for upgrading from the Kit-of-Parts gyro to the navx-MXP            */

	          

	          SmartDashboard.putNumber(   "IMU_TotalYaw",         ahrs.getAngle());

	          SmartDashboard.putNumber(   "IMU_YawRateDPS",       ahrs.getRate());



	          /* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */

	          

	          SmartDashboard.putNumber(   "IMU_Accel_X",          ahrs.getWorldLinearAccelX());

	          SmartDashboard.putNumber(   "IMU_Accel_Y",          ahrs.getWorldLinearAccelY());

	          SmartDashboard.putBoolean(  "IMU_IsMoving",         ahrs.isMoving());

	          SmartDashboard.putBoolean(  "IMU_IsRotating",       ahrs.isRotating());



	          /* Display estimates of velocity/displacement.  Note that these values are  */

	          /* not expected to be accurate enough for estimating robot position on a    */

	          /* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */

	          /* of these errors due to single (velocity) integration and especially      */

	          /* double (displacement) integration.                                       */

	          

	          SmartDashboard.putNumber(   "Velocity_X",           ahrs.getVelocityX());

	          SmartDashboard.putNumber(   "Velocity_Y",           ahrs.getVelocityY());

	          SmartDashboard.putNumber(   "Displacement_X",       ahrs.getDisplacementX());

	          SmartDashboard.putNumber(   "Displacement_Y",       ahrs.getDisplacementY());

	          

	          /* Display Raw Gyro/Accelerometer/Magnetometer Values                       */

	          /* NOTE:  These values are not normally necessary, but are made available   */

	          /* for advanced users.  Before using this data, please consider whether     */

	          /* the processed data (see above) will suit your needs.                     */

	          

	          SmartDashboard.putNumber(   "RawGyro_X",            ahrs.getRawGyroX());

	          SmartDashboard.putNumber(   "RawGyro_Y",            ahrs.getRawGyroY());

	          SmartDashboard.putNumber(   "RawGyro_Z",            ahrs.getRawGyroZ());

	          SmartDashboard.putNumber(   "RawAccel_X",           ahrs.getRawAccelX());

	          SmartDashboard.putNumber(   "RawAccel_Y",           ahrs.getRawAccelY());

	          SmartDashboard.putNumber(   "RawAccel_Z",           ahrs.getRawAccelZ());

	          SmartDashboard.putNumber(   "RawMag_X",             ahrs.getRawMagX());

	          SmartDashboard.putNumber(   "RawMag_Y",             ahrs.getRawMagY());

	          SmartDashboard.putNumber(   "RawMag_Z",             ahrs.getRawMagZ());

	          SmartDashboard.putNumber(   "IMU_Temp_C",           ahrs.getTempC());

	          

	          /* Omnimount Yaw Axis Information                                           */

	          /* For more info, see http://navx-mxp.kauailabs.com/installation/omnimount  */

	          AHRS.BoardYawAxis yaw_axis = ahrs.getBoardYawAxis();

	          SmartDashboard.putString(   "YawAxisDirection",     yaw_axis.up ? "Up" : "Down" );

	          SmartDashboard.putNumber(   "YawAxis",              yaw_axis.board_axis.getValue() );

	          

	          /* Sensor Board Information                                                 */

	          SmartDashboard.putString(   "FirmwareVersion",      ahrs.getFirmwareVersion());

	          

	          /* Quaternion Data                                                          */

	          /* Quaternions are fascinating, and are the most compact representation of  */

	          /* orientation data.  All of the Yaw, Pitch and Roll Values can be derived  */

	          /* from the Quaternions.  If interested in motion processing, knowledge of  */

	          /* Quaternions is highly recommended.                                       */

	          SmartDashboard.putNumber(   "QuaternionW",          ahrs.getQuaternionW());

	          SmartDashboard.putNumber(   "QuaternionX",          ahrs.getQuaternionX());

	          SmartDashboard.putNumber(   "QuaternionY",          ahrs.getQuaternionY());

	          SmartDashboard.putNumber(   "QuaternionZ",          ahrs.getQuaternionZ());

	          

	          /* Connectivity Debugging Support                                           */

	          SmartDashboard.putNumber(   "IMU_Byte_Count",       ahrs.getByteCount());

	          SmartDashboard.putNumber(   "IMU_Update_Count",     ahrs.getUpdateCount());
	          
	          
	      }
		}
	

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}


	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		
	}
	
	public double testNum(){
		double x;
		turnController.setSetpoint(150.0);
		x = 0;
		if(150 <= ahrs.getYaw() && ahrs.getYaw() <= 165){
			
			x = ahrs.getAngle();
			
			myRobot.drive(0, 0);
			buttonPress = false;
		}
		return x;
		
	}
	

}

