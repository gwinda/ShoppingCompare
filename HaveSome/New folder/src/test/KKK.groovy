import groovy.util.XmlSlurper
import groovy.util.slurpersupport.GPathResult
import groovy.xml.MarkupBuilder
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

import javax.xml.xpath.*

import java.awt.TextArea
import java.io.StringWriter
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date
import java.util.TimeZone

String mapping(String inputXmlBody, String[] runtimeParams, Connection conn) {
    oll.b2b.core.mapping.util.MappingUtil util = new oll.b2b.core.mapping.util.MappingUtil();

    //get OLL mapping runtime parameters, "OLLSessionID=aa", "OLL_OriginalSourceFileName=zz", "OLL_SendPortID=yy", "PortProperty=xx"
    def ollSessionId = util.getRuntimeParameter("OLLSessionID", runtimeParams);
    def sourceFileName = util.getRuntimeParameter("OLL_OriginalSourceFileName", runtimeParams);
    def portId = util.getRuntimeParameter("OLL_SendPortID", runtimeParams);
    def portProperty = util.getRuntimeParameter("PortProperty", runtimeParams);
    //pmt info
    def TP_ID = util.getRuntimeParameter("TP_ID", runtimeParams);
    def MSG_TYPE_ID = util.getRuntimeParameter("MSG_TYPE_ID", runtimeParams);
    def DIR_ID = util.getRuntimeParameter("DIR_ID", runtimeParams);
    def MSG_REQ_ID = util.getRuntimeParameter("MSG_REQ_ID", runtimeParams);

    def writer = new StringWriter()
    def outXml = new MarkupBuilder(new IndentPrinter(new PrintWriter(writer), "", false))


    byte[] bs = inputXmlBody.getBytes();
    def instr = inputXmlBody

    //Important: the inputXml is xml root element
    def inXml = new XmlParser().parseText(instr); //XmlSlurper
//	def  inXml = new XmlSlurper()
//	GPathResult inputXml = inXml.parseText(inputXmlBody)
    TimeZone.setDefault(TimeZone.getTimeZone('GMT+8'))
    def currentSystemDt = new Date()
    def current_dateTime = currentSystemDt.format("yyyy-MM-dd'T'HH:mm:ss")

//	println "-------------- session: "+ollSessionId+" start mapping at: "+currentSystemDt.format("yyyy-MM-dd HH:mm:ss.SSS")

    outXml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")


    outXml.
            'ns0:OLLMSGBUS.Alshaya_Invoice_UIF_Root' ('xmlns:ns0':'http://OLLMSGBUS.OLLMSGBUS_Alshaya_Invoice_UIF_Schema', 'xmlns:s0':'http://www.oocllogistics.com/datatype', 'xmlns:s1':'http://www.oocllogistics.com/header', 'xmlns:s2':'http://www.oocllogistics.com/invoice', 'xmlns:xsl':'http://www.w3.org/1999/XSL/Transform')
                    {
                        'Records'{
                            'Header'{
                                'ServiceInvoiceNumber' 'Service Invoice No.'
                                'InvoiceDate' 'Invoice date'
                                'InvoiceCurrency' 'Invoice Currency'
                                'DestinationCountry' 'Destination Country'
                                'MasterBLNumber' 'Master AWB/ BL No.'
                                'HouseBLNumber' 'House AWB / BL No.'
                                'ServiceProviderCode' 'Service Provider Code'
                                'ServiceProviderItemCode' 'Service Provider Item Code'
                                'ItemCode' 'Item Code'
                                'ItemDescription' 'Item description'
                                'ItemQty' 'Item qty'
                                'UnitPrice' 'Unit price'
                                'Remarks' 'Remarks'
                            }
                            inXml?.invoice_msg_root?.each { current_root ->
                                current_root?.invoiceDetail?.each { currents_invoiceDetail ->
                                    def var_v4 = StringLeft(current_root?.fnd?.'datatype:UNLOCODE'?.text() , "2")
                                    def var_v5 = current_root?.fnd?.'datatype:UNLOCODE'?.text()
                                    def var_v6 = "Missing Code Conversion UNCountryCode-AlshayaCountryCode on " + var_v5
                                    def var_v8 = "Missing Code Conversion OLLChargeCode-AlshayaChargeCode, charge code=" + currents_invoiceDetail?.charge_obj?.'datatype:charge_type'?.'datatype:charge_code'?.text()
                                    def var_v9 = currents_invoiceDetail?.charge_obj?.'datatype:charge_type'?.'datatype:charge_code'?.text()
                                    def var_v12 = currents_invoiceDetail?.charge_obj?.'datatype:units'?.text()
                                    'Record'{
                                        'ServiceInvoiceNumber' current_root?.invoice_num?.text()
                                        def var_v1 = util.ConvertDatetimeFormat(current_root?.invoice_attr?.'datatype:crt_date'?.'datatype:record_datetime'?.text(), "yyyy-MM-dd'T'HH:mm:ss" , "dd-MMM-yyyy" , "" , "")
                                        def var_v2 = util.ConvertDatetimeFormat(current_root?.invoice_attr?.issue_date?.'datatype:record_datetime'?.text() , "yyyy-MM-dd'T'HH:mm:ss" , "dd-MMM-yyyy" , "" , "")
                                        def var_v3 = ''
                                        if(var_v2==""){ var_v3 =var_v1 }
                                        else{ var_v3 =var_v2 }
                                        'InvoiceDate' var_v3
                                        'InvoiceCurrency' current_root?.invoice_amount?.'datatype:cur_code'?.text()
                                        def var_v7 = util.getConversion(TP_ID, MSG_TYPE_ID, DIR_ID, "UNCountryCode-AlshayaCountryCode" , string(var_v4)  ,conn)
                                        'DestinationCountry' var_v7
                                        'MasterBLNumber' current_root?.string_obj?.find{it?.'datatype:type_code'?.text()=='OBL_NUM'}?.'datatype:string_value'?.text()
                                        def blNumber = ''
                                        if(current_root?.string_obj?.find{it?.'datatype:type_code'?.text()=='HBL_NUM'}?.'datatype:string_value'?.text()?.length() > 0) {
                                            blNumber = current_root?.string_obj?.find{it?.'datatype:type_code'?.text()=='HBL_NUM'}?.'datatype:string_value'?.text()
                                        }
                                        else {
                                            blNumber = current_root?.string_obj?.find{it?.'datatype:type_code'?.text()=='OBL_NUM'}?.'datatype:string_value'?.text()
                                        }
                                        'HouseBLNumber' blNumber
                                        def OfficeSapCode = current_root?.partner_obj?.find{it?.'datatype:type_code'?.text()=='COLLECTION_OFFICE'}?.'datatype:PARTNER_CODE'?.text()
                                        def FNDUNLOCODE = current_root?.fnd?.'datatype:UNLOCODE'?.text()
                                        def CodeConvertFromVal = OfficeSapCode+'_'+FNDUNLOCODE
                                        def errorMsg='Missing Code Conversion for '+ CodeConvertFromVal +'. type OLLSapFNDUNLOCODE-AlshayaSupplierCode'
                                        def SupplierCode = util.getConversion(TP_ID, MSG_TYPE_ID, DIR_ID, 'OLLSapFNDUNLOCODE-AlshayaSupplierCode',CodeConvertFromVal ,conn)
                                        'ServiceProviderCode' SupplierCode
                                        'ServiceProviderItemCode' currents_invoiceDetail?.charge_obj?.'datatype:charge_type'?.'datatype:charge_code'?.text()
                                        def var_v10 = util.getConversion(TP_ID, MSG_TYPE_ID, DIR_ID, "OLLChargeCode-AlshayaChargeCode" , var_v9  ,conn)//util.CodeConversionThrowExceptionIfReturnEmpty("OLLChargeCode-AlshayaChargeCode" , var_v9 , string(var_v8) ,tib:render-xml(CdeConversionData))

                                        'ItemCode' var_v10
                                        if(currents_invoiceDetail?.desc){
                                            'ItemDescription' currents_invoiceDetail?.desc?.text()
                                        }
                                        def var_v11 = ReturnQTY(currents_invoiceDetail?.charge_obj?.'datatype:units'?.text())
                                        'ItemQty' var_v11
                                        def amt_value=currents_invoiceDetail?.charge_obj?.'datatype:charge_value'?.'datatype:amt'?.text()
                                        def var_v13 =0

                                        if(amt_value!=''  && var_v12!=''){
                                            var_v13 = string(Double.parseDouble(amt_value) / var_v12?.toDouble())
                                            if ( var_v13 - (int) var_v13 == 0){
                                                var_v13= (int) var_v13
                                            }
                                        }else if(var_v12=='' ||amt_value ==''){
                                            var_v13 ='NaN'
                                        }
                                        'UnitPrice' var_v13
                                        'Remarks' ''
                                    }
                                }
                            }
                        }
                    }

    def outxml = XmlUtil.serialize(writer.toString());
//	println outxml;

//	println "finished: "+currentSystemDt.format("yyyy-MM-dd HH:mm:ss.SSS")

    return outxml;

//	return writer.toString();

}

