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
        telemetry.addLine("Starting Near Basket Action");
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

        telemetry.addLine("Finished Near Basket Action");
        telemetry.update();

        // Step 3: Strafe left 72 inches
        //WHEELS.driveDistance(-72, 0); // Negative X for strafing left
        //autoSleep();

        // Step 4: Lift arm 45 degrees again
        //ARM.rotateToAngle(ARM.getRotationDegrees() + 45);
        //autoSleep();

        // Step 5: Reverse the claw intake to score
        //CLAW.ejectIntake();
        //autoSleep();

        // Step 6: Rotate servo back
        //CLAW.stopIntake(); // Not too sure if this is what I need to do but this resets servo
        //autoSleep();

        // Step 7: Bring arm down 90 degrees
        //ARM.rotateToAngle(ARM.getRotationDegrees() - 90);
        //autoSleep();

        // Step 8: Strafe right 65 inches
        //WHEELS.driveDistance(65, 0); // Postive X for strafing right
        //autoSleep();

        // Step 9: Drive forward 72 inches
        //WHEELS.driveDistance(72); // Positive Y for forward
        //autoSleep();

        // Step 10: Turn right 90 degrees
        //WHEELS.turn(90);
        //autoSleep();

        // Step 11: Lift arm 35 degrees
        //ARM.rotateToAngle( ARM.getRotationDegrees() + 35);
        //autoSleep();

        telemetry.addLine("Finished Near Basket Action");
        telemetry.update();
    }

    private void performFarBasketActions() {
        telemetry.addLine("Starting Far Basket Action");
        telemetry.update();

        // Step 4: Strafe right 64 inches
        WHEELS.driveDistance(64, 0); // Positive X for strafing right
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