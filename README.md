# Overview

This repository is a template for our FtcRobotControllers.
It is a fork of FTC's
official [FtcRobotController](https://github.com/FIRST-Tech-Challenge/FtcRobotController.git).
Please feel free to modify this template as necessary or even abandon it altogether.
This template is meant as a gift from the previous programmers to the new programmers.
What you do with it is up to you.
However, this template is meant for general code to be reused across seasons, so if you do use it,
please refrain from putting anything year specific into this template
(e.g. motor names or autonomous commands).

# Getting started

To create new repositories using this one, go to GitHub and and create a new repository with
chsRobotix as the owner. Then,
under "Repository template" select "chsRobotix/TemplateRobotController." By default, GitHub does not
allow users to pull from a template,
which complicates updating the new repository if the template changes.
To pull from the template, type

```
git remote add template https://github.com/chsRobotix/TemplateRobotController.git
git pull template main
```

> [!Warning]
> Before you pull changes from this template into a fork, ensure that the new changes would not
> break the current code.

It is suggested to name the new repo in the format of "{starting year}-{ending year}{season name}."
After creating the new repository,
update the README.md accordingly.

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