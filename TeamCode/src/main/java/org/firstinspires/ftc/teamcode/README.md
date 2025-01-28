# Table of Contents

- [`CustomLinearOp`](#customlinearop)
- [`AutoSettings`](#autosettings)
- [`Auto`](#auto)
- [`DriverMode`](#drivermode)
- [`AlllianceColor`](#alliancecolor)
- [`TeamSide`](#teamside)
- [hardwareSystems/](#hardwareSystems)
    - [`MotorType`](#motortype)
    - [`Wheels`](#wheels)
    - [`MecanumWheels`](#mecanumwheels)
    - [`Arm`](#arm)
    - [`FoldingArm`](#foldingarm)
    - [`Claw`](#claw)
    - [`SingleServoIntakeClaw`](#singleservointakeclaw)
    - [`DoubleServoIntakeClaw`](#doubleservointakeclaw)
    - [`Webcam`](#webcam)

# [`CustomLinearOp`](CustomLinearOp.java)

A custom LinearOpMode that is the parent class of [`Auto`](#Auto) and [`DriverMode`](#DriverMode).
Its `runOpMode()` initializes all the robot's hardware and contains methods for auto-sleep,
sleeping while motors and continuous servos are running.
For the hardware initializing methods(e.g. `initWheels()`),
replace the abstract class return type with the the desired class(e.g. `MecanumWheels`).

# [`AutoSettings`](AutoSettings.java)

A TeleOpMode that writes to a file to store information before running Autonomous.
Pressing the A, B, X, and Y buttons sets the robot to blue near, red far, blue far, and red near,
respectively.
Pressing D-pad up sets the robot to use its arm during autonomous.
Pressing D-pad left sets the robot to only push the piece.
Does **NOT** extend [`CustomLinearOp`](#customlinearop).

# [`Auto`](Auto.java)

The Autonomous class, which runs without driver input.
Child class of [`CustomLinearOp`](#CustomLinearOp).
The annotation `@Autonomous(name = "Auto")` means that the class will be considered
an Autonomous program with the name of "Auto."
The `runOpMode()` method runs automatically without the need to do anything.
The first line of `runOpMode()` should be `super.runOpMode()` to run the parent class's hardware
initialization.

The TeleOp class which runs using driver input.
Child class of [`CustomLinearOp`](#CustomLinearOp).

# [`DriverMode`](DriverMode.java)

The annotation `@TeleOp(name = "DriverMode")` means that the class will be considered
a TeleOp program with the name of "DriverMode."
The `runOpMode()` method runs automatically without the need to do anything.
The first line of `runOpMode()` should be `super.runOpMode()` to run the parent class's hardware
initialization.
The `runOpMode()` runs the code in a try-catch to detect errors.

## [`PositionInput`](PositionInput.java)

A TeleOp that writes the [`AllianceColor`](#AllianceColor) and [`TeamSide`](#TeamSide) of the robot
into
external storage. It also stores the position of the position file.

# [`AllianceColor`](AllianceColor.java)

An enum that states whether the robot is on red or blue side.

# [`TeamSide`](TeamSide.java)

An enum that states whether the robot is on far or near side.

# [hardwareSystems/](hardwareSystems)

This subdirectory contains helper classes.
The classes are meant to separate and organize the various systems of the robot(e.g. arms, wheels,
etc.).
Contain methods for basic tasks such as driving and lifting the arm.
Some of the classes(e.g. [`Arm`](#Arm) and [`Wheels`](#Wheels)) are abstract and are meant to be
used as superclasses.
Being abstract classes rather than interfaces prevents multiple implementing.

## [`MotorType`](hardwareSystems/MotorType.java)

An enum that stores the type of motor(e.g. Tetrix Torquenado) and its number of ticks per
revolution.

## [`Wheels`](hardwareSystems/Wheels.java)

A abstract class for the robot's wheels.
Contains a HashSet of all motors.
Sets each motor to float when zero power is applied.
Contains a inner class called `WheelDistances` to store the distances between the wheels,
which is needed for turning.

## [`MecanumWheels`](hardwareSystems/MecanumWheels.java)

A subclass of the [`Wheels`](#Wheels) class for controlling the driving of a four-mechanic wheel
system.
Contains an inner class(`MotorSet`) to pass in the motors to the `MecanumWheels` constructor.

## [`Arm`](hardwareSystems/Arm.java)

An abstract class to control the robot's arm system.
Contains a HashSet of all motors.
Sets each motor to brake when zero power is applied.

> [!Note]
> `Arm` does not contain the servos for controlling the claw.
> For that, look at [`Claw`](#Claw)

## [`FoldingArm`](hardwareSystems/FoldingArm.java)

A subclass of [`Arm`](#Arm) that controls a rotating arm that can fold in two like an elbow.
Contains four inner classes(i.e. `MotorSet`, `RotationRange`,
and `ExtensionRange`) that group together parameters for the constructor.
More specific details can be found in [`Arm`](#Arm).
The current system is admittedly clunky.
If it becomes cumbersome, please do change it.

## [`Claw`](hardwareSystems/Claw.java)

An abstract class to control the robot's claw.
Has properties for servos to rotate in the X, Y, and Z-axes.
If any of the servos are not needed, set them to null.
The class methods check for null servo values.

## [`SingleServoIntakeClaw`](hardwareSystems/SingleServoIntakeClaw.java)

A subclass of [`Claw`](#Claw) that controls a claw with one intake servo.

## [`DoubleServoIntakeClaw`](hardwareSystems/DoubleServoIntakeClaw.java)

A subclass of [`Claw`](#Claw) that controls a claw with two intake servos.

## [`Webcam`](hardwareSystems/Webcam.java)

A class for vision and color detection.
Currently still in progress. 