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
import entity.SensorInfo;
import utils.GsonUtil;
import utils.StringConstant;

/**
 * Servlet implementation class SensorInfoServlet
 */
@WebServlet("/SensorInfoServlet")
public class SensorInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SensorInfoServlet() {
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
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		SensorInfo sensor = new SensorInfo();
		
		//get json String from client
		String jsonStr = request.getParameter(StringConstant.SENSOR_INFO_JSON);
		String uuid = request.getHeader(StringConstant.UUID_PARAMETER);
		PrintWriter out = response.getWriter();
		System.out.println(jsonStr);
		List<SensorInfo> sensorInfos = new ArrayList<>();
		String insertSql = "insert into sensor_table (uuid, sensor_name, sensor_get_time, sensor_value) values (?, ?, ?, ?)";
		boolean insertValidate = false;
		//change json string to list
		sensorInfos = new GsonUtil().sensorInfoFromJson(jsonStr);
		JdbcUtil util = new JdbcUtil();
		if (null != util.getConnection())
		{
			for(int i = 0; i < sensorInfos.size(); i++)
			{
				sensor = sensorInfos.get(i);
				insertValidate = util.insert(insertSql, StringConstant.SENSOR_INFO_CLASS_NAME, sensor, uuid);
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
				System.out.println("插入成功后返回值！");
				out.println(StringConstant.SENSOR_SERVER_RETURN_VALUE);
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
