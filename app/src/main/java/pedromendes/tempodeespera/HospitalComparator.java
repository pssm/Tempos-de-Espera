package pedromendes.tempodeespera;

import java.util.Comparator;


public class HospitalComparator implements Comparator<Hospital> {
    @Override
    public int compare(Hospital lhs, Hospital rhs) {
        return lhs.getDistrict().compareToIgnoreCase(rhs.getDistrict());
    }
}
