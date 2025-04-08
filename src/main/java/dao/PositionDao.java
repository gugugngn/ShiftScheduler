package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import domain.Position;

public class PositionDao {
	
	private DataSource ds;

    public PositionDao(DataSource ds) {
        this.ds = ds;
    }
    
    //所属部署の全情報をデータベースから全取得
    public List<Position> findAll() throws SQLException {
    	
    	List<Position> list = new ArrayList<>();
    	
    	String sql = "SELECT id, name FROM positions";
    	try (Connection con = ds.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
           while (rs.next()) {
        	   // 取得したデータから各カラムの値を読み出してdeptに格納
        	   Position pos = new Position();
        	   pos.setId(rs.getInt("id"));
        	   pos.setName(rs.getString("name"));
               list.add(pos);
           	   }
           }
    	return list;
    }

}
