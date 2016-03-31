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

package uk.gov.hmrc.ct.version.calculations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct._
import uk.gov.hmrc.ct.box.retriever.{BoxRetriever, FilingAttributesBoxValueRetriever}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.computations.{CP1, CP2}
import uk.gov.hmrc.ct.ct600e.v3.retriever.{CT600EBoxRetriever => V3CT600EBoxRetriever}
import uk.gov.hmrc.ct.ct600e.v2.retriever.{CT600EBoxRetriever => V2CT600EBoxRetriever}
import uk.gov.hmrc.ct.domain.CompanyTypes._
import uk.gov.hmrc.ct.version.CoHoAccounts.{CoHoMicroEntityAbridgedAccounts, CoHoMicroEntityAccounts, CoHoStatutoryAbbreviatedAccounts, CoHoStatutoryAccounts}
import uk.gov.hmrc.ct.version.CoHoVersions.AccountsVersion1
import uk.gov.hmrc.ct.version.HmrcReturns._
import uk.gov.hmrc.ct.version.HmrcVersions._
import uk.gov.hmrc.ct.version.{Version, Return}

object ReturnVersionsCalculator extends ReturnVersionsCalculator

trait ReturnVersionsCalculator {

  def doCalculation[A <: BoxRetriever](boxRetriever: A): Set[Return] = {
    boxRetriever match {

      case br: V3CT600EBoxRetriever with FilingAttributesBoxValueRetriever =>
        calculateReturnVersions(apStartDate = Some(br.retrieveE3().value),
                                apEndDate = Some(br.retrieveE4().value),
                                coHoFiling = br.retrieveCompaniesHouseFiling(),
                                hmrcFiling = br.retrieveHMRCFiling(),
                                microEntityFiling = br.retrieveMicroEntityFiling(),
                                statutoryAccountsFiling = br.retrieveStatutoryAccountsFiling(),
                                abridgedFiling = br.retrieveAbridgedFiling(),
                                abbreviatedAccountsFiling = br.retrieveAbbreviatedAccountsFiling(),
                                companyType = br.retrieveCompanyType(),
                                charityAllExempt = br.retrieveE20().value,
                                charityNoIncome = v3CharityNoIncome(br))

      case br: V2CT600EBoxRetriever with FilingAttributesBoxValueRetriever =>
        calculateReturnVersions(apStartDate = Some(br.retrieveE1021().value),
                                apEndDate = Some(br.retrieveE1022().value),
                                coHoFiling = br.retrieveCompaniesHouseFiling(),
                                hmrcFiling = br.retrieveHMRCFiling(),
                                microEntityFiling = br.retrieveMicroEntityFiling(),
                                statutoryAccountsFiling = br.retrieveStatutoryAccountsFiling(),
                                abridgedFiling = br.retrieveAbridgedFiling(),
                                abbreviatedAccountsFiling = br.retrieveAbbreviatedAccountsFiling(),
                                companyType = br.retrieveCompanyType(),
                                charityAllExempt = br.retrieveE1011().value,
                                charityNoIncome = v2CharityNoIncome(br))

      case br: ComputationsBoxRetriever with FilingAttributesBoxValueRetriever =>
        calculateReturnVersions(apStartDate = Some(br.retrieveCP1().value),
                                apEndDate = Some(br.retrieveCP2().value),
                                coHoFiling = br.retrieveCompaniesHouseFiling(),
                                hmrcFiling = br.retrieveHMRCFiling(),
                                microEntityFiling = br.retrieveMicroEntityFiling(),
                                statutoryAccountsFiling = br.retrieveStatutoryAccountsFiling(),
                                abridgedFiling = br.retrieveAbridgedFiling(),
                                abbreviatedAccountsFiling = br.retrieveAbbreviatedAccountsFiling(),
                                companyType = br.retrieveCompanyType())

      case br: FilingAttributesBoxValueRetriever =>
        calculateReturnVersions(apStartDate = None,
                                apEndDate = None,
                                coHoFiling = br.retrieveCompaniesHouseFiling(),
                                hmrcFiling = br.retrieveHMRCFiling(),
                                microEntityFiling = br.retrieveMicroEntityFiling(),
                                statutoryAccountsFiling = br.retrieveStatutoryAccountsFiling(),
                                abridgedFiling = br.retrieveAbridgedFiling(),
                                abbreviatedAccountsFiling = br.retrieveAbbreviatedAccountsFiling(),
                                companyType = br.retrieveCompanyType())
      case _ => throw new IllegalArgumentException("The box retriever passed in must implement FilingAttributesBoxValueRetriever")
    }
  }

