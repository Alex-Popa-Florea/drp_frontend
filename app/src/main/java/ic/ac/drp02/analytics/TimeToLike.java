package ic.ac.drp02.analytics;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeToLike {

    private LocalDateTime appStartTime;
    private LocalDateTime firstLikeTime;
    private Boolean firstLike = false;
    private final AnalyticType type = AnalyticType.TIME_TO_LIKE;
    private static TimeToLike timeToLike;

    private TimeToLike(){}

    public static TimeToLike getInstance(){
        if(timeToLike == null) {
            timeToLike = new TimeToLike();
        }
        return timeToLike;
    }

    public void setFirstLike(Boolean firstLike) {
        this.firstLike = firstLike;
    }

    public Boolean getFirstLike() {
        return firstLike;
    }

    public void setFirstLikeTime(LocalDateTime firstLikeTime) {
        this.firstLikeTime = firstLikeTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void calculateTime(){
       Duration.between(appStartTime,firstLikeTime);
    }

    public void sendData(){
        //TODO
    }

    public LocalDateTime getFirstLikeTime() {
        return firstLikeTime;
    }

    public void setAppStartTime(LocalDateTime appStartTime) {
        this.appStartTime = appStartTime;
    }
    public LocalDateTime getAppStartTime() {
        return appStartTime;
    }

    public AnalyticType getType() {
        return type;
    }
}
