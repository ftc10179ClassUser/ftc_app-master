package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.WheelController;

@TeleOp(name = "Tech Turtles TeleOp")

public class Teleop extends OpMode {
    double encoderMax = 0;
    double encoderMin = 0;
    double tiltLiftEncoder = 0;
    double tiltLiftSpeed = 20;

    DcMotor tiltLift;

    WheelController wheelController;

    @Override
    public void init() {
        // Normally we would initialise the wheel motors here, but our motor controller class does that for us
        wheelController = new WheelController(hardwareMap);
        // Initialize everything else
        tiltLift = hardwareMap.dcMotor.get("tiltLift");
        tiltLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void loop() {
        // The distance from the joystick's position to the joystick's origin
        double r = Math.hypot(-gamepad1.left_stick_x, gamepad1.left_stick_y);

        // The angle at which the will move the robot
        double robotAngle = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;

        // Let's make the right joystick's x position a variable so it doesn't change as we are doing calculations
        double rightX = gamepad1.right_stick_x;

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

        if (gamepad1.left_bumper) {
            encoderMin -= 10;
        }
        if (gamepad1.right_bumper) {
            encoderMin += 10;
        }
        if (gamepad1.left_trigger > 0.5) {
            encoderMax -= 10;
        }
        if (gamepad1.right_trigger > 0.5) {
            encoderMax += 10;
        }

        if (gamepad1.a) {
            tiltLiftEncoder += tiltLiftSpeed;
        } else if (gamepad1.b) {
            tiltLiftEncoder -= tiltLiftSpeed;
        }

        if (tiltLiftEncoder > encoderMax) {
            tiltLiftEncoder = encoderMax;
        } else if (tiltLiftEncoder < encoderMin) {
            tiltLiftEncoder = encoderMin;
        }

        tiltLift.setTargetPosition((int)tiltLiftEncoder);

        telemetry.addData("Encoder Min", encoderMin);
        telemetry.addData("Encoder Max", encoderMax);
    }
}
