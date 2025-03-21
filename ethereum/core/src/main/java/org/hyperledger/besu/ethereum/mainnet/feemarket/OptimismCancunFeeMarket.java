/*
 * Copyright contributors to Besu.
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
package org.hyperledger.besu.ethereum.mainnet.feemarket;

import org.hyperledger.besu.config.OptimismGenesisConfigOptions;
import org.hyperledger.besu.datatypes.BlobGas;
import org.hyperledger.besu.datatypes.Wei;

import java.util.Optional;

/** The OpCancun fee market. */
public class OptimismCancunFeeMarket extends OptimismCanyonFeeMarket {

  /** The Cancun fee market reference. */
  private final CancunFeeMarket cancunFeeMarket;

  public OptimismCancunFeeMarket(
      final long londonForkBlockNumber,
      final Optional<Wei> baseFeePerGasOverride,
      final OptimismGenesisConfigOptions genesisConfigOptions) {
    super(londonForkBlockNumber, baseFeePerGasOverride, genesisConfigOptions);
    this.cancunFeeMarket = new CancunFeeMarket(londonForkBlockNumber, baseFeePerGasOverride);
  }

  @Override
  public boolean implementsDataFee() {
    return true;
  }

  @Override
  public Wei blobGasPricePerGas(final BlobGas excessBlobGas) {
    return this.cancunFeeMarket.blobGasPricePerGas(excessBlobGas);
  }
}
