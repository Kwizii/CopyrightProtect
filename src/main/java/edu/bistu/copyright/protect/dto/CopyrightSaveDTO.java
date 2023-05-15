package edu.bistu.copyright.protect.dto;

import lombok.Data;

/**
 * @author Chanvo
 * @date 2023/5/13 23:44
 * @description
 */
@Data
public class CopyrightSaveDTO {


    private String description;

    private String content;

    private String url;
}
