package com.app.das.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 각종 데이터에 마스크 처리를 하는 로직이 구현되어 있다.
 * @author ysk523
 *
 */
public class MaskUtil 
{
	/**
	 * 데이타 Formatting을 한다.
	 * @param maskFlag
	 * @param value
	 * @return
	 */
	public static String getMaskString(int maskFlag, String value)
	{
		String newValue = "";
		if(value == null || value.length() == 0)
		{
			return "";
		}
		
		try
		{			
			//날짜포멧이면(yyyy-MM-dd)
			if(maskFlag == 1)
			{
				newValue = MaskUtil.getTemplateType(value, value.length(), "yyyy-MM-dd");
			}
			//	일시포멧이면(yyyy-MM-dd HH:mm:ss)
			else if(maskFlag == 2)
			{
				newValue = MaskUtil.getTemplateType(value, value.length(), "yyyy-MM-dd HH:mm:ss");				
			}
			//원화 금액포멧이면(###,###,###,###) 
			else if(maskFlag == 3)
			{
				DecimalFormat df = new DecimalFormat();				
				df.applyLocalizedPattern("###,###,###,###");
				newValue = df.format(new BigDecimal(value));				
			}
			//달라 금액포멧이면(###,###,###,###.00) 
			else if(maskFlag == 4)
			{
				DecimalFormat df = new DecimalFormat();
				df.applyLocalizedPattern("###,###,###,###.00");
				newValue = df.format(new BigDecimal(value));
			}
			//계좌번호(###-##-######) 
			else if(maskFlag == 5)
			{
				// 계좌번호가 제일 앞에 0이 있으면 디비에는 빠져있으므로 붙여준다
				if (value.length() < 11) {
					int len = value.length();
					
					for (int k=0; k<11-len; k++) {
						value = "0"+value;
					}
				}
				newValue = MaskUtil.getTemplateType(value, value.length(), "###-##-######");				
			}
			//카드번호(####-####-####-####) 
			else if(maskFlag == 6)
			{
				newValue = MaskUtil.getTemplateType(value, value.length(), "####-####-####-####");
			}
			//주민번호(######-#######) 
			else if(maskFlag == 7)
			{
				if( value != null ) {
					if( 10 <= value.length() && value.length() < 13 ) {
						for( int k = value.length(); k < 13; k++ ) {
							value = "0" + value;
						}
					}
				}
				newValue = MaskUtil.getTemplateType(value, value.length(), "######-#######");
			}
			// 날짜포멧 년월(yyyy-MM) 
			else if(maskFlag == 8)
			{
				newValue = MaskUtil.getTemplateType(value, value.length(), "####-##");
			}
			// 우편번호(###-###)
			else if(maskFlag == 9)
			{
				newValue = MaskUtil.getTemplateType(value, value.length(), "###-###");
			}
			// 사업자번호(###-##-#####)
			else if(maskFlag == 10)
			{
				newValue = MaskUtil.getTemplateType(value, value.length(), "###-##-#####");
			}
			// 날짜포멧 년월(yy-MM-dd) 
			else if(maskFlag == 11)
			{
				newValue = MaskUtil.getTemplateType(value, value.length(), "##-##-##");
			}
			// 날짜포멧 년월일(yyyy.MM.dd) 
			else if(maskFlag == 12)
			{
				newValue = MaskUtil.getTemplateType(value, value.length(), "####.##.##");				
			}
			// 날짜포멧 년월일(yy:MM:dd) 
			else if(maskFlag == 13)
			{
				newValue = MaskUtil.getTemplateType(value, value.length(), "##:##:##");
			}
			// 날짜포멧 년월(##:##:##:##) 
			else if(maskFlag == 14)
			{
				newValue = MaskUtil.getTemplateType(value, value.length(), "##:##:##:##");
			}
			// 날짜포멧 년월(yy-MM) 
			else if(maskFlag == 15)
			{
				newValue = MaskUtil.getTemplateType(value, value.length(), "##-##");
			}
			else
			{
				newValue = value;
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e);
			
			newValue = value;
		}
		
		return newValue;
	}

	private static String getTemplateType(String oldType, int fixedLength, String mask)
	{
		StringBuffer newType = null;
		char[] extraChar = {'-', ':', ' ', '.'};
		if (oldType != null && oldType.length() == fixedLength)
		{
			newType = new StringBuffer(oldType);
			if (mask != null)
			{
				for (int i = 0, len = mask.length(); i < len; i++)
				{
					if (extraChar[0] == mask.charAt(i))
					{
						newType.insert(i, extraChar[0]);
					}
					else if (extraChar[1] == mask.charAt(i))
					{
						newType.insert(i, extraChar[1]);
					}
					else if (extraChar[2] == mask.charAt(i))
					{
						newType.insert(i, extraChar[2]);
					}
					else if (extraChar[3] == mask.charAt(i))
					{
						newType.insert(i, extraChar[3]);
					}
				}
			}
			else
			{
				return oldType;
			}
			return newType.toString();
		}
		else
		{
			return oldType;
		}
	}

}
