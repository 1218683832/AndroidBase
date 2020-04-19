package com.mrrun.lib.androidbase.manager;

import android.annotation.SuppressLint;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * <b>方法功能描述:</b><br>
 * WebView管理器，提供常用设置
 * 
 * @author lipin
 * @version 1.0
 */
public class WebViewManager {

	private WebView webView;

	/**
	 * <b>属性功能描述:</b><br>
	 * 对WebView进行配置和管理
	 */
	private WebSettings webSettings;

	public WebViewManager(WebView webView) {
		this.webView = webView;
		this.webSettings = webView.getSettings();
		this.webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
	}

	/**
	 * <b>属性功能描述:</b><br>
	 * 加载网页
	 * 
	 * @param url
	 *            网页地址 <li>加载一个网页:http://www.google.com/</li> <li>
	 *            加载apk包中的html页面:file://android_asset/xxx.html</li> <li>
	 *            加载手机本地的html页面
	 *            :content://com.android.htmlfileprovider/sdcard/xxx.html</li>
	 * 
	 * @see WebView
	 */
	public void loadUrl(String url) {
		if (null != webView) {
			webView.loadUrl(url);// 加载一个网页
		}
	}

	/**
	 * <b>属性功能描述:</b><br>
	 * 处理各种通知 &请求事件
	 * 
	 * @param client
	 *            {@link WebViewClient}
	 */
	public void setWebViewClient(WebViewClient client) {
	}

