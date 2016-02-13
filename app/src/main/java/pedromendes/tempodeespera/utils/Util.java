package pedromendes.tempodeespera.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pedromendes.tempodeespera.data.Hospital;

/**
 * Created by Pedro Mendes on 05-02-2016.
 */
public enum Util {
    INSTANCE;

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
