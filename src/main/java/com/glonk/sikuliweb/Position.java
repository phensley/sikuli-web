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

import org.sikuli.api.Location;

/**
 * Implements Location interface so that TestNG can assert a location's
 * X and Y values together.
 */
public final class Position implements Location {

  private final int x;

  private final int y;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Position(Location loc) {
    this(loc.getX(), loc.getY());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Location) {
      Location other = (Location)obj;
      return getX() == other.getX() && getY() == other.getY();
    }
    return false;
  }

  @Override
  public String toString() {
    return "Location(x=" + getX() + ", y=" + getY() + ")";
  }

  @Override
  public int hashCode() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getY() {
    return y;
  }

  @Override
  public void setX(int x) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setY(int y) {
    throw new UnsupportedOperationException();
  }

}