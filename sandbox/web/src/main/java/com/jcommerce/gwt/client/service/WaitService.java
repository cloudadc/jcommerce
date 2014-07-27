/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.service;

import com.google.gwt.user.client.Timer;

public class WaitService {
    public WaitService(final Job job) {
        Timer timer = new Timer() {
            public void run() {
                if (!job.isReady()) {
                    return;
                }
                
                this.cancel();
                
                job.run();
            }
        };
        
        timer.schedule(200);
        timer.scheduleRepeating(200);
    }
    
    public static interface Job {
        public boolean isReady();
        
        public void run();
    }
}
