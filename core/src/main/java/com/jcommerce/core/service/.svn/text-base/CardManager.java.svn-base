/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Card;
import com.jcommerce.core.service.Criteria;
public interface CardManager extends Manager {
    public Card initialize(Card obj);

    public List<Card> getCardList(int firstRow, int maxRow);

    public int getCardCount(Criteria criteria);

    public List<Card> getCardList(Criteria criteria);

    public List<Card> getCardList(int firstRow, int maxRow, Criteria criteria);

    public List<Card> getCardList();

    public Card getCard(String id);

    public void saveCard(Card obj);

    public void removeCard(String id);
}
