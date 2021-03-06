package com.exercise.p.emailclient;

import android.util.Log;

import com.exercise.p.emailclient.activity.SignActivity;
import com.exercise.p.emailclient.dto.data.MailBoxResponse;
import com.exercise.p.emailclient.dto.data.FolderResponse;
import com.exercise.p.emailclient.dto.data.MailPreviewResponse;
import com.exercise.p.emailclient.dto.data.UserInfoResponse;
import com.exercise.p.emailclient.utils.SQLiteAccess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p on 2017/12/5.
 */

public class GlobalInfo {
    public static UserInfoResponse user = null;
    public static String authorization = "";
    public static ArrayList<MailBoxResponse> mailBoxResponses = new ArrayList<>();
    public static boolean Main2AddIsChange = false;
    public static boolean Main2SendIsChange = false;
    public static boolean Main2DetailIsChange = false;
    public static List<FolderResponse> allMail = new ArrayList<>();
    public static int DRAFT_ID = -100;

    // 当前邮箱账户id
    public static int activeId = 0;

    public static ArrayList<MailPreviewResponse> getMailsByBox(String boxType) {
        for (FolderResponse folder : GlobalInfo.allMail) {
            if (folder.getFolderType().equals(boxType)) {
                return (ArrayList<MailPreviewResponse>) folder.getMailList();
            }
        }
        return new ArrayList<>();
    }

    public static void updateMail(int folderId, ArrayList<MailPreviewResponse> mails) {
        for (FolderResponse folder : GlobalInfo.allMail) {
            if (folder.getId().equals(folderId)) {
                if (folderId == DRAFT_ID) {
                    folder.getMailList().clear();
                    folder.getMailList().addAll(SQLiteAccess.readDraft());
                } else {
                    if (mails != null) {
                        folder.getMailList().clear();
                        folder.getMailList().addAll(mails);
                    }
                }
            }
        }
    }

    public static String getFolderName(int folderId) {
        for (FolderResponse folder : GlobalInfo.allMail) {
            if (folder.getId().equals(folderId)) {
                return folder.getFolderType();
            }
        }
        return "";
    }

    public static int getFolderId(String name) {
        for (FolderResponse folder : GlobalInfo.allMail) {
            if (folder.getFolderType().equals(name)) {
                return folder.getId();
            }
        }
        return 0;
    }

    // 得到当前选择的邮箱信息
    public static MailBoxResponse getCurrent() {
        for (MailBoxResponse mailBoxResponse : mailBoxResponses) {
            if (mailBoxResponse.getId() == activeId)
                return mailBoxResponse;
        }
        return null;
    }
}
