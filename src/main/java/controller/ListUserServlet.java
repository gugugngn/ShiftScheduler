package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoFactory;
import dao.DepartmentDao;
import dao.PositionDao;
import dao.UserDao;
import domain.Department;
import domain.Position;
import domain.User;

/**
 * Servlet implementation class ListUserServlet
 */
@WebServlet("/listUser")
public class ListUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// DAO によるデータ取得
			UserDao userDao = DaoFactory.createUserDao();
			List<User> userList = userDao.findAll();
			  
			DepartmentDao deptDao = DaoFactory.createDepartmentDao();
			List<Department> departments = deptDao.findAll();
			request.setAttribute("departments", departments);
			 
			PositionDao posDao = DaoFactory.createPositionDao();
			List<Position> positions = posDao.findAll();
			request.setAttribute("positions", positions);
  
			// JSP へフォワード
			request.setAttribute("userList", userList); 
			request.getRequestDispatcher("/WEB-INF/view/listUser.jsp")
			.forward(request, response);
			    } catch (Exception e) {
			      throw new ServletException(e);
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
