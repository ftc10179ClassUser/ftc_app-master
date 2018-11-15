package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class WheelController {

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public TickRatio frontRightTick;
    public TickRatio frontLeftTick;
    public TickRatio backLeftTick;
    public TickRatio backRightTick;

    public int leftEncoder() {
        return frontLeft.getCurrentPosition();
    }

    public int rightEncoder() {
        return frontRight.getCurrentPosition();
    }

    public void resetLeftEncoder() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void resetRightEncoder() {
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void moveXY(double tx, double ty) {
        double x = -Range.clip(tx, -1, 1);
        double y = Range.clip(ty, -1, 1);


        // The speed at which we will move the robot
        double r = Math.hypot(-x, y);

        // The angle at which the will move the robot
        double robotAngle = Math.atan2(y, -x) - Math.PI / 4;

        // Do the calculations for the wheel speeds
        double v1 = r * Math.cos(robotAngle);
        double v2 = r * Math.sin(robotAngle);
        double v3 = r * Math.sin(robotAngle);
        double v4 = r * Math.cos(robotAngle);

        // Finally, take the math we did for the speed of the wheels and actually set the wheel's speed
        frontLeft.setPower(v1);
        frontRight.setPower(v2);
        backLeft.setPower(v3);
        backRight.setPower(v4);
    }

    public void moveTurn(double tspeed) {
        double speed = Range.clip(tspeed, -1, 1);
        // Just a simple tank turn, left goes forward and right goes backward
        frontLeft.setPower(speed);
        frontRight.setPower(-speed);
        backLeft.setPower(speed);
        backLeft.setPower(-speed);
    }

    public void stopWheels() {
        moveXY(0,0);
    }

    WheelController(HardwareMap hardwareMap) {
        // When the wheel controller is instantiated, it gets the motors and sets them up
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftTick = new TickRatio(frontLeft);

    }
}
