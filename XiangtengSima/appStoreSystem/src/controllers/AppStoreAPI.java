package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.*;
import utils.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.random;
import static utils.RatingUtility.generateRandomRating;

public class AppStoreAPI {
    private ArrayList<App> apps = new ArrayList<App>();

    public boolean addApp(App app){
        return apps.add(app);
    }

    public String listAllApps(){
        String str = "";
        if (apps.isEmpty()){
            return "No apps";
        }else {
            for (int i = 0; i < apps.size() ; i++) {
              str = apps.get(i).toString();

            }
        }
        return toString();
    }

    public String listSummaryOfAllApps(){
        String str = "";
        if (apps.isEmpty()){
            return "No apps";
        }else {
            for (int i = 0;i < apps.size() ;i++ ){
                str = apps.get(i).appSummary();
            }
        }
        return str;
    }

    public String listAllGameApps(){
        String str = "";
        for (App app : apps){
            if (app instanceof GameAPP){
                str += apps.indexOf(app) + ": " + app.toString() + "\n";
            }
        }
        if (str.isEmpty()){
            return "No Game apps";
        }else {
            return str;
        }
    }

    public String listAllEducationApps(){
        String str = "";
        for (App app : apps){
            if (app instanceof EducationAPP){
                str += apps.indexOf(app) + ": " + app.toString() + "\n";
            }
        }
        if (str.isEmpty()){
            return "No Education apps";
        }else {
            return str;
        }
    }

    public String listAllProductivityApps(){
        String str = "";
        for (App app : apps){
            if (app instanceof ProductivityAPP){
                str += apps.indexOf(app) + ": " + app.toString() + "\n";
            }
        }
        if (str.isEmpty()){
            return "No Education apps";
        }else {
            return str;
        }
    }

    public boolean isValidIndex(int index){
        return (index >= 0) && (index < apps.size());
    }

    public App deleteAppByIndex(int indexToDelete){
        if (isValidIndex( indexToDelete)){
            return apps.remove(indexToDelete);
        }
        return null;
    }

    public boolean isValidAppName(String appName){
           for (App app : apps){
               if (app.getAppName().equals(appName))
                   return true;
           }
           return false;
    }

    public App getAppByName(String name){
        if (name == null){
            return null;
        }
        for (App app : apps){
            if (app.getAppName().equalsIgnoreCase(name)){
                return app;
            }
        }
        return null;
    }

    public String listAllAppsByName(String name){
        if (getAppByName(name) ==  null){
            return "No apps for name" + name + "exists";
        }else {
            String str = "";
            for (int i = 0; i < apps.size() ;i ++) {
                if (apps.get(i).getAppName().equalsIgnoreCase(name)) {
                    str += apps.get(i).getAppName() + "\n";
                }
            }
            return str;
        }
    }

    public String listAllAppsAboveOrEqualAGivenStarRating(int rating){
        if (apps.isEmpty()||rating < 1||rating > 5){
            return "No apps have a rating of " + rating + " or above";
        }else {
            String str = "";
            for (int i = 0;i < apps.size() ;i++){
                if (apps.get(i).calculateRating() >= rating){
                     str += apps.get(i).getAppName() + "\n";
                }
            }
            return str;
        }
    }

    public String listAllRecommendedApps(){
        if (apps.isEmpty()) {
            return "No recommended apps";
        }
        String str = "";
            for (int i = 0;i < apps.size() ;i++){
                if (apps.get(i).isRecommendedApp()){
                    str += apps.get(i).getAppName();
                }else {
                    str =  "No recommended apps";
                }
            }
        return str;
    }

    public String listAllAppsByChosenDeveloper(Developer developer){
        if (apps.isEmpty()){
            return "No apps for developer: " + developer;
        }
         String str = "";
        for (int i = 0;i < apps.size() ; i++){
            if (apps.get(i).getDeveloper().equals(developer)){
                str += apps.get(i).getAppName();
            }else {
                str += "Np apps for developer: " + developer;
            }
        }
        return str;
    }

    public App findApp(int index){
        if (isValidIndex(index)){
            return apps.get(index);
        }
        return null;
    }

    public boolean updateEducationApp(int indexToUpdate, Developer developer, String appName, double appSize , double appVersion, double appCost, int level){
        App foundApp = findApp(indexToUpdate);
        if ((foundApp != null) && (foundApp instanceof EducationAPP)){
            ((EducationAPP)foundApp).setDeveloper(developer);
            ((EducationAPP)foundApp).setAppName(appName);
            ((EducationAPP)foundApp).setAppCost(appCost);
            ((EducationAPP)foundApp).setAppVersion(appVersion);
            ((EducationAPP)foundApp).setAppSize(appSize);
            ((EducationAPP)foundApp).setLevel(level);
            return true;
        }
        return false;
    }

