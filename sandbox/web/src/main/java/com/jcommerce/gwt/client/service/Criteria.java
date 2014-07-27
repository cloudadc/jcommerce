/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Criteria implements IsSerializable {
    private List<Condition> conditions = new ArrayList<Condition>();
    private List<Order> orders = new ArrayList<Order>();
    private List<String> groupBys = new ArrayList<String>();
    
    private int operator = 0;
	public static final int AND = 0;
    public static final int OR = 1;
    
    public Criteria()
    {
    }
    
    public Criteria(int operator)
    {
    	this.operator = operator;
    }
    
    public int getOperator() {
		return operator;
	}
    
    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
    
    public void addCondition(Condition condition) {
        conditions.add(condition);
    }

    public void removeCondition(Condition condition) {
        conditions.remove(condition);
    }

    public void removeAll(){
        conditions.clear();
        groupBys.clear();
        orders.clear();
    }

    public List<String> getGroupBys() {
        return groupBys;
    }

    public void setGroupBys(List<String> groupBys) {
        this.groupBys = groupBys;
    }
    
    public void addGroupBy(String groupBy) {
        groupBys.add(groupBy);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    
    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }
}
