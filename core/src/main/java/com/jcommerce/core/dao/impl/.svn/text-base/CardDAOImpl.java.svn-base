/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.CardDAO;
import com.jcommerce.core.model.Card;

public class CardDAOImpl extends DAOImpl implements CardDAO {
    public CardDAOImpl() {
        modelClass = Card.class;
    }

    public List<Card> getCardList() {
        return getList();
    }

    public Card getCard(String id) {
        return (Card)getById(id);
    }

    public void saveCard(Card obj) {
        save(obj);
    }

    public void removeCard(String id) {
        deleteById(id);
    }
}
