package com.baizhi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FileMes implements Serializable {
    private Boolean is_dir;
    private Boolean has_file;
    private Long filesize;
    private String dir_path;
    private Boolean is_photo;
    private String filetype;
    private String filename;
    private Date datetime;

}
