package pedromendes.tempodeespera.data;

import java.util.concurrent.TimeUnit;

/**
 * Created by Pedro Mendes on 13-02-2016.
 */
public class EmergencyQueue {
    int time;
    int length;

    public EmergencyQueue() {
    }

    public EmergencyQueue(int time, int length) {
        this.time = time;
        this.length = length;
    }

    public String getTime() {
        if (time > 0) {
            int minutes = (time / 60);
            if (minutes <= 60) {
                return "" + (time / 60) + " min";
            } else {
                long hours = TimeUnit.MINUTES.toHours(minutes);
                long remainMinute = minutes - TimeUnit.HOURS.toMinutes(hours);
                return String.format("%02d h %02d min", hours, remainMinute);
            }
        }

        return "-";
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }


}
