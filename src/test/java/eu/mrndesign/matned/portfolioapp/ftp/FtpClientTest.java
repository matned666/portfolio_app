package eu.mrndesign.matned.portfolioapp.ftp;

import org.junit.jupiter.api.*;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class FtpClientTest {

    private FakeFtpServer fakeFtpServer;

    private FtpClient ftpClient;

    @BeforeEach
    public void setup() throws IOException {
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount("user", "password", "/data"));

        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/data"));
        fileSystem.add(new FileEntry("/data/foobar.txt", "abcdef 1234567890"));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(0);

        fakeFtpServer.start();

        ftpClient = new FtpClient("localhost", fakeFtpServer.getServerControlPort(), "user", "password");
        ftpClient.open();
    }

    @Test
    public void givenRemoteFile_whenListingRemoteFiles_thenItIsContainedInList() throws IOException {
        Collection<String> files = ftpClient.listFiles("");
        assertTrue(files.contains("foobar.txt"));
    }

    @Test
    public void givenRemoteFile_whenDownloading_thenItIsOnTheLocalFilesystem() throws IOException {
        ftpClient.downloadFile("/buz.txt", "downloaded_buz.txt");
        assertTrue(new File("downloaded_buz.txt").exists());
        new File("downloaded_buz.txt").delete(); // cleanup
    }

    @Test
    public void givenLocalFile_whenUploadingIt_thenItExistsOnRemoteLocation()
            throws URISyntaxException, IOException {
        String tmpdir = System.getProperty("java.io.tmpdir");
        File file = new File(tmpdir+"/buz.txt");
        ftpClient.putFileToPath(file, "/buz.txt");
        assertTrue(fakeFtpServer.getFileSystem().exists("/buz.txt"));
    }

    @AfterEach
    public void teardown() throws IOException {
        ftpClient.close();
        fakeFtpServer.stop();
    }
}