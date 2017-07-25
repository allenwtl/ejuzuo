package com.ejuzuo.web.controller.buycar;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.webutil.common.CookieUtils;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.domain.ShoppingCart;
import com.ejuzuo.common.service.ShoppingCarService;
import com.ejuzuo.common.util.UUIDUtils;
import com.ejuzuo.common.vo.ShoppingCartVO;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.common.GlobalParam;
import com.ejuzuo.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/15 0015.
 */

@Controller
@RequestMapping("buyCar")
public class BuyCarController extends BaseController {

    @Resource
    private ShoppingCarService shoppingCarService;


    @Login
    @RequestMapping("index")
    public String index(Model model, HttpServletRequest request, DataPage<ShoppingCartVO> dataPage) {
        model.addAttribute("menu", "buyCar");
        if (dataPage == null) {
            dataPage = new DataPage<>(5);
        }
        dataPage.setNeedData(false);
        ModelResult<DataPage<ShoppingCartVO>> listModelResult = shoppingCarService.queryPage(getMemberId(request), dataPage);


        model.addAttribute("data", listModelResult.getModel());

        return "/buycar/carList";
    }


    @Login
    @RequestMapping("listCar")
    @ResponseBody
    public JSONObject queryList(DataPage<ShoppingCartVO> dataPage, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        ModelResult<DataPage<ShoppingCartVO>> listModelResult = shoppingCarService.queryPage(getMemberId(request), dataPage);
        dataPage = listModelResult.getModel();
        jsonObject.put("data", dataPage.getDataList());
        jsonObject.put("totalCount", dataPage.getTotalCount());
        jsonObject.put("totalPage", dataPage.getTotalPages());
        BigDecimal price = new BigDecimal(0);
        int curPageCount = 0;
        for (ShoppingCartVO vo : dataPage.getDataList()) {
            curPageCount = curPageCount + 1;
            price = price.add(BigDecimal.valueOf(vo.getDigitalFurniture().getPointPrice()));
        }
        jsonObject.put("price", price);
        jsonObject.put("curPageCount", curPageCount);
        return jsonObject;
    }

    /**
     * 加入购物车  如果购物车没有 就生产一个
     * id 数字家居id
     */
    @RequestMapping("/addCar/{id}")
    @ResponseBody
    public JSONObject addCar(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();

        String value = CookieUtils.getCookieValue(request, GlobalParam.buyCar);

        if (StringUtils.isBlank(value)) {
            value = UUIDUtils.getUuidStr();
            CookieUtils.setCookie(request, response, GlobalParam.buyCar, value, -1);
        }

        List<Integer> goodsCar = memcachedClient.get(value);

        if (goodsCar == null) {
            goodsCar = new ArrayList<>();
        }

        goodsCar.add(id);
        try {
            memcachedClient.set(value, 10 * 60, goodsCar);
            if (getMember(request) != null) {
                ShoppingCart shoppingCart = new ShoppingCart();
                shoppingCart.setGoodsId(id);
                shoppingCart.setMemberId(getMemberId(request));
                ModelResult<ShoppingCart> modelResult = shoppingCarService.save(shoppingCart);
                if (!modelResult.isSuccess()){
                    jsonObject.put("code", 444);
                    jsonObject.put("msg", modelResult.getErrorMsg());
                    return jsonObject;
                }
            }
        } catch (Exception e) {
            logger.error("加入购物车失败:{}", e);
            jsonObject.put("code", 444);
            jsonObject.put("msg", "系统错误，请联系客服！");
            return jsonObject;
        }
        jsonObject.put("code", 222);
        //jsonObject.put("msg", "您好！您已把素材加入购物车，请尽快用积分结算哦~");
        jsonObject.put("msg", "您好！您已把素材加入购物车!");
        return jsonObject;
    }

    /**
     * 从缓存中购物车清单中移除一个
     *
     * @param id      数字家居
     * @param request
     * @return
     */
    @Login
    @RequestMapping("/remove/{id}")
    @ResponseBody
    public JSONObject remove(@PathVariable Integer id, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(id);
        shoppingCart.setMemberId(getMemberId(request));
        ModelResult<Boolean> result = shoppingCarService.remove(shoppingCart);

        if (result.isSuccess()) {
            jsonObject.put("code", 222);
            jsonObject.put("msg", "移除成功");
            return jsonObject;
        }

        jsonObject.put("code", 444);
        jsonObject.put("msg", "移除失败");
        return jsonObject;
    }


    @Login
    @RequestMapping("/settle")
    @ResponseBody
    public JSONObject settle(String ids, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isBlank(ids)) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "未选择商品结算");
            return jsonObject;
        }

        List<Integer> idList = new ArrayList<>();
        for (String str : ids.split(",")) {
            idList.add(Integer.parseInt(str));
        }

        ModelResult<Boolean> modelResult = shoppingCarService.settle(idList, getMemberId(request));
        if (modelResult.isSuccess()) {
            jsonObject.put("code", 222);
            jsonObject.put("msg", "您好！您已结算素材成功，系统将会自动为您保存5天，请尽快下载哦！");
            return jsonObject;
        }

        jsonObject.put("code", 444);
        jsonObject.put("msg", modelResult.getErrorMsg());
        return jsonObject;
    }


    @Login
    @RequestMapping("/removeList")
    @ResponseBody
    public JSONObject removeList(String ids, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        if (StringUtils.isBlank(ids)) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "参数输入不正确");
            return jsonObject;
        }

        String[] id = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String str : id) {
            idList.add(Integer.parseInt(str));
        }

        ModelResult<Boolean> result = shoppingCarService.batchRemove(idList, getMemberId(request));

        if (result.isSuccess()) {
            jsonObject.put("code", 222);
            jsonObject.put("msg", "移除成功");
            return jsonObject;
        }

        jsonObject.put("code", 444);
        jsonObject.put("msg", "移除失败");
        return jsonObject;
    }
}
