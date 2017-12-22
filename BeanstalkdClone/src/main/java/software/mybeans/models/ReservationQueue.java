package software.mybeans.models;

import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReservationQueue implements Runnable {
    private PriorityQueue<Reservation> reservations;
    private ScheduledExecutorService scheduler;
    private Thread th;
    public ReservationQueue(){
        reservations = new PriorityQueue<Reservation>(100, new Comparator<Reservation>() {
            public int compare(Reservation o1, Reservation o2) {
                if((o1.getExpiryTime()-o2.getExpiryTime())<0){
                    return -1;
                } return 1;
            }
        });
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this,0,100, TimeUnit.MILLISECONDS);
        th = new Thread(this, "ReservationQueue");
        th.start();
    }

    synchronized public void add(Reservation reservation){
        reservations.add(reservation);
    }

    public void run() {
        //System.out.println("Running ReservationQueue thread...");
        synchronized (this){
            try {
                if (reservations.size() > 0) {
                    long currentTime = (new Date()).getTime();
                    while (reservations.size() > 0 && reservations.peek().getExpiryTime() < currentTime) {
                        Reservation reservation = reservations.poll();
                        //System.out.println("CurrentTime => " + currentTime);
                        //System.out.println("Reservation Expiration Time => " + reservation.getExpiryTime());
                        Job job = reservation.getJob();
                        synchronized (job) {
                            if (job.getStatus().equalsIgnoreCase("reserved")) {
                                job.setStatus("READY");
                                job.getTube().queueJob(job);
                            }
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        //System.out.println("Finished running ReservationQueue thread..");
    }
}
