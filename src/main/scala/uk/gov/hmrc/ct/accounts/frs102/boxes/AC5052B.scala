/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC5052B(value: Option[String]) extends CtBoxIdentifier(name = "Balance sheet notes debtors additional information") with CtOptionalString
                                                                                                                                with Input
                                                                                                                                with ValidatableBox[Frs102AccountsBoxRetriever]
                                                                                                                                with Validators {


  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors (
      validateStringMaxLength("AC5052B", value.getOrElse(""), StandardCohoTextFieldLimit),
      validateCoHoStringReturnIllegalChars("AC5052B", this)
    )
  }
}
