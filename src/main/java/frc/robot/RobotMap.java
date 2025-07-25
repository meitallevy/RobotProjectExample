// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class RobotMap {
  public static class Operator {
    public static final int kDriverControllerPort = 0;
  }

  public static class Shooter {
    public static final int kMotorPort = 0;
    public static final int kEncoderPowerDataPort = 0;
    public static final int kEncoderOnlyDataPort = 1;
  }

  public static class Transporter {
    public static final int kMotorPort = 1;
    public static final int kEntranceLimitSwithPort = 2;
    public static final int kExitLimitSwithPort = 3;
  }
}
