package com.example.bootproject.repository.mapper3.multipart;

import com.example.bootproject.vo.vo1.request.image.MultipartUploadRequestDto;
import com.example.bootproject.vo.vo1.response.image.MultipartUploadResponseDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MultipartMapper {
    @Insert("insert into image(identifier,file_name,upload_date,uuid) values(#{dto.identifier},#{filename},now(),'uuid')")
    @Options(useGeneratedKeys = true, keyProperty = "dto.fileId")
    Integer upload(@Param("dto") MultipartUploadRequestDto dto, @Param("filename") String filename);
    @Insert("insert into image_appeal(identifier,file_name,upload_date,uuid) values(#{dto.identifier},#{filename},now(),'uuid')")
    @Options(useGeneratedKeys = true, keyProperty = "dto.fileId")
    Integer uploadAppealData(@Param("dto") MultipartUploadRequestDto dto, @Param("filename") String filename);

    void download();

    @Select("select file_id from image where identifier = #{employeeId} order by file_id desc limit 1")
    Long findByEmployeeId(String employeeId);
    @Select("select file_id from image_appeal where identifier = #{appealRequestId} order by file_id desc limit 1")
    Long findByAppealRequestId(String appealRequestId);

    @Select("select * from image where file_id = #{fileId} order by file_id desc limit 1")
    MultipartUploadResponseDto findByFileId(Long fileId);
    @Select("select * from image_appeal where file_id = #{fileId} order by file_id desc limit 1")
    MultipartUploadResponseDto findAppealDataFileByFileId(Long generatedFileId);
}
