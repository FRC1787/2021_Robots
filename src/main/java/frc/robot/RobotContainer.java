/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Joystick; 
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Intake;

import frc.robot.commands.RobotDrive;
import frc.robot.commands.Shoot;
import frc.robot.commands.TurnToTarget;
import frc.robot.commands.Chase;
import frc.robot.commands.Follow;
import frc.robot.commands.ClimbControl;
import frc.robot.commands.IntakeBawls;
import frc.robot.commands.IntakeExtend;
import frc.robot.commands.SetHood;
import frc.robot.commands.OneEighty;

import frc.robot.commands.PointBlank;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  
  /* Subsystems */
  public final static DriveTrain driveTrain = new DriveTrain();
  public final static Gyro gyro = new Gyro();
  public final static Vision vision = new Vision();
  public final static Shooter shooter = new Shooter();
  public final static Climb climb = new Climb();
  public final static Intake intake = new Intake();

  

  /* Commands */
  public final static RobotDrive robotDrive = new RobotDrive(driveTrain);
  public final static TurnToTarget turnToTarget = new TurnToTarget(driveTrain, vision);
  public final static Shoot shoot = new Shoot(shooter, intake, false);
  public final static Shoot autoShoot = new Shoot(shooter, intake, true);
  private final static PointBlank pointBlank = new PointBlank(driveTrain, shooter);
  private final static ParallelRaceGroup targetingShoot = new ParallelRaceGroup(new Shoot(shooter, intake, true), turnToTarget);
  

  

  /* OI */

  //Right Stick
  public static Joystick rightStick = new Joystick(0);
  public Button rightTrigger = new JoystickButton(rightStick, 1);
  public Button rightThumb = new JoystickButton(rightStick, 2); //back top button
  public Button intakeToggle = new JoystickButton(rightStick, 3); //left thumb button
  public Button reverseIntake = new JoystickButton(rightStick, 4);
  public Button driveTrainOverride = new JoystickButton(rightStick, 5);
  public Button targetButton = new JoystickButton(rightStick, 7);

  //Left Stick
  public static Joystick leftStick = new Joystick(1);
  public Button leftTrigger = new JoystickButton(leftStick, 1);
  public Button leftThumb = new JoystickButton(leftStick, 2);
  public Button hoodManualButton = new JoystickButton(leftStick, 6);
  public Button hoodForward = new JoystickButton(leftStick, 7);
  public Button hoodBack = new JoystickButton(leftStick, 8);
  public Button hoodCalibrateButton = new JoystickButton(leftStick, 11);


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    configureButtonBindings();
    driveTrain.setDefaultCommand(robotDrive);
    //shooter.setDefaultCommand(setHood);
  }


  private void configureButtonBindings() {

    /* INTAKE */
    rightTrigger.whileHeld(new IntakeBawls(intake, 1, 1)); //Intake Balls
    reverseIntake.whileHeld(new IntakeBawls(intake, -1, -1)); //Eject Balls
    intakeToggle.whenPressed(new IntakeExtend(intake)); //Toggle Intake Position

    /* SHOOTER */
    rightThumb.whileHeld(shoot); //Manual Shoot
    leftThumb.whileHeld(targetingShoot); //Targeting Shoot
    leftThumb.whenReleased(new SetHood(shooter, "Calibrate"));

    /* HOOD */
    hoodBack.whenPressed(new SetHood(shooter, "Back")); //Set to preprogrammed "Back" position
    hoodForward.whenPressed(new SetHood(shooter, "Forward")); //Set to preprogrammed "Forward" position
    hoodManualButton.whileHeld(new SetHood(shooter, "Manual")); //Manually set hood position
    hoodCalibrateButton.whenPressed(new SetHood(shooter, "Calibrate")); // Set hood to back limit and re-zero it

    /* VISION */
    targetButton.whenPressed(new TurnToTarget(driveTrain, vision)); //Turn to face vision target

    /* LIFTER */
    leftTrigger.whenPressed(new ClimbControl(climb)); //Change lifter control and position states

    /* TESTING */ 
    //targetButton.whenPressed(new OneEighty(driveTrain, gyro)); //Turn robot in place 180 degrees
    //rightRight.whileHeld(new Chase(driveTrain, vision)); //I don't really remember what this does its probs dangerous though. I'm being serious by the way.
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  
   public Command getAutonomousCommand() {
     return pointBlank;
    }
    //hi
}
