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

import static com.glonk.sikuliweb.SikuliUtils.base64ToImage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.sikuli.api.ColorImageTarget;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.Target.Ordering;

import com.fasterxml.jackson.annotation.JsonProperty;


@Path("/find")
public class FindResource {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response post(FindRequest request) {
    try {

      BufferedImage targetImage = base64ToImage(request.target);
      Target target = request.useColor ? new ColorImageTarget(targetImage) : new ImageTarget(targetImage);
      target.setMinScore(request.minScore);
      target.setLimit(request.limit);
      target.setOrdering(request.ordering);

      MemoryScreenRegion screen = new MemoryScreenRegion(base64ToImage(request.screen));
      List<MatchResponse> response = new ArrayList<>();
      for (ScreenRegion region : target.doFindAll(screen)) {
        response.add(new MatchResponse(region));
      }
      return Response.ok().entity(response).build();

    } catch (IOException e) {
      return Response.serverError().entity(e.getMessage()).build();
    }
  }

  public static class FindRequest {

    @JsonProperty
    private String screen;

    @JsonProperty
    private String target;

    @JsonProperty
    private int limit = 1;

    @JsonProperty
    private Ordering ordering = Ordering.DEFAULT;

    @JsonProperty
    private double minScore = 0.9;

    @JsonProperty
    private boolean useColor = true;

  }

  public static class MatchResponse {

    @JsonProperty
    protected int x;

    @JsonProperty
    protected int y;

    @JsonProperty
    protected int width;

    @JsonProperty
    protected int height;

    @JsonProperty
    protected double score;

    public MatchResponse() {
    }

    public MatchResponse(ScreenRegion region) {
      this.x = region.getX();
      this.y = region.getY();
      this.width = region.getWidth();
      this.height = region.getHeight();
      this.score = region.getScore();
    }
  }

}

