/**
 * Copyright (c) 2014 Patrick Hensley
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glonk.sikuliweb;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.sikuli.api.ColorImageTarget;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Target;


public class SikuliUtils {

  private static final int BUFFER_SIZE = 8192;

  public static Target readTarget(InputStream stream, boolean useColor) throws IOException {
    BufferedImage image = readImage(stream);
    return useColor ? new ColorImageTarget(image) : new ImageTarget(image);
  }

  public static BufferedImage readImage(InputStream stream) throws IOException {
    try (InputStream is = stream) {
      return ImageIO.read(stream);
    }
  }

  public static BufferedImage base64ToImage(String base64) throws IOException {
    byte[] bytes = Base64.getDecoder().decode(base64);
    return ImageIO.read(new ByteArrayInputStream(bytes));
  }

  public static String imageToBase64(BufferedImage image) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
    ImageIO.write(image, "png", out);
    return Base64.getEncoder().encodeToString(out.toByteArray());
  }

  private SikuliUtils() {
  }

}
