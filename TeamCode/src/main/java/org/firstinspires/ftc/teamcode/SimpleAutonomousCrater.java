package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Crater")
public class SimpleAutonomousCrater extends LinearOpMode {
    WheelController wheelController;
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    boolean yeetle = false;
    double diff;
    double doublealpha;
    double doublered;
    Servo tiltDump;
    Servo liftLock;
    SMotor tiltLift;
    ColorSensor colorSensor;
    DcMotor mineralTilter;
    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize wheels
        wheelController = new WheelController(hardwareMap);
        mineralTilter = hardwareMap.dcMotor.get("mineralTilter");
        liftLock = hardwareMap.servo.get("liftLock");
        tiltLift = new SMotor(hardwareMap.dcMotor.get("tiltLift"), this);
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        //mineralTilter = hardwareMap.dcMotor.get("mineralTilter");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        tiltDump = hardwareMap.servo.get("tiltDump");
        tiltLift.setDirection(DcMotorSimple.Direction.REVERSE);
        colorSensor = hardwareMap.colorSensor.get("color");
        liftLock.setPosition(0);
        tiltDump.setPosition(0.075);


        //tiltLift.setTargetPosition(7640);
        tiltLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        tiltLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();


        mineralTilter.setPower(0.3);
        sleep(425);
        mineralTilter.setPower(0);
        sleep(500);
        tiltLift.setPower(0.25);
        liftLock.setPosition(0.5);
        sleep(50);
        tiltLift.setPower(0);
        sleep(2000);
        tiltLift.setPower(1);
        sleep(950);
        tiltLift.setPower(0);
        sleep(1000);


        frontLeft.setPower(0.5);
        frontRight.setPower(-0.5);
        backLeft.setPower(0.5);
        backRight.setPower(-0.5);

        sleep(250);
        wheelController.stopWheels();

        wheelController.moveXY(0,-0.5);
        sleep(650);

        wheelController.stopWheels();
        sleep(500);
        frontLeft.setPower(0.5);
        frontRight.setPower(-0.5);
        backLeft.setPower(0.5);
        backRight.setPower(-0.5);

        sleep(1000);
        wheelController.stopWheels();
        while (getRuntime() <= 29 && !yeetle) {
            doublealpha = Double.valueOf(colorSensor.alpha());
            doublered = Double.valueOf(colorSensor.red());
            diff = doublealpha/doublered;
            if (diff >= 2.8){
                frontLeft.setPower(-0.23);
                frontRight.setPower(0.23);
                backLeft.setPower(-0.25);
                backRight.setPower(0.25);


                telemetry.update();

            } else {
                yeetle = true;
            }

            telemetry.addData("alpha", colorSensor.alpha());
            telemetry.addData("red", colorSensor.red());
            telemetry.addData("ratio", diff);
            telemetry.update();

        }

        sleep(500);
        frontLeft.setPower(0.25);
        frontRight.setPower(-0.25);
        backLeft.setPower(0.25);
        backRight.setPower(-0.25);
        sleep(1250);
        wheelController.stopWheels();
        sleep(500);
        wheelController.moveXY(0, -1);
        sleep(1000);
        wheelController.stopWheels();

    }
}
