import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.wpi.first.wpilibj.simulation.DIOSim;
import frc.robot.RobotMap;
import frc.robot.subsystems.Transporter.Transporter;

public class TransporterTests {
   private DIOSim m_entranceLimitSwitch;
   private DIOSim m_exitLimitSwitch;
   private Transporter m_transporter;
   @BeforeEach
   void setup() {
      m_transporter = new Transporter();  
      m_exitLimitSwitch = new DIOSim(RobotMap.Transporter.kExitLimitSwithPort);
      m_entranceLimitSwitch = new DIOSim(RobotMap.Transporter.kEntranceLimitSwithPort);
   }
   
   @AfterEach
   void shutdown() {
      m_transporter.close();
   }
   @Test
   void testTransportStartsEmpty() {
      assert m_transporter.isEmpty();
   }

   @Test
   void testTransportRecognizesBallEntrance() {
      simulateBallEntrance();
      
      assert m_transporter.getBallCount() == 1;
      assert !m_transporter.isEmpty();
   }

   @Test
   void testTransportRecognizesBallExit() {
      simulateBallEntrance();
      simulateBallExit();
      assert m_transporter.getBallCount() == 0;
      assert m_transporter.isEmpty();
   }

   private void simulateBallEntrance() {
      m_exitLimitSwitch.setValue(false);
      m_entranceLimitSwitch.setValue(true);
      
      m_transporter.periodic();
   }

   private void simulateBallExit() {
      m_exitLimitSwitch.setValue(true);
      m_entranceLimitSwitch.setValue(false);
      
      m_transporter.periodic();
   }
}
