package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import dao.DaoFactory;
import dao.DepartmentDao;
import dao.PositionDao;
import dao.UserDao;
import domain.Department;
import domain.Position;
import domain.User;

/**
 * Servlet implementation class AddUserServlet
 */
@WebServlet("/addUser")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	//所属部署と役職を別テーブルから参照
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//Factoryクラスを使って各DAOオブジェクトを生成し、利用
			DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
	        PositionDao positionDao = DaoFactory.createPositionDao();
			
	        List<Department> departments = departmentDao.findAll(); // 全部署
	        List<Position> positions = positionDao.findAll();       // 全役職
	        
	        request.setAttribute("departments", departments);
	        request.setAttribute("positions", positions);
	        
	     // フォームの表示 
			request.getRequestDispatcher("WEB-INF/view/addUser.jsp")
	        .forward(request, response);
		}  catch (Exception e) {
	        throw new ServletException(e);
	    }
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// パラメータの取得
		String name = request.getParameter("name");
		String loginId = request.getParameter("loginId");
	    String plainPassword = request.getParameter("password");
		Integer departmentId = Integer.parseInt(request.getParameter("departmentId"));
		Integer positionId = Integer.parseInt(request.getParameter("positionId"));
		
		//パスワードのハッシュ化
		String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
		
		// データの追加
		User user = new User();
		user.setName(name);
		user.setLoginId(loginId);
	    user.setLoginPass(hashedPassword); // ← ここはハッシュ済み！
		user.setDepartmentId(departmentId);
		user.setPositionId(positionId);
		
		try {
			UserDao userDao = DaoFactory.createUserDao();
			userDao.insert(user);
			response.sendRedirect(request.getContextPath() + "/listUser");
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
