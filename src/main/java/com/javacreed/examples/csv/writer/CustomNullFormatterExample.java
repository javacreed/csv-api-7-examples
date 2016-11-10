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

/**
 * Provides an example of how to deal with null values. In this example, all {@code null} values are written as
 * {@code THIS-IS-NULL}, but this can be any arbitrary, non-{@code null}, string. There are two ways to achieve this.
 * Either directly through the builder ({@link DefaultCsvFormatter.Builder}) or by providing a custom formatter provider
 * ({@link NullColumnFormatterProvider}).
 * <p>
 * The CSV output is saved in file: {@code CustomNullFormatterExample-Output.csv}
 *
 * @author Albert Attard
 */
public class CustomNullFormatterExample {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomNullFormatterExample.class);

  public static void main(final String[] args) throws Exception {
    final File file = new File(CustomNullFormatterExample.class.getSimpleName() + "-Output.csv");
    try (CsvWriter csv = new CsvWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8")))) {
      /* Tell the CSV writer to also close the output stream when done */
      csv.closeAppendableWhenDone();

      /*
       * In this example, we will be using a custom 'null' formatter. There are two ways how the {@code null} formatter
       * can be changed. The easiest way is to use the 'replaceNullsWith()' method as shown next. Alternatively one can
       * use the provider to have more control on the order when this is invoked. Nulls are treated as a special case
       * and you do not need to specify the column type or index. Nulls are replaced by the phrase 'THIS-IS-NULL'.
       * Finally we need to set the formatter
       */
      final DefaultCsvFormatter.Builder formatterBuilder = new DefaultCsvFormatter.Builder();

      /* Nulls are replaced by the phrase 'THIS-IS-NULL' */
      formatterBuilder.replaceNullsWith("THIS-IS-NULL");

      /* An alternative approach which provides more control should need be */
      // formatter.register(new NullColumnFormatterProvider("THIS-IS-NULL"));

      /* Set the CSV formatter */
      csv.formatter(formatterBuilder.build());

      csv.headers("A", "B", "C", "D");
      for (int i = 0; i < 15; i++) {
        final CsvLine line = csv.line();
        line.setValue("a", "1");
        line.setValue("c", "2");
        line.setValue("d", "3");
        line.setValue("b", null); // This will be replaced with: 'THIS-IS-NULL'
        line.write();
      }
    }

    CustomNullFormatterExample.LOGGER.debug("CSV File {} created", file);
  }
}
