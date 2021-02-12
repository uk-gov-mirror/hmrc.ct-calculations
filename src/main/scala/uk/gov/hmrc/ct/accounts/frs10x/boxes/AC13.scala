/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.AccountsPreviousPeriodValidation
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC13(value: Option[Int]) extends CtBoxIdentifier(name = "Turnover (previous PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[AccountsBoxRetriever]
  with AccountsPreviousPeriodValidation
  with Validators {

  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateInputAllowed("AC13", boxRetriever.ac205()),
      validateZeroOrPositiveInteger(this)
    )
  }
}