package com.whu.dailyexercise.weatherinfo;



import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
/**
 * ͨ��webservice��ȡ��Ӧ��������
 * @author darktemple9
 *
 */
public class GetWeather {
	public String getRemoteInfo(String theCityName)
	{
		/**�������е���ȥ����Ϊ�˷����ѯ
		 * ���人�зָ���人
		 */
		if(theCityName.contains("��"))
		{
			theCityName=theCityName.substring(0, theCityName.indexOf("��"));
		}
		// �����ռ�
		String nameSpace = "http://WebXml.com.cn/";
		// ���õķ�������
		String methodName = "getWeatherbyCityName";
		// EndPointͨ���ǽ�WSDL��ַĩβ��"?WSDL"ȥ����ʣ��Ĳ���
		String endPoint = "http://webservice.webxml.com.cn/WebServices/WeatherWebService.asmx";
		// SOAP Actionͨ��Ϊ�����ռ� + ���õķ�������
		String soapAction = "http://WebXml.com.cn/getWeatherbyCityName";

		// ָ��WebService�������ռ�͵��õķ�����
		SoapObject rpc = new SoapObject(nameSpace, methodName);

		// ���������WebService�ӿ���Ҫ�������������mobileCode��userId
		rpc.addProperty("theCityName", theCityName);

		// ���ɵ���WebService������SOAP������Ϣ,��ָ��SOAP�İ汾
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);

		envelope.bodyOut = rpc;
		// �����Ƿ���õ���dotNet������WebService
		envelope.dotNet = true;
		// �ȼ���envelope.bodyOut = rpc;
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
		

		// ��ȡ���ص�����
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