/**
 * -- Common mapping function, suggest move to core library to provide such general function like xsl provide function, or tib:xxx in designer.
 */


public boolean notEmpty(Object data){
    if(null != data){
        if(data instanceof String){
            return data.length() > 0
        }else if(data instanceof List){
            data.size > 0
        }
    }else{
        return false;
    }
}

public String substring(String str, int start, int length){
    if(null != str && str.length() > 0){
        return str.substring(start,str.length + length)
    }else{
        return ""
    }
}

public def string(def str){
    return str
}

public def string_length(def str){
    if(null != str && str.length() > 0){
        return str.length()
    }else{
        return 0
    }
}

public static String StringLeft(String s, String sLength) {
    try {
        int intLength = Integer.parseInt(sLength);
        if (s != null && !s.equals("") && intLength >= s.length()) {
            return s;
        } else {
            return s.substring(0, intLength);
        }

    } catch (Exception ex) {
        return "";
    }
}

public static String ReturnQTY(String sQTY) {
    try {
        if (IsNullOrEmpty(sQTY)) {
            return "0";
        }
        return sQTY;
    } catch (Exception e) {
        return sQTY;
    }
}
public static boolean IsNullOrEmpty(String sState) {
    if ("".equals(sState) || sState == null)
        return true;
    else
        return false;
}