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
        // Drive back 15 inches
        WHEELS.driveDistance(-15.0);
        autoSleep();
        // Strafe left 5 inches
        WHEELS.driveDistance(-5, 0);
        autoSleep();
        // Drive forward 8 inches
        WHEELS.driveDistance(8.0);
        autoSleep();
        // Turn 90 degrees right
//        WHEELS.turn(90);
//        autoSleep();
        // Rotate arm 25 degrees up.
//        ARM.rotateToAngle(Math.abs(ARM.getRotationDegrees()) + 25);
        telemetry.addLine("Finished Near Basket Action");
        telemetry.update();
    }
    private void performFarBasketActions() {
        telemetry.addLine("Starting Far Basket Action");
        telemetry.update();
        // Drive back 10 inches
        WHEELS.driveDistance(-12.0);
        autoSleep();
        // Strafe right 60 inches
        WHEELS.driveDistance(72.0, 0.0);
        autoSleep();
        telemetry.addLine("Finished Far Basket Actions");
        telemetry.update();
    }
    private void hangSpecimen() {
        telemetry.addLine("Hanging specimen");
        // Raise arm to 60 degrees
        ARM.rotateToAngle(80.0);
        autoSleep();
        // Extend arm
        ARM.foldToAngle(180);
        // Drive forward 5 inches
        WHEELS.driveDistance(20.5);
        autoSleep();
        // Drop arm by 10 degrees
        ARM.rotateToAngle(50.0);
        // Outtake
        CLAW.ejectIntake();
        sleep(1500);
        // Apply a slight power to prevent it from hitting the ground too hard.
        //ARM.rotate(0.1);
        //sleep(1000);
        // ARM.rotate(0.0);
        telemetry.addLine("Finish hanging specimen");
    }
    /**
     * Automatically runs after pressing the "Init" button on the Control Hub
     */
    @Override
    public void runOpMode() {
        super.runOpMode();

        // telemetry.addData("Number of AprilTags", WEBCAM.getAprilTagDetections().size());
        telemetry.update();
        hangSpecimen();
        if (TEAM_SIDE == TeamSide.NEAR) {
            performNearBasketActions();
        } else {
            performFarBasketActions();
        }
    }
}