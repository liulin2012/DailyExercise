package com.whu.dailyexercise.weatherinfo;



import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
/**
 * 通过webservice获取相应城市天气
 * @author darktemple9
 *
 */
public class GetWeather {
	public String getRemoteInfo(String theCityName)
	{
		/**将城市中的市去掉是为了方便查询
		 * 如武汉市分割成武汉
		 */
		if(theCityName.contains("市"))
		{
			theCityName=theCityName.substring(0, theCityName.indexOf("市"));
		}
		// 命名空间
		String nameSpace = "http://WebXml.com.cn/";
		// 调用的方法名称
		String methodName = "getWeatherbyCityName";
		// EndPoint通常是将WSDL地址末尾的"?WSDL"去除后剩余的部分
		String endPoint = "http://webservice.webxml.com.cn/WebServices/WeatherWebService.asmx";
		// SOAP Action通常为命名空间 + 调用的方法名称
		String soapAction = "http://WebXml.com.cn/getWeatherbyCityName";

		// 指定WebService的命名空间和调用的方法名
		SoapObject rpc = new SoapObject(nameSpace, methodName);

		// 设置需调用WebService接口需要传入的两个参数mobileCode、userId
		rpc.addProperty("theCityName", theCityName);

		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);

		envelope.bodyOut = rpc;
		// 设置是否调用的是dotNet开发的WebService
		envelope.dotNet = true;
		// 等价于envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(rpc);

		HttpTransportSE transport = new HttpTransportSE(endPoint);
		try {
			transport.call(soapAction, envelope);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (XmlPullParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		// 获取返回的数据
		SoapObject object = (SoapObject) envelope.bodyIn;
		if(object!=null)
		{
			return object.getProperty(0).toString();
		}
		else
		{
			return null;
		}
	}
}
