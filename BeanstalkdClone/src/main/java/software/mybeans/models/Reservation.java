package software.mybeans.models;

import java.util.Date;

public class Reservation {
    private Job job;
    private long expiryTime;

    public Reservation(Job job){
        this.job = job;
        this.expiryTime = job.getTimeToRun()*1000 + (new Date()).getTime();
    }

    public Job getJob() {
        return job;
    }

    public long getExpiryTime() {
        return expiryTime;
    }
}
