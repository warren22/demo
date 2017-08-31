package com.blf.storage;

import org.javaswift.joss.client.factory.AccountFactory;
import org.javaswift.joss.client.factory.AuthenticationMethod;
import org.javaswift.joss.model.Account;
import org.javaswift.joss.model.Container;
import org.javaswift.joss.model.StoredObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;

/**
 * 
 * @author 万明
 *
 */

@Controller
public class StorageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageController.class);

    private static final String SWIFT_STORAGE_URL = "http://10.0.67.51/v1/AUTH_test";
    private static final String SWIFT_STORAGE_USER = "test";
    private static final String SWIFT_STORAGE_PWD = "testing";
    private static final String SWIFT_STORAGE_TENANT = "tester";


    @SuppressWarnings("unused")
	private final Account loginToSwift() {
        return new AccountFactory()
                .setAuthUrl(SWIFT_STORAGE_URL)
                .setUsername(SWIFT_STORAGE_USER)
                .setPassword(SWIFT_STORAGE_PWD)
                .setTenantName(SWIFT_STORAGE_TENANT)
                .setAuthenticationMethod(AuthenticationMethod.BASIC)
                .createAccount();
    }

    private final Account loginToInMemorySwift() {
        return new AccountFactory()
                .setMock(true)
                .setTenantName(SWIFT_STORAGE_TENANT)
                .setMockMillisDelay(250)
                .createAccount();
    }

    private final String generateFilename() {
        return java.util.UUID.randomUUID().toString();
    }

    public String uploadDocument(byte[] newFile, String targetContainer) {
        StopWatch timer = new StopWatch();
        final Container container = loginToInMemorySwift().getContainer(targetContainer);
        if (!container.exists()) {
            container.create();
        }
        final String generatedFilename = generateFilename();

        timer.start();
        final StoredObject newFileObject = container.getObject(generatedFilename);
        if (!newFileObject.exists()) {
            newFileObject.uploadObject(newFile);
        }
        timer.stop();
        LOGGER.info("=== Executed in {} ms for file of size {} bytes", timer.getLastTaskTimeMillis(), newFile.length);
        return newFileObject.getURL();
    }
}
