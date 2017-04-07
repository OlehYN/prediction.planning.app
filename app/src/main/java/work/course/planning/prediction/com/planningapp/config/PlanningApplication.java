package work.course.planning.prediction.com.planningapp.config;

import android.app.Application;
import android.content.Context;

import work.course.planning.prediction.com.planningapp.service.PlanningApiService;

/**
 * Created by Oleh Yanivskyy on 03.04.2017.
 */

public class PlanningApplication extends Application{
    private static Context context;
    private static PlanningApplication planningApplication;

    private String token = "228";
    private String host = "http://192.168.1.105";
    private int port = 8080;

    public void onCreate() {
        super.onCreate();
        PlanningApplication.context = getApplicationContext();
        planningApplication = this;
    }

    public String getToken(){
        return token;
    }

    public String getHost(){
        return host;
    }

    public int getPort(){
        return port;
    }

    public static Context getAppContext() {
        return PlanningApplication.context;
    }

    public static PlanningApplication getPlanningApplication(){
        return planningApplication;
    }
}
