package com.workspace.server.configuration;

/**
 * Created by CHENMA11 on 1/25/2018.
 */
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ScheduledJob2 implements Job{

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub
        System.out.println("2任务运行----------------------");
    }

}