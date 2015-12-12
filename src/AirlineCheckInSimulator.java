import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class AirlineCheckInSimulator {
	
	static boolean running = true;

	ArrayList<ServiceStation> stationList;
	
	ServiceStation firstClassServiceStation1;
	ServiceStation firstClassServiceStation2;
	ServiceStation coachServiceStation1;
	ServiceStation coachServiceStation2;
	ServiceStation coachServiceStation3;
	
	CoachGenerator coachGenerator;
	FirstClassGenerator firstClassGenerator;
	
	Queue<Coach> coachQueue;
	Queue<FirstClassPsgr> firstClassQueue;
	
	
	static int fcAvgArrivalRate = 20;
	static int coachAvgArrivalRate = 7;
	
	static int fcMaxSerTime = 40;
	static int coachMaxSerTime = 80;
	
	static int runningTime = 3 * 60;//in sec
	
	
	public AirlineCheckInSimulator(){
		firstClassQueue = new Queue<>();
		coachQueue = new Queue<>();
				
		firstClassGenerator = new FirstClassGenerator(firstClassQueue, fcAvgArrivalRate);
		coachGenerator = new CoachGenerator(coachQueue, coachAvgArrivalRate);
		
		
		ArrayList<Queue<? extends Passenger>> queuesAtFirstClassService = new ArrayList<>();
		queuesAtFirstClassService.add(firstClassQueue);
		queuesAtFirstClassService.add(coachQueue);
		
		firstClassServiceStation1 = new ServiceStation(queuesAtFirstClassService, fcMaxSerTime, "First Class Service Station_1");
		firstClassServiceStation2 = new ServiceStation(queuesAtFirstClassService, fcMaxSerTime, "First Class Service Station_2");
		
		
		ArrayList<Queue<? extends Passenger>> queuesAtCoachService = new ArrayList<>();
		queuesAtCoachService.add(coachQueue);
		
		coachServiceStation1 = new ServiceStation(queuesAtCoachService, coachMaxSerTime, "Coach Service Station_1");
		coachServiceStation2 = new ServiceStation(queuesAtCoachService, coachMaxSerTime, "Coach Service Station_2");
		coachServiceStation3 = new ServiceStation(queuesAtCoachService, coachMaxSerTime, "Coach Service Station_3");
		
		stationList = new ArrayList<>(5);
		stationList.add(firstClassServiceStation1);
		stationList.add(firstClassServiceStation2);
		stationList.add(coachServiceStation1);
		stationList.add(coachServiceStation2);
		stationList.add(coachServiceStation3);
	}
	
	public void startSimulator(){
		firstClassGenerator.start();
		coachGenerator.start();
		
		firstClassServiceStation1.start();
		firstClassServiceStation2.start();
		coachServiceStation1.start();
		coachServiceStation2.start();
		coachServiceStation3.start();
		
		Timer timer = new Timer();
		CutOffTask task = new CutOffTask();
		timer.schedule(task, runningTime * 1000);
	}
	
	public void stopSimulator(){
		running = false;
		int totalSerTime = 0;
		int maxSerTime = 0;
		for(ServiceStation sta : stationList){
			totalSerTime += sta.getTotalServiceTime();
			
			maxSerTime = (sta.actualMaxServiceTime > maxSerTime ? sta.actualMaxServiceTime : maxSerTime);
		}
		
		
		int coachServed = coachQueue.servedNum;
		int firstServed = firstClassQueue.servedNum;
		
		
		int avgSerTime = (int) ((float)totalSerTime / (coachServed + firstServed));
		
		
		System.out.println("\n\n************* statistic *************");
		System.out.println("Running time (s): " + runningTime);
		System.out.println("Avg. service time: " + avgSerTime);
		System.out.println("Max. service time: " + maxSerTime);
		System.out.println("First Class Served: " + firstServed);
		System.out.println("Coach Served: " + coachServed);
		
		System.out.println("Max. First Class queue length: " + firstClassQueue.getMaxSize());
		System.out.println("Max. Coach queue length: " + coachQueue.getMaxSize());
		
		
		System.exit(0);
	}
	
	class CutOffTask extends TimerTask{
		
		@Override
		public void run() {
			stopSimulator();
		}
		
	}
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.printf("Defaut configuration is:\n"
				+ " Avg. arrival rate for first class: %d sec\n"
				+ " Avg. arrival rate for coach: %d sec\n"
				+ " Max. service time for first class: %d sec\n"
				+ " Max. service time for coach: %d sec\n"
				+ " Running time: %d sec\n\n",
				fcAvgArrivalRate, coachAvgArrivalRate, fcMaxSerTime, coachMaxSerTime, runningTime);
		
		String yn = null;
		
		while(!"y".equals(yn) && !"n".equals(yn)){
			System.out.println("Do you want to reconfigurate? (y/n)");
			yn = sc.nextLine();
		}
		
		if ("y".equals(yn)) {
			System.out.println("Input average arrival rate for first class passenger (sec): ");
			fcAvgArrivalRate = sc.nextInt();

			System.out.println("Input average arrival rate for coach (sec): ");
			coachAvgArrivalRate = sc.nextInt();

			System.out.println("Input maximum service time for first class passenger (sec): ");
			fcMaxSerTime = sc.nextInt();

			System.out.println("Input maximum service time for coach (sec): ");
			coachMaxSerTime = sc.nextInt();

			System.out.println("Input how long the simulator will be running (sec): ");
			runningTime = sc.nextInt();
		}
		
		sc.close();
		
		System.out.println("\n************* Simulation starts *************\n");
		
		AirlineCheckInSimulator simulator = new AirlineCheckInSimulator();
		simulator.startSimulator();
		
	}

}
