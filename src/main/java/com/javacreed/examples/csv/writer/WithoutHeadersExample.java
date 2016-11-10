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

/**
 * Provides an example of how to create a CSV without using headers. Note that now you cannot use the column names as
 * these do not exists.
 * <p>
 * The CSV output is saved in file: {@code WithoutHeadersExample-Output.csv}
 *
 * @author Albert Attard
 */
public class WithoutHeadersExample {

  private static final Logger LOGGER = LoggerFactory.getLogger(WithoutHeadersExample.class);

  public static void main(final String[] args) throws Exception {
    final File file = new File(WithoutHeadersExample.class.getSimpleName() + "-Output.csv");
    try (CsvWriter csv = new CsvWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8")))) {
      /* Tell the CSV writer to also close the output stream when done */
      csv.closeAppendableWhenDone();

      /*
       * The number of columns needs to be provided when the headers are not provided as the CSV needs to know how many
       * columns needs to work with.
       */
      csv.columns(4);

      for (int i = 0; i < 15; i++) {
        final CsvLine line = csv.line();
        line.setValue(0, "1");
        line.setValue(1, "2");
        line.setValue(2, "3");
        line.setValue(3, "4");
        line.write();
      }
    }

    WithoutHeadersExample.LOGGER.debug("CSV File {} created", file);
  }
}
