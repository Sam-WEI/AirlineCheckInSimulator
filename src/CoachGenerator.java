
public class CoachGenerator extends PassengerGenerator<Coach> {
	int no = 0;
	
	public CoachGenerator(Queue<Coach> queue, int avgArrivalRate) {
		super(queue, avgArrivalRate);
	}

	@Override
	protected Coach generateNew() {
		Coach c = new Coach();
		c.name = "Coach-" + ++no;
		
		return c;
	}

}
