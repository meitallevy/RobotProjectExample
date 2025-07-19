// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Transporter;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Transporter extends SubsystemBase implements AutoCloseable {

  private final VictorSP m_motor;
  private final DigitalInput m_exitLimitSwitch;
  private final DigitalInput m_entranceLimitSwitch;
  private boolean m_ballEntered = false;
  private int m_ballCount;
  private boolean m_ballExited = false;

  public Transporter() {
    m_motor = new VictorSP(RobotMap.Transporter.kMotorPort);
    m_motor.setInverted(TransporterConstants.kMotorInversion);

    m_entranceLimitSwitch = new DigitalInput(RobotMap.Transporter.kEntranceLimitSwithPort);
    m_exitLimitSwitch = new DigitalInput(RobotMap.Transporter.kExitLimitSwithPort);

    m_ballCount = TransporterConstants.kMatchStartBallsCount;
  }

  public void spinTransporter(double speed) {
    m_motor.set(speed);
  }

  public void updateBallsStatus() {
    if (m_entranceLimitSwitch.get() && !m_ballEntered) {
      m_ballCount++;
      System.out.println("Ball Entered");
    }

    m_ballEntered = m_entranceLimitSwitch.get();

    if (m_exitLimitSwitch.get() && !m_ballExited) {
      m_ballCount--;
      System.out.println("Ball Existed");
      if (m_ballCount < 0) {
        System.out.println("Negetive Balls Count! Setting count to 0.");
        System.out.println("  -- Check what clicked the exitLimitSwitch / missed the entranceLimitSwitch.");
        m_ballCount = 0;
      }
    }
    m_ballExited = m_exitLimitSwitch.get();
  }

  /**
   *
   * @return a basic spin command
   */
  public Command basicSpinCommand(double speed) {
    return new StartEndCommand(() -> {
      spinTransporter(speed);
    }, () -> {
      spinTransporter(0);
    }, this);
  }

  public boolean getEntranceSwitch() {
    return m_entranceLimitSwitch.get();
  }

  public int getBallCount() {
    return m_ballCount;
  }

  public boolean getExitSwitch() {
    return m_exitLimitSwitch.get();
  }

  public boolean isEmpty() {
    return m_ballCount <= 0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateBallsStatus();
  }

  @Override
  public void close() {
    m_entranceLimitSwitch.close();
    m_exitLimitSwitch.close();
    m_motor.close();
  }

}
