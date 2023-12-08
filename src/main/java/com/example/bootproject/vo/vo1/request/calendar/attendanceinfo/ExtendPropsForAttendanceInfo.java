package com.example.bootproject.vo.vo1.request.calendar.attendanceinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtendPropsForAttendanceInfo {
    String kind = "attendance_info";
    String status = "normal";
    String identifier =null;
}
