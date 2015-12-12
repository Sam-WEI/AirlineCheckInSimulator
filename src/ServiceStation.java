import java.util.ArrayList;
import java.util.Random;


public class ServiceStation extends Thread {
	
	protected ArrayList<Queue<? extends Passenger>> queuesToServe;
	private final int MAX_SERVICE_TIME;
	
	int actualMaxServiceTime = -1;
	
	private Random random = new Random();
	
	private String stationName;
	
	private ArrayList<Integer> serviceTimeList;
	
	public ServiceStation(ArrayList<Queue<? extends Passenger>> queuesToServe, int maxServeTime, String stationName) {
		this.queuesToServe = queuesToServe;
		this.MAX_SERVICE_TIME = maxServeTime;
		this.stationName = stationName;
		
		serviceTimeList = new ArrayList<>();
	}
	

	@Override
	public void run() {
		while(AirlineCheckInSimulator.running){
			serveNext();
		}
	}
	
	protected void serveNext() {
		Passenger psgr = null;
		
		synchronized (queuesToServe.get(0)) {
			for(Queue<? extends Passenger> queue : queuesToServe){
				if(!queue.isEmpty()){
					psgr = queue.dequeue();
					break;
				}
			}
			if(psgr == null){
				System.out.printf("[%s] has no passenger to serve. Waiting...\n", stationName);
				psgr = queuesToServe.get(0).dequeue();
			}
		}
		
		if(psgr != null){
			serving(psgr);
		}
	}
	
	protected void serving(Passenger psgr){
		int time = random.nextInt(MAX_SERVICE_TIME);
		System.out.printf("[%s] is serving passenger \'%s\' for %d sec.\n", stationName, psgr.name, time);
		serviceTimeList.add(time);
		
		actualMaxServiceTime = (time > actualMaxServiceTime ? time : actualMaxServiceTime);
		
		try {
			sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public int getTotalServiceTime(){
		int sum = 0;
		for(int i : serviceTimeList){
			sum += i;
		}
		
		return sum;
	}
}
