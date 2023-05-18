package edu.bistu.copyright.protect.util;

import org.bytedeco.javacv.Java2DFrameUtils;
import org.bytedeco.opencv.opencv_core.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgproc.FONT_HERSHEY_TRIPLEX;
import static org.bytedeco.opencv.global.opencv_imgproc.putText;

public class WatermarkUtil {

    public static Mat getImageWatermarkWithText(Path path) throws IOException {
        if (!Files.exists(path)) {
            throw new IOException("File not exist");
        }
        Mat mat = imread(path.toAbsolutePath().toString());
        return getImageWatermarkWithText(mat);
    }

    public static void addImageWatermarkWithText(Path inputPath, String watermark, Path outPath) throws IOException {
        if (!Files.exists(inputPath)) {
            throw new IOException("File not exist");
        }
        try {
            Mat imgMat = imread(inputPath.toAbsolutePath().toString());
            Mat watermarkedMat = WatermarkUtil.addImageWatermarkWithText(imgMat, watermark);
            imwrite(outPath.toAbsolutePath().toString(), watermarkedMat);
        } catch (Exception e) {
            throw new IOException("Watermarking Process Error", e);
        }
    }

    public static String getBase64(Mat image) throws IOException {
        return getBase64(mat2bytes(image));
    }

    public static String getBase64(byte[] bytes) {
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] mat2bytes(Mat image) throws IOException {
        BufferedImage bufferedImage = Java2DFrameUtils.toBufferedImage(image);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        return baos.toByteArray();
    }


    public static Mat addImageWatermarkWithText(Mat image, String watermarkText) {
        MatVector allPlanes = new MatVector();
        Mat complexImage = getDFT(image, allPlanes);
        // 添加文本水印
        Scalar scalar = new Scalar(0, 0, 0, 0);
        Point point = new Point(50, 50);
        putText(complexImage, watermarkText, point, FONT_HERSHEY_TRIPLEX, 1D, scalar);
        flip(complexImage, complexImage, -1);
        putText(complexImage, watermarkText, point, FONT_HERSHEY_TRIPLEX, 1D, scalar);
        flip(complexImage, complexImage, -1);
        return antitransformImage(complexImage, allPlanes);
    }

    public static Mat getImageWatermarkWithText(Mat image) {
        MatVector allPlanes = new MatVector();
        Mat complexImage = getDFT(image, allPlanes);
        return createOptimizedMagnitude(complexImage);
    }

    public static Mat getDFT(Mat image, MatVector allPlanes) {
        MatVector planes = new MatVector();
        Mat complexImage = new Mat();
        Mat padded = splitSrc(image, allPlanes);
        padded.convertTo(padded, CV_32F);
        planes.push_back(padded);
        planes.push_back(Mat.zeros(padded.size(), CV_32F).asMat());
        merge(planes, complexImage);
        // dft
        dft(complexImage, complexImage);
        return complexImage;
    }

    private static Mat splitSrc(Mat mat, MatVector allPlanes) {
        mat = optimizeImageDim(mat);
        split(mat, allPlanes);
        Mat padded = new Mat();
        if (allPlanes.size() > 1) {
            for (int i = 0; i < allPlanes.size(); i++) {
                if (i == 0) {
                    padded = allPlanes.get(i);
                    break;
                }
            }
        } else {
            padded = mat;
        }
        return padded;
    }

    private static Mat antitransformImage(Mat complexImage, MatVector allPlanes) {
        Mat invDFT = new Mat();
        idft(complexImage, invDFT, DFT_SCALE | DFT_REAL_OUTPUT, 0);
        Mat restoredImage = new Mat();
        invDFT.convertTo(restoredImage, CV_8U);
        if (allPlanes.size() == 0) {
            allPlanes.push_back(restoredImage);
        } else {
            allPlanes.put(0, restoredImage);
        }
        Mat lastImage = new Mat();
        merge(allPlanes, lastImage);
        return lastImage;
    }

    private static Mat optimizeImageDim(Mat image) {
        //优化尺寸会添加黑边
//        Mat padded = new Mat();
//        int addPixelRows = getOptimalDFTSize(image.rows());
//        int addPixelCols = getOptimalDFTSize(image.cols());
//        copyMakeBorder(image, padded, 0, addPixelRows - image.rows(), 0, addPixelCols - image.cols(),
//                BORDER_CONSTANT, Scalar.all(0));
//        return padded;
        return image;
    }

    private static Mat createOptimizedMagnitude(Mat complexImage) {
        MatVector newPlanes = new MatVector();
        Mat mag = new Mat();
        split(complexImage, newPlanes);
        magnitude(newPlanes.get(0), newPlanes.get(1), mag);
        add(Mat.ones(mag.size(), CV_32F).asMat(), mag, mag);
        log(mag, mag);
        shiftDFT(mag);
        mag.convertTo(mag, CV_8UC1);
        normalize(mag, mag, 0, 255, NORM_MINMAX, CV_8UC1, null);
        return mag;
    }

    private static void shiftDFT(Mat image) {
        image = image.apply(new Rect(0, 0, image.cols() & -2, image.rows() & -2));
        int cx = image.cols() / 2;
        int cy = image.rows() / 2;

        Mat q0 = new Mat(image, new Rect(0, 0, cx, cy));
        Mat q1 = new Mat(image, new Rect(cx, 0, cx, cy));
        Mat q2 = new Mat(image, new Rect(0, cy, cx, cy));
        Mat q3 = new Mat(image, new Rect(cx, cy, cx, cy));
        Mat tmp = new Mat();
        q0.copyTo(tmp);
        q3.copyTo(q0);
        tmp.copyTo(q3);
        q1.copyTo(tmp);
        q2.copyTo(q1);
        tmp.copyTo(q2);
    }
}