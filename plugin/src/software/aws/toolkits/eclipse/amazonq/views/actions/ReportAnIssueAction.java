// Copyright 2024 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package software.aws.toolkits.eclipse.amazonq.views.actions;


import software.aws.toolkits.eclipse.amazonq.views.model.ExternalLink;

public final class ReportAnIssueAction extends OpenUrlAction {

    public ReportAnIssueAction() {
        super("Report an Issue", "ellipses_reportIssue", ExternalLink.GitHubIssues);
    }
}
