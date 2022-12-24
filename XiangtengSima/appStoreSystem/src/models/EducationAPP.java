package models;

import utils.Utilities;

public class EducationAPP extends App {
    private int level = 0;
    
    public EducationAPP(Developer developer,String appName,double appSize,double appVersion,double appCost,int level){
        setDeveloper(developer);
        setAppName(appName);
        setAppSize(appSize);
        setAppVersion(appVersion);
        setAppCost(appCost);
        setLevel(level);
    }

    public void setLevel(int level) {
        if (Utilities.validRange(level,1,10)){
            this.level = level;
        }
    }

    public int getLevel() {
        return level;
    }

    @Override
    public boolean isRecommendedApp() {
        if (getAppCost() >= 0.99 && calculateRating() >= 3.5 && getLevel() > 3){
            return true;
        }
        return false;
    }
    public String appSummary(){
        return getAppName() + "(" + "V" + getAppVersion() + ")" + "by" + getDeveloper() + "$" + getAppCost() + "." + "Rating" + calculateRating();
    }
    public String toString(){
       return super.toString();
    }
}
