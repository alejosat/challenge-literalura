package com.literalura.api.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormatDTO {
    private Long id;
    private String type;
    private String url;
}
