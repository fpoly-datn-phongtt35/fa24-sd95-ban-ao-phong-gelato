package com.example.datn.services;



import com.example.datn.entities.Account;
import com.example.datn.entities.VerificationCode;
import jakarta.mail.MessagingException;


public interface VerificationCodeService {
    VerificationCode createVerificationCode(String email) throws MessagingException;

    Account verifyCode(String code);
}