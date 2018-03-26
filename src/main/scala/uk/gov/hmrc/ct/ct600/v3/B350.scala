/*
 * Copyright 2018 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.calculations.NITradingProfitCalculationParameters
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator

case class B350(value: Int) extends CtBoxIdentifier(name = "Net trading profits") with CtInteger

object B350 extends CorporationTaxCalculator with Calculated[B350, ComputationsBoxRetriever] {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): B350 = {
    calculateNIApportionedTradingProfitsChargeableFy1(
      NITradingProfitCalculationParameters(
        fieldValueRetriever.cp291(),
        HmrcAccountingPeriod(fieldValueRetriever.cp1(), fieldValueRetriever.cp2())
      )
    )
  }
}