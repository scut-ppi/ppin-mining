package com.ppi.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ppi.process.Processor;
import com.ppi.tools.OneSentence;

public class ReceiveText extends HttpServlet {

	ArrayList<OneSentence> array = null;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		try{
			String text = req.getParameter("text");   // 获取textarea的文字

			if( null == text ){  //如果为空 有可能是因为直接访问ReceiveText Servlet 跳转回首页 
				System.out.println("Text is a null pointer.\nGo back to index.jsp!");
				resp.sendRedirect("index.jsp");
				return;
			}
			
			if(text == ""){
				System.out.println("Text is a empty.\nGo back to index.jsp!");
				resp.sendRedirect("index.jsp");
				return;
			}
		
			Processor p = new Processor(text);  // 进行文本处理，以及关系的挖掘和保存
			
			array = p.getResult();		//获取挖掘结果
			if(null == array){
				System.out.println("array is null.\nGo back to index.jsp!");
				resp.sendRedirect("index.jsp");
				return;
			}
			
			req.setAttribute("array", array);
			
			req.getRequestDispatcher("result.jsp").forward(req, resp);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
