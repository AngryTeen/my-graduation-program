package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.JdbcUtil;
import entity.CallInfo;
import utils.GsonUtil;
import utils.StringConstant;

/**
 * Servlet implementation class CallInfoServlet
 */
@WebServlet("/CallInfoServlet")
public class CallInfoServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CallInfoServlet() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//get json String from client
		String jsonStr = request.getParameter(StringConstant.CALL_INFO_JSON);
		String uuid = request.getHeader(StringConstant.UUID_PARAMETER);
		System.out.println(" call json string");
		PrintWriter out = response.getWriter();
		List<CallInfo> callInfos = new ArrayList<>();
		String insertSql = "insert into call_table (uuid, ring_time, start_time, stop_time) values (?, ?, ?, ?)";
		boolean insertValidate = false;
		//change json string to list
		callInfos = new GsonUtil().callInfoFromJson(jsonStr);
		JdbcUtil util = new JdbcUtil();
		CallInfo callInfo = new CallInfo();
		if (null != util.getConnection())
		{
			for(int i = 0; i < callInfos.size(); i++)
			{
				callInfo = callInfos.get(i);
				insertValidate = util.insert(insertSql, StringConstant.CALL_INFO_CLASS_NAME, callInfo, uuid);
				if (!insertValidate)
				{
					break;
				}
			}
			if (!insertValidate)
			{
				out.println(StringConstant.SERVER_ERROR);
			}
			else {
				out.println(StringConstant.CALL_SERVER_RETURN_VALUE);
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
