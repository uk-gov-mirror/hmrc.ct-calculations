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

package uk.gov.hmrc.ct.accounts.frs10x.abridged.accountsApproval

import org.mockito.Mockito._
import uk.gov.hmrc.ct.CompaniesHouseFiling
import uk.gov.hmrc.ct.accounts.frs10x.MockAbridgedAccountsRetriever

class CompaniesHouseAccountsApprovalSpec extends AccountsApprovalFixture with MockAbridgedAccountsRetriever {

  override def setUpMocks(): Unit = {
    when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
  }

  override def setUpDisabledMocks(): Unit = {
    when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))
  }

  testAccountsApproval("CompaniesHouseAccountsApproval", CompaniesHouseAccountsApproval.apply)

}