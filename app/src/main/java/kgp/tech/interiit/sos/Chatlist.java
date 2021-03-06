package kgp.tech.interiit.sos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import kgp.tech.interiit.sos.Utils.ssoft;

public class Chatlist extends AppCompatActivity {
    String TAG = "YOLO";
    private Toolbar toolbar;
    private ListView listView;
    public static JSONObject out_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);

        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(new SpannableString("Analyze"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }

        listView = (ListView) findViewById(R.id.list_data);
        listView.setEmptyView(findViewById(R.id.emptyviewtxt));

        MyAdapter adapter = new MyAdapter(getApplicationContext());
        listView.setAdapter(adapter);

        listView.setDivider(null);
        listView.setDividerHeight(0);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View v, int position, long id) {
                //Get your item here with the position

                final Intent intent = new Intent(Chatlist.this, AChartEnginePieChartActivity.class);

                //FloatingActionsMenu fm = ((FloatingActionsMenu) getActivity().findViewById(R.id.new_up));

//                TextView tv1 = (TextView) v.findViewById(R.id.name);
//                String name = tv1.getText().toString();
//                intent.putExtra("name", name);
                startActivity(intent);


            }
        });

        String ret = "";
        JSONObject in_file_json = new JSONObject();
        //if(!isFirst_write) {
        // It doesnt handle some exceptions, like if it fails to load, whole data will be erased
        // as out data doesnt give a damn about in data not being loaded
        if(true) {
            try {
                InputStream inputStream = openFileInput("log_loc.txt");

                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((receiveString = bufferedReader.readLine()) != null) {
                        stringBuilder.append(receiveString);
                        int breakpoint = 0;
                        for (int i = 0; i < receiveString.length(); i++) {
                            if (receiveString.charAt(i) == '-') {
                                breakpoint = i;
                            }
                        }
                        try {
                            in_file_json.put(receiveString.substring(0, breakpoint), new Long(receiveString.substring(breakpoint + 1, receiveString.length())));
                        }
                        catch (Exception e) {
                            Log.e("JSON EXCEPTION", e.toString());
                        }
                    }

                    inputStream.close();
                    ret = stringBuilder.toString();
                    Log.e(TAG + "List: Found content", ret);
                }
            } catch (FileNotFoundException e) {
                Log.e("login activity", "File not found: " + e.toString());
            } catch (IOException e) {
                Log.e("login activity", "Can not read file: " + e.toString());
            }
        }

        ssoft s = new ssoft();
        out_json = s.run(in_file_json);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chatlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class MyAdapter extends BaseAdapter {

    private Context context;
    String[] txt,name;
//    int[] images={R.drawable.im0,R.drawable.im1,R.drawable.im2,R.drawable.im3,R.drawable.im4,R.drawable.im5,R.drawable.im6,R.drawable.im7,R.drawable.im8,R.drawable.im9,R.drawable.im10,R.drawable.im11,R.drawable.im12};


    MyAdapter(Context context)
    {
        this.context=context;
        name=context.getResources().getStringArray(R.array.sample_names);
        txt=context.getResources().getStringArray(R.array.sample_text);
//        shuffleArray(images);


        Random rng = new Random();
        List<String> arr = Arrays.asList(txt);
        Collections.shuffle(arr, rng);
        arr.toArray(txt);


        rng = new Random();
        arr = Arrays.asList(name);
        Collections.shuffle(arr, rng);
        arr.toArray(name);
    }



    static void shuffleArray(int[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return name[position];
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View row=null;


        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.list_single, parent, false);
        }
        else
        {
            row=convertView;
        }
        TextView tv1=(TextView) row.findViewById(R.id.name);
        TextView tv2=(TextView) row.findViewById(R.id.txt);
        TextView tv3=(TextView) row.findViewById(R.id.time);
        CircleImageView iv1=(CircleImageView) row.findViewById(R.id.img);
        iv1.setImageResource(R.drawable.sample_man);

        if(name[position].equals("Social")) {
            tv1.setText(name[position]);
            try {
                tv2.setText(Chatlist.out_json.getString("social_str"));
                long time_in = Chatlist.out_json.getLong("social_time");
                long hour = time_in/60;
                long minute = time_in%60;
                String f1 = String.format("%02d", hour);
                String f2 = String.format("%02d", minute);
                tv3.setText(f1 + ":" + f2);
                iv1.setImageResource(R.drawable.social);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(name[position].equals("Work")) {
            tv1.setText(name[position]);
            try {
                tv2.setText(Chatlist.out_json.getString("work_str"));
                long time_in = Chatlist.out_json.getLong("work_time");
                long hour = time_in/60;
                long minute = time_in%60;
                String f1 = String.format("%02d", hour);
                String f2 = String.format("%02d", minute);
                tv3.setText(f1 + ":" + f2);
                iv1.setImageResource(R.drawable.work);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(name[position].equals("Leisure")) {
            tv1.setText(name[position]);
            try {
                tv2.setText(Chatlist.out_json.getString("leisure_str"));
                long time_in = Chatlist.out_json.getLong("leisure_time");
                long hour = time_in/60;
                long minute = time_in%60;
                String f1 = String.format("%02d", hour);
                String f2 = String.format("%02d", minute);
                tv3.setText(f1 + ":" + f2);
                iv1.setImageResource(R.drawable.leisure);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(name[position].equals("Health")) {
            tv1.setText(name[position]);
            try {
                tv2.setText(Chatlist.out_json.getString("health_str"));
                long time_in = Chatlist.out_json.getLong("health_time");
                long hour = time_in/60;
                long minute = time_in%60;
                String f1 = String.format("%02d", hour);
                String f2 = String.format("%02d", minute);
                tv3.setText(f1 + ":" + f2);
                iv1.setImageResource(R.drawable.health);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(name[position].equals("Others")) {
            tv1.setText(name[position]);
            try {
                tv2.setText(Chatlist.out_json.getString("others_str"));
                long time_in = Chatlist.out_json.getLong("others_time");
                long hour = time_in/60;
                long minute = time_in%60;
                String f1 = String.format("%02d", hour);
                String f2 = String.format("%02d", minute);
                tv3.setText(f1 + ":" + f2);
                iv1.setImageResource(R.drawable.others);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Log.e("In else", name[position]);
            tv1.setText(name[position]);
            tv2.setText("YOLO");
        }

        Random r = new Random();

        return row;
    }

}

