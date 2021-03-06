package com.jhs.inews.ui.user.modify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jhs.inews.R;
import com.jhs.inews.base.BaseFragment;
import com.jhs.inews.entity.User;
import com.jhs.inews.view.UploadDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by dds on 2016/4/24.
 */
public class NickNameFragment extends BaseFragment {
    @Bind(R.id.tv_nickname)
    EditText tvNickname;
    private UploadDialog uploadDialog;

    @Override
    protected boolean onBackPressed() {
        return false;
    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_nickname, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onInitloadData() {
        //初始化数据
        User user = User.getCurrentUser(getActivity(), User.class);
        tvNickname.setText(user.getNickname());
        tvNickname.setSelection(tvNickname.getText().length());


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_modify, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_save:
                uploadDialog=new UploadDialog(getActivity());
                //更改用户昵称
                if (tvNickname.getText() != null && tvNickname.getText().toString() != "") {
                    uploadDialog.show();
                    User newUser = new User();
                    newUser.setNickname(tvNickname.getText().toString());
                    User bmobUser = User.getCurrentUser(getActivity(), User.class);
                    newUser.update(getActivity(), bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            callbackToActivity(1, tvNickname.getText().toString());
                            uploadDialog.dismiss();

                        }

                        @Override
                        public void onFailure(int i, String s) {
                            callbackToActivity(2, null);
                            uploadDialog.dismiss();


                        }
                    });

                }


                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
