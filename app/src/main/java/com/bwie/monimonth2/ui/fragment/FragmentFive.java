package com.bwie.monimonth2.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.monimonth2.R;
import com.bwie.monimonth2.contract.UserContract;
import com.bwie.monimonth2.entity.HeadEntity;
import com.bwie.monimonth2.entity.UserEntity;
import com.bwie.monimonth2.presenter.UserPresenter;
import com.bwie.monimonth2.utils.TakePhotoUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hao.base.base.mvp.BaseMvpFragment;
import com.hao.base.base.mvp.BasePresenter;
import com.longsh.optionframelibrary.OptionBottomDialog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

public class FragmentFive extends BaseMvpFragment<UserContract.IUserModel, UserContract.UserPresenter> implements UserContract.IUserView {
    @BindView(R.id.settx)
    SimpleDraweeView settx;
    @BindView(R.id.tel)
    TextView tel;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.sex_tv)
    TextView sexTv;
    @BindView(R.id.birthday_tv)
    TextView birthdayTv;
    Unbinder unbinder;
    private UserEntity.DataBean data;
    private String path = "";

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 10002;
    private static final int PICK_ACTIVITY_REQUEST_CODE = 10003;
    private static final int CROP_ACTIVITY_REQUEST_CODE = 10008;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 10010;


    private String imageFilePath; //拍照和选择照片后图片路径
    private File cropFile; //裁剪后的图片文件
    private Uri pickPhotoImageUri; //API22以下相册选择图片uri
    private AlertDialog.Builder builder;

    @Override
    protected int setLayoutId() {
        return R.layout.fragmentfive_layout;
    }

    @Override
    public BasePresenter initPresenter() {
        return new UserPresenter();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    protected void initData() {
        super.initData();
        settx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });
        path = Environment.getExternalStorageDirectory().getPath() + System.currentTimeMillis() + ".jpg";
        presenter.getUser("17224");
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void fail(String Msg) {

    }

    @Override
    public void onSuccess(UserEntity userEntity) {
        Toast.makeText(mActivity, userEntity.toString(), Toast.LENGTH_SHORT).show();

        data = userEntity.getData();
        settx.setImageURI("https://img2.woyaogexing.com/2018/09/21/1d711bb4add56b29!400x400_big.jpg");
        tel.setText(data.getMobile());
        birthdayTv.setText(data.getAge() + "");
        name.setText(data.getNickname());
        sexTv.setText("男");

        birthdayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        birthdayTv.setText(String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth));
                    }
                }, 2000, 1, 2).show();
            }
        });


    }

    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(getActivity()
        );
        rxPermission
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.d("aaa", permission.name + " is granted.");

                            /*List<String> stringList = new ArrayList<String>();
                            stringList.add("拍照");
                            stringList.add("从相册选择");
                            final OptionBottomDialog optionBottomDialog = new OptionBottomDialog(getActivity(), stringList);
                            optionBottomDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    switch (position){
                                        case 0:

                                            takePhoto();

                                            break;
                                        case 1:

                                            pickPhoto();

                                            break;

                                    }

                                    optionBottomDialog.dismiss();
                                }
                            });*/


                            builder = new AlertDialog.Builder(getActivity());

                            builder.setIcon(R.mipmap.ic_launcher)
                                    .setTitle("选择图片：")
                                    .setItems(
                                            new String[]{"相机", "相册"},
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                                    int which) {
                                                    // TODO Auto-generated method stub

                                                    switch (which) {
                                                        case 0:
                                                            takePhoto();
                                                            break;
                                                        case 1:
                                                            pickPhoto();
                                                            break;

                                                        default:
                                                            break;
                                                    }

                                                }

                                            });

                            builder.create().show();

                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.d("aaa", permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.d("aaa", permission.name + " is denied.");
                        }
                    }
                });


    }

    //拍照获取图片
    private void takePhoto() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File imageFile = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
            if (!imageFile.getParentFile().exists()) imageFile.getParentFile().mkdirs();
            imageFilePath = imageFile.getPath();
            //兼容性判断
            Uri imageUri;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                imageUri = TakePhotoUtils.file2Uri(getActivity(), imageFile);
            } else {
                imageUri = Uri.fromFile(imageFile);
            }
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI

            List<ResolveInfo> resInfoList = getActivity().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                getActivity().grantUriPermission(packageName, imageUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    //从相册中取图片
    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onFailure(String Msg) {
        Toast.makeText(mActivity, Msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpSuccess(HeadEntity headEntity) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                //拍照
                if (resultCode == Activity.RESULT_OK) {
                    crop(false);
                }
                break;

            case CROP_ACTIVITY_REQUEST_CODE:
                //裁剪完成
                if (data != null) {
                    Bitmap bitmap;
                    try {
                        bitmap = BitmapFactory.decodeFile(cropFile.getPath());
                        settx.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case PICK_ACTIVITY_REQUEST_CODE:
                //从相册选择
                if (data != null && resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                        imageFilePath = TakePhotoUtils.getPathByUri4kitkat(getActivity(), data.getData());
                    } else {
                        pickPhotoImageUri = data.getData();
                    }

                    crop(true);
                }
                break;
        }
    }

    /**
     * 裁剪
     *
     * @param isPick 是否是从相册选择
     */
    private void crop(boolean isPick) {
        cropFile = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!cropFile.getParentFile().exists()) cropFile.getParentFile().mkdirs();
        Uri outputUri, imageUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            outputUri = TakePhotoUtils.file2Uri(getActivity(), cropFile);
            imageUri = TakePhotoUtils.file2Uri(getActivity(), new File(imageFilePath));
        } else {
            outputUri = Uri.fromFile(cropFile);
            imageUri = isPick ? pickPhotoImageUri : Uri.fromFile(new File(imageFilePath));
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection

        //授予"相机"保存文件的权限 针对API24+
        List<ResolveInfo> resInfoList = getActivity().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            getActivity().grantUriPermission(packageName, outputUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(intent, CROP_ACTIVITY_REQUEST_CODE);
    }
}
