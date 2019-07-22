package Facility;

public class Facility_TableTennis extends Facility {

    public Facility_TableTennis(String courtId){
        super(courtId);
    }

    @Override
    public double getPrice() {
        return 29;
    }

    @Override
    public String getFacilityType() {
        return "table tennis court";
    }
}
