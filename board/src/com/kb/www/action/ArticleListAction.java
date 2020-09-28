package com.kb.www.action;

import com.kb.www.common.Action;
import com.kb.www.common.ActionForward;
import com.kb.www.service.BoardService;
import com.kb.www.vo.ArticleVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ArticleListAction implements Action {
    @Override
    public ActionForward execute
            (HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        BoardService service = new BoardService();
        ArrayList<ArticleVo> list = service.getArticleList();

        ActionForward forward = new ActionForward();
        request.setAttribute("list", list);
        forward.setPath("/views/list.jsp");
        return forward;
    }
}
