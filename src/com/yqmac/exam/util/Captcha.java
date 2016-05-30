package com.yqmac.exam.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码类，用于根据字符生成验证码图片
 */
public class Captcha {
	/**
	 * 生成的验证码图片的宽
	 */
	private int imgWidth;
	/**
	 * 生成的验证码图片的高度
	 */
	private int imgHeight;
	/**
	 * 验证码字符数量
	 */
	private int codeLength;

	/**
	 * 验证码字符库，用于生成随机验证码
	 */
	private String codeContent;
	/**
	 * 随机数种子，用于生成随机字符
	 */
	private static final Random ran = new Random();
	/**
	 * 单例对象
	 */
	private static Captcha captcha;

	/**
	 * 私有构造，
	 * 1、单例
	 * 2、初始化字符库和验证码默认长度
	 */
	private Captcha(){
		codeContent = "0123456789fVxyz";
		//codeContent = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPKRSTUVWXYZ";
		codeLength = 4;
	}

	/**
	 * 单例
	 * @return
     */
	public static Captcha getInstance() {
		if(captcha==null) captcha = new Captcha();
		return captcha;
	}

	/**
	 * 参数设定
	 * @param width
	 * @param height
	 * @param num
     * @param code
     */
	public void set(int width,int height,int num,String code) {
		this.imgWidth = width;
		this.imgHeight = height;
		this.setCodeLength(num);
		this.setCodeContent(code);
	}
	
	public void set(int width,int height) {
		this.imgWidth = width;
		this.imgHeight = height;
	}
	
	public int getWidth() {
		return imgWidth;
	}
	public void setWidth(int width) {
		this.imgWidth = width;
	}
	public int getHeight() {
		return imgHeight;
	}
	public void setHeight(int height) {
		this.imgHeight = height;
	}
	public int getCodeLength() {
		return codeLength;
	}
	public void setCodeLength(int codeLength) {
		this.codeLength = codeLength;
	}
	public String getCodeContent() {
		return codeContent;
	}
	public void setCodeContent(String codeContent) {
		this.codeContent = codeContent;
	}

	/**
	 * 返回一个指定长度的随机验证码
	 * @return
     */
	public String generateCheckcode() {
		StringBuffer cc = new StringBuffer();
		for(int i = 0; i< codeLength; i++) {
			cc.append(codeContent.charAt(ran.nextInt(codeContent.length())));
		}
		return cc.toString();
	}

	/**
	 * 根据验证码字符生成验证码图片并返回
	 * @param checkcode
	 * @return
     */
	public BufferedImage generateCheckImg(String checkcode) {
		//创建一个图片对象
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
		//获取图片对象的画笔
		Graphics2D graphic = img.createGraphics();
		graphic.setColor(Color.white);
		graphic.fillRect(0, 0, imgWidth, imgHeight);
		graphic.setColor(Color.BLACK);
		graphic.drawRect(0, 0, imgWidth-1, imgHeight-1);
		Font font = new Font("宋体",Font.BOLD+Font.ITALIC,(int)(imgHeight*0.8));
		graphic.setFont(font);
		for(int i = 0; i< codeLength; i++) {
			graphic.setColor(new Color(ran.nextInt(180),ran.nextInt(180),ran.nextInt(180)));
			graphic.drawString(String.valueOf(checkcode.charAt(i)), i*(imgWidth/ codeLength)+4, (int)(imgHeight*0.8));
		}
		//加一些点
		for(int i=0;i<(imgWidth+imgHeight);i++) {
			graphic.setColor(new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255)));
			graphic.drawOval(ran.nextInt(imgWidth), ran.nextInt(imgHeight), 1, 1);
		}
		//加一些线
		for(int i=0;i<4;i++) {
			graphic.setColor(new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255)));
			graphic.drawLine(0, ran.nextInt(imgHeight), imgWidth, ran.nextInt(imgHeight));
		}
		return img;
	}
}
