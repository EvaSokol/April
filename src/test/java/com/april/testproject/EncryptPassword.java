package com.april.testproject;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptPassword {


    @Test
    public void testEncryptPassword() {
        // Amy:1234
        // Kim Jon In:test
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("test"));
        System.out.println(encoder.encode("admin"));
    }

}
