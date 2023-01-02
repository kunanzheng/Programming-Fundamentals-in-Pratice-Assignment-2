package models;

import utils.Utilities;

public class GameApp extends App {
    private boolean isMultiplayer = false;
    public GameApp(Developer developer,String appName,double  appSize,double appVersion,double appCost,boolean isMultiplayer){
        setAppVersion(appVersion);
        setAppCost(appCost);
        setAppName(appName);
        setDeveloper(developer);
        setAppSize(appSize);
        setMultiplayer(isMultiplayer);
    }

    public void setMultiplayer(boolean multiplayer) {
        isMultiplayer = multiplayer;
    }

    public boolean isMultiplayer() {
        return isMultiplayer;
    }
   /*
   * method appSummary
   * list app brief information
   * */
    public String appSummary(){
        return getAppName() + "(V" + getAppVersion() + ")" + "by" + getDeveloper().toString() + "€" + getAppCost() + "." + "Rating: " + calculateRating();
    }
    /*
    * method isRecommendedApp
    * determine if the app can be recommended
    * */
    public boolean isRecommendedApp() {
       if (isMultiplayer && Utilities.greaterThanOrEqualTo(calculateRating(),4.0)){
           return true;
       }else {
           return false;
       }
    }
    /*
    * method toString
    * Override the parent method and containing the apps' information and return it
    * */
    public String toString(){
        return super.toString() + Utilities.booleanToYN(isMultiplayer);
    }
}
