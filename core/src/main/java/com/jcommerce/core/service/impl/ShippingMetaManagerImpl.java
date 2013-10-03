package com.jcommerce.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.jcommerce.core.model.Shipping;
import com.jcommerce.core.model.ShippingArea;
import com.jcommerce.core.service.ShippingAreaManager;
import com.jcommerce.core.service.ShippingManager;
import com.jcommerce.core.service.shipping.IShippingMetaManager;
import com.jcommerce.core.service.shipping.IShippingMetaPlugin;
import com.jcommerce.core.service.shipping.ShippingAreaMeta;
import com.jcommerce.core.service.shipping.ShippingConfigMeta;
import com.jcommerce.core.service.shipping.impl.EMS;
import com.jcommerce.core.service.shipping.impl.YuanTong;
import com.jcommerce.core.util.MyPropertyUtil;

@Service("ShippingMetaManager")
public class ShippingMetaManagerImpl extends ManagerImpl implements IShippingMetaManager {
    private String pluginFolder;
    ShippingManager shippingManager;
    ShippingAreaManager shippingAreaManager;
    
    public ShippingMetaManagerImpl() {
    	super();
    	init();
    }
    
    public ShippingManager getShippingManager() {
        return shippingManager;
    }

    public void setShippingManager(ShippingManager shippingManager) {
        this.shippingManager = shippingManager;
    }

    public ShippingAreaManager getShippingAreaManager() {
        return shippingAreaManager;
    }

    public void setShippingAreaManager(ShippingAreaManager shippingAreaManager) {
        this.shippingAreaManager = shippingAreaManager;
    }

    Map<String, IShippingMetaPlugin> metaRepo;
    public void init() {
        // TODO read pluginFolder and load modules:
//        File folder = new File(pluginFolder);
//        File[] pluginInfos = folder.listFiles();
//        for(File pluginInfo:pluginInfos) {
//            
//        }
        
        metaRepo = new HashMap<String, IShippingMetaPlugin>();
        EMS ems = new EMS();
        metaRepo.put(ems.getDefaultConfigMeta().getCode(), ems);
        YuanTong yto = new YuanTong();
        metaRepo.put(yto.getDefaultConfigMeta().getCode(), yto);

    }
    
    public double calculate(String shippingCode, double goodsWeight, double goodsAmount, Map<String, String> configValues) {
    	IShippingMetaPlugin plugin = metaRepo.get(shippingCode);
    	if(plugin == null) {
    		throw new RuntimeException("shipping plugin code: "+shippingCode+" is not loaded!!");
    	}
    	return plugin.calculate(goodsWeight, goodsAmount, configValues);
    }
    
	public List<ShippingConfigMeta> getCombinedShippingMetaList() {
        // 已加载的
        
        // 已安装到数据库中的
        List<Shipping> listData = shippingManager.getShippingList();
        Map<String, Shipping> mapData = new HashMap<String, Shipping>();
        for(Shipping shipping:listData) {
            mapData.put(shipping.getCode(), shipping);
        }
        
        List<ShippingConfigMeta> res = new ArrayList<ShippingConfigMeta>();
        ShippingConfigMeta meta = null;
        
        for(String code:metaRepo.keySet()) {
            IShippingMetaPlugin plugin = metaRepo.get(code);
            meta = new ShippingConfigMeta();
            
            if(mapData.containsKey(code)) {
                // 已在数据库中，使用数据库中的值
                Shipping shipping = mapData.get(code);
                meta.setId(shipping.getId());
                meta.setName(shipping.getName());
                meta.setCode(shipping.getCode());
                meta.setDescription(shipping.getDescription());
                meta.setSupportCod(shipping.isSupportCod());
                meta.setInstall(true);
            }
            else {
                meta.setName(plugin.getDefaultConfigMeta().getName());
                meta.setCode(plugin.getDefaultConfigMeta().getCode());
                meta.setDescription(plugin.getDefaultConfigMeta().getDescription());
                meta.setSupportCod(plugin.getDefaultConfigMeta().isSupportCod());
                meta.setInstall(false);
            }
            meta.setAuthor(plugin.getDefaultConfigMeta().getAuthor());
            meta.setWebsite(plugin.getDefaultConfigMeta().getWebsite());
            meta.setVersion(plugin.getDefaultConfigMeta().getVersion());
            res.add(meta);
            
        }
        return res;
	}

