package models;

public class GameAPP extends App {
    private boolean isMultiplayer = false;
    public GameAPP(Developer developer,String appName,double  appSize,double appVersion,double appCost,boolean isMultiplayer){
        setAppVersion(appVersion);
        setAppCost(appCost);
        setAppName(appName);
        setDeveloper(developer);
        setAppSize(appSize);
    }

    public void setMultiplayer(boolean multiplayer) {
        isMultiplayer = multiplayer;
    }

    public boolean isMultiplayer() {
        return isMultiplayer;
    }

    public String appSummary(){
        return getAppName() + "(" + "V" + getAppVersion() + ")" + "by" + getDeveloper() + "$" + getAppCost() + "." + "Rating" + calculateRating();
    }

    public boolean isRecommendedApp() {
       if (isMultiplayer = true && calculateRating() >= 4.0 ){
           return true;
       }else {
           return false;
       }
    }
    public String toString(){
        return super.toString();
    }
}
