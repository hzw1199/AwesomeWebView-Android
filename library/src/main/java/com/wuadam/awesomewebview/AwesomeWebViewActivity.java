package com.wuadam.awesomewebview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.MailTo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thefinestartist.converters.UnitConverter;
import com.thefinestartist.utils.etc.APILevel;
import com.thefinestartist.utils.service.ClipboardManagerUtil;
import com.thefinestartist.utils.ui.DisplayUtil;
import com.thefinestartist.utils.ui.ViewUtil;
import com.wuadam.awesomewebview.enums.Position;
import com.wuadam.awesomewebview.helpers.BitmapHelper;
import com.wuadam.awesomewebview.helpers.ColorHelper;
import com.wuadam.awesomewebview.helpers.DownPicUtil;
import com.wuadam.awesomewebview.helpers.PermissionHelper;
import com.wuadam.awesomewebview.helpers.TypefaceHelper;
import com.wuadam.awesomewebview.helpers.UrlParser;
import com.wuadam.awesomewebview.listeners.BroadCastManager;
import com.wuadam.awesomewebview.objects.CustomMenu;
import com.wuadam.awesomewebview.views.ShadowLayout;
import com.wuadam.awesomewebview.views.VideoEnabledWebChromeClient;
import com.wuadam.awesomewebview.views.VideoEnabledWebView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

//MailTo Imports

/**
 * Created by Leonardo on 11/14/15.
 */
