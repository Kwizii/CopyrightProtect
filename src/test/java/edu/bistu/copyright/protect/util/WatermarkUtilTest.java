package edu.bistu.copyright.protect.util;

import org.bytedeco.opencv.opencv_core.Mat;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.bytedeco.opencv.global.opencv_imgcodecs.*;

/**
 * @author Chanvo
 * @date 2023/5/18 10:11
 * @description
 */
class WatermarkUtilTest {
    @Test
    public void test() throws IOException {
        Mat img = imread("C:\\Users\\ChanvoBook\\Desktop\\1.png");//加载图片
        Mat outImg = WatermarkUtil.addImageWatermarkWithText(img, "ABCDEFGHIJKLMNOPQRSTABCDEFGHIJKLMNOPQRSTABCDEFGHIJKLMNOPQRST");
        imwrite("C:\\Users\\ChanvoBook\\Desktop\\1-w.png", outImg);//保存加过水印的图片
        //读取图片水印
        Mat watermarkImg = WatermarkUtil.getImageWatermarkWithText(outImg);
        imwrite("C:\\Users\\ChanvoBook\\Desktop\\1-t.png", watermarkImg);//保存获取到的水印
        System.out.println(WatermarkUtil.getBase64(watermarkImg));
    }
}