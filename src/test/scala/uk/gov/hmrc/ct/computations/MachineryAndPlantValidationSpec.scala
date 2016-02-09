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

package uk.gov.hmrc.ct.computations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.stubs.StubbedComputationsBoxRetriever
import uk.gov.hmrc.ct.{CATO02, CATO20, CATO21, CATO22}

class MyStubbedComputationsRetriever(lec01: List[Car] = List(),
                                     cpq8: Option[Boolean] = None,
                                     cp78: Option[Int] = None,
                                     cp81Input: Option[Int] = None,
                                     cp82: Option[Int] = None,
                                     cp83: Option[Int] = None,
                                     cp84: Option[Int] = None,
                                     cp87Input: Option[Int] = None,
                                     cp88: Option[Int] = None,
                                     cp89: Option[Int] = None,
                                     cp666: Option[Int] = None,
                                     cp667: Option[Int] = None,
                                     cp668: Option[Int] = None,
                                     cp672: Option[Int] = None,
                                     cp673: Option[Int] = None,
                                     cp674: Option[Int] = None,
                                     cato02: Int = 0,
                                     cato20: Int = 0,
                                     cato21: Int = 0,
                                     cato22: Int = 0,
                                     cpAux1: Int = 0,
                                     cpAux2: Int = 0,
                                     cpAux3: Int = 0
                                      ) extends StubbedComputationsBoxRetriever {

  override def retrieveLEC01: LEC01 = LEC01(lec01)

  override def retrieveCPQ8: CPQ8 = CPQ8(cpq8)

  override def retrieveCP78: CP78 = CP78(cp78)

  override def retrieveCP666: CP666 = CP666(cp666)

  override def retrieveCP81Input: CP81Input = CP81Input(cp81Input)

  override def retrieveCP82: CP82 = CP82(cp82)

  override def retrieveCP83: CP83 = CP83(cp83)

  override def retrieveCP84: CP84 = CP84(cp84)

  override def retrieveCP667: CP667 = CP667(cp667)

  override def retrieveCP672: CP672 = CP672(cp672)

  override def retrieveCP673: CP673 = CP673(cp673)

  override def retrieveCP674: CP674 = CP674(cp674)

  override def retrieveCP87Input: CP87Input = CP87Input(cp87Input)

  override def retrieveCP88: CP88 = CP88(cp88)

  override def retrieveCP89: CP89 = CP89(cp89)

  override def retrieveCP668: CP668 = CP668(cp668)

  override def retrieveCATO02: CATO02 = CATO02(cato02)

  override def retrieveCATO20: CATO20 = CATO20(cato20)

  override def retrieveCATO21: CATO21 = CATO21(cato21)

  override def retrieveCATO22: CATO22 = CATO22(cato22)

  override def retrieveCPAux1: CPAux1 = CPAux1(cpAux1)

  override def retrieveCPAux2: CPAux2 = CPAux2(cpAux2)

  override def retrieveCPAux3: CPAux3 = CPAux3(cpAux3)
}


class MachineryAndPlantValidationSpec extends WordSpec with Matchers {
  val stubBoxRetriever = new MyStubbedComputationsRetriever

