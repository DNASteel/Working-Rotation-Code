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
public class Robot extends IterativeRobot{
	
	
	SendableChooser<String> chooser = new SendableChooser<>();
	RobotDrive myRobot;
	Joystick mainStick;
	AHRS ahrs;
	
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

	         

	          

	          SmartDashboard.putBoolean(  "IMU_IsMoving",         ahrs.isMoving());

	          SmartDashboard.putBoolean(  "IMU_IsRotating",       ahrs.isRotating());


	          

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

	          
	      }
		}
	

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}


	@Override
	
	
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

