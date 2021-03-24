package spring;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;

public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<User> findAllUsers(){
        String sql = "SELECT * FROM [NORTHWND].[dbo].[Users]";
        
        return jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
            
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                
                return user;
            }
        });
    }
    
    public User findUserByname(String username){
        String sql = "SELECT * FROM [NORTHWND].[dbo].[Users] WHERE username='"+username+"'";
        
        User user = (User) jdbcTemplate.queryForObject(sql, new Object[]{username}, new BeanPropertyRowMapper(User.class));
        
        return user;
    }
    
    public int addUser(final User user){
        final String sql = "INSERT INTO [NORTHWND].[dbo].[Users] ([username] ,[password] )  VALUES (?,?)";
        return jdbcTemplate.execute(sql,new PreparedStatementCallback<Integer>() {
            @Override
            public Integer doInPreparedStatement(PreparedStatement pst) throws SQLException, DataAccessException {
                   pst.setString(1, user.getUsername());
                   pst.setString(2, user.getPassword());
                   return pst.executeUpdate(sql);
            }
        });
    }
    
    public void insert(User user){
        String sql = "INSERT INTO [NORTHWND].[dbo].[Users] ([username] ,[password] )  VALUES ('"+user.getUsername()+"','"+user.getPassword()+"')";
        jdbcTemplate.update(sql);
    }
    public void update(User user){
        String sql = "UPDATE [NORTHWND].[dbo].[Users] SET username='"+user.getUsername()+"' where password='"+user.getPassword()+"'";
        jdbcTemplate.update(sql);
    }
    public void delete(User user){
        String sql = "DELETE FROM [NORTHWND].[dbo].[Users] where username='"+user.getUsername()+"'";
        jdbcTemplate.update(sql);
    }
}
