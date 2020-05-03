package test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.util.Random;
import java.util.Random;

class Barber extends Thread {
	public static final long barberTime = 5000;
	double Mean = 20.0f;
	double StdDev = 15.0f;
	double customer_ArrivalTime = 0.0f;
	double haircut_ServiceTime = 0.0f;
	public static final long ShopCloseTime = barberTime * 3;
	Random fRandom = new Random();
    
    BlockingQueue<Integer> queue = null;
    private int id;

    public Barber(BlockingQueue<Integer> queue, int id) 
    {
        this.id = id;
        this.queue = queue;
    }

    public void run() 
    {
        while (true) 
        { 
        	try {	
            	
            	if(this.queue.isEmpty())	
            	{
            		System.out.println("Barber 0"+this.id+" is sleeping in his chair and waiting for service"); // When there are no customers barber is sleeping
            	}
                    Integer i = this.queue.poll(ShopCloseTime, TimeUnit.MILLISECONDS);
                    if (i==null) 
                    { 
                    	System.out.println("Barber 0"+this.id+" left the shop after working");
                    	break;
                    } // Barber working hours is over so shop should be closed
                this.cHair(i); 
            } catch (InterruptedException e) {
            }
        }
    }

    public void cHair(Integer i) {
    	
        System.out.println("The barber 0" + this.id + " is cutting hair for customer #" + i);
        
        try {
        	
        	/* Customer Arrival calculated at random intervals with Mean and StdDev */
        	customer_ArrivalTime = Math.round(Math.abs(Mean + fRandom.nextGaussian() * StdDev) * 100.0) / 100.0;
        	System.out.println("The customer arrival interval is " + customer_ArrivalTime + " MilliSeconds "); 
        	
        	/* Time taken for customers hair cut is calculated with mean and StdDev */
        	haircut_ServiceTime = Math.round(Math.abs(Mean + fRandom.nextGaussian() * StdDev) * 100.0) / 100.0;
        	System.out.println("The barber 0" +this.id+ " hair cut time is " + haircut_ServiceTime + " MilliSeconds ");
        	
        	sleep(barberTime);
        	} catch (InterruptedException ex) {
        }
    }
}