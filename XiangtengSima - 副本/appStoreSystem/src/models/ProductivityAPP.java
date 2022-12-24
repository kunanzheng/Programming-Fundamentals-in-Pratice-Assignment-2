package models;

public class ProductivityAPP extends App{
    public ProductivityAPP(Developer developer,String appName,double appSize,double appVersion,double appCost){
        setDeveloper(developer);
        setAppName(appName);
        setAppSize(appSize);
        setAppVersion(appVersion);
        setAppCost(appCost);
    }
    public boolean isRecommendedApp(){
        if (getAppCost() > 1.99 && calculateRating() >= 3.0){
            return true;
        }else {
            return false;
        }
    }

    public String toString() {
     return super.toString();
    }
}
