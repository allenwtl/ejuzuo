package com.ejuzuo.web.common;

import java.math.BigDecimal;
import java.util.Map;

public class PayHelpBean {
    /**
     * 交易流水号
     */
    private String chargerNo;
    /**
     * 充值金额
     */
    private BigDecimal amount;
    /**
     * 成功充值金额
     */
    private BigDecimal successAmount;
    /**
     * 充值成功时返回给合作方的字符
     */
    private String successCallback;
    /**
     * 充值失败时返回给合作方的字符
     */
    private String failCallback;
    /**
     * 充值是否成功
     */
    private boolean paySuccess = false;
    
    /**
     * 第三方支付订单号
     */
    private String thirdPayNo;
    
    private String errorMsg;
    
    
    private Boolean s2s;//服务器是否为服务返回
    

    
    private Map<String, String> featuresMap ;
    
    private String bankCode;	//连连支付充值返回时带有银行号	
    
//    private String bankCard;	//银行卡卡号
    
    private Integer payType;	//支付方式（2:借记卡，3:信用卡）
    
    private String acctName;	//用户银行卡账户名
    
    private String certNo;	//证件号码
    
    /**
     * 充值辅助类构造函数
     * @param chargerNo 内部充值流水号
     * @param amount 充值金额
     * @param successAmount 充值成功金额
     * @param successCallback 充值成功返回字符
     * @param failCallback 充值失败返回字符
     * @param paySuccess 是否充值成功
     * @param thirdPayNo 第三方充值订单号
     */
    public PayHelpBean(String chargerNo, BigDecimal amount, BigDecimal successAmount, String successCallback,
                       String failCallback, boolean paySuccess, String thirdPayNo) {
        this.chargerNo = chargerNo;
        this.amount = amount;
        this.successAmount = successAmount;
        this.successCallback = successCallback;
        this.failCallback = failCallback;
        this.paySuccess = paySuccess;
        this.thirdPayNo=thirdPayNo;
    }

    public String getChargerNo() {
        return chargerNo;
    }

    public void setChargerNo(String chargerNo) {
        this.chargerNo = chargerNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(BigDecimal successAmount) {
        this.successAmount = successAmount;
    }

    public String getSuccessCallback() {
        return successCallback;
    }

    public void setSuccessCallback(String successCallback) {
        this.successCallback = successCallback;
    }

    public String getFailCallback() {
        return failCallback;
    }

    public void setFailCallback(String failCallback) {
        this.failCallback = failCallback;
    }

    public boolean isPaySuccess() {
        return paySuccess;
    }

    public void setPaySuccess(boolean paySuccess) {
        this.paySuccess = paySuccess;
    }

    public String getThirdPayNo() {
        return thirdPayNo;
    }

    public void setThirdPayNo(String thirdPayNo) {
        this.thirdPayNo = thirdPayNo;
    }

	public Boolean isS2s() {
		return s2s;
	}

	public void setS2s(Boolean s2s) {
		this.s2s = s2s;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Map<String, String> getFeaturesMap() {
		return featuresMap;
	}

	public void setFeaturesMap(Map<String, String> featuresMap) {
		this.featuresMap = featuresMap;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	
	
}
