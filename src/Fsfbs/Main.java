package Fsfbs;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import Exception.ExIOErrorinGetConfig;
import Util.UtilTime;
import Util.UtilValidation;

public class Main {
 public static void main(String[] args) throws IOException, ExIOErrorinGetConfig
 {
     try {
         Controller controller = Controller.getInstance();
         Scanner in = new Scanner(System.in);
         UtilTime utilTime = UtilTime.getTimeInstance();

         //Step 1: Import Data
         if(SimulationMode.getSimulationMode())
         {
        	simwelcome();
            System.out.println("+----------------Debug Message Start----------------+");
         }
         controller.importData();
         if(SimulationMode.getSimulationMode())
         System.out.println("+-------------------Debug Message End----------------+");
         welcome();

         //Step 2: Choose either login or create user
         loginOrCreateUser();
         User user = User.Login();
         label:
         while (true) {
             TimeUnit.SECONDS.sleep(3);
             user.getFastRecommandation(utilTime);
             userguide();
             String input = in.next();
             switch (input) {
                 case "vacancy":
                     controller.printAllFacilities();
                     System.out.println("\nPlease enter the key of Sport Centre:");
                     String sportCentre = in.next();
                     System.out.println("\nPlease enter the type of court you want to book:");
                     System.out.println("e.g. badminton, tableTennis, activityRoom");
                     String court;
                     court = in.next();
                     while (!(court.equals("badminton") || court.equals("tableTennis") || court.equals("activityRoom"))) {
                         System.out.println("Wrong court information entered.");
                         System.out.println("\nPlease enter: badminton, tableTennis, activityRoom");
                         court = in.next();
                     }
                     user.searchVacancies(sportCentre, court);
                     break;
                 case "add":
                     UtilValidation utilValidation = UtilValidation.getValidationInstance();
                     String time;
                     System.out.println("Please enter the facility code(e.g. E1B1, E1B2)");
                     String sportfacility = in.next();
                     System.out.println("Please enter time range");
                     time = in.next();
                     int iTime = utilValidation.validateTimeFormat(time);
                     if (iTime == 0) {
                         System.out.println("Time in wrong format!");
                     } else
                         user.addBooking(sportfacility, iTime, utilTime);
                     break;
                 case "print":
                     controller.printAllFacilities();
                     break;
                 case "mybooking":
                     user.printTodayBookingHistory(utilTime);
                     break;
                 case "delete":
                     System.out.println("\nPlease enter the booking ID you want to delete");
                     user.printTodayBookingHistory(utilTime);
                     user.deleteBooking(in.next(), utilTime);
                     break;
                 case "exit":
                     break label;
                 default:
                     System.out.println("\nCommand is not found.");
                     break;
             }
         }

     }
     catch (Exception e){
         System.out.println(e.getMessage());
     }
     finally {
    	 Controller controller = Controller.getInstance();
    	 System.out.println("Program end");
         //Last Step: export to txt file and end the program.
    	 if(!SimulationMode.getSimulationMode())
    		 controller.exportAllSchedule();
    		 controller.exportAllMembeer();
    		 }

     }
 

 private static void loginOrCreateUser() {
     String userinput;
     while (true) {
         Scanner in = new Scanner(System.in);
         userinput = in.next();
         //Step 2: Log In or Create User
         switch (userinput) {
             case "Y":
                 return;
             case "N":
                 User.setUpAC(); //setupAccount

                 return;
             default:
                 System.out.println("Invalid input. Input should be Y or N. Please input again.");
                 break;
         }
     }
 }

 private static void welcome() {
	//welcome message
     System.out.println("+-------------------------------------------------------------------+");
     System.out.println("||  +      + +----+ +      +-----+ +-----+  +-+  +-+  +----+   ++  ||");
     System.out.println("||  |      | |      |      |       |     |  | +--+ |  |        ||  ||");
     System.out.println("||  |  +   | +---+  |      |       |     |  |  ++  |  +---+    ++  ||");
     System.out.println("||  |  |   | |      |      |       |     |  |      |  |            ||");
     System.out.println("||  +--+---+ +----+ +----+ +-----+ +-----+  +      +  +----+   ++  ||");
     System.out.println("||        Welcome to Fast Sports Facility Booking System!          ||");
     System.out.println("+-------------------------------------------------------------------+");
     System.out.println("=================Do you have an User Account? (Y/N)==================");
 }
 private static void simwelcome() {
 	System.out.println("+---------------------------------------------------+");
 	System.out.println("+------------------Simulation Mode------------------+");
 	System.out.println("|Notice:                                            |");
 	System.out.println("|In this simulation mode, all user output will not  |");
 	System.out.println("|affect the data. If you want change to production  |");
     System.out.println("|mode, please change the RunConfigurationMode in the|");
 	System.out.println("|configuration file to 1.                           |");
 	System.out.println("|Sorry for causing any inconvenient        Thank you|");
     System.out.println("+---------------------------------------------------+");

 }
 private static void userguide() throws InterruptedException {
     System.out.println("+-------------------------------------------------------------------+");
     System.out.println("|---------------------------User Guide------------------------------|");
     System.out.println("|                   Add booking: please enter 'add'                 |");
     System.out.println("|                Delete booking: please enter 'delete'              |");
     System.out.println("|           Print all sport centre: please enter 'print'            |");
     System.out.println("|             Search vacancies: please enter 'vacancy'              |");
     System.out.println("|             Print my booking: please enter 'mybooking'            |");
     System.out.println("|                     exit: please enter 'exit'                     |");
     System.out.println("+-------------------------------------------------------------------+");
 }

}
