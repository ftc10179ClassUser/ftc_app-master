package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class TickRatio {
    int ticks;
    static final int tickConst = 1440;

    TickRatio(int tempTicks) {
        ticks = tempTicks;
    }

    TickRatio (DcMotor motor) {
        String name = motor.getDeviceName();
        switch (name) {
            case "tetrix":

                break;
            case "TorqueNADO":

                break;
            case "im just makung temporary stuff":

                break;
            case "yeet":

                break;
            default:
                ticks = 1440;
                break;
        }
    }

    public int convert(int ticksIn) {
        return ticksIn/ticks * tickConst;
    }
}
