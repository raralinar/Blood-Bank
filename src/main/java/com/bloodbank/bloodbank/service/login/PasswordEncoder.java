package com.bloodbank.bloodbank.service.login;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordEncoder {
    public String encode(String password) {
        return DigestUtils.sha256Hex(password);
    }
}
