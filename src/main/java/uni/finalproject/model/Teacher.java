package uni.finalproject.model;

import java.util.Objects;

/**
 * Class Teacher represents a teacher object
 */
public class Teacher extends Person {
    private long teacherID;
    private String firstName;
    private String lastName;

    public Teacher(){
    }

    public Teacher( long teacherID,String firstName, String lastName) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.teacherID = teacherID;

    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(long teacherID) {
        this.teacherID = teacherID;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return teacherID == teacher.teacherID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherID);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherID=" + teacherID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                "}\n";
    }
}

