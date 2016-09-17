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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javacreed.api.csv.writer.CsvLine;
import com.javacreed.api.csv.writer.CsvWriter;
import com.javacreed.api.csv.writer.DefaultCsvFormatter;

public class CustomValueSeparatorExample {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomValueSeparatorExample.class);

  public static void main(final String[] args) throws Exception {
    final File file = new File(CustomValueSeparatorExample.class.getSimpleName() + "-Output.csv");
    try (CsvWriter csv = new CsvWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8")))) {
      csv.closeAppendableWhenDone();

      final DefaultCsvFormatter.Builder formatter = new DefaultCsvFormatter.Builder();
      formatter.valueSeparator(";");
      csv.formatter(formatter.build());

      csv.headers("A", "B", "C", "D");
      for (int i = 0; i < 15; i++) {
        final CsvLine line = csv.line();
        line.setValue("a", new Date());
        line.setValue("c", "Hello;World");
        line.setValue("b", "4");
      }
    }

    CustomValueSeparatorExample.LOGGER.debug("CSV File {} created", file);
  }
}
