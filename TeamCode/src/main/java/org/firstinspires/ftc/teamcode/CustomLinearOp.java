package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.hardwareSystems.Arm;
import org.firstinspires.ftc.teamcode.hardwareSystems.Claw;
import org.firstinspires.ftc.teamcode.hardwareSystems.Webcam;
import org.firstinspires.ftc.teamcode.hardwareSystems.Wheels;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

public class CustomLinearOp extends LinearOpMode {
    /**
     * Whether the robot will automatically sleep after each command.
     */
    protected boolean autoSleepEnabled;

    /* Robot systems */

    protected Wheels WHEELS;
    protected Arm ARM;
    protected Claw CLAW;
    protected Webcam WEBCAM;

    /**
     * Store which alliance the robot is on.
     */
    protected AllianceColor ALLIANCE_COLOR;
    /**
     * Store which side the robot is on(i.e. far or near).
     */
    protected TeamSide TEAM_SIDE;

    public HashSet<DcMotor> getAllDcMotors() {
        HashSet<DcMotor> motors = new HashSet<>();
        // hardware.dcMotor stores all the DcMotors as name-device pairs.
        for (Map.Entry<String, DcMotor> ele : hardwareMap.dcMotor.entrySet()) {
            motors.add(ele.getValue());
        }

        return motors;
    }

    /**
     * Gets all CR servos if they are present.
     *
     * @return A HashSet containing all the CR servos used by this robot.
     */
    public HashSet<CRServo> getAllCrServos() {
        HashSet<CRServo> crServos = new HashSet<>();
        // `hardwareMap.crservo` stores all the CRServos as name-device pairs.
        for (Map.Entry<String, CRServo> hardwareDevice : hardwareMap.crservo.entrySet()) {
            crServos.add(hardwareDevice.getValue());
        }

        return crServos;
    }

    /**
     * Get all the names in the `HardwareMap` that that are not connected to a device.
     * <br>
     * <em><strong>THIS METHOD IS NOT WORKING CURRENTLY!!!</strong></em>
     *
     * @return A `HashSet` of all the hardware devices that can not be found.
     */
    public HashSet<String> getMissingHardwareDevices() {
        HashSet<String> missingHardwareDevices = new HashSet<>();

        // Loop through each `DeviceMapping`(e.g. `Servo`s and `DcMotor`s).
        for (HardwareMap.DeviceMapping<? extends HardwareDevice> deviceMapping : hardwareMap.allDeviceMappings) {
            // Check if each device in the mapping is null.
            for (Map.Entry<String, ? extends HardwareDevice> hardwareDevice : deviceMapping.entrySet()) {
                if (hardwareDevice.getValue() == null) {
                    missingHardwareDevices.add(hardwareDevice.getKey());
                }
            }
        }

        return missingHardwareDevices;
    }

    /**
     * Sleeps the robot while any motors are running.
     */
    public void autoSleep() {
        autoSleep(getAllDcMotors());
    }

    public void autoSleep(DcMotor... motors) {
        autoSleep(new HashSet<>(Arrays.asList(motors)));
    }

    /**
     * Sleeps the robot while the given motors are running.
     *
     * @param motors The motors that are running.
     */
    public void autoSleep(HashSet<DcMotor> motors) {
        // Sleep while any of the motors are still running.
        while (motors.stream().anyMatch(DcMotor::isBusy)) {
            sleep(1);
        }
    }

    /**
     * Initiates all hardware needed for the wheels.
     * <br>
     * <strong>When starting a new season, change the return type from `Wheels` to the desired return type.</strong>
     */
    private void initWheels() {
        // Prevent multiple instantiation.
        if (WHEELS != null) {
            return;
        }

        // TODO: Replace `Wheels()` with a constructor of the desired `Wheels` subclass(e.g. `MecanumWheels`)
        //  You might want to look at the class and code from previous years for reference.
        WHEELS = new Wheels();
    }

    /**
     * Initiate all hardware needed for the arm.
     * <strong>When starting a new season, change the return type from `Arm` to the desired return type.</strong>
     */
    private void initArm() {
        // Prevent multiple instantiation.
        if (ARM != null) {
            return;
        }

        // TODO: Replace `Arm()` with a constructor of the desired `Arm` subclass(e.g. `FoldingArm`)
        //  You might want to look at the class and code from previous years for reference.
        ARM = new Arm();
    }

