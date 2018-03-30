package com.ztjs.smartSite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ztjs.smartSite.common.HttpClientSSLUtils;
import com.ztjs.smartSite.common.Utils;
import com.ztjs.smartSite.service.UtilsService;

@Controller
@RequestMapping("/video")
public class VideoController {
	/**
	 * APPKEY需要到部署的平台服务器上生成。
	 * <p>
	 * 调用Openpai的操作码，需要去平台上生成，详见《海康威视iVMS-8700平台SDKV2.*.*
	 * HTTP-OpenAPI使用说明书.chm》中[获取AppKey和Secret]章节说明
	 * </p>
	 * <p>
	 * 《海康威视iVMS-8700平台SDKV2.*.* HTTP-OpenAPI使用说明书.chm》 该文档请找技术支持或者交付的同事提供
	 * </p>
	 */
	private static final String APPKEY = "4811bcb5";

	/**
	 * SECRET需要到部署的平台服务器上生成。
	 * <p>
	 * 调用Openpai的操作码，需要去平台上生成，详见《海康威视iVMS-8700平台SDKV2.*.*
	 * HTTP-OpenAPI使用说明书.chm》中[获取AppKey和Secret]章节说明
	 * </p>
	 * <p>
	 * 《海康威视iVMS-8700平台SDKV2.*.* HTTP-OpenAPI使用说明书.chm》 该文档请找技术支持或者交付的同事提供
	 * </p>
	 */
	private static final String SECRET = "b51cc240ccd3465088773445f9dfaa87";

	/**
	 * http请求地址
	 * <p>
	 * openapi的地址,默认情况下openapi的IP端口与基础应用的IP端口是一致的
	 * </p>
	 * <p>
	 * 请将地址配置正确.
	 * </p>
	 * <p>
	 * 默认情况下是127.0.0.1:80 ，如果地址不通请根据实际情况修改IP端口
	 * </p>
	 */
	private static final String OPENAPI_IP_PORT_HTTP = "http://123.161.209.143:81";

	/**
	 * https请求地址
	 * <p>
	 * openapi的地址,默认情况下openapi的IP端口与基础应用的IP端口是一致的
	 * </p>
	 * <p>
	 * 请将地址配置正确.
	 * </p>
	 * <p>
	 * 默认情况下是127.0.0.1:443 ，如果地址不通请根据实际情况修改IP端口
	 * </p>
	 */
	private static final String OPENAPI_IP_PORT_HTTPS = "https://127.0.0.1:443";

	/**
	 * 获取默认用户UUID的接口地址，此地址可以从《海康威视iVMS-8700平台SDKV2.*.*
	 * HTTP-OpenAPI使用说明书.chm》中具体的接口说明上获取
	 */
	private static final String ITF_ADDRESS_GET_DEFAULT_USER_UUID = "/openapi/service/base/user/getDefaultUserUuid";

	/**
	 * 分页获取监控点信息的接口地址，此地址可以从《海康威视iVMS-8700平台SDKV2.*.*
	 * HTTP-OpenAPI使用说明书.chm》中具体的接口说明上获取
	 */
	private static final String ITF_ADDRESS_Get_PreviewParamByCameraUuid = "/openapi/service/vss/preview/getPreviewParamByCameraUuid";
	private static final String ITF_ADDRESS_GET_NetZones = "/openapi/service/base/netZone/getNetZones";
	private static final String ITF_ADDRESS_GET_CAMERAS = "/openapi/service/vss/res/getCamerasEx";
	/**
	 * 获取默认子系统
	 */
	private static final String ITF_ADDRESS_GET_GetPlatSubsytem = "/openapi/service/base/res/getPlatSubsytem";
	/**
	 * 获取默认控制中心
	 */
	private static final String ITF_ADDRESS_GET_GetDefaultUnit = "/openapi/service/base/org/getDefaultUnit";
	/**
	 * 获取控制中心
	 */
	private static final String ITF_ADDRESS_GET_GetUnitsByParentUuid = "/openapi/service/base/org/getUnitsByParentUuid";

	/**
	 * 根据中心获取下级区域
	 */
	private static final String ITF_ADDRESS_GET_GetRegionsByUnitUuid = "/openapi/service/base/org/getRegionsByUnitUuid";

	/**
	 * 根据区域获取下级区域
	 */
	
