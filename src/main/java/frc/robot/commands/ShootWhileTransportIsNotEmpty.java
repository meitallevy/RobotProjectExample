// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterConstants;
import frc.robot.subsystems.Transporter.Transporter;
import frc.robot.subsystems.Transporter.TransporterConstants;

public class ShootWhileTransportIsNotEmpty extends Command {
  private final Shooter m_shooter;
  private final Transporter m_transporter;

  public ShootWhileTransportIsNotEmpty(Shooter shooter, Transporter transporter) {
    m_shooter = shooter;
    m_transporter = transporter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter, transporter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.spinShooter(0);
    m_transporter.spinTransporter(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooter.spinShooter(ShooterConstants.kDefaultShootingSpeed);
    if (m_shooter.getSpinSpeed() >= ShooterConstants.kMinShootingRate) {
      m_transporter.spinTransporter(TransporterConstants.kDefaultSpeed);
    } else {
      m_transporter.spinTransporter(0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.spinShooter(0);
    m_transporter.spinTransporter(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_transporter.isEmpty();
  }
}
