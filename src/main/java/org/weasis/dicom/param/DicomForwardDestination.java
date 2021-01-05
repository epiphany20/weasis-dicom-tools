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

import java.io.IOException;
import java.util.List;
import org.weasis.dicom.util.StoreFromStreamSCU;

public class DicomForwardDestination extends ForwardDestination {

  private final StoreFromStreamSCU streamSCU;
  private final boolean useDestinationAetForKeyMap;

  private final ForwardDicomNode callingNode;
  private final DicomNode destinationNode;

  public DicomForwardDestination(ForwardDicomNode fwdNode, DicomNode destinationNode)
      throws IOException {
    this(null, fwdNode, destinationNode, null);
  }

  public DicomForwardDestination(
      AdvancedParams forwardParams, ForwardDicomNode fwdNode, DicomNode destinationNode)
      throws IOException {
    this(forwardParams, fwdNode, destinationNode, null);
  }

  public DicomForwardDestination(
      AdvancedParams forwardParams,
      ForwardDicomNode fwdNode,
      DicomNode destinationNode,
      List<AttributeEditor> editors)
      throws IOException {
    this(forwardParams, fwdNode, destinationNode, false, null, editors);
  }

  /**
   * @param forwardParams optional advanced parameters (proxy, authentication, connection and TLS)
   * @param fwdNode the DICOM forwarding node. Cannot be null.
   * @param destinationNode the DICOM destination node. Cannot be null.
   * @param useDestinationAetForKeyMap
   * @param progress
   * @param editors
   * @throws IOException
   */
  public DicomForwardDestination(
      AdvancedParams forwardParams,
      ForwardDicomNode fwdNode,
      DicomNode destinationNode,
      boolean useDestinationAetForKeyMap,
      DicomProgress progress,
      List<AttributeEditor> editors)
      throws IOException {
    this(
        null,
        forwardParams,
        fwdNode,
        destinationNode,
        useDestinationAetForKeyMap,
        progress,
        editors);
  }

  public DicomForwardDestination(
      Long id,
      AdvancedParams forwardParams,
      ForwardDicomNode fwdNode,
      DicomNode destinationNode,
      boolean useDestinationAetForKeyMap,
      DicomProgress progress,
      List<AttributeEditor> editors)
      throws IOException {
    super(id, editors);
    this.callingNode = fwdNode;
    this.destinationNode = destinationNode;
    CstoreParams params = new CstoreParams(editors, false, null);
    this.streamSCU =
        new StoreFromStreamSCU(forwardParams, fwdNode, destinationNode, progress, params);
    this.useDestinationAetForKeyMap = useDestinationAetForKeyMap;
  }

  public StoreFromStreamSCU getStreamSCU() {
    return streamSCU;
  }

  public boolean isUseDestinationAetForKeyMap() {
    return useDestinationAetForKeyMap;
  }

  @Override
  public ForwardDicomNode getForwardDicomNode() {
    return callingNode;
  }

  public DicomNode getDestinationNode() {
    return destinationNode;
  }

  @Override
  public void stop() {
    streamSCU.close(true);
  }

  @Override
  public DicomState getState() {
    return streamSCU.getState();
  }

  @Override
  public String toString() {
    return destinationNode.toString();
  }
}
