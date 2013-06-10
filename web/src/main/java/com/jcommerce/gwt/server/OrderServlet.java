package com.jcommerce.gwt.server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.util.SpringUtil;
import com.jcommerce.core.util.TempleteProcessor;
import com.jcommerce.web.to.GoodsWrapper;
import com.jcommerce.web.to.Lang;

public class OrderServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	    String id = req.getParameter("id");

	    OutputStream oup = null;
		try {
			oup = resp.getOutputStream();
			String s = getOrder(id);
			oup.write(s.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(oup!=null)
					oup.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String getOrder(String id) {
	    try {
	        TempleteProcessor processor = new TempleteProcessor();
	        
	        Map<String, Object> props = new HashMap<String, Object>();
	        props.put("lang", Lang.getInstance());
	        Order order = SpringUtil.getOrderManager().getOrder(id);
	        List<OrderGoods> orderGoodsList = SpringUtil.getOrderGoodsManager().getOrderGoodsListByOrderId(id);
	        List<GoodsWrapper> goodsList = new ArrayList<GoodsWrapper>();
	        for (OrderGoods orderGoods : orderGoodsList) {
	            goodsList.add(new GoodsWrapper(orderGoods.getGoods()));
	        }
	        props.put("order", order);
	        props.put("goodsList", goodsList);
	        
	        String s = processor.getText("template/order_print.html", props);
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        }
	    return "";
	}
}
