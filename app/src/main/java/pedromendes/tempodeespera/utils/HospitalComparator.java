package pedromendes.tempodeespera.utils;

import java.util.Comparator;

import pedromendes.tempodeespera.data.Hospital;


public class HospitalComparator implements Comparator<Hospital> {
    @Override
    public int compare(Hospital lhs, Hospital rhs) {
        return lhs.getDistrict().compareToIgnoreCase(rhs.getDistrict());
    }
}
