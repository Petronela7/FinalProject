package uni.finalproject.repository;

import uni.finalproject.connection.DatabaseConnection;
import uni.finalproject.model.Course;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository implements ICrudRepository<Course> {
    static final String QUERY_SELECT = "SELECT * FROM Course";
    static final String QUERY_FIND = "SELECT * FROM Course WHERE courseID = ?";
    static final String QUERY_INSERT = "INSERT INTO Course VALUES (?, ?, ?, ?, ?)";
    static final String QUERY_UPDATE = "UPDATE Course SET totalCredits = ?, numberStudentsMax = ?,teacherID = ? WHERE courseID = ?";
    static final String QUERY_DELETE = "DELETE FROM Course WHERE courseID = ?";
    private final Connection connection;
    private ResultSet resultSet;


    public CourseRepository() throws SQLException {
        super();
        this.connection = new DatabaseConnection().getConnection();
    }

    /**
     * @param ID -the ID of the entity to be returned id must not be null
     * @return the entity with the specified id or null - if there is no entity with the given id
     */
    @Override
    public Course findOne(long ID) throws SQLException {
       PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND);
            preparedStatement.setLong(1, ID);
            this.resultSet = preparedStatement.executeQuery();

        if (resultSet != null) {
            Course course = new Course();
            while (resultSet.next()) {
                course.setCourseID(resultSet.getLong("courseID"));
                course.setCourseName(resultSet.getString("courseName"));
                course.setTeacher(resultSet.getInt("teacherID"));
                course.setNumberStudentsMax(resultSet.getInt("numberStudentsMax"));
                course.setCredits(resultSet.getInt("totalCredits"));
            }
            return course;
        }
        return null;
    }

    /**
     * @return all entities
     */
    @Override
    public Iterable<Course> findAll() throws SQLException {
        List<Course> courseList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT);
        this.resultSet = preparedStatement.executeQuery(QUERY_SELECT);

        while (resultSet.next()) {
            Course course = new Course();
            course.setCourseID(resultSet.getLong("courseID"));
            course.setCourseName(resultSet.getString("courseName"));
            course.setTeacher(resultSet.getInt("teacherID"));
            course.setNumberStudentsMax(resultSet.getInt("numberStudentsMax"));
            course.setCredits(resultSet.getInt("totalCredits"));
            courseList.add(course);

        }

        return courseList;


    }

    /**
     * @param entity entity must be not null
     * @return null- if the given entity is saved otherwise returns the entity (id already exists)
     */
    @Override
    public Course save(Course entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT);
            if (this.findOne(entity.getCourseID()).getCourseID() == 0) {
                preparedStatement.setLong(1, entity.getCourseID());
                preparedStatement.setString(2, entity.getCourseName());
                preparedStatement.setInt(3, entity.getTeacherID());
                preparedStatement.setInt(4, entity.getNumberStudentsMax());
                preparedStatement.setInt(5, entity.getCredits());
                preparedStatement.executeUpdate();
                return null;
            }

        return entity;

    }

    /**
     * removes the entity with the specified id
     *
     * @param ID must be not null
     * @return the removed entity or null if there is no entity
     */
    @Override
    public Course delete(long ID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
        if (findOne(ID).getCourseID() != 0) {
            Course Course;
            Course = findOne(ID);
            preparedStatement.setLong(1, ID);
            preparedStatement.executeUpdate();
            return Course;
        }
        return null;
    }

    /**
     * @param entity must not be null
     * @return null - if the entity is updated, otherwise returns the entity - (e.g. id does not exist).
     */
    @Override
    public Course update(Course entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
        if (findOne(entity.getCourseID()).getCourseID() != 0) {
            preparedStatement.setInt(1, entity.getCredits());
            preparedStatement.setLong(2, entity.getNumberStudentsMax());
            preparedStatement.setLong(3, entity.getTeacherID());
            preparedStatement.setLong(4,entity.getCourseID());
            preparedStatement.executeUpdate();
            return null;

        }
        return entity;
    }
}
