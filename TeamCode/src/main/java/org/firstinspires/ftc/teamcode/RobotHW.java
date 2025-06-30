package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.priority.HardwareQueue;
import org.firstinspires.ftc.teamcode.utils.priority.PriorityMotor;
import org.firstinspires.ftc.teamcode.utils.priority.PriorityServo;

class Intake {

    public HardwareMap hardwareMap;
    public PriorityMotor intakeMotor;

    public Intake(RobotHW robot) {
        hardwareMap = robot.hardwareMap;

        intakeMotor = new PriorityMotor(
                hardwareMap.get(DcMotorEx.class, "intakeMotor"), "intakeMotor",
                1.0, 1.0, null
        );

        robot.hardwareQueue.addDevice(intakeMotor);

    }

    public void update() {
        //the power will probably depend on some other logic, but given its an active intake
        //i think its just best to always set it to 1/-1 depending on how its oriented
        intakeMotor.setTargetPower(1);
    }

}

class Drivetrain {

    public HardwareMap hardwareMap;
    public PriorityMotor lF, rF, lR, rR;
    public Gamepad gamepad;
    public double vertical, horizontal, rotation;

    public Drivetrain(RobotHW robot) {
        hardwareMap = robot.hardwareMap;
        gamepad = robot.gamepad;


        lF = new PriorityMotor(
                hardwareMap.get(DcMotorEx.class, "leftFront"), "leftFront",
                5.0, 1.0, null
        );

        robot.hardwareQueue.addDevice(lF);

        rF = new PriorityMotor(
                hardwareMap.get(DcMotorEx.class, "rightFront"), "rightFront",
                5.0, 1.0, null
        );

        robot.hardwareQueue.addDevice(rF);

        lR = new PriorityMotor(
                hardwareMap.get(DcMotorEx.class, "leftRear"), "leftRear",
                5.0, 1.0, null
        );

        robot.hardwareQueue.addDevice(lR);

        rR = new PriorityMotor(
                hardwareMap.get(DcMotorEx.class, "rightRear"), "rightRear",
                5.0, 1.0, null
        );

        robot.hardwareQueue.addDevice(rR);

    }

    public void update() {

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

class Deposit {

    public HardwareMap hardwareMap;
    public PriorityServo clawOpen, clawRotation, v4bar1, v4bar2, HExtension1, HExtension2;

    public Deposit(RobotHW robot) {
        hardwareMap = robot.hardwareMap;

        clawOpen = new PriorityServo(
                hardwareMap.get(Servo.class, "clawOpen"),
                "clawOpen",
                PriorityServo.ServoType.SPEED,
                1.0,
                1.0,
                1.0,
                1.0,
                false,
                1.0,
                1.0
        );

        clawRotation = new PriorityServo(
                hardwareMap.get(Servo.class, "clawRotation"),
                "clawRotation",
                PriorityServo.ServoType.TORQUE,
                1.0,
                1.0,
                1.0,
                1.0,
                false,
                1.0,
                1.0
        );

        v4bar1 = new PriorityServo(
                hardwareMap.get(Servo.class, "v4bar1"),
                "v4bar1",
                PriorityServo.ServoType.TORQUE,
                1.0,
                1.0,
                1.0,
                1.0,
                false,
                1.0,
                1.0
        );

        v4bar2 = new PriorityServo(
                hardwareMap.get(Servo.class, "v4bar2"),
                "v4bar2",
                PriorityServo.ServoType.TORQUE,
                1.0,
                1.0,
                1.0,
                1.0,
                false,
                1.0,
                1.0
        );

        HExtension1 = new PriorityServo(
                hardwareMap.get(Servo.class, "HExtension1"),
                "HExtension1",
                PriorityServo.ServoType.TORQUE,
                1.0,
                1.0,
                1.0,
                1.0,
                false,
                1.0,
                1.0
        );

        HExtension2 = new PriorityServo(
                hardwareMap.get(Servo.class, "HExtension2"),
                "HExtension2",
                PriorityServo.ServoType.SPEED,
                1.0,
                1.0,
                1.0,
                1.0,
                false,
                1.0,
                1.0
        );


    }

    public void update() {
        //power will depend on other logic
        //there's really nothing to update at the moment though

    }

}

public class RobotHW {
    public HardwareMap hardwareMap;
    public Intake intake;
    public Drivetrain drivetrain;
    public Deposit deposit;
    public HardwareQueue hardwareQueue;
    public Gamepad gamepad;

    public RobotHW(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        this.intake = new Intake(this);
        this.drivetrain = new Drivetrain(this);
        this.deposit = new Deposit(this);
        this.hardwareQueue = new HardwareQueue();
        this.gamepad = new Gamepad();
    }

    public void update() {
        drivetrain.update();
        intake.update();
        deposit.update();
    }
}
