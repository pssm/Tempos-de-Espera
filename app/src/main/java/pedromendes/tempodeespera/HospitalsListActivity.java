package pedromendes.tempodeespera;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.JsonToken;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

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

import pedromendes.tempodeespera.adapters.HospitalListAdapter;
import pedromendes.tempodeespera.data.Hospital;
import pedromendes.tempodeespera.utils.HospitalComparator;
import pedromendes.tempodeespera.utils.SortedList;
import pedromendes.tempodeespera.utils.Util;

public class HospitalsListActivity extends AppCompatActivity {

    private Logger logger = Logger.getLogger(HospitalsListActivity.class.getName());

    private final static String HOSPITALS_LIST_URI = "http://tempos.min-saude.pt/api.php/institution";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals_list);
        new RequestHospitalsListTask().execute();
    }


    public List readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readHospitalsGetResponse(reader);
        } finally {
            reader.close();
        }
    }


    private List readHospitalsGetResponse(JsonReader reader) throws IOException {
        List<Hospital> hospitalList = null;
        reader.beginObject();
        while (reader.hasNext()) {
            hospitalList = readResult(reader);
        }
        reader.endObject();
        return hospitalList;
    }

    public List readResult(JsonReader reader) throws IOException {
        List hospitals = new ArrayList();

        String name = reader.nextName();
        if (name.equals("Result")) {
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                hospitals.add(readHospital(reader));
            }
            reader.endArray();
        }
        return hospitals;
    }

    public Hospital readHospital(JsonReader reader) throws IOException {
        Hospital hospital = new Hospital();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Id")) {
                hospital.setId(reader.nextLong());
            } else if (name.equals("Name") && reader.peek() != JsonToken.NULL) {
                hospital.setName(reader.nextString());
            } else if (name.equals("Description") && reader.peek() != JsonToken.NULL) {
                hospital.setDescription(reader.nextString());
            } else if (name.equals("Address") && reader.peek() != JsonToken.NULL) {
                hospital.setAddress(reader.nextString());
            } else if (name.equals("Email") && reader.peek() != JsonToken.NULL) {
                hospital.setEmail(reader.nextString());
            } else if (name.equals("Phone") && reader.peek() != JsonToken.NULL) {
                hospital.setPhone(reader.nextString());
            } else if (name.equals("District") && reader.peek() != JsonToken.NULL) {
                hospital.setDistrict(reader.nextString());
            } else if (name.equals("StandbyTimesUrl") && reader.peek() != JsonToken.NULL) {
                hospital.setStandbyTimesUrl(reader.nextString());
            } else if (name.equals("HasEmergency") && reader.peek() != JsonToken.NULL) {
                hospital.setHasEmergency(reader.nextBoolean());
            } else {
                reader.skipValue();
            }
        }

        reader.endObject();
        return hospital;
    }

    class RequestHospitalsListTask extends AsyncTask<String, String, List> {

        @Override
        protected List doInBackground(String... uri) {
            HttpURLConnection urlConnection = null;
            SortedList hospitalSortedList = null;
            try {
                URL url = new URL(HOSPITALS_LIST_URI);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                List<Hospital> hospitalList = readJsonStream(in);
                hospitalSortedList = new SortedList(new HospitalComparator());
                hospitalSortedList.addAll(hospitalList);
            } catch (Exception e) {
                logger.log(Level.ALL, e.getMessage());
            } finally {
                urlConnection.disconnect();
            }

            return hospitalSortedList;
        }

        @Override
        protected void onPostExecute(List result) {
            super.onPostExecute(result);

            final Spinner hospitalRegionsView = (Spinner) findViewById(R.id.hospitalRegions);
            List regions = Util.INSTANCE.extractHospitalRegions(result);
            final ArrayAdapter hospitalRegionsAdapter = new ArrayAdapter(HospitalsListActivity.this, android.R.layout.simple_list_item_1, regions);
            hospitalRegionsView.setAdapter(hospitalRegionsAdapter);

            final ListView listView = (ListView) findViewById(R.id.listView);
            final HospitalListAdapter hospitalListAdapter = new HospitalListAdapter(HospitalsListActivity.this, R.layout.hospital_list_item, result);
            listView.setAdapter(hospitalListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    final Hospital item = (Hospital) parent.getItemAtPosition(position);
                    Intent hospitalDetailActivityIntent = new Intent(HospitalsListActivity.this, HospitalDetailActivity.class);
                    hospitalDetailActivityIntent.putExtra("HOSPITAL_ID", item.getId());
                    hospitalDetailActivityIntent.putExtra("HOSPITAL_NAME", item.getName());
                    hospitalDetailActivityIntent.putExtra("HOSPITAL_DESCRIPTION", item.getDescription());
                    hospitalDetailActivityIntent.putExtra("HOSPITAL_ADDRESS", item.getAddress());
                    hospitalDetailActivityIntent.putExtra("HOSPITAL_PHONE", item.getPhone());
                    hospitalDetailActivityIntent.putExtra("HOSPITAL_EMAIL", item.getEmail());
                    startActivity(hospitalDetailActivityIntent);
                }
            });

            hospitalRegionsView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String region = hospitalRegionsView.getItemAtPosition(position).toString();
                    hospitalListAdapter.getFilter().filter(region);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }
}
