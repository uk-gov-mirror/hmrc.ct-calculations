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

import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC5058A(value: Option[String]) extends CtBoxIdentifier(name = "Balance sheet - Creditors within 1 year note.") with CtOptionalString with Input with ValidatableBox[AbridgedAccountsBoxRetriever] {

  override def validate(boxRetriever: AbridgedAccountsBoxRetriever): Set[CtValidation] = {
    collectErrors (
      cannotExistIf()
        validateStringMaxLength("AC5058A", s, 20000),
      validateOptionalStringByRegex("AC5058A", this, validCoHoCharacters)
    )


    (boxRetriever.ac58(), value) match {
      case (AC58(None), Some(s)) => Set(CtValidation(Some("AC5058A"), "error.AC5058A.cannot.exist"))
      case (AC58(Some(_)), Some(s)) => validateStringMaxLength("AC5058A", s, 20000) ++ validateOptionalStringByRegex("AC5058A", this, validCoHoCharacters)
      case _ => Set.empty
    }
  }
}
