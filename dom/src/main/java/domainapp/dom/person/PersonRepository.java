/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.dom.person;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Person.class
)
public class PersonRepository {

    @Programmatic
    public List<Person> listAll() {
        return container.allInstances(Person.class);
    }

    @Programmatic
    public List<Person> findByName(
            @ParameterLayout(named="Name")
            final String regex
    ) {
        return container.allMatches(
                new QueryDefault<>(
                        Person.class,
                        "findByName",
                        "regex", regex));
    }

    @Programmatic
    public Person findByMemberId(
            Integer memberId){
        return container.uniqueMatch(
                new QueryDefault<>(
                        Person.class,
                        "findByMemberId",
                        "memberId", memberId));
    }

    @Programmatic
    public Person create(final String firstName, final String lastName) {
        final Person obj = container.newTransientInstance(Person.class);
        obj.setFirstName(firstName);
        obj.setLastName(lastName);
        container.persistIfNotAlready(obj);
        return obj;
    }

    @javax.inject.Inject 
    DomainObjectContainer container;

    public Person findByUsername(final String username) {
        return container.uniqueMatch(
                new QueryDefault<>(
                        Person.class,
                        "findByUsername",
                        "username", username));
    }
}
