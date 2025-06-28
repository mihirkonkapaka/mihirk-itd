package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.utils.priority.PriorityMotor;


public class DriveHW extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {


        //i assume this will be defined in another file
        //for the sake of Android Studio not screaming at me, initializing it here
        Gamepad gamepad = new Gamepad();

        //i could just put lfRaw into lF, but I don't see any reason to do it
        //if the extra allocation of memory for the variable matters, then it could be composed
        //but I don't think that would matter
        DcMotorEx lFRaw = hardwareMap.get(DcMotorEx.class, "leftFront");
        DcMotorEx rFRaw = hardwareMap.get(DcMotorEx.class, "rightFront");
        DcMotorEx lRRaw = hardwareMap.get(DcMotorEx.class, "leftRear");
        DcMotorEx rRRaw = hardwareMap.get(DcMotorEx.class, "rightRear");

        //i have no idea what the sensors are for in the initialization, so set them to null
        PriorityMotor lF = new PriorityMotor(lFRaw, "leftFront", 1.0, 1.0, null);
        PriorityMotor rF = new PriorityMotor(rFRaw, "rightFront", 1.0, 1.0, null);
        PriorityMotor lR = new PriorityMotor(lRRaw, "leftRear", 1.0, 1.0, null);
        PriorityMotor rR = new PriorityMotor(rRRaw, "rightRear", 1.0, 1.0, null);


        waitForStart();

        if (isStopRequested()) return;

        double vertical, horizontal, rotation;

        while (opModeIsActive()) {

            //negated y of left-stick because it seems like the controllers have it backwards in the first place
            vertical = -gamepad.left_stick_y;
            horizontal = gamepad.left_stick_x;
            rotation = gamepad.right_stick_x;

            double normDenominator = Math.max(vertical+horizontal+rotation, 1);

            //i could probably make these their own variables for finer control later on
            //theres *probably* a cleaner way to divide everything by normDenominator
            //but this works, and i doubt any performance increases will be big enough to matter


            lF.setTargetPower( (vertical + horizontal + rotation) / normDenominator );
            rF.setTargetPower( (vertical - horizontal - rotation) / normDenominator );
            lR.setTargetPower( (vertical - horizontal + rotation) / normDenominator );
            rR.setTargetPower( (vertical + horizontal - rotation) / normDenominator );

        }
    }
}