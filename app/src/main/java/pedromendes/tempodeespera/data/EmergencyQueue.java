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

import java.util.concurrent.TimeUnit;


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
