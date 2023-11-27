package com.souad.users.service;

import com.souad.users.entities.Mail;

public interface MailService {
    void sendMail(String email, Mail mail);
}