public class AwesomeWebViewActivity extends AppCompatActivity
        implements View.OnClickListener, Handler.Callback {

    protected int key;

    protected boolean rtl;
    protected int theme;

    protected int statusBarColor;
    protected boolean statusBarIconDark;

    protected int toolbarColor;
    protected boolean toolbarVisible;

    protected int iconDefaultColor;
    protected int iconDisabledColor;
    protected int iconPressedColor;
    protected int iconSelector;

    protected boolean showIconClose;
    protected boolean disableIconClose;
    protected boolean showIconBack;
    protected boolean disableIconBack;
    protected boolean showIconForward;
    protected boolean disableIconForward;
    protected boolean showIconMenu;
    protected boolean disableIconMenu;

    protected boolean showDivider;
    protected boolean gradientDivider;
    protected int dividerColor;
    protected float dividerHeight;

    protected boolean showProgressBar;
    protected int progressBarColor;
    protected float progressBarHeight;
    protected Position progressBarPosition;

    protected String titleDefault;
    protected boolean updateTitleFromHtml;
    protected float titleSize;
    protected String titleFont;
    protected int titleColor;

    protected boolean showUrl;
    protected float urlSize;
    protected String urlFont;
    protected int urlColor;

    protected int menuColor;
    protected int menuDropShadowColor;
    protected float menuDropShadowSize;
    protected int menuSelector;

    protected float menuTextSize;
    protected String menuTextFont;
    protected int menuTextColor;

    protected int menuTextGravity;
    protected float menuTextPaddingLeft;
    protected float menuTextPaddingRight;

    protected boolean showMenuRefresh;
    protected int stringResRefresh;
    protected boolean showMenuFind;
    protected int stringResFind;
    protected boolean showMenuShareVia;
    protected int stringResShareVia;
    protected boolean showMenuCopyLink;
    protected int stringResCopyLink;
    protected boolean showMenuOpenWith;
    protected int stringResOpenWith;
    protected boolean showMenuSavePhoto;
    protected int stringResSavePhoto;
    protected boolean showToastPhotoSavedTo;
    protected int stringResPhotoSavedTo;
    protected boolean fileChooserEnabled;
    protected int stringResFileChooserTitle;

    protected List<CustomMenu> customMenus;

    protected int animationCloseEnter;
    protected int animationCloseExit;

    protected boolean backPressToClose;
    protected int stringResCopiedToClipboard;

    protected Boolean webViewSupportZoom;
    protected Boolean webViewMediaPlaybackRequiresUserGesture;
    protected Boolean webViewBuiltInZoomControls;
    protected Boolean webViewDisplayZoomControls;
    protected Boolean webViewAllowFileAccess;
    protected Boolean webViewAllowContentAccess;
    protected Boolean webViewLoadWithOverviewMode;
    protected Boolean webViewSaveFormData;
    protected Integer webViewTextZoom;
    protected Boolean webViewUseWideViewPort;
    protected Boolean webViewSupportMultipleWindows;
    protected WebSettings.LayoutAlgorithm webViewLayoutAlgorithm;
    protected String webViewStandardFontFamily;
    protected String webViewFixedFontFamily;
    protected String webViewSansSerifFontFamily;
    protected String webViewSerifFontFamily;
    protected String webViewCursiveFontFamily;
    protected String webViewFantasyFontFamily;
    protected Integer webViewMinimumFontSize;
    protected Integer webViewMinimumLogicalFontSize;
    protected Integer webViewDefaultFontSize;
    protected Integer webViewDefaultFixedFontSize;
    protected Boolean webViewLoadsImagesAutomatically;
    protected Boolean webViewBlockNetworkImage;
    protected Boolean webViewBlockNetworkLoads;
    protected Boolean webViewJavaScriptEnabled;
    protected Boolean webViewAllowUniversalAccessFromFileURLs;
    protected Boolean webViewAllowFileAccessFromFileURLs;
    protected String webViewGeolocationDatabasePath;
    protected Boolean webViewAppCacheEnabled;
    protected String webViewAppCachePath;
    protected Boolean webViewDatabaseEnabled;
    protected Boolean webViewDomStorageEnabled;
    protected Boolean webViewGeolocationEnabled;
    protected Boolean webViewJavaScriptCanOpenWindowsAutomatically;
    protected String webViewDefaultTextEncodingName;
    protected String webViewUserAgentString;
    protected Boolean webViewUserAgentAppend;
    protected Boolean webViewNeedInitialFocus;
    protected Integer webViewCacheMode;
    protected Integer webViewMixedContentMode;
    protected Boolean webViewOffscreenPreRaster;
    protected Boolean webViewAppJumpEnabled;
    protected Boolean webViewCookieEnabled;
    protected Boolean webViewCameraEnabled;
    protected Boolean webViewAudioEnabled;

    protected String filePickerCamMessage;
    protected ValueCallback<Uri> filePickerFileMessage;
    protected ValueCallback<Uri[]> filePickerFilePath;
    protected final static int FILE_PICKER_REQ_CODE = 1;
    protected String FILE_TYPE = "*/*";

    protected String injectJavaScript;
    protected Boolean injectJavaScriptMainPage;

    protected Map<String, Map<String, String>> injectCookies;

    protected String mimeType;
    protected String encoding;
    protected String data;
    protected String url;
    protected Map<String, String> extraHeaders;
    protected Boolean extraHeadersMainPage;
    protected CoordinatorLayout coordinatorLayout;
    protected AppBarLayout appBar;
    protected Toolbar toolbar;
    protected RelativeLayout toolbarLayout;
    protected TextView title;
    protected TextView urlTv;
    protected AppCompatImageButton close;
    protected AppCompatImageButton back;
    protected AppCompatImageButton forward;
    protected AppCompatImageButton more;
    protected WebView webView;
    protected WebChromeClient webChromeClient;
    protected WebViewClient webViewClient;
    protected View gradient;
    protected View divider;
    protected ProgressBar progressBar;
    protected RelativeLayout menuLayout;
    protected ShadowLayout shadowLayout;
    protected LinearLayout menuBackground;
    protected LinearLayout menuRefresh;
    protected TextView menuRefreshTv;
    protected LinearLayout menuFind;
    protected TextView menuFindTv;
    protected LinearLayout menuShareVia;
    protected TextView menuShareViaTv;
    protected LinearLayout menuCopyLink;
    protected TextView menuCopyLinkTv;
    protected LinearLayout menuOpenWith;
    protected TextView menuOpenWithTv;
    protected FrameLayout webLayout;
    DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                    String mimetype, long contentLength) {
            BroadCastManager.onDownloadStart(AwesomeWebViewActivity.this, key, url, userAgent,
                    contentDisposition, mimetype, contentLength);
        }
    };

    protected Handler handler = new Handler(this);
    protected final int MSG_CLICK_ON_WEBVIEW = 1;
    protected final int MSG_CLICK_ON_URL = 2;

    protected void initializeOptions() {
        Intent intent = getIntent();
        if (intent == null) return;

        AwesomeWebView.Builder builder = (AwesomeWebView.Builder) intent.getSerializableExtra("builder");

        // set theme before resolving attributes depending on those
        setTheme(builder.theme != null ? builder.theme : 0);

        // resolve themed attributes
        TypedValue typedValue = new TypedValue();
        TypedArray typedArray = obtainStyledAttributes(typedValue.data, new int[]{
                R.attr.colorPrimaryDark, R.attr.colorPrimary, R.attr.colorAccent,
                android.R.attr.textColorPrimary, android.R.attr.textColorSecondary,
                android.R.attr.selectableItemBackground, android.R.attr.selectableItemBackgroundBorderless
        });
        int colorPrimaryDark = typedArray.getColor(0, ContextCompat.getColor(this, R.color.finestGray));
        int colorPrimary = typedArray.getColor(1, ContextCompat.getColor(this, R.color.finestWhite));
        int colorAccent = typedArray.getColor(2, ContextCompat.getColor(this, R.color.finestBlack));
        int textColorPrimary =
                typedArray.getColor(3, ContextCompat.getColor(this, R.color.finestBlack));
        int textColorSecondary =
                typedArray.getColor(4, ContextCompat.getColor(this, R.color.finestSilver));
        int selectableItemBackground =
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? typedArray.getResourceId(5, 0)
                        : R.drawable.selector_light_theme;
        int selectableItemBackgroundBorderless =
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? typedArray.getResourceId(6, 0)
                        : R.drawable.selector_light_theme;
        typedArray.recycle();

        key = builder.key;

        rtl = builder.rtl != null ? builder.rtl : getResources().getBoolean(R.bool.is_right_to_left);

        statusBarColor = builder.statusBarColor != null ? builder.statusBarColor : colorPrimaryDark;
        statusBarIconDark = builder.statusBarIconDark != null ? builder.statusBarIconDark : false;

        toolbarColor = builder.toolbarColor != null ? builder.toolbarColor : colorPrimary;
        toolbarVisible = builder.toolbarVisible != null ? builder.toolbarVisible : true;

        iconDefaultColor = builder.iconDefaultColor != null ? builder.iconDefaultColor : colorAccent;
        iconDisabledColor = builder.iconDisabledColor != null ? builder.iconDisabledColor
                : ColorHelper.disableColor(iconDefaultColor);
        iconPressedColor =
                builder.iconPressedColor != null ? builder.iconPressedColor : iconDefaultColor;
        iconSelector =
                builder.iconSelector != null ? builder.iconSelector : selectableItemBackgroundBorderless;

        showIconClose = builder.showIconClose != null ? builder.showIconClose : true;
        disableIconClose = builder.disableIconClose != null ? builder.disableIconClose : false;
        showIconBack = builder.showIconBack != null ? builder.showIconBack : true;
        disableIconBack = builder.disableIconBack != null ? builder.disableIconBack : false;
        showIconForward = builder.showIconForward != null ? builder.showIconForward : true;
        disableIconForward = builder.disableIconForward != null ? builder.disableIconForward : false;
        showIconMenu = builder.showIconMenu != null ? builder.showIconMenu : true;
        disableIconMenu = builder.disableIconMenu != null ? builder.disableIconMenu : false;

        showDivider = builder.showDivider != null ? builder.showDivider : true;
        gradientDivider = builder.gradientDivider != null ? builder.gradientDivider : true;
        dividerColor = builder.dividerColor != null ? builder.dividerColor
                : ContextCompat.getColor(this, R.color.finestBlack10);
        dividerHeight = builder.dividerHeight != null ? builder.dividerHeight
                : getResources().getDimension(R.dimen.defaultDividerHeight);

        showProgressBar = builder.showProgressBar != null ? builder.showProgressBar : true;
        progressBarColor = builder.progressBarColor != null ? builder.progressBarColor : colorAccent;
        progressBarHeight = builder.progressBarHeight != null ? builder.progressBarHeight
                : getResources().getDimension(R.dimen.defaultProgressBarHeight);
        progressBarPosition = builder.progressBarPosition != null ? builder.progressBarPosition
                : Position.BOTTOM_OF_TOOLBAR;

        titleDefault = builder.titleDefault;
        updateTitleFromHtml = builder.updateTitleFromHtml != null ? builder.updateTitleFromHtml : true;
        titleSize = builder.titleSize != null ? builder.titleSize
                : getResources().getDimension(R.dimen.defaultTitleSize);
        titleFont = builder.titleFont != null ? builder.titleFont : "Roboto-Medium.ttf";
        titleColor = builder.titleColor != null ? builder.titleColor : textColorPrimary;

        showUrl = builder.showUrl != null ? builder.showUrl : true;
        urlSize = builder.urlSize != null ? builder.urlSize
                : getResources().getDimension(R.dimen.defaultUrlSize);
        urlFont = builder.urlFont != null ? builder.urlFont : "Roboto-Regular.ttf";
        urlColor = builder.urlColor != null ? builder.urlColor : textColorSecondary;

        menuColor = builder.menuColor != null ? builder.menuColor
                : ContextCompat.getColor(this, R.color.finestWhite);
        menuDropShadowColor = builder.menuDropShadowColor != null ? builder.menuDropShadowColor
                : ContextCompat.getColor(this, R.color.finestBlack10);
        menuDropShadowSize = builder.menuDropShadowSize != null ? builder.menuDropShadowSize
                : getResources().getDimension(R.dimen.defaultMenuDropShadowSize);
        menuSelector = builder.menuSelector != null ? builder.menuSelector : selectableItemBackground;

        menuTextSize = builder.menuTextSize != null ? builder.menuTextSize
                : getResources().getDimension(R.dimen.defaultMenuTextSize);
        menuTextFont = builder.menuTextFont != null ? builder.menuTextFont : "Roboto-Regular.ttf";
        menuTextColor = builder.menuTextColor != null ? builder.menuTextColor
                : ContextCompat.getColor(this, R.color.finestBlack);

        menuTextGravity = builder.menuTextGravity != null ? builder.menuTextGravity
                : Gravity.CENTER_VERTICAL | Gravity.START;
        menuTextPaddingLeft = builder.menuTextPaddingLeft != null ? builder.menuTextPaddingLeft
                : rtl ? getResources().getDimension(R.dimen.defaultMenuTextPaddingRight)
                : getResources().getDimension(R.dimen.defaultMenuTextPaddingLeft);
        menuTextPaddingRight = builder.menuTextPaddingRight != null ? builder.menuTextPaddingRight
                : rtl ? getResources().getDimension(R.dimen.defaultMenuTextPaddingLeft)
                : getResources().getDimension(R.dimen.defaultMenuTextPaddingRight);

        showMenuRefresh = builder.showMenuRefresh != null ? builder.showMenuRefresh : true;
        stringResRefresh =
                builder.stringResRefresh != null ? builder.stringResRefresh : R.string.refresh;
        showMenuFind = builder.showMenuFind != null ? builder.showMenuFind : false;
        stringResFind = builder.stringResFind != null ? builder.stringResFind : R.string.find;
        showMenuShareVia = builder.showMenuShareVia != null ? builder.showMenuShareVia : true;
        stringResShareVia =
                builder.stringResShareVia != null ? builder.stringResShareVia : R.string.share_via;
        showMenuCopyLink = builder.showMenuCopyLink != null ? builder.showMenuCopyLink : true;
        stringResCopyLink =
                builder.stringResCopyLink != null ? builder.stringResCopyLink : R.string.copy_link;
        showMenuOpenWith = builder.showMenuOpenWith != null ? builder.showMenuOpenWith : true;
        stringResOpenWith =
                builder.stringResOpenWith != null ? builder.stringResOpenWith : R.string.open_with;
        showMenuSavePhoto = builder.showMenuSavePhoto != null ? builder.showMenuSavePhoto : true;
        stringResSavePhoto =
                builder.stringResSavePhoto != null ? builder.stringResSavePhoto : R.string.save_photo;
        showToastPhotoSavedTo = builder.showToastPhotoSavedTo != null ? builder.showToastPhotoSavedTo : true;
        stringResPhotoSavedTo =
                builder.stringResPhotoSavedTo != null ? builder.stringResPhotoSavedTo : R.string.photo_saved_to;
        fileChooserEnabled = builder.fileChooserEnabled != null ? builder.fileChooserEnabled : true;
        stringResFileChooserTitle =
                builder.stringResFileChooserTitle != null ? builder.stringResFileChooserTitle : R.string.file_chooser;

        customMenus = builder.customMenus != null? builder.customMenus: new ArrayList<CustomMenu>(0);

        animationCloseEnter = builder.animationCloseEnter != null ? builder.animationCloseEnter
                : R.anim.modal_activity_close_enter;
        animationCloseExit = builder.animationCloseExit != null ? builder.animationCloseExit
                : R.anim.modal_activity_close_exit;

        backPressToClose = builder.backPressToClose != null ? builder.backPressToClose : false;
        stringResCopiedToClipboard =
                builder.stringResCopiedToClipboard != null ? builder.stringResCopiedToClipboard
                        : R.string.copied_to_clipboard;

        webViewSupportZoom = builder.webViewSupportZoom;
        webViewMediaPlaybackRequiresUserGesture = builder.webViewMediaPlaybackRequiresUserGesture;
        webViewBuiltInZoomControls =
                builder.webViewBuiltInZoomControls != null ? builder.webViewBuiltInZoomControls : false;
        webViewDisplayZoomControls =
                builder.webViewDisplayZoomControls != null ? builder.webViewDisplayZoomControls : false;
        webViewAllowFileAccess =
                builder.webViewAllowFileAccess != null ? builder.webViewAllowFileAccess : true;
        webViewAllowContentAccess = builder.webViewAllowContentAccess;
        webViewLoadWithOverviewMode =
                builder.webViewLoadWithOverviewMode != null ? builder.webViewLoadWithOverviewMode : true;
        webViewSaveFormData = builder.webViewSaveFormData;
        webViewTextZoom = builder.webViewTextZoom;
        webViewUseWideViewPort = builder.webViewUseWideViewPort;
        webViewSupportMultipleWindows = builder.webViewSupportMultipleWindows;
        webViewLayoutAlgorithm = builder.webViewLayoutAlgorithm;
        webViewStandardFontFamily = builder.webViewStandardFontFamily;
        webViewFixedFontFamily = builder.webViewFixedFontFamily;
        webViewSansSerifFontFamily = builder.webViewSansSerifFontFamily;
        webViewSerifFontFamily = builder.webViewSerifFontFamily;
        webViewCursiveFontFamily = builder.webViewCursiveFontFamily;
        webViewFantasyFontFamily = builder.webViewFantasyFontFamily;
        webViewMinimumFontSize = builder.webViewMinimumFontSize;
        webViewMinimumLogicalFontSize = builder.webViewMinimumLogicalFontSize;
        webViewDefaultFontSize = builder.webViewDefaultFontSize;
        webViewDefaultFixedFontSize = builder.webViewDefaultFixedFontSize;
        webViewLoadsImagesAutomatically = builder.webViewLoadsImagesAutomatically;
        webViewBlockNetworkImage = builder.webViewBlockNetworkImage;
        webViewBlockNetworkLoads = builder.webViewBlockNetworkLoads;
        webViewJavaScriptEnabled =
                builder.webViewJavaScriptEnabled != null ? builder.webViewJavaScriptEnabled : true;
        webViewAllowUniversalAccessFromFileURLs = builder.webViewAllowUniversalAccessFromFileURLs;
        webViewAllowFileAccessFromFileURLs = builder.webViewAllowFileAccessFromFileURLs;
        webViewGeolocationDatabasePath = builder.webViewGeolocationDatabasePath;
        webViewAppCacheEnabled =
                builder.webViewAppCacheEnabled != null ? builder.webViewAppCacheEnabled : true;
        webViewAppCachePath = builder.webViewAppCachePath;
        webViewDatabaseEnabled = builder.webViewDatabaseEnabled;
        webViewDomStorageEnabled =
                builder.webViewDomStorageEnabled != null ? builder.webViewDomStorageEnabled : true;
        webViewGeolocationEnabled = builder.webViewGeolocationEnabled;
        webViewJavaScriptCanOpenWindowsAutomatically =
                builder.webViewJavaScriptCanOpenWindowsAutomatically;
        webViewDefaultTextEncodingName = builder.webViewDefaultTextEncodingName;
        webViewUserAgentString = builder.webViewUserAgentString;
        webViewUserAgentAppend = builder.webViewUserAgentAppend;
        webViewNeedInitialFocus = builder.webViewNeedInitialFocus;
        webViewCacheMode = builder.webViewCacheMode;
        webViewMixedContentMode = builder.webViewMixedContentMode;
        webViewOffscreenPreRaster = builder.webViewOffscreenPreRaster;
        webViewAppJumpEnabled = builder.webViewAppJumpEnabled != null ? builder.webViewAppJumpEnabled : true;
        webViewCookieEnabled = builder.webViewCookieEnabled != null ? builder.webViewCookieEnabled : true;
        webViewCameraEnabled = builder.webViewCameraEnabled != null ? builder.webViewCameraEnabled : true;
        webViewAudioEnabled = builder.webViewAudioEnabled != null ? builder.webViewAudioEnabled : true;

        injectJavaScript = builder.injectJavaScript;
        injectJavaScriptMainPage = builder.injectJavaScriptMainPage != null ? builder.injectJavaScriptMainPage : true;
        extraHeadersMainPage = builder.extraHeadersMainPage != null ? builder.extraHeadersMainPage : true;
        injectCookies = builder.injectCookies;

        mimeType = builder.mimeType;
        encoding = builder.encoding;
        data = builder.data;
        url = builder.url;
        extraHeaders = builder.extraHeaders;
    }

    protected void bindViews() {
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        appBar = findViewById(R.id.appBar);
        toolbar = findViewById(R.id.toolbar);
        toolbarLayout = findViewById(R.id.toolbarLayout);

        title = findViewById(R.id.title);
        urlTv = findViewById(R.id.url);

        close = findViewById(R.id.close);
        back = findViewById(R.id.back);
        forward = findViewById(R.id.forward);
        more = findViewById(R.id.more);

        close.setOnClickListener(this);
        back.setOnClickListener(this);
        forward.setOnClickListener(this);
        more.setOnClickListener(this);

        gradient = findViewById(R.id.gradient);
        divider = findViewById(R.id.divider);
        progressBar = findViewById(R.id.progressBar);

        menuLayout = findViewById(R.id.menuLayout);
        shadowLayout = findViewById(R.id.shadowLayout);
        menuBackground = findViewById(R.id.menuBackground);

        menuRefresh = findViewById(R.id.menuRefresh);
        menuRefreshTv = findViewById(R.id.menuRefreshTv);
        menuFind = findViewById(R.id.menuFind);
        menuFindTv = findViewById(R.id.menuFindTv);
        menuShareVia = findViewById(R.id.menuShareVia);
        menuShareViaTv = findViewById(R.id.menuShareViaTv);
        menuCopyLink = findViewById(R.id.menuCopyLink);
        menuCopyLinkTv = findViewById(R.id.menuCopyLinkTv);
        menuOpenWith = findViewById(R.id.menuOpenWith);
        menuOpenWithTv = findViewById(R.id.menuOpenWithTv);

        webLayout = findViewById(R.id.webLayout);
        webView = buildWebView();
        webLayout.addView(webView);
    }

    protected void layoutViews() {
        if (!toolbarVisible) {
            setSupportActionBar(toolbar);
            toolbar.setVisibility(View.GONE);
        }

        { // AppBar
            float toolbarHeight = toolbarVisible? getResources().getDimension(R.dimen.toolbarHeight): 0;
            if (!gradientDivider) toolbarHeight += dividerHeight;
            CoordinatorLayout.LayoutParams params =
                    new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            (int) toolbarHeight);
            appBar.setLayoutParams(params);
            coordinatorLayout.requestLayout();
        }

        { // Toolbar
            float toolbarHeight = toolbarVisible? getResources().getDimension(R.dimen.toolbarHeight): 0;
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) toolbarHeight);
            toolbarLayout.setMinimumHeight((int) toolbarHeight);
            toolbarLayout.setLayoutParams(params);
            coordinatorLayout.requestLayout();
        }

        { // TextViews
            int maxWidth = getMaxWidth();
            title.setMaxWidth(maxWidth);
            urlTv.setMaxWidth(maxWidth);
            requestCenterLayout();
        }

        { // Icons
            updateIcon(close, rtl ? R.drawable.more : R.drawable.close);
            updateIcon(back, R.drawable.back);
            updateIcon(forward, R.drawable.forward);
            updateIcon(more, rtl ? R.drawable.close : R.drawable.more);
        }

        { // Divider
            if (gradientDivider) {
                float toolbarHeight = toolbarVisible? getResources().getDimension(R.dimen.toolbarHeight): 0;
                CoordinatorLayout.LayoutParams params =
                        (CoordinatorLayout.LayoutParams) gradient.getLayoutParams();
                params.setMargins(0, (int) toolbarHeight, 0, 0);
                gradient.setLayoutParams(params);
            }
        }

        { // ProgressBar
            progressBar.setMinimumHeight((int) progressBarHeight);
            CoordinatorLayout.LayoutParams params =
                    new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            (int) progressBarHeight);
            float toolbarHeight = toolbarVisible? getResources().getDimension(R.dimen.toolbarHeight): 0;
            switch (progressBarPosition) {
                case TOP_OF_TOOLBAR:
                    params.setMargins(0, 0, 0, 0);
                    break;
                case BOTTOM_OF_TOOLBAR:
                    params.setMargins(0, (int) toolbarHeight - (int) progressBarHeight, 0, 0);
                    break;
                case TOP_OF_WEBVIEW:
                    params.setMargins(0, (int) toolbarHeight, 0, 0);
                    break;
                case BOTTOM_OF_WEBVIEW:
                    params.setMargins(0, DisplayUtil.getHeight() - (int) progressBarHeight, 0, 0);
                    break;
            }
            progressBar.setLayoutParams(params);
        }

        { // WebLayout
            float toolbarHeight = toolbarVisible? getResources().getDimension(R.dimen.toolbarHeight): 0;
            int statusBarHeight = toolbarVisible? DisplayUtil.getStatusBarHeight(): 0;
            int screenHeight = DisplayUtil.getHeight();
            float webLayoutMinimumHeight = screenHeight - toolbarHeight - statusBarHeight;
            if (showDivider && !gradientDivider) webLayoutMinimumHeight -= dividerHeight;
            webLayout.setMinimumHeight((int) webLayoutMinimumHeight);

            CoordinatorLayout.LayoutParams params =
                    new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0, (int) toolbarHeight, 0, 0);
            webLayout.setLayoutParams(params);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void initializeViews() {
        if (! toolbarVisible) {
            setSupportActionBar(toolbar);
            toolbar.setVisibility(View.GONE);
        }

        { // StatusBar
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(statusBarColor);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Window window = getWindow();
                if (statusBarIconDark) {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                }
            }
        }

        { // Toolbar
            toolbar.setBackgroundColor(toolbarColor);
        }

        { // TextViews
            title.setText(titleDefault);
            title.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
            title.setTypeface(TypefaceHelper.get(this, titleFont));
            title.setTextColor(titleColor);

            urlTv.setVisibility(showUrl ? View.VISIBLE : View.GONE);
            urlTv.setText(UrlParser.getHost(url));
            urlTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, urlSize);
            urlTv.setTypeface(TypefaceHelper.get(this, urlFont));
            urlTv.setTextColor(urlColor);

            requestCenterLayout();
        }

        { // Icons
            close.setBackgroundResource(iconSelector);
            back.setBackgroundResource(iconSelector);
            forward.setBackgroundResource(iconSelector);
            more.setBackgroundResource(iconSelector);

            close.setVisibility(showIconClose ? View.VISIBLE : View.GONE);
            close.setEnabled(!disableIconClose);

            if ((showMenuRefresh
                    || showMenuFind
                    || showMenuShareVia
                    || showMenuCopyLink
                    || showMenuOpenWith
                    || customMenus.size() > 0) && showIconMenu) {
                more.setVisibility(View.VISIBLE);
            } else {
                more.setVisibility(View.GONE);
            }
            more.setEnabled(!disableIconMenu);
        }

        { // Cookie
            if (webViewCookieEnabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
            }
        }

        { // WebView
            webChromeClient = buildWebChromeClient();
            webViewClient = buildWebViewClient();

            webView.setWebChromeClient(webChromeClient);
            webView.setWebViewClient(webViewClient);
            webView.setDownloadListener(downloadListener);

            webView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final WebView.HitTestResult hitTestResult = webView.getHitTestResult();
                    // 如果是图片类型或者是带有图片链接的类型
                    if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                            hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                        if (!showMenuSavePhoto) {
                            return false;
                        }
                        // 弹出保存图片的对话框
                        AlertDialog.Builder builder = new AlertDialog.Builder(AwesomeWebViewActivity.this);
                        final String items[] = {getResources().getString(stringResSavePhoto)};
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PermissionHelper.CheckPermissions(AwesomeWebViewActivity.this, new PermissionHelper.CheckPermissionListener() {
                                    @Override
                                    public void onAllGranted(boolean sync) {
                                        String url = hitTestResult.getExtra();
                                        // 下载图片到本地
                                        DownPicUtil.downPic(url, new DownPicUtil.DownFinishListener() {

                                            @Override
                                            public void getDownPath(String s) {
                                                if (showToastPhotoSavedTo) {
                                                    Toast.makeText(AwesomeWebViewActivity.this, getResources().getString(stringResPhotoSavedTo) + s, Toast.LENGTH_LONG).show();
                                                }
                                                // 最后通知图库更新
                                                getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + s)));
                                            }
                                        });
                                    }

                                    @Override
                                    public void onPartlyGranted(List<String> permissionsDenied, boolean sync) {

                                    }
                                }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return true;
                    }
                    return false;
                }
            });

            webView.setOnTouchListener(new View.OnTouchListener() {
                private float xDown, yDown;
                private long timeDown;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (v == webView && event.getAction() == MotionEvent.ACTION_DOWN) {
                        xDown = event.getX();
                        yDown = event.getY();
                        timeDown = System.currentTimeMillis();
                    } else if (v == webView && event.getAction() == MotionEvent.ACTION_UP){
                        if (Math.abs(xDown - event.getX()) < 50 && Math.abs(yDown - event.getY()) < 50 && System.currentTimeMillis() - timeDown < 200) {
                            // https://stackoverflow.com/a/5125620
                            handler.sendEmptyMessageDelayed(MSG_CLICK_ON_WEBVIEW, 500);
                        }
                    }
                    return false;
                }
            });

            WebSettings settings = webView.getSettings();

            if (webViewSupportZoom != null) settings.setSupportZoom(webViewSupportZoom);
            if (webViewMediaPlaybackRequiresUserGesture != null
                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                settings.setMediaPlaybackRequiresUserGesture(webViewMediaPlaybackRequiresUserGesture);
            }
            if (webViewBuiltInZoomControls != null) {
                settings.setBuiltInZoomControls(webViewBuiltInZoomControls);

                if (webViewBuiltInZoomControls) {
                    // Remove NestedScrollView to enable BuiltInZoomControls
                    ((ViewGroup) webView.getParent()).removeAllViews();
                }
            }
            if (webViewDisplayZoomControls != null
                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                settings.setDisplayZoomControls(webViewDisplayZoomControls);
            }

            if (webViewAllowFileAccess != null) settings.setAllowFileAccess(webViewAllowFileAccess);
            if (webViewAllowContentAccess != null
                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                settings.setAllowContentAccess(webViewAllowContentAccess);
            }
            if (webViewLoadWithOverviewMode != null) {
                settings.setLoadWithOverviewMode(webViewLoadWithOverviewMode);
            }
            if (webViewSaveFormData != null) settings.setSaveFormData(webViewSaveFormData);
            if (webViewTextZoom != null
                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                settings.setTextZoom(webViewTextZoom);
            }
            if (webViewUseWideViewPort != null) settings.setUseWideViewPort(webViewUseWideViewPort);
            if (webViewSupportMultipleWindows != null) {
                settings.setSupportMultipleWindows(webViewSupportMultipleWindows);
            }
            if (webViewLayoutAlgorithm != null) settings.setLayoutAlgorithm(webViewLayoutAlgorithm);
            if (webViewStandardFontFamily != null) {
                settings.setStandardFontFamily(webViewStandardFontFamily);
            }
            if (webViewFixedFontFamily != null) settings.setFixedFontFamily(webViewFixedFontFamily);
            if (webViewSansSerifFontFamily != null) {
                settings.setSansSerifFontFamily(webViewSansSerifFontFamily);
            }
            if (webViewSerifFontFamily != null) settings.setSerifFontFamily(webViewSerifFontFamily);
            if (webViewCursiveFontFamily != null)
                settings.setCursiveFontFamily(webViewCursiveFontFamily);
            if (webViewFantasyFontFamily != null)
                settings.setFantasyFontFamily(webViewFantasyFontFamily);
            if (webViewMinimumFontSize != null) settings.setMinimumFontSize(webViewMinimumFontSize);
            if (webViewMinimumLogicalFontSize != null) {
                settings.setMinimumLogicalFontSize(webViewMinimumLogicalFontSize);
            }
            if (webViewDefaultFontSize != null) settings.setDefaultFontSize(webViewDefaultFontSize);
            if (webViewDefaultFixedFontSize != null) {
                settings.setDefaultFixedFontSize(webViewDefaultFixedFontSize);
            }
            if (webViewLoadsImagesAutomatically != null) {
                settings.setLoadsImagesAutomatically(webViewLoadsImagesAutomatically);
            }
            if (webViewBlockNetworkImage != null)
                settings.setBlockNetworkImage(webViewBlockNetworkImage);
            if (webViewBlockNetworkLoads != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                settings.setBlockNetworkLoads(webViewBlockNetworkLoads);
            }
            if (webViewJavaScriptEnabled != null)
                settings.setJavaScriptEnabled(webViewJavaScriptEnabled);
            if (webViewAllowUniversalAccessFromFileURLs != null
                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                settings.setAllowUniversalAccessFromFileURLs(webViewAllowUniversalAccessFromFileURLs);
            }
            if (webViewAllowFileAccessFromFileURLs != null
                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                settings.setAllowFileAccessFromFileURLs(webViewAllowFileAccessFromFileURLs);
            }
            if (webViewGeolocationDatabasePath != null) {
                settings.setGeolocationDatabasePath(webViewGeolocationDatabasePath);
            }
            if (webViewAppCacheEnabled != null) settings.setAppCacheEnabled(webViewAppCacheEnabled);
            if (webViewAppCachePath != null) settings.setAppCachePath(webViewAppCachePath);
            if (webViewDatabaseEnabled != null) settings.setDatabaseEnabled(webViewDatabaseEnabled);
            if (webViewDomStorageEnabled != null)
                settings.setDomStorageEnabled(webViewDomStorageEnabled);
            if (webViewGeolocationEnabled != null) {
                settings.setGeolocationEnabled(webViewGeolocationEnabled);
            }
            if (webViewJavaScriptCanOpenWindowsAutomatically != null) {
                settings.setJavaScriptCanOpenWindowsAutomatically(
                        webViewJavaScriptCanOpenWindowsAutomatically);
            }
            if (webViewDefaultTextEncodingName != null) {
                settings.setDefaultTextEncodingName(webViewDefaultTextEncodingName);
            }
            if (webViewUserAgentString != null) {
                settings.setUserAgentString(webViewUserAgentAppend? settings.getUserAgentString() + " " + webViewUserAgentString: webViewUserAgentString);
            }
            if (webViewNeedInitialFocus != null)
                settings.setNeedInitialFocus(webViewNeedInitialFocus);
            if (webViewCacheMode != null) settings.setCacheMode(webViewCacheMode);
            if (webViewMixedContentMode != null
                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.setMixedContentMode(webViewMixedContentMode);
            }
            if (webViewOffscreenPreRaster != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                settings.setOffscreenPreRaster(webViewOffscreenPreRaster);
            }

            //            // Other webview options
            //            webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            //            webView.setScrollbarFadingEnabled(false);
            //            //Additional Webview Properties
            //            webView.setSoundEffectsEnabled(true);
            //            webView.setHorizontalFadingEdgeEnabled(false);
            //            webView.setKeepScreenOn(true);
            //            webView.setScrollbarFadingEnabled(true);
            //            webView.setVerticalFadingEdgeEnabled(false);
        }

        { // Divider
            gradient.setVisibility(showDivider && gradientDivider ? View.VISIBLE : View.GONE);
            divider.setVisibility(showDivider && !gradientDivider ? View.VISIBLE : View.GONE);
            if (gradientDivider) {
                int dividerWidth = DisplayUtil.getWidth();
                Bitmap bitmap =
                        BitmapHelper.getGradientBitmap(dividerWidth, (int) dividerHeight, dividerColor);
                BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
                ViewUtil.setBackground(gradient, drawable);

                CoordinatorLayout.LayoutParams params =
                        (CoordinatorLayout.LayoutParams) gradient.getLayoutParams();
                params.height = (int) dividerHeight;
                gradient.setLayoutParams(params);
            } else {
                divider.setBackgroundColor(dividerColor);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) divider.getLayoutParams();
                params.height = (int) dividerHeight;
                divider.setLayoutParams(params);
            }
        }

        { // ProgressBar
            progressBar.setVisibility(showProgressBar ? View.VISIBLE : View.GONE);
            progressBar.getProgressDrawable().setColorFilter(progressBarColor, PorterDuff.Mode.SRC_IN);
            progressBar.setMinimumHeight((int) progressBarHeight);
            CoordinatorLayout.LayoutParams params =
                    new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            (int) progressBarHeight);
            float toolbarHeight = toolbarVisible? getResources().getDimension(R.dimen.toolbarHeight): 0;
            switch (progressBarPosition) {
                case TOP_OF_TOOLBAR:
                    params.setMargins(0, 0, 0, 0);
                    break;
                case BOTTOM_OF_TOOLBAR:
                    params.setMargins(0, (int) toolbarHeight - (int) progressBarHeight, 0, 0);
                    break;
                case TOP_OF_WEBVIEW:
                    params.setMargins(0, (int) toolbarHeight, 0, 0);
                    break;
                case BOTTOM_OF_WEBVIEW:
                    params.setMargins(0, DisplayUtil.getHeight() - (int) progressBarHeight, 0, 0);
                    break;
            }
            progressBar.setLayoutParams(params);
        }

        { // Menu
            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadius(getResources().getDimension(R.dimen.defaultMenuCornerRadius));
            drawable.setColor(menuColor);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                menuBackground.setBackground(drawable);
            } else {
                menuBackground.setBackgroundDrawable(drawable);
            }

            shadowLayout.setShadowColor(menuDropShadowColor);
            shadowLayout.setShadowSize(menuDropShadowSize);

            RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            int margin =
                    (int) (getResources().getDimension(R.dimen.defaultMenuLayoutMargin) - menuDropShadowSize);
            params.setMargins(0, margin, margin, 0);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            params.addRule(rtl ? RelativeLayout.ALIGN_PARENT_LEFT : RelativeLayout.ALIGN_PARENT_RIGHT);
            shadowLayout.setLayoutParams(params);

            menuRefresh.setVisibility(showMenuRefresh ? View.VISIBLE : View.GONE);
            menuRefresh.setBackgroundResource(menuSelector);
            menuRefresh.setGravity(menuTextGravity);
            menuRefreshTv.setText(stringResRefresh);
            menuRefreshTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
            menuRefreshTv.setTypeface(TypefaceHelper.get(this, menuTextFont));
            menuRefreshTv.setTextColor(menuTextColor);
            menuRefreshTv.setPadding((int) menuTextPaddingLeft, 0, (int) menuTextPaddingRight, 0);

            menuFind.setVisibility(showMenuFind ? View.VISIBLE : View.GONE);
            menuFind.setBackgroundResource(menuSelector);
            menuFind.setGravity(menuTextGravity);
            menuFindTv.setText(stringResFind);
            menuFindTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
            menuFindTv.setTypeface(TypefaceHelper.get(this, menuTextFont));
            menuFindTv.setTextColor(menuTextColor);
            menuFindTv.setPadding((int) menuTextPaddingLeft, 0, (int) menuTextPaddingRight, 0);

            menuShareVia.setVisibility(showMenuShareVia ? View.VISIBLE : View.GONE);
            menuShareVia.setBackgroundResource(menuSelector);
            menuShareVia.setGravity(menuTextGravity);
            menuShareViaTv.setText(stringResShareVia);
            menuShareViaTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
            menuShareViaTv.setTypeface(TypefaceHelper.get(this, menuTextFont));
            menuShareViaTv.setTextColor(menuTextColor);
            menuShareViaTv.setPadding((int) menuTextPaddingLeft, 0, (int) menuTextPaddingRight, 0);

            menuCopyLink.setVisibility(showMenuCopyLink ? View.VISIBLE : View.GONE);
            menuCopyLink.setBackgroundResource(menuSelector);
            menuCopyLink.setGravity(menuTextGravity);
            menuCopyLinkTv.setText(stringResCopyLink);
            menuCopyLinkTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
            menuCopyLinkTv.setTypeface(TypefaceHelper.get(this, menuTextFont));
            menuCopyLinkTv.setTextColor(menuTextColor);
            menuCopyLinkTv.setPadding((int) menuTextPaddingLeft, 0, (int) menuTextPaddingRight, 0);

            menuOpenWith.setVisibility(showMenuOpenWith ? View.VISIBLE : View.GONE);
            menuOpenWith.setBackgroundResource(menuSelector);
            menuOpenWith.setGravity(menuTextGravity);
            menuOpenWithTv.setText(stringResOpenWith);
            menuOpenWithTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
            menuOpenWithTv.setTypeface(TypefaceHelper.get(this, menuTextFont));
            menuOpenWithTv.setTextColor(menuTextColor);
            menuOpenWithTv.setPadding((int) menuTextPaddingLeft, 0, (int) menuTextPaddingRight, 0);

            for (final CustomMenu customMenu: customMenus) {
                View customMenuRoot = LayoutInflater.from(AwesomeWebViewActivity.this).inflate(R.layout.view_custom_menu, null, false);
                LinearLayout customMenuLayout = customMenuRoot.findViewById(R.id.customMenuLayout);
                TextView customMenuTv = customMenuLayout.findViewById(R.id.customMenu);
                customMenuLayout.setBackgroundResource(menuSelector);
                customMenuLayout.setGravity(menuTextGravity);
                customMenuTv.setText(customMenu.getTitleRes());
                customMenuTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
                customMenuTv.setTypeface(TypefaceHelper.get(this, menuTextFont));
                customMenuTv.setTextColor(menuTextColor);
                customMenuTv.setPadding((int) menuTextPaddingLeft, 0, (int) menuTextPaddingRight, 0);

                customMenuLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BroadCastManager.onCustomMenuClick(AwesomeWebViewActivity.this, key, customMenu.getCode());
                        hideMenu();
                    }
                });

                menuBackground.addView(customMenuRoot, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }
    }

    protected WebView buildWebView() {
        return new VideoEnabledWebView(this);
    }

    protected WebChromeClient buildWebChromeClient() {
        // Initialize the VideoEnabledWebChromeClient and set event handlers
        View nonVideoLayout = webLayout; // Your own view, read class comments
        ViewGroup videoLayout = (ViewGroup) findViewById(R.id.videoLayout); // Your own view, read class comments
        //noinspection all
        View loadingView = getLayoutInflater().inflate(R.layout.view_loading_video, null); // Your own view, read class comments
        MyWebChromeClient webChromeClient = new MyWebChromeClient(nonVideoLayout, videoLayout, loadingView, webView);
        webChromeClient.setOnToggledFullscreen(new VideoEnabledWebChromeClient.ToggledFullscreenCallback() {
            @Override
            public void toggledFullscreen(boolean fullscreen) {
                // Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
                if (fullscreen) {
                    WindowManager.LayoutParams attrs = getWindow().getAttributes();
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getWindow().setAttributes(attrs);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        //noinspection all
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                    }
                } else {
                    WindowManager.LayoutParams attrs = getWindow().getAttributes();
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getWindow().setAttributes(attrs);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        //noinspection all
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }

            }
        });
        return webChromeClient;
    }

    protected WebViewClient buildWebViewClient() {
        return new MyWebViewClient();
    }

    protected void injectCookie() {
        if (injectCookies != null && injectCookies.size() > 0) {
            // https://blog.csdn.net/juhua2012/article/details/52249720
            CookieSyncManager.createInstance(this);
            CookieSyncManager.getInstance().sync();

            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            for (String url: injectCookies.keySet()) {
                Map<String, String> cookie = injectCookies.get(url);
                if (cookie == null || cookie.size() == 0) {
                    continue;
                }
                for (String key: cookie.keySet()) {
                    String value = cookie.get(key);
                    String cookieStr = key + "=" + value;
                    cookieManager.setCookie(url, cookieStr);
                }
            }
            CookieSyncManager.getInstance().sync();
        }
    }

    protected void load() {
        if (data != null) {
            webView.loadData(data, mimeType, encoding);
        } else if (url != null) {
            if (extraHeaders == null) {
                webView.loadUrl(url);
            } else {
                webView.loadUrl(url, extraHeaders);
            }
        }
    }

    protected int getMaxWidth() {
        if (forward.getVisibility() == View.VISIBLE) {
            return DisplayUtil.getWidth() - UnitConverter.dpToPx(100);
        } else {
            return DisplayUtil.getWidth() - UnitConverter.dpToPx(52);
        }
    }

    protected void updateIcon(ImageButton icon, @DrawableRes int drawableRes) {
        StateListDrawable states = new StateListDrawable();
        {
            Bitmap bitmap = BitmapHelper.getColoredBitmap(this, drawableRes, iconPressedColor);
            BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
            states.addState(new int[]{android.R.attr.state_pressed}, drawable);
        }
        {
            Bitmap bitmap = BitmapHelper.getColoredBitmap(this, drawableRes, iconDisabledColor);
            BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
            states.addState(new int[]{-android.R.attr.state_enabled}, drawable);
        }
        {
            Bitmap bitmap = BitmapHelper.getColoredBitmap(this, drawableRes, iconDefaultColor);
            BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
            states.addState(new int[]{}, drawable);
        }
        icon.setImageDrawable(states);

        //        int[][] states = new int[][]{
        //                new int[]{-android.R.attr.state_enabled}, // disabled
        //                new int[]{android.R.attr.state_pressed}, // pressed
        //                new int[]{}  // default
        //        };
        //
        //        int[] colors = new int[]{
        //                iconDisabledColor,
        //                iconPressedColor,
        //                iconDefaultColor
        //        };
        //
        //        ColorStateList colorStateList = new ColorStateList(states, colors);
        //
        //        Drawable drawable = ContextCompat.getDrawable(this, drawableRes);
        //        if (APILevel.require(21)) {
        //            VectorDrawable vectorDrawable = (VectorDrawable) drawable;
        //            vectorDrawable.setTintList(colorStateList);
        //            icon.setImageDrawable(vectorDrawable);
        //        } else {
        //            VectorDrawableCompat vectorDrawable = (VectorDrawableCompat) drawable;
        //            vectorDrawable.setTintList(colorStateList);
        //            icon.setImageDrawable(vectorDrawable);
        //        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeOptions();

        setContentView(R.layout.awesome_web_view);
        bindViews();
        layoutViews();
        initializeViews();
        injectCookie();
        load();
    }

    @Override
    public void onBackPressed() {
        if (webChromeClient instanceof MyWebChromeClient && ((MyWebChromeClient)webChromeClient).onBackPressed()) {
            return;
        }
        if (menuLayout.getVisibility() == View.VISIBLE) {
            hideMenu();
        } else if (backPressToClose || !webView.canGoBack()) {
            exitActivity();
        } else {
            webView.goBack();
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.close) {
            if (rtl) {
                showMenu();
            } else {
                exitActivity();
            }
        } else if (viewId == R.id.back) {
            if (rtl) {
                webView.goForward();
            } else {
                webView.goBack();
            }
        } else if (viewId == R.id.forward) {
            if (rtl) {
                webView.goBack();
            } else {
                webView.goForward();
            }
        } else if (viewId == R.id.more) {
            if (rtl) {
                exitActivity();
            } else {
                showMenu();
            }
        } else if (viewId == R.id.menuLayout) {
            hideMenu();
        } else if (viewId == R.id.menuRefresh) {
            webView.reload();
            hideMenu();
        } else if (viewId == R.id.menuFind) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                webView.showFindDialog("", true);
            hideMenu();
        } else if (viewId == R.id.menuShareVia) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getString(stringResShareVia)));

            hideMenu();
        } else if (viewId == R.id.menuCopyLink) {
            ClipboardManagerUtil.setText(webView.getUrl());

            Snackbar snackbar = Snackbar.make(coordinatorLayout, getString(stringResCopiedToClipboard),
                    Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(toolbarColor);
            if (snackbarView instanceof ViewGroup) updateChildTextView((ViewGroup) snackbarView);
            snackbar.show();

            hideMenu();
        } else if (viewId == R.id.menuOpenWith) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webView.getUrl()));
            startActivity(browserIntent);

            hideMenu();
        }
    }

    protected void updateChildTextView(ViewGroup viewGroup) {
        if (viewGroup == null || viewGroup.getChildCount() == 0) return;

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setTextColor(titleColor);
                textView.setTypeface(TypefaceHelper.get(this, titleFont));
                textView.setLineSpacing(0, 1.1f);
                textView.setIncludeFontPadding(false);
            }

            if (view instanceof ViewGroup) updateChildTextView((ViewGroup) view);
        }
    }

    protected void showMenu() {
        menuLayout.setVisibility(View.VISIBLE);
        Animation popupAnim = AnimationUtils.loadAnimation(this, R.anim.popup_flyout_show);
        shadowLayout.startAnimation(popupAnim);
    }

    protected void hideMenu() {
        Animation popupAnim = AnimationUtils.loadAnimation(this, R.anim.popup_flyout_hide);
        shadowLayout.startAnimation(popupAnim);
        popupAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                menuLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    protected void exitActivity() {
        super.onBackPressed();
        overridePendingTransition(animationCloseEnter, animationCloseExit);
    }

    protected void requestCenterLayout() {
        int maxWidth;
        if (webView.canGoBack() || webView.canGoForward()) {
            maxWidth = DisplayUtil.getWidth() - UnitConverter.dpToPx(48) * 4;
        } else {
            maxWidth = DisplayUtil.getWidth() - UnitConverter.dpToPx(48) * 2;
        }

        title.setMaxWidth(maxWidth);
        urlTv.setMaxWidth(maxWidth);
        title.requestLayout();
        urlTv.requestLayout();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutViews();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutViews();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BroadCastManager.unregister(AwesomeWebViewActivity.this, key);
        if (webView == null) return;
        if (APILevel.require(11)) webView.onPause();
        destroyWebView();
    }

    // Wait for zoom control to fade away
    // https://code.google.com/p/android/issues/detail?id=15694
    // http://stackoverflow.com/a/5966151/1797648
    protected void destroyWebView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (webView != null) webView.destroy();
            }
        }, ViewConfiguration.getZoomControlsTimeout() + 1000L);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == MSG_CLICK_ON_URL){
            handler.removeMessages(MSG_CLICK_ON_WEBVIEW);
            return true;
        }
        if (msg.what == MSG_CLICK_ON_WEBVIEW){
            final WebView.HitTestResult hitTestResult = webView.getHitTestResult();
            // 如果是图片类型或者是带有图片链接的类型
            if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                    hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                BroadCastManager.onClickImage(AwesomeWebViewActivity.this, key, hitTestResult.getExtra());
            }
            return true;
        }
        return false;
    }

    public class MyWebChromeClient extends VideoEnabledWebChromeClient {

        public MyWebChromeClient(View activityNonVideoView, ViewGroup activityVideoView, View loadingView, WebView webView) {
            super(activityNonVideoView, activityVideoView, loadingView, webView);
        }

        @Override
        public void onProgressChanged(WebView view, int progress) {
            BroadCastManager.onProgressChanged(AwesomeWebViewActivity.this, key, progress);

            if (progress == 100) progress = 0;
            progressBar.setProgress(progress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            BroadCastManager.onReceivedTitle(AwesomeWebViewActivity.this, key, title);
        }

        @Override
        public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
            BroadCastManager.onReceivedTouchIconUrl(AwesomeWebViewActivity.this, key, url, precomposed);
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
            PermissionHelper.CheckPermissions(AwesomeWebViewActivity.this, new PermissionHelper.CheckPermissionListener() {
                @Override
                public void onAllGranted(boolean sync) {
                    callback.invoke(origin, true, true);
                }

                @Override
                public void onPartlyGranted(List<String> permissionsDenied, boolean sync) {
                    callback.invoke(origin, false, false);
                }
            }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onPermissionRequest(final PermissionRequest request) {
            for (String res : request.getResources()) {
                if (res.equals(PermissionRequest.RESOURCE_AUDIO_CAPTURE)) {
                    if (!webViewAudioEnabled) {
                        request.deny();
                        return;
                    }
                }
                if (res.equals(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
                    if (!webViewCameraEnabled) {
                        request.deny();
                        return;
                    }
                }
            }

            PermissionHelper.CheckPermissions(AwesomeWebViewActivity.this, new PermissionHelper.CheckPermissionListener() {
                @Override
                public void onAllGranted(boolean sync) {
                    request.grant(request.getResources());
                }

                @Override
                public void onPartlyGranted(List<String> permissionsDenied, boolean sync) {
                    request.deny();
                }
            }, parsePermission(request.getResources()));
        }

        //Handling input[type="file"] requests for android API 16+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            handler.sendEmptyMessage(MSG_CLICK_ON_URL);

            if (!fileChooserEnabled) {
                uploadMsg.onReceiveValue(null);
                return;
            }
            filePickerFileMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType(acceptType);
            startActivityForResult(Intent.createChooser(i, getResources().getString(stringResFileChooserTitle)), FILE_PICKER_REQ_CODE);
        }

        //Handling input[type="file"] requests for android API 21+
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            handler.sendEmptyMessage(MSG_CLICK_ON_URL);

            if (!fileChooserEnabled) {
                filePathCallback.onReceiveValue(null);
                return true;
            }
            getFile();
            if (filePickerFilePath != null) {
                filePickerFilePath.onReceiveValue(null);
            }
            filePickerFilePath = filePathCallback;
            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType(FILE_TYPE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                contentSelectionIntent.putExtra(Intent.EXTRA_MIME_TYPES, fileChooserParams.getAcceptTypes());
            }
            Intent[] intentArray;
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(AwesomeWebViewActivity.this.getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImage();
                } catch (IOException ex) {
//                            Log.e("", "Image file creation failed", ex);
                }
                if (photoFile != null) {
                    filePickerCamMessage = "file:" + photoFile.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                } else {
                    takePictureIntent = null;
                }
            }
            if (takePictureIntent != null) {
                intentArray = new Intent[]{takePictureIntent};
            } else {
                intentArray = new Intent[0];
            }
            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, getResources().getString(stringResFileChooserTitle));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
            startActivityForResult(chooserIntent, FILE_PICKER_REQ_CODE);
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            Uri[] results = null;
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == FILE_PICKER_REQ_CODE) {
                    if (null == filePickerFilePath) {
                        return;
                    }
                    if (intent == null || intent.getDataString() == null) {
                        if (filePickerCamMessage != null) {
                            results = new Uri[]{Uri.parse(filePickerCamMessage)};
                        }
                    } else {
                        String dataString = intent.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }
                }
            }
            filePickerFilePath.onReceiveValue(results);
            filePickerFilePath = null;
        } else {
            if (requestCode == FILE_PICKER_REQ_CODE) {
                if (null == filePickerFileMessage) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                filePickerFileMessage.onReceiveValue(result);
                filePickerFileMessage = null;
            }
        }
    }

    //Creating image file for upload
    protected File createImage() throws IOException {
        @SuppressLint("SimpleDateFormat")
        String file_name = new SimpleDateFormat("yyyy_mm_ss").format(new Date());
        String new_name = "file_" + file_name + "_";
        File sd_directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(new_name, ".jpg", sd_directory);
    }

    //Checking permission for storage and camera for writing and uploading images
    protected void getFile() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

        PermissionHelper.CheckPermissions(AwesomeWebViewActivity.this, new PermissionHelper.CheckPermissionListener() {
            @Override
            public void onAllGranted(boolean sync) {

            }

            @Override
            public void onPartlyGranted(List<String> permissionsDenied, boolean sync) {

            }
        }, perms);
    }

    public class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            BroadCastManager.onPageStarted(AwesomeWebViewActivity.this, key, url);
            if (!url.contains("docs.google.com") && url.endsWith(".pdf")) {
                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            BroadCastManager.onPageFinished(AwesomeWebViewActivity.this, key, url);

            if (updateTitleFromHtml) title.setText(view.getTitle());
            urlTv.setText(UrlParser.getHost(url));
            requestCenterLayout();

            if (view.canGoBack() || view.canGoForward()) {
                back.setVisibility(showIconBack ? View.VISIBLE : View.GONE);
                forward.setVisibility(showIconForward ? View.VISIBLE : View.GONE);
                back.setEnabled(!disableIconBack && (rtl ? view.canGoForward() : view.canGoBack()));
                forward.setEnabled(!disableIconForward && (rtl ? view.canGoBack() : view.canGoForward()));
            } else {
                back.setVisibility(View.GONE);
                forward.setVisibility(View.GONE);
            }

            if (injectJavaScript != null) {
                if (injectJavaScriptMainPage && !url.equals(AwesomeWebViewActivity.this.url)) {
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    webView.evaluateJavascript(injectJavaScript, null);
                } else {
                    webView.loadUrl(injectJavaScript);
                }
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            handler.sendEmptyMessage(MSG_CLICK_ON_URL);

            if (url.endsWith(".mp4")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(url), "video/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
                // If we return true, onPageStarted, onPageFinished won't be called.
                return true;
            } else if (url.startsWith("tel:") || url.startsWith("sms:") || url.startsWith("smsto:") || url
                    .startsWith("mms:") || url.startsWith("mmsto:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
                return true; // If we return true, onPageStarted, onPageFinished won't be called.
            }
            /*******************************************************
             * Added in support for mailto:
             *******************************************************/
            else if (url.startsWith("mailto:")) {

                MailTo mt = MailTo.parse(url);

                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                emailIntent.setType("text/html");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{mt.getTo()});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, mt.getSubject());
                emailIntent.putExtra(Intent.EXTRA_CC, mt.getCc());
                emailIntent.putExtra(Intent.EXTRA_TEXT, mt.getBody());

                startActivity(emailIntent);

                return true;
            } else if (url.startsWith("http") || url.startsWith("https") || url.startsWith("ftp")) {
                if (extraHeaders == null || extraHeadersMainPage && !url.equals(AwesomeWebViewActivity.this.url)) {
                    return super.shouldOverrideUrlLoading(view, url);
                } else {
                    view.loadUrl(url, extraHeaders);
                    return true;
                }
            } else {
                if (webViewAppJumpEnabled) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        view.getContext().startActivity(intent);
                        return true; // If we return true, onPageStarted, onPageFinished won't be called.
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        return true;
                    }
                } else {
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            BroadCastManager.onLoadResource(AwesomeWebViewActivity.this, key, url);
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            BroadCastManager.onPageCommitVisible(AwesomeWebViewActivity.this, key, url);
        }
    }

    protected String[] parsePermission(String[] resource) {
        List<String> permissions = new ArrayList<>();
        for (String res : resource) {
            if (res.equals(PermissionRequest.RESOURCE_AUDIO_CAPTURE)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (res.equals(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
                permissions.add(Manifest.permission.CAMERA);
            }
        }

        String[] result = new String[permissions.size()];
        for (int i = 0; i < permissions.size(); i++) {
            result[i] = permissions.get(i);
        }
        return result;
    }
}
