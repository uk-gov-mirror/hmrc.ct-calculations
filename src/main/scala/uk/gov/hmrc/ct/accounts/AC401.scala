/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC401(value: Option[Int]) extends CtBoxIdentifier(name = "Current Gross turnover from OPW")
with CtOptionalInteger
with Input
with ValidatableBox[AccountsBoxRetriever] {
  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {
    val ac403 = boxRetriever.ac403()

    collectErrors(
      failIf(ac403.value.isDefined && value.isEmpty){
        Set(CtValidation(Some("AC401"), "error.AC401.required"))
      },
      exceedsMax(value, 999999),
      validateZeroOrPositiveInteger(this)
    )

  }
}

object AC401 {
  def apply(value: Int): AC401 = AC401(Some(value))
}