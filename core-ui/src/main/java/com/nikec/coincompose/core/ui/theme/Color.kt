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

package com.nikec.coincompose.core.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Red = Color(0xFFBE2525)
val Green = Color(0xFF51C76A)
val LightGrey = Color(0xFFF8F8F8)
val LightBlack = Color(0xFF0E0E0E)

val Colors.text: Color
    @Composable get() = if (isLight) Black else White

val Colors.coinHeaderBackground: Color
    @Composable get() = if (isLight) LightGrey else LightBlack

val Colors.coinHeaderText: Color
    @Composable get() = if (isLight) LightBlack else White

val Colors.dividerHeader: Color
    @Composable get() = if (isLight) White else Black

val Colors.divider: Color
    @Composable get() = if (isLight) LightGrey else LightBlack

val Colors.fab: Color
    @Composable get() = if (isLight) LightGrey else LightBlack

val Colors.fabContent: Color
    @Composable get() = if (isLight) Black else White

// Sparkline
val Colors.graphLineColor: Color
    @Composable get() = if (isLight) Black else White

val Colors.axisGridStrokeColor: Color
    @Composable get() = if (isLight) LightGrey else LightBlack

val Colors.priceChangeNegative: Color
    @Composable get() = if (isLight) Red else Red

val Colors.priceChangePositive: Color
    @Composable get() = if (isLight) Green else Green