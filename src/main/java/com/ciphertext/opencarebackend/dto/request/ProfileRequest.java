package com.ciphertext.opencarebackend.dto.request;

import com.ciphertext.opencarebackend.dto.response.DistrictResponse;
import com.ciphertext.opencarebackend.dto.response.UnionResponse;
import com.ciphertext.opencarebackend.dto.response.UpazilaResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProfileRequest {
    private String username;
    private String keycloakUserId;
    private byte[] photo;
    private String phone;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String name;
    private String bnName;
    private String gender;
    private Date dateOfBirth;
    private String address;
    private Integer districtId;
    private Integer upazilaId;
    private Integer unionId;
}
