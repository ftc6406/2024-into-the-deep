package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@Autonomous(name = "Auto")
public class Auto extends CustomLinearOp {
    /**
     * Raise the arm 28.5 inches and ejects the sample.
     */
    public void raiseArmAndEject() {
        // Set target degrees to reach the height of 28.5 inches
        double targetDegrees = 170.0; // Replace with actual degrees needed to reach 28.5 inches

        // Move the arm to the calculated target position
        ARM.rotateToAngle(targetDegrees);
        autoSleep(ARM.getRotationMotor());

        // Eject the object using the claw
        try {
            CLAW.ejectIntake();

        } catch (IllegalStateException e) {
            telemetry.addLine("Failed to eject intake");
        }
    }

    public void pickUpSample() {
        ARM.rotateToAngle(0);
        CLAW.startIntake();
        sleep(100);
        CLAW.stopIntake();
    }

    /**
     * When on the near side(i.e. next to bucket),
     * drive to the bucket.
     */
    public void nearDriveToBucket() {
        int targetTagId = (ALLIANCE_COLOR == AllianceColor.RED) ? 16 : 13;

        // Find the right AprilTag
        List<AprilTagDetection> currentDetections = WEBCAM.getAprilTag().getDetections();
        for (AprilTagDetection detection : currentDetections) {
            if (detection.id == targetTagId) {
                // Drive to the AprilTag
                double forwardDistance = detection.ftcPose.y + WEBCAM.getPoseAdjust()[1] - 12;
                double sidewaysDistance = detection.ftcPose.x + WEBCAM.getPoseAdjust()[0] - 12;
                WHEELS.driveDistance(sidewaysDistance, forwardDistance);
            }
        }
    }

    private void performNearBasketActions() {
        telemetry.addLine("Starting Near Basket Actions");
        telemetry.update();

        // Strafe left 48.0 inches
        WHEELS.driveDistance(-48, 0); // Negative X for strafing left
        autoSleep();

        // Strafe right 20 inches
        WHEELS.driveDistance(20, 0);

        // Drive forward 55.0 inches
        WHEELS.driveDistance(55); // Positive Y for forward
        autoSleep();

        // Strafe right 10.0 inches
        WHEELS.driveDistance(10.0, 0.0);
        autoSleep();

        telemetry.addLine("Finished Near Basket Actions");
        telemetry.update();
    }

    private void performFarBasketActions() {
        telemetry.addLine("Starting Far Basket Actions");
        telemetry.update();

        // Step 4: Strafe right 24
        WHEELS.driveDistance(24, 0); // Positive X for strafing right
        autoSleep();

        // Drive 48 inches forward
        WHEELS.driveDistance(48);
        autoSleep();

        // Turn around 180 degrees
        WHEELS.turn(180);
        autoSleep();

        // Strafe 5 inches left
        WHEELS.driveDistance(-5, 0);
        autoSleep();

        // Drive 36 inches forward
        WHEELS.driveDistance(36);
        autoSleep();

        // Drive 36 inches backward
        WHEELS.driveDistance(-36);
        autoSleep();

        // Drive 5 inches left
        WHEELS.driveDistance(-5, 0);
        autoSleep();

        // Drive 36 inches forward
        WHEELS.driveDistance(36);
        autoSleep();

        // Drive 36 inches back
        WHEELS.driveDistance(-36);
        autoSleep();

        // Strafe 5 inches left
        WHEELS.driveDistance(-5, 0);
        autoSleep();

        // Drive 36 inches forward
        WHEELS.driveDistance(36);
        autoSleep();

        telemetry.addLine("Finished Far Basket Actions");
        telemetry.update();
    }

    /**
     * Automatically runs after pressing the "Init" button on the Control Hub
     */
    @Override
    public void runOpMode() {
        super.runOpMode();

        // telemetry.addData("Number of AprilTags", WEBCAM.getAprilTagDetections().size());
        telemetry.update();

        if (TEAM_SIDE == TeamSide.NEAR) {
            performNearBasketActions();

        } else {
            performFarBasketActions();
        }
    }
}