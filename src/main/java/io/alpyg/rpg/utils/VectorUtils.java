package io.alpyg.rpg.utils;

import java.util.Random;

import com.flowpowered.math.vector.Vector3d;

public class VectorUtils {
	
	
	public static Vector3d randomVector3d() {
		Random random = new Random(System.nanoTime());
		double x, y, z;
        x = random.nextDouble() * 2 - 1;
        y = random.nextDouble() * 2 - 1;
        z = random.nextDouble() * 2 - 1;
        
        return new Vector3d(x, y, z).normalize();
	}

	  
	public static double roundYaw(double yaw) {
		double roundedYaw = 0.0F;
	    
	    if (yaw <= 30.0F || yaw >= 330.0F) { roundedYaw = 0.0F; }				// South
	    
	    else if ((yaw > 30.0F) && (yaw <= 60.0F)) { roundedYaw = 45.0F; }		// South East
	    
	    else if ((yaw > 60.0F) && (yaw <= 120.0F)) { roundedYaw = 90.0F; }		// East
	    
	    else if ((yaw > 120.0F) && (yaw <= 150.0F)) { roundedYaw = 135.0F; }	// North East
	    
	    else if ((yaw > 150.0F) && (yaw <= 210.0F)) { roundedYaw = 180.0F; }	// North
	    
	    else if ((yaw > 210.0F) && (yaw <= 240.0F)) { roundedYaw = 225.0F; }	// North West
	    
	    else if ((yaw > 240.0F) && (yaw <= 300.0F)) { roundedYaw = 270.0F; }	// West
	    
	    else if ((yaw > 300.0F) && (yaw <= 330.0F)) { roundedYaw = 315.0F; }	// South West
	    
	    return roundedYaw;
	}
	
	public static String getDirection(double yaw) {
		if (yaw == 0.0F)
			return "S";
		else if (yaw == 45.0F)
			return "SE";
		else if (yaw == 90.0F)
			return "E";
		else if (yaw == 135.0F)
			return "NE";
		else if (yaw == 180.0F)
			return "N";
		else if (yaw == 225.0F)
			return "NW";
		else if (yaw == 270.0F)
			return "W";
		else if (yaw == 315.0F)
			return "SW";
		return "";
	}
}
