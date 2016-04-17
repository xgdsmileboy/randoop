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

        //��ô����Ƶ��ļ��е����֣���������Ƶ��ļ���Ϊ"E:\\dir\\"���ȡ������Ϊ"dir"
        String dirName = srcFile.getName();

        String destDirPath = destDir + File.separator + dirName + File.separator;
        File destDirFile = new File(destDirPath);
        if(destDirFile.getAbsolutePath().equals(srcFile.getAbsolutePath())) {
         return false;
        }
        if(destDirFile.exists() && destDirFile.isDirectory() && !overwriteExistDir) {    // Ŀ��λ����һ��ͬ���ļ����Ҳ�������ͬ���ļ��У���ֱ�ӷ���false
            return false;
        }

        if(!destDirFile.exists() && !destDirFile.mkdirs()) {  // ���Ŀ��Ŀ¼�����ڲ��Ҵ���Ŀ¼ʧ��
         return false;
        }

        File[] fileList = srcFile.listFiles();    //��ȡԴ�ļ����µ����ļ������ļ���
        if(fileList.length==0) {    // ���Դ�ļ���Ϊ��Ŀ¼��ֱ������flagΪtrue����һ���ǳ����Σ�debug�˺ܾ�
            flag = true;
        }
        else {
            for(File temp: fileList) {
                if(temp.isFile()) {    // �ļ�
                    flag = copyFile(temp.getAbsolutePath(), destDirPath, overwriteExistDir);     // �ݹ鸴��ʱҲ�̳и�������
                }
                else if(temp.isDirectory()) {    // �ļ���
                    flag = copyDirectory(temp.getAbsolutePath(), destDirPath, overwriteExistDir);   // �ݹ鸴��ʱҲ�̳и�������
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
     * file cut operator��copy + delete
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
     /** ���Ը����ļ� */
     System.out.println(copyGeneralFile("d://test/Check.java", "d://test/test/"));  // һ����������
     System.out.println(copyGeneralFile("d://notexistfile", "d://test/"));      // ���Ʋ����ڵ��ļ����ļ���
     System.out.println(copyGeneralFile("d://test/Check.java", "d://test/"));      // �������ļ���Ŀ���ļ���ͬһĿ¼��
     System.out.println(copyGeneralFile("d://test/Check.java", "d://test/test/"));  // ����Ŀ��Ŀ¼�µ�ͬ���ļ�
     System.out.println(copyFile("d://test/", "d://test2", true)); // ������Ŀ��Ŀ¼�µ�ͬ���ļ�
     System.out.println(copyGeneralFile("d://test/Check.java", "notexist://noexistdir/")); // �����ļ���һ�������ܴ���Ҳ�����ܴ�����Ŀ¼��

     System.out.println("---------");

     /** ���Ը����ļ��� */
     System.out.println(copyGeneralFile("d://test/", "d://test2/"));

     System.out.println("---------");

     /** ����ɾ���ļ� */
     System.out.println(deleteFile("d://a/"));
    }

}