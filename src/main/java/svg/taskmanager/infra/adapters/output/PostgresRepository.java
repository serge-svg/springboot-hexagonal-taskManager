package svg.taskmanager.infra.adapters.output;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import svg.taskmanager.infra.ports.output.EntityRepository;

@Repository
public class PostgresRepository implements EntityRepository {

    private static final String SELECT_FROM = "SELECT * FROM ";
    private static final String DELETE_FROM = "DELETE FROM ";
    JdbcTemplate jdbcTemplate;
    
    public PostgresRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        return jdbcTemplate.query(SELECT_FROM + clazz.getSimpleName(), new LombokRowMapper<T>(clazz));
    }

    @Override
    public <T> T getById(String id, Class<T> clazz) {
        List<T> list = jdbcTemplate.query(SELECT_FROM + clazz.getSimpleName() + " WHERE id = ?",
                new LombokRowMapper<T>(clazz), id);

        if (!list.isEmpty()) {
            return list.get(0);
        }

        throw new IllegalArgumentException("This id has not been found:" + id);
    }

    @Override
    public <T> T getByNationalId(String nationalId, Class<T> clazz) {
        List<T> list = jdbcTemplate.query(SELECT_FROM + clazz.getSimpleName() + " WHERE nationalId = ?",
                new LombokRowMapper<T>(clazz), nationalId);

        if (!list.isEmpty()){
            return list.get(0);
        }
        
        throw new IllegalArgumentException("This national id has not been found:" + nationalId);
    }

    public <T> List<T> getByName(String name, Class<T> clazz) {
        List<T> list = jdbcTemplate.query(SELECT_FROM + clazz.getSimpleName() + " WHERE name like ? ",
                new LombokRowMapper<T>(clazz), '%' + name + '%');

        if (!list.isEmpty()){
            return list;
        }

        throw new IllegalArgumentException("There are not results for this name:" + name);
    }

    public <T> List<T> getByUserId(String userId, Class<T> clazz) {
        List<T> list = jdbcTemplate.query(SELECT_FROM + clazz.getSimpleName() + " WHERE userId = ? ",
                new LombokRowMapper<T>(clazz), userId);

        if (!list.isEmpty()){
            return list;
        }

        return Collections.emptyList();
    }

    @Override
    public <T> boolean deleteById(String id, Class<T> clazz) {
        String sql = DELETE_FROM + clazz.getSimpleName() + " WHERE id = ?";
        Object[] args = new Object[] { id };

        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public <T> boolean deleteAll(Class<T> clazz) {
        String sql = DELETE_FROM + clazz.getSimpleName() + ";";
        Object[] args = new Object[] {};
        return jdbcTemplate.update(sql, args) >= 0;
    }

    @Override
    public <T> boolean save(T reg) {
        Field[] entityFields = reg.getClass().getDeclaredFields();
        String[] fields = new String[entityFields.length];
        Object[] fieldValues = new Object[entityFields.length];
        try {
            for (int i = 0; i < entityFields.length; i++) {
                fields[i] = entityFields[i].getName();
                fieldValues[i] = reg.getClass()
                        .getMethod("get" + entityFields[i].getName().substring(0, 1).toUpperCase()
                                + entityFields[i].getName().substring(1))
                        .invoke(reg);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO " +
                reg.getClass().getSimpleName() +
                "(" + String.join(",", fields) + ")" +
                " VALUES " +
                "(" + String.join(",", Collections.nCopies(fields.length, "?")) + ")";

        return jdbcTemplate.update(sql, fieldValues) == 1;
    }

    @Override
    public <T> boolean update(T reg) {
        Field[] entityFields = reg.getClass().getDeclaredFields();
        String[] fields = new String[entityFields.length];
        Object[] fieldValues = new Object[entityFields.length];
        try {
            for (int i = 0; i < entityFields.length; i++) {
                fields[i] = entityFields[i].getName();
                fieldValues[i] = reg.getClass()
                        .getMethod("get" + entityFields[i].getName().substring(0, 1).toUpperCase()
                                + entityFields[i].getName().substring(1))
                        .invoke(reg);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ")
                .append(reg.getClass().getSimpleName())
                .append(" SET ");
        
        for (int i = 1; i < fields.length; i++){
            if (i == fields.length - 1) {
                sql.append(fields[i]).append(" = ").append(fieldValues[i] instanceof String  ? "'"+ fieldValues[i] + "'" : fieldValues[i]);
            } else {
                sql.append(fields[i]).append(" = ").append(fieldValues[i] instanceof String  ? "'"+ fieldValues[i] + "'" : fieldValues[i]).append(", ");
            }
        }
        sql.append(" WHERE ")
                .append(fields[0]).append(" = ").append(fieldValues[0] instanceof String  ? "'"+ fieldValues[0] + "'" : fieldValues[0]);
        
        return jdbcTemplate.update(sql.toString()) == 1;
    }    

    private static class LombokRowMapper<T> implements RowMapper<T> {
        private Class<?> clazz = null;
        public LombokRowMapper(Class<?> clazz) {
            this.clazz = clazz;
        }

        @Override
        public T mapRow(ResultSet rs, int rowNum) throws SQLException {
            try {
                Method builderMethod = clazz.getMethod("builder");

                Object row = builderMethod.invoke(null);
                Method[] methods = row.getClass().getDeclaredMethods();

                for(Method method: methods) {
                    int pos = -1;

                    try {
                        pos = rs.findColumn(method.getName());
                    } catch (SQLException ex) { }

                    if (pos != -1) {
                        Object fieldValue = rs.getObject(pos);
                        method.invoke(row, fieldValue);
                    }
                }

                return (T) row.getClass().getMethod("build").invoke(row);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
