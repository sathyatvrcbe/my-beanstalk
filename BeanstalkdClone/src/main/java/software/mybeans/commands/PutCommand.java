package software.mybeans.commands;

import software.mybeans.JobManager;
import software.mybeans.TubeManager;
import software.mybeans.models.Client;
import software.mybeans.models.Job;
import software.mybeans.models.Tube;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PutCommand implements BeanCommand {
    private ScheduledExecutorService scheduledExecutorService;
    Random random;
    public PutCommand(){
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    public boolean process(String command, final Client client, InputStream in, OutputStream out) throws IOException {
        final Tube tube = client.getTube();
        System.out.println("PUT Command : "+command);
        String[] cmd = command.split(" ");
        int bytes = Integer.parseInt(cmd[4].trim());
        final int priority = Integer.parseInt(cmd[1]);
        final int ttr = Integer.parseInt(cmd[3]);
        final byte[] data = new byte[bytes];
        in.read(data);
        in.read();
        in.read();
        int delay = Integer.parseInt(cmd[2]);
        final Job job = new Job(data, priority, ttr, System.currentTimeMillis(),tube);
        JobManager.addJob(job);
        scheduledExecutorService.schedule(new Runnable() {
            public void run() {
                System.out.println("Scheduler putting job into tube...");
                tube.queueJob(job);
            }
        },delay, TimeUnit.SECONDS);
        System.out.println("delay => "+delay);
        out.write(("INSERTED "+job.getJobId()+"\r\n").getBytes());
        return false;
    }
}
