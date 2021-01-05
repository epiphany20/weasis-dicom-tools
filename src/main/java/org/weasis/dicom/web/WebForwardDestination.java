/*
 * Copyright (c) 2018-2020 Weasis Team and other contributors.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0, or the Apache
 * License, Version 2.0 which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */
package org.weasis.dicom.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.weasis.core.util.FileUtil;
import org.weasis.dicom.param.*;

public class WebForwardDestination extends ForwardDestination {

  private final ForwardDicomNode callingNode;
  private final DicomState state;
  private final DicomStowRS stowRS;

  public WebForwardDestination(ForwardDicomNode fwdNode, String requestURL) {
    this(fwdNode, requestURL, null);
  }

  public WebForwardDestination(
      ForwardDicomNode fwdNode, String requestURL, List<AttributeEditor> editors) {
    this(fwdNode, requestURL, null, editors);
  }

  public WebForwardDestination(
      ForwardDicomNode fwdNode,
      String requestURL,
      DicomProgress progress,
      List<AttributeEditor> editors) {
    this(fwdNode, requestURL, null, progress, editors);
  }

  public WebForwardDestination(
      ForwardDicomNode fwdNode,
      String requestURL,
      Map<String, String> headers,
      DicomProgress progress,
      List<AttributeEditor> editors) {
    this(null, fwdNode, requestURL, headers, progress, editors);
  }

  public WebForwardDestination(
      Long id,
      ForwardDicomNode fwdNode,
      String requestURL,
      Map<String, String> headers,
      DicomProgress progress,
      List<AttributeEditor> editors) {
    super(id, editors);
    this.callingNode = fwdNode;
    this.state = new DicomState(progress == null ? new DicomProgress() : progress);
    this.stowRS = new DicomStowRS(requestURL, ContentType.APPLICATION_DICOM, null, headers);
  }

  public WebForwardDestination(
      ForwardDicomNode fwdNode,
      DicomStowRS uploadManager,
      DicomProgress progress,
      List<AttributeEditor> editors) {
    this(null, fwdNode, uploadManager, progress, editors);
  }

  public WebForwardDestination(
      Long id,
      ForwardDicomNode fwdNode,
      DicomStowRS uploadManager,
      DicomProgress progress,
      List<AttributeEditor> editors) {
    super(id, editors);
    this.callingNode = fwdNode;
    this.state = new DicomState(progress == null ? new DicomProgress() : progress);
    this.stowRS = uploadManager;
  }

  @Override
  public ForwardDicomNode getForwardDicomNode() {
    return callingNode;
  }

  public String getRequestURL() {
    return stowRS.getRequestURL();
  }

  public DicomStowRS getStowrsSingleFile() throws IOException {
    return stowRS;
  }

  @Override
  public void stop() {
    FileUtil.safeClose(stowRS);
  }

  @Override
  public String toString() {
    return stowRS.getRequestURL();
  }

  public DicomState getState() {
    return state;
  }
}
