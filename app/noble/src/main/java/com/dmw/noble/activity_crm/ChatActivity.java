package com.dmw.noble.activity_crm;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.activity.ImageActivity;
import com.dmw.noble.activity.ViewPdfActivity;
import com.dmw.noble.adaptor.crm.ChatAdapter;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.CrmManager;
import com.dmw.noble.model_crm.ChatList;
import com.dmw.noble.model_crm.ClaimView;
import com.dmw.noble.model_crm.Message;
import com.dmw.noble.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import in.gauriinfotech.commons.Commons;

public class ChatActivity extends AbstractActivity implements onRequestCompleteCallBackListener,
        ChatAdapter.OnAttachmentListener, ChatAdapter.OnAttachmentPDFListener {

    final CharSequence[] items = {"Take Photo", "Photos", "Documents"};
    File photoFile = null;
    String userId, userType, mCurrentPhotoPath, otherDoc, currentId, currentType, isAttachment, message,
            refNo, refId, chatType;

    EditText edtChatMsg;
    RecyclerView rcChat;
    Context mContext;
    ProgressDialog progressdialog;
    ClaimView claimObj;
    Bundle mBundle;
    ArrayList<Message> chatList = new ArrayList<>();
    ChatAdapter chatAdapter;
    static boolean active = false;

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    boolean isCameraPermitted = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        mBundle = getIntent().getExtras();
        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait...");
        SharedPreferences preferences = getSharedPreferences(String.valueOf(R.string.app_name),
                MODE_PRIVATE);

        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        edtChatMsg = findViewById(R.id.edtChatMsg);
        rcChat = findViewById(R.id.rcChat);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rcChat.setLayoutManager(mLayoutManager);

        chatAdapter = new ChatAdapter(mContext, chatList);
        rcChat.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();

        rcChat.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (bottom < oldBottom)
                mLayoutManager.smoothScrollToPosition(rcChat, null, chatAdapter.getItemCount());
        });

        if (mBundle != null) {
            refNo = mBundle.getString(AppUtils.CLAIM_NO);
            chatType = mBundle.getString(AppUtils.CHAT_TYPE);
            refId = mBundle.getString(AppUtils.CLAIM_ID);

            String manager = mBundle.getString(AppUtils.CLAIM_MANAGER);
            if (!TextUtils.isEmpty(manager))
                this.setTitle(manager);
            if (!TextUtils.isEmpty(refNo) && chatType.equalsIgnoreCase("claim"))
                getClaimData();

            getClaimChat();
        }

        //Pusher used for message triggered real time
        PusherOptions options = new PusherOptions();
        options.setCluster("ap2");

        Pusher pusher = new Pusher("ab292ed447c156572d03", options);
        pusher.connect();
        Channel channel = pusher.subscribe("events-channel");
        channel.bind("new-message", (channelName, eventName, data) -> {
            System.out.println(data);
            try {
                JSONObject jsonObj = new JSONObject(data);
                JSONArray ja_data = jsonObj.getJSONArray("Message");
                int length = jsonObj.length();

                for (int i = 0; i < length; i++) {
                    JSONObject jObj = ja_data.getJSONObject(i);
                    String senderId = jObj.getString("Sender_Id");
                    String type = jObj.getString("Type");
                    if (senderId.equals(userId) && type.equals(chatType) && active) {
                        getClaimChat();
                    }
                }
            } catch (Throwable t) {
                if (active)
                    getClaimChat();
                Log.e(getString(R.string.app_name), "Could not parse malformed JSON: ");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSendClick(View view) {
        message = edtChatMsg.getText().toString();
        otherDoc = "";
        isAttachment = "No";
        if (!TextUtils.isEmpty(message))
            sendChat();

        edtChatMsg.setText("");
    }

    public void onUploadClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Upload File!");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                onDialogClick(dialog, item);
            }
        });
        builder.show();
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            isCameraPermitted = false;
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
    }

    private void onDialogClick(DialogInterface dialog, int item) {
        switch (item) {
            case 0:
                if (isCameraPermitted)
                    dispatchTakePictureIntent();
                else AppUtils.showToast(mContext, "Camera Permission Denied");
                break;
            case 1:
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, AppUtils.REQUEST_CODE_GALLERY);
                break;
            case 2:
                String path = String.valueOf(Environment.getExternalStorageDirectory());
                File file = new File(path);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setDataAndType(Uri.fromFile(file), "*/*");

                try {
                    startActivityForResult(Intent.createChooser(intent, "Select File"), AppUtils.REQUEST_CODE_FILES);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                dialog.dismiss();
                break;
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.dmw.noble.provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(takePictureIntent, AppUtils.REQUEST_CODE_CAMERA);
            }
        }
    }

    private File createImageFile() throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image;
        image = File.createTempFile("IMG_", ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        image.renameTo(storageDir);
        return image;
    }

    public void onUploadFile(View view) {
        String path = String.valueOf(Environment.getExternalStorageDirectory());
        File file = new File(path);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(Uri.fromFile(file), "*/*");

        try {
            startActivityForResult(Intent.createChooser(intent, "Select File"), AppUtils.REQUEST_CODE_FILES);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getRealPathFromURIPdf(Context context, Uri uri) {

        String path = null;
        if (isDownloadsDocument(uri) || isExternalStorageDocument(uri)) {
            String[] proj = {MediaStore.MediaColumns.DATA};
            Cursor cursor = getContentResolver().query(uri, proj, null, null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                path = cursor.getString(column_index);
                if (TextUtils.isEmpty(path))
                    path = uri.getPath();
            }
            assert cursor != null;
            cursor.close();
        } else path = Commons.getPath(uri, context);
        if (path != null) {
            path = path.replace("/document/raw:", "");
        }
        return path;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case AppUtils.REQUEST_CODE_CAMERA:
                    if (true) {//intent != null) {
                        if (photoFile != null) {
                            otherDoc = photoFile.getAbsolutePath();
                            message = "";
                            isAttachment = "Yes";
                            sendChat();
                        } else
                            AppUtils.showToast(mContext, "Something went wrong");
                    }
                    break;
                case AppUtils.REQUEST_CODE_GALLERY:
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        if (selectedImage != null) {
                            Uri selectedImageUri = data.getData();
                            otherDoc = getRealPathFromURI(selectedImage);
                            message = "";
                            isAttachment = "Yes";
                            sendChat();
                        }
                    }
                    break;

                case AppUtils.REQUEST_CODE_FILES:
                    Uri data1 = data.getData();
                    otherDoc = getRealPathFromURIPdf(mContext, data1);
                    message = "";
                    isAttachment = "Yes";
                    sendChat();
                    break;
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null,
                null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToLast();
        return cursor.getString(column_index);
    }

    public void sendChat() {
        if (AppUtils.isOnline(mContext)) {
            try {
//                CrmManager.getInstance().sendChat(mContext, userId, userType, currentId,
//                        currentType, isAttachment, message, claimNo, refId, chatType, otherDoc);
                CrmManager.getInstance().sendChat(mContext, userId, userType, userId,
                        userType, isAttachment, message, refNo, refId, chatType, otherDoc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getClaimData() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                CrmManager.getInstance().getClaimViewData(mContext, refNo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getClaimChat() {
        if (AppUtils.isOnline(mContext)) {
            try {
                CrmManager.getInstance().getClaimChat(mContext, userId, chatType, refId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof ClaimView) {
            ClaimView cResponse = (ClaimView) response;
            if (cResponse.getStatus() != null) {
                claimObj = cResponse;
                currentId = claimObj.getCurrentUserId();
                currentType = claimObj.getCreateUserType();
                refId = claimObj.getId();
                getClaimChat();
            }
            progressdialog.dismiss();
        }

        if (response instanceof ChatList) {
            ChatList cResponse = (ChatList) response;
            if (cResponse.getStatus()) {
                if (cResponse.getMessages() != null && cResponse.getMessages().size() > 0) {
                    if (chatList.size() > 0)
                        chatList.clear();
                    chatList.addAll(cResponse.getMessages());
                    chatAdapter.notifyDataSetChanged();
                    rcChat.scrollToPosition(chatList.size() - 1);
                }
            }
        }
    }

    @Override
    public void onImageViewClick(int position) {
        String path = chatList.get(position).getAttachmentUrl();
        if (!TextUtils.isEmpty(path)) {
            Intent intent1 = new Intent(mContext, ImageActivity.class);
            intent1.putExtra("img", path);
            startActivity(intent1);
        }
    }

    @Override
    public void onPDFClick(int position) {
        String path = chatList.get(position).getAttachmentUrl();
        if (!TextUtils.isEmpty(path)) {

            try {
                Intent intentUrl = new Intent(Intent.ACTION_VIEW);
                intentUrl.setDataAndType(Uri.parse(path), "application/pdf");
                intentUrl.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentUrl);
            } catch (ActivityNotFoundException e) {
                Intent intent1 = new Intent(mContext, ViewPdfActivity.class);
                intent1.putExtra("path", path);
                startActivity(intent1);
            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }
}