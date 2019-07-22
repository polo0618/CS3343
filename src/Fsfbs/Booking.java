package Fsfbs;

import java.io.IOException;

import Util.UtilTime;

public class Booking {

	private String userName;
	private String bookingID;
	private int bookingTime;
	private String facilitiesID;

	public Booking (String userName, int bookingTime, String facilitiesID,UtilTime utilTime) throws IOException{
	    this.userName = userName;
	    this.bookingID = createBookingID(bookingTime,facilitiesID,utilTime);
	    this.bookingTime = bookingTime;
	    this.facilitiesID = facilitiesID;
    }

    private String createBookingID(int bookingTime, String facilitiesID, UtilTime utilTime) throws IOException{
	    return (utilTime.getCurrentDate() + facilitiesID + bookingTime);
    }


    public String getBookingID() {
        return bookingID;
    }

    public String getuserName() {
        return userName;
    }

    public String getFacilitiesID() {
        return facilitiesID;
    }

    public int getBookingTime() {
        return bookingTime;
    }
}
