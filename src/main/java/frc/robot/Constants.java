/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

	//CONTROLS
	public static final int DRIVETRAIN_OVERRIDE_BUTTON = 5;

	//PID VALUES
	public static final double PROPORTIONAL_TWEAK = 0.007; // 0.0065 0.0047
	public static final double INTEGRAL_TWEAK = 0.0001;
	public static final double DERIVATIVE_TWEAK = 0.0005;
	
	

}
