package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import domain.Department;

public class DepartmentDao {
	
	private DataSource ds;

    public DepartmentDao(DataSource ds) {
        this.ds = ds;
    }
    
    //所属部署の全情報をデータベースから全取得
    public List<Department> findAll() throws SQLException {
    	
    	List<Department> list = new ArrayList<>();
    	
    	String sql = "SELECT id, name FROM departments";
    	try (Connection con = ds.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
           while (rs.next()) {
        	   // 取得したデータから各カラムの値を読み出してdeptに格納
               Department dept = new Department();
               dept.setId(rs.getInt("id"));
               dept.setName(rs.getString("name"));
               list.add(dept);
           	   }
           }
    	return list;
    }
}
