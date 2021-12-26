package uni.finalproject.repository;

import uni.finalproject.connection.DatabaseConnection;
import uni.finalproject.model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepository implements ICrudRepository<Teacher> {
    static final String QUERY_SELECT = "SELECT * FROM Teacher";
    static final String QUERY_FIND = "SELECT * FROM Teacher WHERE teacherID = ?";
    static final String QUERY_INSERT = "INSERT INTO Teacher VALUES (?)";
    static final String QUERY_DELETE = "DELETE FROM Teacher WHERE teacherID = ?";
    private final Connection connection;
    private ResultSet resultSet;


    public TeacherRepository() throws SQLException {
        super();
        this.connection = new DatabaseConnection().getConnection();
    }

    /**
     * @param ID -the ID of the entity to be returned id must not be null
     * @return the entity with the specified id or null - if there is no entity with the given id
     */
    @Override
    public Teacher findOne(long ID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND);
        preparedStatement.setLong(1, ID);
        this.resultSet = preparedStatement.executeQuery();

        if (resultSet != null) {
            Teacher teacher = new Teacher();
            while (resultSet.next()) {
                teacher.setTeacherID(resultSet.getLong("teacherID"));
            }
            return teacher;
        }
        return null;
    }

    /**
     * @return all entities
     */
    @Override
    public Iterable<Teacher> findAll() throws SQLException {
        List<Teacher> teachersList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT);
        this.resultSet = preparedStatement.executeQuery(QUERY_SELECT);

        while (resultSet.next()) {
            Teacher teacher = new Teacher();
            teacher.setTeacherID(resultSet.getLong("teacherID"));
            teacher.setFirstName(resultSet.getString("firstName"));
            teacher.setLastName(resultSet.getString("lastName"));
            teachersList.add(teacher);
        }

        return teachersList;
    }

    /**
     * @param entity entity must be not null
     * @return null- if the given entity is saved otherwise returns the entity (id already exists)
     */
    @Override
    public Teacher save(Teacher entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT);
        if (this.findOne(entity.getTeacherID()).getTeacherID() == 0) {
            preparedStatement.setLong(1, entity.getTeacherID());
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
    public Teacher delete(long ID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
        if (findOne(ID).getTeacherID() != 0) {
            Teacher teacher;
            teacher = findOne(ID);
            preparedStatement.setLong(1, ID);
            preparedStatement.executeUpdate();
            return teacher;
        }
        return null;
    }

    /**
     * @param entity must not be null
     * @return null - if the entity is updated, otherwise returns the entity - (e.g. id does not exist).
     */
    @Override
    public Teacher update(Teacher entity) {
        return null;
    }

}
