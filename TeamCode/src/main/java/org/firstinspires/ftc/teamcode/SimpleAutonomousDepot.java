package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Depot")
public class SimpleAutonomousDepot extends LinearOpMode {
    WheelController wheelController;
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    Servo mineralBlocker;
    boolean yeetle = false;
    double diff;
    double doublealpha;
    double doublered;
    Servo tiltDump;
    Servo liftLock;
    SMotor tiltLift;
    ColorSensor colorSensor;
    DcMotor mineralTilter;
    int position;
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
        mineralBlocker = hardwareMap.servo.get("mineralBlocker");
        backRight = hardwareMap.dcMotor.get("backRight");
        tiltDump = hardwareMap.servo.get("tiltDump");
        tiltLift.setDirection(DcMotorSimple.Direction.REVERSE);
        colorSensor = hardwareMap.colorSensor.get("color");
        liftLock.setPosition(0);
        tiltDump.setPosition(0.05);
        mineralBlocker.setPosition(0.65);

        //tiltLift.setTargetPosition(7640);
        tiltLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        tiltLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();


        mineralTilter.setPower(0.3);
        sleep(450);
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
            if (diff >= 2.6){
                frontLeft.setPower(-0.225);
                frontRight.setPower(0.225);
                backLeft.setPower(-0.25);
                backRight.setPower(0.25);


                telemetry.update();

            } else {
                yeetle = true;
                if(getRuntime() >= 11 && getRuntime() <= 14) {
                    position = 2;
                } else if(getRuntime() >= 17 && getRuntime()<= 19) {
                    position = 1;
                } else if(getRuntime() >= 4 && getRuntime() <= 9) {
                    position = 3;
                }
            }

            telemetry.addData("alpha", colorSensor.alpha());
            telemetry.addData("red", colorSensor.red());
            telemetry.addData("ratio", diff);
            telemetry.update();

        }
        telemetry.addData("ree", position);
        sleep(500);
        frontLeft.setPower(0.25);
        frontRight.setPower(-0.25);
        backLeft.setPower(0.25);
        backRight.setPower(-0.25);
        sleep(1250);
        wheelController.stopWheels();
        sleep(500);
        wheelController.moveXY(0, -0.25);
        switch (position) {
            case 1:
                sleep(2500);
                break;
            case 2:
                sleep(3000);
                break;
            case 3:
                sleep(2500);
                break;
        }

        wheelController.stopWheels();

        switch (position) {
            case 1:
                frontLeft.setPower(-0.5);
                frontRight.setPower(0.5);
                backLeft.setPower(-0.5);
                backRight.setPower(0.5);
                sleep(750);
                wheelController.stopWheels();

                break;
            case 2:



                break;
            case 3:
                frontLeft.setPower(-0.5);
                frontRight.setPower(0.5);
                backLeft.setPower(-0.5);
                backRight.setPower(0.5);
                sleep(750);
                wheelController.stopWheels();
                break;


        }
        mineralTilter.setPower(0.25);

        sleep(400);
        mineralTilter.setPower(0);
        mineralBlocker.setPosition(0.5);
        sleep(500);
        mineralTilter.setPower(-5);
        sleep(900);
        mineralTilter.setPower(0);


    }
}
