package sitv.s003.dianbo.com.appmarkettwo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.text.TextUtils;
import android.util.Log;

public class Coder {

	static boolean DEBUG = true;
	private static final String TAG = "Coder";

	private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static final String decodeAES(String paramString1,
			String paramString2) {
		return "ds1";
	}

	public static final String encodeAES(String paramString1,
			String paramString2) {
		return "ds1";
	}


	//加密
	public static final String encodeSHA(String str) {

		String sha = "";
		if (TextUtils.isEmpty(str)) {
			return sha;
		}
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA");
			digest.update(str.getBytes());
			sha = bytesToString(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			sha = "";
			Log.e(TAG, e.getMessage());
		}
		return sha;
	}

	// 字节数组转换为字符串
	private static String bytesToString(byte[] bytes) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			buf.append(byteToHexString(bytes[i]));
		}
		return buf.toString();
	}

	private static String byteToHexString(byte paramByte) {
		int i = paramByte;
		if (i < 0)
			i += 256;
		int j = i / 16;
		int k = i % 16;
		return hexDigits[j] + hexDigits[k];
	}
}
