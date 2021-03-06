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
package domainapp.app;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.dom.DomainAppDomainModule;
import domainapp.fixture.DomainAppFixtureModule;

/**
 * Bootstrap the application.
 */
public class DomainAppAppManifest implements AppManifest {

    /**
     * Load all services and entities found in (the packages and subpackages within) these modules
     */
    @Override
    public List<Class<?>> getModules() {
        return Arrays.asList(
//                org.isisaddons.module.audit.AuditModule.class,
//                org.isisaddons.module.command.CommandModule.class,
                org.isisaddons.module.devutils.DevUtilsModule.class,
                org.isisaddons.module.docx.DocxModule.class,
//                org.isisaddons.module.commchannel.CommChannelModule.class,
//                org.isisaddons.module.event.EventModule.class,
                org.isisaddons.module.excel.ExcelModule.class,
//                org.isisaddons.module.publishing.PublishingModule.class,
//                org.isisaddons.module.sessionlogger.SessionLoggerModule.class,
                org.isisaddons.module.security.SecurityModule.class,
                org.isisaddons.module.settings.SettingsModule.class, // needed for themes.
                org.isisaddons.wicket.fullcalendar2.cpt.ui.FullCalendar2UiModule.class,
                org.isisaddons.wicket.gmap3.cpt.service.Gmap3ServiceModule.class,
                org.isisaddons.wicket.gmap3.cpt.ui.Gmap3UiModule.class,
                DomainAppDomainModule.class,  // domain (entities and repositories)
                DomainAppFixtureModule.class,
                DomainAppAppModule.class
                );
    }

    /**
     * No additional services.
     */
    @Override
    public List<Class<?>> getAdditionalServices() {
        return Arrays.asList(
                org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt.class
                , org.isisaddons.module.publishing.dom.eventserializer.RestfulObjectsSpecEventSerializer.class
        );
    }

    /**
     * Use shiro for authentication.
     *
     * <p>
     *     NB: this is ignored for integration tests, which always use "bypass".
     * </p>
     */
    @Override
    public String getAuthenticationMechanism() {
        return "shiro";
    }

    /**
     * Use shiro for authorization.
     *
     * <p>
     *     NB: this is ignored for integration tests, which always use "bypass".
     * </p>
     */
    @Override
    public String getAuthorizationMechanism() {
        return "shiro";
    }

    /**
     * No fixtures.
     */
    @Override
    public List<Class<? extends FixtureScript>> getFixtures() {
        return Collections.emptyList();
    }

    /**
     * No overrides.
     */
    @Override
    public Map<String, String> getConfigurationProperties() {
        final Map<String, String> props = Maps.newHashMap();
        props.put("isis.service.email.sender.address", "myuser@gmail.com");
        props.put("isis.service.email.sender.password", "mypassword");
        props.put("isis.service.email.sender.hostname", "smtp.gmail.com");
        props.put("isis.service.email.port", "587");
        props.put("isis.service.email.tls.enabled", "true");
        return props;
    }

}
