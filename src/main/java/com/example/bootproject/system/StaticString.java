package com.example.bootproject.system;

public class StaticString {

    public static final String VACATION_REQUEST_STATE_REQUESTED = "연차 요청 중";
    public static final String VACATION_REQUEST_STATE_REJECTED = "연차 요청 반려";
    public static final String VACATION_REQUEST_STATE_PERMITTED = "연차 요청 승인";
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final String APPEAL_REQUEST_STATE_REQUESTED = "조정 요청 중";
    public static final String APPEAL_REQUEST_STATE_REJECTED = "조정 요청 반려";
    public static final String APPEAL_REQUEST_STATE_PERMITTED = "조정 요청 승인";
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final String ATTENDANCE_INFO_STATUS_PENDING = "근태 판정 중";
    public static final String ATTENDANCE_INFO_STATUS_NORMAL = "정상 근태";
    public static final String ATTENDANCE_INFO_STATUS_ABSENT = "이상 근태(결근)";
    public static final String ATTENDANCE_INFO_STATUS_REQUESTED = "조정 요청 중";
    public static final String ATTENDANCE_INFO_STATUS_NORMAL_START_EARLY_END = "이상 근태(조기 퇴근)";
    public static final String ATTENDANCE_INFO_STATUS_LATE_START_EARLY_END = "이상 근태(지각, 조기 퇴근)";
    public static final String ATTENDANCE_INFO_STATUS_LATE_START_NORMAL_END = "이상 근태(지각)";
    public static final String ATTENDANCE_INFO_STATUS_NULL_END = "이상 근태(퇴근 정보 없음)";
    public static final String ATTENDANCE_INFO_STATUS_LATE_START_NULL_END = "이상 근태(지각, 퇴근 정보 없음)";
    public static final String ATTENDANCE_INFO_STATUS_NORMAL_START_NULL_END = "이상 근태(퇴근 정보 없음)";
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final String SESSION_ID_NOT_MATCHED_LOGIN_REQUEST = "notMatchSessionId";
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final String VACATION_REQUEST_STATUS_CATEGORY_REQUESTED = "연차 요청 중";
    public static final String VACATION_REQUEST_STATUS_CATEGORY_REJECTED = "연차 요청 반려";
    public static final String VACATION_REQUEST_STATUS_CATEGORY_APPROVAL = "연차 요청 승인";
}
