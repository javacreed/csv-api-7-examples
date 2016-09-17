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
      csv.closeAppendableWhenDone();

      final List<Person> persons = new ArrayList<>();
      persons.add(new Person("Albert", "Attard", "2000-09-01"));
      persons.add(new Person("Mary", "White", "1998-07-28"));

      csv.headers("name", "surname", "dateOfBirth");
      for (final Person person : persons) {
        final CsvLine line = csv.line();
        line.setValue("name", person.getName());
        line.setValue("surname", person.getSurname());
        line.setValue("dateOfBirth", person.getDateOfBirth());
      }
    }

    CollectionOfObjectsExample.LOGGER.debug("CSV File {} created", file);
  }
}
