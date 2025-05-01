package org.sopt.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PostRequest (String title , List<MultipartFile> photos){
}
