package com.office.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import com.office.entity.Customer;

public class Mail {

	private String server = "stmp.163.com"; // 发件人邮箱服务域名
	private String user = "jiashouhui1988@163.com"; // 发件人称号，同邮箱地址
	private String password = "TMCTcwDskL/gquIJDxlmZw=="; // 发件人邮箱客户端密码/授权码(qq邮箱为授权码，非邮箱密码 pmrkjidrwwyibfgf)

	/**
	 * 发送邮件
	 * @param to
	 * @param multipart
	 * @param title
	 */
	public boolean sendMail(String to, String title, Multipart multipart) {
		
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		String sql = "select * from mailbox";
		try {
			conn = JdbcPool.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			if(rs.next()){
				server = rs.getString("server")==null?server:rs.getString("server");
				user = rs.getString("account")==null?user:rs.getString("account");
				password = rs.getString("password")==null?password:rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcPool.release(rs,statement,conn);
		}
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", server); //设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
		props.setProperty("mail.smtp.auth", "true"); //需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
		props.setProperty("mail.smtp.starttls.enable", "true"); //STARTTLS是对纯文本通信协议的扩展。它提供一种方式将纯文本连接升级为加密连接（TLS或SSL）

		Session session = Session.getDefaultInstance(props); //用刚刚设置好的props对象构建一个session
		session.setDebug(true); //有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使用（你可以在控制台（console)上看到发送邮件的过程）
		MimeMessage message = new MimeMessage(session); //用session为参数定义消息对象
		try {
			message.setFrom(new InternetAddress(user)); //加载发件人地址
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); //加载收件人地址
			//如果发件人为163邮箱，且附件有图片，为防止163服务器垃圾邮件拦截，给自己抄送一下
			if(user.contains("@163.com")&&!user.equals(to)){
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(user));
			}
			message.setSubject(title); //加载标题
			message.setContent(multipart);
			message.saveChanges(); // 保存变化
			Transport transport = session.getTransport("smtp"); // 连接服务器的邮箱
			transport.connect(server, user, AesUtil.aesDecrypt(password)); // 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 设置客户订阅成功邮件内容
	 * @param customer
	 * @param imagePath
	 * @param type
	 * @return
	 */
	public Multipart setContect(Customer customer, String imagePath, String type) {
		try {
			// 设置图片
			MimeBodyPart img = new MimeBodyPart();
			MimeBodyPart qrCode = new MimeBodyPart();
			
			DataHandler dh = new DataHandler(new FileDataSource(imagePath + "image001.png"));// 图片路径
			img.setDataHandler(dh);
			img.setContentID("img");
			DataHandler dh1 = new DataHandler(new FileDataSource(imagePath + "image002.png"));// 图片路径
			qrCode.setDataHandler(dh1);
			qrCode.setContentID("qrCode");
			StringBuffer textBuffer = new StringBuffer();
			textBuffer.append("<img src='cid:img'><br>");
			textBuffer.append("<font style='font-weight:bold;' size='4'>尊敬的用户(").append(customer.getCompanyName())
					.append(")</font><br>");
			if ("customer".equals(type)) {
				textBuffer.append("<p style='text-indent:2em'>我们已经为您创建了管理员账号，您需要登陆服务网站激活您的管理员账号</p>");
				textBuffer.append(
						"<p style='text-indent:2em'>登陆地址：<a href='https://login.partner.microsoftonline.cn/'>https://login.partner.microsoftonline.cn/</a></p>");
				textBuffer.append("<p style='text-indent:2em'>用户名：admin@").append(customer.getDomain())
						.append(".partner.onmschina.cn</p>");
				textBuffer.append("<p style='text-indent:2em'>临时密码：").append(AesUtil.aesDecrypt(customer.getPassword()))
						.append("</p>");
				textBuffer.append("<p style='text-indent:2em'>主域名：").append(customer.getDomain())
						.append(".partner.onmschina.cn</p>");
			} else if ("orders".equals(type)) {
				textBuffer.append("<p style='text-indent:2em'>您的续订（新增）已成功，请进入管理中心—账单—许可证界面查看。</p>");
			}
			textBuffer.append("<p style='text-indent:2em'>若您在产品使用中遇到任何问题，请拨打我们的服务热线400-685-5866或发送邮件至<a href='mailto:365service@ecschina.com'>365service@ecschina.com</a></p>");
			if ("create".equals(type)) {
				textBuffer.append("<font size='4'>开始使用快速指南</font><br>");
				textBuffer.append("<p style='text-indent:2em'>感谢您使用Office 365！Office 365依托于云技术，令您可在诸多设备上轻松使用如电子邮件、会议、共享文档等功能，并能几乎随时随地访问最新版本的 Office 。此外，您还会获得最新功能的自动更新。我们希望您能尽享 Office 365 带来的灵活且高效的工作体验！</p>");
				textBuffer.append("<p style='text-indent:2em'>若要开始使用，请访问 Office 365 学习中心。在此处，您将找到有关设置和使用 Office 365 所有强大功能的帮助信息。<a href='https://support.office.com/zh-cn/learn/office365-for-business'>访问office365学习中心</a></p>");
			}
			textBuffer.append("<p style='text-indent:2em'>*本邮件是从不受监控的电子邮件地址发出的。请不要回复本邮件。</p>");
			textBuffer.append("<hr width='400px' color='red' align='left' size=10/>");
			textBuffer.append("<b>&nbsp;&nbsp;Office365 Support Team</b><br>");
			textBuffer.append("<b>&nbsp;&nbsp;伟仕佳杰中国北京 集团总部</b><br>");
			textBuffer.append("&nbsp;&nbsp;<img src='cid:qrCode'><br>");
			textBuffer.append("<font color='red' size ='1'>&nbsp;&nbsp;关注伟仕佳杰官方微信，更多商机，更多共赢</font><br>");
			textBuffer.append("&nbsp;&nbsp;地 址：北京市海淀区长春桥路11号万柳亿城大厦A座6/7层 100089<br>");
			textBuffer.append("&nbsp;&nbsp;电话：400-890-1909<br>");
			textBuffer.append("&nbsp;&nbsp;邮箱：<a href='mailto:365service@ecschina.com'>365service@ecschina.com</a><br>");
			textBuffer.append("&nbsp;&nbsp;网 址：<a href='www.ecschina.com'>www.ecschina.com</a><br>");
			textBuffer.append("<p><font color='grey'>本电子邮件以及其所传输的任何文件均为保密信息，仅供传送双方的个人或实体使用。如果您不是指定的收件人，那么我们在此通知您，严格禁止对这些信息进行披露、复制、分发或采取以这些信息内容为基础的任何行动。如果您是由于发送错误收到这封电子邮件或不是指定的收件人，请回电子邮件通知发件人并立即删除该电子邮件（及其所附任何文件）。除非这封电子邮件另有明文指出，否则此电子邮件的发送者的任何观点或意见纯属个人目的，不一定代表佳杰科技（中国）有限公司。佳杰科技（中国）有限公司不负责由于该电子邮件传播相关此病毒而造成的任何损害赔偿责任</font></p>");

			Multipart multipart = new MimeMultipart(); // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			BodyPart contentPart = new MimeBodyPart(); // 设置邮件的文本内容
			contentPart.setContent(textBuffer.toString(), "text/html;charset=utf-8");
			multipart.addBodyPart(img);
			multipart.addBodyPart(qrCode);
			multipart.addBodyPart(contentPart);
			return multipart;
		} catch (MessagingException e) {
			e.printStackTrace();
			return null;
		}
	}
}