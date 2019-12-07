/*
 *
 *  * @author Segla Boladji Vinny Trinite Adjibi
 *  * AndrewID : vadjibi
 *  * Program : MSIT
 *  *
 *  * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work.
 *
 */

package edu.cmu.officient.ui.assignments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

import edu.cmu.officient.DBCommunication.RequestData;
import edu.cmu.officient.R;
import edu.cmu.officient.model.Course;

public class AddAssignmentFragment extends Fragment
{

    private Spinner selectCourse;
    private Button addAssignmentBtn;
    private EditText assign_title;
    private EditText expect_time;
    private EditText deadline;
    private EditText availability;
    private ProgressBar progressBar;
  //  private Context context;
    private ArrayAdapter adapter;
    private LocalDateTime posted_on;
    ArrayList<Course> courses;
    private AppCompatActivity activity;

    Course selectedCourse;

    public AddAssignmentFragment(AppCompatActivity activity)
    {
       this.activity=activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_add_assigment, container, false);
        progressBar =view.findViewById(R.id.load);
        selectCourse=view.findViewById(R.id.select_course);
        new CourseList().execute("Courses");
        addAssignmentBtn=view.findViewById(R.id.add_course);
        assign_title=view.findViewById(R.id.ass_title);
        expect_time=view.findViewById(R.id.exp_time);
        deadline=view.findViewById(R.id.due_date);
        availability=view.findViewById(R.id.avail_time);

        courses = new ArrayList<>();
        adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_dropdown_item, courses);
        selectCourse.setAdapter(adapter);

        addAssignmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(selectedCourse!=null)
                {
                    new AddAssignment().execute("addAssignment",assign_title.getText().toString(),deadline.getText().toString(),posted_on.toString(),
                            availability.getText().toString(),expect_time.getText().toString(), selectedCourse.getId() + "");
                }
                else{
                    Toast.makeText(activity, "Please select a course for the assignment", Toast.LENGTH_SHORT).show();
                }
            }
        });
      return view;
    }

    private class AddAssignment extends AsyncTask<String, String, String>
    {
        private JSONObject jsonObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String[] args)
        {
            String message=" ";
            String[] attributes = new String[]{"addAssignment", "title", "deadline", "published_on", "avalability","expected_time","course_id"};
            RequestData requestData = new RequestData(activity, "http://gamfruits.com/officient_api/functions.php", attributes, args);
            jsonObject = requestData.getResponse();
            System.out.println(jsonObject);
            try {
                message = jsonObject.getString("message");
            } catch (JSONException e) {
                message = "error";
            }
            return message;
        }

        protected void onPostExecute(String result){
            progressBar.setVisibility(View.GONE);
            if (result.equalsIgnoreCase("success")){
                Toast.makeText(activity, "Assignment added Successfully", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(context, CoursesList.class);
//                startActivity(intent);
            }
            else if(result.equalsIgnoreCase("error")){
                Toast.makeText(activity, "Unable to connect to the internet. Assignment not added", Toast.LENGTH_SHORT).show();
            }
            else if (result.equalsIgnoreCase("failed")){
                Toast.makeText(activity, "Problem with App. Contact admin.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class CourseList extends AsyncTask<String, String, String>
    {
        private JSONObject jsonObject;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String[] args) {
            String message;
            String[] attributes = new String[]{"courses"};
            RequestData requestData = new RequestData(activity,"http://gamfruits.com/officient_api/functions.php", attributes, args);
            jsonObject = requestData.getResponse();
            System.out.println(jsonObject);
            if(jsonObject!=null){
                try {
                    message = jsonObject.getString("message");
                } catch (JSONException e) {
                    message = "error";
                    e.printStackTrace();
                }
            }
            else {
                message = "error";
            }
            return message;
        }

        protected void onPostExecute(String result){
            progressBar.setVisibility(View.GONE);
            System.out.println(result);
            edu.cmu.officient.util.DateConversion dateConversion = new edu.cmu.officient.util.DateConversion();
            if (result.equalsIgnoreCase("success")){
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    JSONObject row;
                    for(int i=0;i<jsonArray.length();i++){
                        row = (JSONObject) jsonArray.get(i);
                        courses.add(new Course(row.getInt("id"),row.getString("title"),row.getString("code")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(activity, "Course List Retrieved", Toast.LENGTH_SHORT).show();
            }
            else if(result.equalsIgnoreCase("error")){
                Toast.makeText(activity, "Unable to connect to the internet. The course list won't be updated", Toast.LENGTH_SHORT).show();
            }
            else if (result.equalsIgnoreCase("no_data")){
                //items.add("Term list empty");
                Toast.makeText(activity, "there is no such course", Toast.LENGTH_SHORT).show();
            }
            adapter.notifyDataSetChanged();

        }
    }

}
