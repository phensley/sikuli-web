
Sikuli Web
==========

Exposing the Sikuli API as a web service.

This is a quick proof-of-concept for running Sikuli as a web service to provide
access to image recognition functions to other testing frameworks, like
Selenium.


Example
-------

```python

import base64, json, os, sys, urllib2

ROOT = os.path.abspath(os.path.dirname(__file__))
IMAGE_DIR = '../src/test/resources/com/glonk/sikuliweb'
BASE_URL = 'http://localhost:8080'
OPENER = urllib2.build_opener()
MIME_JSON = {'Content-Type': 'application/json'}

def read_image(name):
    'Read an image from disk, returning it as base64'
    path = os.path.join(ROOT, IMAGE_DIR, name)
    return base64.b64encode(open(path, 'rb').read())

def find(**params):
    'Find target image in screen'
    data = json.dumps(params)
    url = BASE_URL + '/find'
    req = urllib2.Request(url, data, headers=MIME_JSON)
    res = OPENER.open(req)
    return json.loads(res.read())

def main():
    res = find(
        screen = read_image('test-field.png'),
        target = read_image('boing-ball.png'),
        minScore = 0.9,
        limit = 5
        )
    print json.dumps(res, indent=2)

if __name__ == '__main__':
    main()

```

*Outputs*:

```json

[
  {
    "y": 24,
    "x": 31,
    "height": 64,
    "score": 0.9999998807907104,
    "width": 64
  },
  {
    "y": 170,
    "x": 42,
    "height": 64,
    "score": 0.9431651830673218,
    "width": 64
  }
]

```

License
-------

    Copyright 2014 Patrick Hensley

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

