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

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import org.sikuli.api.Screen;


public class MemoryScreen implements Screen {

  private final BufferedImage image;

  private final Dimension size;

  public MemoryScreen(BufferedImage image) {
    this.image = image;
    this.size = new Dimension(image.getWidth(), image.getHeight());
  }

  @Override
  public BufferedImage getScreenshot(int x, int y, int width, int height) {
    return image.getSubimage(x, y, width, height);
  }

  @Override
  public Dimension getSize() {
    return size;
  }

}
