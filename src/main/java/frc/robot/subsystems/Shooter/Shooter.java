// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase implements AutoCloseable{

  private final VictorSP m_motor;
  private final Encoder m_wheelEncoder;

  public Shooter() {
    m_motor = new VictorSP(RobotMap.Shooter.kMotorPort);
    m_motor.setInverted(ShooterConstants.kMotorInversion);

    m_wheelEncoder = new Encoder(RobotMap.Shooter.kEncoderPowerDataPort, RobotMap.Shooter.kEncoderOnlyDataPort);
    m_wheelEncoder.setDistancePerPulse(ShooterConstants.kEncoderDistancePerPulse);
    m_wheelEncoder.reset();

  }

  public void spinShooter(double speed) {
    m_motor.set(speed);
  }

  public double getSpinSpeed() {
    return m_wheelEncoder.getRate();
  }

  /**
   *
   * @return a basic spin command
   */
  public Command basicSpinCommand(double speed) {
    return new StartEndCommand(() -> {
      spinShooter(speed);
    }, () -> {
      spinShooter(0);
    }, this);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void close() {
    m_motor.close();
    m_wheelEncoder.close();
  }

}
