/*
 * Copyright 2014-2015 the original author or authors.
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
package griffon.plugins.jide.swing.support;

import com.jidesoft.swing.Searchable;
import com.jidesoft.swing.SearchableBar;

import javax.annotation.Nonnull;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class SearchableBarWrapper extends SearchableBar {
    private Object constraints = BorderLayout.PAGE_END;
    private KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK);

    public SearchableBarWrapper(@Nonnull Searchable searchable) {
        super(searchable);
    }

    public SearchableBarWrapper(@Nonnull Searchable searchable, String initialText) {
        super(searchable, initialText, false);
    }

    public SearchableBarWrapper(@Nonnull Searchable searchable, boolean compact) {
        super(searchable, compact);
    }

    public SearchableBarWrapper(@Nonnull Searchable searchable, String initialText, boolean compact) {
        super(searchable, initialText, compact);
    }

    public Object getConstraints() {
        return constraints;
    }

    public void setConstraints(Object constraints) {
        this.constraints = constraints;
    }

    public KeyStroke getKeyStroke() {
        return keyStroke;
    }

    public void setKeyStroke(KeyStroke keyStroke) {
        this.keyStroke = keyStroke;
    }

    public void installOnContainer(@Nonnull final Container container) {
        setInstaller(new Installer() {
            @Override
            public void openSearchBar(SearchableBar searchableBar) {
                container.add(searchableBar, constraints);
                container.invalidate();
                container.revalidate();
            }

            @Override
            public void closeSearchBar(SearchableBar searchableBar) {
                container.remove(searchableBar);
                container.invalidate();
                container.revalidate();
            }
        });

        Component component = getSearchable().getComponent();
        if (component instanceof JComponent) {
            ((JComponent) component).registerKeyboardAction(
                new OpenSearchBarAction(this),
                keyStroke,
                JComponent.WHEN_FOCUSED);
        }
    }

    private static class OpenSearchBarAction extends AbstractAction {
        private final SearchableBar searchableBar;

        private OpenSearchBarAction(@Nonnull SearchableBar searchableBar) {
            this.searchableBar = searchableBar;
        }

        public void actionPerformed(ActionEvent e) {
            searchableBar.getInstaller().openSearchBar(searchableBar);
            searchableBar.focusSearchField();
        }
    }
}