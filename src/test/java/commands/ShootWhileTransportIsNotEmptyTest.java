import static org.mockito.Mockito.*;

import org.mockito.ArgumentMatcher;
import org.mockito.Mockito.*;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import frc.robot.commands.ShootWhileTransportIsNotEmpty;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterConstants;
import frc.robot.subsystems.Transporter.Transporter;

public class ShootWhileTransportIsNotEmptyTest {
    private Transporter m_transporter;
    private Shooter m_shooter;
    private ShootWhileTransportIsNotEmpty m_shootWhileTransportIsNotEmpty;

    @BeforeEach
    void setup() {
        m_transporter = mock(Transporter.class);
        m_shooter = mock(Shooter.class);
        m_shootWhileTransportIsNotEmpty = new ShootWhileTransportIsNotEmpty(m_shooter, m_transporter);
        CommandScheduler.getInstance().enable();
    }

    @AfterEach
    void shutdown() {
        m_transporter.close();
        m_shooter.close();
    }

    @Test
    void testTransportMovesWhenShooterReachesMinimalSpeedAndTransportIsNotEmpty() {
        when(m_shooter.getSpinSpeed()).thenReturn(ShooterConstants.kMinShootingRate);
        m_shootWhileTransportIsNotEmpty.execute();
        verify(m_transporter).spinTransporter(doubleThat(dbl -> dbl > 0));
    }

    @Test
    void testTransportDoesNotMoveWhenShooterDoesNotReachMinimalSpeed() {
        when(m_shooter.getSpinSpeed()).thenReturn(ShooterConstants.kMinShootingRate-1);
        m_shootWhileTransportIsNotEmpty.execute();
        verify(m_transporter, never()).spinTransporter(doubleThat(dbl -> dbl > 0));
    }

    @Test
    void testTransportIsStoppedWhenShooterLossesSpeed() {
        when(m_shooter.getSpinSpeed()).thenReturn(ShooterConstants.kMinShootingRate, ShooterConstants.kMinShootingRate / 2);
        m_shootWhileTransportIsNotEmpty.execute();
        m_shootWhileTransportIsNotEmpty.execute();
        verify(m_transporter, times(1)).spinTransporter(doubleThat(dbl ->  dbl > 0));
        verify(m_transporter, times(1)).spinTransporter(0);
        
    }
}
