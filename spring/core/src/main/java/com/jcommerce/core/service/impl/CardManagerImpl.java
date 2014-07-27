/**
* Author: Bob Chen
*         Kylin Soong
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.CardDAO;
import com.jcommerce.core.model.Card;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.CardManager;

@Service("CardManager")
public class CardManagerImpl extends ManagerImpl implements CardManager {
    private static Log log = LogFactory.getLog(CardManagerImpl.class);
    
    @Autowired
    private CardDAO dao;

    public void setCardDAO(CardDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Card initialize(Card obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getCard(obj.getId());
        }
        return obj;
    }

    public List<Card> getCardList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Card>)list;
    }

    public int getCardCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Card> getCardList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Card>)list;
    }

    public List<Card> getCardList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Card>)list;
    }

    public List<Card> getCardList() {
        return dao.getCardList();
    }

    public Card getCard(Long id) {
        Card obj = dao.getCard(id);
        return obj;
    }

    public void saveCard(Card obj) {
        dao.saveCard(obj);
    }

    public void removeCard(Long id) {
        dao.removeCard(id);
    }
}
