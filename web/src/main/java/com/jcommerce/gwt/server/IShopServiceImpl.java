/**
 * Author: Bob Chen
 */

package com.jcommerce.gwt.server;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import au.com.bytecode.opencsv.CSVReader;

import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.ListLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jcommerce.core.action.BeanConfig;
import com.jcommerce.core.action.DeleteAction;
import com.jcommerce.core.action.MapCreateAction;
import com.jcommerce.core.action.MapListAction;
import com.jcommerce.core.action.MapReadAction;
import com.jcommerce.core.action.MapUpdateAction;
import com.jcommerce.core.action.PropertyBeanConfig;
import com.jcommerce.core.database.DataBaseInitialize;
import com.jcommerce.core.database.DatabaseBackup;
import com.jcommerce.core.database.DatabaseFileInfo;
import com.jcommerce.core.database.DatabaseRestore;
import com.jcommerce.core.io.DisposePictures;
import com.jcommerce.core.io.FileManagerFactory;
import com.jcommerce.core.io.IFile;
import com.jcommerce.core.io.IFileManager;
import com.jcommerce.core.io.LocalFile;
import com.jcommerce.core.io.LocalFileManager;
import com.jcommerce.core.model.Gallery;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.Region;
import com.jcommerce.core.service.ArticleCategoryManager;
import com.jcommerce.core.service.AttributeManager;
import com.jcommerce.core.service.GalleryManager;
import com.jcommerce.core.service.GoodsManager;
import com.jcommerce.core.service.GoodsTypeManager;
import com.jcommerce.core.service.RegionManager;
import com.jcommerce.core.service.UserBonusManager;
import com.jcommerce.core.service.payment.IPaymentMetaManager;
import com.jcommerce.core.util.SpringUtil;
import com.jcommerce.gwt.client.IShopService;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAttribute;
import com.jcommerce.gwt.client.model.IBonusType;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IPayment;
import com.jcommerce.gwt.client.model.IUserBonus;
import com.jcommerce.gwt.client.model.SystemInfoKey;
import com.jcommerce.gwt.client.panels.system.PaymentConfigMetaForm;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;

public class IShopServiceImpl extends RemoteServiceServlet implements IShopService {
	
	private DeleteAction deleteAction;
	private MapReadAction readAction;
	private MapCreateAction createAction;
	private MapListAction listAction;
	private MapUpdateAction updateAction;
	private PagingListAction pagingAction;
//	IndexAction indexAction;
	TreeAction treeAction;
	private RegionManager regionManager;
	private GoodsTypeManager goodsTypeManager;
	private AttributeManager attributeManager;
	private GalleryManager galleryManager;
	private GoodsManager goodsManager;
	private DisposePictures disposePictures;
	private IPaymentMetaManager paymentMetaManager;
//	private EmailAccountManager emailAccountManager;
	ArticleCategoryManager articleCategoryManager;
	
//	DataSource dataSource;
	
	protected void doUnexpectedFailure(Throwable e) 
	{
		e.printStackTrace();
	}
	
