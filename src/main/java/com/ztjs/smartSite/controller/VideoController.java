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
	 * APPKEY��Ҫ�������ƽ̨�����������ɡ�
	 * <p>
	 * ����Openpai�Ĳ����룬��Ҫȥƽ̨�����ɣ��������������iVMS-8700ƽ̨SDKV2.*.*
	 * HTTP-OpenAPIʹ��˵����.chm����[��ȡAppKey��Secret]�½�˵��
	 * </p>
	 * <p>
	 * ����������iVMS-8700ƽ̨SDKV2.*.* HTTP-OpenAPIʹ��˵����.chm�� ���ĵ����Ҽ���֧�ֻ��߽�����ͬ���ṩ
	 * </p>
	 */
	private static final String APPKEY = "4811bcb5";

	/**
	 * SECRET��Ҫ�������ƽ̨�����������ɡ�
	 * <p>
	 * ����Openpai�Ĳ����룬��Ҫȥƽ̨�����ɣ��������������iVMS-8700ƽ̨SDKV2.*.*
	 * HTTP-OpenAPIʹ��˵����.chm����[��ȡAppKey��Secret]�½�˵��
	 * </p>
	 * <p>
	 * ����������iVMS-8700ƽ̨SDKV2.*.* HTTP-OpenAPIʹ��˵����.chm�� ���ĵ����Ҽ���֧�ֻ��߽�����ͬ���ṩ
	 * </p>
	 */
	private static final String SECRET = "b51cc240ccd3465088773445f9dfaa87";

	/**
	 * http�����ַ
	 * <p>
	 * openapi�ĵ�ַ,Ĭ�������openapi��IP�˿������Ӧ�õ�IP�˿���һ�µ�
	 * </p>
	 * <p>
	 * �뽫��ַ������ȷ.
	 * </p>
	 * <p>
	 * Ĭ���������127.0.0.1:80 �������ַ��ͨ�����ʵ������޸�IP�˿�
	 * </p>
	 */
	private static final String OPENAPI_IP_PORT_HTTP = "http://123.161.209.143:81";

	/**
	 * https�����ַ
	 * <p>
	 * openapi�ĵ�ַ,Ĭ�������openapi��IP�˿������Ӧ�õ�IP�˿���һ�µ�
	 * </p>
	 * <p>
	 * �뽫��ַ������ȷ.
	 * </p>
	 * <p>
	 * Ĭ���������127.0.0.1:443 �������ַ��ͨ�����ʵ������޸�IP�˿�
	 * </p>
	 */
	private static final String OPENAPI_IP_PORT_HTTPS = "https://127.0.0.1:443";

	/**
	 * ��ȡĬ���û�UUID�Ľӿڵ�ַ���˵�ַ���Դӡ���������iVMS-8700ƽ̨SDKV2.*.*
	 * HTTP-OpenAPIʹ��˵����.chm���о���Ľӿ�˵���ϻ�ȡ
	 */
	private static final String ITF_ADDRESS_GET_DEFAULT_USER_UUID = "/openapi/service/base/user/getDefaultUserUuid";

	/**
	 * ��ҳ��ȡ��ص���Ϣ�Ľӿڵ�ַ���˵�ַ���Դӡ���������iVMS-8700ƽ̨SDKV2.*.*
	 * HTTP-OpenAPIʹ��˵����.chm���о���Ľӿ�˵���ϻ�ȡ
	 */
	private static final String ITF_ADDRESS_Get_PreviewParamByCameraUuid = "/openapi/service/vss/preview/getPreviewParamByCameraUuid";
	private static final String ITF_ADDRESS_GET_NetZones = "/openapi/service/base/netZone/getNetZones";
	private static final String ITF_ADDRESS_GET_CAMERAS = "/openapi/service/vss/res/getCamerasEx";
	/**
	 * ��ȡĬ����ϵͳ
	 */
	private static final String ITF_ADDRESS_GET_GetPlatSubsytem = "/openapi/service/base/res/getPlatSubsytem";
	/**
	 * ��ȡĬ�Ͽ�������
	 */
	private static final String ITF_ADDRESS_GET_GetDefaultUnit = "/openapi/service/base/org/getDefaultUnit";
	/**
	 * ��ȡ��������
	 */
	private static final String ITF_ADDRESS_GET_GetUnitsByParentUuid = "/openapi/service/base/org/getUnitsByParentUuid";

	/**
	 * �������Ļ�ȡ�¼�����
	 */
	private static final String ITF_ADDRESS_GET_GetRegionsByUnitUuid = "/openapi/service/base/org/getRegionsByUnitUuid";

	/**
	 * ���������ȡ�¼�����
	 */
	
	private static final String ITF_ADDRESS_GET_GetRegionsByParentUuid = "/openapi/service/base/org/getRegionsByParentUuid";
