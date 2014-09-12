package com.glonk.sikuliweb;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


public class SampleImages {

  public static final String BALL = "boing-ball.png";

  public static final String CUBE = "rubiks-cube.png";

  public static final String FIELD = "test-field.png";

  public static final Position BALL_ORIG_COLOR = new Position(31, 24);

  public static final Position BALL_ORIG_GREY = new Position(69, 97);

  public static final Position BALL_FUZZ_COLOR = new Position(42, 170);

  public static final Position BALL_FUZZ_GREY = new Position(106, 216);

  public static final Position CUBE_ORIG_COLOR = new Position(145, 24);

  public static final Position CUBE_ORIG_GREY = new Position(209, 82);

  public static final Position CUBE_FUZZ_COLOR = new Position(163, 146);

  public static final Position CUBE_FUZZ_GREY = new Position(216, 216);

  public static BufferedImage getImage(String name) throws IOException {
    URL url = SampleImages.class.getResource(name);
    return ImageIO.read(url.openStream());
  }

  private SampleImages() {
  }

}
