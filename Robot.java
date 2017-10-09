
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.hardware.motor.Motor;



public class Robot {

	static EV3UltrasonicSensor ultrasensor = new EV3UltrasonicSensor(SensorPort.S3);
	static EV3TouchSensor touchsensor = new EV3TouchSensor(SensorPort.S4);
	//To track total revolutions
//	static float totalRevsForward = (float) 8.52615766563725;
//	static float totalRevsBackward = (float) 2.557847299691175;
	
	//Track margin of error total revolutions forward
//	static float minusRevsForward = (float) 8.51000000000000;
//	static float positiveRevsForward = (float) 8.53000000000;
	static float speedForward = (float) (3069.41675962941/30);
	static float speedBackward = (float) (184.165006/5);
	
	
	//Track margin of error total revolutions backward
//	static float positiveRevsBackward = (float) 2.56000000;
//	static float minusRevsBackward = (float) 2.540000000000000;
	
	
	
	
	//Distance to Object with margin of error 
	static float minusDistancetoObject = (float) .448;
	static float positiveDistancetoObject = (float) .452;
	
	
public static void main(String[] args) {
	
	SensorMode touch = touchsensor.getTouchMode();
	SensorMode sonic = (SensorMode) ultrasensor.getDistanceMode();
	float[] sample_touch = new float[touch.sampleSize()];
	float[] sample_sonic = new float[sonic.sampleSize()];
	int process;
	
	
	Button.waitForAnyPress();
	
	Motor.A.setSpeed(speedForward);
	Motor.B.setSpeed(speedForward);
	Motor.A.forward();
	Motor.B.forward();
	long startTime = System.currentTimeMillis();
	process = 1;
	
	while(Motor.A.isMoving() && Motor.B.isMoving() && process == 1)
	{
		long movingTime = System.currentTimeMillis() - startTime;
		System.out.println("Time moving:" +""+ movingTime);
		if (29990 <= movingTime && movingTime <= 30010)
		{
			Motor.A.stop();
			Motor.B.stop();
		}
		
	}
	
	
	Button.waitForAnyPress();
	Motor.A.forward();
	Motor.B.forward();
	
	process = 2;
	while(Motor.A.isMoving() && Motor.B.isMoving() && process == 2)
	{
		sonic.fetchSample(sample_sonic, 0);
		System.out.println("Sonic distance:" + ""+ sample_sonic[sample_sonic.length-1]);
		if(sample_sonic[sample_sonic.length-1] <= positiveDistancetoObject && sample_sonic[sample_sonic.length-1]>= minusDistancetoObject)
			{
				Motor.A.stop();
				Motor.B.stop();
				Sound.beep();
			}
	}
	
	Button.waitForAnyPress();
	Motor.A.forward();
	Motor.B.forward();
	
	process = 3;
	while(Motor.A.isMoving() && Motor.B.isMoving() && process == 3)
	{	
		touch.fetchSample(sample_touch,0);
		if(sample_touch[sample_touch.length-1] == 1)
		{
			Motor.A.stop();
			Motor.B.stop();
		}
	}
	
	Motor.A.setSpeed(speedBackward);
	Motor.B.setSpeed(speedBackward);
	Motor.A.backward();
	Motor.B.backward();
	
    process =4;
    startTime = System.currentTimeMillis();
	while(Motor.A.isMoving() && Motor.B.isMoving() && process == 4)
	{
		long movingTime = System.currentTimeMillis() - startTime;
		System.out.println("Time moving:" +""+ movingTime);
		if (4990 <= movingTime && movingTime <= 5010)
		{
			Motor.A.stop();
			Motor.B.stop();
		}
	}
	

}
}