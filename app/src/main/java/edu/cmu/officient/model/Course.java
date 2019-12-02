/*
 *
 *  * @author Segla Boladji Vinny Trinite Adjibi
 *  * AndrewID : vadjibi
 *  * Program : MSIT
 *  *
 *  * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work.
 *
 */

package edu.cmu.officient.model;

import androidx.annotation.NonNull;

import java.util.List;

public class Course implements Scannable {
    private int id;
    private String title, code;
    private Term term;
    private List<Instructor> instructors;
    private List<Student> students;
    private List<OfficeHours> officeHours;

    public Course(){

    }

    public Course(int id, String title, String code, Term term, List<Instructor> instructors, List<Student> students, List<OfficeHours> oh) {
        this.id = id;
        this.title = title;
        this.code = code;
        this.term = term;
        this.instructors = instructors;
        this.students = students;
        officeHours = oh;
    }

    @Override
    public boolean isInRange() { // Get the list of all office hours and check whether it's okay
        // Should check if there is a valid office hours being held at the moment
        return true;
    }

    @Override
    public boolean userCanAccess(int id) {
        // Returns true if the user can access a certain office hour i.e there are registered in the class
        return isAStudentOfCourse(id);
    }

    public boolean isAStudentOfCourse(int id) {
        for (Student student : students) {
            if (student.getId() == id)
                return true;
        }
        return false;
    }

    public boolean isAStudentOfCourse(Student student) {
        for (Student _student : students) {
            if (student.equals(_student))
                return true;
        }
        return false;
    }

    public OfficeHours getAppropriateOfficeHours(int instructorId) {
        for (OfficeHours oh : officeHours) {
            if (oh.isInRange() && oh.getHolder().getId() == instructorId)
                return oh;
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Course) {
            Course course = (Course) obj;
            return id==course.id && title.equals(course.title) && code.equals(course.code) && term.equals(course.term);
        }
        return false;
    }

    @Override
    public String getType() {
        return "Assignment";
    }

    @NonNull
    @Override
    public String toString() {
        return title + " - " + term;
    }
}
