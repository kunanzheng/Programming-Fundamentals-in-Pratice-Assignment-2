package models;

import utils.Utilities;

public class ProductivityApp extends App{
    public ProductivityApp(Developer developer,String appName,double appSize,double appVersion,double appCost){
        setDeveloper(developer);
        setAppName(appName);
        setAppSize(appSize);
        setAppVersion(appVersion);
        setAppCost(appCost);
    }

    /*
     * method isRecommendedApp
     * override the method, determine if the app can be recommended
     * */
    public boolean isRecommendedApp(){
        if (Utilities.greaterThanOrEqualTo(getAppCost(),1.99) && Utilities.greaterThanOrEqualTo(calculateRating(),3.1)){
            return true;
        }else {
            return false;
        }
    }

    /*
     * method toString
     * Override the parent method and containing the apps' information and return it
     * */
    public String toString() {
     return super.toString();
    }
}
