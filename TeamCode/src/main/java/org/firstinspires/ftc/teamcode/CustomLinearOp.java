package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.hardwareSystems.DoubleServoIntakeClaw;
import org.firstinspires.ftc.teamcode.hardwareSystems.FoldingArm;
import org.firstinspires.ftc.teamcode.hardwareSystems.MecanumWheels;
import org.firstinspires.ftc.teamcode.hardwareSystems.MotorType;
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

    protected MecanumWheels WHEELS;
    protected FoldingArm ARM;
    protected DoubleServoIntakeClaw CLAW;
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
     * Initiates all hardware needed for the wheels.
     * <br>
     * <strong>When starting a new season, change the return type from `Wheels` to the desired return type.</strong>
     */
    private MecanumWheels initWheels() {
        // Prevent multiple instantiation.
        if (WHEELS != null) {
            return WHEELS;
        }

        /*
         * Define wheels system hardware here.
         * e.g. hardwareMap.get(DcMotor.class, "exampleMotor");
         */
        MecanumWheels.MotorSet motorSet = new MecanumWheels.MotorSet(
                hardwareMap.get(DcMotor.class, "frontLeftWheel"),
                hardwareMap.get(DcMotor.class, "frontRightWheel"),
                hardwareMap.get(DcMotor.class, "backLeftWheel"),
                hardwareMap.get(DcMotor.class, "backRightWheel")
        );

        // Approximately measured from the CAD model in inches
        double wheelCircumference = 4.0 * Math.PI;
        double gearRatio = 1.0;
        double ticksPerInch = MotorType.TETRIX_TORQUENADO.getTicksPerRotation() * gearRatio / wheelCircumference;
        // Approximately measured from CAD
        Wheels.WheelDistances wheelDistances = new Wheels.WheelDistances(
                8.5,
                14.5
        );

        return new MecanumWheels(motorSet, wheelDistances, ticksPerInch);
    }

    /**
     * Initiate all hardware needed for the arm.
     * <strong>When starting a new season, change the return type from `Arm` to the desired return type.</strong>
     */
    private FoldingArm initArm() {
        // Prevent multiple instantiation.
        if (ARM != null) {
            return ARM;
        }

        /*
         * Define arm hardware here.
         * e.g. hardwareMap.get(DcMotor.class, "exampleMotor");
         */
        FoldingArm.MotorSet motorSet = new FoldingArm.MotorSet(
                hardwareMap.get(DcMotor.class, "rotationMotor"),
                hardwareMap.get(DcMotor.class, "foldingMotor")
        );

        double rotationGearRatio = 32.0 / 16.0; // 120.0 / 40.0;
        FoldingArm.RotationRange rotationRange = new FoldingArm.RotationRange(
                Integer.MIN_VALUE,
                Integer.MAX_VALUE, // 1080
                MotorType.TETRIX_TORQUENADO.getTicksPerRotation()
                        / 360.0
                        * rotationGearRatio
        );

        double foldingGearRatio = 120.0 / 40.0;
        FoldingArm.FoldingRange foldingRange = new FoldingArm.FoldingRange(
                Integer.MIN_VALUE,
                Integer.MAX_VALUE,
                MotorType.TETRIX_TORQUENADO.getTicksPerRotation()
                        / 360.0
                        * foldingGearRatio
        );

        return new FoldingArm(motorSet, rotationRange, foldingRange);
    }

    /**
     * Initiate all hardware needed for the claw.
     * <strong>When starting a new season, change the return type from `Claw` to the desired return type.</strong>
     */
    public DoubleServoIntakeClaw initClaw() {
        // Prevent multiple instantiation.
        if (CLAW != null) {
            return null;
        }

        /*
         * Define claw hardware here.
         * e.g. hardwareMap.get(Servo.class, "exampleServo");
         */
        return new DoubleServoIntakeClaw(
                hardwareMap.get(Servo.class, "rollServo"),
                null,
                null,
                hardwareMap.get(CRServo.class, "leftIntakeServo"),
                hardwareMap.get(CRServo.class, "rightIntakeServo")
        );
    }

    /**
     * Initiate the webcam.
     *
     * @return The `Webcam` object instantiated by this method.
     */
    public Webcam initWebcam(int cameraMonitorViewId) {
        int[] resolution = {160, 120};

        return new Webcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"),
                resolution,
                new double[]{
                        12,
                        -2,
                        12
                }
        );
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
     * Run automatically after pressing "Init."
     * Initiate all the robot's hardware.
     * Wait until the driver presses "Start."
     */
    @Override
    public void runOpMode() {
        autoSleepEnabled = true;

        WHEELS = initWheels();
        ARM = initArm();
        CLAW = initClaw();

        /*
         * Get camera ID to stream.
         * Currently not working.
         */
        /*
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                    "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()
            );
            telemetry.addData("cameraMonitorViewId", cameraMonitorViewId);
            telemetry.update();
            WEBCAM = initWebcam(cameraMonitorViewId);
        */

        // Try to read the start position
        try (BufferedReader reader = new BufferedReader(new FileReader(AutoSettings.getPositionFile()))) {
            // Read first line.
            String data = reader.readLine();
            telemetry.addData("Starting position: ", data);

            // Extract the data values.
            ALLIANCE_COLOR = AllianceColor.valueOf(data.split(",")[0]);
            TEAM_SIDE = TeamSide.valueOf(data.split(",")[1]);

        } catch (IOException | NullPointerException e) {
            telemetry.addLine(
                    (e instanceof IOException)
                            ? "ERROR: FAILED TO READ ROBOT POSITION FROM STORAGE FILE!"
                            : "The position file is blank."
            );

            telemetry.addLine("Defaulting to RED NEAR");
            ALLIANCE_COLOR = AllianceColor.RED;
            TEAM_SIDE = TeamSide.NEAR;
        }

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