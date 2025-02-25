package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.SwerveDrivetrain;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class SwerveDrive extends CommandBase {

    private double m_rotation;
    private Translation2d m_translation;
    private boolean m_fieldRelative;
    private boolean m_openLoop;
    
    private SwerveDrivetrain m_swerveDrivetrain;
    private XboxController m_driverController;
    private int m_driveAxis;
    private int m_strafeAxis;
    private int m_rotationAxis;

    /**
     * Driver control
     */
    public SwerveDrive(SwerveDrivetrain swerveDrivetrain, XboxController driverController, int driveAxis, int strafeAxis, int rotationAxis, boolean fieldRelative, boolean openLoop) {
        m_swerveDrivetrain = swerveDrivetrain;
        addRequirements(m_swerveDrivetrain);

        m_driverController = driverController;
        m_driveAxis = driveAxis;
        m_strafeAxis = strafeAxis;
        m_rotationAxis = rotationAxis;
        m_fieldRelative = fieldRelative;
        m_openLoop = openLoop;
    }

    @Override
    public void execute() {
        double yAxis = -m_driverController.getRawAxis(m_driveAxis);
        double xAxis = -m_driverController.getRawAxis(m_strafeAxis);
        double rAxis = -m_driverController.getRawAxis(m_rotationAxis);
        
        /* Deadbands */
        yAxis = (Math.abs(yAxis) < Constants.STICK_DEADBAND) ? 0 : yAxis;
        xAxis = (Math.abs(xAxis) < Constants.STICK_DEADBAND) ? 0 : xAxis;
        rAxis = (Math.abs(rAxis) < Constants.STICK_DEADBAND) ? 0 : rAxis;

        m_translation = new Translation2d(yAxis, xAxis).times(Constants.MAX_SPEED);
        m_rotation = rAxis * Constants.MAX_ANGULAR_VELOCITY;
        m_swerveDrivetrain.drive(m_translation, m_rotation, m_fieldRelative, m_openLoop);
    }
}
