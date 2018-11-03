package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class TickRatio {
    int rot, ticks;



    TickRatio(int tempRot, int tempTicks) {
        rot = tempRot;
        ticks = tempTicks;
    }

    TickRatio (DcMotor motor) {
        motor.getDeviceName();
    }
}
