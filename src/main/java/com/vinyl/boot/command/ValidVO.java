package com.vinyl.boot.command;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidVO {

    @NotEmpty(message = "이름은 필수 입니다.")
    @Size(min = 6, max = 15, message = "아이디는 6~15글자로 입력해주세요.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수 입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^&*])[A-Za-z!@#$%^&*\\d]{8,20}$", message = "비밀번호는 문자, 특수문자 포함 8~20글자로 입력해주세요.")
    private String password; // Integer = null값을 받음

    /*@NotBlank(message = "이메일은 공백일 수 없습니다.")
    @Email(message = "이메일 형식이어야 합니다.") // 이메일은 공백이 통과
    private String user_email;*/

    @Pattern(message = "'-'를 제외한 11자리를 입력해주세요.", regexp = "[0-9]{11}")
    private String user_phone;









}
