package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.WheelController;

@TeleOp(name = "Tech Turtles TeleOp")

public class Teleop extends LinearOpMode {
    int tiltEncoderMax = 0;
    int tiltEncoderMin = 0;
    int tiltLiftEncoder = 0;
    int tiltLiftSpeed = 1;

    DcMotor tiltLift;
    DcMotor mineralExtend;
    Servo mineralBlocker;
    DcMotor mineralTilter;
    DcMotor snorfler;
    Servo liftLock;
    Servo tiltDump;
    DcMotor mineralLift;

    WheelController wheelController;

    @Override
    public void runOpMode() {
        // Normally we would initialise the wheel motors here, but our motor controller class does that for us
        wheelController = new WheelController(hardwareMap);
        // Initialize everything else
        liftLock = hardwareMap.servo.get("liftLock");
        tiltDump = hardwareMap.servo.get("tiltDump");
        tiltLift = hardwareMap.dcMotor.get("tiltLift");

        mineralTilter = hardwareMap.dcMotor.get("mineralTilter");
        mineralExtend = hardwareMap.dcMotor.get("mineralExtend");
        mineralBlocker = hardwareMap.servo.get("mineralBlocker");
        snorfler = hardwareMap.dcMotor.get("snorfler");

        //tiltLift.calibrate();

        waitForStart();
        while (opModeIsActive()) {
            // The distance from the joystick's position to the joystick's origin
            double r = Math.hypot(-gamepad1.left_stick_x, gamepad1.left_stick_y);

            // The angle at which the will move the robot
            double robotAngle = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;

            // Let's make the right joystick's x position a variable so it doesn't change as we are doing calculations
            double rightX = -gamepad1.right_stick_x;

            // Do the calculations for the wheel speeds
            double v1 = r * Math.cos(robotAngle) + rightX;
            double v2 = r * Math.sin(robotAngle) - rightX;
            double v3 = r * Math.sin(robotAngle) + rightX;
            double v4 = r * Math.cos(robotAngle) - rightX;

            // Finally, take the math we did for the speed of the wheels and actually set the wheel's speed
            wheelController.frontLeft.setPower(v1);
            wheelController.frontRight.setPower(v2);
            wheelController.backLeft.setPower(v3);
            wheelController.backRight.setPower(v4);

            if (Math.abs(gamepad2.right_stick_y) >= 0.05) {
                tiltLift.setPower(gamepad2.right_stick_y);
            } else {
                tiltLift.setPower(0);
            }

            if (Math.abs(gamepad2.left_stick_y) >= 0.05) {
                mineralExtend.setPower(gamepad2.left_stick_y);
            } else {
                mineralExtend.setPower(0);
            }

            float tilterSpeed = Range.clip(gamepad2.left_trigger - gamepad2.right_trigger, -1, 1);
            if (Math.abs(tilterSpeed) >= 0.05) {
                mineralTilter.setPower(tilterSpeed);
            } else {
                mineralTilter.setPower(0);
            }


            if (gamepad2.right_bumper) {
                snorfler.setPower(1);
            } else if (gamepad2.left_bumper) {
                snorfler.setPower(-1);
            } else {
                snorfler.setPower(0);
            }

            if (gamepad2.a) {
                mineralBlocker.setPosition(0.7);
            } else if (gamepad2.b) {
                mineralBlocker.setPosition(0.5);
            }

            if (gamepad2.x) {
                tiltDump.setPosition(0.05);
            } else if (gamepad2.y){
                tiltDump.setPosition(0.6);
            }

            if (gamepad1.a) {
                liftLock.setPosition(0);
            } else if (gamepad1.b) {
                liftLock.setPosition(1);
            }

        }
    }
}
