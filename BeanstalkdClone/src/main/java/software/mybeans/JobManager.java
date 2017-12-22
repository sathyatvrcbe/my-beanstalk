package software.mybeans;

import software.mybeans.models.Job;

import java.util.HashMap;
import java.util.HashSet;

public class JobManager {
    private static HashMap<Long,Job> jobMap;
    public static void initialize(){
        jobMap = new HashMap<Long,Job>();
    }

    public static void addJob(Job job){
        jobMap.put(job.getJobId(),job);
    }

    public static Job getJobById(Long id){
        return jobMap.get(id);
    }

    public static void removeJob(Job job){
        jobMap.remove(job.getJobId());
    }
}
