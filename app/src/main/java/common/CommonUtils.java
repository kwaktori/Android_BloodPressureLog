package common;

import android.app.Activity;
import android.os.Build;
import android.view.View;

public class CommonUtils {

    public static void setSystembarColor(Activity activity, int customBgColor, boolean isTextDarkColor) {

        if (activity == null) {
            return;
        }

        // 상태바 색상 변경은 5.0 부터 지원되지만 상태바 내부 아이이콘 색상변경은 6.0부터 되므로 기능을 6.0 부터 적용되도록 설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.getWindow().getStatusBarColor() != customBgColor) {
                activity.getWindow().setStatusBarColor(customBgColor);
            }

            /**
             * 상태바 배경 색상을 밝은색으로 적용 시, 상태바내 텍스트 색상도 흰색이기 때문에 텍스트가 잘 보이지 않음
             * 그래서 SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 속성을 적용해 텍스트 색상을 어두운 색으로 변경.
             */
            if (isTextDarkColor) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(0);
            }
        }
    }
}
