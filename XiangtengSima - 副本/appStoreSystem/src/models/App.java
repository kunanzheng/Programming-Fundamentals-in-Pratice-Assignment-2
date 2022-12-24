package models;

import java.util.ArrayList;

public abstract class App {
    private Developer developer;
    private String appName = "No app name";
    private double appSize = 0;
    private double appVersion = 1.0;
    private double appCost = 0;
    private ArrayList<Rating> ratings = new ArrayList<Rating>();
    public void App(Developer developer,String appName,double appSize,double appVersion,double appCost){
        setDeveloper(developer);
        setAppName(appName);
        setAppSize(appSize);
        setAppCost(appCost);
        setAppVersion(appVersion);
    }
    public boolean isRecommendedApp(){
        return true;
    }
    public String appSummary(){
        return getAppName() + "(" + "V" + getAppVersion() + ")" + "by" + developer + "," + "$" + getAppCost() + getRatings();

    }
    public boolean addRating(Rating rating){
        return ratings.add(rating);

    }
    public String listRatings(){
        String str = "";
        if (ratings.isEmpty()){
            return "No ratings added yet";
        }else {
            for (int i = 0; i <= ratings.size();i++){
                str = ratings.get(i).getRatingComment();
            }
        }
        return str;
    }
    public double calculateRating(){
        double sum = 0;
        double avg = 0;
        for (int i = 0;i < ratings.size();i++){
            sum += ratings.get(i).getNumberOfStars();
        }
         avg = sum/ratings.size();
        return avg;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppVersion(double appVersion) {
        this.appVersion = appVersion;
    }

    public void setAppSize(double appSize) {
        this.appSize = appSize;
    }

    public void setAppCost(double appCost) {
        this.appCost = appCost;
    }

    public void setRatings(ArrayList<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getAppName() {
        return appName;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public double getAppVersion() {
        return appVersion;
    }

    public double getAppSize() {
        return appSize;
    }

    public double getAppCost() {
        return appCost;
    }

    public ArrayList<Rating> getRatings() {
        return ratings;
    }


    public String toString() {
        return getAppName() + "(" + "Version" + getAppVersion() + ")" +"\n"
                + "Developer: " + getDeveloper() + "\n"
                + "Cost: " + getAppCost() + "\n"
                +"Ratings: " + calculateRating() + "\n";
    }
}
