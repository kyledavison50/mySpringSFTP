package sftp.regular;

import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.util.FastByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

public class StreamUpload {
    // Server info
    public DefaultSftpSessionFactory connectionInfo() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
        factory.setHost("0.0.0.0");
        factory.setPort(22);
        factory.setAllowUnknownKeys(true);
        factory.setUser("username");
        factory.setPassword("password");
        return factory;
    }

    public void upload() {
        String testString = "This is a test string to be converted to a byte array";

        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();
        try {
            fastByteArrayOutputStream.write(testString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        SftpSession session = connectionInfo().getSession();

        // Requires InputStream but FastByteArrayOutputStream has an InputStream parameter
        InputStream resourceAsStream = fastByteArrayOutputStream.getInputStream();

        // Will need to change directory for production
        String directory = "ftpReceive/";
        String filename = "autoready" + LocalDateTime.now() + ".txt";

        try {
            // Need to know file destination on the server
            session.write(resourceAsStream, directory + filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        session.close();
    }
}
