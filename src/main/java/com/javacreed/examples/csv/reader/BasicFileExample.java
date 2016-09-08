/*
 * #%L
 * JavaCreed CSV API
 * %%
 * Copyright (C) 2012 - 2016 Java Creed
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.javacreed.examples.csv.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import com.javacreed.api.csv.reader.CsvLine;
import com.javacreed.api.csv.reader.CsvReader;
import com.javacreed.api.csv.reader.CsvReadable;

public class BasicFileExample {
  public static void main(final String[] args) throws Exception {
    final File file = new File(BasicFileExample.class.getSimpleName() + "-Output.csv");
    try (Reader reader = new BufferedReader(
        new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")))) {

      final CsvReader csv = new CsvReader(new CsvReadable(reader));
      csv.readHeaders();
      for (CsvLine line; (line = csv.readLine()) != null;) {
        final String cell = line.getValue("a");
        System.out.println(cell);
      }
    }
  }
}
