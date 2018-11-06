package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.WheelController;

@TeleOp(name = "Tech Turtles TeleOp")

public class Teleop extends LinearOpMode {
    int tiltEncoderMax = 0;
    int tiltEncoderMin = 0;
    int tiltLiftEncoder = 0;
    int tiltLiftSpeed = 1;

    DcMotor tiltLift;

    Servo liftLock;
    Servo tiltDump;    

    WheelController wheelController;

    @Override
    public void runOpMode() {
        // Normally we would initialise the wheel motors here, but our motor controller class does that for us
        wheelController = new WheelController(hardwareMap);
        // Initialize everything else
        liftLock = hardwareMap.servo.get("liftLock");
        tiltDump = hardwareMap.servo.get("tiltDump");
        tiltLift = hardwareMap.dcMotor.get("tiltLift");
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

            if (gamepad1.a && gamepad1.b) {
                tiltLift.setPower(0);
            } else if (gamepad1.a) {
                tiltLift.setPower(tiltLiftSpeed);
            } else if (gamepad1.b) {
                tiltLift.setPower(-tiltLiftSpeed);
            } else {
                tiltLift.setPower(0);
            }
        }
    }
}
