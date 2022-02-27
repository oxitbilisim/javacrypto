package com.svn.app.core.service;

import com.google.auth.Credentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.svn.app.core.config.FileStorageProperties;
import com.svn.app.core.util.FileStorageException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;
    private final Boolean local;
    private Bucket bucket;
    /*String credentialDetail = "{\n" +
            "  \"type\": \"service_account\",\n" +
            "  \"project_id\": \"xypros\",\n" +
            "  \"private_key_id\": \"f9c92150fb9120d658fff65d49b4a4ea1a791f62\",\n" +
            "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQChfpq15guGOeKc\\nbsPEDPr/NRzuekZ/L6XAb2TPq4zLZldNis6BPa2ZoGCGovBIPNd3ogjtxFTalN5j\\nh7EbnfAXybl7WqflX2wEvHktCr9nxq/90ZqF0FL/Whwtv6ainrGFFAjz2HLrrDfa\\nauGYqOwxSZZn7u05W+qQVwaOiwk8DsjR8Mzu/EZZn4BvG+kT+tDQcGWh4m0DR0+3\\nDGD9hRDn1M4WaFZo+ycnX+E75TF58FheE0/ONKJOznb7tjOGvOU+xwvys3ECfq4o\\nK4TWTlPw+ER+9hHoXPaPqvqv1sWBTGDP3XMy7o1Plq2KiwkVYRseqozugnwR9VaS\\noXcRF7vLAgMBAAECggEAS42ECsqDpLnLC12Ep+Fcwe3Syb0Z4yHi/UsAEmoTNgt8\\nl5Hzd6Jn58IQtpxfJqSUN2tumvIpspNIEa9nS+DRdQeSh6PLfbNtQS/9zvVCw+W/\\n61LW5Hiog79gBIUvi9ALhBNx9sbSSPCqtrwfLAEHcy2WuKwefqHCdOHJ9wEUAmy3\\nlzyGyZncx5W6kpK3j5QYQdcERje2b8Z0alRasCTnBEwSN8XMmA5UdjJCF5tG3PcJ\\nD0hFvj191Zf3iD5/vGBO10wKfasIKYl5eZZzzL/Gqp6Ug0dHYXvUTK/h3jWCe4VU\\n8Dg/7G0hAXBLVcQ24zG6RwIx//imYYyhQ9FiieoecQKBgQDZfJeVFMMlwwGa17Th\\ngfsZxHiEX8EH5v44VHhd2boCepVYVxa2OOMhhg+CoLj/zFb066NuQxqnqXS2bmYP\\n4ufQDP1X5SUf8NNnAk6m7xwkJRoDpuNwsYxFRY5g3FmlWsKMh/w63OBR23fQa3u0\\nTdCGnoIjvWbBRdBayeOoEM4GOwKBgQC+F7Lu86vTUGoA276ywOnYSWOd5qHr+dlQ\\nn2s0M5b2YwkkYfm04Q/uYXakKsB2/mK6bOLR8kY8KevowMu7oZglczlVdJr2ooxs\\nFvr5300LKNvVw0eyp2+NUdeDL4jkgoBbZ5GZFaa4RiIWq3b5+atPrpkYg2qfB40V\\nIScxbUB3sQKBgB8SSMm4vKShdpI7rRDmtAwbYKhw0bAiVUGQ991YpNWD0+BaGNmR\\nyQlaEM81zRhmhinZnHC9T4qSkCUcIqK0ZS/5Gxy6lRI6Ism9r6Tz1k5nqo7JAgdw\\npjyjUlMOSBRyogbXyrZSJg4rz7NMF9FLqLYC4U7f2VVVdw3xd//j3rtPAoGACrM4\\nzjyHGoD3Ro8X/+XeA9+1rMizqp3IvsIKfci9oabXd+XP8X+NrwRrcjg51zjgg8/n\\nQJwbmf1eGx7nGOuyrcD7pDTJGEL0Fo2AmRoZsxwcq76vRX4pSFIoCGlfkkVKRlrh\\nFjXiyZKhDpeePdnw665PTdLv6H27Ukooc57LtzECgYBFStAKBoD8IQtrqriZ38/L\\nPxUPo/OVhfT7DsK8O+Z6IWBsNwvqWDvGx9aqrgeZAxhd8oLeTXYIHI5A5O0Fp5op\\nqYwyDQTYmncuSn5fsmIQXzgs0EHPJr/kc6oNvn+EJyFDBa8G4bbk6EsfgilHXwDc\\nFyXAXm1foC+/0z0BAtrvqA==\\n-----END PRIVATE KEY-----\\n\",\n" +
            "  \"client_email\": \"194020810329-compute@developer.gserviceaccount.com\",\n" +
            "  \"client_id\": \"114253079000913594974\",\n" +
            "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
            "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
            "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
            "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/194020810329-compute%40developer.gserviceaccount.com\"\n" +
            "}\n";*/
    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        Credentials credentials = null;
        Storage storage = null;
        bucket = null;
        local = fileStorageProperties.getLocal();
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        if (local) {
            try {
                Files.createDirectories(this.fileStorageLocation);
            } catch (Exception ex) {
//            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
            }
        } else { //google cloud
            try {
                credentials = null;//GoogleCredentials.fromStream(new ByteArrayInputStream(credentialDetail.getBytes()));
                storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId("xypros").build().getService();
                bucket = storage.get("xypros-bucket");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public String storeFile(MultipartFile file) {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = generateFileName() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (local) {
            try {
                // Check if the file's name contains invalid characters
                if (fileName.contains("..")) {
                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                }

                // Copy file to the target location (Replacing existing file with the same name)
                //fileName=TenantContext.getTenantId() + "_" + fileName;
                Path targetLocation = this.fileStorageLocation.resolve(fileName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                return fileName;
            } catch (IOException ex) {
                throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
            }
        } else {
            try {
                //fileName=TenantContext.getTenantId() + "/" + fileName;
                Blob blob = bucket.create(fileName, file.getInputStream());
                return fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Resource loadFileAsResource(String fileName) {
        if (local) {
            try {
                Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
                Resource resource = new UrlResource(filePath.toUri());
                if (resource.exists()) {
                    return resource;
                } else {
                    System.out.println("File not found " + fileName);
                    return null;
//                throw new MyFileNotFoundException("File not found " + fileName);
                }
            } catch (MalformedURLException ex) {
//            throw new MyFileNotFoundException("File not found " + fileName, ex);
                ex.printStackTrace();
                return null;
            }
        } else {
            Blob blob = bucket.get(fileName);
            if (blob != null)
                return new ByteArrayResource(blob.getContent());
            else
                return null;
        }
    }

    private String generateFileName() {
        String filename = "";
        long millis = System.currentTimeMillis();
        String datetime = new Date().toGMTString();
        datetime = datetime.replace(" ", "");
        datetime = datetime.replace(":", "");
        String rndchars = RandomStringUtils.randomAlphanumeric(16);
        filename = rndchars + "_" + datetime + "_" + millis;
        return filename;
    }

}
