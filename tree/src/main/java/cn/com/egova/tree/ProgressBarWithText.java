/**
 * 
 */
package cn.com.egova.tree;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarWithText extends LinearLayout {
	private ProgressBar bar;
	private TextView tvTip;

	public ProgressBarWithText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ProgressBarWithText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ProgressBarWithText(Context context) {
		super(context);
		init(context);
	}

	/**
	 * 
	 */
	private void init(Context context) {
		LayoutInflater flater = LayoutInflater.from(context);
		View view = flater.inflate(R.layout.progressbar_withtext, null);
		
		bar = (ProgressBar)view.findViewById(R.id.progressbar_withtext);
		tvTip = (TextView)view.findViewById(R.id.progressbar_tip);
		
		addView(view);
	}

	/**
	 * @return 进度条图片
	 */
	public ProgressBar getProgressBar() {
		return bar;
	}

	/**
	 * 设置提示信息
	 * @param strHint
	 */
	public void setHint(String strHint) {
		tvTip.setText(strHint);
	}
}