	public IShopServiceImpl() {
//		String[] paths = { "/WEB-INF/applicationContext.xml" };
		ApplicationContext ctx /*= new ClassPathXmlApplicationContext(paths)*/ = null;
//		
//		dataSource = (DataSource) ctx.getBean("dataSource");
		
		Properties beanProps = new Properties();
		InputStream is = getClass().getResourceAsStream("beans.properties");
		try {
			beanProps.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		disposePictures = new DisposePictures();
		BeanConfig config = new PropertyBeanConfig(beanProps);
		deleteAction = new DeleteAction(ctx, config);
		readAction = new MapReadAction(ctx, config);
		createAction = new MapCreateAction(ctx, config);
		listAction = new MapListAction(ctx, config);
//		indexAction = new IndexAction(listAction);
		updateAction = new MapUpdateAction(ctx, config);
		treeAction = new TreeAction(ctx,config);
		pagingAction = new PagingListAction(ctx, config);
		regionManager = (RegionManager) ctx.getBean("RegionManager");
		goodsTypeManager = (GoodsTypeManager) ctx.getBean("GoodsTypeManager");
		attributeManager = (AttributeManager) ctx.getBean("AttributeManager");
		paymentMetaManager = (IPaymentMetaManager) ctx.getBean("PaymentMetaManager");
		galleryManager = (GalleryManager) ctx.getBean("GalleryManager");
		goodsManager = (GoodsManager) ctx.getBean("GoodsManager");
//		emailAccountManager = (EmailAccountManager) ctx.getBean("EmailAccountManager");
		articleCategoryManager = (ArticleCategoryManager)ctx.getBean("ArticleCategoryManager");
	}

	
	
//	public com.jcommerce.gwt.client.form.PaymentConfigMetaForm getPaymentConfigMeta(
//			int paymentId) {
//		PaymentConfigMeta configMeta = paymentMetaManager
//				.getPaymentConfigMeta(paymentId);
//		Map<String, PaymentConfigFieldMeta> fields = configMeta.getFieldMetas();
//		// BeanUtils.copyProperties();
//		PaymentConfigMetaForm res = new PaymentConfigMetaForm();
//		Map<String, PaymentConfigFieldMetaForm> resfields = new HashMap<String, PaymentConfigFieldMetaForm>();
//
//		res.setCod(configMeta.isCod());
//		res.setCode(configMeta.getCode());
//		res.setDescription(configMeta.getDescription());
//		res.setEnabled(configMeta.isEnabled());
//		res.setId(configMeta.getId());
//		res.setName(configMeta.getName());
//		res.setOnline(configMeta.isOnline());
//		res.setPayFee(configMeta.getPayFee());
//
//		for (String key : fields.keySet()) {
//			PaymentConfigFieldMeta fieldMeta = fields.get(key);
//			PaymentConfigFieldMetaForm resfield = new PaymentConfigFieldMetaForm();
//			resfield.setLable(fieldMeta.getLable());
//			resfield.setOptions(fieldMeta.getOptions());
//			resfield.setTip(fieldMeta.getTip());
//			resfields.put(key, resfield);
//		}
//		// com.jcommerce.gwt.client.panels.leontest.PaymentConfigFieldMeta();
//		res.setFieldMetas(resfields);
//		res.setFieldValues(configMeta.getFieldValues());
//		return res;
//
//	}

	@Override
    public void init() throws ServletException {
        super.init();
        
        String home = System.getProperty("JCOMMERCE_HOME");
        if (home == null || home.trim().length() == 0) {
            System.setProperty("JCOMMERCE_HOME", getServletContext().getRealPath("WEB-INF"));
        }
    }

    public boolean savePayment(Map<String, String> props) {
		try {
			paymentMetaManager.savePaymentConfig(props);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean installPayment(String paymentCode) {
		try {
			paymentMetaManager.install(paymentCode);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean uninstallPayment(int paymentId) {
		try {
			paymentMetaManager.uninstall(""+paymentId);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public ListLoadResult<BeanObject> getPaymentMetaList(ListLoadConfig config) {
		List<Map<String, String>> maps = paymentMetaManager
				.getCombinedPaymentMetaList();
		List<BeanObject> objs = new ArrayList<BeanObject>();
		for (Map<String, String> map : maps) {
			objs.add(new BeanObject(ModelNames.PAYMENT_META, (Map<String, Object>)(Map)map));
		}
		return new BaseListLoadResult<BeanObject>(objs);
	}

	public ListLoadResult<Map<String, String>> getMyPaymentMetaList(
			ListLoadConfig config) {
		List<Map<String, String>> maps = paymentMetaManager
				.getCombinedPaymentMetaList();

		List<Map<String, String>> objs = new ArrayList<Map<String, String>>();
		for (Map<String, String> map : maps) {
			objs.add(map);
		}
		return new BaseListLoadResult<Map<String, String>>(objs);
	}

	public boolean deleteObject(String modelName, String id) {
		System.out.println("deleteObject(" + modelName + "," + id);
		return deleteAction.delete(modelName, id);
	}

    public int deleteObjects(String modelName, List<String> ids) {
        System.out.println("deleteObjects(" + modelName + ", total:" + ids.size());
        int res = 0;
        for (String id : ids) {
            boolean r = false;
            try {
                r = deleteAction.delete(modelName, id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (r) {
                res++;
            }
        }

        return res;
    }

	public List<BeanObject> getBeans(String modelName, String[] ids) {
		System.out.println("getBeans(" + modelName);
		List<BeanObject> beans = new ArrayList<BeanObject>();
		if (ids == null || ids.length == 0) {
			throw new IllegalArgumentException("ids = null");
		}

		for (String id : ids) {
			beans.add(new BeanObject(modelName, readAction.getBean(modelName,
					id)));
		}
		return beans;
	}

	public BeanObject getBean(String modelName, String id) {
		System.out.println("getBean(" + modelName + "," + id);
		return new BeanObject(modelName, readAction.getBean(modelName, id));
	}

	public List<BeanObject> getList(String modelName) {
		return getList(modelName, null, null);
	}

	public List<BeanObject> getList(String modelName, Criteria criteria) {
		return getList(modelName, criteria, null);
	}

	public List<BeanObject> getList(String modelName, Criteria criteria,
			List<String> wantedFields) {
		try {
			System.out.println("getList(" + modelName);
			List<Map<String, Object>> maps = listAction.getList(modelName,
					convert(criteria), wantedFields);
			List<BeanObject> objs = new ArrayList<BeanObject>();
			for (Map<String, Object> map : maps) {
				objs.add(new BeanObject(modelName, map));
			}
			return objs;
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	public List<BeanObject> getModuleList(String bean){
		List<Map<String, Object>> maps = listAction.getModuleList(bean);
		
		List<BeanObject> objs = new ArrayList<BeanObject>();
		for (Map<String, Object> map : maps) {
			objs.add(new BeanObject(bean, map));
		}
		return objs;
	}
	
//	public PagingLoadResult<BeanObject> getModulePagingList(String modelName) {
//		try {
//			System.out.println("second>>>>>>>>>>>>>>>>>>>>>");
//			return pagingAction.getModulePagingList(modelName);
//		} catch (RuntimeException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw e;
//		}   
//		
//	}
//	
//	public PagingLoadResult<BeanObject> getIndexPagingList(String modelName, PagingLoadConfig config){
//		
//		return (PagingLoadResult<BeanObject>)indexAction.seacher(modelName);
//	}
	  public PagingLoadResult<BeanObject> getPagingList(String modelName, PagingLoadConfig config) {
	    	return getPagingList(modelName, null, null, config);
	    }
	    
	    public PagingLoadResult<BeanObject> getPagingList(String modelName, Criteria criteria, PagingLoadConfig config) {
	        return getPagingList(modelName, criteria, null, config);        
	    }
	    
	    public PagingLoadResult<BeanObject> getPagingList(String modelName, Criteria criteria, List<String> wantedFields, PagingLoadConfig config) {
	        try {
				return pagingAction.getPagingList(modelName, convert(criteria), wantedFields, config);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}        
	    }
	    public List<ModelData> getTreePagingList(String modelName, Criteria criteria, BeanObject config){
	    	 try {
					return treeAction.getTreeList(modelName,convert(criteria),config);
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw e;
				}       
	    }
	    

	public boolean updateObject(String id, BeanObject props) {
		System.out.println("updateObject(" + props.getModelName() + "," + id);
		return updateAction.updateObject(props.getModelName(), id, props
				.getProperties());
	}

	public boolean updateObject(String id, BeanObject props, BeanObject props1) {
//		System.out.println("updateObject(" + props.getModelName() + "," + id);
//		
//		String serializedConfig = Tools.getSerializedConfig(props1.getProperties());
//		Map<String,  Object> values = props.getProperties();
//		values.put("extraInfo", serializedConfig);
//		
//		props.setProperties(values);
//		return updateAction.updateObject(props.getModelName(), id, props
//				.getProperties());
		return false;
	}
	
	public String newObject(BeanObject props) {
		System.out.println("newObject(" + props.getModelName());
		return createAction.newObject(props.getModelName(), props
				.getProperties());
	}

	public String newObject(BeanObject props,BeanObject props1) {
//		System.out.println("newObject(" + props.getModelName());
//		String serializedConfig = Tools.getSerializedConfig(props1.getProperties());
//		Map<String,  Object> values = props.getProperties();
//		values.put("extraInfo", serializedConfig);
//		
//		props.setProperties(values);
//		
//		return createAction.newObject(props.getModelName(), props
//				.getProperties());
		return null;
	}
	
	private com.jcommerce.core.service.Criteria convert(Criteria criteria) {
		if (criteria == null) {
			return null;
		}

		com.jcommerce.core.service.Criteria _criteria = new com.jcommerce.core.service.Criteria();
		
		//modify by Daniel
		_criteria.setOperator(criteria.getOperator());
		
		List<Condition> conds = criteria.getConditions();
		for (Condition cond : conds) {
			com.jcommerce.core.service.Condition _cond = new com.jcommerce.core.service.Condition();
			_cond.setField(cond.getField());
			_cond.setValue(cond.getValue());
			_cond.setOperator(cond.getOperator());
			_criteria.addCondition(_cond);
		}

		List<String> groupBys = criteria.getGroupBys();
		_criteria.setGroupBys(groupBys);
		
		List<com.jcommerce.gwt.client.service.Order> orders = criteria.getOrders();
        for (com.jcommerce.gwt.client.service.Order order : orders) {
            com.jcommerce.core.service.Order _order = new com.jcommerce.core.service.Order();
            _order.setField(order.getField());
            _order.setAscend(order.isAscend());
            _criteria.addOrder(_order);
        }
		
		return _criteria;
	}

	public PagingLoadResult<BeanObject> getGoodsTypeUnit(
			boolean needAttrNumber, PagingLoadConfig config) {
		// Add something you like
		Map<String, Object> maps = new HashMap<String, Object>();
		if (needAttrNumber) {
			try {
				List<GoodsType> goodsTypeUnit = goodsTypeManager
						.getGoodsTypeList();
				List<BeanObject> objs = new ArrayList<BeanObject>();
				com.jcommerce.core.service.Condition cond = new com.jcommerce.core.service.Condition();
				cond.setField(IAttribute.GOODSTYPE);
				cond.setOperator(Condition.EQUALS);
				com.jcommerce.core.service.Criteria criteria = new com.jcommerce.core.service.Criteria();

				for (Iterator<GoodsType> it = goodsTypeUnit.iterator(); it
						.hasNext();) {
					GoodsType goodsType = it.next();
					maps.put(GoodsType.NAME, goodsType.getName());
					maps.put(GoodsType.ID, goodsType.getId());
					maps.put(GoodsType.ENABLED, goodsType.isEnabled());
					cond.setValue(goodsType.getId() + "");
					criteria.addCondition(cond);
					maps.put(GoodsType.ATTRCOUNT, attributeManager
							.getAttributeCount(criteria)
							+ "");
					criteria.removeAll();
					maps.put(GoodsType.ATTRIBUTEGROUP, goodsType
							.getAttributeGroup());
					objs.add(new BeanObject("GoodsType", maps));
				}
				return new BasePagingLoadResult(objs, config.getOffset(),
						goodsTypeUnit.size());
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		} else {
			return pagingAction.getPagingList("GoodsType", null, config);
		}

	}

	public List<BeanObject> getRegionChildren(String parent_id) {
		List<Region> children = regionManager.getChildList(parent_id);
		List<BeanObject> objs = new ArrayList<BeanObject>();
		for (Iterator<Region> it = children.iterator(); it.hasNext();) {
			Region child = it.next();
	        Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("name", child.getName());
			maps.put("id", child.getId());
			maps.put("parent", child.getParent().getId());
			objs.add(new BeanObject("Region", maps));
		}
		return objs;
	}

    public List<BeanObject> getRegionAncestors(String id) {
        List<BeanObject> objs = new ArrayList<BeanObject>();
        
        RegionManager manager = SpringUtil.getRegionManager();
        Region region = manager.getRegion(id);
        while(region != null) {
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("name", region.getName());
            maps.put("id", region.getId());
            maps.put("parent", region.getParent().getId());
            objs.add(0, new BeanObject("Region", maps));
            
            region = region.getParent();
        }
        
        return objs;
    }

	public String generateSpecialFilePath(String[] filePath_Pieces,
			String specialCategory) {
		String SpecialFilePath = "";
		specialCategory = "_" + specialCategory + ".";
		for (int i = 0; i < filePath_Pieces.length; i++) {
			if (i == filePath_Pieces.length - 1) {
				SpecialFilePath = SpecialFilePath.substring(0, SpecialFilePath
						.length() - 1);
				SpecialFilePath += specialCategory + filePath_Pieces[i];
			} else {
				SpecialFilePath += filePath_Pieces[i] + ".";
			}

		}
		return SpecialFilePath;
	}

	/**
	 */
	public String disposePhotos(BeanObject goods, boolean isGenerateDetails,
			boolean isGenerateThumbnails, boolean isErroSkip) {
		String picturesID = (String) goods.get(IGoods.GALLERIES);
		Gallery picture = galleryManager.getGallery(picturesID);
		String filePath = picture.getOriginalImage();
		String[] filePath_Pieces = null;
		String thumb_filePath = "";
		String img_filePath = "";
		String desc_filePath = "";

		try {
			if (filePath.indexOf('.') < 0)
				throw new Exception("");
			filePath_Pieces = filePath.split("\\.");
			thumb_filePath = generateSpecialFilePath(filePath_Pieces, "Thumb");
			img_filePath = generateSpecialFilePath(filePath_Pieces, "Img");
			desc_filePath = generateSpecialFilePath(filePath_Pieces, "Desc");

			disposePictures.createMark(filePath, "");
			disposePictures.pressText(filePath, "Monospaced", Font.BOLD,
					Color.LIGHT_GRAY, 50, 200, 300);
			if (isGenerateThumbnails) {
				disposePictures.saveImageAsJpg(filePath, thumb_filePath, 100,
						100);
			}
			disposePictures.saveImageAsJpg(filePath, img_filePath, 300, 300);
			if (isGenerateDetails) {
				disposePictures.saveImageAsJpg(filePath, desc_filePath, 600,
						600);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "*澶勭悊鍥剧墖ID锟� " + picturesID + "鏃跺嚭锟�" + "鍏跺浘鐗囪矾寰勪负: " + filePath;

		}
		picture.setThumbUrl(thumb_filePath);
		picture.setImageUrl(img_filePath);
		picture.setDescription(desc_filePath);

		galleryManager.saveGallery(picture);

		return null;
	}

	/**
	 */
	public String disposePictures(BeanObject result, boolean isGenerateDetails,
			boolean isGenerateThumbnails, boolean isErroSkip) {

		String goodsID = result.get(IGoods.ID);
		Goods goods = goodsManager.getGoods(goodsID);
		String filePath = goods.getOriginalImage();
		String[] filePath_Pieces = null;
		String thumb_filePath = "";
		String img_filePath = "";
		String desc_filePath = "";

		try {
			if (filePath.indexOf('.') < 0)
				throw new Exception("");
			filePath_Pieces = filePath.split("\\.");
			thumb_filePath = generateSpecialFilePath(filePath_Pieces, "Thumb");
			img_filePath = generateSpecialFilePath(filePath_Pieces, "Img");
			desc_filePath = generateSpecialFilePath(filePath_Pieces, "Desc");

			disposePictures.createMark(filePath, "");
			disposePictures.pressText(filePath, "Monospaced", Font.BOLD,
					Color.LIGHT_GRAY, 50, 200, 300);


			if (isGenerateThumbnails)
				disposePictures.saveImageAsJpg(filePath, thumb_filePath, 100,
						100);
			disposePictures.saveImageAsJpg(filePath, img_filePath, 300, 300);
			if (isGenerateDetails)
				disposePictures.saveImageAsJpg(filePath, desc_filePath, 600,
						600);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "*澶勭悊鍟嗗搧ID鐨勪负: " + goodsID + "鐨勫浘鐗囨椂鍑洪敊," + "鍏跺浘鐗囪矾寰勪负: " + filePath;
		}

		goods.setImage(img_filePath);
		goods.setThumb(thumb_filePath);
		goods.setDescription(desc_filePath);

		goodsManager.saveGoods(goods);

		return null;
	}

	public PagingLoadResult<BeanObject> getAllDiliveryMethods(PagingLoadConfig config) {
		
		return new BasePagingLoadResult(ShippingResourceUtil.getShippingBeanObjects());
	}
	
	public PagingLoadResult<BeanObject> getAllPaymentMethods(PagingLoadConfig config) {
		
		List<BeanObject> beans = PaymentResourceUtil.getPaymentBeanObjects();
		System.out.println(beans.size() + " \t :beans.size()");
		for(int i=0; i<beans.size(); i++)
		{
			BeanObject bean = beans.get(i);
			System.out.println(bean.getString(IPayment.NAME) + "---------+++++++++----");
		}
		PagingLoadResult<BeanObject> pagingLoad = new BasePagingLoadResult(beans);
		
		return pagingLoad;
	}
	/*public List<Shipping> getAllDiliveryMethods() {
		
		return null;
	}*/


    // added: email server settings.
	public HashMap<String, String> getEmailServerSettings() {
		Properties properties = new Properties();
		Enumeration enumeration = null;
		HashMap<String, String> emailSettings = new HashMap<String, String>();
		try {
			InputStream inputStream = getClass().getResourceAsStream("EmailServerSettings.properties");
			properties.load(inputStream);
			
			enumeration = properties.propertyNames();
			while(enumeration.hasMoreElements()) {
				String key = (String)enumeration.nextElement(); 
				String value = properties.getProperty(key); 
				emailSettings.put(key, value);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found while reading the properties of email server settings.");
		} catch (IOException e) {
			throw new RuntimeException("IO exception while reading the properties of email server settings.");
		}
		return emailSettings;
	}

	// added: set email server information
	public boolean setEmailServerSettings(HashMap<String, String> settings) {
		
		Properties properties = new Properties();
		try {
			FileOutputStream outputStream = new FileOutputStream(System.getProperty("user.dir") + "/src/com/jcommerce/gwt/server/EmailServerSettings.properties");
			Set<String> keys = settings.keySet();
			for(String key : keys) {
				String value = settings.get(key);
				properties.setProperty(key, value);
			}
			properties.store(outputStream, "set");
		} catch (FileNotFoundException e) {
			throw new RuntimeException("file not found while writing the server settings properties file.");
		} catch (IOException e) {
			throw new RuntimeException("IO exception while writing the server settings properties file.");
		}
		return true;
	}
	
	// added : send email
	public boolean sendEmailAndGetState(Map<String, String> email) {
		boolean state = false;
		Properties properties = new Properties();		
		try {
			InputStream inputStream = getClass().getResourceAsStream("EmailServerSettings.properties");
			properties.load(inputStream);
			
			String username = properties.getProperty("account");
			String password = properties.getProperty("password");
			String smtpServer = properties.getProperty("serverAddress");
			String sendTo = email.get("sendTo");
			String subject = email.get("subject");
			String content = email.get("content");
			String[] sendToGroup = sendTo.split(";");
			
			MailSender sender = MailSender.getHtmlMailSender(smtpServer, username, password);	
			sender.setSubject(subject);
			sender.setSendDate(new Date());
			sender.setMailContent(content); // 
			sender.setMailFrom(username);
			sender.setMailTo(sendToGroup, "to");			
			sender.sendMail();
			state = true;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found while reading the properties of email server settings.");
		} catch (IOException e) {
			throw new RuntimeException("IO exception while reading the properties of email server settings.");
		} catch (Exception e) {
			return state;
		}
		return state;
	}

	// added : send test email
	public boolean sendTestEmailAndGetState() {
		boolean state = false;
		Properties properties = new Properties();		
		try {
			InputStream inputStream = getClass().getResourceAsStream("EmailServerSettings.properties");
			properties.load(inputStream);
			
			String username = properties.getProperty("account");
			String password = properties.getProperty("password");
			String smtpServer = properties.getProperty("serverAddress");
			String sendTo = properties.getProperty("emailAddress");
			String subject = "Test Email Sent By Monkey";
			String content = "This is test email, please don't reply.";
			String[] sendToGroup = { sendTo };
			
			MailSender senderTest = MailSender.getHtmlMailSender(smtpServer, username, password);	
			senderTest.setSubject(subject);
			senderTest.setSendDate(new Date());
			senderTest.setMailContent(content); // 
			senderTest.setMailFrom(username);
			senderTest.setMailTo(sendToGroup, "to"); 
//			sendmail.setMailTo(toAddress, "cc"); 
			senderTest.sendMail();
			state = true;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found while reading the properties of email server settings.");
		} catch (IOException e) {
			throw new RuntimeException("IO exception while reading the properties of email server settings.");
		} catch (Exception e) {
			throw new RuntimeException("send test email failure.");
		}
		return true;
	}

	/**
	 * backup the database
	 */
	  public String Backup(String backUpFileName) {	      
			DatabaseBackup backup=null;
			DatabaseFileInfo dbf=new DatabaseFileInfo();
						
		
			try {
					Boolean isHaveSameName=dbf.ifHasSameFile(backUpFileName);
					if(isHaveSameName==true){
						return "sameFileName";
					}
					backup=new DatabaseBackup(backUpFileName);
					backup.backup();
					return "success";
                } catch (SQLException e) {
                    e.printStackTrace();
                    return "SQLException";
                } catch (IOException e) {
                    e.printStackTrace();
                    return "IOException";
                }
}
	  
	
	/**
	 * get basic information of all files
	 */
    public List<List<String>> getAllFileInfo() {
        List<List<String>> filesInfo = new ArrayList<List<String>>();
        DatabaseFileInfo dbf = new DatabaseFileInfo();
        try {
            filesInfo = dbf.getAllFileInfo();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return filesInfo;
    }

		/**
		 * delete one file
		 */
	  public String deleteFile(String fileName){
		  try{
			  DatabaseFileInfo dbf=new DatabaseFileInfo();
			  dbf.deleteFile(fileName);
			  return "success";
		  }catch(RuntimeException e){
			  e.printStackTrace();
			  return "failure";
		  }
	  }
	
	/**
	 * restore a file to database
	 */
	    public String restoreFile(String fileName){
	    	
	    	try{
		    	DatabaseRestore dbr=new DatabaseRestore();
		    	dbr.restore(fileName);
		    	return "success";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "UnsupportedEncodingException";
            } catch (IOException e) {
                e.printStackTrace();
                return "IOException";
            } catch (SQLException e) {
                e.printStackTrace();
                return "SQLException";
            }	    	
	    }

	/**
	 * initialize database
	 */
	    public String initialize(){
	    	try{
	    		DataBaseInitialize dbInit=new DataBaseInitialize();
	    		dbInit.initialize();
	    		return "success";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "UnsupportedEncodingException";
            } catch (IOException e) {
                e.printStackTrace();
                return "IOException";
            } catch (SQLException e) {
                e.printStackTrace();
                return "SQLException";
            }
	    }
	
	    /*
	     * 妫ｆ牕鍘涢柅姘崇箖account閿涘牐澶勯幋鍑ょ礆->EmailUID閸忓磭閮撮弻銉﹀UID閺勵垰鎯佺�妯烘躬閺夈儱鍨介弬顓☆嚉闁喕娆㈤弰顖氭儊閺勵垱鏌婇柇顔绘閿�  * 濮ｅ繑顐奸幒銉︽暪閸氬海娈戦柇顔绘鐎涙ê鍙嗛弫鐗堝祦鎼存挷鑵戦敍灞芥倱閺冭埖娲块弬鐧甤count uid閻ㄥ嫯锟�	     */
//	    public boolean receiveNewEmail() {
//	        boolean isSuccess = false;
//	        Properties properties = new Properties();
//	        String username = ""; // 閹恒儲鏁圭拹锔藉煕閻ㄥ嫮鏁ら幋宄版倳
//	        String password = ""; // 閹恒儲鏁圭拹锔藉煕閻ㄥ嫬鐦戦惍?        
//	        try {
//	            InputStream inputStream = getClass().getResourceAsStream("EmailServerSettings.properties");
//	            properties.load(inputStream);
//	            
//	            username = properties.getProperty("account");
//	            password = properties.getProperty("password");
//	            System.out.println( username + password );
//	            // 鐠併倛锟�	            
//	            Properties props = System.getProperties(); 
//	            props.put("mail.smtp.host", "smtp.163.com");   
//	            props.put("mail.smtp.auth", "true");
//	            Session session = Session.getDefaultInstance(props, null);
//	            
//	            URLName urln = new URLName("pop3", "pop3.163.com", 110, null, username, password);
//	            Store store = session.getStore(urln);
//	            store.connect();
//	            System.out.println("connecting successfully!");
//	            Folder folder = store.getFolder("INBOX");   
//	            folder.open(Folder.READ_ONLY);
//	            // 閸掋倖鏌噁older鐎圭偘绶ラ敍灞界繁閸掔櫊ID閿涘湶ID娑撳秹娓剁憰浣风瑓鏉炰粙鍋栨禒璺哄敶鐎圭櫢绱濋懓瀹甧ssageId闂団偓鐟曚椒绗呮潪浠嬪仏娴犺泛銇旈敍灞界发閼版妞傞敍?          
//	            if( folder instanceof POP3Folder ) {
//	                POP3Folder inbox = (POP3Folder)folder;
//	                Message message[] = inbox.getMessages();
//	                // 瀵版鍩岀�妯绘杹闁喕娆㈤惃鍕殶缂佸嫸绱濇＃鏍у帥閸掋倖鏌嘦ID閺勵垰鎯佺�妯烘躬閵嗗倸浜ｆ俊鍌欑瑝鐎涙ê婀敍灞界繁閸掓澘鎮囨稉顏勭潣閹冣偓闂寸箽鐎涙ê鎮撻弮鑸垫纯閺傜櫘ccount閸滃ID閻ㄥ嫪绔寸�鐟邦樋閻ㄥ嫯銆冮妴?               
//	                for( int i = 0; i < message.length; i++ ) {
//	                    MimeMessage mimeMessage = (MimeMessage) message[i];
//	                    String uid = inbox.getUID( mimeMessage );
//	                    // 閸掋倖鏌噓id閺勵垰鎯佺�妯猴拷?	                    
//	                    boolean isExist = false;
//	                    String hsql = "from EmailAccount as t where t.emailAccount = '" + username + "'";
//	                    List<EmailAccount> accounts= emailAccountManager.getList(hsql);
//	                    for( EmailAccount email : accounts ) {
//	                        if( email.getUid().equals(uid) ) {
//	                            isExist = true;
//	                            break;
//	                        }
//	                    }
//	                    // 婵″倹鐏塽id娑撳秴鐡ㄩ崷?鐠囧瓨妲戝銈夊仏娴犳湹璐熼弬浼村仏锟�閹绘劕褰囬柇顔绘娣団剝浼呮穱婵嗙摠锟�                  
//	                    if ( !isExist ) {
//	                        System.out.println(" this is new email !");
//	                        MailReceiver mailReceiver = new MailReceiver( mimeMessage );
//	                        System.out.println(mailReceiver.getSubject());
//	                        Map<String, Object> pros = new HashMap<String, Object>();
//	                        pros.put(IEmailReceiver.SENDERNAME, mailReceiver.getFromAddress());
//	                        mailReceiver.getMailContent( (Part) mimeMessage );
//	                        pros.put(IEmailReceiver.EMAILCONTENT, mailReceiver.getContent());
//	                        pros.put(IEmailReceiver.MAILSUBJECT, mailReceiver.getSubject());
//	                        mailReceiver.setAttachPath("C:\\");
//	                        mailReceiver.saveAttachMent((Part) message[i]);
////	                      StringBuffer attachFile = new StringBuffer();
////	                      for( String attach : list ) {
////	                          attachFile.append( "," + attach );
////	                      }
////	                      String allFiles = "";
////	                      if( list.size() > 1 ) {
////	                          allFiles = attachFile.toString().substring(1);
////	                      }
////	                      pros.put(IEmailReceiver.ATTACHFILENAME, allFiles);
//	                        pros.put(IEmailReceiver.ISATTACH, mailReceiver.isContainAttach((Part) message[i]));
//	                        pros.put(IEmailReceiver.ISREAD, isExist);
//	                        pros.put(IEmailReceiver.RECEIVERNAME, username);
//	                        mailReceiver.setDateFormat("yy楠炵M閺堝潐d锟�HH:mm");
//	                        pros.put(IEmailReceiver.RECEIVETIME, mailReceiver.getSentDate());
//	                        System.out.println(" here junk email !");
//	                        // 閸ㄥ啫婧囬柇顔绘閻ㄥ嫬鍨介弬?                      
//	                        System.out.println(" deal with new email! ");
//	                        // 閺備即鍋栨禒鍓佹畱婢跺嫮锟�	                        
//	                        StringBuffer sb = new StringBuffer();
//	                        if( mailReceiver.getContent().contains("<") ) {
//	                            sb.append(mailReceiver.getContent().substring(0, mailReceiver.getContent().indexOf('<')) + "\n" + mailReceiver.getSubject());
//	                        } else {
//	                            sb.append(mailReceiver.getContent() + "\n" + mailReceiver.getSubject());
//	                        }
//	                        BeanObject emailObject = new BeanObject(ModelNames.EMAILRECEIVER, pros);
//	                        newObject(emailObject);
//
//	                        Map<String, Object> relations = new HashMap<String, Object>();
//	                        relations.put(IEmailAccount.EMAILACCOUNT, username);
//	                        relations.put(IEmailAccount.UID, uid);
//	                        BeanObject relation = new BeanObject(ModelNames.EMAILACCOUT, relations);
//	                        newObject(relation);
//	                    }
//	                }
//	                isSuccess = true;
//	            } else if( folder instanceof IMAPFolder ) {
//	                IMAPFolder inbox = (IMAPFolder) folder;
//	                Message message[] = inbox.getMessages();
//	                for( int i = 0; i < message.length; i++ ) {
//	                    String uid = Long.toString(inbox.getUID((MimeMessage)message[i]));                  
//	                }
//	            }
//	        } catch (FileNotFoundException e) {
//	            throw new RuntimeException("File not found while reading the properties of email server settings.");
//	        } catch (IOException e) {
//	            throw new RuntimeException("IO exception while writing the server settings properties file.");
//	        } catch (NoSuchProviderException e) {
//	            throw new RuntimeException("No such provider exception : " + e.getMessage());
//	        } catch (MessagingException e) {
//	            throw new RuntimeException("Message exception : " + e.getMessage());
//	        } catch (Exception e) {
//	            throw new RuntimeException("connection exception : " + e.getMessage());
//	        } 
//	        return isSuccess;
//	    }

        public PagingLoadResult<BeanObject> getIndexPagingList(String modelName, PagingLoadConfig config) {
            // TODO Auto-generated method stub
            new Exception("getIndexPagingList unimplemented").printStackTrace();
            return null;
        }

        public PagingLoadResult<BeanObject> getModulePagingList(String modelName) {
            new Exception("getModulePagingList unimplemented").printStackTrace();
            return null;
        }

        public PaymentConfigMetaForm getPaymentConfigMeta(String paymentId) {
            new Exception("getPaymentConfigMeta unimplemented").printStackTrace();
            return null;
        }

        public boolean uninstallPayment(String paymentId) {
            new Exception("uninstallPayment unimplemented").printStackTrace();
            return false;
        }

        public boolean receiveNewEmail() {
            new Exception("receiveNewEmail unimplemented").printStackTrace();
            return false;
        }

        public String generateReport(String name, Map<String, String> params) {
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
            
//            try {            
//                Map _params = getParameters(name, params);
//
//                InputStream is = FileManagerFactory.getFileManager().getFile("reports/"+name+".jrxml").getInputStream();
//                JasperReport report = JasperCompileManager.compileReport(is);
//                is.close();
//                JasperPrint print = JasperFillManager.fillReport(report, _params, dataSource.getConnection());
//                
////                transaction.rollback();
////                session.close();
//  
//                JRHtmlExporter exporter = new JRHtmlExporter();
//                
//                OutputStream os = FileManagerFactory.getFileManager().createFile("reports/result/"+name+".html").getOutputStream();
//                
//                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
//                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);                
//                exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "");
//                exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
////                exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
//                exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "reportService/reports/result/"+name+".html_files/");
//                exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.FALSE);
//                
//                Map images = new HashMap();
//                
//                exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, images);
//                
//                exporter.exportReport();
//                
//                os.close();
//                
//                for (Iterator<String> it = images.keySet().iterator() ; it.hasNext() ; ) {
//                    String imageName = it.next();
//                    byte[] content = (byte[])images.get(imageName);
//                    
//                    FileManagerFactory.getFileManager().createFile("reports/result/"+name+".html_files/"+imageName).saveContent(content);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JRException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            
            return "reportService/reports/result/"+name+".html";
        }

        private static Map getParameters(String reportName, Map<String, String> params)
        {
            Map _params = new HashMap();
            _params.putAll(params);
            
//            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            
            if ("SalesReport".equalsIgnoreCase(reportName)) {
//                parameters.put(JRHibernateQueryExecuterFactory.PARAMETER_HIBERNATE_SESSION, session);
//                String key = "STARTDATE";
//                String s = params.get(key);
//                if (s != null) {
//                    try {
//                        _params.put(key, format.parse(s));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//                
//                key = "ENDDATE";
//                s = params.get(key);
//                if (s != null) {
//                    try {
//                        _params.put(key, format.parse(s));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
            } else {
                throw new RuntimeException("Unknown report: " + reportName);
            }
            return _params;
        }

        public String getOrderTemplate() {
            byte[] bytes;
            try {
                bytes = FileManagerFactory.getFileManager().getFile("template/order_print.html").readContent();
            } catch (IOException e) {
                e.printStackTrace();
                bytes = new byte[0];
            }
            return new String(bytes);
        }

        public boolean purgeGoods(String id) {
            GoodsManager manager = SpringUtil.getGoodsManager();
            manager.purgeGoods(id);
            return true;
        }

        public boolean undoDeletedGoods(String id) {
            GoodsManager manager = SpringUtil.getGoodsManager();
            manager.undoDeletedGoods(id);
            return true;
        }

        public int countBeans(String modelName) {
            int count = listAction.getCount(modelName, null);
            return count;
        }
        
        public int countBeans(String modelName, Criteria criteria) {
            int count = listAction.getCount(modelName, convert(criteria));
            return count;
        }

        public PagingLoadResult<BeanObject> getUserBonusPagingList(Criteria criteria, PagingLoadConfig config) {
            UserBonusManager userBonusManager = SpringUtil.getUserBonusManager();
            
            PagingLoadResult<BeanObject> result = pagingAction.getPagingList(ModelNames.BONUSTYPE, convert(criteria), null, config);
            List<BeanObject> bonusTypes = result.getData();
            for (BeanObject bonusType : bonusTypes) {
                com.jcommerce.core.service.Criteria _criteria = new com.jcommerce.core.service.Criteria(); 
                _criteria.addCondition(new com.jcommerce.core.service.Condition(IUserBonus.BONUS_TYPE, com.jcommerce.core.service.Condition.EQUALS, bonusType.getString(IBonusType.ID)));
                int count = userBonusManager.getUserBonusCount(_criteria);
                bonusType.set(IBonusType.NUMBER, count);
                _criteria.addCondition(new com.jcommerce.core.service.Condition(IUserBonus.USED_TIME, com.jcommerce.core.service.Condition.ISNULL, bonusType.getString(IBonusType.ID)));
                count = userBonusManager.getUserBonusCount(_criteria);
                bonusType.set(IBonusType.USED_NUMBER, count);
            }
            return result;
        }

        public BeanObject getSystemInfo() {
            BeanObject info = new BeanObject();

            String osName = System.getProperty("os.name");
            String osVersion = System.getProperty("os.version");
            
            info.set(SystemInfoKey.OS_NAME, osName);
            info.set(SystemInfoKey.OS_VERSION, osVersion);
            info.set(SystemInfoKey.VERSION, "1.0.0");
            
            int waitingShipping = 0;
            int uncomfirmShipping = 0;
            int waitingPay = 0;
            int completeOrder = 0;
            List<Order> orders = SpringUtil.getOrderManager().getOrderList();
            if (orders != null) {
                for (Order order : orders) {
                    int os = order.getStatus();
                    int ss = order.getShippingStatus();
                    int ps = order.getPayStatus();
                    if (os == Order.ORDER_CONFIRMED && (ss == Order.SHIPPING_UNSHIPPED || ss == Order.SHIPPING_PREPARING)) {
                        if (ps == Order.PAY_PAYED || ps == Order.PAY_PAYING) {
                            waitingShipping++;
                        } else {
                            
                        }
                    } else if (order.getShippingStatus() == Order.SHIPPING_SHIPPED) {
                        uncomfirmShipping++;
                    } else if (order.getShippingStatus() == Order.SHIPPING_RECEIVED) {
                        waitingPay++;
                    }
                }
            }
            info.set(SystemInfoKey.SHIPPING_WAITING_ORDERS, waitingShipping);
            info.set(SystemInfoKey.COMFIRM_WAITING_ORDERS, uncomfirmShipping);
            info.set(SystemInfoKey.PAY_WAITING_ORDERS, waitingPay);
            info.set(SystemInfoKey.COMPLETE_ORDERS, completeOrder);
            
            return info;
        }
        
        public List<BeanObject> getBeansFromFile(String modelName, String path, String category, String encoding){
        	FileManagerFactory managerFactory = new FileManagerFactory();
    		IFileManager fileManager = (LocalFileManager) managerFactory.getFileManager();
    		IFile file = (LocalFile) fileManager.getFile(path);
    		try {
				ByteArrayInputStream byteStream = new ByteArrayInputStream(file.readContent());
				InputStreamReader streamReader = new InputStreamReader(byteStream, encoding);
				CSVReader reader = new CSVReader(streamReader);
				
				List<BeanObject> objs = new ArrayList<BeanObject>();
				Map<String, Object> values = new HashMap<String, Object>();
				
				List<String[]> allElements = reader.readAll();
				Iterator<String[]> it = allElements.iterator();
				
				it.next();
				
				for (; it.hasNext();) {
					String[] element = it.next();

					values.put(IGoods.NAME, element[0]);
					values.put(IGoods.SN, element[1]);
					values.put(IGoods.BRAND, element[2]);
					values.put(IGoods.MARKETPRICE, element[3]);
					values.put(IGoods.SHOPPRICE, element[4]);
					values.put(IGoods.INTEGRAL, element[5]);
					values.put(IGoods.ORIGINALIMAGE, element[6]);
					values.put(IGoods.IMAGE, element[7]);
					values.put(IGoods.THUMB, element[8]);
					values.put(IGoods.KEYWORDS, element[9]);
					values.put(IGoods.BRIEF, element[10]);
					values.put(IGoods.DESCRIPTION, element[11]);
					values.put(IGoods.WEIGHT, element[12]);
					values.put(IGoods.NUMBER, element[13]);
					values.put(IGoods.WARNNUMBER, element[14]);
					values.put(IGoods.BESTSOLD, element[15]);
					values.put(IGoods.NEWADDED, element[16]);
					values.put(IGoods.HOTSOLD, element[17]);
					values.put(IGoods.ONSALE, element[18]);
					values.put(IGoods.ALONESALE, element[19]);
					values.put(IGoods.REALGOODS, element[20]);
					values.put(IGoods.CATEGORIES, category);

					BeanObject obj = new BeanObject(modelName, values);
					objs.add(obj);
					
					createAction.newObject(obj.getModelName(), obj.getProperties());
				}
				
				fileManager.deleteFile(path);

				return objs;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
        	
        	
        }
}
