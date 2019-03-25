package app.xtoolwallpaper.com.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import app.xtoolwallpaper.com.myapplication.R;
import app.xtoolwallpaper.com.myapplication.activity.AboutActivity;
import app.xtoolwallpaper.com.myapplication.activity.WebViewActivity;
import app.xtoolwallpaper.com.myapplication.api.C;
import app.xtoolwallpaper.com.myapplication.base.bean.MessageEvent;
import app.xtoolwallpaper.com.myapplication.utils.AlertDialog;
import app.xtoolwallpaper.com.myapplication.utils.CleanMessageUtils;
import app.xtoolwallpaper.com.myapplication.utils.FileUtil;

import static com.blankj.utilcode.util.SnackbarUtils.dismiss;

public class SettingFragment extends Fragment implements View.OnClickListener {
    RelativeLayout mRelSetting;
    RelativeLayout mRelDeletePic;
    RelativeLayout mRelSettingPrivacy;
    RelativeLayout mRelSettingTermUse;
    TextView mShowMemory;
    File folder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mRelSetting = view.findViewById(R.id.rl_about);
        mRelSetting.setOnClickListener(this);
        mRelDeletePic = view.findViewById(R.id.rl_delete_data);
        mRelDeletePic.setOnClickListener(this::onClick);
        mShowMemory = view.findViewById(R.id.tv_show);
        mRelSettingPrivacy = view.findViewById(R.id.rl_layout_privacy);
        mRelSettingPrivacy.setOnClickListener(this);
        mRelSettingTermUse = view.findViewById(R.id.rl_layout_term_of_use);
        mRelSettingTermUse.setOnClickListener(this);
        folder = FileUtil.getSavedShotsFolder();
        if (folder != null) {
            String mShotPhotosPath = folder.getAbsolutePath();
            final long total =
                    getTotalSizeOfFilesInDir(new File(mShotPhotosPath));
            try {

                mShowMemory.setText(CleanMessageUtils.getFormatSize(CleanMessageUtils.getFolderSize(folder)));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this))//加上判断
            EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_about:
                AboutActivity.launch(getActivity());
                break;
            case R.id.rl_layout_privacy:
                WebViewActivity.start(getActivity(), C.URL.policy.VALUE_STRING_PRIVACY_POLICY_URL, "Privacy");
                break;
            case R.id.rl_layout_term_of_use:
                WebViewActivity.start(getActivity(), C.URL.termofuser.VALUE_STRING_TERM_OF_USE, "Term of use");
                break;
            case R.id.rl_delete_data:
                try {
                    if (!CleanMessageUtils.getFormatSize(CleanMessageUtils.getFolderSize(folder)).equals("0.00KB"))
                        ConfirmDialog();
                    else {
                        ToastUtils.showLong(R.string.Settingfragment_no_clean);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getMsgCode() == 0x16) {
            if (folder != null) {
                try {
                    mShowMemory.setText(CleanMessageUtils.getFormatSize(CleanMessageUtils.getFolderSize(folder)));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void ConfirmDialog() {
        AlertDialog alertDialog = new AlertDialog(getActivity(), getString(R.string.Detail_SuccessWallpaper_Tips_Text),
                getString(R.string.setting_fragment_confirm_content_text),
                getString(R.string.setting_fragment_confirm_button), getString(R.string.setting_fragment_cancel_button), new AlertDialog.OnDialogButtonClickListener() {
            @Override
            public void onDialogButtonClick(boolean isPositive) {
                if (isPositive) {
                    FileUtil.deleteDirectory(folder.getAbsolutePath());
                    try {
                        mShowMemory.setText(CleanMessageUtils.getFormatSize(CleanMessageUtils.getFolderSize(folder)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dismiss();
                    clearDialog();
                } else {
                    dismiss();
                }
            }
        });
        alertDialog.show();
    }

    public void clearDialog() {
        AlertDialog mAlertDialog = new AlertDialog(getActivity(), getString(R.string.Detail_SuccessWallpaper_Tips_Text),
                getString(R.string.setting_fragment_clean_content_text),
                getString(R.string.setting_fragment_confirm_button), "", new AlertDialog.OnDialogButtonClickListener() {
            @Override
            public void onDialogButtonClick(boolean isPositive) {
                if (isPositive) {
                    dismiss();
                }
            }
        });
        mAlertDialog.show();
    }

    // 递归方式 计算文件的大小
    private long getTotalSizeOfFilesInDir(final File file) {
        if (file.isFile())
            return file.length();
        final File[] children = file.listFiles();
        long total = 0;
        if (children != null)
            for (final File child : children)
                total += getTotalSizeOfFilesInDir(child);
        return total;
    }
}