    /**
     * Initiate all hardware needed for the claw.
     * <strong>When starting a new season, change the return type from `Claw` to the desired return type.</strong>
     */
    public void initClaw() {
        // Prevent multiple instantiation.
        if (CLAW != null) {
            return;
        }

        // TODO: Replace `Claw()` with a constructor of the desired `Claw` subclass(e.g. `SingleServoIntakeClaw`)
        //  You might want to look at the class and code from previous years for reference.
        CLAW = new Claw(
                null, // TODO: Replace with the appropriate servo object, e.g. `hardwareMap.get(Servo.class, "exampleServo");`
                null, // TODO: Replace with the appropriate servo object, e.g. `hardwareMap.get(Servo.class, "exampleServo");`
                null // TODO: Replace with the appropriate servo object, e.g. `hardwareMap.get(Servo.class, "exampleServo");`
        );
    }

    /**
     * Initiate the webcam.
     *
     * @return The `Webcam` object instantiated by this method.
     */
    public void initWebcam(int cameraMonitorViewId) {
        // TODO: This is the lowest resolution(width, height) supported by a Logitech webcam.
        //  Adjust as necessary.
        int[] resolution = {160, 120};

        // TODO: This adjusts the pose to account for where the camera is positioned.
        //  Probably best to measure from the intake to the camera.
        //  Measured in inches.
        double[] poseAdjust = new double[]{
                0,
                0,
                0
        };

        WEBCAM = new Webcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), // Default camera name
                resolution,
                poseAdjust
        );
    }

    /**
     * Retrieve the contents of the Auto Settings file as a `String`,
     * or `null` if there is nothing to read.
     *
     * @param autoSettingsFile A String representing the file path to be read.
     * @return A String representation of the setting file's contents.
     */
    public String readAutoSettingsFile(String autoSettingsFile) {
        // Try to read the auto settings
        try (BufferedReader reader = new BufferedReader(new FileReader(autoSettingsFile))) {
            // Read first line.
            String data = reader.readLine();
            telemetry.addData("Starting position: ", data);

            return data;

        } catch (IOException | NullPointerException e) {
            telemetry.addLine(
                    (e instanceof IOException)
                            ? "ERROR: FAILED TO READ AUTO_SETTINGS FILE!"
                            : "The position file is blank."
            );
            telemetry.addLine("Defaulting to RED NEAR");

            return null;
        }
    }

    /**
     * Overloads {@link CustomLinearOp#readAutoSettingsFile(String)}.
     * {@code autoSettingsFile} defaults to {@link AutoSettings#getPositionFile()}.
     *
     * @see CustomLinearOp#readAutoSettingsFile(String)
     */
    public String readAutoSettingsFile() {
        return readAutoSettingsFile(AutoSettings.getPositionFile());
    }

    /**
     * Run automatically after pressing "Init."
     * Initiate all the robot's hardware.
     * Wait until the driver presses "Start."
     */
    @Override
    public void runOpMode() {
        autoSleepEnabled = true;

        initWheels();
        initArm();
        initClaw();

        /*
         * Get camera ID to stream.
         * TODO: Currently not working.
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()
        );
        telemetry.addData("cameraMonitorViewId", cameraMonitorViewId);
        telemetry.update();
        initWebcam(cameraMonitorViewId);

        // Try to read the auto settings
        String autoSettings = readAutoSettingsFile(AutoSettings.getPositionFile());
        ALLIANCE_COLOR = autoSettings != null ? AllianceColor.valueOf(autoSettings.split(",")[0]) : AllianceColor.RED;
        TEAM_SIDE = autoSettings != null ? TeamSide.valueOf(autoSettings.split(",")[1]) : TeamSide.NEAR;

        // Set the camera color.
        /*
        switch (ALLIANCE_COLOR) {
            case RED:
                WEBCAM.setTargetColor(Webcam.Color.RED);
                break;

            case BLUE:
                WEBCAM.setTargetColor(Webcam.Color.BLUE);
                break;
        }
         */
        telemetry.addData("Starting position", ALLIANCE_COLOR.name() + ", " + TEAM_SIDE.name());

        waitForStart();
    }
}