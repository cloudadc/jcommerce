package com.jcommerce.gwt.client.form;

import java.util.Map;

import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IGoodsType;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.widgets.MyHTMLEditor;

public class GoodsForm extends BeanObject implements IGoods {
	
	public GoodsForm() {
		super();
	}
	public GoodsForm(String modelName) {
		super(modelName);
	}
	public GoodsForm(String modelName, Map<String, Object>props) {
		super(modelName,props);
	}
	
	public static String validate(Map<String, String> props) {
		String error = null;
		Object endDate = props.get(PROMOTEEND);
		Object startDate = props.get(PROMOTESTART);
		// TODO
		// if(endDate<startDate) error = "enddate should after startdate";
		return error;
		
	}
	
	public static HiddenField<String> getIdField() {
		HiddenField<String> idField = new HiddenField<String>();
		idField.setName(ID);
		return idField;
	}
	
	public static TextField<String> getNameField(String fieldTitle) {
		TextField<String> field = new TextField<String>();
		field.setName(NAME);
		field.setMaxLength(10);
		field.setAutoValidate(true);
		field.setAllowBlank(false);
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));
		field.setMessages(tfm);
		return field;
	}
	
	public static ComboBox<BeanObject> getBrandIdField() {
		ComboBox<BeanObject> field = new ComboBox<BeanObject>();
		field.setName(BRAND);
		field.setDisplayField(IBrand.NAME);
        field.setValueField(IBrand.ID);
        field.setAllowBlank(false);
		return field;
	}
	
	public static ComboBox<BeanObject> getGoodsTypeIdField() {
		ComboBox<BeanObject> field = new ComboBox<BeanObject>();
		field.setName(TYPE);
		field.setDisplayField(IGoodsType.NAME);
        field.setValueField(IGoodsType.ID);
		return field;
	}
	
	public static ComboBox<BeanObject> getCatIdField() {
		ComboBox<BeanObject> field = new ComboBox<BeanObject>();
		field.setName(MAINCATEGORY);
		field.setDisplayField(ICategory.NAME);
        field.setValueField(ICategory.ID);
        field.setAllowBlank(false);
		return field;
	}
	public static ListField<BeanObject> getCategoryIdsField() {
		ListField<BeanObject> field = new ListField<BeanObject>();
		field.setName(CATEGORIES);
        field.setDisplayField(ICategory.NAME);
        field.setValueField(ICategory.ID);
		return field;
	}
	public static TextField<String> getSnField() {
		TextField<String> field = new TextField<String>();
		field.setName(SN);
		return field;
	}
	
	public static NumberField getShopPriceField() {
		NumberField field = new NumberField();
		field.setName(SHOPPRICE);
		field.setAllowNegative(false);
		return field;
	}
	public static NumberField getMarketPriceField() {
		NumberField field = new NumberField();
		field.setName(MARKETPRICE);
		field.setAllowNegative(false);
		return field;
	}
	public static NumberField getGiveIntegralField() {
		NumberField field = new NumberField();
		field.setName(INTEGRAL);
		field.setAllowNegative(true);
		field.setAllowDecimals(false);
		return field;
	}
	public static NumberField getRankIntegralField() {
		NumberField field = new NumberField();
		field.setName(INTEGRAL);
		field.setAllowNegative(true);
		field.setAllowDecimals(false);
		return field;
	}
	
	public static NumberField getIntegralField() {
		NumberField field = new NumberField();
		field.setName(INTEGRAL);
		field.setAllowNegative(true);
		field.setAllowDecimals(false);
		return field;
	}
	public static CheckBox getIsPromoteField() {
		CheckBox field = new CheckBox();
		field.setName(PROMOTED);
		return field;
	}
	public static NumberField getPromotePriceField() {
		NumberField field = new NumberField();
		field.setName(PROMOTEPRICE);
		field.setAllowNegative(false);
		return field;
	}
	public static DateField getPromoteStartDateField() {
		DateField field = new DateField();
		field.setName(PROMOTESTART);
		return field;
	}
	public static DateField getPromoteEndDateField() {
		DateField field = new DateField();
		field.setName(PROMOTEEND);
		return field;
	}
	public static FileUploadField getImageField() {
		FileUploadField field = new FileUploadField();
		field.setName(IMAGE);
		return field;
	}
//	public static FileUploadField getThumbField() {
//		FileUploadField field = new FileUploadField();
//		field.setName(THUMB);
//		return field;
//	}
	public static NumberField getWeightField(String fieldTitle) {
		NumberField field = new NumberField();
		field.setName(WEIGHT);
		field.setAllowNegative(false);
		return field;
	}
	
	public static NumberField getNumberField(String fieldTitle) {
		NumberField field = new NumberField();
		field.setName(NUMBER);
		field.setAllowNegative(false);
		field.setAllowDecimals(false);
		return field;
	}
	
	public static CheckBox getHotSoldField() {
		CheckBox field = new CheckBox();
		field.setName(HOTSOLD);
		field.setValueAttribute("true");
		field.setAutoValidate(true);
		return field;
	}
	public static CheckBox getNewAddedField() {
		CheckBox field = new CheckBox();
		field.setName(NEWADDED);
		field.setValueAttribute("true");
		field.setAutoValidate(true);
		return field;
	}
	public static CheckBox getBestSoldField() {
		CheckBox field = new CheckBox();
		field.setName(BESTSOLD);
		field.setValueAttribute("true");
		field.setAutoValidate(true);
		return field;
	}
	
	public static CheckBox getIsOnSaleField() {
		CheckBox field = new CheckBox();
		field.setName(ONSALE);
		field.setValueAttribute("true");
		field.setAutoValidate(true);
		return field;
	}
	
	public static HtmlEditor getDescField() {
		MyHTMLEditor field = new MyHTMLEditor() {
			@Override
			protected boolean validateValue(String value) {
				if(super.validateValue(value) == false) {
					return false;
				}
//				if(value!=null && value.length()>500) {
//					markInvalid("Size of content must be less than 500 words.");
//					return false;
//				}
				return true;
			}			
		};
		field.setName(DESCRIPTION);
		field.setAutoValidate(true);
		return field;
	}
//	public static HiddenField<String> getGoogleBaseDataIdField() {
//		HiddenField<String> googleBaseDataIdField = new HiddenField<String>();
//		googleBaseDataIdField.setName(GOOGLE_BASE_DATA_ID);
//		return googleBaseDataIdField;
//	}
}
