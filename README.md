# Overview

This repository is a template for our `FtcRobotController`s.
It is a fork of FTC's
official [FtcRobotController](https://github.com/FIRST-Tech-Challenge/FtcRobotController.git).
Please feel free to modify this template as necessary or even abandon it altogether.
This template is meant to be a gift from the previous programmers to the new programmers.
What you do with it is up to you.

However, this template is meant for general code to be reused across seasons,
so if you do use it, please refrain from putting anything year specific into this template
(e.g. motor names or autonomous commands).

# Creating a new repo

> [!Important]
> Only organization owners have the privileges to delete repos owned by chsRobotix.
> Before creating a repo under chsRobotix, consider if it is necessary.
> If not, create it under your own account.

1. Create a new repo
2. Under "Repository template", select "chsRobotix/TemplateRobotController"
3. Under "Owner", select "chsRobotix"
4. Name the repository. Preferably, the format for naming the repository should be "{starting
   year}-{season name in `kebab-case`}"
5. If you desire, add a short description, but it is probably unnecessary.
6. Click "Public" to set the repo's visibility.
7. Click "Create Repository"

Congrats! You have successfully created a repository!

# Pulling from template

By default, GitHub does not
allow users to pull from a template,
which complicates updating the new repository if the template changes.
To pull from the template, type

```
git remote add template https://github.com/chsRobotix/TemplateRobotController.git
git pull template main
```

> [!Caution]
> Before you pull changes from this template, ensure that the new changes would not
> break the current code.
> It is best to pull the changes from `template` into a branch
> and then merge it into the `main` branch.

# Updating with FTC's FtcRobotController

Keep this fork up to date with FTC's
official [FtcRobotController](https://github.com/FIRST-Tech-Challenge/FtcRobotController.git).
To do that, go to the GitHub page
for [this repository](https://github.com/chsRobotix/TemplateRobotController.git) and click on "Sync
fork."
Alternatively, you can type

```
git remote add upstream https://github.com/FIRST-Tech-Challenge/FtcRobotController.git
git pull upstream master
```

# RoadRunner

Our programs use [RoadRunner](https://github.com/acmerobotics/road-runner.git), a motion-planning
library for FTC.
For more details look at [Learn Road Runner](https://learnroadrunner.com/introduction.html) and
the [Road Runner Docs](https://rr.brott.dev/docs/v1-0/installation/).

## Tuning RoadRunner

The following is copied from the [Road Runner Docs](https://rr.brott.dev/docs/v1-0/tuning/).

1. Open either MecanumDrive or TankDrive depending on your bot.
2. Set the logo and USB direction of your IMU using the instructions on this page.
3. In TuningOpModes, set the DRIVE_CLASS variable to the drive class you’re using.
4. Then specify how the robot should track its position. There are a few built-in localizers:
    - Drive encoders: This is the default. The IMU will also be used on mecanum to get better
      heading.
    - Two (dead) wheel: Change the right-hand-side of localizer =   (mecanum, tank) to new
      TwoDeadWheelLocalizer(hardwareMap, lazyImu.get(), PARAMS.inPerTick, pose). The code expects
      the parallel, forward-pointing encoder to be named "par" and the perpendicular one to be
      named "perp".
    - Three (dead) wheel: Change the right-hand-side of localizer =   (mecanum, tank) to new
      ThreeDeadWheelLocalizer(hardwareMap, PARAMS.inPerTick, pose). The code expects the two
      parallel encoders to be named "par0" and "par1" and the perpendicular one to be named "perp".
    - Pinpoint Odometry Computer: Change the right-hand-side of localizer =   (mecanum, tank) to new
      PinpointLocalizer(hardwareMap, PARAMS.inPerTick, pose). The code expects a Pinpoint device to
      be configured with name "pinpoint". Tuning for a Pinpoint device is the same as tuning for two
      dead wheels.
5. Now check that the motors spin in the right direction. Positive power on all wheels should move
   the robot forward. And if you’re using drive encoders, the ticks recorded should increase in a
   positive direction.

Those with mecanum drives should use MecanumDirectionDebugger to make sure all the directions are
correct. The op mode uses the following button mappings:

/**

  * Xbox/PS4 Button - Motor
  * X / ▢ - Front Left
  * Y / Δ - Front Right
  * B / O - Rear Right
  * A / X - Rear Left
  *                                    The buttons are mapped to match the wheels spatially if you
  *                                    were to rotate the gamepad 45deg°. x/square is the front left
  *                    ________        and each button corresponds to the wheel as you go clockwise
  *                   / ______ \
  *     ------------.-'   _  '-..+              Front of Bot
  *              /   _  ( Y )  _  \                  ^
  *             |  ( X )  _  ( B ) |     Front Left   \    Front Right
  *        ___  '.      ( A )     /|       Wheel       \      Wheel
  *      .'    '.    '-._____.-'  .'       (x/▢)        \     (Y/Δ)
  *     |       |                 |                      \
  *      '.___.' '.               |          Rear Left    \   Rear Right
  *               '.             /             Wheel       \    Wheel
  *                \.          .'              (A/X)        \   (B/O)
  *                  \________/
  
*/

Reverse any motors running in the wrong direction with setDirection(...), and do the same for
corresponding drive encoders as well.

If you’re using dead wheels, run DeadWheelDirectionDebugger and reverse those encoders accordingly.

6. Connect to the robot wifi
7. Open [FTC Dashboard](http://192.168.43.1:8080/dash).
8. Now begin the tuning process.
> [!Important]
> The params on the Dashboard ***ARE NOT SAVED***.
> To save them, go to [MecanumDrive](./TeamCode/src/main/java/org/firstinspires/ftc/teamcode/MecanumDrive.java) and edit the appropriate variables.

## List of RoadRunner files and directories

- [messages/](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/messages)
- [tuning/](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/tuning)
- [Drawing](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Drawing.java)
- [MecanumDrive](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/MecanumDrive.java)
- [TankDrive](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/TankDrive.java)
- [ThreeDeadWheelLocalizer](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/ThreeDeadWheelLocalizer.java)
- [TwoDeadWheelLocalizer](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/TwoDeadWheelLocalizer.java)

## Updating RoadRunner

To update RoadRunner in this repository, type

```
git clone https://github.com/acmerobotics/road-runner-quickstart.git ../road-runner-quickstart
find ../road-runner-quickstart/ -name "*.md" -type f -delete
cp -rf ../road-runner-quickstart/* ./
rm -rf ../road-runner-quickstart
```

# TeamCode

Our team's code is
in [./TeamCode/src/main/java/org/firstinspires/ftc/teamcode/](./TeamCode/src/main/java/org/firstinspires/ftc/teamcode/).
It contains an Autonomous, TeleOp, and various helper classes to ease the process of programming the
robot.
For more details, look at
the [README.md](./TeamCode/src/main/java/org/firstinspires/ftc/teamcode/README.md).
The README also includes details about how OpModes work.
