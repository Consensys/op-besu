/*
 * Copyright ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package org.hyperledger.besu.datatypes;

import java.util.Arrays;
import java.util.Set;

/** The enum Transaction type. */
public enum MainnetTransactionType implements TransactionType {
  /** The Frontier. */
  FRONTIER(0xf8 /* this is serialized as 0x0 in TransactionCompleteResult */),
  /** Access list transaction type. */
  ACCESS_LIST(0x01),
  /** Eip1559 transaction type. */
  EIP1559(0x02),
  /** Blob transaction type. */
  BLOB(0x03),
  /** Eip7702 transaction type. */
  DELEGATE_CODE(0x04);

  private static final Set<TransactionType> ACCESS_LIST_SUPPORTED_TRANSACTION_TYPES =
      Set.of(ACCESS_LIST, EIP1559, BLOB, DELEGATE_CODE);

  private static final Set<TransactionType> LEGACY_FEE_MARKET_TRANSACTION_TYPES =
      Set.of(FRONTIER, ACCESS_LIST);

  private final int typeValue;

  MainnetTransactionType(final int typeValue) {
    this.typeValue = typeValue;
  }

  @Override
  public boolean isFrontier() {
    return this == FRONTIER;
  }

  @Override
  public boolean isBlob() {
    return this == BLOB;
  }

  @Override
  public boolean isAccessList() {
    return this == ACCESS_LIST;
  }

  @Override
  public boolean isDelegateCode() {
    return this == DELEGATE_CODE;
  }

  @Override
  public String typeName() {
    return this.name();
  }

  @Override
  public int getTypeValue() {
    return this.typeValue;
  }

  @Override
  public int typeOrdinal() {
    return this.ordinal();
  }

  @Override
  public Set<TransactionType> getAccessListSupportedTransactionTypes() {
    return ACCESS_LIST_SUPPORTED_TRANSACTION_TYPES;
  }

  @Override
  public Set<TransactionType> getLegacyFeeMarketTransactionTypes() {
    return LEGACY_FEE_MARKET_TRANSACTION_TYPES;
  }

  /**
   * Convert TransactionType from int serialized type value.
   *
   * @param serializedTypeValue the serialized type value
   * @return the transaction type
   */
  public static MainnetTransactionType of(final int serializedTypeValue) {
    return Arrays.stream(
            new MainnetTransactionType[] {
              MainnetTransactionType.FRONTIER,
              MainnetTransactionType.ACCESS_LIST,
              MainnetTransactionType.EIP1559,
              MainnetTransactionType.BLOB,
              MainnetTransactionType.DELEGATE_CODE
            })
        .filter(transactionType -> transactionType.getTypeValue() == serializedTypeValue)
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    String.format("Unsupported transaction type %x", serializedTypeValue)));
  }
}
