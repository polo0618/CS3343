package Fsfbs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import Exception.ExBookingNotExist;
import Exception.ExFullBooking;
import Exception.ExIOErrorinGetConfig;
import Exception.ExMaxFailLogin;
import Exception.ExMemberShipFilePathNotExist;
import Facility.Facility;
import Membership.Membership;
import Membership.Membership_Adult;
import Membership.Membership_Senior;
import Membership.Membership_Student;
import Util.UtilTime;
import Util.UtilValidation;
import Util.UtilsExport;
import Util.UtilsLoadconfig;

import java.util.Map;

public class User {
	private String userName;
    private String userPassword;
    private Membership membership = null;
    private String preferSportCentre;
    private String preferFacilities;
    private Map<String, Booking> todayBooking;

    public User(String userName, String userPassword, String mem, String preferSportCentre, String preferFacilities) {
        this.userName = userName;
        this.userPassword = userPassword;

        switch (mem) {
            case "Membership_Adult":
                this.membership = Membership_Adult.getInstance();
                break;
            case "Membership_Student":
                this.membership = Membership_Student.getInstance();
                break;
            case "Membership_Senior":
                this.membership = Membership_Senior.getInstance();
                break;
        }

        this.preferSportCentre = preferSportCentre;
        this.preferFacilities = preferFacilities;
        todayBooking = new HashMap<>();
    }

    //login----------------------------------------------------------------------------------------
    public static User Login() throws ExMemberShipFilePathNotExist, ExIOErrorinGetConfig, ExMaxFailLogin {
        try {
            //variable initialization
            String username, password;
            Scanner in = new Scanner(System.in); //user input
            UtilValidation utilValidation = UtilValidation.getValidationInstance();
            int failureCount = 0;

            System.out.println("+-------------------------------------------------------------------+");
            System.out.println("|                            LOG IN FSFBS                           |");
            System.out.println("+-------------------------------------------------------------------+");

            //login procedure
            while (true) {
                System.out.println("Please enter user name:");
                username = in.next();
                if (utilValidation.existedAC(username))
                    break;
                else
                    System.out.println("Invalid username. Please enter again :");

            }
            //get Password
            //read file
        /*
        String filepath = UtilsLoadconfig.getConfig("membershipFilePath") + username + ".txt";
        File file = new File(filepath);
        Scanner inFile = new Scanner(file);
        String ac = inFile.next();
        String correctpass = inFile.next(); 

        while (true) {
            //user input
            System.out.println("Your username: " + ac);
            System.out.println("Please enter password:");
            password = in.next();
            if (password.equals(correctpass)) {
                System.out.println("Login Success.");
                return true;
            } else
                System.out.println("Invalid password. Please enter again.");
                failureCount+=1;
                if (failureCount >= 3){
                    throw new ExMaxFailLogin();
                }
        }
        */
            User user = Controller.getInstance().getUserbyID(username);
            while (true) {
                //user input
                System.out.println("Your username: " + user.getUserName());
                System.out.println("Please enter password:");
                password = in.next();
                if (password.equals(user.getUserPassword())) {
                    System.out.println("Login Success. Loading...");
                    return user;
                } else
                    System.out.println("Invalid password. Please enter again.");
                failureCount+=1;
                if (failureCount >= 3){
                    throw new ExMaxFailLogin();
                }
            }
        }
        catch(FileNotFoundException fe){
            throw new ExMemberShipFilePathNotExist();
        }
        catch(IOException ioe){
            throw new ExIOErrorinGetConfig();
        }
    }

