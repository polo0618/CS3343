package Membership;

public class Membership_Senior implements Membership{
	private static final double discount = 0.5;
	private static Membership_Senior instance = new Membership_Senior();

	private Membership_Senior() {
		
	}

	public static Membership_Senior getInstance() {
		return instance;
	}
	public double getDiscount() {
		return discount;
	}
	public String getMembershipName() { return "Membership_Senior"; }
}
