package gpiocontrol.liml;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.view.View;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class MainActivity extends Activity {

    private int currentBackgroundColor = 0xffffffff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(gpiocontrol.liml.R.layout.activity_main);
        /********************************/
         /*    Define all the buttons    */
        /********************************/
        final Switch led1 = (Switch) findViewById(R.id.Led1);
        final Switch led2 = (Switch) findViewById(R.id.Led2);
        final Switch led3 = (Switch) findViewById(R.id.Led3);
        final Button customColor = (Button) findViewById(R.id.btn_customColor);

        customColor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                final Context context = MainActivity.this;

                ColorPickerDialogBuilder
                        .with(context)
                        .setTitle("Choose color")
                        .initialColor(currentBackgroundColor)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                RGBhandler(selectedColor);
                                customColor.setBackgroundColor(selectedColor);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
                //

            }
        });




        /*******************************************************/
        /*                    OnClickListeners                 */
        /*******************************************************/

        //RED
        led1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    /* Switch led 1 */
                    new Background_get().execute("led1=1");
                } else {
                    new Background_get().execute("led1=0");
                }
            }
        });

        //GREEN
        led2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    /* Switch led 2 */
                    new Background_get().execute("led2=1");
                } else {
                    new Background_get().execute("led2=0");
                }
            }
        });

        //BLUE
        led3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    /* Switch led 3 */
                    new Background_get().execute("led3=1");
                } else {
                    new Background_get().execute("led3=0");
                }
            }
        });


    }



    private void RGBhandler(int RGB) {
        int R = (RGB >>> 16) & 0xFF;
        int G = (RGB >>>  8) & 0xFF;
        int B = RGB & 0xFF;
        String command =    "R=" + String.valueOf(R) +
                            "&G=" + String.valueOf(G) +
                            "&B=" + String.valueOf(B);
        Toast.makeText(this, command, Toast.LENGTH_LONG).show();
        new Background_get().execute(command);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(gpiocontrol.liml.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == gpiocontrol.liml.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*****************************************************/
       /*  This is a background process for connecting      */
      /*           to the server and sending               */
     /*    the GET request withe the added data           */

    /*****************************************************/

    private class Background_get extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                /* RPI local IP*/
                URL url = new URL("http://192.168.178.198/?" + params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    result.append(inputLine).append("\n");

                in.close();
                connection.disconnect();
                return result.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
