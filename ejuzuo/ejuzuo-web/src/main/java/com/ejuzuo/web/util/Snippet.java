package com.ejuzuo.web.util;

/**
 * Created by tianlun.wu on 2016/5/7 0007.
 */
public class Snippet {

    /**
     * 创建图片缩略图(等比缩放)
     *
     * @param src
     *            源图片文件完整路径
     * @param dist
     *            目标图片文件完整路径
     * @param width
     *            缩放的宽度
     * @param height
     *            缩放的高度
     */
    public static void createThumbnail(String src, String dist, float width,
                                       float height) {
/*        try {
            File srcfile = new File(src);
            if (!srcfile.exists()) {
                System.out.println("文件不存在");
                return;
            }
            BufferedImage image = ImageIO.read(srcfile);

            // 获得缩放的比例
            double ratio = 1.0;
            // 判断如果高、宽都不大于设定值，则不处理
            if (image.getHeight() > height || image.getWidth() > width) {
                if (image.getHeight() > image.getWidth()) {
                    ratio = height / image.getHeight();
                } else {
                    ratio = width / image.getWidth();
                }
            }
            // 计算新的图面宽度和高度
            int newWidth = (int) (image.getWidth() * ratio);
            int newHeight = (int) (image.getHeight() * ratio);

            BufferedImage bfImage = new BufferedImage(newWidth, newHeight,
                    BufferedImage.TYPE_INT_RGB);
            bfImage.getGraphics().drawImage(
                    image.getScaledInstance(newWidth, newHeight,
                            Image.SCALE_SMOOTH), 0, 0, null);

            FileOutputStream os = new FileOutputStream(dist);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
            encoder.encode(bfImage);
            os.close();
            System.out.println("创建缩略图成功");
        } catch (Exception e) {
            System.out.println("创建缩略图发生异常" + e.getMessage());
        }*/
    }


    public static void main(String[] args) {
/*
        String srcFile = "E:\\DownLoadSVN\\ejuzuo\\res\\巨作3雪碧图\\images\\img\\big_banner_32.jpg";
        String distFile = "D:\\win7我的文档-桌面-收藏夹\\Desktop\\todo\\ejuzuo\\3.13\\thume\\big_banner_32.jpg";
        float width = 180;
        float height = 180 ;
        createThumbnail(srcFile, distFile, width, height);*/

    }


}
