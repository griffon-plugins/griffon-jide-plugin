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
package griffon.plugins.jide.swing.support;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.app.beans.SVGIcon;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * @author Andres Almiray
 */
public class ResizableSVGIcon extends SVGIcon implements ComponentListener {
    private boolean trackSize;
    private boolean retainAspectRatio = true;
    private int resizePercentage = 100;

    private Dimension lastSize;
    private Dimension iconSize;
    private Component component;
    private boolean sizeSet;

    public ResizableSVGIcon() {
        setAntiAlias(true);
        setScaleToFit(true);
    }

    public boolean isTrackSize() {
        return trackSize;
    }

    public void setTrackSize(boolean trackSize) {
        this.trackSize = trackSize;
    }

    public boolean isRetainAspectRatio() {
        return retainAspectRatio;
    }

    public void setRetainAspectRatio(boolean retainAspectRatio) {
        this.retainAspectRatio = retainAspectRatio;
    }

    public int getResizePercentage() {
        return resizePercentage;
    }

    public void setResizePercentage(int pct) {
        if (pct >= 10 && pct <= 100) {
            resizePercentage = pct;
        }
    }

    public void installSizeTracker(Component component) {
        if (component != null && trackSize) {
            if (this.component != null) {
                this.component.removeComponentListener(this);
            }
            component.addComponentListener(this);
            lastSize = component.getSize();
            this.component = component;
        }
    }

    /**
     * Invoked when the component has been made invisible.
     */
    public void componentHidden(ComponentEvent e) { /*empty */ }

    /**
     * Invoked when the component's position changes.
     */
    public void componentMoved(ComponentEvent e) { /*empty */ }

    /**
     * Invoked when the component has been made visible.
     */
    public void componentShown(ComponentEvent e) {
        resize(e.getComponent().getSize());
    }

    /**
     * Invoked when the component's size changes.
     */
    public void componentResized(ComponentEvent e) {
        resize(e.getComponent().getSize());
    }

    /**
     * Returns true if the size has been set to a
     * non-<code>null</code> value otherwise returns false.
     *
     * @return true if <code>setSize</code> has been invoked
     * with a non-null value.
     */
    public boolean isSizeSet() {
        return sizeSet;
    }

    /**
     * Resizes this object so that it has width and height.
     *
     * @param size - the dimension specifying the new size of the object
     */
    public void setSize(Dimension size) {
        sizeSet = true;
        _setSize(size);
    }

    private void resize(Dimension actualSize) {
        if (trackSize && actualSize.width != lastSize.width &&
            actualSize.height != lastSize.height) {
            int width = actualSize.width * resizePercentage / 100;
            int height = actualSize.height * resizePercentage / 100;
            _setSize(new Dimension(width, height));
        }
    }

    private void _setSize(Dimension size) {
        if (retainAspectRatio) {
            int width = size.width;
            int height = size.height;
            if (iconSize == null) {
                cacheIconSize();
            }
            int nheight = Math.round(width * iconSize.height / iconSize.width);
            int nwidth = Math.round(height * iconSize.width / iconSize.height);
            if (nheight <= height) {
                height = nheight;
            } else {
                width = nwidth;
            }
            setPreferredSize(new Dimension(width, height));
        } else {
            setPreferredSize(size);
        }
    }

    private void cacheIconSize() {
        SVGDiagram diagram = getSvgUniverse().getDiagram(getSvgURI());
        if (diagram == null) {
            return;
        }
        iconSize = new Dimension((int) diagram.getWidth(), (int) diagram.getHeight());
    }
}