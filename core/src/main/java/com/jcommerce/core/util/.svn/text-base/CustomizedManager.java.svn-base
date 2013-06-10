package com.jcommerce.core.util;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Gallery;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttribute;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.model.Region;
import com.jcommerce.core.model.ShippingArea;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;

public class CustomizedManager {
    public static final String GOODSTYPE = "goodsType";

    interface IAreaRegion {
        // relation
        public static final String SHIPPING_AREA = "shippingArea";
        public static final String REGION_NAME = "regionName";
    }

    interface IShippingArea {
        public static final String ID = "id"; 
        public static final String NAME = "name";
        public static final String SHIPPING = "shipping";
        public static final String CONFIG = "config";
        public static final String REGIONS = "regions";
    }
    
    public int getGoodsTypeListWithAttrCount(List<GoodsType> resultSet, int firstRow,
            int maxRow, Criteria criteria) {
    	try {
    		List<GoodsType> res = SpringUtil.getGoodsTypeManager().getGoodsTypeList(firstRow, maxRow, criteria);
    		for(GoodsType gt:res) {
        		String gtid = gt.getId();
    			Criteria c2 = new Criteria();
        		Condition cond = new Condition();
        		cond.setField(GOODSTYPE);
        		cond.setOperator(Condition.EQUALS);
        		cond.setValue(gtid);
        		c2.addCondition(cond);
        		
    			int attcount = SpringUtil.getAttributeManager().getAttributeCount(c2);
    			gt.setAttrCount(attcount);
    		}
    		
    		resultSet.addAll(res);
    		
    		return SpringUtil.getGoodsTypeManager().getGoodsTypeCount(criteria);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }

    public void getShippingAreaWithRegionName(List<ShippingArea> resultSet, String shippingId) {
    	try {
    		Criteria criteria = new Criteria();
    		criteria.addCondition(new Condition(IShippingArea.SHIPPING, Condition.EQUALS, shippingId));
    		List<ShippingArea> list = SpringUtil.getShippingAreaManager().getShippingAreaList(criteria);
//    		for(ShippingArea sa:list) {
//        		String saId = sa.getId();
//    			Criteria c2 = new Criteria();
//        		Condition cond = new Condition();
//        		cond.setField(IAreaRegion.SHIPPING_AREA);
//        		cond.setOperator(Condition.EQUALS);
//        		cond.setValue(saId);
//        		c2.addCondition(cond);
//        		
//    			List<AreaRegion> ars = SpringUtil.getAreaRegionManager().getAreaRegionList(c2);
//    			sa.setRegions(new HashSet<AreaRegion>(ars));
//    		}
    		resultSet.addAll(list);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }
    public String addBrand(Brand to) {
    	try {
    	    SpringUtil.getBrandManager().saveBrand(to);
			String res = to.getId(); 
    		
//    		populateIdWithPo(to);
//			String res = dao.add(to);
			
//			DSFile logo = to.getLogo();
//			String id = logo.getPkId();
//			System.out.println("id: "+id);

			
			// NOTE: This does not work
			// throw exception: 
			// can't update the same entity twice in a transaction or operation
//			to.setLogoFileId(id);
			return res;
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
    
    public boolean updateBrand(Brand to) {
    	try {
    		String id = to.getId();
    		System.out.println("id: "+id);
    		Brand po = SpringUtil.getBrandManager().getBrand(id);
//    		String bkn = po.getKeyName();
//    		String fkn = null;
//    		String fid = null;
//    		DSFile newFile = null, oldFile = null;
    		MyPropertyUtil.copySimpleProperties(po, to);
//    		if(to.getLogoFile()==null) {
//    			// no new logofile, do nothing    			
//    		}
//    		else {
//    			oldFile = po.getLogoFile();
//        		newFile = to.getLogoFile();
//    			fkn = DataStoreUtils.genKeyName(newFile);
//    			fid = KeyFactory.keyToString(new KeyFactory.Builder("Brand",bkn).addChild("DSFile", fkn).getKey());
//    			newFile.setKeyName(fkn);
//    			po.setLogoFileId(fid);
//    			po.setLogoFile(newFile);
//    			boolean suc = txdelete(oldFile);
//    		}
    		
    		SpringUtil.getBrandManager().saveBrand(po);
			String res = po.getId();
			return true;
    	} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }

    public String addGoods(Goods to) {
    	try {
//    		String goodskn = DataStoreUtils.genKeyName(to);
//    		to.setKeyName(goodskn);
    		// TODO need overcome the checkbox issue
    		// just for test with Web Home page. 
//    		to.setIsBest(true);
//    		to.setIsNew(true);
//    		to.setIsHot(true);
//    		to.setLongId(UUIDLongGenerator.newUUID());
    		
    		Set<Gallery> galleries = to.getGalleries();
    		
			for(Gallery gallery : galleries) {
//				String gkn = DataStoreUtils.genKeyName(gallery);
//				gallery.setKeyName(gkn);
//				String gid = KeyFactory.keyToString(new KeyFactory.Builder("Goods",goodskn).addChild("Gallery", gkn).getKey());
//				gallery.setKeyName(gkn);
//				gallery.setId(gid);
				
//				DSFile file = gallery.getImageFile();
//				String fkn = DataStoreUtils.genKeyName(file);
//				String fid = KeyFactory.keyToString(new KeyFactory.Builder("Goods",goodskn).addChild(GoodsGallery.class.getSimpleName(), gkn).addChild("DSFile", fkn).getKey());
//				file.setKeyName(fkn);
////				file.setId(fid);
//				gallery.setImageFileId(fid);
				
//				DSFile thumbfile = gallery.getThumbFile();
//				String tfkn = DataStoreUtils.genKeyName(thumbfile);
//				String tfid = KeyFactory.keyToString(new KeyFactory.Builder("Goods",goodskn).addChild(GoodsGallery.class.getSimpleName(), gkn).addChild("DSFile", tfkn).getKey());
//				thumbfile.setKeyName(tfkn);				
//				gallery.setThumbFileId(tfid);
//				
//				gallery.setLongId(UUIDLongGenerator.newUUID());				
			}
			
			// TODO temporary. to ensure image not empty
			if(galleries.size()>0) {
				Gallery gallery = (Gallery)galleries.iterator().next();
				to.setImage(gallery.getImageUrl());
				// TODO temporary
				to.setThumb(gallery.getThumbUrl());
			}
			
			Set<GoodsAttribute> gts = to.getAttributes();
			for(GoodsAttribute gt:gts) {
//				String gkn = DataStoreUtils.genKeyName(gt);
//				String gid = KeyFactory.keyToString(new KeyFactory.Builder("Goods",goodskn).addChild(GoodsAttr.class.getSimpleName(), gkn).getKey());
//				gt.setKeyName(gkn);
////				gt.setId(gid);
//				gt.setLongId(UUIDLongGenerator.newUUID());
			}

			SpringUtil.getGoodsManager().saveGoods(to);
			String res = to.getId();
			
//			GoogleBaseUtil gbUtil = new GoogleBaseUtil(SpringUtil.getShopConfigManager().getCachedShopConfig("en"));
//			 String token = gbUtil.authenticate();
//			 gbUtil.buildDataItem(to);
//			 String gbdid = gbUtil.postItem(token);
//			 to.setGoogleBaseDataId(gbdid);
			
			// verify,  debug only 
//			for(Gallery gallery : galleries) {
//				System.out.println("galleryId: "+gallery.getId());
//			}
//			String goodsId = to.getId();
//			System.out.println("goodsId="+goodsId);
//			
//			Criteria criteria = new Criteria();
//			Condition cond = new Condition();
//			cond.setField(IGoodsGallery.GOODS);
//			cond.setOperator(Condition.EQUALS);
//			cond.setValue(goodsId);
//			criteria.addCondition(cond);
//			List<GoodsGallery> t1 = SpringUtil.getGoodsGalleryManager().getGoodsGalleryList(criteria);
//			System.out.println("size: "+t1.size());
//			
//            SpringUtil.getGoodsManager().saveGoods(to);
//            res = to.getId();
			
			return res;
    	}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    	
    	
    }
    public boolean updateGoods(Goods to) {
    	try {
    		String id = to.getId();
    		System.out.println("id: "+id);
    		Goods po = SpringUtil.getGoodsManager().getGoods(id);
    		
    		System.out.println("1) size of po.gallery: "+po.getGalleries().size());
    		
//    		String goodskn = po.getKeyName();
    		
    		MyPropertyUtil.copySimpleProperties(po, to);
    		// TODO image/thumb
    		
    		Set<Gallery> toGalleries = to.getGalleries();
			for(Gallery gallery : toGalleries) {
				if(StringUtils.isNotEmpty(gallery.getId())) {
					// existing gallery
					for(Gallery gpo : po.getGalleries()) {
						if(gpo.getId().equals(gallery.getId())) {
							gpo.setDescription(gallery.getDescription());
							break;
						}
					}
//					dao.update(gallery);
//					gallery.setImageFileId(gallery.getImageFile().getPkId());
				} else {
					// new gallery
//					String gkn = DataStoreUtils.genKeyName(gallery);
//					gallery.setKeyName(gkn);
//					DSFile file = gallery.getImageFile();
//					String fkn = DataStoreUtils.genKeyName(file);
//					file.setKeyName(fkn);
//					String fid = KeyFactory.keyToString(new KeyFactory.Builder("Goods",goodskn).addChild(GoodsGallery.class.getSimpleName(), gkn).addChild("DSFile", fkn).getKey());
//					gallery.setImageFileId(fid);
//					
//					DSFile thumbfile = gallery.getThumbFile();
//					String tfkn = DataStoreUtils.genKeyName(thumbfile);
//					String tfid = KeyFactory.keyToString(new KeyFactory.Builder("Goods",goodskn).addChild(GoodsGallery.class.getSimpleName(), gkn).addChild("DSFile", tfkn).getKey());
//					thumbfile.setKeyName(tfkn);				
//					gallery.setThumbFileId(tfid);
//					
//					gallery.setLongId(UUIDLongGenerator.newUUID());
					po.getGalleries().add(gallery);
				}
			}
			
			System.out.println("2) size of po.gallery: "+po.getGalleries().size());

			Set<Gallery> galleries = po.getGalleries();
			if(galleries.size()>0) {
				Gallery gallery = (Gallery)(galleries.iterator().next());
				po.setImage(gallery.getImageUrl());
				po.setThumb(gallery.getThumbUrl());
			} else {
				po.setImage(null);
			}
				
			
			po.getAttributes().clear();
			Set<GoodsAttribute> gts = to.getAttributes();
			for(GoodsAttribute gt:gts) {
//				String gkn = DataStoreUtils.genKeyName(gt);
//				gt.setKeyName(gkn);
//				gt.setLongId(UUIDLongGenerator.newUUID());
				po.getAttributes().add(gt);
			}
			
			System.out.println("System.out.println(updateResponse);");
			SpringUtil.getGoodsManager().saveGoods(po);
			
//			GoogleBaseUtil gbUtil = new GoogleBaseUtil(SpringUtil.getShopConfigManager().getCachedShopConfig("en"));
//			String token = gbUtil.authenticate();
//			gbUtil.buildDataItem(po);
//			String updateResponse = gbUtil.updateItem( token , po.getGoogleBaseDataId());
//			System.out.println(updateResponse);
			
			return true;
    	}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
    
    public void getAreaRegionListWithName(List<Region> resultSet, String shippingAreaId){
    	try {
    	    ShippingArea area = SpringUtil.getShippingAreaManager().getShippingArea(shippingAreaId);
    	    Set<Region> ars = area.getRegions();
    		for(Region ar : ars) {
//    			String regionId = ar.getRegion().getId();
//    			Region region = SpringUtil.getRegionManager().getRegion(regionId);
//    			ar.setRegionName(region.getName());
    			resultSet.add(ar);
    		}
    	}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
    
    // for test case1 
//    public String addPerson(Person to, Address a) {
//    	try {
//    		populateIdWithPo(to);
//			String res = dao.add(to);
//			
//			a.setPerson(to);
//			dao.add(a);
//			
//			return res;
//    	}catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//    	
//    	
//    }
}
