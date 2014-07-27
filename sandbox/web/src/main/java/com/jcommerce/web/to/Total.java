package com.jcommerce.web.to;

import java.math.BigDecimal;

import com.jcommerce.core.wrapper.BaseWrapper;
import com.jcommerce.web.util.WebFormatUtils;

public class Total extends BaseWrapper{

	private static final long serialVersionUID = -230085058068887407L;

	Double discount = 0.0;
//	String discountFormated = "0.0";
	
	Double tax = 0.0;
//	String taxFormated = "0.0";
	
	Double shippingFee = 0.0;
//	String shippingFeeFormated = "0.0";
	Double shippingInsure = 0.0;
//	String shippingInsureFormated = "0.0";
	
	Double payFee = 0.0;
//	String payFeeFormated = "0.0";
	
	Double packFee = 0.0;
//	String packFeeFormated = "0.0";
	Double cardFee = 0.0;
//	String cardFeeFormated = "0.0";
	Double surplus = 0.0;
//	String surplusFormated = "0.0";
	
	Integer integral = 0;
	String integralFormated = "0";
		
	Double bonus = 0.0;
//	String bonusFormated = "0.0";
	
	Double amount = 0.0;
//	String amountFormated= "0.0";
	
	Integer realGoodsCount = 0;
	Double goodsPrice = 0.0;
//	String goodsPriceFormated = "0.0";
	Double marketPrice = 0.0;
//	String marketPriceFormated = "0.0";
	Double saving = 0.0;
//	String savingFormated = "0.0";
	
	Double saveRate = 0.0;
	
	String willGetBonus = "0.0";
	String formatedSaving = "0.0";
	
	
	public Total() {
		
	}


	public Double getDiscount() {
		return discount;
	}


	public void setDiscount(Double discount) {
		this.discount = discount;
	}


	public Double getTax() {
		return tax;
	}


	public void setTax(Double tax) {
		this.tax = tax;
	}


	public Double getShippingFee() {
		return shippingFee;
	}


	public void setShippingFee(Double shippingFee) {
		this.shippingFee = shippingFee;
	}


	public Double getShippingInsure() {
		return shippingInsure;
	}


	public void setShippingInsure(Double shippingInsure) {
		this.shippingInsure = shippingInsure;
	}


	public Double getPayFee() {
		return payFee;
	}


	public void setPayFee(Double payFee) {
		this.payFee = payFee;
	}


	public Double getPackFee() {
		return packFee;
	}


	public void setPackFee(Double packFee) {
		this.packFee = packFee;
	}


	public Double getCardFee() {
		return cardFee;
	}


	public void setCardFee(Double cardFee) {
		this.cardFee = cardFee;
	}


	public Double getSurplus() {
		return surplus;
	}


	public void setSurplus(Double surplus) {
		this.surplus = surplus;
	}





	public Double getBonus() {
		return bonus;
	}


	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}


	public String getAmountFormated() {
		return WebFormatUtils.priceFormat(amount);
	}





	public Integer getRealGoodsCount() {
		return realGoodsCount;
	}


	public void setRealGoodsCount(Integer realGoodsCount) {
		this.realGoodsCount = realGoodsCount;
	}


	public String getDiscountFormated() {
		return WebFormatUtils.priceFormat(discount);
	}

	public String getTaxFormated() {
		return WebFormatUtils.priceFormat(tax);
	}

	public String getShippingFeeFormated() {
		return WebFormatUtils.priceFormat(shippingFee);
	}

	public String getShippingInsureFormated() {
		return WebFormatUtils.priceFormat(shippingInsure);
	}

	public String getPayFeeFormated() {
		return WebFormatUtils.priceFormat(payFee);
	}

	public String getPackFeeFormated() {
		return WebFormatUtils.priceFormat(packFee);
	}

	public String getCardFeeFormated() {
		return WebFormatUtils.priceFormat(cardFee);
	}
	
	public String getSurplusFormated() {
		return WebFormatUtils.priceFormat(surplus);
	}

	public String getIntegralFormated() {
		return integralFormated;
	}
	
	public void setIntegralFormated(String integralFormated) {
		this.integralFormated = integralFormated;
	}
	
	public String getBonusFormated() {
		return WebFormatUtils.priceFormat(bonus);
	}

	public Double getAmount() {
		return amount;
	}


	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public Double getGoodsPrice() {
		return goodsPrice;
	}


	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}


	public String getGoodsPriceFormated() {
		return WebFormatUtils.priceFormat(goodsPrice);
	}

	public Double getMarketPrice() {
		return marketPrice;
	}


	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}


	public String getMarketPriceFormated() {
		return WebFormatUtils.priceFormat(marketPrice);
	}




	public Double getSaving() {
		return saving;
	}


	public void setSaving(Double saving) {
		this.saving = saving;
	}


	public String getSavingFormated() {
		return WebFormatUtils.priceFormat(new BigDecimal(saving).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
	}



	public Double getSaveRate() {
		return saveRate;
	}


	public void setSaveRate(Double saveRate) {
		this.saveRate = saveRate;
	}

	public String getSaveRateFormated() {
		return saveRate+"%";
	}
	
	
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}


	public String getWillGetBonus() {
		return willGetBonus;
	}


	public void setWillGetBonus(String willGetBonus) {
		this.willGetBonus = willGetBonus;
	}

	public String getWillGetIntegral(){
		return this.integral+"";
	}



	public String getFormatedSaving() {
		return formatedSaving;
	}


	public void setFormatedSaving(String formatedSaving) {
		this.formatedSaving = formatedSaving;
	}


	public Integer getIntegral() {
		return integral;
	}
	
}
