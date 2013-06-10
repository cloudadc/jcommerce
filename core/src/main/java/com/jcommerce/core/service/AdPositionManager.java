/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.AdPosition;
import com.jcommerce.core.service.Criteria;
public interface AdPositionManager extends Manager {
    public AdPosition initialize(AdPosition obj);

    public List<AdPosition> getAdPositionList(int firstRow, int maxRow);

    public int getAdPositionCount(Criteria criteria);

    public List<AdPosition> getAdPositionList(Criteria criteria);

    public List<AdPosition> getAdPositionList(int firstRow, int maxRow, Criteria criteria);

    public List<AdPosition> getAdPositionList();

    public AdPosition getAdPosition(String id);

    public void saveAdPosition(AdPosition obj);

    public void removeAdPosition(String id);
}
