/*
 * #%L
 * JavaCreed CSV API Examples
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
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javacreed.api.csv.reader.CsvLine;
import com.javacreed.api.csv.reader.CsvReadable;
import com.javacreed.api.csv.reader.CsvReader;

public class CollectionOfObjectsExample {

  private static class Person {
    private final String name;
    private final String surname;
    private final LocalDate dateOfBirth;

    public Person(final String name, final String surname, final String dateOfBirth) {
      this.name = name;
      this.surname = surname;
      this.dateOfBirth = new LocalDate(dateOfBirth);
    }

    @Override
    public String toString() {
      return name + " " + surname + " (" + dateOfBirth + ")";
    }
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(CollectionOfObjectsExample.class);

  public static void main(final String[] args) throws Exception {
    final File file = new File(CollectionOfObjectsExample.class.getSimpleName() + "-Output.csv");
    if (false == file.isFile()) {
      CollectionOfObjectsExample.LOGGER.debug("The file: '{}' does not exists.", file);
      CollectionOfObjectsExample.LOGGER.debug(
          "Please run the writer example: {} first to create this file and then run this again.",
          CollectionOfObjectsExample.class.getCanonicalName().replace("reader", "writer"));
      return;
    }

    final List<Person> persons = new ArrayList<>();

    try (Reader reader = new BufferedReader(
        new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")))) {

      final CsvReader csv = new CsvReader(new CsvReadable(reader));
      csv.readHeaders();
      for (CsvLine line; (line = csv.readLine()) != null;) {
        persons.add(new Person(line.getValue("name"), line.getValue("surname"), line.getValue("dateOfBirth")));
      }
    }

    CollectionOfObjectsExample.LOGGER.debug("Read {} persons", persons.size());
    for (final Person person : persons) {
      CollectionOfObjectsExample.LOGGER.debug("  >> {}", person);
    }
  }
}
