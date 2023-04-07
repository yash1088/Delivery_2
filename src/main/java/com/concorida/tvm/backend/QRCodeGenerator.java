package com.concorida.tvm.backend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeGenerator {
    private static QRCodeGenerator instance = new QRCodeGenerator();

    private QRCodeGenerator() {
    }

    public static QRCodeGenerator getInstance(){
        if (instance == null){
            instance = new QRCodeGenerator();
        }
        return instance;
    }
    public void generateQRCode() throws WriterException, IOException, URISyntaxException {
        String paymentCode = "YOUR_PAYMENT_CODE_HERE";

        int size = 250;


        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");


        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(paymentCode, BarcodeFormat.QR_CODE, size, size, hintMap);

        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }


        String filePath = this.getClass().getResource("/QR/payment_code.png").getPath();
        File outputFile = new File("payment_code.png");
        ImageIO.write(image, "png", outputFile);
    }

}
