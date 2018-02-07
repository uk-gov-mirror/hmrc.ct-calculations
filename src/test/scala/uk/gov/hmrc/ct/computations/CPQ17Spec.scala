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

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CPQ17Spec extends WordSpec with Matchers with MockitoSugar {

  "CPQ17" should {
    val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]

    "when empty" when {
      "pass validation when CP117 == zero" in {
        when(boxRetriever.cp117()).thenReturn(CP117(0))
        CPQ17(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CP117 > 0" in {
        when(boxRetriever.cp117()).thenReturn(CP117(10))
        CPQ17(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ17"), "error.CPQ17.required"))
      }
    }
    "when true" when {
      "fail validation when CP117 is zero" in {
        when(boxRetriever.cp117()).thenReturn(CP117(0))
        CPQ17(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ17"), "error.CPQ17.cannot.exist"))
      }
      "pass validation when CP117 > zero" in {
        when(boxRetriever.cp117()).thenReturn(CP117(10))
        CPQ17(Some(true)).validate(boxRetriever) shouldBe empty
      }
    }
  }
}
