package test;

import java.util.concurrent.BlockingQueue;

class Customer extends Thread {
    public int iD;

    BlockingQueue<Integer> queue = null;

    public Customer(int i, BlockingQueue<Integer> queue) {
        iD = i;
        this.queue = queue;
    }

    public void run() {
        while (true) { 
            try {
                this.queue.add(this.iD); // Customer stays on waiting chair queue till he gets a haircut
                this.getHcut(); 
            } catch (IllegalStateException e) {

                System.out.println("There are no free seats in waiting room. Customer "
                        + this.iD + " has left the barbershop.");
            }
            break;
        }
    }

    public void getHcut() {
        System.out.println("Customer " + this.iD + " took a chair for a haircut");
    }
}
