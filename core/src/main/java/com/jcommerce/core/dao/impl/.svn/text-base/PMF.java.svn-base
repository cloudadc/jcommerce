package com.jcommerce.core.dao.impl;

import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.compass.core.Compass;
import org.compass.core.config.CompassConfiguration;
import org.compass.core.config.CompassEnvironment;
import org.compass.gps.CompassGps;
import org.compass.gps.device.jdo.Jdo2GpsDevice;
import org.compass.gps.impl.SingleCompassGps;

import com.jcommerce.core.model.Goods;

public final class PMF {
    private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");
    
    private static final Logger log = Logger.getLogger(PMF.class.getName());
    private static final Compass compass;
	private static final CompassGps compassGps;
	

	static {
		compass =  new CompassConfiguration().setConnection("gae://index")
			.setSetting(CompassEnvironment.ExecutorManager.EXECUTOR_MANAGER_TYPE, "disabled")
					.addClass(Goods.class)
						.buildCompass();

		compassGps = new SingleCompassGps(compass);
		compassGps.addGpsDevice(new Jdo2GpsDevice("appenine", pmfInstance));
//		compassGps.start(); 
//
//		compassGps.index();
//		
//		compassGps.stop();
	}
	
    private PMF() {}

    public static PersistenceManagerFactory get() {
		return pmfInstance;
	}

	public static Compass getCompass(){
		return compass;
	}
	
	public static CompassGps getCompassGps(){
		return compassGps;
	}

}