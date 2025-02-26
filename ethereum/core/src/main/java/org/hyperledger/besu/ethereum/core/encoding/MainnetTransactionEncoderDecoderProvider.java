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
package org.hyperledger.besu.ethereum.core.encoding;

import static com.google.common.base.Preconditions.checkNotNull;

import org.hyperledger.besu.datatypes.MainnetTransactionType;
import org.hyperledger.besu.datatypes.TransactionType;

import com.google.common.collect.ImmutableMap;

/** The transaction encoder provider for Mainnet. */
public class MainnetTransactionEncoderDecoderProvider
    implements TransactionEncoder.EncoderProvider, TransactionDecoder.DecoderProvider {

  private static final ImmutableMap<Integer, TransactionEncoder.Encoder>
      TYPED_TRANSACTION_ENCODERS =
          ImmutableMap.of(
              MainnetTransactionType.ACCESS_LIST.getTypeValue(),
              AccessListTransactionEncoder::encode,
              MainnetTransactionType.EIP1559.getTypeValue(),
              EIP1559TransactionEncoder::encode,
              MainnetTransactionType.BLOB.getTypeValue(),
              BlobTransactionEncoder::encode,
              MainnetTransactionType.DELEGATE_CODE.getTypeValue(),
              CodeDelegationTransactionEncoder::encode);

  private static final ImmutableMap<Integer, TransactionDecoder.Decoder>
      TYPED_TRANSACTION_DECODERS =
          ImmutableMap.of(
              MainnetTransactionType.ACCESS_LIST.getTypeValue(),
              AccessListTransactionDecoder::decode,
              MainnetTransactionType.EIP1559.getTypeValue(),
              EIP1559TransactionDecoder::decode,
              MainnetTransactionType.BLOB.getTypeValue(),
              BlobTransactionDecoder::decode,
              MainnetTransactionType.DELEGATE_CODE.getTypeValue(),
              CodeDelegationTransactionDecoder::decode);

  private static final ImmutableMap<Integer, TransactionEncoder.Encoder>
      POOLED_TRANSACTION_ENCODERS =
          ImmutableMap.of(
              MainnetTransactionType.BLOB.getTypeValue(), BlobPooledTransactionEncoder::encode);

  private static final ImmutableMap<Integer, TransactionDecoder.Decoder>
      POOLED_TRANSACTION_DECODERS =
          ImmutableMap.of(
              MainnetTransactionType.BLOB.getTypeValue(), BlobPooledTransactionDecoder::decode);

  @Override
  public TransactionEncoder.Encoder getEncoder(
      final TransactionType transactionType, final EncodingContext encodingContext) {

    if (encodingContext.equals(EncodingContext.POOLED_TRANSACTION)) {
      if (POOLED_TRANSACTION_ENCODERS.containsKey(transactionType.getTypeValue())) {
        return POOLED_TRANSACTION_ENCODERS.get(transactionType.getTypeValue());
      }
    }
    return checkNotNull(
        TYPED_TRANSACTION_ENCODERS.get(transactionType.getTypeValue()),
        "Developer Error. A supported transaction type %s has no associated encoding logic",
        transactionType);
  }

  @Override
  public TransactionDecoder.Decoder getDecoder(
      final TransactionType transactionType, final EncodingContext encodingContext) {
    if (encodingContext.equals(EncodingContext.POOLED_TRANSACTION)) {
      if (POOLED_TRANSACTION_DECODERS.containsKey(transactionType.getTypeValue())) {
        return POOLED_TRANSACTION_DECODERS.get(transactionType.getTypeValue());
      }
    }
    return checkNotNull(
        TYPED_TRANSACTION_DECODERS.get(transactionType.getTypeValue()),
        "Developer Error. A supported transaction type %s has no associated decoding logic",
        transactionType);
  }
}
