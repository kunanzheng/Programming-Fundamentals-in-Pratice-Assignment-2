package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.random;
import static utils.RatingUtility.generateRandomRating;

public class AppStoreAPI {
    private ArrayList<App> apps = new ArrayList<App>();

    /*
    * method addApp
    * adds an object to the ArrayList apps and returns a boolean judgment as to whether it was successful
    * */
    public boolean addApp(App app) {
        return apps.add(app);
    }

    /*
    * method listAllApps
    * List all apps in the ArrayList. If the ArrayList is empty, return"No apps"
    * */
    public String listAllApps() {
        if (apps.isEmpty()) return "No apps";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < apps.size(); i++) {
            sb.append(i).append(": ").append(apps.get(i)).append('\n');
        }
        return sb.toString();
    }

    /*
    * method ListSummaryOfAllApps
    * List all the app summaries, if the ArrayList is empty, prints "No apps"
    * */
    public String listSummaryOfAllApps() {
        if (apps.isEmpty()) {
            return "No apps";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < apps.size(); i++) {
            sb.append(i).append(": ").append(apps.get(i).appSummary()).append('\n');
        }
        return sb.toString();
    }

    /*
    * method ListAllGameApps
    * List all Game apps. If the arrayList is empty or there are no Game apps, print"No Game apps"
    * */
    public String listAllGameApps() {
        if (apps.isEmpty()) return "No Game apps";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i) instanceof GameApp) {
                sb.append(i).append(": ").append(apps.get(i)).append('\n');
            }
        }
        if (sb.isEmpty()) sb.append("No Game apps");
        return sb.toString();
    }

    /*
    * method ListAllEducationApps
    * List all Education apps. If the ArrayList is empty or there are no Education apps, print"No Education apps"
    * */
    public String listAllEducationApps() {
        if (apps.isEmpty()) {
            return "No education apps";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i) instanceof EducationApp) {
                sb.append(i).append(": ").append(apps.get(i)).append("\n");
            }
        }
        if (sb.isEmpty()) {
            sb.append("No education apps");
        }
        return sb.toString();
    }

    /*
    * method listAllProductivityApps
    * List all productivity apps. If the ArrayList is empty or there are no productivity apps, print"No Productivity apps"
    * */
    public String listAllProductivityApps() {
        if (apps.isEmpty()) return "No Productivity apps";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i) instanceof ProductivityApp) {
                sb.append(i).append(": ").append(apps.get(i)).append('\n');
            }
        }
        if (sb.isEmpty()) sb.append("No Productivity apps");
        return sb.toString();
    }

    /*
    * method isValidIndex
    * determine whether the data is valid
    * */
    public boolean isValidIndex(int index) {

        return (index >= 0) && (index < apps.size());
    }

    /*
    * method deleteAppByIndex
    * It first determines whether the incoming data is valid, and then deletes the object at the corresponding location
    * */
    public App deleteAppByIndex(int indexToDelete) {
        if (isValidIndex(indexToDelete)) {
            return apps.remove(indexToDelete);
        }
        return null;
    }

    /*
    * method isValidAppName
    * Determines whether the String passed in is valid
    * */
    public boolean isValidAppName(String appName) {
        for (App app : apps) {
            if (app.getAppName().equals(appName))
                return true;
        }
        return false;
    }

    /*
    * method getAppByName
    * Gets the corresponding object in the ArrayList based on the app name passed in
    * */
    public App getAppByName(String name) {
        if (name == null) {
            return null;
        }
        for (App app : apps) {
            if (app.getAppName().equalsIgnoreCase(name)) {
                return app;
            }
        }
        return null;
    }

    /*
    * method listAllAppsByName
    * apps are listed according to the app names passed in
    * */
    public String listAllAppsByName(String name) {
        if (apps.isEmpty() || !isValidAppName(name)) {
            return "No apps for name " + name + " exists";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).getAppName().equalsIgnoreCase(name)) {
                builder.append(i).append(": ").append(apps.get(i)).append('\n');
            }
        }
        if (builder.isEmpty()) {
            builder.append("No apps for name ").append(name).append(" exists");
        }
        return builder.toString();
    }

    /*
    * method listAllAppsAboveOrEqualAGivenStarRating
    * list apps above or equal to the rating given. If there are no objects in the ArrayList or no application meets the requirements, return"No apps have a rating of 'rating' or above "
    * */
    public String listAllAppsAboveOrEqualAGivenStarRating(int rating) {
        if (apps.isEmpty() || rating < 1 || rating > 5) {
            return "No apps have a rating of " + rating + " or above";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).calculateRating() >= rating)
                builder.append(i).append(": ").append(apps.get(i)).append('\n');
        }
        if (builder.isEmpty()) {
            return "No apps have a rating of " + rating + " or above";
        }
        return builder.toString();
    }

    /*
    * method listAllRecommendedApps
    * List all apps that meet the recommended criteria. If there are no objects in the ArrayList or no eligible apps, return"No recommended apps"
    * */
    public String listAllRecommendedApps() {
        if (apps.isEmpty()) {
            return "No recommended apps";
        }
        String str = "";
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).isRecommendedApp()) {
                str += apps.get(i).getAppName();
            } else {
                str = "No recommended apps";
            }
        }
        return str;
    }

    /*
    * method listAllAppsByChosenDeveloper
    * The apps are listed according to the author name passed in. If there are no objects in the ArrayList or no eligible apps, return"No apps for developer 'developer'"
    * */
    public String listAllAppsByChosenDeveloper(Developer developer) {
        if (apps.isEmpty()) {
            return "No apps for developer: " + developer;
        }
        StringBuilder a = new StringBuilder();
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).getDeveloper().equals(developer)) {
                a.append(i).append(": ").append(apps.get(i)).append('\n');
            }
        }
        if (a.isEmpty()) a.append("No apps for developer: ").append(developer);
        return a.toString();
    }

    /*
    * method findApp
    * Returns the corresponding apps based on the incoming data
    * */
    public App findApp(int index) {
        if (isValidIndex(index)) {
            return apps.get(index);
        }
        return null;
    }

    /*
    * method updateEducationApp
    * Updates the Education apps in the ArrayList based on the incoming data and returns a boolean judgment on whether the update was successful
    * */
    public boolean updateEducationApp(int indexToUpdate, Developer developer, String appName, double appSize, double appVersion, double appCost, int level) {
        App foundApp = findApp(indexToUpdate);
        if ((foundApp != null) && (foundApp instanceof EducationApp)) {
            ((EducationApp) foundApp).setDeveloper(developer);
            ((EducationApp) foundApp).setAppName(appName);
            ((EducationApp) foundApp).setAppCost(appCost);
            ((EducationApp) foundApp).setAppVersion(appVersion);
            ((EducationApp) foundApp).setAppSize(appSize);
            ((EducationApp) foundApp).setLevel(level);
            return true;
        }
        return false;
    }

    /*
     * method updateGameApp
     * Updates the Game apps in the ArrayList based on the incoming data and returns a boolean judgment on whether the update was successful
     * */
    public boolean updateGameApp(int indexToUpdate, Developer developer, String appName, double appSize, double appVersion, double appCost, boolean isMultiplayer) {
        App foundApp = findApp(indexToUpdate);
        if ((foundApp != null) && (foundApp instanceof GameApp)) {
            ((GameApp) foundApp).setDeveloper(developer);
            ((GameApp) foundApp).setAppName(appName);
            ((GameApp) foundApp).setAppSize(appSize);
            ((GameApp) foundApp).setAppCost(appCost);
            ((GameApp) foundApp).setAppVersion(appVersion);
            ((GameApp) foundApp).setMultiplayer(isMultiplayer);
            return true;
        }
        return false;
    }

    /*
     * method updateProductivityApp
     * Updates the Productivity apps in the ArrayList based on the incoming data and returns a boolean judgment on whether the update was successful
     * */
    public boolean updateProductivityApp(int indexToUpdate, Developer developer, String appName, double appSize, double appVersion, double appCost) {
        App foundApp = findApp(indexToUpdate);
        if ((foundApp != null) && (foundApp instanceof ProductivityApp)) {
            ((ProductivityApp) foundApp).setAppName(appName);
            ((ProductivityApp) foundApp).setAppSize(appSize);
            ((ProductivityApp) foundApp).setAppVersion(appVersion);
            ((ProductivityApp) foundApp).setAppCost(appCost);
            ((ProductivityApp) foundApp).setDeveloper(developer);
            return true;
        }
        return false;
    }

    /*
    * method numberOfAppsByChosenDeveloper
    * List the number of apps according to the developers
    * */
    public int numberOfAppsByChosenDeveloper(Developer developer) {
        if (apps.isEmpty()) {
            return 0;
        }
        int number = 0;
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).getDeveloper().equals(developer)) {
                number++;
            } else {
                number = number + 0;
            }
        }
        return number;
    }

    /*
    * method numberOfApps
    * List the number of objects in the ArrayList memory
    * */
    public int numberOfApps() {
        return apps.size();
    }

    /*
    * method numberOfEducationApps
    * List the number of Education apps in the ArrayList memory
    * */
    public int numberOfEducationApps() {
        int number = 0;
        for (App app : apps) {
            if (app instanceof EducationApp) {
                number++;
            }
        }
        return number;
    }

    /*
    * method numberOfGameApps
    * List the number of Game apps in the ArrayList memory
    * */
    public int numberOfGameApps() {
        int number = 0;
        for (App app : apps) {
            if (app instanceof GameApp) {
                number++;
            }
        }
        return number;
    }

    /*
    * method numberOfProductivityApp
    * List the number of productivity apps in the ArrayList memory
    * */
    public int numberOfProductivityApp() {
        int number = 0;
        for (App app : apps) {
            if (app instanceof ProductivityApp) {
                number++;
            }
        }
        return number;
    }

    /*
    * method randomApp
    * Returns an object stored in the ArrayList randomly
    * */
    public App randomApp() {
        if (apps.isEmpty()) {
            return null;
        } else {
            return apps.get(new Random().nextInt(apps.size()));
        }
    }

    /*
    * method simulateRating
    * Gives the apps in the ArrayList a random rating
    * */
    public void simulateRatings() {
        for (App app : apps) {
            app.addRating(generateRandomRating());
        }
    }

    /*
    * method sortAppsByNameAscending
    * Sort existing apps in the ArrayList
    * */
    public void sortAppsByNameAscending() {
        for (int i = apps.size() - 1; i >= 0; i--) {
            int highestIndex = 0;
            for (int j = 0; j <= i; j++) {
                if (apps.get(j).getAppName().compareTo(apps.get(highestIndex).getAppName()) > 0) {
                    highestIndex = j;
                }
            }
            swapApps(apps, i, highestIndex);
        }
    }

    /*
    * method swapApps
    * Swap the two application in the ArrayList
    * */
    private void swapApps(ArrayList<App> apps, int i, int j) {
        App smaller = apps.get(i);
        App bigger = apps.get(j);

        apps.set(i, bigger);
        apps.set(j, smaller);
    }

    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{App.class, EducationApp.class, GameApp.class, ProductivityApp.class, Rating.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(fileName()));
        apps = (ArrayList<App>) in.readObject();
        in.close();
    }

    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(fileName()));
        out.writeObject(apps);
        out.close();
    }

    public String fileName() {
        return "apps.xml";
    }
}
