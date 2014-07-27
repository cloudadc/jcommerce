/**
 * 用于调用计划任务
 * @author KingZhao
 */
package com.jcommerce.web.action;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TimerTaskSchedule extends TimerTask {
    Log log= LogFactory.getLog(CartTimerTask.class);
    private CartTimerTask cartTimerTask;
    public TimerTaskSchedule() {
    }

    public void run() {
       //cartTimerTask.doSomeThing();
    	//keywordstask.doSomeThing();
    	System.out.println("hi! i am task schedule!");
    }

    //spring的依赖注入
    public void setCartTimerTask(CartTimerTask cartTimerTask) {
        this.cartTimerTask = cartTimerTask;
    }
}
