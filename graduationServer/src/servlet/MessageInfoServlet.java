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
import entity.CallInfo;
import entity.MessageInfo;
import utils.GsonUtil;
import utils.StringConstant;

/**
 * Servlet implementation class MessageInfoServlet
 */
@WebServlet("/MessageInfoServlet")
public class MessageInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageInfoServlet() {
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
		//get json String from client
		String jsonStr = request.getParameter(StringConstant.MESSAGE_INFO_JSON);
		String uuid = request.getHeader(StringConstant.UUID_PARAMETER);
		System.out.println("message json string : "  + jsonStr);
		MessageInfo messageInfo = new MessageInfo(); 
		PrintWriter out = response.getWriter();
		
		List<MessageInfo> messageInfos = new ArrayList<>();
		String insertSql = "insert into message_table (uuid, message_time, message_type) values (?, ?, ?)";
		boolean insertValidate = false;
		//change json string to list
		messageInfos = new GsonUtil().messageInfoFromJson(jsonStr);
		JdbcUtil util = new JdbcUtil();
		if (null != util.getConnection())
		{
			for(int i = 0; i < messageInfos.size(); i++)
			{
				messageInfo = messageInfos.get(i);
				insertValidate = util.insert(insertSql, StringConstant.MESSAGE_INFO_CLASS_NAME, messageInfo, uuid);
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
				out.println(StringConstant.MESSAGE_SERVER_RETURN_VALUE);
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
