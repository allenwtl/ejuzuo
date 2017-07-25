package com.ejuzuo.server.news.manager;


import com.ejuzuo.BaseTest;
import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.domain.CodeValue;
import com.ejuzuo.common.domain.DigitalFurniture;
import com.ejuzuo.server.codevalue.manager.CodeValueManager;
import com.ejuzuo.server.digitalFurniture.manager.DigitalFurnitureManager;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

public class NewsInfoManagerTest extends BaseTest {

	@Resource
	private NewsInfoManager newsInfoManager;

	@Resource
	private CodeValueManager codeValueManager;

	@Resource
	private DigitalFurnitureManager digitalFurnitureManager;
	
	@Test
	public void testInsert() {

		Integer [] typeArray = new Integer[]{1,2,3,4};

		String [] codeA = new String[]{"10014"};

		for (int j=0;j<codeA.length;j++){

			String code = codeA[j];

			List<CodeValue> codeValueList = codeValueManager.queryList(CollectionCode.kongjianxiaolei, null, code).getModel();

			for (int q =0; q< codeValueList.size() ;q++){
				CodeValue codeValue = codeValueList.get(q);
				for (int i =0; i < typeArray.length;i++){
					List<DigitalFurniture> digitalFurnitureList =  digitalFurnitureManager.queryList(typeArray[i]);

					for (int k=0; k< digitalFurnitureList.size();k++){
						if(k<=2){
							DigitalFurniture digitalFurniture = digitalFurnitureList.get(k);
							digitalFurniture.setSpaceCategory(code);
							digitalFurniture.setSpaceSmallCategory(codeValue.getValueCode());
							digitalFurniture.setId(null);
							digitalFurnitureManager.insert(digitalFurniture);
						}
					}
				}
			}

		}




	}

	@Test
	public void testQueryById() {
//		newsInfoManager.queryById(id)
	}

	@Test
	public void testUpdate() {

	}

}
