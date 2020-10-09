package eu.mrndesign.matned.portfolioapp.service;

import eu.mrndesign.matned.portfolioapp.ftp.FtpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FilesService {

    @Value("${ftp.server.host}")
    private String ftpHost;

    @Value("${ftp.server.port}")
    private Integer ftpPort;

    @Value("${ftp.server.user}")
    private String ftpUser;

    @Value("${ftp.server.password}")
    private String ftpPassword;

    public boolean multipleFilesUpload(MultipartFile[] files, String[] fileNames) {
        try {
            FtpClient ftp = startFtpClient();
            for (int i = 0; i < files.length; i++) {
                putFileOnFTP(files[i], fileNames[i], ftp);
            }
            ftp.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP exception found while adding files");
        }
    }

    public boolean fileUpload(MultipartFile file, String fileName) {
        try {
            FtpClient ftp = startFtpClient();
            putFileOnFTP(file, fileName, ftp);
            ftp.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP exception found while adding files");
        }
    }

    void deleteFile(String path) {
        try {
            FtpClient ftp = startFtpClient();
            ftp.deleteFile(path);
            ftp.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP exception found while deleting file");
        }
    }

    void deleteMultipleFiles(String[] paths) {
        try {
            FtpClient ftp = startFtpClient();
            for (String path :
                    paths) {
                ftp.deleteFile(path);
            }
            ftp.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP exception found while deleting file");
        }
    }

    private void putFileOnFTP(MultipartFile file, String fileName, FtpClient ftp) throws IOException {
        File physicalFile = File.createTempFile(System.currentTimeMillis() + "tmp", "jpg");
        file.transferTo(physicalFile);
        ftp.putFileToPath(physicalFile, fileName);
    }

    private FtpClient startFtpClient() throws IOException {
        FtpClient ftp = new FtpClient(ftpHost, ftpPort, ftpUser, ftpPassword);
        ftp.open();
        return ftp;
    }
}
