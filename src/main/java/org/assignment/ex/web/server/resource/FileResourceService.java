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
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

/**
 * The service writes and reads from the file repository (disk).
 */
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

  /**
   * Returns the content of the file.
   *
   * @throws FileNotFoundException if the file is not found.
   * @param fileName
   * @return
   */
  public byte[] getFile(String fileName) {
    log.info("Retrieving file {}", fileName);
    String absoluteFileName = createAbsoluteFileName(fileName);
    try {
        RandomAccessFile randomAccessFile = new RandomAccessFile(absoluteFileName, "rw");
        FileChannel channel = randomAccessFile.getChannel();
        FileLock fileLock = channel.lock();

        ByteBuffer fileContentBuff = ByteBuffer.allocate((int) channel.size());
        channel.read(fileContentBuff);
        fileContentBuff.flip();

        fileLock.release();
        randomAccessFile.close();
        channel.close();

        return fileContentBuff.array();
    } catch (IOException e) {
      throw new FileNotFoundException();
    }
  }

  /**
   * Writes the file to the repository.
   *
   * @param fileName
   * @param fileContent
   */
  public void createFile(String fileName, InputStream fileContent) {
    log.info("Creating file {}", fileName);
    writeToFile(fileName, fileContent);
  }

  /**
   * Writes the file to the repository.
   *
   * @param fileName
   * @param fileContent
   */
  public void updateFile(String fileName, InputStream fileContent) {
    log.info("Updating file {}", fileName);
    writeToFile(fileName, fileContent);
  }

  /**
   * Deletes the file from the repository.
   *
   * @param fileName
   */
  public void deleteFile(String fileName) {
    log.info("Deleting file {}", fileName);
    String absoluteFileName = createAbsoluteFileName(fileName);

    try {
      RandomAccessFile randomAccessFile = new RandomAccessFile(absoluteFileName, "rw");
      randomAccessFile.getChannel().lock();
      randomAccessFile.close();
      File file = new File(absoluteFileName);
      file.delete();
    } catch (IOException e) {
      throw new RuntimeException("Could not delete file " + absoluteFileName);
    }
  }

  /**
   * Writes the file to disk, locking the file.
   *
   * @param fileName
   * @param fileContent
   */
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
