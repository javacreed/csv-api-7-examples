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
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javacreed.api.csv.writer.CsvLine;
import com.javacreed.api.csv.writer.CsvWriter;

/**
 * Provides an example that shows how to write a collection of objects as a CSV file.
 * <p>
 * The CSV output is saved in file: {@code CollectionOfObjectsExample-Output.csv}
 *
 * @author Albert
 */
public class CollectionOfObjectsExample {

  /**
   * A basic value object used to demonstrate how to format collections of objects into CSV
   *
   * @author Albert Attard
   */
  private static class Person {
    private final String name;
    private final String surname;
    private final LocalDate dateOfBirth;

    public Person(final String name, final String surname, final String dateOfBirth) {
      this.name = name;
      this.surname = surname;
      this.dateOfBirth = new LocalDate(dateOfBirth);
    }

    public LocalDate getDateOfBirth() {
      return dateOfBirth;
    }

    public String getName() {
      return name;
    }

    public String getSurname() {
      return surname;
    }
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(CollectionOfObjectsExample.class);

  public static void main(final String[] args) throws Exception {
    final File file = new File(CollectionOfObjectsExample.class.getSimpleName() + "-Output.csv");
    try (CsvWriter csv = new CsvWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8")))) {
      /* Tell the CSV writer to also close the output stream when done */
      csv.closeAppendableWhenDone();

      /* Create the collection to be formatted into CSV */
      final List<Person> persons = new ArrayList<>();
      persons.add(new Person("Albert", "Attard", "2000-09-01"));
      persons.add(new Person("Mary", "White", "1998-07-28"));

      /* Create the headers */
      csv.headers("Name", "Surname", "DateOfBirth");

      /*
       * Write the collection. Note that the headers are all written in lower case. This is a simple example to show
       * that when using the default headers, the column names are case-insensitive. We do not have a mapper yet, but
       * this is something which may be added at a later stage.
       */
      for (final Person person : persons) {
        final CsvLine line = csv.line();
        line.setValue("name", person.getName());
        line.setValue("surname", person.getSurname());
        line.setValue("dateofbirth", person.getDateOfBirth());
        line.write();
      }
    }

    CollectionOfObjectsExample.LOGGER.debug("CSV File {} created", file);
  }
}
