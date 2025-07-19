// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Transporter.Transporter;
import frc.robot.subsystems.Transporter.TransporterConstants;

public final class Autos {
  /** static factory for an autonomous command. */
  public static Command moveTransporterThenShootAll(Shooter shooter, Transporter transporter) {
    return Commands.sequence(transporter.basicSpinCommand(TransporterConstants.kDefaultSpeed),
        new ShootWhileTransportIsNotEmpty(shooter, transporter));
  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
