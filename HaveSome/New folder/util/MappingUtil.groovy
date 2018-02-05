package oll.b2b.core.mapping.util

// for groovy script common mapping library

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.text.SimpleDateFormat
import oll.b2b.core.msglogger.MsgBusLogger

import oll.b2b.core.common.session.OLLRuntimeSession




public  String WriteObsoleteEventLog(String sessionId, String desc) {
	return WriteEventLogWithSeverity(sessionId, desc, 25);
}

public  String WriteErrorEventLog(String sessionId, String desc) {
	return WriteEventLogWithSeverity(sessionId, desc, 30);
}

private  String WriteEventLogWithSeverity(String sessionId,
		String desc, int severityLevel) {
	String key = "n/a";
	switch (severityLevel) {
		case 10:
			key = "InfoDescriptionInMapping";
			break;
		case 20:
			key = "WarningDescriptionInMapping";
			break;
		case 25:
			key = "ObsoleteDescriptionInMapping";
			break;
		case 30:
			key = "ErrorDescriptionInMapping";
			break;
	}
	String val = oll.b2b.core.common.session.OLLRuntimeSession.getSessionValue(sessionId, key);
	if (val == null || val.trim().length() == 0) {
		val = desc;
	} else {
		val += ";; " + desc;
	}
	oll.b2b.core.common.session.OLLRuntimeSession.addSessionValue(sessionId, key, val);

	return "";
}



public String string(def str) {
	if (str instanceof String) {
		return (str==null?"":str.toString());
	} else {
		return str.toString();
	}
}

public int stringLength(String str){
	if (null != str && str.length() > 0) {
		return str.length();
	} else {
		return 0;
	}
}

/*
public  String substring(String str, int start, int length) {

		try{
			if (null != str && str.length() > 0) {
				
				int subStringLength=length+start;
				if(subStringLength<str.length())
				{
				return str.substring(start, length+start);
				}
				else
				{
					return str.substring(start, str.length());
				}
			} else {
				return "";
			}
		}
		catch(Exception ex)
		{
			return "";
		}
	}
	*/


//Rollback to David's original version
public String substring(String str, int start, int length) {
	if (null != str && str.length() > 0) {
		return str.substring(start, str.length + length);
	} else {
		return "";
	}
}




public String formatString(long val, String outputFormat) {
	//string format:
	//parameter_1;;parameter_2;;.....;;parameter_[N]##{output_format}##
	//{output_format} -- Java String.format, pls refer this feature in JDK api. For example when {output_format} = "%09d"
	// 0  = pad with '0' in prefix, 9 = total length is 9, d = mean the value is integer

	//for example:
	//GetSequenceNextValWithDefault;;LowesTradeStone856;;1002##%09d##
	//GetSequenceNextValWithDefault;;LowesTradeStone856;;1002####

	String ret = val;
	if (outputFormat!=null && outputFormat.length()>0) {
		ret = String.format(outputFormat, val);
	}
	return ret;
}

public long GetSequenceNextValWithDefault(String seqKey, long defaultVal, Connection conn) {
	MsgBusLogger logger = new MsgBusLogger();
	return logger.GetSequenceNextValWithDefault(conn, seqKey, defaultVal);;
}

public boolean notEmpty(Object data) {
	return isNotEmpty(data);
}

public boolean isNotEmpty(Object data) {
	if (data==null)
		return false;

	if (data instanceof String){
		return (data.length() > 0)
	} else{
		return (data!=null)
	}
}

public String removeBOM(String str) {
	byte[] bs = str.getBytes();
	String instr = str;
	if (-17 == bs[0] && -69 == bs[1] && -65 == bs[2]) {
		instr = new String(bs, 3, bs.length-3);
	} else if (-17 == bs[0] && -65 == bs[1] && -67 == bs[2]) {
		instr = new String(bs, 3, bs.length-3);
	} else if (-17 == bs[0] && -69 == bs[1] && 63 == bs[2]) {
		instr = new String(bs, 3, bs.length-3);
	} else if (63 == bs[0] && 63 == bs[1] && 63 == bs[2]) {
		instr = new String(bs, 3, bs.length-3);
	}
	return instr;
}

public String getConversionWithDefault(String TP_ID, String MSG_TYPE_ID, String DIR_ID, String convertTypeId, String fromValue, String defaultValue, Connection conn) throws Exception {
	String ret = getConversion(TP_ID, MSG_TYPE_ID, DIR_ID, convertTypeId, fromValue, conn);
	if (ret==null || ret.length()==0) {
		ret = defaultValue;
	}
	return ret;
}

public String CodeConversionWithOriginalValue(String TP_ID, String MSG_TYPE_ID, String DIR_ID, String convertTypeId, String fromValue, Connection conn) throws Exception {
	String ret = getConversion(TP_ID, MSG_TYPE_ID, DIR_ID, convertTypeId, fromValue, conn);
	if (ret==null || ret.length()==0) {
		ret = fromValue;
	}
	return ret;
}

public String getConversion(String TP_ID, String MSG_TYPE_ID, String DIR_ID, String convertTypeId, String fromValue, Connection conn) throws Exception {
	if (conn==null)
		return "";

	String ret = "";
	PreparedStatement pre = null;
	ResultSet result = null;
	String sql = "select ext_cde from b2b_cde_conversion where tp_id=? and msg_type_id=? and dir_id=? and convert_type_id=? and int_cde=?";
	try {
		pre = conn.prepareStatement(sql);
		pre.setMaxRows(50000);
		pre.setQueryTimeout(60);

		pre.setString(1, TP_ID);
		pre.setString(2, MSG_TYPE_ID);
		pre.setString(3, DIR_ID);
		pre.setString(4, convertTypeId);
		pre.setString(5, fromValue);
		result = pre.executeQuery();

		if (result.next()) {
			ret = result.getString(1);
		}
	} finally {
		if (result!=null)
			result.close();
		if (pre!=null)
			pre.close();
	}
	return ret;
}

