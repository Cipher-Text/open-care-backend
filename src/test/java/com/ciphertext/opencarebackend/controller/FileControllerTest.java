//package com.ciphertext.opencarebackend.controller;
//
//import com.ciphertext.opencarebackend.service.MinioService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.multipart.MultipartFile;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(FileController.class)
//public class FileControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private MinioService minioService;
//
//    @Test
//    public void testUploadFile() throws Exception {
//        // Given
//        MockMultipartFile file = new MockMultipartFile(
//                "file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
//
//        when(minioService.uploadFile(any(MultipartFile.class))).thenReturn("abc-123.txt");
//
//        // When
//        mockMvc.perform(multipart("/api/files/upload").file(file))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.objectName").value("abc-123.txt"));
//
//        // Then
//        verify(minioService).uploadFile(any(MultipartFile.class));
//    }
//}