	private static final String ITF_ADDRESS_GET_GetRegionsByParentUuid = "/openapi/service/base/org/getRegionsByParentUuid";
/**
 * 获取默认USER
 */
	private static final String ITF_ADDRESS_GET_GetDefaultUserUuid = "/openapi/service/base/user/getDefaultUserUuid";
	/**
	 * <p>
	 * 操作用户UUID，即用户UUID，首次使用操作用户UUID可以通过接口 [获取默认用户UUID]来获取
	 * </p>
	 * <p>
	 * 也可以通过接口[分页获取用户]来获取
	 * </p>
	 */
	private static final String OP_USER_UUID = "cc78be40ec8611e78168af26905e6f0f";
	/**
	 * 获取默认控制中心
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@Autowired
	private Utils utils;
	@RequestMapping(value="/getDefaultUnit",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getDefaultUnit(HttpServletRequest request) throws Exception{
		String url = OPENAPI_IP_PORT_HTTP + ITF_ADDRESS_GET_GetDefaultUnit;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", APPKEY);// 设置APPKEY
		map.put("time", System.currentTimeMillis());// 设置时间参数
		map.put("opUserUuid", OP_USER_UUID);// 设置操作用户UUID
		map.put("subSystemCode", "2097152");// 获取控制中心
		String params = JSON.toJSONString(map);
		String data = HttpClientSSLUtils.doPost(url + "?token=" + HttpClientSSLUtils.buildToken(url + "?" + params, null, SECRET),
				params);
		JSONObject jsonObject=new JSONObject();
		return jsonObject.parseObject(data);
		
	}
	/**
	 * 通过区域id获取下级区域
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getRegionsByParentUuid",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getRegionsByParentUuid(String parentUuid) throws Exception{
		String url = OPENAPI_IP_PORT_HTTP + ITF_ADDRESS_GET_GetRegionsByParentUuid;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", APPKEY);// 设置APPKEY
		map.put("time", System.currentTimeMillis());// 设置时间参数
		map.put("pageNo", 1);// 设置操作用户UUID
		map.put("pageSize", 100);// 设置操作用户UUID
		map.put("opUserUuid", OP_USER_UUID);// 设置操作用户UUID
		map.put("parentUuid", parentUuid);// 设置操作用户UUID
		map.put("allChildren", 0);// 设置操作用户UUID
		String params = JSON.toJSONString(map);
		String data = HttpClientSSLUtils.doPost(url + "?token=" + HttpClientSSLUtils.buildToken(url + "?" + params, null, SECRET),
				params);
		JSONObject jsonObject=JSONObject.parseObject(data);
		return jsonObject;
		
	}
	/**
	 * 根据中心id获取下级区域
	 * @param parentUuid
	 * @return"1048576"
	 * @throws Exception
	 */
	@RequestMapping(value="/getRegionsByUnitUuid",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getRegionsByUnitUuid(String parentUuid) throws Exception{
		String url = OPENAPI_IP_PORT_HTTP + ITF_ADDRESS_GET_GetRegionsByUnitUuid;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", APPKEY);// 设置APPKEY
		map.put("time", System.currentTimeMillis());// 设置时间参数
		map.put("pageNo", 1);// 设置操作用户UUID
		map.put("pageSize", 400);// 设置操作用户UUID
		map.put("opUserUuid", OP_USER_UUID);// 设置操作用户UUID
		map.put("parentUuid", parentUuid);// 设置操作用户UUID
		map.put("allChildren", 0);// 设置操作用户UUID
		String params = JSON.toJSONString(map);
		String data = HttpClientSSLUtils.doPost(url + "?token=" + HttpClientSSLUtils.buildToken(url + "?" + params, null, SECRET),
				params);
		//data="{'errorCode':0,'errorMessage':null,'data':{'total':3,'pageNo':1,'pageSize':400,'list':[{'regionUuid':'7dc4207dff5544218c2e911ba9f115ae','name':'河南','parentUuid':'1048576','parentNodeType':1,'isParent':1,'createTime':1521202543641,'updateTime':1521203247723,'remark':''},{'regionUuid':'7dc4207dff5544218c2e911ba9f115ae1','name':'河南1','parentUuid':'1048576','parentNodeType':1,'isParent':1,'createTime':1521202543641,'updateTime':1521203247723,'remark':''},{'regionUuid':'cf42ac45881047de8769598d3f69e7b2','name':'信阳榕基软件园项目','parentUuid':'7dc4207dff5544218c2e911ba9f115ae','parentNodeType':2,'isParent':0,'createTime':1521203253251,'updateTime':1521203253251,'remark':''},{'regionUuid':'cf42ac45881047de8769598d3f69e7b21','name':'信阳榕基软件园项1目','parentUuid':'7dc4207dff5544218c2e911ba9f115ae1','parentNodeType':2,'isParent':0,'createTime':1521203253251,'updateTime':1521203253251,'remark':''},{'regionUuid':'cf42ac45881047de8769598d3f69e7b21','name':'信阳榕基软件园项2目','parentUuid':'7dc4207dff5544218c2e911ba9f115ae1','parentNodeType':2,'isParent':0,'createTime':1521203253251,'updateTime':1521203253251,'remark':''},{'regionUuid':'4ca28f602e8f4763ba745353c177af84','name':'信阳领秀城福山园小区','parentUuid':'7dc4207dff5544218c2e911ba9f115ae','parentNodeType':2,'isParent':0,'createTime':1521341440293,'updateTime':1521341440293,'remark':''}]}}";
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=JSONObject.parseObject(data);
		return jsonObject;
	}
	/**
	 * 根据中心id获取下级中心
	 * @param parentUuid
	 * @return"1048576"
	 * @throws Exception
	 */
	@RequestMapping(value="/getUnitsByParentUuid",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getUnitsByParentUuid(String parentUuid) throws Exception{
		String url = OPENAPI_IP_PORT_HTTP + ITF_ADDRESS_GET_GetUnitsByParentUuid;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", APPKEY);// 设置APPKEY
		map.put("time", System.currentTimeMillis());// 设置时间参数
		map.put("pageNo", 1);// 设置操作用户UUID
		map.put("pageSize", 400);// 设置操作用户UUID
		map.put("opUserUuid", OP_USER_UUID);// 设置操作用户UUID
		map.put("parentUuid", parentUuid);// 设置操作用户UUID
		map.put("allChildren", 0);// 设置操作用户UUID
		String params = JSON.toJSONString(map);
		String data = HttpClientSSLUtils.doPost(url + "?token=" + HttpClientSSLUtils.buildToken(url + "?" + params, null, SECRET),
				params);
		JSONObject jsonObject=JSONObject.parseObject(data);
		return jsonObject;
	}
	/**
	 * 根据区域id获取监控点
	 * @param parentUuid
	 * @return"1048576"
	 * @throws Exception
	 */
	@RequestMapping(value="/getCamerasByRegionUuids",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getCamerasByRegionUuids(String regionUuid) throws Exception{
		String url = OPENAPI_IP_PORT_HTTP + ITF_ADDRESS_GET_CAMERAS;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", APPKEY);// 设置APPKEY
		map.put("time", System.currentTimeMillis());// 设置时间参数
		map.put("pageNo", 1);// 设置分页参数
		map.put("pageSize", 400);// 设置分页参数
		map.put("regionUuids", regionUuid);
		map.put("opUserUuid", OP_USER_UUID);// 设置操作用户UUID
		String params = JSON.toJSONString(map);
		String data = HttpClientSSLUtils.doPost(url + "?token=" + HttpClientSSLUtils.buildToken(url + "?" + params, null, SECRET),
				params);
		JSONObject jsonObject=JSONObject.parseObject(data);
		return jsonObject;
	}
	/**
	 * 获取默认用户
	 * @param parentUuid
	 * @return"1048576"
	 * @throws Exception
	 */
	@RequestMapping(value="/getDefaultUserUuid",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getDefaultUserUuid() throws Exception{
		String url = OPENAPI_IP_PORT_HTTP + ITF_ADDRESS_GET_GetDefaultUserUuid;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", APPKEY);// 设置APPKEY
		map.put("time", System.currentTimeMillis());// 设置时间参数
		String params = JSON.toJSONString(map);
		String data = HttpClientSSLUtils.doPost(url + "?token=" + HttpClientSSLUtils.buildToken(url + "?" + params, null, SECRET),
				params);
		
		JSONObject jsonObject=JSONObject.parseObject(data);
		return jsonObject;
	}
	/**
	 * 插叙预览信息
	 * @param parentUuid
	 * @return"1048576"
	 * @throws Exception
	 */
	@RequestMapping(value="/getPreviewParamByCameraUuid",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getPreviewParamByCameraUuid(String cameraUuid) throws Exception{
		String url = OPENAPI_IP_PORT_HTTP + ITF_ADDRESS_Get_PreviewParamByCameraUuid;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", APPKEY);// 设置APPKEY
		map.put("time", System.currentTimeMillis());// 设置时间参数
		map.put("opUserUuid", OP_USER_UUID);// 设置操作用户UUID
		map.put("cameraUuid", cameraUuid);// 设置操作用户UUID
		map.put("netZoneUuid", "57383fef406f4830b20a6164ab3715bd");// 设置操作用户UUID
		String params = JSON.toJSONString(map);
		String data = HttpClientSSLUtils.doPost(url + "?token=" + HttpClientSSLUtils.buildToken(url + "?" + params, null, SECRET),
				params);
		
		JSONObject jsonObject=JSONObject.parseObject(data);
		return jsonObject;
	}
}
