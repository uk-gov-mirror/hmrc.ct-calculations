/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class ACQ5034(value: Option[Boolean]) extends CtBoxIdentifier(name = "Office equipment")  with CtOptionalBoolean with Input
  with ValidatableBox[FullAccountsBoxRetriever]
{

  def validate(boxRetriever: FullAccountsBoxRetriever) = {
    import boxRetriever._
    cannotExistErrorIf(hasValue && ac44.noValue && ac45.noValue)
  }
}
