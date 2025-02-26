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

import java.util.Set;

/** The interface Transaction type. */
public interface TransactionType {

  /**
   * check if the transaction type is frontier type.
   *
   * @return the boolean
   */
  boolean isFrontier();

  /**
   * check if the transaction type is blob.
   *
   * @return the boolean
   */
  boolean isBlob();

  /**
   * check if the transaction type is access list.
   *
   * @return the boolean
   */
  boolean isAccessList();

  /**
   * check if the transaction type is delegate code.
   *
   * @return the boolean
   */
  boolean isDelegateCode();

  /**
   * The type name.
   *
   * @return the type name.
   */
  String typeName();

  /**
   * The type value.
   *
   * @return the type value.
   */
  int getTypeValue();

  /**
   * The type ordinal value.
   *
   * @return the type ordinal value.
   */
  int typeOrdinal();

  /**
   * Gets access list supported transaction types.
   *
   * @return the list of transaction types
   */
  Set<TransactionType> getAccessListSupportedTransactionTypes();

  /**
   * Gets list of legacy fee market transaction type.
   *
   * @return the list of transaction types
   */
  Set<TransactionType> getLegacyFeeMarketTransactionTypes();

  /**
   * Gets type name.
   *
   * @return the type name
   */
  default String name() {
    return typeName();
  }

  /**
   * get enum ordinal
   *
   * @return the enum type ordinal
   */
  default int ordinal() {
    return typeOrdinal();
  }

  /**
   * Gets serialized type.
   *
   * @return the serialized type
   */
  default byte getSerializedType() {
    return (byte) getTypeValue();
  }

  /**
   * Gets serialized type for returning in an eth transaction result, factoring in the special case
   * for FRONTIER transactions which have enum type 0xf8 but are represented as 0x00 in transaction
   * results.
   *
   * @return the serialized type
   */
  default byte getEthSerializedType() {
    return (this.isFrontier() ? 0x00 : this.getSerializedType());
  }

  /**
   * Compare to serialized type.
   *
   * @param b the byte value
   * @return the int result of comparison
   */
  default int compareTo(final Byte b) {
    return Byte.valueOf(getSerializedType()).compareTo(b);
  }

  /**
   * Does transaction type support access list.
   *
   * @return the boolean
   */
  default boolean supportsAccessList() {
    return getAccessListSupportedTransactionTypes().contains(this);
  }

  /**
   * Does transaction type support EIP-1559 fee market.
   *
   * @return the boolean
   */
  default boolean supports1559FeeMarket() {
    return !getLegacyFeeMarketTransactionTypes().contains(this);
  }

  /**
   * Does transaction type require chain id.
   *
   * @return the boolean
   */
  default boolean requiresChainId() {
    return !isFrontier();
  }

  /**
   * Does transaction type support data blobs.
   *
   * @return the boolean
   */
  default boolean supportsBlob() {
    return isBlob();
  }

  /**
   * Does transaction type support delegate code.
   *
   * @return the boolean
   */
  default boolean supportsDelegateCode() {
    return isDelegateCode();
  }

  /**
   * Does transaction type require code.
   *
   * @return the boolean
   */
  default boolean requiresCodeDelegation() {
    return isDelegateCode();
  }
}
