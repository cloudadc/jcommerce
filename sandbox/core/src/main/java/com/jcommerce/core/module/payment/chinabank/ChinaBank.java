package com.jcommerce.core.module.payment.chinabank;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.module.ConfigMetaData;
import com.jcommerce.core.module.FieldMetaData;
import com.jcommerce.core.module.IConfigMetaData;
import com.jcommerce.core.module.IFieldMetaData;
import com.jcommerce.core.module.payment.IPaymentModule;
import com.jcommerce.core.util.MD5;

public class ChinaBank implements IPaymentModule {
    private static final Map<String, String> defaultConfigValues;
    private static final Map<String, IFieldMetaData> fieldMetas;
    public static final String ACCOUNT = "chinabank_account";
    public static final String KEY = "chinabank_key";

    public static final String URL = "https://pay3.chinabank.com.cn/PayGate";
    
    
    static {
        defaultConfigValues = new HashMap<String, String>();
        defaultConfigValues.put(ACCOUNT, "21040782");
        defaultConfigValues.put(KEY, "aaaaabbbbbccccc");
        
        fieldMetas = new HashMap<String, IFieldMetaData>();
        IFieldMetaData m = null;
        
        m = new FieldMetaData();
        m.setLable("商户编号");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(ACCOUNT, m);
        
        m = new FieldMetaData();
        m.setLable("MD5 密钥");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(KEY, m);
        
    }
    
    public String getCode() {
        return "ChinaBank";
    }

    public String getAuthor() {
        return null;
    }

    public String getName() {
        return "网银在线";
    }

    public String getVersion() {
        return "1.0.0";
    }

    public URL getWebSite() {
        try {
            return new URL("http://www.alipay.com");
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public String getPayFee() {
        return "2.5%";
    }
    public boolean isOnlinePaymentSupported() {
        return true;
    }
    public boolean isCashOnDeliverySupported() {
        return false;
    }

    public String getDescription() {
        return "网银在线与中国工商银行、招商银行、中国建设银行、农业银行、民生银行等数十家金融机构达成协议。全面支持全国19家银行的信用卡及借记卡实现网上支付。（网址：http://www.chinabank.com.cn）";
    }
    
    private String serialize(Map<String, String> config) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(config);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        String serializedConfig = new String(Base64.encodeBase64(bos.toByteArray())); 

        System.out.println("serializedConfig: "+serializedConfig);
        return serializedConfig;
    }
    
    /*
     *  (non-Javadoc)
<form style=\"text-align:center;\" method=post action=\"https://pay3.chinabank.com.cn/PayGate\" target=\"_blank\">
<input type=HIDDEN name='v_mid' value='1232132'>
<input type=HIDDEN name='v_oid' value='2008123009729'>
<input type=HIDDEN name='v_amount' value='2871.79'>
<input type=HIDDEN name='v_moneytype'  value='CNY'>
<input type=HIDDEN name='v_url'  value='http://localhost/respond.php?code=chinabank'>
<input type=HIDDEN name='v_md5info' value='2962934934F215BB2D86AF4A265BE7E3'>
<input type=HIDDEN name='remark1' value=''>
<input type=submit value='立即使用网银在线支付'>
</form>
  
     * @see com.jcommerce.core.payment.IPaymentMetaPlugin#getCode(com.jcommerce.core.model.Order, com.jcommerce.core.model.Payment)
     */
    
    public String getPaymentCode(Order order, Payment payment) {
        IConfigMetaData configMeta = getConfigMetaData(payment.getConfig());
        Map<String, String> values = configMeta.getFieldValues();
        
        String data_vid = values.get(ACCOUNT).trim();
        String data_orderid = order.getSN();
        double data_vamount = order.getOrderAmount();
        String data_vmoneytype="CNY";
        String data_returnurl="http://localhost:8080/jcommerce/respond.do?code=chinabank";
        String data_vpaykey = values.get(KEY).trim();
        String md5 = data_vamount+data_vmoneytype+data_orderid+data_vid+data_returnurl+data_vpaykey;
        md5 = MD5.encode(md5);
        
        StringBuffer buf = new StringBuffer();
        buf.append("<form style=\"text-align:center;\" method=post action=\"")
            .append(URL).append("\" target=\"_blank\">");
        buf.append("<input type=HIDDEN name='v_mid' value='").append(data_vid).append("'>");
        buf.append("<input type=HIDDEN name='v_oid' value='").append(data_orderid).append("'>");
        buf.append("<input type=HIDDEN name='v_amount' value='").append(data_vamount).append("'>");
        buf.append("<input type=HIDDEN name='v_moneytype' value='").append(data_vmoneytype).append("'>");
        buf.append("<input type=HIDDEN name='v_url' value='").append(data_returnurl).append("'>");
        buf.append("<input type=HIDDEN name='v_md5info' value='").append(md5).append("'>");
        buf.append("<input type=HIDDEN name='remark1' value='").append("").append("'>");
        buf.append("<input type=submit value='立即使用网银在线支付'></form>");
        
        return buf.toString();
    }
    public String getSerializedConfig(Map<String, Object> props) {
        String res = null;
        Map<String, String> config = new HashMap<String, String>();
        // pick only those keys in fieldMetas
        for(String key:fieldMetas.keySet() ) {
            Object value = props.get(key);
            System.out.println("getSerializedConfig: key="+key+", value="+value);
            config.put(key, value==null? null: value.toString());
        }
        res = serialize(config);
        return res;
    }
    public String getDefaultConfig() {
        return serialize(defaultConfigValues);
    }

    public IConfigMetaData getConfigMetaData(String serializedConfig) {
        IConfigMetaData configMeta = new ConfigMetaData();
        configMeta.setFieldMetaData(fieldMetas);

        Map<String, String> config = null;

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decodeBase64(serializedConfig.getBytes()));
           
            ObjectInputStream ois;
            ois = new ObjectInputStream(bis);
            config = (Map<String, String>)ois.readObject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
        configMeta.setFieldValues(config);
        return configMeta;
    }

}
