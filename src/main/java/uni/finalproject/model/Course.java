package uni.finalproject.model;

import java.util.*;

/**
 * Class Course represents a course object
 */
public class Course {
    private long courseID;
    private String courseName;
    private int teacherID;
    private int numberStudentsMax;
    private int totalCredits;

    public Course(){}

    public Course(long courseID,String courseName, int teacherID, int numberStudentsMax, int credits) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.teacherID = teacherID;
        this.numberStudentsMax = numberStudentsMax;
        this.totalCredits = credits;
    }


    public long getCourseID() {
        return courseID;
    }

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getTeacherID() {
        return teacherID;
    }


    public int getNumberStudentsMax() {
        return numberStudentsMax;
    }

    public int getCredits() {
        return totalCredits;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setTeacher(int teacherID) {
        this.teacherID = teacherID;
    }

    public void setNumberStudentsMax(int numberStudentsMax) {
        this.numberStudentsMax = numberStudentsMax;
    }

    public void setCredits(int credits) {
        this.totalCredits = credits;
    }


    /**
     *
     * @param o object to be compared
     * @return true or false, depending on whether objects are equal or not
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseName, course.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseName, teacherID, numberStudentsMax, totalCredits);
    }

    /**
     * @return the object in a string format
     */
    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseID +
                ", courseName='" + courseName + '\'' +
                ", teacherID=" + teacherID +
                ", numberStudentsMax=" + numberStudentsMax +
                ", credits=" + totalCredits +
                "}\n";
    }

}

