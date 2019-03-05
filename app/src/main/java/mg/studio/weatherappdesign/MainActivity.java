package mg.studio.weatherappdesign;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static mg.studio.weatherappdesign.R.drawable.rainy_small;

public class MainActivity extends AppCompatActivity {


    private ImageView pic1=null;
    private ImageView pic2=null;
    private ImageView pic3=null;
    private ImageView pic4=null;
    private ImageView pic5=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
    }
    public String replace(String text){
        text = text.replace( "星期一", "Mon" );
        text = text.replace( "星期二", "Tue" );
        text = text.replace( "星期三", "Wed" );
        text = text.replace( "星期四", "Thu" );
        text = text.replace( "星期五", "Fri" );
        text = text.replace( "星期六", "Sat" );
        text = text.replace( "星期天", "Sun" );
        return (text);
    }


    public void btnClick(View view) {
        new DownloadUpdate().execute();
        AlertDialog.Builder builder = new Builder( this );
        builder.setIcon( R.drawable.button_shape_pressed );
        builder.setTitle( "Update Successful" );
        builder.setPositiveButton( "OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        } );
        AlertDialog b = builder.create();
        b.show();
    }


    private class DownloadUpdate extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = "http://t.weather.sojson.com/api/weather/city/101040100?tdsourcetag=s_pcqq_aiomsg";
            HttpURLConnection urlConnection = null;
            BufferedReader reader;

            try {
                URL url = new URL( stringUrl );

                // Create the request to get the information from the server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod( "GET" );
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader( new InputStreamReader( inputStream ) );

                String line;
                while ((line = reader.readLine()) != null) {
                    // Mainly needed for debugging
                    buffer.append( line + "\n" );
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                //The temperature
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String buffer) {
            //Update the temperature displayed
            String temperature, date;

            temperature = buffer.substring( buffer.indexOf( "wendu" ) + 8, buffer.indexOf( "ganmao" ) - 3 );
            ((TextView) findViewById( R.id.temperature_of_the_day )).setText( temperature );

            date = buffer.substring( buffer.indexOf( "time" ) + 7, buffer.indexOf( "cityInfo" ) - 3 );
            ((TextView) findViewById( R.id.tv_date )).setText( date );


            String date1, date2, date3, date4, today_week;

            {
                int i;
                int con = 0;
                for (i = 1; i < 4; i++) {
                    con = buffer.indexOf( "week", con + 6 );
                }
                date1 = buffer.substring( con + 7, con + 10 );
                date1= replace(date1);
                ((TextView) findViewById( R.id.tomorrow1 )).setText( date1 );
            }

            {
                int i;
                int con = 0;
                for (i = 1; i < 5; i++) {
                    con = buffer.indexOf( "week", con + 6 );
                }
                date2 = buffer.substring( con + 7, con + 10 );
               date2=replace( date2 );
                ((TextView) findViewById( R.id.tomorrow2 )).setText( date2 );
            }
            {
                int i;
                int con = 0;
                for (i = 1; i < 6; i++) {
                    con = buffer.indexOf( "week", con + 6 );
                }
                date3 = buffer.substring( con + 7, con + 10 );
               date3=replace( date3 );
                ((TextView) findViewById( R.id.tomorrow3 )).setText( date3 );
            }
            {
                int i;
                int con = 0;
                for (i = 1; i < 7; i++) {
                    con = buffer.indexOf( "week", con + 6 );
                }
                date4 = buffer.substring( con + 7, con + 10 );
                date4=replace( date4 );
                ((TextView) findViewById( R.id.tomorrow4 )).setText( date4 );
            }
            {
                int i;
                int con = 0;
                for (i = 1; i < 3; i++) {
                    con = buffer.indexOf( "week", con + 6 );
                }
                today_week = buffer.substring( con + 7, con + 10 );
              today_week=replace( today_week );
                ((TextView) findViewById( R.id.week_of_today )).setText( today_week );
            }
            String wendu1,wendu2,wendu3,wendu4;
            {
                int i;
                int con = 0;
                for (i = 1; i < 4; i++) {
                    con = buffer.indexOf( "sunset", con + 6 );
                }
                wendu1 = buffer.substring( con -8, con -3 );
                ((TextView) findViewById( R.id.textView )).setText( wendu1 );
            }
            {
                int i;
                int con = 0;
                for (i = 1; i < 5; i++) {
                    con = buffer.indexOf( "sunset", con + 6 );
                }
                wendu2 = buffer.substring( con -8, con -3 );
                ((TextView) findViewById( R.id.textView2 )).setText( wendu2 );
            }
            {
                int i;
                int con = 0;
                for (i = 1; i < 6; i++) {
                    con = buffer.indexOf( "sunset", con + 6 );
                }
                wendu3 = buffer.substring( con -8, con -3 );
                ((TextView) findViewById( R.id.textView3 )).setText( wendu3 );
            }
            {
                int i;
                int con = 0;
                for (i = 1; i < 7; i++) {
                    con = buffer.indexOf( "sunset", con + 6 );
                }
                wendu4 = buffer.substring( con -8, con -3 );
                ((TextView) findViewById( R.id.textView4 )).setText( wendu4 );
            }


            String weather1,weather2,weather3,weather4,weather5;
            {

                int i;
                int con1 = 0;
                for (i = 1; i < 4; i++) {
                    con1 = buffer.indexOf( "type", con1 + 6 );
                }
                int con2 = 0;
                for (i = 1; i < 4; i++) {
                    con2 = buffer.indexOf( "notice", con2 + 6 );
                }
                weather1 = buffer.substring( con1 + 7, con2 - 3 );
                pic1 = (ImageView) findViewById( R.id.imageView11 );
                    if (weather1.equals( "小雨" )) {
                        pic1.setImageResource( R.drawable.rainy_small );
                    } else if (weather1.equals( "晴" )) {
                        pic1.setImageResource( R.drawable.sunny_small );
                    } else if (weather1.equals( "阴" )) {
                        pic1.setImageResource( R.drawable.partly_sunny_small );
                    } else {
                        pic1.setImageResource( R.drawable.windy_small );
                    }

            }
            {

                int i;
                int con1 = 0;
                for (i = 1; i < 5; i++) {
                    con1 = buffer.indexOf( "type", con1 + 6 );
                }
                int con2 = 0;
                for (i = 1; i < 5; i++) {
                    con2 = buffer.indexOf( "notice", con2 + 6 );
                }
                weather2 = buffer.substring( con1 + 7, con2 - 3 );
                pic2 = (ImageView) findViewById( R.id.imageView12 );
                if (weather2.equals( "小雨" )) {
                    pic2.setImageResource( R.drawable.rainy_small );
                } else if (weather2.equals( "晴" )) {
                    pic2.setImageResource( R.drawable.sunny_small );
                } else if (weather2.equals( "阴" )) {
                    pic2.setImageResource( R.drawable.partly_sunny_small );
                } else {
                    pic2.setImageResource( R.drawable.windy_small );
                }
            }
            {

                int i;
                int con1 = 0;
                for (i = 1; i < 6; i++) {
                    con1 = buffer.indexOf( "type", con1 + 6 );
                }
                int con2 = 0;
                for (i = 1; i < 6; i++) {
                    con2 = buffer.indexOf( "notice", con2 + 6 );
                }
                weather3 = buffer.substring( con1 + 7, con2 - 3 );
                pic3 = (ImageView) findViewById( R.id.imageView13 );
                if (weather3.equals( "小雨" )) {
                    pic3.setImageResource( R.drawable.rainy_small );
                } else if (weather3.equals( "晴" )) {
                    pic3.setImageResource( R.drawable.sunny_small );
                } else if (weather3.equals( "阴" )) {
                    pic3.setImageResource( R.drawable.partly_sunny_small );
                } else {
                    pic3.setImageResource( R.drawable.windy_small );
                }
            }
            {

                int i;
                int con1 = 0;
                for (i = 1; i < 7; i++) {
                    con1 = buffer.indexOf( "type", con1 + 6 );
                }
                int con2 = 0;
                for (i = 1; i < 7; i++) {
                    con2 = buffer.indexOf( "notice", con2 + 6 );
                }
                weather4 = buffer.substring( con1 + 7, con2 - 3 );
                pic4 = (ImageView) findViewById( R.id.imageView14);
                if (weather4.equals( "小雨" )) {
                    pic4.setImageResource( R.drawable.rainy_small );
                } else if (weather3.equals( "晴" )) {
                    pic4.setImageResource( R.drawable.sunny_small );
                } else if (weather3.equals( "阴" )) {
                    pic4.setImageResource( R.drawable.partly_sunny_small );
                } else {
                    pic4.setImageResource( R.drawable.windy_small );
                }
            }{

                int i;
                int con1 = 0;
                for (i = 1; i < 3; i++) {
                    con1 = buffer.indexOf( "type", con1 + 6 );
                }
                int con2 = 0;
                for (i = 1; i < 3; i++) {
                    con2 = buffer.indexOf( "notice", con2 + 6 );
                }
                weather5 = buffer.substring( con1 + 7, con2 - 3 );
                pic5 = (ImageView) findViewById( R.id.img_weather_condition );
                if (weather4.equals( "小雨" )) {
                    pic5.setImageResource( R.drawable.rainy_small );
                } else if (weather3.equals( "晴" )) {
                    pic5.setImageResource( R.drawable.sunny_small );
                } else if (weather3.equals( "阴" )) {
                    pic5.setImageResource( R.drawable.partly_sunny_small );
                } else {
                    pic5.setImageResource( R.drawable.windy_small );
                }
            }
        }
    }
}
