package org.assignment.ex.web.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
  public static final String BACKLOG = "backlog";
  public static final String PORT = "port";
  public static final String ROOT_REPOSITORY = "root";

  private static final String CONFIG_FILE = "config.properties";

  private static Configuration configuration;
  private final Logger log = LoggerFactory.getLogger(getClass());

  private String rootRepositoryPath;
  private Properties properties;

  private Configuration() {
    try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
      properties = new Properties();
      properties.load(input);

      createRepository();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  public static Configuration getInstance() {
    if (configuration == null) {
      configuration = new Configuration();
    }
    return configuration;
  }

  public String getRootRepositoryPath() {
    return rootRepositoryPath;
  }

  private void createRepository() throws IOException {
    File rootRepository = new File(properties.getProperty(ROOT_REPOSITORY));
    rootRepositoryPath = rootRepository.getCanonicalPath();

    if (rootRepository.exists()) {
      if (!deleteFolder(rootRepository)) {
        throw new RuntimeException("Could not delete old repository " + rootRepositoryPath);
      }
    }
    if (!rootRepository.mkdirs()) {
      throw new RuntimeException("Could not create repository " + rootRepositoryPath);
    }
  }

  private boolean deleteFolder(File foler) {
    if (foler.isDirectory()) {
      for (String file : foler.list()) {
        deleteFolder(new File(foler.getPath() + File.separator + file));
      }
      return foler.delete();
    } else {
      return foler.delete();
    }
  }

  public String getProperty(String key) {
    return this.properties.getProperty(key);
  }
}