	/**
	 * <b>属性功能描述:</b><br>
	 * 辅助 WebView处理 Javascript的对话框,网站图标,网站标题等等
	 * 
	 * @param client
	 *            {@link WebChromeClient}
	 */
	public void setWebChromeClient(WebChromeClient client) {
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 是否开启自适应功能
	 * 
	 * @param isAlow
	 * @return {@link WebViewManager}类的实例对象
	 * 
	 * @see WebViewManager
	 * @see WebSettings
	 */
	public WebViewManager setAdaptiveEnable(boolean isAlow) {
		if (null != webSettings) {
			webSettings.setUseWideViewPort(isAlow);// 将图片调整到适合webview的大小
			webSettings.setLoadWithOverviewMode(isAlow);// 缩放至屏幕的大小
		}
		return this;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 是否开启访问文件
	 * 
	 * @param isAlow
	 * @return {@link WebViewManager}类的实例对象
	 * 
	 * @see WebViewManager
	 * @see WebSettings
	 */
	public WebViewManager setFileAccessEable(boolean isAlow) {
		if (null != webSettings) {
			webSettings.setAllowFileAccess(isAlow);// 访问文件
		}
		return this;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 是否开启缩放功能
	 * 
	 * @return {@link WebViewManager}类的实例对象
	 * 
	 * @see WebViewManager
	 * @see WebSettings
	 */
	public WebViewManager setZoomEnable(boolean isAlow) {
		if (null != webSettings) {
			webSettings.setSupportZoom(isAlow);// 支持缩放，默认为true。是下面那个的前提。
			webSettings.setBuiltInZoomControls(isAlow);// 设置内置的缩放控件。若为false，则该WebView不可缩放
			webSettings.setDisplayZoomControls(true);// 隐藏原生的缩放控件
		}
		return this;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 是否启用JavaScript
	 * 
	 * @param isAlow
	 * @return {@link WebViewManager}类的实例对象
	 * 
	 * @see WebViewManager
	 * @see WebSettings
	 */
	@SuppressLint("SetJavaScriptEnabled")
	public WebViewManager setJavaScriptEnabled(boolean isAlow) {
		if (null != webSettings) {
			webSettings.setJavaScriptEnabled(isAlow);
		}
		return this;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 是否开启JavaScript自动弹窗
	 * 
	 * @param isOpen
	 * @return {@link WebViewManager}类的实例对象
	 * 
	 * @see WebViewManager
	 * @see WebSettings
	 */
	public WebViewManager setJavaScriptCanOpenWindowsAutomatically(
			boolean isOpen) {
		if (null != webSettings) {
			webSettings.setJavaScriptCanOpenWindowsAutomatically(isOpen);
		}
		return this;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * webview中缓存 的缓存模式
	 * 
	 * @param mode
	 *            {@link WebSettings} <li>LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据</li>
	 *            <li>LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据</li> <li>
	 *            LOAD_NO_CACHE: 不使用缓存，只从网络获取数据</li> <li>
	 *            LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据</li>
	 * 
	 * @return {@link WebViewManager}类的实例对象
	 * 
	 * @see WebViewManager
	 * @see WebSettings
	 */
	public WebViewManager setCacheMode(int mode) {
		if (null != webSettings) {
			webSettings.setCacheMode(mode);
		}
		return this;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 设置应用缓存路径
	 * 
	 * @param appCachePath
	 *            应用缓存路径
	 * @return {@link WebViewManager}类的实例对象
	 * 
	 * @see WebViewManager
	 * @see WebSettings
	 */
	public WebViewManager setAppCachePath(String appCachePath) {
		if (null != webSettings) {
			webSettings.setAppCacheEnabled(true);// 开启应用缓存功能
			webSettings.setAppCachePath(appCachePath);// 设置应用缓存路径
		}
		return this;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 设置应用最大缓存
	 * 
	 * @param appCacheMaxSize
	 * @return {@link WebViewManager}类的实例对象
	 * 
	 * @see WebViewManager
	 * @see WebSettings
	 */
	@SuppressWarnings("deprecation")
	public WebViewManager setAppCacheMaxSize(long appCacheMaxSize) {
		if (null != webSettings) {
			webSettings.setAppCacheMaxSize(appCacheMaxSize);
		}
		return this;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 设置编码格式 设置编码格式
	 * 
	 * @param encoding
	 *            编码格式
	 * @return {@link WebViewManager}类的实例对象
	 * 
	 * @see WebViewManager
	 * @see WebSettings
	 */
	public WebViewManager setDefaultTextEncodingName(String encoding) {
		if (null != webSettings) {
			webSettings.setDefaultTextEncodingName(encoding);
		}
		return this;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 是否开启自动加载图片
	 * 
	 * @param isAlow
	 * @return {@link WebViewManager}类的实例对象
	 * 
	 * @see WebViewManager
	 * @see WebSettings
	 */
	public WebViewManager setLoadsImagesAutomaticallyEable(boolean isAlow) {
		if (null != webSettings) {
			webSettings.setLoadsImagesAutomatically(isAlow);
		}
		return this;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 返回前一个页面
	 * 
	 * @return true：已经返回，false：到头了没法返回了
	 * 
	 * @see WebView
	 */
	public boolean goBack() {
		if (null != webView && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return false;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 返回后一个页面
	 * 
	 * @return true：已经返回，false：到头了没法返回了
	 * 
	 * @see WebView
	 */
	public boolean goForward() {
		if (null != webView && webView.canGoForward()) {
			webView.goForward();
			return true;
		}
		return false;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 在 Activity销毁{@link WebView}的时候，先让 {@link WebView}加载null内容，然后移除
	 * {@link WebView}，再销毁 {@link WebView}，最后置空。<br>
	 * <b>作用:</b><br>
	 * 避免{@link WebView}内存泄露<br>
	 * 
	 * @see WebView
	 */
	public void destroyWebView() {
		if (webView != null) {
			webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
			webView.clearHistory();
			((ViewGroup) webView.getParent()).removeView(webView);
			webView.destroy();
			webView = null;
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 清除网页访问留下的缓存<br>
	 * 由于内核缓存是全局的因此这个方法不仅仅针对{@link WebView}而是针对整个应用程序.
	 * 
	 * @param isClear
	 *            是否清除缓存
	 */
	public void clearCache(boolean isClear) {
		if (null != webView) {
			webView.clearCache(isClear);
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 清除当前webview访问的历史记录<br>
	 * 只会webview访问历史记录里的所有记录除了当前访问记录
	 */
	public void clearHistory() {
		if (null != webView) {
			webView.clearHistory();
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
	 */
	public void clearFormData() {
		if (null != webView) {
			webView.clearFormData();
		}
	}

	public WebView getWebView() {
		return webView;
	}

	public WebSettings getWebSettings() {
		return webSettings;
	}
}