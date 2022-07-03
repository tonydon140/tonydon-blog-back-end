package top.tonydon.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import top.tonydon.constant.MailConstants;
import top.tonydon.domain.entity.Comment;
import top.tonydon.domain.entity.FriendLink;

import javax.annotation.Resource;

@Component
@Slf4j
public class MailUtils {

    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送评论邮件通知
     *
     * @param comment 评论
     */
    public void sendCommentMail(Comment comment) {
        String subject = "[TonyDon博客]：您有一条新的评论，在《" + comment.getArticleTitle() + "》中";
        String content = "文章标题：《" + comment.getArticleTitle() + "》\n" +
                "评论内容：" + comment.getContent() + "\n" +
                "点击链接查看：http://tonydon.top/article/" + comment.getArticleId();

        sendSimpleMail(MailConstants.adminMail, subject, content);
    }

    /**
     * 发送友链申请通知
     *
     * @param friendLink 友链
     */
    public void sendFriendLinkMail(FriendLink friendLink) {
        String subject = "[TonyDon博客]：您有新的友链申请待审核";
        String content = "友链名称：《" + friendLink.getName() + "》\n" +
                "友链描述：" + friendLink.getDescription() + "\n" +
                "友链地址：" + friendLink.getAddress() + "\n" +
                "前往审核：http://tonydon.top/login\n";
        sendSimpleMail(MailConstants.adminMail, subject, content);
    }

    /**
     * 发送简单邮件
     *
     * @param to      收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendSimpleMail(String to, String subject, String content) {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(from);
        //邮件接收人
        message.setTo(to);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(content);
        //发送邮件
        javaMailSender.send(message);
        log.info("to: {}, subject: {}", to, subject);
    }
}
