/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pedromendes.tempodeespera.data;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import pedromendes.tempodeespera.utils.Util;

public class Emergency {
    String name;
    String description;
    Date lastUpdate;
    EmergencyQueue redQueue = new EmergencyQueue();
    EmergencyQueue orangeQueue = new EmergencyQueue();
    EmergencyQueue yellowQueue = new EmergencyQueue();
    EmergencyQueue greenQueue = new EmergencyQueue();
    EmergencyQueue blueQueue = new EmergencyQueue();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EmergencyQueue getRedQueue() {
        return redQueue;
    }

    public void setRedQueue(int time, int length) {
        this.redQueue = new EmergencyQueue(time, length);
    }

    public EmergencyQueue getOrangeQueue() {
        return orangeQueue;
    }

    public void setOrangeQueue(int time, int length) {
        this.orangeQueue = new EmergencyQueue(time, length);
    }

    public EmergencyQueue getYellowQueue() {
        return yellowQueue;
    }

    public void setYellowQueue(int time, int length) {
        this.yellowQueue = new EmergencyQueue(time, length);
    }

    public EmergencyQueue getGreenQueue() {
        return greenQueue;
    }

    public void setGreenQueue(int time, int length) {
        this.greenQueue = new EmergencyQueue(time, length);
    }

    public EmergencyQueue getBlueQueue() {
        return blueQueue;
    }

    public void setBlueQueue(int time, int length) {
        this.blueQueue = new EmergencyQueue(time, length);
    }

    public void setRedQueue(EmergencyQueue queue) {
        this.redQueue = queue;
    }

    public void setOrangeQueue(EmergencyQueue queue) {
        this.orangeQueue = queue;
    }

    public void setYellowQueue(EmergencyQueue queue) {
        this.yellowQueue = queue;
    }

    public void setGreenQueue(EmergencyQueue queue) {
        this.greenQueue = queue;
    }

    public void setBlueQueue(EmergencyQueue queue) {
        this.blueQueue = queue;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public String getFormattedLastUpdate() {
        String result = "-";

        if (lastUpdate != null) {
            long minutes = (Calendar.getInstance().getTime().getTime() - lastUpdate.getTime()) / 60000;
            if (minutes <= 60) {
                return minutes + " min";
            } else {
                long hours = TimeUnit.MINUTES.toHours(minutes);
                long remainMinute = minutes - TimeUnit.HOURS.toMinutes(hours);
                return String.format("%02d h %02d min", hours, remainMinute);
            }

        }

        return result;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        //2016-02-14T09:21:19.018
        DateFormat df = new SimpleDateFormat(Util.ISO_8601_24H_FULL_FORMAT, Locale.getDefault());
        try {
            Date result = df.parse(lastUpdate);
            this.lastUpdate = result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getName() + "\n");
        sb.append(getDescription() + "\n");
        sb.append("Senha verde: " + getGreenQueue().getTime() + "\n");
        sb.append("Senha azul: " + getBlueQueue().getTime() + "\n");
        sb.append("Senha amarela: " + getYellowQueue().getTime() + "\n");
        sb.append("Senha laranja: " + getOrangeQueue().getTime() + "\n");
        sb.append("Senha vermelha: " + getRedQueue().getTime() + "\n");

        return sb.toString();
    }
}
