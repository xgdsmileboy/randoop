package randoop.variation.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileOperate {

    /**
     * copy a file or directory
     * @param srcPath the path of source file or directory
     * @param destDir the path of target directory
     * @return
     */
    public static boolean copyGeneralFile(String srcPath, String destDir) {
        boolean flag = false;
        File file = new File(srcPath);
        if(!file.exists()) {
            return false;
        }

        if(file.isFile()) {
            flag = copyFile(srcPath, destDir);
        }
        else if(file.isDirectory()) {
            flag = copyDirectory(srcPath, destDir);
        }

        return flag;
    }

    /**
     * copy the source file into the target and the file will be over-write if it exists by default
     * @param srcPath
     *            absolute path of source file
     * @param destDir
     *            directory of target file
     * @return boolean
     */
    public static boolean copyFile(String srcPath, String destDir) {
     return copyFile(srcPath, destDir, true/**overwriteExistFile*/);
    }

    /**
     * copy the source directory into the target directory, over-write by default
     * @param srcPath    the path of source file
     * @param destPath    the path of target directory
     * @return boolean
     */
    public static boolean copyDirectory(String srcPath, String destDir) {
     return copyDirectory(srcPath, destDir, true/**overwriteExistDir*/);
    }

    /**
     * copy files into the target directory
     * @param srcPath
     *            absolute path of source files
     * @param destDir
     *            the path of target files
     * @param overwriteExistFile
     *            over-write the files with the same name or not
     * @return boolean
     */
    public static boolean copyFile(String srcPath, String destDir, boolean overwriteExistFile) {
        boolean flag = false;

        File srcFile = new File(srcPath);
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }

        String fileName = srcFile.getName();
        String destPath = destDir + File.separator +fileName;
        File destFile = new File(destPath);
        if (destFile.getAbsolutePath().equals(srcFile.getAbsolutePath())) {
            return false;
        }
        if(destFile.exists() && !overwriteExistFile) {
            return false;
        }

        File destFileDir = new File(destDir);
        if(!destFileDir.exists() && !destFileDir.mkdirs()) {
         return false;
        }

        try {
            FileInputStream fis = new FileInputStream(srcPath);
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] buf = new byte[1024];
            int c;
            while ((c = fis.read(buf)) != -1) {
                fos.write(buf, 0, c);
            }
            fos.flush();
            fis.close();
            fos.close();

            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 
     * @param srcPath    the path of source directory
     * @param destPath    the path of target directory
     * @return
     */
    public static boolean copyDirectory(String srcPath, String destDir, boolean overwriteExistDir) {
        if(destDir.contains(srcPath))
           return false;
        boolean flag = false;

        File srcFile = new File(srcPath);
        if (!srcFile.exists() || !srcFile.isDirectory()) {
            return false;
        }

        //获得待复制的文件夹的名字，比如待复制的文件夹为"E:\\dir\\"则获取的名字为"dir"
        String dirName = srcFile.getName();

        String destDirPath = destDir + File.separator + dirName + File.separator;
        File destDirFile = new File(destDirPath);
        if(destDirFile.getAbsolutePath().equals(srcFile.getAbsolutePath())) {
         return false;
        }
        if(destDirFile.exists() && destDirFile.isDirectory() && !overwriteExistDir) {    // 目标位置有一个同名文件夹且不允许覆盖同名文件夹，则直接返回false
            return false;
        }

        if(!destDirFile.exists() && !destDirFile.mkdirs()) {  // 如果目标目录不存在并且创建目录失败
         return false;
        }

        File[] fileList = srcFile.listFiles();    //获取源文件夹下的子文件和子文件夹
        if(fileList.length==0) {    // 如果源文件夹为空目录则直接设置flag为true，这一步非常隐蔽，debug了很久
            flag = true;
        }
        else {
            for(File temp: fileList) {
                if(temp.isFile()) {    // 文件
                    flag = copyFile(temp.getAbsolutePath(), destDirPath, overwriteExistDir);     // 递归复制时也继承覆盖属性
                }
                else if(temp.isDirectory()) {    // 文件夹
                    flag = copyDirectory(temp.getAbsolutePath(), destDirPath, overwriteExistDir);   // 递归复制时也继承覆盖属性
                }

                if(!flag) {
                    break;
                }
            }
        }

        return flag;
    }

    /**
     * delete files or directory
     * @param path
     *            absolute path of deleting file
     * @return boolean
     */
    public static boolean deleteFile(String path) {
        boolean flag = false;

        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        flag = file.delete();

        return flag;
    }

    
    /**
     * file cut operator：copy + delete
     * @param  destDir 
     */
    public static boolean cutGeneralFile(String srcPath, String destDir) {
     boolean flag = false;
        if(copyGeneralFile(srcPath, destDir) && deleteFile(srcPath)) {
         flag = true;
        }

        return flag;
    }

    public static void main(String[] args) {
     /** 测试复制文件 */
     System.out.println(copyGeneralFile("d://test/Check.java", "d://test/test/"));  // 一般正常场景
     System.out.println(copyGeneralFile("d://notexistfile", "d://test/"));      // 复制不存在的文件或文件夹
     System.out.println(copyGeneralFile("d://test/Check.java", "d://test/"));      // 待复制文件与目标文件在同一目录下
     System.out.println(copyGeneralFile("d://test/Check.java", "d://test/test/"));  // 覆盖目标目录下的同名文件
     System.out.println(copyFile("d://test/", "d://test2", true)); // 不覆盖目标目录下的同名文件
     System.out.println(copyGeneralFile("d://test/Check.java", "notexist://noexistdir/")); // 复制文件到一个不可能存在也不可能创建的目录下

     System.out.println("---------");

     /** 测试复制文件夹 */
     System.out.println(copyGeneralFile("d://test/", "d://test2/"));

     System.out.println("---------");

     /** 测试删除文件 */
     System.out.println(deleteFile("d://a/"));
    }

}