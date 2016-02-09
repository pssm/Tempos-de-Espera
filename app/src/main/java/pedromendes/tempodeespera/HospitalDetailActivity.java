package pedromendes.tempodeespera;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.JsonToken;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HospitalDetailActivity extends AppCompatActivity {

    private Logger logger = Logger.getLogger(HospitalDetailActivity.class.getName());

    private static final String HOSPITAL_DETAIL_URL = "http://tempos.min-saude.pt/api.php/standbyTime/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);
        Long hospitalId = getIntent().getExtras().getLong("HOSPITAL_ID");
        String hospitalName = getIntent().getExtras().getString("HOSPITAL_NAME");
        String hospitalDescription = getIntent().getExtras().getString("HOSPITAL_DESCRIPTION");
        String hospitalAddress = getIntent().getExtras().getString("HOSPITAL_ADDRESS");
        String hospitalPhone = getIntent().getExtras().getString("HOSPITAL_PHONE");
        String hospitalEmail = getIntent().getExtras().getString("HOSPITAL_EMAIL");



        TextView hospitalNameView = (TextView) findViewById(R.id.hospitalName);
        hospitalNameView.setText(hospitalDescription);
        TextView hospitalDescriptionView = (TextView) findViewById(R.id.hospitalDescription);
        hospitalDescriptionView.setText(hospitalName);


        TextView hospitalAddressView = (TextView) findViewById(R.id.hospitalAddress);
        hospitalAddressView.setText(hospitalName);

        new RequestHospitalDetailTask().execute(hospitalId.toString());
    }


    class RequestHospitalDetailTask extends AsyncTask<String, String, HospitalEmergencyDetail> {

        @Override
        protected HospitalEmergencyDetail doInBackground(String... hospitalId) {
            HttpURLConnection urlConnection = null;
            HospitalEmergencyDetail hospitalEmergencyDetail = null;
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
        protected void onPostExecute(HospitalEmergencyDetail result) {
            super.onPostExecute(result);
            if (result == null) {
                return;
            }
            final TextView emergencyNameView = (TextView) findViewById(R.id.emergencyName);
            emergencyNameView.setText(result.getName());

            TextView redQueueTimeView = (TextView) findViewById(R.id.redQueueTime);
            redQueueTimeView.setText(result.getRedQueue().getTime());
            TextView orangeQueueTimeView = (TextView) findViewById(R.id.orangeQueueTime);
            orangeQueueTimeView.setText(result.getOrangeQueue().getTime());
            TextView yellowQueueTimeView = (TextView) findViewById(R.id.yellowQueueTime);
            yellowQueueTimeView.setText(result.getYellowQueue().getTime());
            TextView blueQueueTime = (TextView) findViewById(R.id.blueQueueTime);
            blueQueueTime.setText(result.getBlueQueue().getTime());
            TextView greenQueueTimeView = (TextView) findViewById(R.id.greenQueueTime);
            greenQueueTimeView.setText(result.getGreenQueue().getTime());

        }
    }

    public HospitalEmergencyDetail readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readHospitalDetailGetResponse(reader);
        } finally {
            reader.close();
        }
    }


    private HospitalEmergencyDetail readHospitalDetailGetResponse(JsonReader reader) throws IOException {
        HospitalEmergencyDetail hospitalEmergencyDetail = null;
        reader.beginObject();
        while (reader.hasNext()) {
            hospitalEmergencyDetail = readResult(reader);
        }
        reader.endObject();
        return hospitalEmergencyDetail;
    }

    public HospitalEmergencyDetail readResult(JsonReader reader) throws IOException {
        HospitalEmergencyDetail hospitalEmergencyDetail = new HospitalEmergencyDetail();
        String name = reader.nextName();
        if (name.equals("Result")) {
            reader.beginArray();
            reader.beginObject();
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
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            reader.endArray();
        }
        return hospitalEmergencyDetail;
    }

    public void fillQueue(JsonReader reader, HospitalEmergencyDetail.EmergencyQueue queue) throws IOException {
        reader.nextName();
        int time = reader.nextInt();
        reader.nextName();
        int length = reader.nextInt();
        queue.setTime(time);
        queue.setLength(length);
    }
}
