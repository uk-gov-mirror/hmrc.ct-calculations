/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.RevaluationReserveCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

case class AC190(value: Option[Int]) extends CtBoxIdentifier(name = "Balance at [POA END DATE]")
                                       with CtOptionalInteger
                                       with ValidatableBox[Frs102AccountsBoxRetriever]
                                       with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    failIf (anyHaveValue(ac76(), ac77()))(
      collectErrors(
        validateTotalEqualToCurrentAmount(boxRetriever)
      )
    )
  }

  def validateTotalEqualToCurrentAmount(boxRetriever: Frs102AccountsBoxRetriever)() = {
    failIf(this.value != boxRetriever.ac76().value) {
      Set(CtValidation(None, "error.AC190.mustEqual.AC76"))
    }
  }

}

object AC190 extends Calculated[AC190, Frs102AccountsBoxRetriever]
               with RevaluationReserveCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC190 = {
    calculateAC190(boxRetriever.ac76(), boxRetriever.ac77(), boxRetriever.ac189())
  }

}
