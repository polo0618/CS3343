package Facility;

public class Facility_ActivityRoom extends Facility {

    public Facility_ActivityRoom(String facilityId){
        super(facilityId);
    }

    @Override
    public double getPrice() {
        return 99;
    }

    @Override
    public String getFacilityType() {
        return "activity room";
    }
}
