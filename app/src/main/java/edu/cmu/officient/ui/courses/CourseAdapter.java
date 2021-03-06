/*
 *
 *  * @author Segla Boladji Vinny Trinite Adjibi
 *  * AndrewID : vadjibi
 *  * Program : MSIT
 *  *
 *  * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work.
 *
 */

package edu.cmu.officient.ui.courses;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

import edu.cmu.officient.R;
import edu.cmu.officient.model.Course;
import edu.cmu.officient.ui.tasks.TaskAdapter;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private List<Course> courses;
    private AppCompatActivity parentActivity;

    public CourseAdapter(AppCompatActivity activity, List<Course> courseList){
        parentActivity = activity;
        courses = courseList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Course course = courses.get(position);
        holder.title.setText(course.getTitle());
        holder.term.setText(course.getTerm().toString());
        holder.officeHoursCount.setText(parentActivity.getString(R.string.office_hours_count, course.getOfficeHours().size()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the detail fragment
                Intent intent = new Intent(parentActivity, CourseDetailActivity.class);
                intent.putExtra("course", course);
                parentActivity.startActivity(intent);
/*
                parentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CourseDetailFragment(parentActivity, course))
                        .addToBackStack(null).commit();
*/
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_course, parent, false);
        return new CourseAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title, term, officeHoursCount;

        ViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.course_block);
            title = view.findViewById(R.id.course_title);
            term = view.findViewById(R.id.term);
            officeHoursCount = view.findViewById(R.id.office_hours_count);
        }
    }

}
