import java.util.Random;


public abstract class PassengerGenerator<T extends Passenger> extends Thread{

	private Queue<T> queue;
	int avgArrivalRate;
	
	Random random = new Random();
	
	public PassengerGenerator(Queue<T> queue, int avgArrivalRate){
		this.queue = queue;
		this.avgArrivalRate = avgArrivalRate;
	}
	
	@Override
	public void run() {
		while(AirlineCheckInSimulator.running){
			enqueueNew();
			
			int time = random.nextInt(avgArrivalRate * 2);
			try {
				sleep(time * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void enqueueNew(){
		T p = generateNew();
		
		synchronized (queue) {
			queue.enqueue(p);
			System.out.printf("[New Passenger] \'%s\' is on line.\n", p.name);
		}
		
	}
	
	protected abstract T generateNew();
}
