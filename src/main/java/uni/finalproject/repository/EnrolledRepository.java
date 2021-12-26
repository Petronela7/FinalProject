package uni.finalproject.repository;

import uni.finalproject.connection.DatabaseConnection;
import uni.finalproject.model.Course;
import uni.finalproject.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrolledRepository {
    static final String QUERY_FIND = "SELECT * FROM Enrolled WHERE studentID = ? AND courseID = ?";
    static final String QUERY_FIND_COURSE = "SELECT * FROM Enrolled WHERE courseID = ?";
    static final String QUERY_FIND_STUDENT = "SELECT * FROM Enrolled WHERE studentID = ?";
    static final String QUERY_FIND_TEACHER = "SELECT * FROM Enrolled WHERE teacherID = ?";
    static final String QUERY_INSERT = "INSERT INTO Enrolled VALUES (?, ?)";
    static final String QUERY_DELETE_BY_COURSE = "DELETE FROM Enrolled WHERE courseID= ?";
    static final String QUERY_DELETE_ALL = "TRUNCATE Enrolled";
    private final Connection connection;
    private ResultSet resultSet;


    public EnrolledRepository() throws SQLException {
        super();
        this.connection = new DatabaseConnection().getConnection();
    }

    public boolean findOne(long studentID, long courseID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND);
        preparedStatement.setLong(1, studentID);
        preparedStatement.setLong(2, courseID);
        this.resultSet = preparedStatement.executeQuery();
        return resultSet.next();

    }

    public List<Long> findStudentsByCourse(long ID) throws SQLException {
        List<Long> studentList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_COURSE);
        preparedStatement.setLong(1, ID);
        this.resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            studentList.add(resultSet.getLong("studentID"));
        }
        return studentList;
    }

    public List<Long> findCoursesByStudent(long ID) throws SQLException {
        List<Long> studentList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_STUDENT);
        preparedStatement.setLong(1, ID);
        this.resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            studentList.add(resultSet.getLong("courseID"));
        }
        return studentList;
    }


    public void save(long studentID, long courseID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT);
        if (!findOne(studentID, courseID)) {
            preparedStatement.setLong(1, studentID);
            preparedStatement.setLong(2, courseID);
            preparedStatement.executeUpdate();

        }
    }

    public void deleteByCourse(long courseID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE_BY_COURSE);
            preparedStatement.setLong(1, courseID);
            preparedStatement.executeUpdate();


    }

    public void deleteAllEntries() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE_ALL);
        preparedStatement.executeUpdate();

    }

}
