package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import domain.User;

public class UserDaoImpl implements UserDao {
	
	//データベース接続に使うDataSource保持用フィールド
	private DataSource ds;
	  public UserDaoImpl(DataSource ds) {
	    this.ds = ds;
	}

	@Override
	public List<User> findAll() throws Exception {
		 List<User> userList = new ArrayList<>();
		    try (Connection con = ds.getConnection()) {
		      String sql = "SELECT"
		          + " users.id, users.name, users.department_id,"
		          + " users.position_id, users.created, users.updated,"
		          + " departments.name AS department_name,"
		          + " positions.name AS position_name,"
		          + " users.department_id AS departmentId,"
		          + " users.position_id AS positionId"
		          + " FROM users"
		          + " JOIN departments ON users.department_id = departments.id"
		          + " JOIN positions ON users.position_id = positions.id"; 
		      
		      PreparedStatement stmt = con.prepareStatement(sql);
		      ResultSet rs = stmt.executeQuery();
		      while (rs.next()) {
		        userList.add(mapToUser(rs));
		      }
		  } catch (Exception e) {
		      throw e;
		  }
		  return userList;
	}

	@Override
	public User findById(Integer id) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void insert(User user) throws Exception {
		try (Connection con = ds.getConnection()) {  //データベース接続用のオブジェクト取得
			String sql = "INSERT INTO users"
					+ " (name, department_id, position_id, login_id, login_pass, created)"
					+ " VALUES (?, ?, ?, ?, ?, NOW())";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,  user.getName());
			stmt.setObject(2,  user.getDepartmentId(), Types.INTEGER);
			stmt.setObject(3,  user.getPositionId(), Types.INTEGER);
			stmt.setString(4,  user.getLoginId());
			stmt.setString(5,  user.getLoginPass());
			stmt.executeUpdate();   //SQL文を実行
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void update(User user) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void deleteById(User user) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
	/**
	* ResultSet から User オブジェクトへの変換 */
	  private User mapToUser(ResultSet rs) throws Exception{
	    Integer id = (Integer) rs.getObject("id");
	    String name = rs.getString("name");
	    String departmentName = rs.getString("department_name");
	    String positionName = rs.getString("position_name");
	    Date created = rs.getTimestamp("created");
	    Date updated = rs.getTimestamp("updated");
	    return new User(id, name, departmentName, positionName, created,updated);
	  }

}