    public boolean updateGameApp(int indexToUpdate, Developer developer, String appName, double appSize, double appVersion, double appCost, boolean isMultiplayer){
        App foundApp = findApp(indexToUpdate);
        if ((foundApp != null) && (foundApp instanceof GameAPP)){
            ((GameAPP)foundApp).setDeveloper(developer);
            ((GameAPP)foundApp).setAppName(appName);
            ((GameAPP)foundApp).setAppSize(appSize);
            ((GameAPP)foundApp).setAppCost(appCost);
            ((GameAPP)foundApp).setAppVersion(appVersion);
            ((GameAPP)foundApp).setMultiplayer(isMultiplayer);
            return true;
        }
        return false;
    }

    public boolean updateProductivityApp(int indexToUpdate, Developer developer, String appName, double appSize, double appVersion, double appCost){
        App foundApp = findApp(indexToUpdate);
        if ((foundApp != null) && (foundApp instanceof ProductivityAPP)){
            ((ProductivityAPP)foundApp).setAppName(appName);
            ((ProductivityAPP)foundApp).setAppSize(appSize);
            ((ProductivityAPP)foundApp).setAppVersion(appVersion);
            ((ProductivityAPP)foundApp).setAppCost(appCost);
            ((ProductivityAPP)foundApp).setDeveloper(developer);
            return true;
        }
        return false;
    }

    public int numberOfAppsByChosenDeveloper(Developer developer){
        if (apps.isEmpty()){
            return 0;
        }
        int number = 0;
        for (int i = 0;i < apps.size();i++){
            if (apps.get(i).getDeveloper().equals(developer)){
                number ++;
            }else {
                number = number + 0;
            }
        }
        return number;
    }

    public int numberOfApps(){
        return apps.size();
    }

    public int numberOfEducationApps(){
        int number = 0;
        for (App app : apps){
            if (app instanceof EducationAPP){
                number ++;
            }
        }
        return number;
    }

    public int numberOfGameApps(){
        int number = 0;
        for (App app : apps){
            if (app instanceof GameAPP){
                number ++;
            }
        }
        return number;
    }

    public int numberOfProductivityApp(){
        int number = 0;
        for (App app : apps){
            if (app instanceof ProductivityAPP){
                number ++;
            }
        }
        return number;
    }

    public App randomApp(){
        if (apps.isEmpty()){
            return null;
        }else {
            return apps.get(new Random().nextInt(apps.size()));
        }

    }

    public void simulateRatings(){
        for (App app :apps) {
            app.addRating(generateRandomRating());
        }
    }

    public void sortAppsByNameAscending(){
        for(int i =apps.size() - 1; i >= 0; i--){
            int highestIndex = 0;
            for(int j = 0; j <= i; j++){
                if (apps.get(j).getAppName().compareTo(apps.get(highestIndex).getAppName()) > 0){
                    highestIndex = j;
                }
            }
            swapApps(apps, i, highestIndex);
        }
    }

    private void swapApps(ArrayList<App> apps, int i, int j){
        App smaller = apps.get(i);
        App bigger = apps.get(j);

        apps.set(i,bigger);
        apps.set(j,smaller);
    }

    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{App.class, EducationAPP.class, GameAPP.class, ProductivityAPP.class, Rating.class};

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

    public String fileName(){
        return "apps.xml";
    }



    //TODO refer to the spec and add in the required methods here (make note of which methods are given to you first!)

    //---------------------
    // Method to simulate ratings (using the RatingUtility).
    // This will be called from the Driver (see skeleton code)
    //---------------------
    // TODO UNCOMMENT THIS COMPLETED method as you start working through this class
    //---------------------
    /*
    public void simulateRatings(){
        for (App app :apps) {
            app.addRating(generateRandomRating());
        }
    }*/

    //---------------------
    // Validation methods
    //---------------------
    // TODO UNCOMMENT THIS COMPlETED method as you start working through this class
    //---------------------
    /*
    public boolean isValidIndex(int index) {
        return (index >= 0) && (index < apps.size());
    }*/

    //---------------------
    // Persistence methods
    //---------------------
    // TODO UNCOMMENT THIS COMPLETED CODE block as you start working through this class
    //---------------------
    /*
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
        apps = (List<App>) in.readObject();
        in.close();
    }

    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(fileName()));
        out.writeObject(apps);
        out.close();
    }

    public String fileName(){
        return "apps.xml";
    }
    */
}
