package com.glonk.sikuliweb;

import io.dropwizard.Configuration;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jersey.DropwizardResourceConfig;
import io.dropwizard.jersey.jackson.JacksonMessageBodyProvider;
import io.dropwizard.logging.LoggingFactory;
import io.dropwizard.setup.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.LowLevelAppDescriptor;


public abstract class ResourceTest {

  protected final ObjectMapper objectMapper = Jackson.newObjectMapper();

  protected final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  protected final MetricRegistry metricRegistry = new MetricRegistry();

  protected final Environment environment = new Environment("test", objectMapper, validator,
      metricRegistry, Thread.currentThread().getContextClassLoader());

  private final Set<Object> singletons = new HashSet<>();

  private final Set<Class<?>> providers = new HashSet<>();

  private final Map<String, Boolean> features = new HashMap<>();

  private final Map<String, Object> properties = new HashMap<>();

  private JerseyTest testCase;

  static {
    LoggingFactory.bootstrap();
  }

  public void addFeature(String feature, Boolean value) {
    features.put(feature, value);
  }

  public void addProperty(String property, Object value) {
    properties.put(property, value);
  }

  public void addProviders(Class<?>... providers) {
    for (Class<?> provider : providers) {
      this.providers.add(provider);
    }
  }

  public void addResources(Object... resources) {
    for (Object resource : resources) {
      singletons.add(resource);
    }
  }

  public void addSingletons(Object... providers) {
    for (Object provider : providers) {
      singletons.add(provider);
    }
  }

  public Client client() {
    return testCase.client();
  }

  public JerseyTest getJerseyTest() {
    return testCase;
  }

  @AfterMethod
  protected void afterMethod() throws Exception {
    if (testCase != null) {
      testCase.tearDown();
    }
  }

  @BeforeMethod
  protected void beforeMethod() throws Exception {
    singletons.clear();
    providers.clear();
    features.clear();
    properties.clear();
    setupResources();

    testCase = new JerseyTest() {
      @Override
      protected AppDescriptor configure() {
        final DropwizardResourceConfig config = DropwizardResourceConfig.forTesting(metricRegistry);
        for (Class<?> provider : providers) {
          config.getClasses().add(provider);
        }
        for (Map.Entry<String, Boolean> feature : features.entrySet()) {
          config.getFeatures().put(feature.getKey(), feature.getValue());
        }
        for (Map.Entry<String, Object> property : properties.entrySet()) {
          config.getProperties().put(property.getKey(), property.getValue());
        }
        config.getSingletons().add(new JacksonMessageBodyProvider(objectMapper, validator));
        config.getSingletons().addAll(singletons);
        return new LowLevelAppDescriptor.Builder(config).build();
      }
    };

    testCase.setUp();
  }

  /**
   * Returns a configuration object read in from the {@code fileName}.
   */
  protected <T extends Configuration> T getConfiguration(String filename, Class<T> configurationClass)
      throws Exception {

    final ConfigurationFactory<T> configurationFactory = new ConfigurationFactory<>(
        configurationClass, validator, objectMapper, "dw");
    if (filename != null) {
      final File file = new File(Resources.getResource(filename).getFile());
      if (!file.exists()) {
        throw new FileNotFoundException("File " + file + " not found");
      }
      return configurationFactory.build(file);
    }

    return configurationFactory.build();
  }

  protected abstract void setupResources() throws Exception;

}