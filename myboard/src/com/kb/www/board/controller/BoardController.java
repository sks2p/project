package com.kb.www.board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kb.www.board.home.action.HomeAction;
import com.kb.www.board.member.action.MemberDetailAction;
import com.kb.www.board.member.action.MemberJoinAction;
import com.kb.www.board.member.action.MemberJoinProcAction;
import com.kb.www.board.member.action.MemberLeaveAction;
import com.kb.www.board.member.action.MemberLoginAction;
import com.kb.www.board.member.action.MemberLoginProcAction;
import com.kb.www.board.member.action.MemberLogoutAction;
import com.kb.www.board.member.action.ModifyMemberInfoAction;
import com.kb.www.board.member.action.ModifyMemberProcAction;
import com.kb.www.common.Action;
import com.kb.www.common.ActionForward;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("*.do")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public BoardController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	String requestURI = request.getRequestURI();
    	String contextPath = request.getContextPath();
    	String command = requestURI.substring(contextPath.length());
    	ActionForward forward = null;
    	Action action = null;
    	
    	
    	if (command.equals("/memberJoin.do")) {
    		action = new MemberJoinAction();
    	} else if (command.equals("/memberJoinProc.do")) {
    		action = new MemberJoinProcAction();
    	} else if (command.equals("/login.do")) {
    		action = new MemberLoginAction();
    	} else if (command.equals("/loginProc.do")) {
    		action = new MemberLoginProcAction();
    	} else if (command.equals("/logout.do")) {
    		action = new MemberLogoutAction();
    	} else if (command.equals("/leave.do")) {
    		action = new MemberLeaveAction();
    	}else if (command.equals("/MemberDetail.do")) {
    		action = new MemberDetailAction();
    	}else if (command.equals("/modifyMemberInfo.do")) {
    		action = new ModifyMemberInfoAction();
    	}else if (command.equals("/modifyMemberProc.do")) {
    		action = new ModifyMemberProcAction();
    	}
    	
    	
    	try {
			forward = action.execute(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			forward = new ActionForward();
			forward.setPath("/views/error.jsp");
			forward.setRedirect(true);
		}

    	if (forward != null) {
    		if (forward.isRedirect()) {
        		// 리다이렉트
        		response.sendRedirect(forward.getPath());
        	} else {
        		// 디스패치
        		RequestDispatcher dispatcher
        		= request.getRequestDispatcher(forward.getPath());
        		dispatcher.forward(request, response);
        	}
    	}
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

}