  "CP78 " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP78(Some(0)).validate(stubBoxRetriever) shouldBe Set()
      CP78(None).validate(stubBoxRetriever) shouldBe Set()
      CP78(Some(-1)).validate(stubBoxRetriever) shouldBe Set(CtValidation(boxId = Some("CP78"), errorMessageKey = "error.CP78.mustBeZeroOrPositive"))
    }
  }

  "CP666 " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP666(Some(0)).validate(stubBoxRetriever) shouldBe Set()
      CP666(None).validate(stubBoxRetriever) shouldBe Set()
      CP666(Some(-1)).validate(stubBoxRetriever) shouldBe Set(CtValidation(boxId = Some("CP666"), errorMessageKey = "error.CP666.mustBeZeroOrPositive"))
    }
  }

  "CP81Input " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP81Input(Some(0)).validate(stubBoxRetriever) shouldBe Set()
      CP81Input(None).validate(stubBoxRetriever) shouldBe Set()
      CP81Input(Some(-1)).validate(stubBoxRetriever) shouldBe Set(CtValidation(boxId = Some("CP81Input"), errorMessageKey = "error.CP81Input.mustBeZeroOrPositive"))
    }
  }

  "CP82 " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP82(Some(0)).validate(stubBoxRetriever) shouldBe Set()
      CP82(None).validate(stubBoxRetriever) shouldBe Set()
      CP82(Some(-1)).validate(stubBoxRetriever) shouldBe Set(CtValidation(boxId = Some("CP82"), errorMessageKey = "error.CP82.mustBeZeroOrPositive"))
    }
  }

  "CP83 " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP83(Some(0)).validate(stubBoxRetriever) shouldBe Set()
      CP83(None).validate(stubBoxRetriever) shouldBe Set()
      CP83(Some(-1)).validate(stubBoxRetriever) shouldBe Set(CtValidation(boxId = Some("CP83"), errorMessageKey = "error.CP83.mustBeZeroOrPositive"))
    }
  }

  "CP674 " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP674(Some(0)).validate(stubBoxRetriever) shouldBe Set()
      CP674(None).validate(stubBoxRetriever) shouldBe Set()
      CP674(Some(-1)).validate(stubBoxRetriever) shouldBe Set(CtValidation(boxId = Some("CP674"), errorMessageKey = "error.CP674.mustBeZeroOrPositive"))
    }
  }

  "CP84 " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP84(Some(0)).validate(stubBoxRetriever) shouldBe Set()
      CP84(None).validate(stubBoxRetriever) shouldBe Set()
      CP84(Some(-1)).validate(stubBoxRetriever) shouldBe Set(CtValidation(boxId = Some("CP84"), errorMessageKey = "error.CP84.mustBeZeroOrPositive"))
    }
  }

  "CP673 " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP673(Some(0)).validate(stubBoxRetriever) shouldBe Set()
      CP673(None).validate(stubBoxRetriever) shouldBe Set()
      CP673(Some(-1)).validate(stubBoxRetriever) shouldBe Set(CtValidation(boxId = Some("CP673"), errorMessageKey = "error.CP673.mustBeZeroOrPositive"))
    }
  }

  "CP667 " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP667(Some(0)).validate(stubBoxRetriever) shouldBe Set()
      CP667(None).validate(stubBoxRetriever) shouldBe Set()
      CP667(Some(-1)).validate(stubBoxRetriever) shouldBe Set(CtValidation(boxId = Some("CP667"), errorMessageKey = "error.CP667.mustBeZeroOrPositive"))
    }
  }

  "CP672 " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP672(Some(0)).validate(stubBoxRetriever) shouldBe Set()
      CP672(None).validate(stubBoxRetriever) shouldBe Set()
      CP672(Some(-1)).validate(stubBoxRetriever) shouldBe Set(CtValidation(boxId = Some("CP672"), errorMessageKey = "error.CP672.mustBeZeroOrPositive"))
    }
  }

  "CP87Input, given is trading and first Year Allowance Not Greater Than Max FYA" should {
    "validate if present and non-negative, otherwise fail" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(cpq8 = Some(false))

      CP87Input(Some(0)).validate(stubTestComputationsRetriever) shouldBe Set()
      CP87Input(Some(-1)).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP87Input"), errorMessageKey = "error.CP87Input.mustBeZeroOrPositive"))
    }
  }

  "CP87Input, given is non-negative" should {
    "validate correctly when not greater than CP81  CPaux1" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(
        cpq8 = Some(false),
        cp81Input = Some(49),
        cpAux1 = 51)

      CP87Input(Some(100)).validate(stubTestComputationsRetriever) shouldBe Set()
    }

    "fail validation when greater than CP81  CPaux1" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(
        cpq8 = Some(false),
        cp81Input = Some(49),
        cpAux1 = 51)

      CP87Input(Some(101)).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP87Input"), errorMessageKey = "error.CP87Input.firstYearAllowanceClaimExceedsAllowance"))
    }

    "validate because FYA defaults to 0 when not entered" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(
        cpq8 = Some(true),
        cp81Input = Some(49),
        cpAux1 = 51)

      CP87Input(None).validate(stubTestComputationsRetriever) shouldBe Set()
    }

    "fail validation when trading but no value entered" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(cpq8 = Some(false))

      CP87Input(None).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP87Input"), errorMessageKey = "error.CP87Input.fieldMustHaveValueIfTrading"))
    }
    "validate when ceased trading but no value entered" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(cpq8 = Some(true))

      CP87Input(None).validate(stubTestComputationsRetriever) shouldBe Set()
    }
    "validate when ceased trading not set" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever()

      CP87Input(None).validate(stubTestComputationsRetriever) shouldBe Set()
    }
    "fails validation when negative" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(cpq8 = Some(false))

      CP87Input(-1).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP87Input"), errorMessageKey = "error.CP87Input.mustBeZeroOrPositive"))
    }
  }

  "CP88(annual investment allowance claimed)" should {

    "fail to validate when negative" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever()

      CP88(Some(-1)).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP88"), errorMessageKey = "error.CP88.mustBeZeroOrPositive"))
    }

    "validate correctly when not greater than the minimum of CATO02 (maxAIA) and CP83 (expenditureQualifyingAnnualInvestmentAllowance)" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(
        cp81Input = Some(11),
        cato02 = 10
      )

      CP88(Some(10)).validate(stubTestComputationsRetriever) shouldBe Set()
    }

    "fails validation when greater than the minimum of CATO02 (maxAIA) and CP83 (expenditureQualifyingAnnualInvestmentAllowance)" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(
        cp81Input = Some(11),
        cato02 = 10
      )

      CP88(Some(11)).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP88"), errorMessageKey = "error.CP88.annualInvestmentAllowanceExceeded"))
    }

    "fails validation when CATO02 (maxAIA) is the minimum" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(
        cp81Input = Some(10),
        cato02 = 11
      )

      CP88(Some(11)).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP88"), errorMessageKey = "error.CP88.annualInvestmentAllowanceExceeded"))
    }

    "fail validation when trading but no value entered" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(cpq8 = Some(false))

      CP88(None).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP88"), errorMessageKey = "error.CP88.fieldMustHaveValueIfTrading"))
    }
    "validate when ceased trading but no value entered" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(cpq8 = Some(true))

      CP88(None).validate(stubTestComputationsRetriever) shouldBe Set()
    }
    "validate when ceased trading not set" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever()

      CP88(None).validate(stubTestComputationsRetriever) shouldBe Set()
    }
    "fails validation when negative" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(cpq8 = Some(false))

      CP88(-1).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP88"), errorMessageKey = "error.CP88.mustBeZeroOrPositive"))
    }
  }

  "CP89 (Writing Down Allowance claimed from Main pool)" should {

    "validates correctly when not greater than MAX(0, MainPool% * ( CP78 (Main Pool brought forward) " +
      "+ CP82 (Additions Qualifying for Main Pool) + MainRatePool - CP672 (Proceed from Disposals from Main Pool) " +
      "+ UnclaimedAIA_FYA (Unclaimed FYA and AIA amounts)) - CATO-2730" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(
        cp78 = Some(2000),    // writtenDownValueBroughtForward
        cp81Input = Some(50), // expenditureQualifyingForFirstYearAllowanceInput
        cp82 = Some(2000),    // additionsQualifyingWritingDownAllowanceMainPool
        cp83 = Some(50),      // expenditureQualifyingAnnualInvestmentAllowance
        cp87Input = Some(50), // firstYearAllowanceClaimedInput
        cp672 = Some(1000),   // proceedsFromDisposalsFromMainPool
        cpAux1 = 0,
        cpAux2 = 0,
        cato21 = 18
      )

      CP89(549).validate(stubTestComputationsRetriever) shouldBe Set()
      CP89(550).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP89"), errorMessageKey = "error.CP89.mainPoolAllowanceExceeded"))
    }

    "validates when greater than MAX(0, MainPool% * ( CP78 (Main Pool brought forward) " +
      "+ CP82 (Additions Qualifying for Main Pool) + MainRatePool - CP672 (Proceed from Disposals from Main Pool) " +
      "+ LEC14 (Unclaimed FYA and AIA amounts)))" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(
        cp78 = Some(100),   // writtenDownValueBroughtForward
        cp82 = Some(100),   // additionsQualifyingWritingDownAllowanceMainPool
        cp672 = Some(100),  // proceedsFromDisposalsFromMainPool
        cpAux2 = 50,
        cato21 = 10,
        cato20 = 50
      )

      CP89(15).validate(stubTestComputationsRetriever) shouldBe Set()
      CP89(16).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP89"), errorMessageKey = "error.CP89.mainPoolAllowanceExceeded"))
    }

    "validated when CP672 is large enough to make the total -ve and any +ve claim is made" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(
        cp78 = Some(100),   // writtenDownValueBroughtForward
        cp82 = Some(100),   // additionsQualifyingWritingDownAllowanceMainPool
        cp672 = Some(1000), // proceedsFromDisposalsFromMainPool
        cpAux2 = 100,
        cato21 = 10
      )

      CP89(0).validate(stubTestComputationsRetriever) shouldBe Set()
      CP89(1).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP89"), errorMessageKey = "error.CP89.mainPoolAllowanceExceeded"))
    }

    "fail validation when trading but no value entered" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(cpq8 = Some(false))

      CP89(None).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP89"), errorMessageKey = "error.CP89.fieldMustHaveValueIfTrading"))
    }
    "validate when ceased trading but no value entered" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(cpq8 = Some(true))

      CP89(None).validate(stubTestComputationsRetriever) shouldBe Set()
    }
    "validate when ceased trading not set" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever()

      CP89(None).validate(stubTestComputationsRetriever) shouldBe Set()
    }
    "fails validation when negative" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(cpq8 = Some(false))

      CP89(-1).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP89"), errorMessageKey = "error.CP89.mustBeZeroOrPositive"))
    }
  }

  "(CP668) Writing Down Allowance claimed from Special rate pool" should {
     "validates correctly when not greater than MAX( 0, SpecialPool% * ( CP666 + CPaux3 - CP667) )" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(
        cp666 = Some(100), // writtenDownValueOfSpecialRatePoolBroughtForward
        cp667 = Some(100), // proceedsFromDisposalsFromSpecialRatePool
        cpAux3 = 100,
        cato22 = 10
      )

      CP668(10).validate(stubTestComputationsRetriever) shouldBe Set()
      CP668(11).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP668"), errorMessageKey = "error.CP668.specialRatePoolAllowanceExceeded"))
     }

    "fails validation when CP667 is large enough to make the total -ve and any +ve claim is made" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(
          cp666 = Some(100),  // writtenDownValueOfSpecialRatePoolBroughtForward
          cp667 = Some(1000), // proceedsFromDisposalsFromSpecialRatePool
          cpAux3 = 100,
          cato22 = 10
      )

      CP668(0).validate(stubTestComputationsRetriever) shouldBe Set()
      CP668(1).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP668"), errorMessageKey = "error.CP668.specialRatePoolAllowanceExceeded"))
    }

    "fail validation when trading but no value entered" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(cpq8 = Some(false))

      CP668(None).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP668"), errorMessageKey = "error.CP668.fieldMustHaveValueIfTrading"))
    }
    "validate when ceased trading but no value entered" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(cpq8 = Some(true))

      CP668(None).validate(stubTestComputationsRetriever) shouldBe Set()
    }
    "validate when ceased trading not set" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever()

      CP668(None).validate(stubTestComputationsRetriever) shouldBe Set()
    }
    "fails validation when negative" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(cpq8 = Some(false))

      CP668(-1).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP668"), errorMessageKey = "error.CP668.mustBeZeroOrPositive"))
    }
  }
}