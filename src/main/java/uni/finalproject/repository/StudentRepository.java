package uni.finalproject.repository;

import uni.finalproject.connection.DatabaseConnection;
import uni.finalproject.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository implements ICrudRepository<Student> {
    static final String QUERY_SELECT = "SELECT * FROM Student";
    static final String QUERY_FIND = "SELECT * FROM Student WHERE studentID = ?";
    static final String QUERY_INSERT = "INSERT INTO Student VALUES (?, ?, ?, ?)";
    static final String QUERY_UPDATE = "UPDATE Student SET totalCredits = ? WHERE studentID = ?";
    static final String QUERY_DELETE = "DELETE FROM Student WHERE studentID = ?";
    static final String QUERY_RESET_CREDITS_TO_0 = "UPDATE Student SET totalCredits = 0";
    private final Connection connection;
    private ResultSet resultSet;


    public StudentRepository() throws SQLException {
        super();
        this.connection = new DatabaseConnection().getConnection();
    }

    /**
     * @param ID -the ID of the entity to be returned id must not be null
     * @return the entity with the specified id or null - if there is no entity with the given id
     */
    @Override
    public Student findOne(long ID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND);
        preparedStatement.setLong(1, ID);
        this.resultSet = preparedStatement.executeQuery();
        if (resultSet != null) {
            Student student = new Student();
            while (resultSet.next()) {
                student.setStudentID(resultSet.getLong("studentID"));
                student.setFirstName(resultSet.getString("firstName"));
                student.setLastname(resultSet.getString("lastName"));
                student.setTotalCredits(resultSet.getInt("totalCredits"));
            }
            return student;
        }
        return null;
    }

    /**
     * @return all entities
     */
    @Override
    public Iterable<Student> findAll() throws SQLException {
        List<Student> studentList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT);
        this.resultSet = preparedStatement.executeQuery(QUERY_SELECT);
        while (resultSet.next()) {
            Student student = new Student();
            student.setStudentID(resultSet.getLong("studentID"));
            student.setFirstName(resultSet.getString("firstName"));
            student.setLastname(resultSet.getString("lastName"));
            student.setTotalCredits(resultSet.getInt("totalCredits"));
            studentList.add(student);

        }
        resultSet.close();

        return studentList;


    }

    /**
     * @param entity entity must be not null
     * @return null- if the given entity is saved otherwise returns the entity (id already exists)
     */
    @Override
    public Student save(Student entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT);
        if (this.findOne(entity.getStudentID()).getStudentID() == 0) {
            preparedStatement.setLong(1, entity.getStudentID());
            preparedStatement.setString(2, entity.getFirstName());
            preparedStatement.setString(3, entity.getLastname());
            preparedStatement.setLong(4, entity.getTotalCredits());
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
    public Student delete(long ID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
        if (findOne(ID).getStudentID() != 0) {
            Student student;
            student = findOne(ID);
            preparedStatement.setLong(1, ID);
            preparedStatement.executeUpdate();
            return student;
        }
        return null;
    }

    /**
     * @param entity must not be null
     * @return null - if the entity is updated, otherwise returns the entity - (e.g. id does not exist).
     */
    @Override
    public Student update(Student entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
        if (findOne(entity.getStudentID()).getStudentID() != 0) {
            preparedStatement.setInt(1,entity.getTotalCredits());
            preparedStatement.setLong(2,entity.getStudentID());
            preparedStatement.executeUpdate();
            return null;

        }
        return entity;
    }

    public void setAllCreditsTo0() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_RESET_CREDITS_TO_0);
        preparedStatement.executeUpdate();
    }
}
