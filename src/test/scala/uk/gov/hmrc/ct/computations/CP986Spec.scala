/*
 * Copyright 2021 HM Revenue & Customs
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

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.{AC401, AC403}
import uk.gov.hmrc.ct.computations.stubs.StubbedComputationsBoxRetriever


class CP986Spec extends WordSpec with Matchers with MockitoSugar {

  "CP986" should {
    "calculate cp986 as 0 if they have made a loss this year." in {

      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def cp980 = CP980(Some(53000))

        override def cp981 = CP981(Some(30000))

        override def cp982 = CP982(Some(7000))

      }

      val cp986 = CP986.calculate(boxRetriever).value

      cp986 shouldBe 90000

    }

  }
}
