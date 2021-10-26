/*
 * Developed by n1k3c (Nikola Curilović)  2021
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.nikec.coincompose.domain.usecases

import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.data.model.Currency
import com.nikec.coincompose.data.repository.SettingsRepository
import com.nikec.coincompose.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider(),
    private val settingsRepository: SettingsRepository
) : SubjectInteractor<Unit, Currency>() {

    override fun createObservable(params: Unit): Flow<Currency> =
        settingsRepository.getCurrency().flowOn(coroutineContextProvider.io)
}
