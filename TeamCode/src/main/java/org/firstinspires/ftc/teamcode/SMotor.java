package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;

import static java.lang.Thread.sleep;

public class SMotor {
    private DcMotor motor;

    private LinearOpMode opMode;

    private int startingPos;
    private int currentSpeed;
    private int stage = 0;
    private int threshhold = 10;
    private int slowLength = 100;
    private int goToPosition = 0;
    private double positiveCalibration = 0;
    private double negativeCalibration = 0;

    private static final DcMotor.RunMode RUN_TO_POSITION = DcMotor.RunMode.RUN_TO_POSITION;
    private static final DcMotor.RunMode RUN_WITHOUT_ENCODER = DcMotor.RunMode.RUN_WITHOUT_ENCODER;
    private static final DcMotor.RunMode RUN_USING_ENCODER = DcMotor.RunMode.RUN_USING_ENCODER;
    private static final DcMotor.RunMode STOP_AND_RESET_ENCODER = DcMotor.RunMode.STOP_AND_RESET_ENCODER;

    public boolean isAtPosition = true;
    public boolean alreadyCalibrated = true;

    DcMotor.RunMode mode = DcMotor.RunMode.RUN_USING_ENCODER;

    SMotor(DcMotor tempMotor, LinearOpMode tempOpMode) {
        motor = tempMotor;
        opMode = tempOpMode;
    }

    public void setPower(double power) {
        if (mode == RUN_WITHOUT_ENCODER || mode == RUN_USING_ENCODER) {
            motor.setPower(Range.clip(power, -1, 1));
        }
    }

    public void setDirection(DcMotor.Direction dir) {
        motor.setDirection(dir);
    }

    public boolean isBusy() {
        return motor.isBusy();
    }

    public void setTargetPosition(int pos) {
        goToPosition = pos;
    }

    private void calibrate() {
        switch (stage) {
            case 0:
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                startingPos = motor.getCurrentPosition();
                currentSpeed = 0;
                stage++;
                break;
            case 1:
                if (motor.getCurrentPosition() == startingPos && currentSpeed < 1) {
                    stage++;
                }
                currentSpeed += 0.05;
                motor.setPower(Range.clip(currentSpeed, -1, 1));
                break;
            case 2:
                motor.setPower(0);
                positiveCalibration = currentSpeed;
                startingPos = motor.getCurrentPosition();
                currentSpeed = 0;
                stage++;
                break;
            case 3:
                if (motor.getCurrentPosition() == startingPos && currentSpeed > -1) {
                    stage++;
                }
                currentSpeed -= 0.05;
                motor.setPower(Range.clip(currentSpeed, -1, 1));
                break;
            case 4:
                stage++;
                alreadyCalibrated = true;
                negativeCalibration = currentSpeed;
                motor.setPower(0);
                break;
        }
    }

    public void setMode(DcMotor.RunMode tempMode) {
        mode = tempMode;
    }

    public int getCurrentPosition() {
        return motor.getCurrentPosition();
    }

    public void update() {
        if (alreadyCalibrated) {
            switch (mode) {
                case RUN_TO_POSITION:
                    if (motor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    }

                    isAtPosition = Math.abs(motor.getCurrentPosition() - goToPosition) <= threshhold;

                    if (isAtPosition) {
                        if (Math.abs(positiveCalibration) > Math.abs(negativeCalibration)) {
                            motor.setPower(positiveCalibration);
                        } else {
                            motor.setPower(negativeCalibration);
                        }
                    } else {
                        if (motor.getCurrentPosition() < goToPosition) {
                            motor.setPower(Range.clip(((Math.abs(motor.getCurrentPosition() - goToPosition)-threshhold) / slowLength) + positiveCalibration, 0, 1));
                        } else {
                            motor.setPower(Range.clip((-(Math.abs(motor.getCurrentPosition() - goToPosition)-threshhold) / slowLength) + negativeCalibration, -1, 0));
                        }
                    }
                    break;
                case STOP_AND_RESET_ENCODER:
                    if (motor.getMode() != DcMotor.RunMode.STOP_AND_RESET_ENCODER) {
                        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    }
                    break;
                case RUN_USING_ENCODER:
                    if (motor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    }
                    break;
                case RUN_WITHOUT_ENCODER:
                    if (motor.getMode() != DcMotor.RunMode.RUN_WITHOUT_ENCODER) {
                        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    }
                    break;
            }
        } else {
            //calibrate();
        }
    }
}
