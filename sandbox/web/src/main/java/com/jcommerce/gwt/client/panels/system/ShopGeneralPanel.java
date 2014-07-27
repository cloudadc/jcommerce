package com.jcommerce.gwt.client.panels.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.gwt.client.model.IShopConfig;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.RegionService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.FileUploader;

public class ShopGeneralPanel extends ColumnPanel {

	private String SHOPNAME = "shop_name";//商店名称
	private String SHOPTITLE = "shop_title";//商店标题
	private String SHOPDESC = "shop_desc";//商店描述
	private String SHOPKEYWORDS = "shop_keywords";//商店关键字
	private String SHOPCOUNTRY = "shop_country";//所在国家
	private String SHOPPROVINCE = "shop_province";//所在省份
	private String SHOPCITY = "shop_city";//所在城市
	private String SHOPADDRESS = "shop_address";//详细地址
	private String QQ = "qq";//客服QQ号码
	private String WW = "ww";//淘宝旺旺
	private String SKYPE = "skype";//Skype
	private String YM = "ym";//Yahoo Messenger
	private String MSN = "msn";//MSN Messenger
	private String SERVICEEMAIL = "service_email";//客服邮件地址
	private String SERVICEPHONE = "service_phone";//客服电话
	private String SHOPCLOSED = "shop_closed";//暂时关闭网站
	private String CLOSECOMMENT = "close_comment";//关闭网店的原因
	private String SHOPLOGO = "shop_logo";//商店 Logo
	private String LICENSED = "licensed";//是否显示 Licensed
	private String USERNOTICE = "user_notice";//用户中心公告
	private String SHOPNOTICE = "shop_notice";//商店公告
    private String COUNTRYID = "1";

    private Map<String, Object> configs = new HashMap<String, Object>();
	private Map<String, Object> nameIDs = new HashMap<String, Object>();
	private List<String> name = new ArrayList<String>();
	
	private ListBox city = new ListBox();
	private ListBox province = new ListBox();

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);

		createPanel(SHOPNAME, Resources.constants.Shop_name(), new TextBox());
		createPanel(SHOPTITLE, Resources.constants.Shop_title(), new TextBox());
		createPanel(SHOPDESC, Resources.constants.Shop_desc(), new TextBox());
		createPanel(SHOPKEYWORDS, Resources.constants.shop_keywords(), new TextBox());
		ListBox country = new ListBox();
		country.addItem(Resources.constants.select_notice());
		country.addItem("中国", "1");
		country.setSelectedIndex(1);
		createPanel(SHOPCOUNTRY, Resources.constants.shop_country(), country);
		createPanel(SHOPPROVINCE, Resources.constants.shop_province(), province);
		createPanel(SHOPCITY, Resources.constants.shop_city(), city);
		createPanel(SHOPADDRESS, Resources.constants.shop_address(), new TextBox());
		createPanel(QQ, Resources.constants.qq(), new TextBox());
		createPanel(WW, Resources.constants.ww(), new TextBox());		
		createPanel(SKYPE, Resources.constants.skype(),	new TextBox());
		createPanel(YM, Resources.constants.ym(), new TextBox());
		createPanel(MSN, Resources.constants.msn(), new TextBox());
		createPanel(SERVICEEMAIL, Resources.constants.service_email(), new TextBox());
		createPanel(SERVICEPHONE, Resources.constants.service_phone(), new TextBox());

		ListBox closed = new ListBox();
		closed.addItem(Resources.constants.no(), "0");
		closed.addItem(Resources.constants.yes(), "1");
		closed.setSelectedIndex(0);		
		createPanel(SHOPCLOSED,Resources.constants.shop_closed(),closed);
		
		TextArea closeComment = new TextArea();
		closeComment.setSize("500", "100");
		createPanel(CLOSECOMMENT, Resources.constants.close_comment(), closeComment);
		createPanel(SHOPLOGO, Resources.constants.shop_logo(), new FileUploader());

		ListBox licensed = new ListBox();
		licensed.addItem(Resources.constants.no(), "0");
		licensed.addItem(Resources.constants.yes(), "1");
		licensed.setSelectedIndex(0);		
		createPanel(LICENSED,Resources.constants.licensed(),licensed);
		
		TextArea userNotice = new TextArea();
		userNotice.setSize("500", "100");
		createPanel(USERNOTICE, Resources.constants.user_notice(), userNotice);
		TextArea shopNotice = new TextArea();
		shopNotice.setSize("500", "100");
		createPanel(SHOPNOTICE, Resources.constants.shop_notice(), shopNotice);

		province.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				showRegion(city, province.getSelectedIndex(),"0");
			}
		});
	}

	/**
	 * fill right Items in cityListBox depend on which province you selected
	 * 
	 * @param listBox cityListBox
	 * @param provinceIndex the province you choose
	 * @param cityIndex the city you choose
	 */
	private void showRegion(final ListBox listBox, final int provinceIndex, final String cityIndex) {
		listBox.clear();
		listBox.addItem(Resources.constants.select_notice());
		listBox.setSelectedIndex(0);
		if (provinceIndex != 0) {
			new RegionService().getRegionChildList(provinceIndex+1+"", new RegionService.Listener() {
                public void onSuccess(List<BeanObject> beans) {
	            	for(Iterator<BeanObject> it = beans.iterator(); it.hasNext();){
	    				BeanObject cityShow = it.next();
	    				listBox.addItem(cityShow.getString(IRegion.NAME), cityShow.getString(IRegion.ID));
	    		    }
	            	if(!cityIndex.equals("0")){	            		
	            		for(int select = 1;select<listBox.getItemCount();select++){	            			
	            			if(listBox.getValue(select).equals(cityIndex)){
	            				listBox.setSelectedIndex(select);
	            				break;
	            			}	
	            		}
	            	}
	            }
	        });			
		}
	}

	public void refresh() {		
		city.clear();
		province.clear();
		city.addItem(Resources.constants.select_notice());
		city.setSelectedIndex(0);
		province.addItem(Resources.constants.select_notice());
		province.setSelectedIndex(0);
        new RegionService().getRegionChildList(COUNTRYID, new RegionService.Listener() {
            public void onSuccess(List<BeanObject> beans) {
                for(Iterator<BeanObject> it = beans.iterator(); it.hasNext();){
    				BeanObject provinceShow = it.next();
    				province.addItem(provinceShow.getString(IRegion.NAME), provinceShow.getString(IRegion.ID));
    		     }
            }
            public synchronized void onFailure(Throwable caught) {
                System.out.println("getRegionList onFailure("+caught);
            }
        });  
		
    	if(!province.isItemSelected(0)){
    		showRegion(city, province.getSelectedIndex(),(String)configs.get(SHOPCITY));
    	}
	}
}
