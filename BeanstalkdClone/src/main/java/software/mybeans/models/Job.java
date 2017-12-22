package software.mybeans.models;

import java.util.Random;

public class Job {
    private long priority;
    private byte[] jobData;
    private long timeToRun;
    private long timeQueued;
    private long jobId;
    private Tube tube;
    private String status;

    public Job(byte[] jobData, long priority, long timeToRun, long timeQueued, Tube tube){
        this.jobData = jobData;
        this.priority = priority;
        this.timeQueued = timeQueued;
        this.timeToRun = timeToRun;
        if(timeToRun==0) this.timeToRun++;
        jobId = (new Random()).nextLong();
        this.tube = tube;
        this.status = "DELAYED";
    }

    public byte[] getJobData() {
        return jobData;
    }

    public long getPriority() {
        return priority;
    }

    public long getTimeToRun() {
        return timeToRun;
    }

    public long getTimeQueued() {
        return timeQueued;
    }

    public long getJobId() {
        return jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Tube getTube() {
        return tube;
    }
}
