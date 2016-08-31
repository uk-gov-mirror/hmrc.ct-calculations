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

package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations.BalanceSheetTangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC132(value: Option[Int]) extends CtBoxIdentifier(name = "Net book value of tangible assets at the end of the previous period")
  with CtOptionalInteger with BalanceSheetTangibleAssetsCalculator
  with ValidatableBox[AbridgedAccountsBoxRetriever]
  with Validators{

  override def validate(boxRetriever: AbridgedAccountsBoxRetriever): Set[CtValidation] = {
    failIf (boxRetriever.ac44.value.nonEmpty) (
      collectErrors(
        validateNetBooValueMatchesTotalAssets(boxRetriever)
      )
    )
  }

  def validateNetBooValueMatchesTotalAssets(boxRetriever: AbridgedAccountsBoxRetriever)() = {
    failIf (boxRetriever.ac132().orZero != boxRetriever.ac44().orZero || boxRetriever.ac5132().orZero != boxRetriever.ac45().orZero) {
      Set(CtValidation(None, "error.global.tangible.assets.note.netBookValue.notEqualToAssets"))
    }
  }
}

object AC132 extends Calculated[AC132, AbridgedAccountsBoxRetriever] with BalanceSheetTangibleAssetsCalculator {

  override def calculate(boxRetriever: AbridgedAccountsBoxRetriever): AC132 = {
    calculateNetBookValueOfTangibleAssetsAEndOfPreviousPeriod(
    boxRetriever.ac217(),
    boxRetriever.ac131()
    )
  }
}

/*
val anyProfitOrLossFieldHasAValue =
      (value orElse
        ac18().value orElse
        ac20().value orElse
        ac28().value orElse
        ac30().value orElse
        ac34().value)
        .nonEmpty
 */
