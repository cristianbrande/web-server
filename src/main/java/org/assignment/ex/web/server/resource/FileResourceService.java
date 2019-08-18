package org.assignment.ex.web.server.resource;

import org.apache.commons.io.IOUtils;
import org.assignment.ex.web.server.config.Configuration;
import org.assignment.ex.web.server.exception.FileLocationOutOfRepoException;
import org.assignment.ex.web.server.exception.FileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileResourceService {
  private static final Object lock = new Object();
  private static FileResourceService fileResourceService;
  private Logger log = LoggerFactory.getLogger(getClass());
  private Configuration configuration;

  private FileResourceService() {
    this.configuration = Configuration.getInstance();
  }

  public static FileResourceService getInstance() {
    FileResourceService tempFileResourceController = fileResourceService;

    if (tempFileResourceController == null) {
      synchronized (lock) {
        tempFileResourceController = fileResourceService;
        if (tempFileResourceController == null) {
          tempFileResourceController = new FileResourceService();
          fileResourceService = tempFileResourceController;
        }
      }
    }

    return tempFileResourceController;
  }

  public byte[] getFile(String fileName) {
    log.info("Retrieving file {}", fileName);
    String absoluteFileName = createAbsoluteFileName(fileName);
    try {
      return Files.readAllBytes(Paths.get(absoluteFileName));
    } catch (IOException e) {
      throw new FileNotFoundException();
    }
  }

  public void createFile(String fileName, InputStream fileContent) {
    log.info("Creating file {}", fileName);
    writeToFile(fileName, fileContent);
  }

  public void updateFile(String fileName, InputStream fileContent) {
    log.info("Updating file {}", fileName);
    writeToFile(fileName, fileContent);
  }

  public void deleteFile(String fileName) {
    log.info("Deleting file {}", fileName);
    String absoluteFileName = createAbsoluteFileName(fileName);
    File file = new File(absoluteFileName);
    file.delete();
  }

  private void writeToFile(String fileName, InputStream fileContent) {
    String absoluteFileName = createAbsoluteFileName(fileName);

    try {
      RandomAccessFile stream = new RandomAccessFile(absoluteFileName, "rw");
      FileChannel channel = stream.getChannel();

      try {
        FileLock lock = channel.tryLock();
        stream.write(IOUtils.toByteArray(fileContent));
        lock.release();
        stream.close();
        channel.close();
      } catch (OverlappingFileLockException e) {
        log.error(e.getMessage(), e);
        stream.close();
        channel.close();
      }
    } catch (IOException e) {
      throw new RuntimeException("Error writing to file");
    }
  }

  private String createAbsoluteFileName(String fileName) {
    String absoluteFileName = configuration.getRootRepositoryPath() + File.separator + fileName;

    // check if the file is contained in our root repository. If not, we don't allow any other
    // operation.
    if (!absoluteFileName.startsWith(configuration.getRootRepositoryPath())) {
      throw new FileLocationOutOfRepoException();
    }
    return absoluteFileName;
  }
}