  private def ct600ForLimitedBySharesCharity(ct600Version: Option[Version], charityAllExempt: Option[Boolean], charityNoIncome: Option[Boolean]) = {
    (ct600Version, charityAllExempt, charityNoIncome) match {
      case (Some(version), Some(true), _) => Set(Return(CT600e, version),
                                                 Return(CT600j, version))
      case (Some(version), _, Some(true)) => Set(Return(CT600e, version),
                                                 Return(CT600j, version))
      case (Some(version), Some(false), _) => Set(Return(CT600e, version),
                                                  Return(CT600, version),
                                                  Return(CT600a, version),
                                                  Return(CT600j, version))
      case (Some(version), _, _) => Set(Return(CT600, version),
                                        Return(CT600a, version),
                                        Return(CT600j, version))
    }
  }

  private def ct600ForLimitedByGuaranteeCharity(ct600Version: Option[Version], charityAllExempt: Option[Boolean], charityNoIncome: Option[Boolean]) = {
    (ct600Version, charityAllExempt, charityNoIncome) match {
      case (Some(version), Some(true), _) => Set(Return(CT600e, version),
                                                 Return(CT600j, version))
      case (Some(version), _, Some(true)) => Set(Return(CT600e, version),
                                                 Return(CT600j, version))
      case (Some(version), Some(false), _) => Set(Return(CT600e, version),
                                                  Return(CT600, version),
                                                  Return(CT600j, version))
      case (Some(version), _, _) => Set(Return(CT600, version),
                                        Return(CT600j, version))
    }
  }

  private def ct600ForLimitedByGuaranteeCompany(ct600Version: Version) = Set(Return(CT600, ct600Version),
                                                                             Return(CT600j, ct600Version))

  private def ct600ForCharity(ct600Version: Option[Version], charityAllExempt: Option[Boolean], charityNoIncome: Option[Boolean]) = {
    (ct600Version, charityAllExempt, charityNoIncome) match {
      case (Some(version), Some(true), _) => Set(Return(CT600e, version),
                                                 Return(CT600j, version))
      case (Some(version), _, Some(true)) => Set(Return(CT600e, version),
                                                 Return(CT600j, version))
      case (Some(version), Some(false), _) => Set(Return(CT600e, version),
                                                  Return(CT600, version),
                                                  Return(CT600j, version))
      case (Some(version), _, _) => Set(Return(CT600, version),
                                        Return(CT600j, version))
    }
  }


