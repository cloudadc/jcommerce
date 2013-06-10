package com.jcommerce.core.dao.impl;

import org.springframework.orm.jdo.JdoTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class MyTxManager extends JdoTransactionManager {

	public MyTxManager() {
		super(PMF.get());
	}

	@Override
	public void doBegin(Object transaction, TransactionDefinition definition) {
		System.out.println("tx doBegin...");
		super.doBegin(transaction, definition);
	}
	
	@Override
	public void doCommit(DefaultTransactionStatus status) {
		System.out.println("tx doCommit...");
		super.doCommit(status);
		
	}
    
}
