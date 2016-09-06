package sitv.s003.dianbo.com.appmarkettwo.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	private static Toast lastToast = null;

	public static void clear() {
		if (lastToast != null)
			lastToast.cancel();
	}

	public static void show(Context paramContext, int paramInt) {
		show(paramContext, paramContext.getString(paramInt), 0);
	}

	public static void show(Context paramContext, int paramInt1, int paramInt2) {
		show(paramContext, paramContext.getString(paramInt1), paramInt2);
	}

	public static void show(Context paramContext, CharSequence paramCharSequence) {
		show(paramContext, paramCharSequence, 0);
	}

	public static void show(Context paramContext,
			CharSequence paramCharSequence, int paramInt) {
		if (lastToast != null)
			lastToast.cancel();
		Toast localToast = Toast.makeText(paramContext, paramCharSequence,
				paramInt);
		lastToast = localToast;
		localToast.show();
	}
}