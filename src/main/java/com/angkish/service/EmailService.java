package com.angkish.service;



public interface EmailService {

    /**
     * 发送注册验证码邮件
     *
     * @param toEmail 接收方邮箱
     * @param code    验证码
     */
    void sendRegisterCode(String toEmail, String code);
}

