/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.validation.{Frs102TestBoxRetriever, ValidateAssetsEqualSharesSpec}
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

class AC81Spec extends ValidateAssetsEqualSharesSpec[Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever] {

  override def addOtherBoxValue100Mock(mockRetriever: Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever) =
    when(mockRetriever.ac69()).thenReturn(AC69(Some(100)))

  override def addOtherBoxValueNoneMock(mockRetriever: Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever) =
    when(mockRetriever.ac69()).thenReturn(AC69(None))

  testAssetsEqualToSharesValidation("AC81", AC81.apply)

  override def createMock(): Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever = mock[Frs102TestBoxRetriever]
}
