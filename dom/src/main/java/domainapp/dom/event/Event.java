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
package domainapp.dom.event;

import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.menu.Menu;
import domainapp.dom.menu.MenuRepository;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Event"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.event.Event "),
        @javax.jdo.annotations.Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.event.Event "
                        + "WHERE name.indexOf(:name) >= 0 ")
})
@javax.jdo.annotations.Unique(name="Event_name_UNQ", members = {"name"})
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT,
        cssClassFa = "fa-flag"
)
public class Event implements Comparable<Event> {


    //region > identificatiom
    public TranslatableString title() {
        return TranslatableString.tr("Object: {name}", "name", getName());
    }
    //endregion

    //region > name (property)

    private String name;

    @javax.jdo.annotations.Column(allowsNull="false", length = 40)
    @Title(sequence="1")
    @Property
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    // endregion

    //region > updateName (action)

    @Action
    public Event updateName(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "New name")
            final String name) {
        setName(name);
        return this;
    }

    public String default0UpdateName() {
        return getName();
    }

    public TranslatableString validateUpdateName(final String name) {
        return name.contains("!")? TranslatableString.tr("Exclamation mark is not allowed"): null;
    }

    //endregion

    //region > date (property)
    private LocalDate date;

    @Column(allowsNull = "false")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }
    //endregion

    //region > menu (derived property)
    private Menu menu;

    @javax.jdo.annotations.NotPersistent // REVIEW: alternatively, map a 1:1 relationship
    public Menu getMenu() {
        return menuRepository.findByEvent(this);
    }

    //endregion
    
    //region > version (derived property)
    public Long getVersionSequence() {
        return (Long) JDOHelper.getVersion(this);
    }
    //endregion

    //region > compareTo

    @Override
    public int compareTo(final Event other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > injected services

    @Inject
    MenuRepository menuRepository;

    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    //endregion


}