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

package pedromendes.tempodeespera.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pedromendes.tempodeespera.data.Hospital;

public enum Util {
    INSTANCE;

    public static final String ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";


    public List<String> extractHospitalRegions(List<Hospital> hospitals) {
        Map<String, String> hospitalMap = new HashMap<>();

        for (Hospital h : hospitals) {
            if (!hospitalMap.containsKey(h.getDistrict())) {
                hospitalMap.put(h.getDistrict(), h.getDistrict());
            }
        }
        ArrayList<String> resultSet = new ArrayList<>(hospitalMap.keySet());


        Collections.sort(resultSet);

        return resultSet;
    }

}
