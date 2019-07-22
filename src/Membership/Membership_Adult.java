package Membership;

public class Membership_Adult implements Membership{
private static final double discount = 1;
private static Membership_Adult instance = new Membership_Adult();

private Membership_Adult() {
	
}

public static Membership_Adult getInstance() {
	return instance;
}

public double getDiscount() {
	return discount;
}

public String getMembershipName() { return "Membership_Adult"; }
}
 