	public List<ShippingConfigMeta> getInstalledShippingMetaList() {
		try {
			List<ShippingConfigMeta> res = new ArrayList<ShippingConfigMeta>();
			List<Shipping> payments = getShippingList();
			for (Shipping payment : payments) {
				String code = payment.getCode();
				IShippingMetaPlugin plugin = metaRepo.get(code);
//				ShippingConfigMeta meta = plugin.deserializeConfig(payment.getPayConfig());
				// copy common fields
//				BeanUtils.copyProperties(meta, payment);
//				res.add(meta);
			}
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public ShippingConfigMeta getShippingConfigMeta(Long shippingId) {
        try {
            ShippingConfigMeta res = new ShippingConfigMeta();
            Shipping shipping = shippingManager.getShipping(shippingId);

            // copy common fields
            BeanUtils.copyProperties(res, shipping);

            String code = shipping.getCode();
            IShippingMetaPlugin plugin = metaRepo.get(code);
//            if(StringUtils.isEmpty(res.getShippingPrint())) {
//            	// fill with plugin default
//            	res.setShippingPrint(plugin.getDefaultConfigMeta().getShippingPrint());
//            }
            
            return res; 
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
	}
	public ShippingAreaMeta getShippingAreaMeta(Long shippingAreaId, Long shippingId) {
        try {
        	System.out.println("shippingAreaId: "+shippingAreaId+", shippingId: "+shippingId);
            Shipping s = shippingManager.getShipping(shippingId);
            String code = s.getCode();
            
            IShippingMetaPlugin plugin = metaRepo.get(code);

        	
        	ShippingArea sa;
        	ShippingAreaMeta res;
        	if(shippingId == null) {
        		throw new RuntimeException("shippingId is NULL!");
        	}
        	if(shippingAreaId == null) {
        		sa = new ShippingArea();
        		res = plugin.getDefaultAreaMeta(); 
        	}
        	else {
        		sa = shippingAreaManager.getShippingArea(shippingAreaId);
        		res = plugin.deserializeConfig(sa.getConfig());
        	}

            
            // copy common fields
            BeanUtils.copyProperties(res, sa);            
            
            return res; 
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
	}
	
	public List<Shipping> getShippingList() {
		return shippingManager.getShippingList();
	}

	public void install(String shippingCode) {
        try {
            
            IShippingMetaPlugin plugin = metaRepo.get(shippingCode);
            Shipping obj = new Shipping();
            ShippingConfigMeta meta = plugin.getDefaultConfigMeta();
            obj.setSupportCod(meta.isSupportCod());
            obj.setCode(meta.getCode());
            obj.setName(meta.getName());
            obj.setDescription(meta.getDescription());
            obj.setInsure(meta.getInsure());
            obj.setEnabled(true);
            shippingManager.saveShipping(obj);
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("install error:"+e.getMessage());
            throw e;
        }

	}

	public void saveShippingConfig(ShippingConfigMeta meta) {
        try {
            Shipping shipping = new Shipping();
            BeanUtils.copyProperties(shipping, meta);

            shippingManager.saveShipping(shipping);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }

	}

	public void uninstall(Long shippingId) {
        try {
            shippingManager.removeShipping(shippingId);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
	}
    public boolean saveShippingArea(ShippingArea to, Map<String, Object> props) {
    	try {
    		Long saId = to.getId();
    		Long shippingId = to.getShipping().getId();
			Shipping shipping = shippingManager.getShipping(shippingId);
			String code = shipping.getCode();
			IShippingMetaPlugin plugin = metaRepo.get(code);
			String configure = plugin.serializeConfig(props);
			to.setConfig(configure);
			
			if (saId == null) {
				// alternatively, we could call txAdd and let the parent be attached there
				to.setShipping(shipping);
				
				// FIXME
//				String sakn = DataStoreUtils.genKeyName(to);
//				to.setKeyName(sakn);
//				to.setLongId(UUIDLongGenerator.newUUID());
				
//				for(AreaRegion ar : to.getAreaRegions()) {
//					String arkn = DataStoreUtils.genKeyName(ar);	
////				String arid = KeyFactory.keyToString(new KeyFactory.Builder("ShippingArea",sakn).
////									addChild("AreaRegion", arkn).getKey());
//					ar.setKeyName(arkn);
//					ar.setLongId(UUIDLongGenerator.newUUID());
//				}
		
				shippingAreaManager.saveShippingArea(to);
				
				// verify,  debug only 
//				for(AreaRegion ar : to.getAreaRegions()) {
//					System.out.println("arId: "+ar.getPkId());
//				}
				saId = to.getId();
				System.out.println("saId="+saId);
			} else {
//				ShippingArea po = shippingAreaManager.getShippingArea(saId);
//				MyPropertyUtil.copySimpleProperties(po, to);
//				po.getAreaRegions().clear();
//				for(AreaRegion ar : to.getAreaRegions()) {
//					String arkn = DataStoreUtils.genKeyName(ar);	
////				String arid = KeyFactory.keyToString(new KeyFactory.Builder("ShippingArea",sakn).
////									addChild("AreaRegion", arkn).getKey());
//					ar.setKeyName(arkn);
//					ar.setLongId(UUIDLongGenerator.newUUID());
//					po.getAreaRegions().add(ar);
//				}
				shippingAreaManager.saveShippingArea(to);
			}
			return true;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
    }

	public String getPluginFolder() {
		return pluginFolder;
	}

	public void setPluginFolder(String pluginFolder) {
		this.pluginFolder = pluginFolder;
	}


}
