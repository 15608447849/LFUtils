package appmians.fragments.sharedlayer;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lzping.lfutils.R;
import com.lzping.lfutils.core.actyfrg.LFFragment;
import com.lzping.lfutils.core.manage.LFManager;
import com.lzping.lfutils.tool.Fprint;

/**
 * Created by user on 2017/1/12.
 * 显示
 */

public class ContentShow extends LFFragment {
    private static final String TAG = "_ContentShow";
    private View baselayout;
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Fprint.I(TAG,"onCreateView() - "+baselayout +" - -  " + webView + " ,bundle : "+savedInstanceState);
        if (savedInstanceState!=null){
            setBundle(savedInstanceState);
        }
        if (baselayout == null) {
            baselayout = inflater.inflate(R.layout.content_frag_layout, null);
            webView = (WebView) baselayout.findViewById(R.id.content_web);
            InitWebView(webView);
        }
        return baselayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Fprint.I(TAG,"onViewCreated() - "+baselayout +" - -  " + webView );
        try {
            String defurl = null;
            if (baselayout != null) {
                if (mBundle != null) {
                    Fprint.I(TAG,"mBundle : "+ mBundle);
                    defurl = mBundle.getString("def_url","http://www.iguoguo.net/2015/52925.html");

                    Bundle bundle = mBundle.getBundle(LFManager.SUB_BUNDLE_KEY);
                    if (bundle != null) {
                        defurl = bundle.getString("url","http://www.iguoguo.net/2015/52925.html");
                    }
                    startWebview(defurl);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void receiveMessage(LFFragment form, Bundle bundle) {
        super.receiveMessage(form, bundle);
        Fprint.I(TAG, "receiveMessage() " + bundle);
        if (bundle != null) {
            startWebview(bundle.getString("url"));
        }
    }

    // 支持flash
    public void playFlashUrl(String url){
        if (webView!=null){
            String temp = "<html><body bgcolor=\"" + "black"
                    + "\"> <br/><embed src=\"" + url + "\" width=\"" + "100%"
                    + "\" height=\"" + "90%" + "\" scale=\"" + "noscale"
                    + "\" type=\"" + "application/x-shockwave-flash"
                    + "\"> </embed></body></html>";
            String mimeType = "text/html";
            String encoding = "utf-8";
            webView.loadDataWithBaseURL("null", temp, mimeType, encoding, "");
        }
    }

    //初始化 webview
    private void InitWebView(WebView webView) {
        Fprint.I(TAG, "初始化webview 参数..."+webView);
        if (webView==null){
            return;
        }



        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//在内容显示内部显示滚动条
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Fprint.I(TAG," view web 加载url : "+url);
                view.loadUrl(url);
                return true;
//                return super.shouldOverrideUrlLoading(view, url);
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                Fprint.I(TAG, " onPageStarted " + view +" " + url+" "+ favicon);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                Fprint.I(TAG, " onPageFinished " + view +" " + url+" ");
                super.onPageFinished(view, url);
            }


        });
        webView.setWebChromeClient(new WebChromeClient(){

        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 可以打开JavaScript.
        if (true) return;
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setUseWideViewPort(true);//关键点
        /*
         * 用WebView显示图片，可使用这个参数
         * 设置网页布局类型：1、LayoutAlgorithm.NARROW_COLUMNS ：
         * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true); // 允许访问文件

        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮

        webSettings.setSupportZoom(true); // 支持缩放

        webSettings.setLoadWithOverviewMode(true);
        DisplayMetrics metrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        Fprint.I(TAG, "densityDpi = " + mDensity);
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Fprint.I(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Fprint.I(TAG, "onResume");

    }

    @Override
    public void onPause() {
        super.onPause();
        Fprint.I(TAG, "onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
        Fprint.I(TAG, "onStop");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Fprint.I(TAG,"onDestroy");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Fprint.I(TAG, "onHiddenChanged : " + hidden);
        if (hidden) {
//            stopWebView();
        }

    }

    private void startWebview(String defurl) {
        Fprint.I(TAG, "开始加载 - " + defurl + " - " + hashCode() + " - " + webView);
        if (webView != null && defurl != null) {
            webView.loadUrl(defurl);
        }
    }

    private void stopWebView() {
        if (webView!=null){
//            webView.pauseTimers();
//            webView.stopLoading();
//        webView.loadData("", "text/html", "utf-8");
//        webView.reload();
//            webView.destroy();
//            ((ViewGroup)baselayout).removeView(webView);
//            webView.destroy();
//            webView = null;
            Fprint.I(TAG, "停止- >>> ");
        }

    }
}
