package pedromendes.tempodeespera;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filterable;

/**
 * Created by Pedro Mendes on 05-02-2016.
 */
public class HospitalListAdapter extends ArrayAdapter<String> implements Filterable {


    public HospitalListAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
    }


}
