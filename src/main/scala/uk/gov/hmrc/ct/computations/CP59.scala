/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.TotalDeductionsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP59(value: Int) extends CtBoxIdentifier(name = "Total deductions") with CtInteger

object CP59 extends Calculated[CP59, ComputationsBoxRetriever] with TotalDeductionsCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP59 = {
    totalDeductionsCalculation(cp58 = fieldValueRetriever.cp58(),
                               cp505 = fieldValueRetriever.cp505(),
                               cp509 = fieldValueRetriever.cp509(),
                               cp55 = fieldValueRetriever.cp55(),
                               cp57 = fieldValueRetriever.cp57())
  }
}
