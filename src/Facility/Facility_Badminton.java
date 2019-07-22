package Facility;

public class Facility_Badminton extends Facility {

    public Facility_Badminton(String facilityId){
        super(facilityId);
    }

    @Override
    public double getPrice() {
        return 59;
    }

    @Override
    public String getFacilityType() {
        return "badminton court";
    }
}