/**
 * ��ȡĬ��USER
 */
	private static final String ITF_ADDRESS_GET_GetDefaultUserUuid = "/openapi/service/base/user/getDefaultUserUuid";
	/**
	 * <p>
	 * �����û�UUID�����û�UUID���״�ʹ�ò����û�UUID����ͨ���ӿ� [��ȡĬ���û�UUID]����ȡ
	 * </p>
	 * <p>
	 * Ҳ����ͨ���ӿ�[��ҳ��ȡ�û�]����ȡ
	 * </p>
	 */
	private static final String OP_USER_UUID = "cc78be40ec8611e78168af26905e6f0f";
	/**
	 * ��ȡĬ�Ͽ�������
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
		map.put("appkey", APPKEY);// ����APPKEY
		map.put("time", System.currentTimeMillis());// ����ʱ�����
		map.put("opUserUuid", OP_USER_UUID);// ���ò����û�UUID
		map.put("subSystemCode", "2097152");// ��ȡ��������
		String params = JSON.toJSONString(map);
		String data = HttpClientSSLUtils.doPost(url + "?token=" + HttpClientSSLUtils.buildToken(url + "?" + params, null, SECRET),
				params);
		JSONObject jsonObject=new JSONObject();
		return jsonObject.parseObject(data);
		
	}
	/**
	 * ͨ������id��ȡ�¼�����
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getRegionsByParentUuid",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getRegionsByParentUuid(String parentUuid) throws Exception{
		String url = OPENAPI_IP_PORT_HTTP + ITF_ADDRESS_GET_GetRegionsByParentUuid;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", APPKEY);// ����APPKEY
		map.put("time", System.currentTimeMillis());// ����ʱ�����
		map.put("pageNo", 1);// ���ò����û�UUID
		map.put("pageSize", 100);// ���ò����û�UUID
		map.put("opUserUuid", OP_USER_UUID);// ���ò����û�UUID
		map.put("parentUuid", parentUuid);// ���ò����û�UUID
		map.put("allChildren", 0);// ���ò����û�UUID
		String params = JSON.toJSONString(map);
		String data = HttpClientSSLUtils.doPost(url + "?token=" + HttpClientSSLUtils.buildToken(url + "?" + params, null, SECRET),
				params);
		JSONObject jsonObject=JSONObject.parseObject(data);
		return jsonObject;
		
	}
	/**
	 * ��������id��ȡ�¼�����
	 * @param parentUuid
	 * @return"1048576"
	 * @throws Exception
	 */
	@RequestMapping(value="/getRegionsByUnitUuid",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getRegionsByUnitUuid(String parentUuid) throws Exception{
		String url = OPENAPI_IP_PORT_HTTP + ITF_ADDRESS_GET_GetRegionsByUnitUuid;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", APPKEY);// ����APPKEY
		map.put("time", System.currentTimeMillis());// ����ʱ�����
		map.put("pageNo", 1);// ���ò����û�UUID
		map.put("pageSize", 400);// ���ò����û�UUID
		map.put("opUserUuid", OP_USER_UUID);// ���ò����û�UUID
		map.put("parentUuid", parentUuid);// ���ò����û�UUID
		map.put("allChildren", 0);// ���ò����û�UUID
		String params = JSON.toJSONString(map);
		String data = HttpClientSSLUtils.doPost(url + "?token=" + HttpClientSSLUtils.buildToken(url + "?" + params, null, SECRET),
				params);
		//data="{'errorCode':0,'errorMessage':null,'data':{'total':3,'pageNo':1,'pageSize':400,'list':[{'regionUuid':'7dc4207dff5544218c2e911ba9f115ae','name':'����','parentUuid':'1048576','parentNodeType':1,'isParent':1,'createTime':1521202543641,'updateTime':1521203247723,'remark':''},{'regionUuid':'7dc4207dff5544218c2e911ba9f115ae1','name':'����1','parentUuid':'1048576','parentNodeType':1,'isParent':1,'createTime':1521202543641,'updateTime':1521203247723,'remark':''},{'regionUuid':'cf42ac45881047de8769598d3f69e7b2','name':'�����Ż����԰��Ŀ','parentUuid':'7dc4207dff5544218c2e911ba9f115ae','parentNodeType':2,'isParent':0,'createTime':1521203253251,'updateTime':1521203253251,'remark':''},{'regionUuid':'cf42ac45881047de8769598d3f69e7b21','name':'�����Ż����԰��1Ŀ','parentUuid':'7dc4207dff5544218c2e911ba9f115ae1','parentNodeType':2,'isParent':0,'createTime':1521203253251,'updateTime':1521203253251,'remark':''},{'regionUuid':'cf42ac45881047de8769598d3f69e7b21','name':'�����Ż����԰��2Ŀ','parentUuid':'7dc4207dff5544218c2e911ba9f115ae1','parentNodeType':2,'isParent':0,'createTime':1521203253251,'updateTime':1521203253251,'remark':''},{'regionUuid':'4ca28f602e8f4763ba745353c177af84','name':'��������Ǹ�ɽ԰С��','parentUuid':'7dc4207dff5544218c2e911ba9f115ae','parentNodeType':2,'isParent':0,'createTime':1521341440293,'updateTime':1521341440293,'remark':''}]}}";
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=JSONObject.parseObject(data);
		return jsonObject;
	}
	/**
	 * ��������id��ȡ�¼�����
	 * @param parentUuid
	 * @return"1048576"
	 * @throws Exception
	 */
	@RequestMapping(value="/getUnitsByParentUuid",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getUnitsByParentUuid(String parentUuid) throws Exception{
		String url = OPENAPI_IP_PORT_HTTP + ITF_ADDRESS_GET_GetUnitsByParentUuid;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", APPKEY);// ����APPKEY
		map.put("time", System.currentTimeMillis());// ����ʱ�����
		map.put("pageNo", 1);// ���ò����û�UUID
		map.put("pageSize", 400);// ���ò����û�UUID
		map.put("opUserUuid", OP_USER_UUID);// ���ò����û�UUID
		map.put("parentUuid", parentUuid);// ���ò����û�UUID
		map.put("allChildren", 0);// ���ò����û�UUID
		String params = JSON.toJSONString(map);
		String data = HttpClientSSLUtils.doPost(url + "?token=" + HttpClientSSLUtils.buildToken(url + "?" + params, null, SECRET),
				params);
		JSONObject jsonObject=JSONObject.parseObject(data);
		return jsonObject;
	}
	/**
	 * ��������id��ȡ��ص�
	 * @param parentUuid
	 * @return"1048576"
	 * @throws Exception
	 */
	@RequestMapping(value="/getCamerasByRegionUuids",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getCamerasByRegionUuids(String regionUuid) throws Exception{
		String url = OPENAPI_IP_PORT_HTTP + ITF_ADDRESS_GET_CAMERAS;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", APPKEY);// ����APPKEY
		map.put("time", System.currentTimeMillis());// ����ʱ�����
		map.put("pageNo", 1);// ���÷�ҳ����
		map.put("pageSize", 400);// ���÷�ҳ����
		map.put("regionUuids", regionUuid);
		map.put("opUserUuid", OP_USER_UUID);// ���ò����û�UUID
		String params = JSON.toJSONString(map);
		String data = HttpClientSSLUtils.doPost(url + "?token=" + HttpClientSSLUtils.buildToken(url + "?" + params, null, SECRET),
				params);
		JSONObject jsonObject=JSONObject.parseObject(data);
		return jsonObject;
	}
	/**
	 * ��ȡĬ���û�
	 * @param parentUuid
	 * @return"1048576"
	 * @throws Exception
	 */
	@RequestMapping(value="/getDefaultUserUuid",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getDefaultUserUuid() throws Exception{
		String url = OPENAPI_IP_PORT_HTTP + ITF_ADDRESS_GET_GetDefaultUserUuid;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", APPKEY);// ����APPKEY
		map.put("time", System.currentTimeMillis());// ����ʱ�����
		String params = JSON.toJSONString(map);
		String data = HttpClientSSLUtils.doPost(url + "?token=" + HttpClientSSLUtils.buildToken(url + "?" + params, null, SECRET),
				params);
		
		JSONObject jsonObject=JSONObject.parseObject(data);
		return jsonObject;
	}
	/**
	 * ����Ԥ����Ϣ
	 * @param parentUuid
	 * @return"1048576"
	 * @throws Exception
	 */
	@RequestMapping(value="/getPreviewParamByCameraUuid",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getPreviewParamByCameraUuid(String cameraUuid) throws Exception{
		String url = OPENAPI_IP_PORT_HTTP + ITF_ADDRESS_Get_PreviewParamByCameraUuid;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", APPKEY);// ����APPKEY
		map.put("time", System.currentTimeMillis());// ����ʱ�����
		map.put("opUserUuid", OP_USER_UUID);// ���ò����û�UUID
		map.put("cameraUuid", cameraUuid);// ���ò����û�UUID
		map.put("netZoneUuid", "57383fef406f4830b20a6164ab3715bd");// ���ò����û�UUID
		String params = JSON.toJSONString(map);
		String data = HttpClientSSLUtils.doPost(url + "?token=" + HttpClientSSLUtils.buildToken(url + "?" + params, null, SECRET),
				params);
		
		JSONObject jsonObject=JSONObject.parseObject(data);
		return jsonObject;
	}
}
