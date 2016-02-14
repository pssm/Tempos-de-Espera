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

package pedromendes.tempodeespera;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.JsonReader;
import android.util.JsonToken;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import pedromendes.tempodeespera.adapters.EmergencyListAdapter;
import pedromendes.tempodeespera.data.Emergency;
import pedromendes.tempodeespera.data.EmergencyQueue;

public class HospitalDetailActivity extends AppCompatActivity {

    private Logger logger = Logger.getLogger(HospitalDetailActivity.class.getName());

    private static final String HOSPITAL_DETAIL_URL = "http://tempos.min-saude.pt/api.php/standbyTime/";
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);

        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        Long hospitalId = getIntent().getExtras().getLong("HOSPITAL_ID");
        String hospitalName = getIntent().getExtras().getString("HOSPITAL_NAME");
        String hospitalDescription = getIntent().getExtras().getString("HOSPITAL_DESCRIPTION");
        String hospitalAddress = getIntent().getExtras().getString("HOSPITAL_ADDRESS");
        String hospitalPhone = getIntent().getExtras().getString("HOSPITAL_PHONE");
        String hospitalEmail = getIntent().getExtras().getString("HOSPITAL_EMAIL");
        String latitude = getIntent().getExtras().getString("HOSPITAL_LAT");
        String longitude = getIntent().getExtras().getString("HOSPITAL_LONG");

        TextView hospitalNameView = (TextView) findViewById(R.id.hospitalName);
        hospitalNameView.setText(hospitalName);

        if (hospitalAddress != null && !hospitalAddress.isEmpty()) {
            TextView hospitalAddressView = (TextView) findViewById(R.id.hospitalAddress);
            hospitalAddressView.setText(Html.fromHtml(hospitalAddress + "<br/>" +
                    "<a href=\"geo:" + latitude + "," + longitude + "\">" +
                    "Ver mapa" +
                    "</a>"));
            hospitalAddressView.setMovementMethod(LinkMovementMethod.getInstance());
        }


        if (hospitalPhone != null && !hospitalPhone.isEmpty()) {
            TextView hospitalPhoneView = (TextView) findViewById(R.id.hospitalPhone);
            hospitalPhoneView.append(" " + hospitalPhone);
            hospitalPhoneView.setTypeface(font);
        }

        dialog = new ProgressDialog(this);
        dialog.setMessage("A carregar...");
        dialog.show();

        new RequestHospitalDetailTask().execute(hospitalId.toString());
    }


    class RequestHospitalDetailTask extends AsyncTask<String, String, List<Emergency>> {

        @Override
        protected List<Emergency> doInBackground(String... hospitalId) {
            HttpURLConnection urlConnection = null;
            List<Emergency> hospitalEmergencyDetail = null;
            try {
                URL url = new URL(HOSPITAL_DETAIL_URL.concat(hospitalId[0]));
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                hospitalEmergencyDetail = readJsonStream(in);
            } catch (Exception e) {
                logger.log(Level.ALL, e.getMessage());
            } finally {
                urlConnection.disconnect();
            }

            return hospitalEmergencyDetail;
        }

        @Override
        protected void onPostExecute(List<Emergency> result) {
            dialog.hide();
            super.onPostExecute(result);

            if (result == null) {
                TextView noAvailableData = (TextView) findViewById(R.id.no_available_data);
                noAvailableData.setText("Esta instituição não partilha tempos da urgência.");
                return;
            }
            ArrayAdapter emergencyListAdapter = new EmergencyListAdapter(HospitalDetailActivity.this, R.layout.emergency_list_item, result);
            ListView emergenciesListView = (ListView) findViewById(R.id.emergencies_list);
            emergenciesListView.setAdapter(emergencyListAdapter);
        }
    }

    public List<Emergency> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readHospitalDetailGetResponse(reader);
        } finally {
            reader.close();
        }
    }


    private List<Emergency> readHospitalDetailGetResponse(JsonReader reader) throws IOException {
        List<Emergency> result = null;
        reader.beginObject();
        while (reader.hasNext()) {
            result = readResult(reader);
        }
        reader.endObject();
        return result;
    }

    public List<Emergency> readResult(JsonReader reader) throws IOException {
        List<Emergency> result = new ArrayList<Emergency>();
        String name = reader.nextName();
        if (name.equals("Result")) {
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                Emergency hospitalEmergencyDetail = new Emergency();
                readEmergency(reader, hospitalEmergencyDetail);
                reader.endObject();
                result.add(hospitalEmergencyDetail);
            }
            reader.endArray();
        }
        return result;
    }

    private void readEmergency(JsonReader reader, Emergency hospitalEmergencyDetail) throws IOException {
        while (reader.hasNext()) {
            String fieldDame = reader.nextName();
            if (fieldDame.equals("Emergency")) {
                reader.beginObject();
                reader.nextName();
                reader.nextString();
                reader.nextName();
                hospitalEmergencyDetail.setDescription(reader.nextString());
                reader.endObject();
            } else if (fieldDame.equals("Queue") && reader.peek() != JsonToken.NULL) {
                reader.beginObject();
                reader.nextName();
                reader.nextString();
                reader.nextName();
                hospitalEmergencyDetail.setName(reader.nextString());
                reader.endObject();
            } else if (fieldDame.equals("Red")) {
                reader.beginObject();
                fillQueue(reader, hospitalEmergencyDetail.getRedQueue());
                reader.endObject();
            } else if (fieldDame.equals("Orange")) {
                reader.beginObject();
                fillQueue(reader, hospitalEmergencyDetail.getOrangeQueue());
                reader.endObject();
            } else if (fieldDame.equals("Yellow")) {
                reader.beginObject();
                fillQueue(reader, hospitalEmergencyDetail.getYellowQueue());
                reader.endObject();
            } else if (fieldDame.equals("Green")) {
                reader.beginObject();
                fillQueue(reader, hospitalEmergencyDetail.getGreenQueue());
                reader.endObject();
            } else if (fieldDame.equals("Blue")) {
                reader.beginObject();
                fillQueue(reader, hospitalEmergencyDetail.getBlueQueue());
                reader.endObject();
            } else if (fieldDame.equals("LastUpdate")) {
                hospitalEmergencyDetail.setLastUpdate(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
    }

    public void fillQueue(JsonReader reader, EmergencyQueue queue) throws IOException {
        reader.nextName();
        int time = reader.nextInt();
        reader.nextName();
        int length = reader.nextInt();
        queue.setTime(time);
        queue.setLength(length);
    }
}
