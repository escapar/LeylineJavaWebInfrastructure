package net.masadora.mall.business.infrastructure.common;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import java.security.Key;

/**
 * @author XUYI DES加密
 */
public class DESUtil {
	private static String strKey = "MIIEoQIBAAKCAQEAuR+J5D3lhSPaRzpdqmK5G6sQBEONJf5HtVHpXn4eGDA2XpnG"
			+ "9xY3nI88PMSPlMMBJGjrCNavGUcBgwnZl/V/f+4Gb8lGUcMkVMw4K38i/VIWZv5M"
			+ "2mnTPayZrNN9Wp3uZKTu0L2Jxuyoi4j1yyYwAu7/oGpsqWISJUklalplII6tRPuv"
			+ "Aycru2ii3JWAE7jHktchb05vD/CPGfSC+I40Q7rLNKTwP3cAZU6gTcbcod1zuv8f"
			+ "h+si4k2k2xblalbEE0JbHSvg/1idY+RFfAfLoDCx6uwQeOr3iFudFkgfV2UsiCYR"
			+ "frTd9FLR8TwTncUKQx5ABmJuY2cmPGxrlleDzQIBIwKCAQBfNMqSo31aajW+O0Yd"
			+ "HNQ6HXXzkHR58HymZKPndA96J2xrKoOVBByZqL/kvNwgns1jLqTCt41AMyVZVYXZ"
			+ "Hyug4NAcO6CB0hKvRHSop8jaDPWUDcEf3qcnCE8IbMQf+XNJs+iIm/22wtpWY7Gb"
			+ "rT1DVleUU/1ebPNjofX8Ln0mrubSD94bi2Za7U215NbfXbhivts54L9RcXNBGpwO"
			+ "jmNYWWDEgM/E8P6M1T/yYJnjAxInsQDK3FtR45pJNRnu1Hg56bkNARUtJ9CKlvPQ"
			+ "rZ4mIeTRlH1FyGm6JOr7KIxcTg+eEqXQTX1431esYq/arAPr/E4Tlaly4IUs/xLd"
			+ "kheLAoGBAN/hkQuxw3u39UsIpoVkS+68+p5PIvbQZUk56eva+AyFhpfLmHJQlHqb"
			+ "AXPkFIZ1uNfuvQq0n25YM6oVs1E9EbC62hVnIjowo7EUch0hI2m6Ijsfk/fZ48hT"
			+ "6Syy8+x/Rd0LBxIblwx7FOzN24HbZEXLrisAO8jhyOtvNMECk7LNAoGBANOuhKxU"
			+ "uDAZwl0rI8qnxNXc95/2vZJ2hAI2yKCPgUogotrOg81+Kl3xWTASaEdkgjFiosws"
			+ "ZlhObaQamMVOp/Cu6OwcwG9orp3sofjvy65jBXLCDLNCAX4RnG6XaOkIMjEfFy6/"
			+ "3HnxxIHF9gLuhBIsk7YY9Wo+sMYSKz7IfRUBAoGBANl8CT6PbXDeloNnfTEfmjhf"
			+ "zuLtyjGXPdIbAH63dJ5zFQh8orgxBUPnCLm4/f72BAT2i8FBv3KBkUYjtYIeES9W"
			+ "bXPgh6Y94Nfn+dMngXymL+Gppa7w6+cs8SQbjd5tAgntdJU/X4h3kKuGJbFYx87F"
			+ "3GRJXqXipewFoPYCgNmTAoGAbN1ozafpslZj9WaksV2Ybfycm2jzx6qqSkgPaIRR"
			+ "Hs70qwsfNntmPvEmjcBS4uM7pF6c3gg0onF6NyOfBmL3SJR3yeLmn7It6tF33xw8"
			+ "3Vd/JRNW/Rqhroy22co19DAZ0B6eNUy6h9tsX/9ok8sfWc3ITwWFhxjtMqpCIEnZ"
			+ "7YsCgYB2+iUjJGAUTKq7Z6K2On4XQN1hB4O8wTuMiw7novo2EOi5YTy7g9N5icQ/"
			+ "0iktJpDv9mSB9tvPic12GYt0XVZgtL76/B2n6WBMpUMqAqy5BACGeXiZVsMzxG3v"
			+ "g9UXZthrO35JbLY+cZNX2WrFrCzYGyfeTKj4/CmvtaNgEVs/Nw==";
	private static Cipher encryptCipher = null;
	private static Cipher decryptCipher = null;

	static {
		try {
			java.security.Security
					.addProvider(new com.sun.crypto.provider.SunJCE());
			Key key = getKey(strKey.getBytes());
			encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);

			decryptCipher = Cipher.getInstance("DES");
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
		}
	}

	private static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	private static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	private static byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	public static String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	private static byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	public static String decrypt(String strIn) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

	private static Key getKey(byte[] arrBTmp) throws Exception {
		byte[] arrB = new byte[8];

		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}
	
	public static String doubleEncrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(Base64.encode(strIn.getBytes()).getBytes()));
	}
	
	public static String doubleDecrypt(String strIn) throws Exception {
		return new String(Base64.decode(new String(decrypt(hexStr2ByteArr(strIn)))));
	}

}
