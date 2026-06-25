package org.liu3.image;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 使用java.awt.image.BufferedImage和javax.imageio.ImageIO操作图片
 * 基本是围绕InputStream和OutputStream进行转换和操作
 * @Author: liutianshuo
 * @Date: 2026/1/23
 */
public class BufferedImageUtil {

    /**
     * 创建空白图片
     * @param width
     * @param height
     * @param imageType
     * @return
     */
    public static BufferedImage createBlank(int width, int height, int imageType) {

        // 创建一个BufferedImage对象，类型为透明PNG（TYPE_INT_ARGB）
        BufferedImage image = new BufferedImage(width, height, imageType);

        // 获取Graphics2D对象，用于绘制
        Graphics2D g2d = image.createGraphics();
        // 设置背景颜色为白色，但不绘制，因为我们想要的是透明的背景
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // 释放Graphics2D资源
        g2d.dispose();
        return image;
    }

    public static BufferedImage zoomHeight(BufferedImage sourceImage, int targetHeight) {

        // 307 * 50
        int width1 = sourceImage.getWidth();
        int height1 = sourceImage.getHeight();
        int imageType = sourceImage.getType();
        if (imageType == 0) {
            imageType = 1;
        }

        int scaledWidth = width1 * targetHeight / height1;
        int scaledHeight = targetHeight;
        //BufferedImage.TYPE_INT_RGB
        BufferedImage zoomImage = new BufferedImage(scaledWidth, scaledHeight, imageType);

        Graphics2D g2d = zoomImage.createGraphics();
        g2d.setComposite(AlphaComposite.Src);
        //g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        /*
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AffineTransform at = AffineTransform.getScaleInstance(width1, height1);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        scaleOp.filter(originalImage, resizedImage);
        */
        g2d.drawImage(sourceImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
        return zoomImage;
    }

    /**
     * 将一张图片放到另一张上,居中
     *
     * @param backgroundImage
     * @param imageToDraw
     * @return
     */
    public static BufferedImage mergeImageCenter(BufferedImage backgroundImage, BufferedImage imageToDraw) {

        //imageToDraw = zoomHeight(imageToDraw);

        int x1 = backgroundImage.getWidth();
        int x2 = imageToDraw.getWidth();
        int x = (x1 - x2) / 2;
        // 创建一个Graphics2D对象来绘制图片
        Graphics2D g = backgroundImage.createGraphics();
        g.drawImage(imageToDraw, x, 0, null); // 在背景图片上绘制，位置从(10,10)开始
        g.dispose();
        return backgroundImage;
    }

    public static byte[] toBytes(BufferedImage image, String format) throws IOException {
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            ImageIO.write(image, format, outputStream);
            byte[] bs = outputStream.toByteArray();
            return bs;
        }
    }

    /**
     * 将svg图片的数据转转为png的数据
     * @param svgInputStream
     * @param pngOutputStream
     * @throws TranscoderException
     * @throws IOException
     */
    public static void svgStreamToPngStream(InputStream svgInputStream, OutputStream pngOutputStream, int width, int height) throws TranscoderException, IOException {

        PNGTranscoder transcoder = new PNGTranscoder();
        if(width > 0){
            transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, new Float(width));
            transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, new Float(height));
        }

        TranscoderInput input = new TranscoderInput(svgInputStream);
        TranscoderOutput output = new TranscoderOutput(pngOutputStream);

        transcoder.transcode(input, output);
    }




}
