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
		// バリデーション用のフラグ
		boolean isError = false;
		
		// パラメータの取得とバリデーション
		String name = request.getParameter("name");
		request.setAttribute("name", name);   // 再表示用
		if (name.isEmpty()) {
			// エラーメッセージの作成
			request.setAttribute("nameError", "名前が未入力です。");
			isError = true;  // 入力に不備ありと判定
		}
		
		String loginId = request.getParameter("loginId");
		request.setAttribute("loginId", loginId);   // 再表示用
		if (loginId.isEmpty()) {
			// エラーメッセージの作成
			request.setAttribute("loginIdError", "ログインIDが未入力です。");
			isError = true;  // 入力に不備ありと判定
		}
		
	    String plainPassword = request.getParameter("password");
	    if (plainPassword == null || plainPassword.isEmpty()) {
			// エラーメッセージの作成
			request.setAttribute("passwordError", "パスワードが未入力です。");
			isError = true;  // 入力に不備ありと判定
		} 
	    
		Integer departmentId = Integer.parseInt(request.getParameter("departmentId"));
		Integer positionId = Integer.parseInt(request.getParameter("positionId"));

		// 入力に不備がある場合は、フォームを再表示し、処理を中断
		if (isError == true) {
			try {
				DepartmentDao deptDao = DaoFactory.createDepartmentDao();
			    List<Department> departments = deptDao.findAll();
			    request.setAttribute("departments", departments);
				
	            PositionDao positionDao = DaoFactory.createPositionDao();
	            List<Position> positions = positionDao.findAll(); 
	            request.setAttribute("positions", positions);

	            request.setAttribute("departmentId", departmentId);
	    		request.setAttribute("positionId", positionId);

	            request.getRequestDispatcher("/WEB-INF/view/addUser.jsp").forward(request, response);
	            return;
	        } catch (Exception e) {
	            throw new ServletException(e);
	        }
		}
		
		// ↑エラーがなかったときだけ↓ハッシュ化
		 String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
		 System.out.println("ハッシュ化されたパスワード: " + hashedPassword);


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
