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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javacreed.api.csv.writer.CsvLine;
import com.javacreed.api.csv.writer.CsvWriter;
import com.javacreed.api.csv.writer.DefaultCsvFormatter;
import com.javacreed.api.csv.writer.NullColumnFormatterProvider;

public class CustomNullFormatter {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomNullFormatter.class);

  public static void main(final String[] args) throws Exception {
    final File file = new File(CustomNullFormatter.class.getSimpleName() + "-Output.csv");
    try (CsvWriter csv = new CsvWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8")))) {
      csv.closeAppendableWhenDone();

      /*
       * Creates a column formatter provider so that you can customise the formatting of the CSV columns. In this
       * example, we will be using a custom null formatter. Nulls are replaced by the phrase 'THIS-IS-NULL'. Finally we
       * need to set the column formatter and sets the CSV formatter
       */
      final DefaultCsvFormatter.Builder formatter = new DefaultCsvFormatter.Builder();
      formatter.register(new NullColumnFormatterProvider("THIS-IS-NULL"));
      csv.formatter(formatter.build());

      csv.headers("A", "B", "C", "D");
      for (int i = 0; i < 15; i++) {
        final CsvLine line = csv.line();
        line.setValue("a", "1");
        line.setValue("c", "2");
        line.setValue("d", "3");
        line.setValue("b", null); // This will be replaced with: 'THIS-IS-NULL'
      }
    }

    CustomNullFormatter.LOGGER.debug("CSV File {} created", file);
  }
}
