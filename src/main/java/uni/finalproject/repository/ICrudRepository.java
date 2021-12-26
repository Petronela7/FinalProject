package uni.finalproject.repository;

import java.sql.SQLException;

public interface ICrudRepository <E>{

    /**
     * @param ID-the ID of the entity to be returned ,id must not be null
     * @return the entity with the specified id or null - if there is no entity with the given id
     */
    E findOne(long ID) throws SQLException;

    /**
     * @return all entities
     */
    Iterable<E> findAll() throws SQLException;

    /**
     * @param entity entity must be not null
     * @return null- if the given entity is saved otherwise returns the entity (id already exists)
     */
    E save(E entity) throws SQLException;

    /**
     * removes the entity with the specified id
     *
     * @param ID must be not null
     * @return the removed entity or null if there is no entity
     */
    E delete(long ID) throws SQLException;

    /**
     * @param entity  must not be null
     * @return null - if the entity is updated, otherwise returns the entity - (e.g. id does not exist).
     */
    E update(E entity) throws SQLException;
}
