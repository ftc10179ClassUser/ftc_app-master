package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "In front of Crater")
public class SimpleAutonomousCrater extends LinearOpMode {
    WheelController wheelController;

    Servo liftLock;
    DcMotor tiltLift;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize wheels
        wheelController = new WheelController(hardwareMap);

        liftLock = hardwareMap.servo.get("liftLock");
        tiltLift = hardwareMap.dcMotor.get("tiltLift");

        waitForStart();

        tiltLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftLock.setPosition(0);
        sleep(1000);
        tiltLift.setTargetPosition(100);
        sleep(3000);

        wheelController.moveXY(0,-1);
        sleep(2000);
        wheelController.stopWheels();
    }
}
