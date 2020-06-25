import org.junit.Test;
import sftp.regular.StreamUpload;

import static org.junit.Assert.*;

public class UploadTest {
    @Test
    public void upload() {
        new StreamUpload().upload();
    }
}