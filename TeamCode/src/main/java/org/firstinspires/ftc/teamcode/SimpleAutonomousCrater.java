package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "In front of Crater")
public class SimpleAutonomousCrater extends LinearOpMode {
    WheelController wheelController;
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    boolean yeetle = false;

    Servo liftLock;
    SMotor tiltLift;
    ColorSensor colorSensor;
    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize wheels
        wheelController = new WheelController(hardwareMap);

        liftLock = hardwareMap.servo.get("liftLock");
        tiltLift = new SMotor(hardwareMap.dcMotor.get("tiltLift"), this);
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        //mineralTilter = hardwareMap.dcMotor.get("mineralTilter");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        tiltLift.setDirection(DcMotorSimple.Direction.REVERSE);
        colorSensor = hardwareMap.colorSensor.get("color");
        liftLock.setPosition(0);

        //tiltLift.setTargetPosition(7640);
        tiltLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        tiltLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();



        tiltLift.setPower(0.25);
        liftLock.setPosition(0.5);
        sleep(50);
        tiltLift.setPower(0);
        sleep(10000);


        frontLeft.setPower(0.5);
        frontRight.setPower(-0.5);
        backLeft.setPower(0.5);
        backRight.setPower(-0.5);

        sleep(250);
        wheelController.stopWheels();

        wheelController.moveXY(0,-0.5);
        sleep(600);

        wheelController.stopWheels();
        sleep(500);
        frontLeft.setPower(0.5);
        frontRight.setPower(-0.5);
        backLeft.setPower(0.5);
        backRight.setPower(-0.5);

        sleep(1000);
        wheelController.stopWheels();
        while (getRuntime() <= 29 && colorSensor.alpha() <=25 && !yeetle) {
            if (colorSensor.alpha() <=100) {
                frontLeft.setPower(-0.4);
                frontRight.setPower(0.4);
                backLeft.setPower(-0.4);
                backRight.setPower(0.4);
                telemetry.addData("alpha", colorSensor.alpha());
                telemetry.update();
            } else if (colorSensor.alpha() >= 100) {
                yeetle = true;
            }

        }
        sleep(500);
        wheelController.moveXY(0, -1);
        sleep(1000);
        wheelController.stopWheels();

    }
}
