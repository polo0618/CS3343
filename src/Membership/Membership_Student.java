package Membership;

public class Membership_Student implements Membership{
	private static final double discount = 0.5;
	private static Membership_Student instance = new Membership_Student();

	private Membership_Student() {
		
	}

	public static Membership_Student getInstance() {
		return instance;
	}
	
	public double getDiscount() {
		return discount;
	}

	public String getMembershipName() { return "Membership_Student"; }
	}

