/**
* Author: Bob Chen
*/

package com.jcommerce.web.to;

import java.util.ArrayList;
import java.util.List;

import com.jcommerce.core.model.Goods;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.GoodsManager;
import com.jcommerce.core.util.SpringUtil;
import com.jcommerce.web.front.action.helper.Pager;

public class GoodsListWrapper {
    private List<GoodsWrapper> values;
    private Pager pager = new Pager();
    private Criteria criteria;

    // FIXME add temperally
    private boolean valuesReady = false;
    
    public GoodsListWrapper(List<Goods> list) {
        valuesReady = true;
        
        this.values = new ArrayList<GoodsWrapper>();
        for (Goods goods : list) {
            this.values.add(new GoodsWrapper(goods));
        }
        
        pager.setRecordCount(list.size());
    }
    
    public GoodsListWrapper(Criteria criteria) {
        this.criteria = criteria;
    }

    public List<GoodsWrapper> getValues() {
        return getTop(0);
    }
    
    public List<GoodsWrapper> getTop4() {
        return getTop(4);
    }
    
    public List<GoodsWrapper> getTop5() {
        return getTop(4);
    }
    
    public List<GoodsWrapper> getTop6() {
        return getTop(4);
    }
    
    public List<GoodsWrapper> getTop7() {
        return getTop(4);
    }
    
    public List<GoodsWrapper> getTop8() {
        return getTop(4);
    }
    
    private List<GoodsWrapper> getTop(int max) {
        if (valuesReady) {
            return values;
        }
        
        pager.setRecordCount(countGoods(max));
        
        this.values = new ArrayList<GoodsWrapper>();
        
        List<Goods> goodsList = listGoods(max);
        for (Goods goods : goodsList) {
            this.values.add(new GoodsWrapper(goods));
        }
        return values;
    }
    
    public Pager getPager() {
        return pager;
    }
    
    public void setPager(Pager pager) {
        this.pager = pager;
    }
    
    protected int getPageStartItem() {
        return getPager().getStart();
    }

    private List<Goods> listGoods(int max) {
        System.out.println("getPager().getRecordPerPage():"+getPager().getRecordPerPage()+" max:"+max);
        if (getPager().getRecordPerPage() == 0) {
            if (max == 0) {
                return SpringUtil.getGoodsManager().getGoodsList(criteria);
            }
            return SpringUtil.getGoodsManager().getGoodsList(0, max, criteria);
        }
        
        return SpringUtil.getGoodsManager().getGoodsList(getPageStartItem(), getPager().getRecordPerPage(), criteria);
    }
    
    private int countGoods(int max) {
        GoodsManager manager = SpringUtil.getGoodsManager();
        if (getPager().getRecordPerPage() == 0) {
            if (max == 0) {
                return manager.getGoodsCount(criteria);
            }
            
            return Math.min(max, manager.getGoodsCount(criteria));
        }
        
        return manager.getGoodsCount(criteria);
    }
}
