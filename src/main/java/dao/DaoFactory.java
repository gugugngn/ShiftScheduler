package dao;

import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DaoFactory {
	

	  public static UserDao createUserDao() {
	    return new UserDaoImpl(getDataSource());
	  }
	  
	  public static DepartmentDao createDepartmentDao() throws SQLException {
	        return new DepartmentDao(getDataSource());
	    }

	    public static PositionDao createPositionDao() throws SQLException {
	        return new PositionDao(getDataSource());
	    }
	  
	  private static DataSource getDataSource() {
	    InitialContext ctx = null;
	    DataSource ds = null;
	    try {
	      ctx = new InitialContext();
	      ds = (DataSource) ctx.lookup("java:comp/env/jdbc/maidb");
	    } catch (NamingException e) {
	      if (ctx != null) {
	        try {
	          ctx.close();
	        } catch (NamingException e1) {
	          throw new RuntimeException(e1);
	        }
	}
	      throw new RuntimeException(e);
	    }
	return ds; }
	
}
