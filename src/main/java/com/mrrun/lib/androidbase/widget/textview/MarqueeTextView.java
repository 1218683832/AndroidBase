package com.mrrun.lib.androidbase.widget.textview;

import java.lang.ref.WeakReference;

import com.mrrun.lib.androidbase.ablog.ABLog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * <b>类功能描述:</b><br>
 * 自定义TextView实现跑马灯<br>
 * <b>新增api:</b><br>
 * <li>调用{@link #setDelayed(int)}设置跑马灯开始的延迟时间 <li>调用
 * {@link #startMarquee()}开始跑马灯 <li>调用{@link #stopMarquee()}停止跑马灯 <li>调用
 * {@link #setDirection(Direction)}设置跑马灯的方向 <li>调用 {@link #setSpeed(float)
 * 设置跑马灯默认速度的倍数
 * 
 * @author lipin
 * @version 1.0
 */
public class MarqueeTextView extends AppCompatTextView {

	/**
	 * <b>属性功能描述:</b><br>
	 * 绘制文本的画笔
	 */
	private Paint mPaint;

	/**
	 * <b>属性功能描述:</b><br>
	 * 要绘制的文本内容
	 */
	private CharSequence mText;

	/**
	 * <b>属性功能描述:</b><br>
	 * 默认文字对齐方式
	 */
	private int gravity = android.view.Gravity.CENTER_VERTICAL
			| android.view.Gravity.LEFT;

	/**
	 * <b>属性功能描述:</b><br>
	 * 当前绘制文本的x位置
	 */
	private float mCurrentX = 0f;

	/**
	 * <b>属性功能描述:</b><br>
	 * 当前绘制文本的y位置
	 */
	private float mCurrentY = 0f;

	private float firstScrollX = 0f;

	private float firstScrollY = 0f;

	/**
	 * <b>属性功能描述:</b><br>
	 * 可以认为是文本沿X轴滚动的长度
	 */
	private float endX = 0f;

	/**
	 * <b>属性功能描述:</b><br>
	 * 可以认为是文本沿Y轴滚动的长度
	 */
	private float endY = 0f;

	/**
	 * <b>属性功能描述:</b><br>
	 * 控件宽度
	 */
	private float mViewWidth;

	/**
	 * <b>属性功能描述:</b><br>
	 * 控件高度
	 */
	private float mViewHeight;

	/**
	 * <b>属性功能描述:</b><br>
	 * 文本宽度
	 */
	private float mTextWidth;

	/**
	 * <b>属性功能描述:</b><br>
	 * 文本高度
	 */
	private float mTextHeight;

	/**
	 * <b>属性功能描述:</b><br>
	 * 是否第一次绘制文本
	 */
	private boolean isFirstPaint = true;

	/**
	 * <b>属性功能描述:</b><br>
	 * 持续绘制文本形成跑马灯效果的线程
	 */
	private Marquee mMarquee;

	/**
	 * <b>属性功能描述:</b><br>
	 * 每次滚动的距离(上下左右方向通用)
	 */
	private float mSroll;

	/**
	 * <b>属性功能描述:</b><br>
	 * 调节跑马灯速度的倍速
	 */
	private float speedMultiple = 1.0f;

	/**
	 * <b>属性功能描述:</b><br>
	 * 默认的跑马灯次数
	 */
	private int mMarqueeRepeatLimit = 3;

	/**
	 * <b>属性功能描述:</b><br>
	 * 跑马灯方向
	 */
	private Direction mDirection = Direction.LEFT;

	public MarqueeTextView(Context context) {
		super(context);
		init();
	}

	public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public MarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 初始化数据
	 */
	private void init() {
		mText = this.getText();
		mPaint = this.getPaint();
		mDirection = Direction.LEFT;
		setSingleLine(true);// 将文本设置为单行模式
		setGravity(gravity);// 左对齐垂直居中
		mMarqueeRepeatLimit = this.getMarqueeRepeatLimit();
		float density = this.getContext().getResources().getDisplayMetrics().density;
		mSroll = ((1.50f / 3.0f) * density) * speedMultiple;

		mMarquee = new Marquee(this);
		mMarquee.setRepeatLimit(mMarqueeRepeatLimit);
		mMarquee.setMarquee_delay(Marquee.MARQUEE_DELAY);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		isFirstPaint();
		calculateCurrentXOrY();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 判断此次绘制是否是第一次绘制
	 */
	private void isFirstPaint() {
		if (isFirstPaint) {
			isFirstPaint = false;
			textWidthAndHeight();
			startMarquee();
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 每次绘制文本之后，根据跑马灯方向计算当前X轴或Y轴的数值为下次绘制文本做准备
	 */
	private void calculateCurrentXOrY() {
		switch (mDirection) {
		case LEFT:
			mCurrentX = (float) (mCurrentX + mSroll);
			break;
		case RIGHT:
			mCurrentX = (float) (mCurrentX - mSroll);
			break;
		case UP:
			mCurrentY = (float) (mCurrentY + mSroll);
			break;
		case DOWN:
			mCurrentY = (float) (mCurrentY - mSroll);
			break;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mViewWidth = this.getMeasuredWidth();
		mViewHeight = this.getMeasuredHeight();
		firstScrollX = getScrollX(); // 起始位置不一定为0,改变内容后会变，需重新赋值
		firstScrollY = getScrollY();
		mCurrentX = firstScrollX;
		mCurrentY = firstScrollY;
		textWidthAndHeight();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 文字宽度高度
	 */
	private void textWidthAndHeight() {
		if (null != mText && mText.length() > 0) {
			Paint.FontMetrics metrics = mPaint.getFontMetrics();
			mTextWidth = mPaint.measureText(mText, 0, mText.length());
			mTextHeight = this.getTextSize() - metrics.descent;
			restScrollRelatedVariables();
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 重置滚动相关变量
	 */
	private void restScrollRelatedVariables() {
		switch (mDirection) {
		case LEFT:
			endX = firstScrollX + mTextWidth;
			break;
		case RIGHT:
			endX = firstScrollX + mViewWidth;
			break;
		case UP:
			endY = firstScrollY + mTextHeight / 2 + mViewHeight / 2;
			break;
		case DOWN:
			endY = firstScrollY + mTextHeight / 2 + mViewHeight / 2;
			break;
		}
	}

	/**
	 * <b>类功能描述:</b><br>
	 * 枚举类跑马灯的方向:上下左右
	 * 
	 * @author lipin
	 * 
	 */
	public enum Direction {
		LEFT, RIGHT, UP, DOWN
	}

	/**
	 * <b>类功能描述:</b><br>
	 * 跑马灯操作类
	 * 
	 * @author lipin
	 * 
	 */
	private final class Marquee extends Thread {
		/**
		 * <b>属性功能描述:</b><br>
		 * 默认开始跑马灯的延迟
		 */
		private static final int MARQUEE_DELAY = 1200;

		/**
		 * <b>属性功能描述:</b><br>
		 * 指向一个{@link MarqueeTextView}的软引用
		 */
		private final WeakReference<MarqueeTextView> mView;

		/**
		 * <b>属性功能描述:</b><br>
		 * 指定开始跑马灯的延迟
		 */
		private int marquee_delay = -1;

		/**
		 * <b>属性功能描述:</b><br>
		 * 跑马灯重复次数
		 */
		private int mRepeatLimit;

		private static final byte MARQUEE_STOPPED = 0x0;
		private static final byte MARQUEE_STARTING = 0x1;
		private static final byte MARQUEE_RUNNING = 0x2;

		/**
		 * <b>属性功能描述:</b><br>
		 * 跑马灯状态
		 */
		private byte mStatus = MARQUEE_STOPPED;

		Marquee(MarqueeTextView v) {
			mView = new WeakReference<MarqueeTextView>(v);
			this.marquee_delay = MARQUEE_DELAY;
		}

		/**
		 * <b>属性功能描述:</b><br>
		 * 跑马灯是否停止
		 * 
		 * @return <li>true，已停止<li>false，未停止
		 */
		@SuppressWarnings("unused")
		public boolean isStoped() {
			if (this.mStatus == MARQUEE_STOPPED) {
				return true;
			}
			return false;
		}

		@Override
		public void run() {
			MarqueeTextView marqueeTextView = mView.get();
			if (mRepeatLimit == 0) {// No Marquee
				returnToOriginaPositon(marqueeTextView);
				stopMarquee();
				return;
			}
			if (mStatus == MARQUEE_STOPPED) {
				stopMarquee();
				return;
			}
			if (null != marqueeTextView) {
				mStatus = MARQUEE_RUNNING;
				textScrollTo(marqueeTextView);
				switch (mDirection) {
				case LEFT:
					ABLog.i("MarqueeTextView", "mCurrentX = " + mCurrentX);
					ABLog.i("MarqueeTextView", "endX = " + endX);
					if (mCurrentX >= endX) {// 0+
						marqueeTextView.removeCallbacks(this);
						if (mStatus == MARQUEE_RUNNING) {
							if (mRepeatLimit >= 0) {
								mRepeatLimit--;
							}
							mCurrentX = firstScrollX - mViewWidth;
							post(mMarquee);
						}
					}
					break;
				case RIGHT:
					ABLog.i("MarqueeTextView", "mCurrentX = " + mCurrentX);
					ABLog.i("MarqueeTextView", "endX = " + endX);
					if (-mCurrentX >= endX) {// 0-
						marqueeTextView.removeCallbacks(this);
						if (mStatus == MARQUEE_RUNNING) {
							if (mRepeatLimit >= 0) {
								mRepeatLimit--;
							}
							mCurrentX = firstScrollX + mTextWidth;
							post(mMarquee);
						}
					}
					break;
				case UP:
					ABLog.i("MarqueeTextView", "mCurrentY = " + mCurrentY);
					ABLog.i("MarqueeTextView", "endY = " + endY);
					if (mCurrentY >= endY) {// 0+
						marqueeTextView.removeCallbacks(this);
						if (mStatus == MARQUEE_RUNNING) {
							if (mRepeatLimit >= 0) {
								mRepeatLimit--;
							}
							mCurrentY = firstScrollY - mTextHeight / 2
									- mViewHeight / 2;
							post(mMarquee);
						}
					}
					break;
				case DOWN:
					ABLog.i("MarqueeTextView", "mCurrentY = " + mCurrentY);
					ABLog.i("MarqueeTextView", "endY = " + endY);
					if (-mCurrentY >= endY) {// 0-
						marqueeTextView.removeCallbacks(this);
						if (mStatus == MARQUEE_RUNNING) {
							if (mRepeatLimit >= 0) {
								mRepeatLimit--;
							}
							mCurrentY = firstScrollY + mTextHeight / 2
									+ mViewHeight / 2;
							post(mMarquee);
						}
					}
					break;
				}
			}

		}

		/**
		 * <b>方法功能描述:</b><br>
		 * 文本回到原来的位置
		 * 
		 * @param marqueeTextView
		 */
		private void returnToOriginaPositon(MarqueeTextView marqueeTextView) {
			switch (mDirection) {
			case LEFT:
				marqueeTextView.scrollTo((int) firstScrollX, 0);
				break;
			case RIGHT:
				marqueeTextView.scrollTo((int) firstScrollX, 0);
				break;
			case UP:
				marqueeTextView.scrollTo(0, (int) firstScrollY);
				break;
			case DOWN:
				marqueeTextView.scrollTo(0, (int) firstScrollY);
				break;
			}
			marqueeTextView.invalidate();//
			// 有些Android调用scrollTo没有用，需要再调用一次invalidate()
			post(this);
		}

		/**
		 * <b>方法功能描述:</b><br>
		 * 文本滚动
		 * 
		 * @param marqueeTextView
		 *            {@link MarqueeTextView}
		 */
		private void textScrollTo(MarqueeTextView marqueeTextView) {
			switch (mDirection) {
			case LEFT:
				marqueeTextView.scrollTo((int) mCurrentX, 0);
				break;
			case RIGHT:
				marqueeTextView.scrollTo((int) mCurrentX, 0);
				break;
			case UP:
				marqueeTextView.scrollTo(0, (int) mCurrentY);
				break;
			case DOWN:
				marqueeTextView.scrollTo(0, (int) mCurrentY);
				break;
			}
			marqueeTextView.invalidate();//
			// 有些Android调用scrollTo没有用，需要再调用一次invalidate()
			post(this);
		}

		public byte getmStatus() {
			return mStatus;
		}

		public void setmStatus(byte mStatus) {
			this.mStatus = mStatus;
		}

		public void setRepeatLimit(int repeatLimit) {
			this.mRepeatLimit = repeatLimit;
		}

		public int getMarquee_delay() {
			return marquee_delay;
		}

		public void setMarquee_delay(int marquee_delay) {
			this.marquee_delay = marquee_delay;
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 停止跑马灯
	 */
	public void stopMarquee() {
		this.mMarquee.setmStatus(Marquee.MARQUEE_STOPPED);
		this.removeCallbacks(mMarquee);// 清空队列
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 开始跑马灯
	 */
	public void startMarquee() {
		if (this.mMarquee.getmStatus() == Marquee.MARQUEE_STOPPED) {
			this.mMarquee.setmStatus(Marquee.MARQUEE_STARTING);
			postDelayed(mMarquee, mMarquee.getMarquee_delay());
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 设置跑马灯开始的延迟时间
	 * 
	 * @param msecDelay
	 *            参数毫秒级
	 */
	public void setDelayed(int msecDelay) {
		if (msecDelay >= 0) {
			this.mMarquee.setMarquee_delay(msecDelay);
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 设置跑马灯默认速度的倍数
	 * 
	 * @param speedMultiple
	 *            参数必须大于0才起作用
	 */
	public void setSpeed(float speedMultiple) {
		if (speedMultiple < 0) {
			return;
		}
		this.speedMultiple = speedMultiple;
		float density = this.getContext().getResources().getDisplayMetrics().density;
		mSroll = ((1.40f / 3.0f) * density) * this.speedMultiple;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 设置跑马灯的方向
	 * 
	 * @param direction
	 *            跑马灯方向 {@link Direction}
	 */
	public void setDirection(Direction direction) {
		this.mDirection = direction;
		restScrollRelatedVariables();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * View资源回收，终止该View跑马灯线程
	 */
	@Override
	protected void onDetachedFromWindow() {
		if (null != this.mMarquee) {
			this.removeCallbacks(this.mMarquee);
			this.mMarquee.interrupt();
		}
		super.onDetachedFromWindow();
	}

	@Override
	protected void onTextChanged(CharSequence text, int start,
			int lengthBefore, int lengthAfter) {

		this.removeCallbacks(mMarquee); // 清空队列
		if (null != mDirection) {
			switch (mDirection) {
			case LEFT:
				this.mCurrentX = this.firstScrollX;// 滚动到初始位置
				this.scrollTo((int) mCurrentX, 0);
				break;
			case RIGHT:
				this.mCurrentX = this.firstScrollX;// 滚动到初始位置
				this.scrollTo((int) mCurrentX, 0);
				break;
			case UP:
				this.mCurrentY = this.firstScrollY;// 滚动到初始位置
				this.scrollTo(0, (int) mCurrentY);
				break;
			case DOWN:
				this.mCurrentY = this.firstScrollY;// 滚动到初始位置
				this.scrollTo(0, (int) mCurrentY);
				break;
			}
			this.invalidate();
		}
		super.onTextChanged(text, start, lengthBefore, lengthAfter);
		isFirstPaint = true;
		this.mText = text;
		if (null != mMarquee) {
			postDelayed(mMarquee, mMarquee.getMarquee_delay()); // 头部停
		} else {
			postDelayed(mMarquee, Marquee.MARQUEE_DELAY); // 头部停
		}

	}
}
