/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ambari.logfeeder.output;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.apache.ambari.logfeeder.util.LogFeederUtil;

public class S3LogPathResolverTest {

  @Test
  public void shouldResolveHostName() {
    String resolvedPath = new S3LogPathResolver().getResolvedPath("my_s3_path/$host", "filename.log", "cl1");
    assertEquals("my_s3_path/" + LogFeederUtil.hostName + "/filename.log", resolvedPath);
  }

  @Test
  public void shouldResolveIpAddress() {
    String resolvedPath = new S3LogPathResolver().getResolvedPath("my_s3_path/$ip", "filename.log", "cl1");
    assertEquals("my_s3_path/" + LogFeederUtil.ipAddress + "/filename.log", resolvedPath);
  }

  @Test
  public void shouldResolveCluster() {
    String resolvedPath = new S3LogPathResolver().getResolvedPath("my_s3_path/$cluster", "filename.log", "cl1");
    assertEquals("my_s3_path/cl1/filename.log", resolvedPath);
  }

  @Test
  public void shouldResolveYear() {
    String currentYear = OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy"));
    String resolvedPath = new S3LogPathResolver().getResolvedPath("my_s3_path/$year", "filename.log", "cl1");
    assertEquals("my_s3_path/" + currentYear + "/filename.log", resolvedPath);
  }

  @Test
  public void shouldResolveMonth() {
    String currentMonth = OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("MM"));
    String resolvedPath = new S3LogPathResolver().getResolvedPath("my_s3_path/$month", "filename.log", "cl1");
    assertEquals("my_s3_path/" + currentMonth + "/filename.log", resolvedPath);
  }

  @Test
  public void shouldResolveDay() {
    String currentDay = OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("dd"));
    String resolvedPath = new S3LogPathResolver().getResolvedPath("my_s3_path/$day", "filename.log", "cl1");
    assertEquals("my_s3_path/" + currentDay + "/filename.log", resolvedPath);
  }

  @Test
  public void shouldResolveHour() {
    String currentHour = OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("HH"));
    String resolvedPath = new S3LogPathResolver().getResolvedPath("my_s3_path/$hour", "filename.log", "cl1");
    assertEquals("my_s3_path/" + currentHour + "/filename.log", resolvedPath);
  }

  @Test
  public void shouldResolveCombinations() {
    String date = OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH"));
    String resolvedPath = new S3LogPathResolver().getResolvedPath("my_s3_path/$year-$month-$day-$hour/$cluster/$host", "filename.log", "cl1");
    assertEquals("my_s3_path/" + date + "/cl1/" + LogFeederUtil.hostName + "/filename.log", resolvedPath);
  }
}
