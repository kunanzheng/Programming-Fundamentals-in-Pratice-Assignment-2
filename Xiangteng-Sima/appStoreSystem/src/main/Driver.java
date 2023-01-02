package main;

import controllers.AppStoreAPI;
import controllers.DeveloperAPI;
import models.*;
import utils.ScannerInput;
import utils.Utilities;

public class Driver {

    private DeveloperAPI developerAPI = new DeveloperAPI();
    private AppStoreAPI appStoreAPI = new AppStoreAPI();

    public static void main(String[] args) {
        new Driver().start();
    }

    public void start() {
        loadAllData();
        runMainMenu();
    }

    private int mainMenu() {
        System.out.println("""
                 -------------App Store------------
                |  1) Developer - Management MENU  |
                |  2) App - Management MENU        |
                |  3) Reports MENU                 |
                |----------------------------------|
                |  4) Search                       |
                |  5) Sort                         |
                |----------------------------------|
                |  6) Recommended Apps             |
                |  7) Random App of the Day        |
                |  8) Simulate ratings             |
                |----------------------------------|
                |  20) Save all                    |
                |  21) Load all                    |
                |----------------------------------|
                |  0) Exit                         |
                 ----------------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runMainMenu() {
        int option = mainMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runDeveloperMenu();
                case 2 -> runAppMenu();
                case 3 -> runReportMenu();
                case 4 -> searchAppsBySpecificCriteria();
                case 5 -> sortAppByName();
                case 6 -> listRecommendedApps();
                case 7 -> listRandomApps();
                case 8 -> simulateRatings();
                case 20 -> saveAllData();
                case 21 -> loadAllData();
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = mainMenu();
        }
        exitApp();
    }

    private void exitApp() {
        saveAllData();
        System.out.println("Exiting....");
        System.exit(0);
    }

    //--------------------------------------------------
    //  Developer Management - Menu Items
    //--------------------------------------------------
    private int developerMenu() {
        System.out.println("""
                 -------Developer Menu-------
                |   1) Add a developer       |
                |   2) List developer        |
                |   3) Update developer      |
                |   4) Delete developer      |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runDeveloperMenu() {
        int option = developerMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addDeveloper();
                case 2 -> System.out.println(developerAPI.listDevelopers());
                case 3 -> updateDeveloper();
                case 4 -> deleteDeveloper();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = developerMenu();
        }
    }

    private void addDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        String developerWebsite = ScannerInput.validNextLine("Please enter the developer website: ");

        if (developerAPI.addDeveloper(new Developer(developerName, developerWebsite))) {
            System.out.println("Add successful");
        } else {
            System.out.println("Add not successful");
        }
    }

    private void updateDeveloper() {
        System.out.println(developerAPI.listDevelopers());
        Developer developer = readValidDeveloperByName();
        if (developer != null) {
            String developerWebsite = ScannerInput.validNextLine("Please enter new website: ");
            if (developerAPI.updateDeveloperWebsite(developer.getDeveloperName(), developerWebsite))
                System.out.println("Developer Website Updated");
            else
                System.out.println("Developer Website NOT Updated");
        } else
            System.out.println("Developer name is NOT valid");
    }

    private void deleteDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        if (developerAPI.removeDeveloper(developerName) != null) {
            System.out.println("Delete successful");
        } else {
            System.out.println("Delete not successful");
        }
    }

    private Developer readValidDeveloperByName() {
        String developerName = ScannerInput.validNextLine("Please enter the developer's name: ");
        if (developerAPI.isValidDeveloper(developerName)) {
            return developerAPI.getDeveloperByName(developerName);
        } else {
            return null;
        }
    }

    private int appMenu(){
        System.out.println("""
                 -------App Store Menu-------
                 |  1) Add an app            |
                 |  2) Update app            |
                 |  3) Delete app            |
                 |  0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runAppMenu(){
        int option = appMenu();
        while(option != 0 ){
            switch (option) {
                case 1 -> addApp();
                case 2 -> updateApp();
                case 3 -> deleteApp();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = appMenu();
        }
    }

    private void addApp(){
        boolean isAdded = false;

        int option = ScannerInput.validNextInt("""
                                 -----------------------------
                                 | 1) Add a EducationApp     |
                                 | 2) Add a GameApp          |
                                 | 3) Add a ProductivityApp  |
                                 -----------------------------
                                 ==>> """);
        switch (option){
            case 1 ->{
                Developer developer = readValidDeveloperByName();
                String appName = ScannerInput.validNextLine("Please enter the name of app: ");
                double appSize = ScannerInput.validNextDouble("Please enter the size of app: ");
                double appVersion = ScannerInput.validNextDouble("Please enter the version of app: ");
                double appCost = ScannerInput.validNextDouble("Please enter the cost of app: ");
                int level = ScannerInput.validNextInt("Please enter the level of app: ");
                isAdded = appStoreAPI.addApp(new EducationApp(developer,appName,appSize,appVersion,appCost,level));
            }

            case 2 ->{
                Developer developer = readValidDeveloperByName();
                String appName = ScannerInput.validNextLine("Please enter the name of app: ");
                double appSize = ScannerInput.validNextDouble("Please enter the size of app: ");
                double appVersion = ScannerInput.validNextDouble("Please enter the version of app: ");
                double appCost = ScannerInput.validNextDouble("Please enter the cost of app: ");
                boolean isMultiplayer = Utilities.YNtoBoolean(ScannerInput.validNextChar("Does the game support more players?[y/n]: "));
                isAdded = appStoreAPI.addApp(new GameApp(developer,appName,appSize,appVersion,appCost,isMultiplayer));
            }
            case 3->{
                Developer developer = readValidDeveloperByName();
                String appName = ScannerInput.validNextLine("Please enter the name of app: ");
                double appSize = ScannerInput.validNextDouble("Please enter the size of app: ");
                double appVersion = ScannerInput.validNextDouble("Please enter the size of app: ");
                double appCost = ScannerInput.validNextDouble("Please enter the cost of app: ");
                isAdded = appStoreAPI.addApp(new ProductivityApp(developer,appName,appSize,appVersion,appCost));
            }
        }
    }

    private void updateApp(){
        if (appStoreAPI.numberOfApps() > 0){
            boolean isUpdated = false;

            int option = ScannerInput.validNextInt("""
                    --------------------------------
                    | 1) Update a EducationApp     |
                    | 2) Update a GameApp          |
                    | 3) Update a ProductivityApp  |
                    --------------------------------
                    ==>> """);
            switch (option){
                case 1->{
                    showEducationApp();
                    if (appStoreAPI.numberOfEducationApps() > 0){
                        int appIndex = ScannerInput.validNextInt("Enter the index of the app to update ==> ");
                        if (appStoreAPI.isValidIndex(appIndex)){
                            Developer developer = readValidDeveloperByName();
                            String appName = ScannerInput.validNextLine("Enter the name of app: ");
                            double appSize = ScannerInput.validNextDouble("Enter the size of app: ");
                            double appVersion = ScannerInput.validNextDouble("Enter the version of app: ");
                            double appCost = ScannerInput.validNextDouble("Enter the cost of app: ");
                            int level = ScannerInput.validNextInt("Enter the level of app: ");
                            isUpdated = appStoreAPI.updateEducationApp(appIndex,developer,appName,appSize,appVersion,appCost,level);
                        }
                    }
                }
                case 2->{
                    showGameApp();
                    if (appStoreAPI.numberOfGameApps() > 0){
                        int appIndex = ScannerInput.validNextInt("Enter the index of the app to update ==> ");
                        if (appStoreAPI.isValidIndex(appIndex)){
                            Developer developer = readValidDeveloperByName();
                            String appName = ScannerInput.validNextLine("Enter the name of app: ");
                            double appSize = ScannerInput.validNextDouble("Enter the size of app: ");
                            double appVersion = ScannerInput.validNextDouble("Enter the version of app: ");
                            double appCost = ScannerInput.validNextDouble("Enter the cost of app: ");
                            boolean isMultiplayer = Utilities.YNtoBoolean(ScannerInput.validNextChar("Does the game support more players?[y/n]: "));
                            isUpdated = appStoreAPI.updateGameApp(appIndex,developer,appName,appSize,appVersion,appCost,isMultiplayer);
                        }
                    }
                }
                case 3->{
                    showProductivityApps();
                    if (appStoreAPI.numberOfProductivityApp() > 0){
                        int appIndex = ScannerInput.validNextInt("Enter the index of the app to update ==> ");
                        Developer developer = readValidDeveloperByName();
                        String appName = ScannerInput.validNextLine("Enter the name of app: ");
                        double appSize = ScannerInput.validNextDouble("Enter the size of app: ");
                        double appVersion = ScannerInput.validNextDouble("Enter the version of app: ");
                        double appCost = ScannerInput.validNextDouble("Enter the cost of app: ");
                        isUpdated = appStoreAPI.updateProductivityApp(appIndex,developer,appName,appSize,appVersion,appCost);
                    }
                }
            }
        }
    }

    private void deleteApp(){
        showApps();
        if (appStoreAPI.numberOfApps() > 0){
            int indexToDelete = ScannerInput.validNextInt("Enter the index of the app to delete ==> ");
            App appToDelete = appStoreAPI.deleteAppByIndex(indexToDelete);
            if (appToDelete != null){
                System.out.println("Delete Successful! ");
            }else {
                System.out.println("Delete Not Successful");
            }
        }
    }

    private void showApps(){
        System.out.println("List of all apps are: ");
        System.out.println(appStoreAPI.listAllApps());
    }

    private void showEducationApp(){
        System.out.println("List of Education apps are: ");
        System.out.println(appStoreAPI.listAllEducationApps());
    }

    private void showProductivityApps(){
        System.out.println("List of Productivity apps are: ");
    }

    private void showGameApp(){
        System.out.println("List of Game apps are: ");
        System.out.println(appStoreAPI.listAllGameApps());
    }

    private void runReportMenu(){
        if (appStoreAPI.numberOfApps() > 0){
            int option = ScannerInput.validNextInt("""
                    -----------------------------
                    | 1) list all app           |
                    | 2) list Education App     |
                    | 3) list Game App          |
                    | 4) list productivity app  |
                    | 0) RETURN to main menu    |
                    -----------------------------
                    ==>> """);

            switch (option){
                case 1 -> listAllApps();
                case 2 -> listEducationApps();
                case 3 -> listGameAPPs();
                case 4 -> listProductivityApps();
                default -> System.out.println("Invalid option entered: " + option);
            }
        }else {
            System.out.println("Option Invalid - No Apps stored");
        }
    }

    private void listAllApps(){
        System.out.println("List of all apps are here: ");
        System.out.println(appStoreAPI.listAllApps());
    }

    private void listEducationApps(){
        System.out.println("List of Education apps are here: ");
        System.out.println(appStoreAPI.listAllEducationApps());
    }

    private void listGameAPPs(){
        System.out.println("List of Game apps are here: ");
        System.out.println(appStoreAPI.listAllGameApps());
    }

    private void listProductivityApps(){
        System.out.println("List of Productivity apps are here: ");
        System.out.println(appStoreAPI.listAllProductivityApps());
    }

    private void sortAppByName(){
        appStoreAPI.sortAppsByNameAscending();
    }

    private void listRecommendedApps(){
        System.out.println(appStoreAPI.listAllRecommendedApps());
    }

    private void listRandomApps(){
        System.out.println("---App of the Day---");
        System.out.println(appStoreAPI.randomApp());
    }


    //--------------------------------------------------
    // TODO UNCOMMENT THIS CODE as you start working through this class
    //--------------------------------------------------
    private void searchAppsBySpecificCriteria() {
        System.out.println("""
                What criteria would you like to search apps by:
                  1) App Name
                  2) Developer Name
                  3) Rating (all apps of that rating or above)""");
        int option = ScannerInput.validNextInt("==>> ");
        switch (option) {
            // TODO Search methods below
             case 1 -> searchAppsByName();
             case 2 -> searchAppsByDeveloper(readValidDeveloperByName());
             case 3 -> searchAppsEqualOrAboveAStarRating();
             default -> System.out.println("Invalid option");
        }
    }

    private void searchAppsByName(){
        System.out.println(appStoreAPI.listAllAppsByName(ScannerInput.validNextLine("Please enter the app name you want search: ")));
    }

    private void searchAppsByDeveloper(Developer developer){
        System.out.println("A total of " + appStoreAPI.numberOfAppsByChosenDeveloper(developer) + " results were found");
        System.out.println("The result is " + appStoreAPI.listAllAppsByChosenDeveloper(developer));
    }

    private void searchAppsEqualOrAboveAStarRating(){
        System.out.println("You must enter a rating greater than 1 and less than 5!");
        System.out.println(appStoreAPI.listAllAppsAboveOrEqualAGivenStarRating(ScannerInput.validNextInt("Please enter the rating you want search:")));
    }

    //--------------------------------------------------
    // TODO UNCOMMENT THIS COMPLETED CODE as you start working through this class
    //--------------------------------------------------
    private void simulateRatings() {
        // simulate random ratings for all apps (to give data for recommended apps and reports etc).
        if (appStoreAPI.numberOfApps() > 0) {
            System.out.println("Simulating ratings...");
            appStoreAPI.simulateRatings();
            System.out.println(appStoreAPI.listSummaryOfAllApps());
        } else {
            System.out.println("No apps");
        }
    }

    //--------------------------------------------------
    //  Persistence Menu Items
    //--------------------------------------------------

    private void saveAllData() {
        try{
            System.out.println("Saving to file: " + appStoreAPI.fileName());
            appStoreAPI.save();
        }catch (Exception e){
            System.out.println("Error writing to file: " + e);
        }
    }

    private void loadAllData() {
        try {
            System.out.println("Loading from file: " + appStoreAPI.fileName());
            appStoreAPI.load();
        }catch (Exception e){
            System.out.println("Error reading from file: " + e);
        }
    }

}

