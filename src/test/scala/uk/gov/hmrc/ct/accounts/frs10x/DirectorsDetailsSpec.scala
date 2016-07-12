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

package uk.gov.hmrc.ct.accounts.frs10x

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class DirectorsDetailsSpec extends WordSpec with MockitoSugar with Matchers {

  val mockBoxRetriever = mock[Frs10xAccountsBoxRetriever]
  when(mockBoxRetriever.retrieveAC8023()).thenReturn(AC8023(Some(true)))


  "DirectorsDetails" should {

    "validate successfully when no validation errors are present" in {

      val directorDetails = DirectorDetails("444", "luke")
      val directorsDetails = DirectorsDetails(List(directorDetails))

      directorsDetails.validate(mockBoxRetriever) shouldBe empty
    }

    "validate director name length" in {

      val directorDetails = DirectorDetails("444", "")
      val directorsDetails = DirectorsDetails(List(directorDetails))

      val expectedError = Set(CtValidation(Some("AC8001"), "error.AC8001.text.sizeRange", Some(List("1", "40"))))
      directorsDetails.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate director name length more than 40 chars" in {

      val directorDetails = DirectorDetails("444", "a" * 41)
      val directorsDetails = DirectorsDetails(List(directorDetails))

      val expectedError = Set(CtValidation(Some("AC8001"), "error.AC8001.text.sizeRange", Some(List("1", "40"))))
      directorsDetails.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate director name characters" in {

      val directorDetails = DirectorDetails("444", "??")
      val directorsDetails = DirectorsDetails(List(directorDetails))

      val expectedError = Set(CtValidation(Some("AC8001"), "error.AC8001.regexFailure", None))
      directorsDetails.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate at least one director name present is enabled when AC8023 is true" in {

      val directorsDetails = DirectorsDetails(List())

      val expectedError = Set(CtValidation(Some("AC8001"), "error.AC8001.global.atLeast1", None))
      directorsDetails.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate at least one director name present is disabled when AC8023 is false" in {
      when(mockBoxRetriever.retrieveAC8023()).thenReturn(AC8023(Some(false)))

      val directorsDetails = DirectorsDetails(List())

      directorsDetails.validate(mockBoxRetriever) shouldBe empty
    }

    "validate at most 12 director names" in {

      val directors = for {
        i <- ('A' to 'Z').toList
        d = DirectorDetails("444", s"director$i")
      } yield d

      val directorsDetails = DirectorsDetails(directors)

      val expectedError = Set(CtValidation(Some("AC8001"), "error.AC8001.global.atMost12", None))
      directorsDetails.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate duplicate director names" in {

      val directorsDetails = DirectorsDetails(List(DirectorDetails("444", "Jack"), DirectorDetails("555", "Jack")))

      val expectedError = Set(CtValidation(Some("AC8001"), "error.AC8001.unique", None))
      directorsDetails.validate(mockBoxRetriever) shouldBe expectedError
    }

  }
}
