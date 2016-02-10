package pedromendes.tempodeespera;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filterable;


public class HospitalListAdapter extends ArrayAdapter<String> implements Filterable {


    public HospitalListAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
    }


}
