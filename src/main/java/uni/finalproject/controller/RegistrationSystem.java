package uni.finalproject.controller;

import uni.finalproject.model.Course;
import uni.finalproject.model.Student;
import uni.finalproject.model.Teacher;
import uni.finalproject.repository.CourseRepository;
import uni.finalproject.repository.EnrolledRepository;
import uni.finalproject.repository.StudentRepository;
import uni.finalproject.repository.TeacherRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationSystem {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final EnrolledRepository enrolledRepository;

    public RegistrationSystem() throws SQLException {
        this.courseRepository = new CourseRepository();
        this.studentRepository = new StudentRepository();
        this.teacherRepository = new TeacherRepository();
        this.enrolledRepository = new EnrolledRepository();
    }

    public TeacherRepository getTeacherRepository() {
        return teacherRepository;
    }

    public CourseRepository getCourseRepository() {
        return courseRepository;
    }

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    public EnrolledRepository getEnrolledRepository() {
        return enrolledRepository;
    }

    /**
     * Function addCourse takes the attributes of a course as input and calls the Create function in the repository
     * @return true if the course was created successfully
     */
    public boolean addCourse(int courseId, String name, int teacherId, int credits, int maxEnrollment) throws SQLException {

        Course c = new Course(courseId, name, teacherId, credits, maxEnrollment);
        courseRepository.save(c);
        return true;
    }

    /**
     * Function addStudent takes the attributes of a student as input and calls the Create function in the repository
     * @return true if the student was created successfully
     */
    public boolean addStudent(long studentID,String firstName, String lastName) throws SQLException {
        Student s = new Student(studentID,firstName, lastName);
        studentRepository.save(s);
        return true;
    }

    /**
     * Function addCourse takes the attributes of a course as input and calls the Create function in the repository
     * @return true if the course was created successfully
     */
    public boolean addTeacher(String firstName, String lastName, int teacherId) throws SQLException {
        Teacher teacher = new Teacher(teacherId,firstName, lastName);
        teacherRepository.save(teacher);
        return true;
    }

    /**
     * @return true if the student is successfully enrolled in course
     */
    public boolean register(int studentID, int courseID) throws SQLException {
        if (courseRepository.findOne(courseID).getCourseID() == 0 && studentRepository.findOne(studentID).getStudentID() == 0) {
            System.out.println("Either the course or the student don't exist");
            return false;
        }
        if (enrolledRepository.findOne(studentID, courseID)) {
            System.out.println("You are already registered");
            return false;
        }
        if (retrieveStudentsFromCourse(courseID).size() + 1 > courseRepository.findOne(courseID).getNumberStudentsMax()) {
            System.out.println("There are no free places");
            return false;
        }
        if (courseRepository.findOne(courseID).getCredits() + studentRepository.findOne(studentID).getTotalCredits() > 30) {
            System.out.println("Your credits exceed the limit of 30");
            return false;
        }

        Student student = studentRepository.findOne(studentID);
        Course course = courseRepository.findOne(courseID);
        student.setTotalCredits(student.getTotalCredits() + course.getCredits());//update credits
        studentRepository.update(student);
        enrolledRepository.save(student.getStudentID(), course.getCourseID());
        System.out.println("Successfully registered");
        return true;


    }


    /**
     * @return the list of courses with free places
     */
    public List<Course> retrieveCoursesWithFreePlaces() throws SQLException {
        List<Course> coursesWithFreePlaces = new ArrayList<>();
        for (Course course : courseRepository.findAll())
            if (course.getNumberStudentsMax() > enrolledRepository.findStudentsByCourse(course.getCourseID()).size())
                coursesWithFreePlaces.add(course);
        return coursesWithFreePlaces;
    }

    /**
     * @param courseID is a course
     * @return the list of students attending the specified course
     */
    public List<Student> retrieveStudentsFromCourse(long courseID) throws SQLException {
        List<Long> studentIDs = enrolledRepository.findStudentsByCourse(courseID);
        List<Student> students = (ArrayList<Student>)studentRepository.findAll();
        students.removeIf(s -> !studentIDs.contains(s.getStudentID()));
        return students;

    }

    /**
     * @return a list of all courses
     */
    public List<Course> getAllCourses() throws SQLException {
        return (ArrayList<Course>) courseRepository.findAll();
    }

    /**
     * updates the credits of a course
     * updates the number of credits of student enrolled in that course
     *
     * @param course  is a course
     * @param credits is the new number of credits
     */
    public boolean updateCredits(Course course, int credits) throws SQLException {
        if (courseRepository.findOne(course.getCourseID()).getCourseID() == 0)
            return false;
        int difference = course.getCredits() - credits;
        for (Student s : retrieveStudentsFromCourse(course.getCourseID())) {
            s.setTotalCredits(s.getTotalCredits() - difference);
            studentRepository.update(s);
        }
        course.setCredits(credits);
        courseRepository.update(course);
        return true;
    }

    /**
     * Deletes the course
     *
     * @param course  is the course deleted
     * @param teacher is the teacher deleting the course
     */
    public boolean deleteCourse(Teacher teacher, Course course) throws SQLException {
        if (teacherRepository.findOne(teacher.getTeacherID()).getTeacherID() == 0 || courseRepository.findOne(course.getCourseID()).getCourseID() == 0)
            return false;
        for (Student s : retrieveStudentsFromCourse(course.getCourseID())) {
            s.setTotalCredits(s.getTotalCredits() - course.getCredits());
            studentRepository.update(s);
        }
        enrolledRepository.deleteByCourse(course.getCourseID());
        courseRepository.delete(course.getCourseID());
        return true;

    }

}
