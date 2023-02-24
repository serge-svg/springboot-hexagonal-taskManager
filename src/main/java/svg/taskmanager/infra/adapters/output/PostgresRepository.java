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

    JdbcTemplate jdbcTemplate;

    public PostgresRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        return jdbcTemplate.query("SELECT * FROM " + clazz.getSimpleName(), new LombokRowMapper<T>(clazz));
    }

    @Override
    public <T> T getById(String id, Class<T> clazz) {
        List<T> list = jdbcTemplate.query("SELECT * FROM " + clazz.getSimpleName() + " WHERE id = ?",
                new LombokRowMapper<T>(clazz), id);

        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public <T> T getByNationalId(String nationalId, Class<T> clazz) {
        List<T> list = jdbcTemplate.query("SELECT * FROM " + clazz.getSimpleName() + " WHERE national_id = ?",
                new LombokRowMapper<T>(clazz), nationalId);

        if (!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    public <T> List<T> getByName(String name, Class<T> clazz) {
        List<T> list = jdbcTemplate.query("SELECT * FROM " + clazz.getSimpleName() + " WHERE name like ? ",
                new LombokRowMapper<T>(clazz), '%' + name + '%');

        if (!list.isEmpty()){
            return list;
        }
        return null;
    }

    public <T> List<T> getByUserId(String userId, Class<T> clazz) {
        List<T> list = jdbcTemplate.query("SELECT * FROM " + clazz.getSimpleName() + " WHERE user_id = ? ",
                new LombokRowMapper<T>(clazz), userId);

        if (!list.isEmpty()){
            return list;
        }
        return null;
    }

    @Override
    public <T> boolean deleteById(String id, Class<T> clazz) {
        String sql = "DELETE FROM " + clazz.getSimpleName() + " WHERE id = ?";
        Object[] args = new Object[] { id };

        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public <T> boolean deleteAll(Class<T> clazz) {
        String sql = "DELETE FROM " + clazz.getSimpleName() + ";";
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

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ")
                .append(reg.getClass().getSimpleName())
                .append("(").append(String.join(",", fields)).append(")")
                .append(" VALUES ")
                .append("(").append(String.join(",", Collections.nCopies(fields.length, "?"))).append(")");

        return jdbcTemplate.update(sql.toString(), fieldValues) == 1;
    }

    private class LombokRowMapper<T> implements RowMapper<T> {
        private Class<?> clazz = null;
        public LombokRowMapper(Class<?> clazz) {
            this.clazz = clazz;
        }

        @Override
        public T mapRow(ResultSet rs, int rowNum) throws SQLException {
            try {
                Method builderMethod = clazz.getMethod("builder");
                if (builderMethod == null){
                    return null;
                }                
                Object row = builderMethod.invoke(null);
                Method[] m = row.getClass().getDeclaredMethods();

                for (int i = 0; i < m.length; i++) {
                    int pos = -1;

                    try {
                        pos = rs.findColumn(m[i].getName());
                    } catch (SQLException ex) { }

                    if (pos != -1) {
                        Object fieldValue = rs.getObject(pos);
                        m[i].invoke(row, fieldValue);
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
