package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.internal.opengl.models.Teapot;

import static com.sun.tools.javac.util.Constants.format;

@Autonomous(name = "Vuforia Test")
public class VuforiaTest extends LinearOpMode {
    VuforiaTrackables beacons;
    VuforiaLocalizer vuforia;
    VuforiaLocalizer.Parameters params;
    VuforiaTrackable relicTemplate;
    WheelController wheelController;

    double toX = 0;
    double toZ = -2;

    @Override
    public void runOpMode() throws InterruptedException {
        wheelController = new WheelController(hardwareMap);

        params = new VuforiaLocalizer.Parameters(com.qualcomm.ftcrobotcontroller.R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "AcuNtA7/////AAAAmY411Wf19002k5uGCVSx9Etw4/sJrBDz06eC+CTJqcia0KXAOu8HzPbmcDU9PdSW0qC/4qWEbGV8iuIdQhQUqR54xsoiNaXxWOgF02gkCxy7vHG9wvBtAIbieFLoSbkpU4KZry3BqCnemu9Z3FmnD2uOyhM7bXtPGvCcgn78cyIKDvZXfXftGCqR7VMl04yE2LSJpwWaw3AzOOovUKULIrl5oA2OX8BeF58ktVLTwXux8KnMkpM83H/XO1xGoTqTZ2Oo18BvTesJBGNcojtI9E0NtYA1dHgNUaFA/zPFS6rh3PrwK3PojSqQZ5e/Yd+698uQSvoSHBwx4Yiq6EKzFAXgobjX/t8Gwf9ttFlodt7Q";

        vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        relicTemplate = beacons.get(0);

        waitForStart();

        super.start();
        beacons.activate();

        while (opModeIsActive()) {
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();

            // Turn the pose into position and rotation to make it usable for us
            if (pose != null) {
                VectorF trans = pose.getTranslation();
                Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                // Extract the X, Y, and Z components of the offset of the target relative to the robot
                double tX = trans.get(0);
                double tY = trans.get(1);
                double tZ = trans.get(2);

                // Extract the rotational components of the target relative to the robot
                double rX = rot.firstAngle;
                double rY = rot.secondAngle;
                double rZ = rot.thirdAngle;

                if (dist(toX,tX,toZ,tZ) < 0.2) {
                    wheelController.moveXY(toX - tX, toZ - tZ);
                } else {
                    wheelController.moveTurn(-rY/1.1);
                }
            }
        }

        super.stop();
        beacons.deactivate();
    }

    double dist(double x1,double y1,double x2,double y2) {
        return Math.sqrt(((x1-x2) * (x1-x2)) + ((y1-y2)*(y1-y2)));
    }
}
