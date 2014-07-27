package com.jcommerce.core.io;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;



/**
 *         TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 * 
 *         添加水印, filePath 源图片路径 含图片名， watermark 水印图片路径 savePath
 *         为你添加水印后的图片保存路径文件夹 words 要添加的文字
 */
// 添加水印,filePath 源图片路径， watermark 水印图片路径
public class DisposePictures {

	private static int waterMarkWidth = 0;

	private static final String logoWords = "www.ishop.com";
	private static final String warterMarkURL = "F:/wade.jpg";
	private static final String pressText = "ishop";


	private static int waterMarkHeight = 0;

	public synchronized void createMark(String filePath, String savePath) throws Exception {
		try {
			File f = new File(filePath);
			if (!f.exists()) {
				throw new Exception("");
			}
			ImageIcon imgIcon = new ImageIcon(filePath);
			Image theImg = imgIcon.getImage();

			ImageIcon waterIcon = new ImageIcon(warterMarkURL);

			Image waterImg = waterIcon.getImage();

			// /////////////////////////////////////////////////////////////////////

			String picname = f.getName();// 取得图片名

			if (warterMarkURL != null && !warterMarkURL.equals("")) {// 当

				ImageIcon markIcon = new ImageIcon(warterMarkURL); // 要添加的水印图标

				Image markImg = markIcon.getImage();

				waterMarkWidth = markImg.getWidth(null); // 水印图标宽度
				// wid = 300;
				// System.out.println(wid + "");
				waterMarkHeight = markImg.getHeight(null); // 水印图标高度
				// het = 400;
				// System.out.println(het + "");
			}
			// ////////////////////////////////////////////////////////////////////
			// int width = 2000;
			int width = theImg.getWidth(null); // 源图片宽度
			// System.out.println(width + "");
			// int height = 900;
			int height = theImg.getHeight(null); // 源图片高度
			// System.out.println(height + "");

			if (savePath.equals("")){
				savePath = filePath;// 如果未指定保存路径则保存回原路径
			}else{
				savePath = savePath + "\\" + picname;// 指定保存文件夹时,拼接出保存路径
			}

			BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			Graphics2D g = bimage.createGraphics();

			g.setColor(Color.red); // 设置颜色

			g.setBackground(Color.white);

			g.drawImage(theImg, 0, 0, null);

			g.drawImage(waterImg, width - waterMarkWidth + 5, height - waterMarkHeight + 5, null); // 添加图标中间两个数字参数
			// 是设定位置

			g.drawString(logoWords, width - 120, height - 10); // 添加文字

			FileOutputStream out = new FileOutputStream(savePath);
			ImageIO.write(bimage,"JPEG",new File(savePath));   
			out.close();

		} catch (Exception e) {

			throw new Exception("");
		} finally {
			System.gc();// 清理 垃圾对象
		}

	}

	/** */
	/**
	 * 打印文字水印图片
	 * 
	 * @param pressText
	 *            --文字
	 * @param targetImg
	 *            -- 目标图片
	 * @param fontName
	 *            -- 字体名
	 * @param fontStyle
	 *            -- 字体样式
	 * @param color
	 *            -- 字体颜色
	 * @param fontSize
	 *            -- 字体大小
	 * @param x
	 *            -- 偏移量
	 * @param y
	 */
	public synchronized void pressText(String targetImg, String fontName, int fontStyle,
			Color color, int fontSize, int x, int y) throws Exception {
		try {
			File _file = new File(targetImg);
			if (!_file.exists()) {
				throw new Exception("");
			}
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			// String s="www.qhd.com.cn";
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));

			g.drawString(pressText, wideth - fontSize - x, height - fontSize
					/ 2 - y);
			g.dispose();
			FileOutputStream out = new FileOutputStream(targetImg);
			ImageIO.write(image,"JPEG",out);
			out.close();
		} catch (Exception e) {
			throw new Exception();
		} finally {
			System.gc();// 清理 垃圾对象
		}

	}

	/**
	 * 生成缩略图
	 * 
	 * @param source
	 * @param targetW
	 * @param targetH
	 * @return
	 */
	public BufferedImage resize(BufferedImage source, int targetW, int targetH) {
		// targetW，targetH分别表示目标长和宽
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
		// 则将下面的if else语句注释即可
		if (sx > sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else {
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}
		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
					targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(targetW, targetH, type);
		Graphics2D g = target.createGraphics();
		// smoother than exlax:
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	public synchronized void saveImageAsJpg(String fromFileStr, String saveToFileStr, int width, int hight) throws Exception {
		BufferedImage srcImage;
		// String ex =
		// fromFileStr.substring(fromFileStr.indexOf("."),fromFileStr.length());
		String imgType = "JPG";
		if (fromFileStr.toLowerCase().endsWith(".png")) {
			imgType = "PNG";
		}
		// System.out.println(ex);
		try {
			File saveFile = new File(saveToFileStr);
			File fromFile = new File(fromFileStr);

			srcImage = ImageIO.read(fromFile);
			if (width > 0 || hight > 0) {
				srcImage = resize(srcImage, width, hight);
			}
			ImageIO.write(srcImage, imgType, saveFile);
		} catch (Exception e) {
			throw new Exception("error");
		}
	}
}