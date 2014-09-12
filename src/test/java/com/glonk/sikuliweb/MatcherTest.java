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

import static com.glonk.sikuliweb.SampleImages.BALL;
import static com.glonk.sikuliweb.SampleImages.BALL_FUZZ_COLOR;
import static com.glonk.sikuliweb.SampleImages.BALL_FUZZ_GREY;
import static com.glonk.sikuliweb.SampleImages.BALL_ORIG_COLOR;
import static com.glonk.sikuliweb.SampleImages.BALL_ORIG_GREY;
import static com.glonk.sikuliweb.SampleImages.CUBE;
import static com.glonk.sikuliweb.SampleImages.CUBE_FUZZ_COLOR;
import static com.glonk.sikuliweb.SampleImages.CUBE_FUZZ_GREY;
import static com.glonk.sikuliweb.SampleImages.CUBE_ORIG_COLOR;
import static com.glonk.sikuliweb.SampleImages.CUBE_ORIG_GREY;
import static com.glonk.sikuliweb.SampleImages.FIELD;
import static com.glonk.sikuliweb.SampleImages.getImage;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.sikuli.api.ColorImageTarget;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Location;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.Target.Ordering;
import org.testng.annotations.Test;


public class MatcherTest {

  @Test
  public void testBallColorMatch() throws Exception {
    List<ScreenRegion> matches = colorMatch(BALL, 0.98);
    assertEquals(1, matches.size());
    assertLocation(BALL_ORIG_COLOR, matches.get(0));

    matches = colorMatch(BALL, 0.9);
    assertEquals(2, matches.size());
    assertLocation(BALL_ORIG_COLOR, matches.get(0));
    assertLocation(BALL_FUZZ_COLOR, matches.get(1));
  }

  @Test
  public void testBallColorlessMatch() throws Exception {
    List<ScreenRegion> matches = colorlessMatch(BALL, 0.98);
    assertEquals(2, matches.size());
    assertLocation(BALL_ORIG_COLOR, matches.get(0));
    assertLocation(BALL_ORIG_GREY, matches.get(1));

    matches = colorlessMatch(BALL, 0.9);
    assertEquals(4, matches.size());
    assertLocation(BALL_ORIG_COLOR, matches.get(0));
    assertLocation(BALL_FUZZ_COLOR, matches.get(1));
    assertLocation(BALL_ORIG_GREY, matches.get(2));
    assertLocation(BALL_FUZZ_GREY, matches.get(3));
  }

  @Test
  public void testCubeColorMatch() throws Exception {
    List<ScreenRegion> matches = colorMatch(CUBE, 0.98);
    assertEquals(1, matches.size());
    assertLocation(CUBE_ORIG_COLOR, matches.get(0));

    // Fuzzier match
    matches = colorMatch(CUBE, 0.5);
    assertEquals(2, matches.size());
    assertLocation(CUBE_ORIG_COLOR, matches.get(0));
    assertLocation(CUBE_FUZZ_COLOR, matches.get(1));
  }

  @Test
  public void testCubeColorlessMatch() throws Exception {
    List<ScreenRegion> matches = colorlessMatch(CUBE, 0.98);
    assertEquals(1, matches.size());
    assertLocation(CUBE_ORIG_COLOR, matches.get(0));

    matches = colorlessMatch(CUBE, 0.8);
    assertEquals(4, matches.size());
    assertLocation(CUBE_ORIG_COLOR, matches.get(0));
    assertLocation(CUBE_FUZZ_COLOR, matches.get(1));
    assertLocation(CUBE_ORIG_GREY, matches.get(2));
    assertLocation(CUBE_FUZZ_GREY, matches.get(3));
  }

  private void assertLocation(Location expected, ScreenRegion region) {
    // Wrap the location for clearer error reporting
    assertEquals(expected, new Position(region.getUpperLeftCorner()));
  }

  private List<ScreenRegion> colorMatch(String name, double minScore) throws IOException {
    return doMatch(new ColorImageTarget(getImage(name)), minScore);
  }

  private List<ScreenRegion> colorlessMatch(String name, double minScore) throws IOException {
    return doMatch(new ImageTarget(getImage(name)), minScore);
  }

  private List<ScreenRegion> doMatch(Target target, double minScore) throws IOException {
    target.setOrdering(Ordering.LEFT_RIGHT);
    target.setMinScore(minScore);
    target.setLimit(10);
    MemoryScreenRegion screen = new MemoryScreenRegion(getImage(FIELD));
    return target.doFindAll(screen);
  }

}

