/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package griffon.builder.swing.jide;

import griffon.builder.jide.JideBuilder;
import griffon.builder.swing.jide.factory.SVGIconFactory;
import griffon.builder.swing.jide.factory.SearchableBarFactory;
import griffon.builder.swing.jide.factory.StandardDialogFactory;
import griffon.builder.swing.jide.factory.StandardDialogPaneFactory;
import griffon.inject.DependsOn;
import griffon.plugins.jide.swing.support.DialogBannerPanel;
import griffon.plugins.jide.swing.support.DialogButtonPanel;
import griffon.plugins.jide.swing.support.DialogContentPanel;
import groovy.swing.SwingBuilder;
import groovy.util.Factory;
import org.codehaus.griffon.runtime.groovy.view.AbstractBuilderCustomizer;

import javax.inject.Named;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Andres Almiray
 */
@DependsOn("swing")
@Named("jide-swing")
public class JideSwingBuilderCustomizer extends AbstractBuilderCustomizer {
    public JideSwingBuilderCustomizer() {
        SwingBuilder swing = new SwingBuilder();
        JideBuilder builder = new JideBuilder();

        Map<String, Factory> factories = new LinkedHashMap<>();
        for (Map.Entry<String, Factory> e : builder.getFactories().entrySet()) {
            String name = e.getKey();
            if (swing.getFactories().containsKey(name)) continue;
            factories.put(name, e.getValue());
        }

        factories.put("svgIcon", new SVGIconFactory());
        factories.put("searchableBar", new SearchableBarFactory());
        factories.put("standardDialog", new StandardDialogFactory());
        factories.put("dialogBannerPanel", new StandardDialogPaneFactory(DialogBannerPanel.class));
        factories.put("dialogContentPanel", new StandardDialogPaneFactory(DialogContentPanel.class));
        factories.put("dialogButtonPanel", new StandardDialogPaneFactory(DialogButtonPanel.class));

        setFactories(factories);
        setVariables(builder.getVariables());
        setMethods(builder.getExplicitMethods());
        setProps(builder.getExplicitProperties());
        setAttributeDelegates(builder.getAttributeDelegates());
        setPreInstantiateDelegates(builder.getPreInstantiateDelegates());
        setPostInstantiateDelegates(builder.getPostInstantiateDelegates());
        setPostNodeCompletionDelegates(builder.getPostNodeCompletionDelegates());
        // setDisposalClosures(builder.getDisposalClosures());
        setMethodMissingDelegate(builder.getMethodMissingDelegate());
        setPropertyMissingDelegate(builder.getPropertyMissingDelegate());
    }
}
