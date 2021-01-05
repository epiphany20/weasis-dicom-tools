/*
 * Copyright (c) 2014-2020 Weasis Team and other contributors.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0, or the Apache
 * License, Version 2.0 which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */
package org.weasis.dicom;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.BasicConfigurator;
import org.dcm4che6.data.Tag;
import org.dcm4che6.net.Status;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.weasis.dicom.op.CGet;
import org.weasis.dicom.param.DicomNode;
import org.weasis.dicom.param.DicomParam;
import org.weasis.dicom.param.DicomProgress;
import org.weasis.dicom.param.DicomState;

public class CGetNetTest {
  @TempDir File testFolder;

  @Test
  public void testProcess() throws IOException {
    BasicConfigurator.configure();

    DicomProgress progress = new DicomProgress();
    progress.addProgressListener(
        progress1 -> {
          System.out.println(
              "Remaining operations:" + progress1.getNumberOfRemainingSuboperations());
          // if (progress.getNumberOfRemainingSuboperations() == 100) {
          // progress.cancel();
          // }
        });

    /** The following parameters must be changed to get a successful test. */
    DicomParam[] params = {new DicomParam(Tag.StudyInstanceUID, "1.2.826.0.1.3680043.11.111")};
    DicomNode calling = new DicomNode("WEASIS-SCU");
    DicomNode called = new DicomNode("DICOMSERVER", "dicomserver.co.uk", 11112);

    DicomState state = CGet.process(calling, called, progress, testFolder, params);

    // Should never happen
    Assert.assertNotNull(state);

    System.out.println("DICOM Status:" + state.getStatus());
    System.out.println(state.getMessage());
    System.out.println(
        "NumberOfRemainingSuboperations:" + progress.getNumberOfRemainingSuboperations());
    System.out.println(
        "NumberOfCompletedSuboperations:" + progress.getNumberOfCompletedSuboperations());
    System.out.println("NumberOfFailedSuboperations:" + progress.getNumberOfFailedSuboperations());
    System.out.println(
        "NumberOfWarningSuboperations:" + progress.getNumberOfWarningSuboperations());

    // see org.dcm4che3.net.Status
    // See server log at http://dicomserver.co.uk/logs/
    Assert.assertThat(state.getMessage(), state.getStatus(), IsEqual.equalTo(Status.Success));
  }
}
