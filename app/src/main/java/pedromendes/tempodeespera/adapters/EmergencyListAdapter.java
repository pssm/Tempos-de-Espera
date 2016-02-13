package pedromendes.tempodeespera.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pedromendes.tempodeespera.R;
import pedromendes.tempodeespera.data.Emergency;


public class EmergencyListAdapter extends ArrayAdapter<Emergency> {

    private List<Emergency> emergencies;

    public EmergencyListAdapter(Context context, int resource, List<Emergency> objects) {
        super(context, resource, objects);

        this.emergencies = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Emergency emergency = emergencies.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.emergency_list_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.emergency_name);
        if (emergency.getName() != null) {
            name.setText(emergency.getName());
        }

        TextView redQueue = (TextView) convertView.findViewById(R.id.emergency_list_red_queue);
        TextView orangeQueue = (TextView) convertView.findViewById(R.id.emergency_list_orange_queue);
        TextView yellowQueue = (TextView) convertView.findViewById(R.id.emergency_list_yellow_queue);
        TextView greenQueue = (TextView) convertView.findViewById(R.id.emergency_list_green_queue);
        TextView blueQueue = (TextView) convertView.findViewById(R.id.emergency_list_blue_queue);

        redQueue.setText(emergency.getRedQueue().getTime());
        orangeQueue.setText(emergency.getOrangeQueue().getTime());
        yellowQueue.setText(emergency.getYellowQueue().getTime());
        greenQueue.setText(emergency.getGreenQueue().getTime());
        blueQueue.setText(emergency.getBlueQueue().getTime());

        return convertView;
    }

}
