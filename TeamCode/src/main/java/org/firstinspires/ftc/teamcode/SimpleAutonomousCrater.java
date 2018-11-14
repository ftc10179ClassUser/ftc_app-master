package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "In front of Crater")
public class SimpleAutonomousCrater extends LinearOpMode {
    WheelController wheelController;

    Servo liftLock;
    SMotor tiltLift;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize wheels
        wheelController = new WheelController(hardwareMap);

        liftLock = hardwareMap.servo.get("liftLock");
        tiltLift = new SMotor(hardwareMap.dcMotor.get("tiltLift"), this);
        tiltLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        tiltLift.setDirection(DcMotorSimple.Direction.REVERSE);
        liftLock.setPosition(1);
        tiltLift.setTargetPosition(7640);

        waitForStart();

        tiltLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftLock.setPosition(0);
        sleep(1000);
        tiltLift.setTargetPosition(-570);
        while (!tiltLift.isAtPosition) {
            tiltLift.update();
        }

        wheelController.moveXY(0,-1);
        sleep(2000);
        wheelController.stopWheels();
    }
}
