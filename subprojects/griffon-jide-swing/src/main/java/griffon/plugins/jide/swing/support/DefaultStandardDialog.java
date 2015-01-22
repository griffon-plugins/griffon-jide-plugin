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

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.StandardDialog;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.Dialog;
import java.awt.Frame;

/**
 * @author Andres Almiray
 */
public class DefaultStandardDialog extends StandardDialog {
    private JComponent bannerPanel = new JPanel();
    private JComponent contentPanel = new JPanel();
    private ButtonPanel buttonPanel = new ButtonPanel();

    public DefaultStandardDialog() {
        super();
    }

    public DefaultStandardDialog(Frame owner) {
        super(owner, true);
    }

    public DefaultStandardDialog(Dialog owner) {
        super(owner, true);
    }

    public JComponent getBannerPanel() {
        return bannerPanel;
    }

    public void setBannerPanel(JComponent bannerPanel) {
        this.bannerPanel = bannerPanel;
    }

    public JComponent getContentPanel() {
        return contentPanel;
    }

    public void setContentPanel(JComponent contentPanel) {
        this.contentPanel = contentPanel;
    }

    public ButtonPanel getButtonPanel() {
        return buttonPanel;
    }

    public void setButtonPanel(ButtonPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

    public JComponent createBannerPanel() {
        return bannerPanel;
    }

    public JComponent createContentPanel() {
        return contentPanel;
    }

    public ButtonPanel createButtonPanel() {
        return buttonPanel;
    }
}