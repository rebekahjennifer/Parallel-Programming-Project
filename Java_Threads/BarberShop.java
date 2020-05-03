package test;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
 
public class BarberShop extends Thread {
 
	private static final long Cust_Time = 1500;
  
    public static void main(String args[]) {
        BarberShop barberShop = new BarberShop();
        barberShop.start(); 
    }
 
    public void run() {
    			Scanner obj = new Scanner(System.in);
    			
    			System.out.println("Enter the number of barbers in the shop M");
    			int barber = obj.nextInt();
    			System.out.println("There are " +barber+ " barbers in the shop");
    			
    			System.out.println("Enter the number of Customers in the shop C");
    			int customer = obj.nextInt();
    			System.out.println("There are " +customer+ " customers in the shop");
    			
    			System.out.println("Enter the number of waiting room chairs N");
    			int chairs = obj.nextInt();
    			System.out.println("There are " +chairs+ " Waiting room chairs available in the shop");
    			
    			obj.close();
    	
         BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(chairs);

    	for (int i = 1; i <= barber; i++) {
        Barber barber1 = new Barber(queue, i);
        barber1.start();  
    	 } 
 
        for (int j = 1; j <= customer; j++) {
            Customer aCustomer = new Customer(j, queue);
            aCustomer.start();
            try {
            	sleep(Cust_Time);
            } catch (InterruptedException ex) {};
        }
    	 
    }
}