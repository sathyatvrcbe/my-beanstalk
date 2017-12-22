package software.mybeans;

import com.dinstone.beanstalkc.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import software.mybeans.server.ServerThread;

import java.util.HashSet;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
        String[] args = new String[10];
        //App.main(args);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Configuration config = new Configuration();
        config.setServiceHost("127.0.0.1");
        config.setServicePort(10000);
        // create job producer and consumer
        simpleProduceAndConsume(config);
        priorityTest(config);
        delayTest(config);
        reserveMultiple(config);
        requeueTest(config);
        deleteTest(config);
    }

    public void deleteTest(Configuration config){
        BeanstalkClientFactory factory = new BeanstalkClientFactory(config);
        JobProducer producer = factory.createJobProducer("deletetest");
        Long jobId = producer.putJob(0, 0, 2, "Hello".getBytes());
        producer.putJob(0, 0, 2, "World!!!".getBytes());
        producer.close();


        JobConsumer consumer = factory.createJobConsumer("deletetest");
        Job job = consumer.reserveJob(0);
        consumer.deleteJob(job.getId());
        System.out.println("Job Received => "+new String(job.getData()));

        assertTrue( new String(job.getData()).equals("Hello") );

        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        job = consumer.reserveJob(0);
        System.out.println("Job Received => "+new String(job.getData()));

        assertTrue( new String(job.getData()).equals("World!!!") );
        consumer.close();

    }

    public void requeueTest(Configuration config){
        System.out.println("-------------------- Starting requeue test ----------------------");
        BeanstalkClientFactory factory = new BeanstalkClientFactory(config);
        JobProducer producer = factory.createJobProducer("queuetest");

        for(int i=0;i<1;i++) {
            Long jobId = producer.putJob(0, 0, 2, "Hello".getBytes());
            System.out.println("Job Id => " + jobId);
        }

        producer.close();

        JobConsumer consumer = factory.createJobConsumer("queuetest");
        Job job = consumer.reserveJob(0);
        System.out.println("Job Received => "+new String(job.getData()));

        assertTrue( new String(job.getData()).equals("Hello") );

        job = consumer.reserveJob(0);
        System.out.println("Job Received => "+new String(job.getData()));

        assertTrue( new String(job.getData()).equals("Hello") );
        consumer.close();
    }

    public void simpleProduceAndConsume(Configuration config){
        BeanstalkClientFactory factory = new BeanstalkClientFactory(config);
        JobProducer producer = factory.createJobProducer("testa");

        for(int i=0;i<1;i++) {
            Long jobId = producer.putJob(0, 0, 0, "Hello".getBytes());
            System.out.println("Job Id => " + jobId);
        }


        producer.close();
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JobConsumer consumer = factory.createJobConsumer("testa");
        Job job = consumer.reserveJob(0);
        System.out.println("Job Received => "+new String(job.getData()));
        consumer.close();


        assertTrue( new String(job.getData()).equals("Hello") );
    }

    public void reserveMultiple(Configuration config){
        BeanstalkClientFactory factory = new BeanstalkClientFactory(config);

        HashSet<String> testSet = new HashSet<String>();
        for(int i=0;i<10;i++) testSet.add("tube"+i);

        for(String test : testSet) {
            JobProducer producer = factory.createJobProducer(test);
            Long jobId = producer.putJob(0, 0, 10, test.getBytes());
            producer.close();
        }



        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JobConsumer consumer = factory.createJobConsumer(testSet.toArray(new String[testSet.size()]));
        HashSet<String> verifySet = new HashSet<String>();
        for(int i=0;i<10;i++) {
            Job job = consumer.reserveJob(0);
            System.out.println("Job Received => " + new String(job.getData()));
            verifySet.add(new String(job.getData()));
        }
        consumer.close();
        System.out.println("---------------------------------------> "+verifySet);

        assertTrue( verifySet.containsAll(testSet) );
    }

    public void delayTest(Configuration config){
        BeanstalkClientFactory factory = new BeanstalkClientFactory(config);
        JobProducer producer = factory.createJobProducer("testb");

        producer.putJob(2, 10, 0, "Hello".getBytes());

        producer.close();
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JobConsumer consumer = factory.createJobConsumer("testb");
        Job job = consumer.reserveJob(0);
        assertTrue( new String(job.getData()).equals("Hello") );
        consumer.close();



    }

    public void priorityTest(Configuration config){
        BeanstalkClientFactory factory = new BeanstalkClientFactory(config);
        JobProducer producer = factory.createJobProducer("testa");

        producer.putJob(10, 0, 2, "Hello".getBytes());
        producer.putJob(2, 0, 2, "World".getBytes());
        producer.putJob(0, 0, 2, "!".getBytes());

        producer.close();

        JobConsumer consumer = factory.createJobConsumer("testa");
        Job job = consumer.reserveJob(0);
        System.out.println("!!!"+new String(job.getData())+" ");
        assertTrue( new String(job.getData()).equals("!") );
        job = consumer.reserveJob(0);
        System.out.println("!!!"+new String(job.getData()));
        assertTrue( new String(job.getData()).equals("World") );
        job = consumer.reserveJob(0);
        System.out.println("!!!"+new String(job.getData()));
        assertTrue( new String(job.getData()).equals("Hello") );
        consumer.close();



    }
}