  def calculateReturnVersions(apStartDate: Option[LocalDate] = None, apEndDate: Option[LocalDate] = None,
                              coHoFiling: CompaniesHouseFiling = CompaniesHouseFiling(false),
                              hmrcFiling: HMRCFiling = HMRCFiling(false),
                              microEntityFiling: MicroEntityFiling = MicroEntityFiling(false),
                              statutoryAccountsFiling: StatutoryAccountsFiling = StatutoryAccountsFiling(false),
                              abridgedFiling: AbridgedFiling = AbridgedFiling(false),
                              abbreviatedAccountsFiling: AbbreviatedAccountsFiling = AbbreviatedAccountsFiling(false),
                              companyType: FilingCompanyType = FilingCompanyType(UkTradingCompany),
                              charityAllExempt: Option[Boolean] = None,
                              charityNoIncome: Option[Boolean] = None): Set[Return] = {

    if (isIllegalArguments(companyType.value, hmrcFiling.value, coHoFiling.value, microEntityFiling.value)) {
      throw new IllegalArgumentException(s"")
    }

    val accountsVersion = AccountsVersion1

    val cohoReturn: Set[Return] = (coHoFiling, microEntityFiling, statutoryAccountsFiling, abridgedFiling, abbreviatedAccountsFiling) match {
      case (CompaniesHouseFiling(true), MicroEntityFiling(true), _, AbridgedFiling(false), _) =>
        Set(Return(CoHoMicroEntityAccounts, accountsVersion))

      case (CompaniesHouseFiling(true), MicroEntityFiling(true), _, AbridgedFiling(true), _) =>
        Set(Return(CoHoMicroEntityAbridgedAccounts, accountsVersion))

      case (CompaniesHouseFiling(true), _, StatutoryAccountsFiling(true), _, AbbreviatedAccountsFiling(false)) =>
        Set(Return(CoHoStatutoryAccounts, accountsVersion))

      case (CompaniesHouseFiling(true), _, StatutoryAccountsFiling(true), _, AbbreviatedAccountsFiling(true)) =>
        Set(Return(CoHoStatutoryAbbreviatedAccounts, accountsVersion))

      case _ => Set.empty
    }

    val hmrcAccounts = (hmrcFiling, microEntityFiling, statutoryAccountsFiling, companyType) match {
      case (HMRCFiling(true), MicroEntityFiling(true), _, _) =>
        Set(Return(HmrcMicroEntityAccounts, accountsVersion))

      case (HMRCFiling(true), _, StatutoryAccountsFiling(true), FilingCompanyType(LimitedByGuaranteeCharity) | FilingCompanyType(LimitedBySharesCharity)) =>
        Set(Return(HmrcStatutoryAccounts, accountsVersion))

      case (HMRCFiling(true), _, StatutoryAccountsFiling(true), FilingCompanyType(LimitedByGuaranteeCASC) | FilingCompanyType(LimitedBySharesCASC)) =>
        Set(Return(HmrcStatutoryAccounts, accountsVersion))

      case (HMRCFiling(true), _, StatutoryAccountsFiling(true), _) =>
        Set(Return(HmrcStatutoryAccounts, accountsVersion))

      case (HMRCFiling(true), MicroEntityFiling(false), StatutoryAccountsFiling(false), _) =>
        Set(Return(HmrcUploadedAccounts, UploadedAccounts))

      case _ => Set.empty
    }

    val ct600Version = apStartDate.map(ct600VersionBasedOnApStartDate)

    val ct600Returns = if (hmrcFiling == HMRCFiling(true)) {
      companyType match {
        case FilingCompanyType(LimitedBySharesCharity) | FilingCompanyType(LimitedBySharesCASC) => ct600ForLimitedBySharesCharity(ct600Version, charityAllExempt, charityNoIncome)
        case FilingCompanyType(LimitedByGuaranteeCharity) | FilingCompanyType(LimitedByGuaranteeCASC) => ct600ForLimitedByGuaranteeCharity(ct600Version, charityAllExempt, charityNoIncome)
        case FilingCompanyType(Charity) | FilingCompanyType(CASC) => ct600ForCharity(ct600Version, charityAllExempt, charityNoIncome)
        case FilingCompanyType(MembersClub) => ct600ReturnsForMembersClub(ct600Version.getOrElse(throw new IllegalStateException(s"Cannot have a hmrc filing without a version for CT600")))
        case FilingCompanyType(CompanyLimitedByGuarantee) => ct600ForLimitedByGuaranteeCompany(ct600Version.getOrElse(throw new IllegalStateException(s"Cannot have a hmrc filing without a version for CT600")))
        case FilingCompanyType(_) => ct600ForCompany(ct600Version.getOrElse(throw new IllegalStateException(s"Cannot have a hmrc filing without a version for CT600")))
      }
    }
    else Set.empty

    val compsReturns = (hmrcFiling, apStartDate, apEndDate, charityAllExempt, charityNoIncome, companyType) match {
      case (HMRCFiling(true), _, _, Some(true), _, FilingCompanyType(Charity) | FilingCompanyType(LimitedByGuaranteeCharity) |FilingCompanyType(LimitedBySharesCharity)) =>
        Set.empty

      case (HMRCFiling(true), _, _, Some(true), _, FilingCompanyType(CASC) | FilingCompanyType(LimitedByGuaranteeCASC) |FilingCompanyType(LimitedBySharesCASC)) =>
        Set.empty

      case (HMRCFiling(true), _, _, None, Some(true), FilingCompanyType(Charity) | FilingCompanyType(LimitedByGuaranteeCharity) |FilingCompanyType(LimitedBySharesCharity)) =>
        Set.empty

      case (HMRCFiling(true), _, _, None, Some(true), FilingCompanyType(CASC) | FilingCompanyType(LimitedByGuaranteeCASC) |FilingCompanyType(LimitedBySharesCASC)) =>
        Set.empty

      case (HMRCFiling(true), Some(startDate), Some(endDate), _, _, _) =>
        Set(Return(Computations, computationsVersionBasedOnDate(startDate, endDate)))

      case _ => Set.empty
    }

    cohoReturn ++ hmrcAccounts ++ ct600Returns ++ compsReturns
  }

