
public class FirstClassGenerator extends PassengerGenerator<FirstClassPsgr> {
	int no = 0;
	public FirstClassGenerator(Queue<FirstClassPsgr> queue, int avgArrivalRate) {
		super(queue, avgArrivalRate);
	}

	@Override
	protected FirstClassPsgr generateNew() {
		FirstClassPsgr f = new FirstClassPsgr();
		f.name = "FirstClass-" + ++no;
		
		return f;
	}

}
