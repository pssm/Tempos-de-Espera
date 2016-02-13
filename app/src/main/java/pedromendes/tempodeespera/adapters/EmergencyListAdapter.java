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

package pedromendes.tempodeespera.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pedromendes.tempodeespera.R;
import pedromendes.tempodeespera.data.Emergency;


public class EmergencyListAdapter extends ArrayAdapter<Emergency> {

    private final Context context;
    private List<Emergency> emergencies;

    public EmergencyListAdapter(Context context, int resource, List<Emergency> objects) {
        super(context, resource, objects);
        this.context = context;

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

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");

        TextView clockView0 = (TextView) convertView.findViewById(R.id.clock0);
        TextView clockView1 = (TextView) convertView.findViewById(R.id.clock1);
        TextView clockView2 = (TextView) convertView.findViewById(R.id.clock2);
        TextView clockView3 = (TextView) convertView.findViewById(R.id.clock3);
        TextView clockView4 = (TextView) convertView.findViewById(R.id.clock4);
        clockView0.setTypeface(font);
        clockView1.setTypeface(font);
        clockView2.setTypeface(font);
        clockView3.setTypeface(font);
        clockView4.setTypeface(font);

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
