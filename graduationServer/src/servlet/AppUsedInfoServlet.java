package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.JdbcUtil;
import entity.AppUsedInfo;
import entity.SensorInfo;
import utils.GsonUtil;
import utils.StringConstant;

/**
 * Servlet implementation class AppUsedInfoServlet
 */
@WebServlet("/AppUsedInfoServlet")
public class AppUsedInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppUsedInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		AppUsedInfo appUsedInfo = new AppUsedInfo();
		
		//get json String from client
		String jsonStr = request.getParameter(StringConstant.APP_INFO_JSON);
		String uuid = request.getHeader(StringConstant.UUID_PARAMETER);
		PrintWriter out = response.getWriter();
		
		List<AppUsedInfo> appUsedInfos = new ArrayList<>();
		String insertSql = "insert into app_table(uuid, app_name, start_time, stop_time) values (?, ?, ?, ?);";
		boolean insertValidate = false;
		//change json string to list
		appUsedInfos = new GsonUtil().appInfoFromJson(jsonStr);
		JdbcUtil util = new JdbcUtil();
		if (null != util.getConnection())
		{
			for(int i = 0; i < appUsedInfos.size(); i++)
			{
				appUsedInfo = appUsedInfos.get(i);
				insertValidate = util.insert(insertSql, StringConstant.APP_INFO_CLASS_NAME, appUsedInfo, uuid);
				if (!insertValidate)
				{
					break;
				}
			}
			if (!insertValidate)
			{
				out.println(StringConstant.SERVER_ERROR);
			}
			else 
			{
				out.println(StringConstant.APP_SERVER_RETURN_VALUE);
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
