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
package com.javacreed.examples.csv.writer;

import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javacreed.api.csv.writer.CsvLine;
import com.javacreed.api.csv.writer.CsvWriter;

public class BasicExample {

  private static final Logger LOGGER = LoggerFactory.getLogger(BasicExample.class);

  public static void main(final String[] args) throws Exception {
    final StringWriter writer = new StringWriter();
    try (CsvWriter csv = new CsvWriter(writer)) {
      csv.closeAppendableWhenDone();

      csv.headers("A", "B", "C", "D");
      for (int i = 0; i < 15; i++) {
        final CsvLine line = csv.line();
        line.setValue("a", "1");
        line.setValue("c", "2");
        line.setValue("d", "3");
        line.setValue("b", "4");
      }
    }

    BasicExample.LOGGER.debug("CSV String created\n{}", writer.toString());
  }
}
