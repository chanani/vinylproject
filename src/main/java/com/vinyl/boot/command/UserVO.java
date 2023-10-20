package com.vinyl.boot.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO {

    private String username;
    private String password;
    private String user_phone;
    private String user_email;
    private String user_add;
    private String user_birth;

}
