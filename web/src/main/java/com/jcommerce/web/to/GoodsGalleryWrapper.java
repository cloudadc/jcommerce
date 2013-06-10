package com.jcommerce.web.to;

import com.jcommerce.core.model.Gallery;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.gwt.client.util.URLConstants;

public class GoodsGalleryWrapper extends BaseModelWrapper implements URLConstants{

	Gallery goodsGallery;
	@Override
	protected Object getWrapped() {
		return getGoodsGallery();
	}
	public GoodsGalleryWrapper(ModelObject goodsGallery) {
		super();
		this.goodsGallery = (Gallery)goodsGallery;
	}
	
	public Gallery getGoodsGallery() {
		return goodsGallery;
	}
	
	public String getImageId() {
		return getGoodsGallery().getId();
	}
	
	public String getThumbUrl() {
//		if (getGoodsGallery().getThumbFileId() == null)
//			return null;
//		return SERVLET_IMAGE + getGoodsGallery().getThumbFileId();
		
		// currently we do not have thumb due to DS issue 154
		return getImgUrl();
	}
	
	public String getImgUrl() {
		return SERVLET_IMAGE + getGoodsGallery().getId();
	}

}
