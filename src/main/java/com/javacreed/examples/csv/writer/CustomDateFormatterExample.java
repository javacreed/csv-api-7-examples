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
import com.javacreed.api.csv.writer.DateColumnFormatter;
import com.javacreed.api.csv.writer.DefaultCsvFormatter;

/**
 * Provides an example of how to apply custom formatters to columns of a specific type or specific columns.
 * <p>
 * The CSV output is saved in file: {@code CustomDateFormatterExample-Output.csv}
 *
 * @author Albert Attard
 */
public class CustomDateFormatterExample {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomDateFormatterExample.class);

  public static void main(final String[] args) throws Exception {
    final File file = new File(CustomDateFormatterExample.class.getSimpleName() + "-Output.csv");
    try (CsvWriter csv = new CsvWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8")))) {
      /* Tell the CSV writer to also close the output stream when done */
      csv.closeAppendableWhenDone();

      /*
       * All CVS formatting are managed by the CSV formatter. This example shows how to format the dates using the
       * 'com.javacreed.api.csv.writer.DefaultCsvFormatter'. Note that this can be achieved in two ways. One used the
       * formatter for a type, such as 'java.util.Date' or by specifying a column index. Both are illustrated here.
       */
      final DefaultCsvFormatter.Builder formatterBuilder = new DefaultCsvFormatter.Builder();

      /* All values of type 'java.util.Date', irrespective of the column index, will use this formatter */
      formatterBuilder.register(Date.class, DateColumnFormatter.date());

      /*
       * All values in the forth column will use this formatter. Note that here we are converting long into formatted
       * date
       */
      formatterBuilder.register(DateColumnFormatter.time(), 3);

      /* Set the CSV formatter */
      csv.formatter(formatterBuilder.build());

      csv.headers("A", "B", "C", "D");
      for (int i = 0; i < 15; i++) {
        final CsvLine line = csv.line();
        line.setValue("a", new Date());
        line.setValue("c", "2");
        line.setValue("d", System.currentTimeMillis());
        line.setValue("b", "4");
        line.write();
      }
    }

    CustomDateFormatterExample.LOGGER.debug("CSV File {} created", file);
  }
}