    //Setup Account ----------------------------------------------------------------------------------------
    public static void setUpAC() {
        try {
            Scanner in = new Scanner(System.in);
            String[] temp = new String[5];
            String password;
            String inputSc;
            String type;
            String type_fullName = "";
            String inputAc;
            String membershipName;
            Controller controller = Controller.getInstance();
            UtilValidation utilValidation = UtilValidation.getValidationInstance();

            //User account set up
            System.out.println("Please enter your preferred userName:");
            inputAc = in.nextLine();
            while (utilValidation.existedAC(inputAc)) {
                System.out.println("Account already exist. Please input again.");
                inputAc = in.nextLine();
            }

            //User password
            password = validatePassword();

            //set membership
            membershipName = getMembershipbyAge().getMembershipName();

            //set SportCentre
            controller.printAllFacilities();
            System.out.println("Please enter your prefer Sport Centre:");
            inputSc = in.next();
            while (controller.searchSportCentre(inputSc) == null) {
                System.out.println("Your input sport centre does not exist. Please input again.");
                inputSc = in.next();
            }

            //set Facilities

            System.out.println("Please enter your prefer Facility:");
            System.out.println("B: Badminton, A: ActivityRoom, T:TableTennis");
            type = in.next();
            while (!(type.equals("B") || type.equals("A") || type.equals("T"))) {
                System.out.println("Your input facility does not exist. Please input again.");
                type = in.next();
            }
            switch (type) {
                case "B":
                    type_fullName = "badminton";
                    break;
                case "A":
                    type_fullName = ("tableTennis");
                    break;
                case "T":
                    type_fullName = ("activityRoom");
                    break;
            }

            //User Profile setup
            temp[0] = inputAc;
            temp[1] = password;
            temp[2] = membershipName;
            //temp[2] = this.membership.getClass().getSimpleName();
            temp[3] = inputSc;
            temp[4] = type_fullName;

            //Create User object and add to controller
            User newuser = new User(inputAc, password, membershipName, inputSc, type_fullName);
            controller.addUser(newuser);

            UtilsExport.printToFile(UtilsLoadconfig.getConfig("membershipFilePath") + inputAc + ".txt", temp);
            System.out.println("Create User Success!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //getter and setter...-----------------------------------------------------------------------------------
    public String getUserName() {
        return userName;
    }

    public Membership getMembership() {
        return membership;
    }

    private String getUserPassword() {
        return userPassword;
    }

    public int getTodayBookingNum() {
		return todayBooking.size();
	}


    //-----------------validate User Pw-------------------
    private static String validatePassword() {
        Scanner in = new Scanner(System.in);
        String password;
        //User password set up
        while (true) {
            System.out.println("Please enter your password:");
            password = in.next();
            System.out.println("Please enter your password again.");
            if (password.equals(in.next())) {
                return password;
            } else
                System.out.println("Your password does not match with what you previously entered. Please enter again!");
        }
    }
//-----------------validate User Age-------------------

    private static Membership getMembershipbyAge() {
        Scanner in = new Scanner(System.in);
        int age;
        while (true) {
            System.out.println("Please enter your age:");
            age = in.nextInt();
            if (age > 0) {
                if (age <= 18)
                    return (Membership_Student.getInstance());
                else if (age <= 60)
                    return (Membership_Adult.getInstance());
                else return (Membership_Senior.getInstance());
            } else
                System.out.println("Invalid input entered. Please enter a number.");
        }
    }


    public void addBooking(String inputFacilitiesId, int time, UtilTime utilTime) {
        try {
            Controller controller = Controller.getInstance();
            //Step 1: Validate Input
            SportCentre sc;
            Facility facility;
            sc = controller.searchSportCentre(inputFacilitiesId.substring(0, 2));
            facility = controller.searchFacility(inputFacilitiesId);
            //Step 2: check if user exceed the maximum booking limit
            boolean todayBkBelow3 = todayBooking.size() < 3;
            //Step 3:
            if (todayBkBelow3) {
                boolean canBook = facility.canBook(time,utilTime);
                if (canBook) {
                    Booking booking = new Booking(userName, time, facility.getFacilityId(),UtilTime.getTimeInstance());
                    facility.addToTimeTable(time, booking.getBookingID());
                    todayBooking.putIfAbsent(booking.getBookingID(), booking);
                    System.out.println();
                    System.out.println("-----------------Booking Confirmation--------------------");
                    System.out.println("Booking ID: " + booking.getBookingID());
                    System.out.println("Sport Centre: " + sc.getScName());
                    System.out.println("Facility Type: " + facility.getFacilityType());
                    System.out.println("Facility ID: " + booking.getFacilitiesID());
                    System.out.println("Price: " + getPriceByMembership(facility.getPrice()));
                    System.out.println("--------------------------End----------------------------");
                    System.out.println();
                } else {
                    throw new ExFullBooking(sc.getScName(), facility.getFacilityType(), time);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteBooking(String bookingId, UtilTime utilTime) {
        try {
            Controller controller = Controller.getInstance();
            Booking booking = searchBookingById(bookingId);
            int bookedTimeSlot = booking.getBookingTime();
            Facility facility;
            if (Facility.canDelete(bookedTimeSlot,utilTime)){
                todayBooking.remove(bookingId);
                facility = controller.searchFacility(booking.getFacilitiesID());
                facility.removeFromTimeTable(booking.getBookingTime());
                System.out.println();
                System.out.println("-------------------Delete Confirmation-------------------");
                System.out.println("User ID: " + booking.getuserName());
                System.out.println("Booking ID: " + booking.getBookingID());
                System.out.println("Facility Type: " + facility.getFacilityType());
                System.out.println("Facility ID: " + booking.getFacilitiesID());
                System.out.println("Booking with id: " + bookingId + " has been deleted.");
                System.out.println("--------------------------End----------------------------");
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void printTodayBookingHistory(UtilTime utilTime){
        System.out.println("----------------------------Booking History--------------------------");
        if (todayBooking.size() == 0){
            System.out.println();
            System.out.println("               There is no bookings currently.");
            System.out.println();
        }
        else {
            for (Booking booking : todayBooking.values()) {
                System.out.println("  Booking ID: " + booking.getBookingID() + " Court ID: " + booking.getFacilitiesID() + " Time: " + utilTime.getTimeWithFormat(booking.getBookingTime()));
            }
        }
        System.out.println("---------------------------------End---------------------------------");

    }

    public void getFastRecommandation(UtilTime utilTime){
        FastRecommendationAlgorithm fra = FastRecommendationAlgorithm.getInstance();
        fra.fastRecommendation(preferSportCentre,preferFacilities,utilTime);
    }



    private Booking searchBookingById(String bookingId) throws ExBookingNotExist {
        Booking booking = todayBooking.get(bookingId);
        if (booking == null) {
            throw new ExBookingNotExist();
        }
        return booking;
    }

    public void searchVacancies(String scId, String sfType){
        try {
            Controller controller = Controller.getInstance();
            SportCentre sc = controller.searchSportCentre(scId);
            System.out.println("\nSport Centre: " + sc.getScName()+ "\n");
            ArrayList<Facility> facilityList =controller.searchFacilitiesByType(sc,sfType);
            for (Facility fac: facilityList){
                System.out.println("Facility Code: " + fac.getFacilityId());
                fac.showVacancies();
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private double getPriceByMembership(double price){
        return membership.getDiscount() * price;
    }

    public void exportBooking() throws IOException {
    	UtilsExport.printToFile(UtilsLoadconfig.getConfig("bookingFilePath") + this.getUserName() + ".txt", "");
        for (String key : todayBooking.keySet()) {
            UtilsExport.appendToFile(UtilsLoadconfig.getConfig("bookingFilePath") + this.getUserName() + ".txt", todayBooking.get(key).getBookingID());
        }
    }

    public void importBooking() throws IOException {
        String filepath = UtilsLoadconfig.getConfig("bookingFilePath") + this.getUserName() + ".txt";
        File file = new File(filepath);
        Scanner in = new Scanner(file);
        if (file.exists()) {
            while (in.hasNext()) {
                String input = in.next();
                Booking temp = new Booking(this.getUserName(), Integer.parseInt(input.substring(12, 16)), input.substring(8, 12),UtilTime.getTimeInstance());
                todayBooking.put(temp.getBookingID(), temp);
            }
        }
    }
    
}
