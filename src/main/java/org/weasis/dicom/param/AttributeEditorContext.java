/*
 * Copyright (c) 2017-2020 Weasis Team and other contributors.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0, or the Apache
 * License, Version 2.0 which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */
package org.weasis.dicom.param;

import org.dcm4che6.img.op.MaskArea;

public class AttributeEditorContext {

  /** Abort status allows to skip the file transfer or abort the DICOM association */
  public enum Abort {
    // Do nothing
    NONE,
    // Allows to skip the bulk data transfer to go to the next file
    FILE_EXCEPTION,
    // Stop the DICOM connection. Attention, this will abort other transfers when there are several
    // destinations for one source.
    CONNECTION_EXCEPTION
  }

  private final ForwardDestination destination;

  private Abort abort;
  private String abortMessage;
  private MaskArea maskArea;

  public AttributeEditorContext(ForwardDestination destination) {
    this.destination = destination;
    this.abort = Abort.NONE;
  }

  public Abort getAbort() {
    return abort;
  }

  public void setAbort(Abort abort) {
    this.abort = abort;
  }

  public String getAbortMessage() {
    return abortMessage;
  }

  public void setAbortMessage(String abortMessage) {
    this.abortMessage = abortMessage;
  }

  public ForwardDestination getDestination() {
    return destination;
  }

  public MaskArea getMaskArea() {
    return maskArea;
  }

  public void setMaskArea(MaskArea maskArea) {
    this.maskArea = maskArea;
  }
}
