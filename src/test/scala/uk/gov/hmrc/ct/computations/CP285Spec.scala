/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP285Spec extends WordSpec with Matchers with MockitoSugar {

  "CP285" should {
    val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]
    "when empty" when {
      "pass validation when CPQ18 is empty" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(None))
        CP285(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ18 is false" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(false)))
        CP285(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ18 is true" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
        CP285(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP285"), "error.CP285.required"))
      }
    }

    "when non empty" when {
      "fail validation when CPQ18 is empty" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(None))
        when(boxRetriever.cp2()).thenReturn(CP2(new LocalDate("2015-03-31")))
        CP285(someDate("2015-04-01")).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP285"), "error.CP285.cannot.exist"))
      }
      "pass validation when CPQ18 is false" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(false)))
        when(boxRetriever.cp2()).thenReturn(CP2(new LocalDate("2015-03-31")))
        CP285(someDate("2015-04-01")).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP285"), "error.CP285.cannot.exist"))
      }
      "fail validation when CPQ18 is true" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
        when(boxRetriever.cp2()).thenReturn(CP2(new LocalDate("2015-03-31")))
        CP285(someDate("2015-04-01")).validate(boxRetriever) shouldBe empty
      }
    }

    "when CPQ18 is true and has value" when {
      "pass validation when value after CP2" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
        when(boxRetriever.cp2()).thenReturn(CP2(new LocalDate("2015-03-31")))
        CP285(someDate("2015-04-01")).validate(boxRetriever) shouldBe empty
      }
      "pass validation when value before CP2 + 1 year" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
        when(boxRetriever.cp2()).thenReturn(CP2(new LocalDate("2015-03-31")))
        CP285(someDate("2016-03-31")).validate(boxRetriever) shouldBe empty
      }
      "fail validation when value more than a year after CP2" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
        when(boxRetriever.cp2()).thenReturn(CP2(new LocalDate("2015-03-31")))
        CP285(someDate("2016-04-01")).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP285"), "error.CP285.date.outside.range", Some(Seq("01/04/2015", "31/03/2016"))))
      }
      "fail validation when value is not after CP2" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
        when(boxRetriever.cp2()).thenReturn(CP2(new LocalDate("2015-03-31")))
        CP285(someDate("2015-03-31")).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP285"), "error.CP285.date.outside.range", Some(Seq("01/04/2015", "31/03/2016"))))
      }
    }
  }

  def someDate(dateString: String): Some[LocalDate] = {
    Some(new LocalDate(dateString))
  }
}