public String getConversionIgnoreCase(String TP_ID, String MSG_TYPE_ID, String DIR_ID, String convertTypeId, String fromValue, Connection conn) throws Exception {
	if (conn==null)
		return "";

	String ret = "";
	PreparedStatement pre = null;
	ResultSet result = null;
	String sql = "select ext_cde from b2b_cde_conversion where tp_id=? and msg_type_id=? and dir_id=? and convert_type_id=? and upper(int_cde)=upper(?)";
	try {
		pre = conn.prepareStatement(sql);
		pre.setMaxRows(50000);
		pre.setQueryTimeout(60);

		pre.setString(1, TP_ID);
		pre.setString(2, MSG_TYPE_ID);
		pre.setString(3, DIR_ID);
		pre.setString(4, convertTypeId);
		pre.setString(5, fromValue);
		result = pre.executeQuery();

		if (result.next()) {
			ret = result.getString(1);
		}
	} finally {
		if (result!=null)
			result.close();
		if (pre!=null)
			pre.close();
	}
	return ret;
}


public String getConversionByConvertType(String convertTypeId, String fromValue, Connection conn) throws Exception {
	String ret = null;
	PreparedStatement pre = null;
	ResultSet result = null;

	String sql = "select ext_cde from b2b_cde_conversion where convert_type_id=? and int_cde=?";

	try {
		pre = conn.prepareStatement(sql);

		pre.setMaxRows(50000);
		pre.setQueryTimeout(60);

		pre.setString(1, convertTypeId);
		pre.setString(2, fromValue);
		result = pre.executeQuery();

		if (result.next()) {
			ret = result.getString(1);
		}
	} finally {
		if (result!=null)
			result.close();
		if (pre!=null)
			pre.close();
	}
	return ret;
}




public String convertDateTime(String inputDate, String inputFormat, String outputFormat) throws Exception {
	String output = "";

	if (inputDate!=null && inputDate.trim().length()>0 && inputFormat!=null && inputFormat.trim().length()>0 && outputFormat!=null && outputFormat.trim().length()>0) {

		SimpleDateFormat sfmt = new SimpleDateFormat(inputFormat);
		java.util.Date date = sfmt.parse(inputDate);

		SimpleDateFormat soutfmt = new SimpleDateFormat(outputFormat);
		output = soutfmt.format(date);
	}

	return output;
}


//Jackson, pls try to use ConvertDatetimeFormat instead of convertDateTime!!!
/**
 * @param - inputValue input string for format if input format is "", then
 *        use standard formating, likes xmldatetime, 'yyyy-MM-dd' etc., for
 *        example, xmldatetime 'yyyy-MM-dd' do not need to inputFormat
 * @param inputFormat
 *            - input datetime format
 * @param outputFormat
 *            - ouput date format
 * @param emptyValue
 *            - if inputValue is null, will return this value
 * @param exceptionValue
 *            - if convert exception, will return this value.
 * @return
 */
public  String ConvertDatetimeFormat(String inputValue,
		String inputFormat, String outputFormat, String emptyValue,
		String exceptionValue) {
	String defaultDateFormat = "yyyy-MM-dd";

	if (inputValue == null || "".equals(inputValue))
		return emptyValue;
	try {
		Date dateTimeValue;
		SimpleDateFormat sdf;
		if (!"".equals(inputFormat)) {
			sdf = new SimpleDateFormat(inputFormat);
			sdf.setLenient(false);
			dateTimeValue = sdf.parse(inputValue);
		} else {
			sdf = new SimpleDateFormat(defaultDateFormat);
			sdf.setLenient(false);
			dateTimeValue = sdf.parse(inputValue);
		}

		/* Special Handle for dt format */
		if (outputFormat.startsWith("yyyy-MM-ddT")) {
			outputFormat = outputFormat.replaceAll("yyyy-MM-ddT",
					"yyyy-MM-dd'T'");
		}

		sdf = new SimpleDateFormat(outputFormat);

		return sdf.format(dateTimeValue);
	} catch (Exception ex) {
		return exceptionValue;
	}
}
		
		
public  String ConverttoXMLDateTime(String param1, String inputFormat) {
			try {
				String param3 = param1;
	
				return ConvertDatetimeFormat(param3, inputFormat,
						"yyyy-MM-dd'T'HH:mm:ss'.'S", "", "");
			} catch (Exception ex) {
				return "";
			}
		}


public  String ConvertXMLDateTimeToOtherFormat(String param1,
	String outputFormat) {
try {
	String param3 = param1;

	return ConvertDatetimeFormat(param3, "yyyy-MM-dd'T'HH:mm:ss",
			outputFormat, "", "");
} catch (Exception ex) {
	return "";
}
}

	
		

public String getRuntimeParameter(String name, String[] params) {
	String pn = name+"=";
	for(int i=0; params!=null && i<params.length; i++) {
		String tmp = params[i];
		if (tmp==null || tmp.length()==0)
			continue;
		if (tmp.startsWith(pn)) {
			return tmp.substring(pn.length());
		}
	}
	return "";
}

