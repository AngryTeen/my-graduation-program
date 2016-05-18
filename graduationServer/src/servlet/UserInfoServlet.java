package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonNull;
import com.sun.corba.se.impl.orbutil.closure.Constant;

import dao.JdbcUtil;
import entity.UserInfo;
import utils.GsonUtil;
import utils.StringConstant;

/**
 * Servlet implementation class UserInfoServlet
 */
@WebServlet("/UserInfoServlet")
public class UserInfoServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfoServlet() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		UserInfo userInfo = new UserInfo();
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		userInfo.setAccount(request.getParameter(StringConstant.ACCOUNT));
		userInfo.setPassword(request.getParameter(StringConstant.PASSWORD));
		userInfo.setEmail(request.getParameter(StringConstant.EMAIL));
		userInfo.setSchoolName(request.getParameter(StringConstant.SCHOOL));
		userInfo.setStudentId(request.getParameter(StringConstant.STUDENT_ID));
		userInfo.setBirth(request.getParameter(StringConstant.BIRTH));
		userInfo.setSex(request.getParameter(StringConstant.SEX));
		System.out.println(userInfo.getAccount() + "," + userInfo.getPassword());
		
		
		JdbcUtil util = new JdbcUtil();
		String querySql = "select * from user_table where account = ?";
		String insertSql = "insert into user_table (account, password, email, school_name, student_id, birth, sex) "
				+ "values (?,?,?,?,?,?,?)";
		ResultSet resultSet = null;
		if (null != util.getConnection())
		{
			System.out.println("database connect success!");
			try
			{
				resultSet = util.query(querySql, userInfo.getAccount());
				//System.out.println(resultSet.next());
				if (resultSet.next())
				{
					System.out.println("account has exist!");
					out.println(StringConstant.USER_EXIST);
				}
				else 
				{
					System.out.println("insert to mysql database!");
					if (util.insert(insertSql, StringConstant.USER_INFO_CLASS_NAME, userInfo, null))
					{
						out.println(StringConstant.USER_REGISTER_SUCCESS);
					}
					else 
					{
						out.println(StringConstant.SERVER_ERROR);
					}
				}
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else 
		{
			out.println(StringConstant.SERVER_ERROR);
			
		}
		
		out.flush();
		out.close();
		util.releaseConnection();
		
	}

}
