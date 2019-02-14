package com.wuadam.awesomewebview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.wuadam.awesomewebview.AwesomeWebView;
import com.wuadam.awesomewebview.listeners.WebViewListener;
import com.wuadam.awesomewebview.objects.CustomMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.functionPlayground) {
            Map<String, String> headers = new HashMap<>();
            headers.put("Referer", "https://github.com/hzw1199");

            List<CustomMenu> customMenus = new ArrayList<>();
            customMenus.add(new CustomMenu(R.string.menu_custom_0, "custom_menu_0"));

            Map<String, Map<String, String>> cookies = new HashMap<>();
            Map<String, String> cookie_0 = new HashMap<>();
            cookie_0.put("key_0", "value_0");
            cookie_0.put("key_1", "value_1");
            cookies.put("http://www.html-kit.com/tools/cookietester/", cookie_0);

            new AwesomeWebView.Builder(this)
                    .webViewGeolocationEnabled(true)
                    .webViewCookieEnabled(true)
                    .webViewAppJumpEnabled(true)
                    .webViewCameraEnabled(true)
                    .webViewAudioEnabled(true)
                    .showMenuSavePhoto(true)
                    .stringResSavePhoto(R.string.save_photo)
                    .showToastPhotoSavedOrFailed(true)
                    .stringResPhotoSavedTo(R.string.photo_saved_to)
                    .setStringResPhotoSaveFailed(R.string.photo_save_failed)
                    .fileChooserEnabled(true)
                    .setHeader(headers)
                    .headersMainPage(false)
                    .injectJavaScript("javascript: alert(\"This is js inject\")")
                    .injectJavaScriptMainPage(true)
                    .webViewUserAgentString("AwesomeWebView")
                    .webViewUserAgentAppend(true)
                    .statusBarColorRes(R.color.finestWhite)
                    .statusBarIconDark(true)
                    .customMenus(customMenus)
                    .addCustomMenu(new CustomMenu(R.string.menu_custom_1, "custom_menu_1"))
                    .injectCookies(cookies)
                    .addWebViewListener(new WebViewListener() {
                        @Override
                        public void onCustomMenuClick(String menuCode) {
                            if (menuCode.equals("custom_menu_0")) {
                                Toast.makeText(MainActivity.this, "Custom Menu 0 Clicked", Toast.LENGTH_SHORT).show();
                            } else if (menuCode.equals("custom_menu_1")) {
                                Toast.makeText(MainActivity.this, "Custom Menu 1 Clicked", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onClickImage(String imageUrl) {
                            Toast.makeText(MainActivity.this, "Image clicked: " + imageUrl, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show("file:///android_asset/test.html");
        } else if (view.getId() == R.id.redTheme) {
            //            Intent intent = new Intent(this, WebViewActivity.class);
            //            startActivity(intent);
            new AwesomeWebView.Builder(this).theme(R.style.RedTheme)
                    .titleDefault("Bless This Stuff")
                    .webViewBuiltInZoomControls(true)
                    .webViewDisplayZoomControls(true)
                    .dividerHeight(0)
                    .gradientDivider(false)
                    .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit,
                            R.anim.activity_close_enter, R.anim.activity_close_exit)
                    .show("http://www.blessthisstuff.com");
        } else if (view.getId() == R.id.blueTheme) {
            new AwesomeWebView.Builder(this).theme(R.style.FinestWebViewTheme)
                    .titleDefault("Vimeo")
                    .showUrl(false)
                    .statusBarColorRes(R.color.bluePrimaryDark)
                    .toolbarColorRes(R.color.bluePrimary)
                    .titleColorRes(R.color.finestWhite)
                    .urlColorRes(R.color.bluePrimaryLight)
                    .iconDefaultColorRes(R.color.finestWhite)
                    .progressBarColorRes(R.color.finestWhite)
                    .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                    .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                    .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                    .menuSelector(R.drawable.selector_light_theme)
                    .menuTextGravity(Gravity.CENTER)
                    .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
                    .dividerHeight(0)
                    .gradientDivider(false)
                    .setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down)
                    .show("http://example.com");
        } else if (view.getId() == R.id.blackTheme) {
            new AwesomeWebView.Builder(this).theme(R.style.FinestWebViewTheme)
                    .titleDefault("Dribbble")
                    .statusBarColorRes(R.color.blackPrimaryDark)
                    .toolbarColorRes(R.color.blackPrimary)
                    .titleColorRes(R.color.finestWhite)
                    .urlColorRes(R.color.blackPrimaryLight)
                    .iconDefaultColorRes(R.color.finestWhite)
                    .progressBarColorRes(R.color.finestWhite)
                    .menuSelector(R.drawable.selector_light_theme)
                    .menuTextGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT)
                    .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
                    .dividerHeight(0)
                    .gradientDivider(false)
                    //                    .setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down)
                    .setCustomAnimations(R.anim.slide_left_in, R.anim.hold, R.anim.hold,
                            R.anim.slide_right_out)
                    //                    .setCustomAnimations(R.anim.fade_in_fast, R.anim.fade_out_medium, R.anim.fade_in_medium, R.anim.fade_out_fast)
                    .disableIconBack(true)
                    .disableIconClose(true)
                    .disableIconForward(true)
                    .disableIconMenu(true)
                    .show("https://dribbble.com");
        }
    }
}
