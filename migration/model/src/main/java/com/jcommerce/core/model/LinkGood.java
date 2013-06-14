package com.jcommerce.core.model;

import java.util.HashSet;
import java.util.Set;

/**  
 * generated with my extension of middleGen 
 * @author <a href="http://code.google.com/p/gcloudshop/">Leon</a>
 */
 
public class LinkGood extends ModelObject {

    // relations
        
    

  private java.lang.String goodsId; 

  private java.lang.String linkGoodsId; 

  private java.lang.Boolean isDouble=false; 

  private java.lang.String adminId; 



	public LinkGood() {
	}

  public java.lang.String getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(java.lang.String newGoodsId) {
    goodsId = newGoodsId;
  }



  public java.lang.String getLinkGoodsId() {
    return linkGoodsId;
  }

  public void setLinkGoodsId(java.lang.String newLinkGoodsId) {
    linkGoodsId = newLinkGoodsId;
  }



  public java.lang.Boolean getIsDouble() {
    return isDouble;
  }

  public void setIsDouble(java.lang.Boolean newIsDouble) {
    isDouble = newIsDouble;
  }



  public java.lang.String getAdminId() {
    return adminId;
  }

  public void setAdminId(java.lang.String newAdminId) {
    adminId = newAdminId;
  }

}
