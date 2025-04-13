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
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 編集する職員の ID の取得
		String strId = request.getParameter("id");
		Integer id = Integer.parseInt(strId);

		try {
			// 編集する会員データの取得
		     UserDao userDao = DaoFactory.createUserDao();
		     User user = userDao.findById(id);
		     
		     DepartmentDao deptDao = DaoFactory.createDepartmentDao();
		     List<Department> departments = deptDao.findAll();
		     request.setAttribute("departments", departments);
		     
		     PositionDao posDao = DaoFactory.createPositionDao();
		     List<Position> positions = posDao.findAll();
		     request.setAttribute("positions", positions);
		     
			// 編集ページの表示
			request.setAttribute("name", user.getName()); 
			request.setAttribute("departmentId", user.getDepartmentId());
			request.setAttribute("positionId", user.getPositionId()); 
			request.setAttribute("loginId", user.getLoginId()); 
			request.setAttribute("loginPass", user.getLoginPass()); 
			request.getRequestDispatcher("/WEB-INF/view/editUser.jsp").forward(request, response);
			  } catch (Exception e) {
			    throw new ServletException(e);
			  }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// バリデーション用のフラグ
		boolean isError = false;
		
		// 編集する会員の ID の取得
		String strId = request.getParameter("id"); 
		Integer id = Integer.parseInt(strId);
		
		// 各パラメータの取得とバリデーション
		String name = request.getParameter("name"); 
		request.setAttribute("name", name); 
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

	            request.getRequestDispatcher("/WEB-INF/view/editUser.jsp").forward(request, response);
	            return;
	        } catch (Exception e) {
	            throw new ServletException(e);
	        }
		}
		
		// データの追加
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setLoginId(loginId);
		user.setDepartmentId(departmentId);
		user.setPositionId(positionId);
		

        // 新しいパスワードが入力された場合
        if (plainPassword != null && !plainPassword.isEmpty()) {
            // 新しいパスワードをハッシュ化
            String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
            user.setLoginPass(hashedPassword);
        } else {
            // パスワードが空の場合、現在のパスワードをそのまま使用（事前にgetLoginPass()で取得しておく）
            String currentPassword = request.getParameter("currentPassword"); // hiddenフィールドで送信された現在のパスワード
            user.setLoginPass(currentPassword);
        }

        // データベースの更新処理
		try {
			UserDao userDao = DaoFactory.createUserDao();
			userDao.update(user);
			response.sendRedirect(request.getContextPath() + "/listUser");
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}	

}
