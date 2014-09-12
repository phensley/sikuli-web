package com.glonk.sikuliweb;

import static com.glonk.sikuliweb.SampleImages.BALL;
import static com.glonk.sikuliweb.SampleImages.BALL_FUZZ_COLOR;
import static com.glonk.sikuliweb.SampleImages.BALL_ORIG_COLOR;
import static com.glonk.sikuliweb.SampleImages.FIELD;
import static com.glonk.sikuliweb.SampleImages.getImage;
import static com.glonk.sikuliweb.SikuliUtils.imageToBase64;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.testng.annotations.Test;

import com.glonk.sikuliweb.FindResource.MatchResponse;
import com.sun.jersey.api.client.GenericType;


public class FindResourceTest extends ResourceTest {

  protected void setupResources() throws Exception {
    addResources(new FindResource());
  };

  @Test
  public void testFind() throws IOException {
    Map<String, Object> map = mapBuilder()
        .put("screen", imageToBase64(getImage(FIELD)))
        .put("target", imageToBase64(getImage(BALL)))
        .put("minScore", 0.9)
        .put("limit", 2)
        .put("ordering", "TOP_DOWN")
        .build();

    List<MatchResponse> res = client().resource("/find")
        .type(MediaType.APPLICATION_JSON)
        .post(new GenericType<List<MatchResponse>>() { }, map);

    assertEquals(res.size(), 2);
    assertEquals(res.get(0).x, BALL_ORIG_COLOR.getX());
    assertEquals(res.get(0).y, BALL_ORIG_COLOR.getY());
    assertEquals(res.get(1).x, BALL_FUZZ_COLOR.getX());
    assertEquals(res.get(1).y, BALL_FUZZ_COLOR.getY());
  }

  private MapBuilder<String, Object> mapBuilder() {
    return MapBuilder.<String, Object>mapBuilder();
  }

}
