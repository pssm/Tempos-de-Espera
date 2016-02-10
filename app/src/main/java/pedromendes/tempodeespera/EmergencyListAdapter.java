package pedromendes.tempodeespera;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;


public class EmergencyListAdapter extends ArrayAdapter<Emergency> {
    public EmergencyListAdapter(Context context, int resource, List<Emergency> objects) {
        super(context, resource, objects);



    }
}
