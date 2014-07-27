/**
* @Author: Kylin Soong
*          
*/

package com.jcommerce.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.payment.IPaymentMetaPlugin;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.OrderGoodsManager;
import com.jcommerce.core.service.OrderManager;
import com.jcommerce.core.service.PaymentManager;
import com.jcommerce.core.service.payment.IPaymentMetaManager;
import com.jcommerce.core.service.payment.PaymentConfigMeta;
import com.jcommerce.core.service.payment.impl.AliPay;
import com.jcommerce.core.service.payment.impl.Authorizenet;
import com.jcommerce.core.service.payment.impl.ChinaBank;
import com.jcommerce.core.service.payment.impl.GoogleCheckout;
import com.jcommerce.core.service.payment.impl.Paypal;

@Service("PaymentMetaManager")
public class PaymentMetaManagerImpl extends ManagerImpl implements IPaymentMetaManager{
	
	@Autowired
    PaymentManager paymentManager;
	
	@Autowired
    OrderManager orderManager;   
	
	@Autowired
    OrderGoodsManager orderGoodsManager;    
	
    private String pluginFolder;
    
    public PaymentMetaManagerImpl() {
        super();
        init();
    }
    
    public String getPluginFolder() {
        return pluginFolder;
    }

    public void setPluginFolder(String pluginFolder) {
        this.pluginFolder = pluginFolder;
    }

    public PaymentManager getPaymentManager() {
        return paymentManager;
    }

    public void setPaymentManager(PaymentManager paymentManager) {
        this.paymentManager = paymentManager;
    }

    public OrderManager getOrderManager() {
        return orderManager;
    }

    public void setOrderManager(OrderManager orderManager) {
        this.orderManager = orderManager;
    }
    
    public OrderGoodsManager getOrderGoodsManager() {
        return orderGoodsManager;
    }

    public void setOrderGoodsManager(OrderGoodsManager orderGoodsManager) {
        this.orderGoodsManager = orderGoodsManager;
    }

    Map<String, IPaymentMetaPlugin> metaRepo;
    public void init() {
        // TODO read pluginFolder and load modules:
//        File folder = new File(pluginFolder);
//        File[] pluginInfos = folder.listFiles();
//        for(File pluginInfo:pluginInfos) {
//            
//        }
        
        metaRepo = new HashMap<String, IPaymentMetaPlugin>();
        ChinaBank netbank = new ChinaBank();
        metaRepo.put(netbank.getDefaultConfigMeta().getCode(), netbank);
        GoogleCheckout checkout = new GoogleCheckout();
        metaRepo.put(checkout.getDefaultConfigMeta().getCode(), checkout);
        Authorizenet authorizenet = new Authorizenet();
        metaRepo.put(authorizenet.getDefaultConfigMeta().getCode(), authorizenet);
        Paypal paypal = new Paypal();
        metaRepo.put(paypal.getDefaultConfigMeta().getCode(), paypal);
        AliPay alipay = new AliPay();
        metaRepo.put(alipay.getDefaultConfigMeta().getCode(), alipay);
    }
    
