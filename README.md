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
4. Name the repository. Preferably, the format for naming the repository should be "{starting year}-{season name in `kebab-case`}"
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
