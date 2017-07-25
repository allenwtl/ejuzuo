package com.ejuzuo.admin.webBindType;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import com.ejuzuo.common.domain.type.CheckCodeRecordCheckType;
import com.ejuzuo.common.domain.type.CheckCodeRecordDestType;
import com.ejuzuo.common.domain.type.CheckCodeRecordStatus;
import com.ejuzuo.common.domain.type.SmsRecordMobileType;
import com.ejuzuo.common.domain.type.SmsRecordSendStatus;

public class CustomerWebBindingInitializer extends ConfigurableWebBindingInitializer {

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		super.initBinder(binder, request);
		binder.registerCustomEditor(SmsRecordSendStatus.class, new BaseTypeEditor(SmsRecordSendStatus.class));
		binder.registerCustomEditor(SmsRecordMobileType.class, new BaseTypeEditor(SmsRecordMobileType.class));
		binder.registerCustomEditor(CheckCodeRecordCheckType.class, new BaseTypeEditor(CheckCodeRecordCheckType.class));
		binder.registerCustomEditor(CheckCodeRecordDestType.class, new BaseTypeEditor(CheckCodeRecordDestType.class));
		binder.registerCustomEditor(CheckCodeRecordStatus.class, new BaseTypeEditor(CheckCodeRecordStatus.class));
//		binder.registerCustomEditor(Calendar.class, new CalendarEditor());
//		binder.registerCustomEditor(JSONObject.class, new JsonEditor());
//
//		binder.registerCustomEditor(AdminOperType.class, new BaseTypeEditor(AdminOperType.class));
//		binder.registerCustomEditor(OperType.class, new BaseTypeEditor(OperType.class));
//
//		binder.registerCustomEditor(ActionPlaceType.class, new BaseTypeEditor(ActionPlaceType.class));
//		binder.registerCustomEditor(PageCodeType.class, new BaseTypeEditor(PageCodeType.class));
//		binder.registerCustomEditor(CashOutStatus.class, new BaseTypeEditor(CashOutStatus.class));
//		binder.registerCustomEditor(PeiziPlanStatus.class, new BaseTypeEditor(PeiziPlanStatus.class));
//		binder.registerCustomEditor(ApplyType.class, new BaseTypeEditor(ApplyType.class));
//		binder.registerCustomEditor(ApplyStatus.class, new BaseTypeEditor(ApplyStatus.class));
//		binder.registerCustomEditor(PeriodType.class, new BaseTypeEditor(PeriodType.class));
//		binder.registerCustomEditor(PlanFundChangeType.class, new BaseTypeEditor(PlanFundChangeType.class));
//		binder.registerCustomEditor(PlanFundStatus.class, new BaseTypeEditor(PlanFundStatus.class));
//		binder.registerCustomEditor(ResolutionType.class, new BaseTypeEditor(ResolutionType.class));
//		binder.registerCustomEditor(MemberThirdType.class, new BaseTypeEditor(MemberThirdType.class));
//
//		binder.registerCustomEditor(ReasonType.class, new BaseTypeEditor(ReasonType.class));
//		binder.registerCustomEditor(StockBackListStatus.class, new BaseTypeEditor(StockBackListStatus.class));
//		
//		
//		binder.registerCustomEditor(SubjectType.class, new BaseTypeEditor(SubjectType.class));
//		binder.registerCustomEditor(SubjectStatus.class, new BaseTypeEditor(SubjectStatus.class));
//		binder.registerCustomEditor(GuessPlanStatus.class, new BaseTypeEditor(GuessPlanStatus.class));
//		binder.registerCustomEditor(PrizeStatus.class, new BaseTypeEditor(PrizeStatus.class));
//		binder.registerCustomEditor(GuessItemCode.class, new BaseTypeEditor(GuessItemCode.class));
//		
//		binder.registerCustomEditor(PointTransType.class, new BaseTypeEditor(PointTransType.class));
		
	}

}