    public void savePaymentConfig(Map<String, Object> props) {
        try {
            Payment payment = new Payment();
            payment.setCod(Boolean.valueOf((String)props.get(IPayment.COD)));
            payment.setCode((String)props.get(IPayment.CODE));
            payment.setDescription((String)props.get(IPayment.DESCRIPTION));
//        payment.setEnabled((String)props.get(DESC));
            payment.setFee((String)props.get(IPayment.FEE));
            payment.setId((Long)props.get(IPayment.ID));
            payment.setName((String)props.get(IPayment.NAME));
            payment.setOnline(Boolean.valueOf((String)props.get(IPayment.ONLINE)));
            payment.setOrder(Integer.valueOf((String)props.get(IPayment.ORDER)));
            
            IPaymentMetaPlugin meta = metaRepo.get(payment.getCode());
            
            payment.setConfig(meta.serializeConfig((Map)props));
            paymentManager.savePayment(payment);
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
        
    }

    public List<Map<String, Object>> getCombinedPaymentMetaList() {
        // 已加载的
        
        // 已安装到数据库中的
        List<Payment> listData = paymentManager.getPaymentList();
        Map<String, Payment> mapData = new HashMap<String, Payment>();
        for(Payment payment:listData) {
            mapData.put(payment.getCode(), payment);
        }
        
        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        Map<String, Object> maps = null;
        
        for(String code:metaRepo.keySet()) {
            IPaymentMetaPlugin plugin = metaRepo.get(code);
            maps = new HashMap<String, Object>();
            
            if(mapData.containsKey(code)) {
                // 已在数据库中，使用数据库中的值
                Payment payment = mapData.get(code);
                maps.put(IPayment.ID, payment.getId());
                maps.put(IPayment.NAME, payment.getName());
                maps.put(IPayment.CODE, payment.getCode());
                maps.put(IPayment.FEE, payment.getFee());
                maps.put(IPayment.COD, ""+payment.isCod());
                maps.put(IPayment.DESCRIPTION, ""+payment.getDescription());
                maps.put(IPayment.ORDER, ""+payment.getOrder());
                maps.put(IPaymentConfigMeta.INSTALL, "true");
            }
            else {
                maps.put(IPayment.NAME, plugin.getDefaultConfigMeta().getName());
                maps.put(IPayment.CODE, plugin.getDefaultConfigMeta().getCode());
                maps.put(IPayment.FEE, plugin.getDefaultConfigMeta().getFee());
                maps.put(IPayment.DESCRIPTION, plugin.getDefaultConfigMeta().getDescription());
                maps.put(IPaymentConfigMeta.INSTALL, "false");
            }
            
            res.add(maps);
            
        }
        return res;
    }
    
    public List<Payment> getPaymentList() {
        return paymentManager.getPaymentList();
    }
    
    public String getCode(Long orderId, Long paymentId) {
        System.out.println("getCode: orderId="+orderId+", paymentId="+paymentId);
        Order order = orderManager.getOrder(orderId);
        Payment payment = paymentManager.getPayment(paymentId);
        
        String code = payment.getCode();
        IPaymentMetaPlugin meta = metaRepo.get(code);
        
        Criteria criteria = new Criteria();
        Condition cond = new Condition();
        cond.setField(IOrderGoods.ID);
        cond.setOperator(Condition.EQUALS);
        cond.setValue(orderId);
        criteria.addCondition(cond);
        
        List<OrderGoods> orderGoods = orderGoodsManager.getOrderGoodsList(criteria);
        
        return meta.getCode(order, payment, orderGoods);
    }
    
    public void install(String paymentCode) {
        try {
            
            IPaymentMetaPlugin plugin = metaRepo.get(paymentCode);
            Payment obj = new Payment();
            obj.setCod(plugin.getDefaultConfigMeta().isCod());
            obj.setCode(plugin.getDefaultConfigMeta().getCode());
            obj.setConfig(plugin.getDefaultConfigMeta().getConfig());
            
            obj.setDescription(plugin.getDefaultConfigMeta().getDescription());
            obj.setEnabled(true);
            obj.setFee(plugin.getDefaultConfigMeta().getFee());
            obj.setName(plugin.getDefaultConfigMeta().getName());
            obj.setOnline(plugin.getDefaultConfigMeta().isOnline());
            obj.setOrder(0);
//            obj.setKeyName(plugin.getDefaultConfigMeta().getKeyName());

            paymentManager.savePayment(obj);
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("install error:"+e.getMessage());
            throw e;
        }
    }
    
    
    public void uninstall(Long paymentId) {
        try {
            paymentManager.removePayment(paymentId);
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
    }
    
    public List<PaymentConfigMeta> getInstalledPaymentMetaList() {
		try {
			List<PaymentConfigMeta> res = new ArrayList<PaymentConfigMeta>();
			List<Payment> payments = getPaymentList();
			for (Payment payment : payments) {
				String code = payment.getCode();
				IPaymentMetaPlugin plugin = metaRepo.get(code);
				PaymentConfigMeta meta = plugin.deserializeConfig(payment.getConfig());
				// copy common fields
				BeanUtils.copyProperties(meta, payment);
				res.add(meta);
			}
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
    
    public PaymentConfigMeta getPaymentConfigMeta(Long paymentId) {
        try {
            PaymentConfigMeta res = null;
            Payment payment = paymentManager.getPayment(paymentId);
            String code = payment.getCode();
            IPaymentMetaPlugin plugin = metaRepo.get(code);
            res = plugin.deserializeConfig(payment.getConfig());
            
            // copy common fields
            BeanUtils.copyProperties(res, payment);
            
            return res; 
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    interface IOrderGoods {
        public static final String ID = "id";
        public static final String ORDER = "order";
        public static final String GOODS = "goods";
        public static final String GOODSNAME = "goodsName";
        public static final String GOODSSN = "goodsSN";
        public static final String GOODSNUMBER = "goodsNumber";
        public static final String MARKETPRICE = "marketPrice";
        public static final String GOODSPRICE = "goodsPrice";
        public static final String GOODSATTRIBUTE = "goodsAttribute";
        public static final String SENDNUMBER = "sendNumber";
        public static final String REALGOODS = "realGoods";
        public static final String GIFT = "gift";
        public static final String EXTENSIONCODE = "extensionCode";
        public static final String PARENT = "parent";
    }

    interface IPayment {
        public static final String NAME = "name";
        public static final String ID = "id";
        public static final String CODE = "code";
        public static final String FEE = "fee";
        public static final String DESCRIPTION = "description";
        public static final String ORDER = "order";
        public static final String CONFIG = "config";
        public static final String ENABLED = "enabled";
        public static final String COD = "cod";
        public static final String ONLINE = "online";
    }

    interface IPaymentConfigMeta {
        public static final String INSTALL = "install";
        public static final String FIELDMETAS = "fieldMetas";
        public static final String FIELDVALUES = "fieldValues";
    }
}
