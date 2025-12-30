package com.angkish.service.impl;

import com.angkish.exception.BusinessException;
import com.angkish.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendRegisterCode(String toEmail, String code) {
        try {
            // 1. 创建简单邮件对象
            SimpleMailMessage message = new SimpleMailMessage();
            // 2. 设置发件人（服务器邮箱）
            message.setFrom(fromEmail);
            // 3. 设置收件人
            message.setTo(toEmail);
            // 4. 设置邮件标题
            message.setSubject("【农产品溯源系统】注册验证码");
            // 5. 设置邮件正文
            message.setText(buildMailContent(code));
            // 6. 发送
            mailSender.send(message);
            log.info("验证码邮件发送成功 -> {}, code={}", toEmail, code);
        } catch (Exception e) {
            log.error("验证码邮件发送失败 -> {}", toEmail, e);
            throw new BusinessException("邮件发送失败，请稍后重试");
        }
    }

    /**
     * 构建邮件内容
     */
    private String buildMailContent(String code) {
        return "您好：\n\n"
                + "您正在注册【农产品质量安全溯源系统】，验证码为：\n\n"
                + code + "\n\n"
                + "验证码有效期 5 分钟，请勿泄露。\n\n"
                + "如非本人操作，请忽略此邮件。";
    }

}

