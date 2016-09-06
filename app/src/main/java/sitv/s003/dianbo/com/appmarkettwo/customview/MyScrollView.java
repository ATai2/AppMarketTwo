package sitv.s003.dianbo.com.appmarkettwo.customview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

	public MyScrollView(Context paramContext) {
		super(paramContext);
	}

	public MyScrollView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public MyScrollView(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
		return false;
	}

	protected boolean onRequestFocusInDescendants(int paramInt, Rect paramRect) {
		return false;
	}
}
