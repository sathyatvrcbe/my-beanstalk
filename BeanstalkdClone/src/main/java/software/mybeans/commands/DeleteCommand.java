package software.mybeans.commands;

import software.mybeans.JobManager;
import software.mybeans.models.Client;
import software.mybeans.models.Job;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DeleteCommand implements BeanCommand {
    public boolean process(String command, Client client, InputStream in, OutputStream out) throws IOException {
        String[] cmd = command.split(" ");
        Long jobId = Long.parseLong(cmd[1].trim());
        Job job = JobManager.getJobById(jobId);
        if(job==null){
            out.write("NOT FOUND\r\n".getBytes());
        }else{
            synchronized (job){
                if(job.getStatus().equals("RESERVED")){
                    job.setStatus("DELETED");
                    JobManager.removeJob(job);
                    out.write("DELETED\r\n".getBytes());
                }else{
                    out.write("NOT FOUND\r\n".getBytes());
                }
            }
        }
        return false;
    }
}
