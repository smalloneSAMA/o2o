package com.smallone.o2o.util;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 图片处理工具类
 *
 * @author smallone
 * @created 2019--11--12--0:47
 */
public class ImageUtil {

    // 获取classpath的绝对值路径
    private static String basePath;
    static {
        try {
            basePath = URLDecoder.decode(Thread.currentThread().getContextClassLoader().getResource("").getPath(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // 时间格式化的格式
    private static final SimpleDateFormat sDateFormate = new SimpleDateFormat("yyyyMMddHHmmss");
    // 随机数
    private static final Random r = new Random();

    public static void main(String[] args) {
        try {
            Thumbnails.of(new File("E:\\学习\\java\\项目学习\\o2o\\src\\main\\resources\\image\\gakki1.jpg")).size(400, 300)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.jpg")), 0.5f)
                    .outputQuality(0.8).toFile(new File("E:\\学习\\java\\项目学习\\o2o\\src\\main\\resources\\image\\watermark\\watermark-gakki1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateThumbnail(MultipartFile thumbnail,String targetAddr){
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getInputStream()).size(200,200)
                    .watermark(Positions.BOTTOM_RIGHT,
                            ImageIO.read(new File(basePath + "watermark.jpg")),
                            0.25f).outputQuality(0.8).toFile(dest);
        }catch (IOException e){
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 生成随机文件名，当前年月日小时分钟秒+五位随机数
     * @return 随机文件名
     */
    private static String getRandomFileName() {
        //获取随机五位数
        int random = r.nextInt(89999)+10000;
        String nowTime = sDateFormate.format(new Date());
        return nowTime + random;
    }

    /**
     * 获取输入文件流的扩展名
     * @param cFile
     * @return
     */
    private static String getFileExtension(MultipartFile cFile) {

        String originalFileName = cFile.getOriginalFilename();
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }

    /**
     * 创建目标路径所这几到的目录，即/home/work/.../xxx.jpg
     * 那么 home work ... 这三个文件夹都得创建出来
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    /**
     * storePath是文件路劲还是目录路径，
     * 如果是storePath是文件路径则删除该文件，
     * 如果storePath是目录路径则删除该目录下的所有文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
        if(fileOrPath.exists()){
            if(fileOrPath.isDirectory()){
                File files[] = fileOrPath.listFiles();
                for(int i =0;i<fileOrPath.length(); i++){
                    files[i].delete();
                }
                fileOrPath.delete();
            }
        }


    }

    /**
     * 处理商品缩略图
     *
     * @param thumbnail  Spring自带的文件处理对象
     * @param targetAddr 图片存储路径
     * @return
     */
    public static String generateProductImg(MultipartFile thumbnail, String targetAddr) {
        // 获取随机文件名，防止文件重名
        String realFileName = getRandomFileName();
        // 获取文件扩展名
        String extension = getFileExtension(thumbnail);
        // 在文件夹不存在时创建
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getInputStream()).size(337, 640)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.jpg")), 0.5f)
                    .outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        return relativeAddr;
    }
    /**
     * filePath to MultipartFile
     *
     * @param filePath
     * @throws IOException
     */
    public static MultipartFile path2MultipartFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
                IOUtils.toByteArray(input));
        return multipartFile;
    }

}
