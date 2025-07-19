// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.RobotMap.Operator;
import frc.robot.commands.Autos;
import frc.robot.commands.ShootWhileTransportIsNotEmpty;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Transporter.Transporter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here.
  private final Shooter m_shooter = new Shooter();
  private final Transporter m_transporter = new Transporter();

  private final CommandXboxController m_driverController = new CommandXboxController(Operator.kDriverControllerPort);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings.
   */
  private void configureBindings() {
    final Trigger shootAtMaxSpeed = m_driverController.b().whileTrue(
      m_shooter.basicSpinCommand(1)
      );
    final Trigger shootAllBalls = m_driverController.a().whileTrue(
      new ShootWhileTransportIsNotEmpty(m_shooter, m_transporter)
      );
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return Autos.moveTransporterThenShootAll(m_shooter, m_transporter);
  }
}
