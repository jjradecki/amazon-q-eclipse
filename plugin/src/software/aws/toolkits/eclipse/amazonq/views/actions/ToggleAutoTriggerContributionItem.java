// Copyright 2024 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package software.aws.toolkits.eclipse.amazonq.views.actions;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import software.aws.toolkits.eclipse.amazonq.plugin.Activator;
import software.aws.toolkits.eclipse.amazonq.telemetry.UiTelemetryProvider;

public final class ToggleAutoTriggerContributionItem extends ContributionItem {

    public static final String AUTO_TRIGGER_ENABLEMENT_KEY = "aws.q.autotrigger.eclipse";
    private static final String PAUSE_TEXT = "Pause Auto-Suggestions";
    private static final String RESUME_TEXT = "Resume Auto-Suggestions";

    private Image pause;
    private Image resume;

    public ToggleAutoTriggerContributionItem() {
        var pauseImageDescriptor = Activator.imageDescriptorFromPlugin("org.eclipse.ui.navigator",
                "icons/full/clcl16/pause.png");
        pause = pauseImageDescriptor.createImage(Display.getCurrent());
        var resumeImageDescriptor = Activator.imageDescriptorFromPlugin("org.eclipse.ui.cheatsheets",
                "icons/elcl16/start_task.png");
        resume = resumeImageDescriptor.createImage(Display.getCurrent());
    }

    @Override
    public void setVisible(final boolean isVisible) {
        super.setVisible(isVisible);
    }

    @Override
    public void fill(final Menu menu, final int index) {
        String settingValue = Activator.getPluginStore().get(AUTO_TRIGGER_ENABLEMENT_KEY);
        boolean isEnabled;
        if (settingValue == null) {
            // on by default
            Activator.getPluginStore().put(AUTO_TRIGGER_ENABLEMENT_KEY, "true");
            isEnabled = true;
        } else {
            isEnabled = !settingValue.isBlank() && settingValue.equals("true");
        }
        MenuItem menuItem = new MenuItem(menu, SWT.NONE, index);
        menuItem.setText(isEnabled ? PAUSE_TEXT : RESUME_TEXT);
        menuItem.setImage(isEnabled ? pause : resume);
        menuItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                String settingValue = Activator.getPluginStore().get(AUTO_TRIGGER_ENABLEMENT_KEY);
                boolean wasEnabled = settingValue != null && !settingValue.isBlank() && settingValue.equals("true");
                UiTelemetryProvider.emitClickEventMetric((wasEnabled) ? "ellipses_pauseAutoTrigger" : "ellipses_resumeAutoTrigger");
                if (wasEnabled) {
                    Activator.getPluginStore().put(AUTO_TRIGGER_ENABLEMENT_KEY, "false");
                } else {
                    Activator.getPluginStore().put(AUTO_TRIGGER_ENABLEMENT_KEY, "true");
                }
                menuItem.setText(wasEnabled ? RESUME_TEXT : PAUSE_TEXT);
                menuItem.setImage(wasEnabled ? resume : pause);
            }
        });
    }

    @Override
    public void dispose() {
        if (pause != null) {
            pause.dispose();
            pause = null;
        }

        if (resume != null) {
            resume.dispose();
            resume = null;
        }

        super.dispose();
    }
}
