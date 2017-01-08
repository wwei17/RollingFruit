package com.weiwei.rollingfruit;

public class MyMath{
	public static  float calculateAngle(float P1X, float P1Y, float P2X, float P2Y, float P3X, float P3Y){

        double numerator = P2Y*(P1X-P3X) + P1Y*(P3X-P2X) + P3Y*(P2X-P1X);
        double denominator = (P2X-P1X)*(P1X-P3X) + (P2Y-P1Y)*(P1Y-P3Y);
        double ratio = numerator/denominator;

        double angleRad = Math.atan(ratio);
        double angleDeg = (angleRad*180)/Math.PI;

        return (float) angleDeg;
    }
	
	public static float[] rotateVector(float[] p, float[] origin, float angleRadius){
		double cos_a = Math.cos(angleRadius);
		double sin_a = Math.sin(angleRadius);
		float x = (float) (cos_a * (p[0]-origin[0]) - sin_a * (p[1]-origin[1]) + origin[0]);
		float y = (float) (sin_a * (p[0]-origin[0]) + cos_a * (p[1]-origin[1]) + origin[1]);
		return new float[]{x , y};
	}
	
	public static <T> T[][] rotateMatric(T[][] t, int n_90){
		T temp = null;
		//terrible logic, pending improvement
		for(int k = 0 ; k < n_90; k++){
			for(int i = 0 ;i <= t.length/2; i++){
				for(int j = 0; j <= t[i].length/2; j++){
					temp = t[i][j];
					t[i][j] = t[j][t[j].length-i-1];
					t[j][t[j].length-i-1] = t[t.length-i-1][t[t.length-i-1].length-j-1];
					t[t.length-i-1][t[t.length-i-1].length-j-1] = t[t.length-j-1][i];
					t[t.length-j-1][i] = temp;
				}
			}
		}
		return t;
	}
	
	public static int getUpSide(float angle){
		angle = (angle%360+360)%360;
		if(angle < 45 || angle > 315) return 0;
		else if(angle < 135) return 1;
		else if(angle < 225) return 2;
		else return 3;
	}
	
}
