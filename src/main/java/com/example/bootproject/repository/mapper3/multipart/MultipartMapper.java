package com.example.bootproject.repository.mapper3.multipart;

import com.example.bootproject.vo.vo3.response.image.MultipartUploadResponseDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MultipartMapper {
    @Insert("insert into image(employee_id,file_name,upload_date,uuid) values(#{employeeId},#{filename},now(),'uuid')")
    @Options(useGeneratedKeys = true)
    Integer upload(@Param("employeeId") String employeeId, @Param("filename") String filename);

    void download();

    @Select("select file_id from image where employee_id = #{employeeId} order by file_id desc limit 1")
    Integer findByEmployeeId(String employeeId);

    @Select("select * from image where file_id = #{fileId} order by file_id desc limit 1")
    MultipartUploadResponseDto findByFileId(Integer fileId);
}
