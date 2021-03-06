/*
 *
 *  * @author Segla Boladji Vinny Trinite Adjibi, Wuyeh Jobe
 *  * AndrewID : vadjibi, jwuyeh
 *  * Program : MSIT
 *  *
 *  * On our honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work.
 *
 */

package edu.cmu.officient.model;

import android.content.ContentValues;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.cmu.officient.logic.ApplicationManager;
import edu.cmu.officient.storage.OfficientLocalDbContract.*;

public class Assignment implements Scannable, Serializable {
    private int id, expectedTime, courseId; // Expected time in hours
    Date publishedOn, deadline, availableTill;
    private String title;
    private Course course;

    public Assignment() { // Constructs an empty object - Just for testing now

    }

    public Assignment(int id, int expectedTime, Date publishedOn, Date deadline, Date availableTill,
                      String title, Course course) {
        this.id = id;
        this.expectedTime = expectedTime;
        this.publishedOn = publishedOn;
        this.deadline = deadline;
        this.availableTill = availableTill;
        this.title = title;
        this.course = course;
    }

    @Override
    public boolean isInRange() {
        Date now = new Date();
        return now.after(publishedOn) && now.before(availableTill);
    }

    @Override
    public boolean userCanAccess(int id) {
        // Based on if the student is in the class, returns true
        return true;
    }

    @Override
    public ContentValues getStorableData() {
        ContentValues values = new ContentValues();
        values.put(ScannedAssignment.COL_ASSIGNMENT_ID, id);
        values.put(ScannedAssignment.COL_ASSIGNMENT_TITLE, title);
        values.put(ScannedAssignment.COL_DEADLINE, deadline.toString());
        values.put(ScannedAssignment.COL_PUBLICATION_DATE, publishedOn.toString());
        values.put(ScannedAssignment.COL_LATE_SUBMISSION_DATE, availableTill.toString());
        if (course != null) {
            values.put(ScannedAssignment.COL_COURSE_ID, "");
            values.put(ScannedAssignment.COL_COURSE_TITLE, course.getTitle());
        }
        
        return values;
    }

    @Override
    public Map<String, Object> getStorableDataMap() {
        Map<String, Object> data = new HashMap<>();
        data.put("user", ApplicationManager.getInstance(null).getLoggedInUser().getAndrewId());
        data.put("assignment_id", "" + id);
        data.put("course_id", courseId);
        data.put("title", title);
        return data;
    }

    @Override
    public String getCollectionName() {
        return ScannedAssignment.TABLE_NAME;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Assignment) {
            Assignment o = (Assignment) obj;
            boolean ans = id==o.id && expectedTime==o.expectedTime && publishedOn.equals(o.publishedOn) &&
                    deadline.equals(o.deadline) && availableTill.equals(o.availableTill) && title.equals(o.title);
            if (course != null)
                return ans && course.equals(o.course);
            return ans;
        }
        return false;
    }

    public String getType(){
        return "Assignment";
    }

    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(int expectedTime) {
        this.expectedTime = expectedTime;
    }

    public Date getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getAvailableTill() {
        return availableTill;
    }

    public void setAvailableTill(Date availableTill) {
        this.availableTill = availableTill;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return title;
    }
}