  private def computationsVersionBasedOnDate(apStartDate: LocalDate, apEndDate: LocalDate): Version = {
    (apStartDate, apEndDate) match {
      case (startDate, _) if startDate.isAfter(LocalDate.parse("2015-03-31")) =>
        ComputationsCT20150201
      case (_, endDate) if endDate.isAfter(LocalDate.parse("2013-03-31")) =>
        ComputationsCT20141001
      case (_, endDate) if endDate.isAfter(LocalDate.parse("2008-03-31")) =>
        ComputationsCT20130721
      case _ => throw new IllegalArgumentException(s"Cannot calculate the Computations Version for the provided dates: $apStartDate -> $apEndDate")
    }
  }

  private def ct600VersionBasedOnApStartDate(apStartDate: LocalDate): Version = if (apStartDate.isAfter(LocalDate.parse("2015-03-31"))) CT600Version3 else CT600Version2

  private def ct600ReturnsForMembersClub(version: Version): Set[Return] = {
    Set(Return(CT600, version),
        Return(CT600j, version))
  }

  private def ct600ForCompany(version: Version): Set[Return] = {
    Set(Return(CT600, version),
        Return(CT600a, version),
        Return(CT600j, version))
  }

  private def isIllegalArguments(companyType: CompanyType, hmrcFiling: Boolean, coHoFiling: Boolean, microEntityFiling: Boolean): Boolean = {
    (companyType, hmrcFiling, coHoFiling, microEntityFiling) match {
      case (Charity | LimitedByGuaranteeCharity | LimitedBySharesCharity | CASC | LimitedByGuaranteeCASC | LimitedBySharesCASC, true, true, _) => true
      case (Charity | LimitedByGuaranteeCharity | LimitedBySharesCharity | CASC | LimitedByGuaranteeCASC | LimitedBySharesCASC, _, _, true) => true
      case _ => false
    }
  }

  private def v3CharityNoIncome(br: V3CT600EBoxRetriever): Option[Boolean] = {
    (br.retrieveE15().value, br.retrieveE20().value) match {
      case (Some(true), None) => Some(true)
      case _ => None
    }
  }

  private def v2CharityNoIncome(br: V2CT600EBoxRetriever): Option[Boolean] = {
    (br.retrieveE1010().value, br.retrieveE1011().value) match {
      case (Some(true), None) => Some(true)
      case _ => None
    }
  }
}
