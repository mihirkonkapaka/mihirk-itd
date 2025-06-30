package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.RobotHW;
import org.firstinspires.ftc.teamcode.utils.priority.PriorityMotor;


public class DriveHW extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {


        //i assume this will be defined in another file
        //for the sake of Android Studio not screaming at me, initializing it here

        RobotHW robot = new RobotHW(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            robot.update();

        }
    }
}