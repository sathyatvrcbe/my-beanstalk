package software.mybeans.models;

import software.mybeans.BeanSettings;

import java.io.OutputStream;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Tube implements Runnable {
    private String tubeName;
    private PriorityQueue<Job> queue;
    private LinkedList<ReservationRequest> reservationRequestQueue;
    Integer reserveMonitor;
    Thread th;

    public Tube() {
        tubeName = "default";
        reserveMonitor = 1;
        queue = new PriorityQueue<Job>(100, new Comparator<Job>() {
            public int compare(Job o1, Job o2) {
                long diff = o1.getPriority() - o2.getPriority();
                if (diff == 0) {
                    if ((o1.getTimeQueued() - o2.getTimeQueued()) < 0) return -1;
                    return 1;
                }
                if (diff < 0) return -1;
                else return 1;
            }
        });
        this.reservationRequestQueue = new LinkedList<ReservationRequest>();
        //System.out.println("Constructor =>" + this.hashCode());

        th = new Thread(this, "TubeThread-" + tubeName);
        th.start();
    }

    public Tube(String tubeName) {
        this.tubeName = tubeName;
        reserveMonitor = 1;
        queue = new PriorityQueue<Job>(100, new Comparator<Job>() {
            public int compare(Job o1, Job o2) {
                long diff = o1.getPriority() - o2.getPriority();
                if (diff == 0) {
                    if ((o1.getTimeQueued() - o2.getTimeQueued()) < 0) return -1;
                    return 1;
                }
                if (diff < 0) return -1;
                else return 1;
            }
        });
        this.reservationRequestQueue = new LinkedList<ReservationRequest>();
        //System.out.println("Constructor =>" + this.hashCode());

        th = new Thread(this, "TubeThread-" + tubeName);
        th.start();
    }

    public String getTubeName() {
        return tubeName;
    }

    synchronized public void addReservation(ReservationRequest reservationRequest) {
        //System.out.println("Adding reservationRequest");

        //System.out.println("Adding reservationRequest - 1");
        //System.out.println(this.hashCode());
        reservationRequestQueue.addLast(reservationRequest);
        notify();

    }

    synchronized public void queueJob(Job job) {
        queue.add(job);
        job.setStatus("READY");
        //System.out.println("Queue length - "+queue.size());
        //System.out.println("Top => " + new String(queue.peek().getJobData()));
        notify();

    }

    public void run() {
        while (true) {
            try {
                synchronized (this) {
                    if (reservationRequestQueue.size() == 0 || queue.size() == 0) {
                        //System.out.println("waiting on reservationRequestQueue");
                        wait();
                        //System.out.println("notified..");
                        continue;
                    }
                    ReservationRequest reservationRequest = reservationRequestQueue.getFirst();
                    Job job = queue.peek();

                    synchronized (job) {
                        synchronized (reservationRequest) {
                            if (!reservationRequest.isDone()) {
                                if (job.getStatus().equalsIgnoreCase("ready")) {
                                    OutputStream out = reservationRequest.getClient().getClientSocket().getOutputStream();
                                    out.write(("RESERVED " + job.getJobId() + " " + job.getJobData().length + "\r\n").getBytes());
                                    out.write(job.getJobData());
                                    out.write("\r\n".getBytes());
                                    queue.remove(job);
                                    reservationRequestQueue.removeFirst();
                                    reservationRequest.setDone(true);
                                    job.setStatus("RESERVED");
                                    BeanSettings.getReservationQueue().add(new Reservation(job));
                                } else {
                                    queue.remove(job);
                                }
                            } else {
                                reservationRequestQueue.removeFirst();
                            }
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
